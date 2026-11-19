# ORANGIA — DATABASE DESIGN

**Version : 1.0 — Version Finale**
**SGBD : PostgreSQL**
**Application : Java 21 / JavaFX / Maven**
**Architecture : MVC — DAO — Service**

---

# 1. Présentation générale

ORANGIA est une application desktop destinée à la gestion des fermes spécialisées dans la production d'oranges.

L'application permet :

- La gestion des utilisateurs et des rôles.
- La gestion des fermes et des parcelles.
- La gestion des ouvriers et de leurs affectations.
- La gestion des présences et des salaires.
- La gestion des récoltes.
- La gestion des clients et des expéditions.
- La gestion des paiements.
- La gestion des dépenses.
- La gestion des documents et notifications.
- La traçabilité des opérations.
- Le calcul automatique du stock.

---

# 2. Règles métier générales

## Utilisateurs

- Chaque utilisateur possède exactement un rôle.
- Les comptes sont créés uniquement par l'Administrateur.
- Les ouvriers ne possèdent aucun compte utilisateur.
- Le Responsable est affecté à une seule ferme.
- RH et Comptable disposent d'un accès global.

## Fermes

- Une ferme possède plusieurs parcelles.
- Une ferme possède au maximum un Responsable actif.

## Ouvriers

- Un ouvrier peut être affecté à plusieurs fermes dans le temps.
- Une seule affectation active est autorisée à un instant donné.
- Les affectations historiques sont conservées.

## Présences

- Un ouvrier ne possède qu'une seule présence par jour.
- Une fiche de présence contient plusieurs présences.
- Après transmission, la fiche ne peut plus être modifiée directement.

## Salaires

- Les salaires sont calculés par RH.
- Plusieurs paiements sont autorisés pour un salaire.
- Les anciens calculs ne doivent jamais être modifiés.

## Récoltes

- Une récolte appartient à une parcelle.
- Une récolte annulée reste dans l'historique.

## Expéditions

- Une expédition appartient à une ferme et à un client.
- Une expédition doit être confirmée par le Comptable.
- Une expédition non confirmée n'affecte pas le stock.
- Une expédition confirmée diminue le stock.

## Dépenses

- Une demande de dépense peut être acceptée ou refusée.
- Une dépense réelle peut provenir d'une demande acceptée.

---

# 3. Tables du système

## A. Authentification

### ROLE

| Attribut | Type |
|----------|------|
| id_role | BIGINT PK |
| nom_role | VARCHAR(50) UNIQUE |
| description | TEXT |

Valeurs :

- ADMINISTRATEUR
- RESPONSABLE
- RH
- COMPTABLE

---

### UTILISATEUR

| Attribut | Type |
|----------|------|
| id_utilisateur | BIGINT PK |
| id_role | BIGINT FK |
| id_ferme | BIGINT FK NULL |
| identifiant | VARCHAR(100) UNIQUE |
| mot_de_passe | TEXT |
| nom | VARCHAR(100) |
| prenom | VARCHAR(100) |
| telephone | VARCHAR(20) |
| email | VARCHAR(150) |
| statut | VARCHAR(20) |
| date_creation | TIMESTAMPTZ |
| derniere_connexion | TIMESTAMPTZ |

Contraintes :

- ADMINISTRATEUR : id_ferme = NULL
- RH : id_ferme = NULL
- COMPTABLE : id_ferme = NULL
- RESPONSABLE : id_ferme obligatoire

---

# B. Fermes et parcelles

### FERME

| Attribut | Type |
|----------|------|
| id_ferme | BIGINT PK |
| code_ferme | VARCHAR(30) UNIQUE |
| nom_ferme | VARCHAR(100) |
| localisation | VARCHAR(255) |
| superficie | NUMERIC(10,2) |
| statut | VARCHAR(20) |
| date_creation | TIMESTAMPTZ |

---

### PARCELLE

| Attribut | Type |
|----------|------|
| id_parcelle | BIGINT PK |
| id_ferme | BIGINT FK |
| code_parcelle | VARCHAR(30) |
| nom_parcelle | VARCHAR(100) |
| superficie | NUMERIC(10,2) |
| variete_orange | VARCHAR(100) |
| nombre_arbres | INTEGER |
| annee_plantation | INTEGER |
| statut | VARCHAR(20) |

Contrainte :

```sql
UNIQUE(id_ferme, code_parcelle)
```

---

# C. Ressources humaines

### OUVRIER

| Attribut | Type |
|----------|------|
| id_ouvrier | BIGINT PK |
| code_ouvrier | VARCHAR(30) UNIQUE |
| nom | VARCHAR(100) |
| prenom | VARCHAR(100) |
| telephone | VARCHAR(20) |
| adresse | TEXT |
| date_embauche | DATE |
| categorie_ouvrier | VARCHAR(20) |
| statut | VARCHAR(20) |

Valeurs :

- NORMAL
- CABRAN
- NAIB

---

### AFFECTATION_OUVRIER

| Attribut | Type |
|----------|------|
| id_affectation | BIGINT PK |
| id_ouvrier | BIGINT FK |
| id_ferme | BIGINT FK |
| date_debut | DATE |
| date_fin | DATE NULL |
| motif | TEXT |
| cree_par | BIGINT FK |

Règle :

- Un seul enregistrement actif par ouvrier.

---

### TARIF_OUVRIER

| Attribut | Type |
|----------|------|
| id_tarif | BIGINT PK |
| id_ouvrier | BIGINT FK |
| mode_remuneration | VARCHAR(20) |
| montant | NUMERIC(12,2) |
| date_debut | DATE |
| date_fin | DATE NULL |

Modes :

- HORAIRE
- JOURNALIER
- MENSUEL

---

# D. Présences

### FICHE_PRESENCE

| Attribut | Type |
|----------|------|
| id_fiche | BIGINT PK |
| id_ferme | BIGINT FK |
| date_fiche | DATE |
| statut | VARCHAR(20) |
| transmis_par | BIGINT FK |
| valide_par | BIGINT FK |
| observation | TEXT |

Statuts :

- BROUILLON
- TRANSMISE
- VALIDEE
- A_CORRIGER

---

### PRESENCE

| Attribut | Type |
|----------|------|
| id_presence | BIGINT PK |
| id_fiche | BIGINT FK |
| id_ouvrier | BIGINT FK |
| date_presence | DATE |
| statut_presence | VARCHAR(20) |
| heure_arrivee | TIME |
| heure_depart | TIME |
| heure_debut_appliquee | TIME |
| heure_fin_appliquee | TIME |
| absence_justifiee | BOOLEAN |
| justification_acceptee | BOOLEAN |
| observation | TEXT |

Statuts :

- PRESENT
- ABSENT
- RETARD
- PARTIEL
- CONGE

Contrainte :

```sql
UNIQUE(id_ouvrier, date_presence)
```

---

### DEMANDE_CORRECTION

| Attribut | Type |
|----------|------|
| id_demande_correction | BIGINT PK |
| id_presence | BIGINT FK |
| demande_par | BIGINT FK |
| motif | VARCHAR(150) |
| description | TEXT |
| document_id | BIGINT FK NULL |
| statut | VARCHAR(20) |
| traitee_par | BIGINT FK |
| commentaire_rh | TEXT |

Statuts :

- EN_ATTENTE
- ACCEPTEE
- REFUSEE

---

# E. Salaires

### SALAIRE

| Attribut | Type |
|----------|------|
| id_salaire | BIGINT PK |
| id_ouvrier | BIGINT FK |
| id_ferme | BIGINT FK |
| id_tarif | BIGINT FK |
| periode_debut | DATE |
| periode_fin | DATE |
| montant_base | NUMERIC(12,2) |
| montant_prime | NUMERIC(12,2) |
| montant_total | NUMERIC(12,2) |
| detail_calcul | JSONB |

---

### PAIEMENT_SALAIRE

| Attribut | Type |
|----------|------|
| id_paiement_salaire | BIGINT PK |
| id_salaire | BIGINT FK |
| montant_paye | NUMERIC(12,2) |
| date_paiement | DATE |
| mode_paiement | VARCHAR(30) |
| reference | VARCHAR(100) |
| enregistre_par | BIGINT FK |

---

# F. Récoltes

### RECOLTE

| Attribut | Type |
|----------|------|
| id_recolte | BIGINT PK |
| id_parcelle | BIGINT FK |
| reference | VARCHAR(40) UNIQUE |
| date_recolte | DATE |
| quantite_kg | NUMERIC(12,2) |
| qualite | VARCHAR(20) |
| statut | VARCHAR(20) |
| observation | TEXT |
| cree_par | BIGINT FK |

Statuts :

- ENREGISTREE
- ANNULEE

---

# G. Clients et expéditions

### CLIENT

| Attribut | Type |
|----------|------|
| id_client | BIGINT PK |
| code_client | VARCHAR(30) UNIQUE |
| nom_client | VARCHAR(150) |
| telephone | VARCHAR(20) |
| email | VARCHAR(150) |
| adresse | TEXT |
| ville | VARCHAR(100) |
| statut | VARCHAR(20) |

---

### EXPEDITION

| Attribut | Type |
|----------|------|
| id_expedition | BIGINT PK |
| id_ferme | BIGINT FK |
| id_client | BIGINT FK |
| reference | VARCHAR(40) UNIQUE |
| date_expedition | DATE |
| quantite_kg | NUMERIC(12,2) |
| quantite_facturee | NUMERIC(12,2) |
| prix_unitaire | NUMERIC(12,2) |
| montant_total | NUMERIC(16,2) |
| statut_expedition | VARCHAR(30) |
| statut_confirmation | VARCHAR(25) |
| motif_refus | TEXT |
| confirme_par | BIGINT FK |
| cree_par | BIGINT FK |
| observation | TEXT |

Statuts expédition :

- BROUILLON
- EN_ATTENTE_CONFIRMATION
- CONFIRMEE
- ANNULEE

Statuts confirmation :

- EN_ATTENTE
- CONFIRMEE
- REFUSEE

---

### PAIEMENT_EXPEDITION

| Attribut | Type |
|----------|------|
| id_paiement | BIGINT PK |
| id_expedition | BIGINT FK |
| montant_paye | NUMERIC(12,2) |
| date_paiement | DATE |
| mode_paiement | VARCHAR(30) |
| reference | VARCHAR(100) |
| enregistre_par | BIGINT FK |

---

# H. Dépenses

### DEMANDE_DEPENSE

| Attribut | Type |
|----------|------|
| id_demande | BIGINT PK |
| id_ferme | BIGINT FK |
| reference | VARCHAR(40) UNIQUE |
| sujet | VARCHAR(150) |
| categorie | VARCHAR(80) |
| montant_estime | NUMERIC(12,2) |
| description | TEXT |
| justification | TEXT |
| statut | VARCHAR(20) |
| cree_par | BIGINT FK |
| traitee_par | BIGINT FK |
| commentaire_comptable | TEXT |

Statuts :

- EN_ATTENTE
- ACCEPTEE
- REFUSEE
- ANNULEE

---

### DEPENSE

| Attribut | Type |
|----------|------|
| id_depense | BIGINT PK |
| id_ferme | BIGINT FK |
| id_demande | BIGINT FK NULL |
| reference | VARCHAR(40) UNIQUE |
| categorie | VARCHAR(80) |
| description | TEXT |
| montant | NUMERIC(12,2) |
| date_depense | DATE |
| mode_paiement | VARCHAR(30) |
| statut | VARCHAR(20) |
| enregistre_par | BIGINT FK |

---

# I. Services communs

### DOCUMENT

| Attribut | Type |
|----------|------|
| id_document | BIGINT PK |
| nom_fichier | VARCHAR(255) |
| chemin_fichier | TEXT |
| extension | VARCHAR(10) |
| type_mime | VARCHAR(100) |
| taille_octets | BIGINT |
| type_operation | VARCHAR(50) |
| id_operation | BIGINT |
| ajoute_par | BIGINT FK |

---

### NOTIFICATION

| Attribut | Type |
|----------|------|
| id_notification | BIGINT PK |
| id_utilisateur | BIGINT FK |
| titre | VARCHAR(150) |
| message | TEXT |
| type_notification | VARCHAR(50) |
| lu | BOOLEAN |
| date_creation | TIMESTAMPTZ |

---

### JOURNAL_ACTION

| Attribut | Type |
|----------|------|
| id_action | BIGINT PK |
| id_utilisateur | BIGINT FK |
| id_ferme | BIGINT FK NULL |
| action | VARCHAR(100) |
| type_operation | VARCHAR(50) |
| id_operation | BIGINT |
| description | TEXT |
| date_action | TIMESTAMPTZ |

---

### PARAMETRE_SYSTEME

| Attribut | Type |
|----------|------|
| id_parametre | BIGINT PK |
| cle | VARCHAR(100) UNIQUE |
| valeur | TEXT |
| description | TEXT |
| modifie_par | BIGINT FK |

Exemples :

- HEURE_DEBUT_TRAVAIL
- HEURE_FIN_TRAVAIL
- PRIME_CABRAN
- NOMBRE_ABSENCES_MAX_PRIME_CABRAN

---

# 4. Vue calculée

## V_STOCK_FERME

Le stock est calculé automatiquement.

Formule :

```text
Stock disponible =
Récoltes enregistrées non annulées
-
Expéditions confirmées non annulées
```

Attributs :

- id_ferme
- total_recoltes
- total_expeditions
- stock_disponible

Aucune modification manuelle du stock n'est autorisée.

---

# 5. Tables exclues du modèle

Les tables suivantes ne font pas partie de la version actuelle :

- UTILISATEUR_FERME
- PERMISSION
- ROLE_PERMISSION
- EXPEDITION_RECOLTE
- PRODUIT_STOCK
- MOUVEMENT_STOCK

---

# 6. Décision finale

Ce document constitue la référence officielle de conception de la base de données ORANGIA.

Il servira de base pour :

- Le diagramme UML
- Le schéma SQL PostgreSQL
- Les entités Java
- Les DAO
- Les Services
- Les contrôleurs JavaFX
- Les tests

**Statut : FINAL**
**Version : 1.0**