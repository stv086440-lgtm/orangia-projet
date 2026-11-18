# ORANGIA — DATABASE DESIGN


# 1. Analyse des besoins

## 1.1 Présentation du projet

ORANGIA est une application desktop destinée à la gestion des fermes spécialisées dans la production d'oranges.

Elle permet de centraliser les informations et de faciliter la gestion des activités agricoles, du personnel, des récoltes et des opérations financières.

## 1.2 Objectifs du projet

L'application a pour objectifs de :

- Gérer les fermes et les parcelles.
- Gérer les ouvriers et leurs affectations.
- Suivre les présences et les absences.
- Calculer les salaires et gérer les paiements.
- Enregistrer les récoltes.
- Gérer les clients et les expéditions.
- Gérer les dépenses.
- Calculer automatiquement le stock disponible.
- Conserver l'historique des opérations.

## 1.3 Les acteurs

L'application contient quatre espaces de travail :

**1. Administrateur**
- Gérer les comptes utilisateurs.
- Gérer les fermes.
- Gérer les paramètres du système.
- Consulter les informations générales.

**2. Responsable de ferme**
- Gérer sa ferme et ses parcelles.
- Gérer les récoltes.
- Enregistrer les présences.
- Gérer les clients et créer les expéditions.
- Envoyer les demandes de dépenses.

**3. Agent RH**
- Gérer les ouvriers et leurs affectations.
- Gérer les tarifs des ouvriers.
- Vérifier les présences.
- Traiter les demandes de correction.
- Calculer les salaires et enregistrer les paiements.

**4. Comptable**
- Vérifier et confirmer les expéditions.
- Gérer les paiements des expéditions.
- Traiter les demandes de dépenses.
- Enregistrer les dépenses.
- Consulter les informations financières.

## 1.4 Fonctionnement de l'authentification

Au démarrage, l'application affiche quatre espaces de travail :

- Administrateur
- Responsable
- RH
- Comptable

L'utilisateur sélectionne son espace, puis accède à l'interface de connexion correspondante.

Les comptes sont créés et gérés par l'administrateur. Il n'existe pas d'inscription publique.

Le système vérifie l'identifiant, le mot de passe et le rôle de l'utilisateur avant de lui permettre d'accéder à son espace.

## 1.5 Règles de gestion principales

- Chaque utilisateur possède un seul rôle.
- Chaque ferme peut avoir plusieurs parcelles.
- Un responsable est associé à une ferme.
- Un ouvrier peut travailler dans différentes fermes au cours du temps.
- Chaque ouvrier possède un historique d'affectations et de tarifs.
- Une fiche de présence contient plusieurs présences.
- Une présence concerne un seul ouvrier pour une date donnée.
- Un salaire peut recevoir plusieurs paiements.
- Une récolte appartient à une parcelle.
- Une expédition appartient à une ferme et concerne un client.
- Le comptable doit confirmer une expédition avant sa prise en compte dans le stock.
- Une demande de dépense acceptée peut être transformée en dépense réelle.
- Le stock est calculé automatiquement à partir des récoltes et des expéditions confirmées.
- Chaque utilisateur accède uniquement aux fonctionnalités et aux informations autorisées.

---

# 2. Modèle Conceptuel de Données (MCD)

Le MCD représente les principales entités du système et leurs relations, sans entrer dans les détails techniques de PostgreSQL.

## 2.1 Les entités

Le système ORANGIA contient les entités suivantes :

| Entité | Description |
|---|---|
| ROLE | Les rôles des utilisateurs |
| UTILISATEUR | Les comptes de connexion |
| FERME | Les fermes agricoles |
| PARCELLE | Les parcelles de chaque ferme |
| OUVRIER | Les employés agricoles |
| AFFECTATION_OUVRIER | L'historique des affectations |
| TARIF_OUVRIER | L'historique des rémunérations |
| FICHE_PRESENCE | Les fiches quotidiennes de présence |
| PRESENCE | La présence de chaque ouvrier |
| DEMANDE_CORRECTION | Les demandes de correction |
| SALAIRE | Les salaires calculés |
| PAIEMENT_SALAIRE | Les paiements des salaires |
| RECOLTE | Les quantités d'oranges récoltées |
| CLIENT | Les clients de la ferme |
| EXPEDITION | Les expéditions d'oranges |
| PAIEMENT_EXPEDITION | Les paiements des expéditions |
| DEMANDE_DEPENSE | Les demandes de dépenses |
| DEPENSE | Les dépenses réellement enregistrées |
| DOCUMENT | Les documents et justificatifs |
| NOTIFICATION | Les notifications des utilisateurs |
| JOURNAL_ACTION | L'historique des actions |
| PARAMETRE_SYSTEME | Les paramètres de l'application |

## 2.2 Les associations et les cardinalités

| Association | Cardinalité |
|---|---|
| Un rôle peut être attribué à plusieurs utilisateurs. | 1,N |
| Une ferme contient plusieurs parcelles. | 1,N |
| Une ferme peut avoir plusieurs responsables au cours du temps. | 1,N |
| Un ouvrier peut avoir plusieurs affectations. | 1,N |
| Une ferme peut recevoir plusieurs affectations. | 1,N |
| Un ouvrier peut avoir plusieurs tarifs. | 1,N |
| Une ferme possède plusieurs fiches de présence. | 1,N |
| Une fiche de présence contient plusieurs présences. | 1,N |
| Un ouvrier peut avoir plusieurs présences. | 1,N |
| Une présence peut avoir plusieurs demandes de correction. | 1,N |
| Un ouvrier peut avoir plusieurs salaires. | 1,N |
| Un salaire peut recevoir plusieurs paiements. | 1,N |
| Une parcelle peut avoir plusieurs récoltes. | 1,N |
| Un client peut avoir plusieurs expéditions. | 1,N |
| Une ferme peut avoir plusieurs expéditions. | 1,N |
| Une expédition peut recevoir plusieurs paiements. | 1,N |
| Une ferme peut avoir plusieurs demandes de dépenses. | 1,N |
| Une demande de dépense peut être liée à une dépense réelle. | 0,1 |
| Une ferme peut avoir plusieurs dépenses. | 1,N |
| Un utilisateur peut recevoir plusieurs notifications. | 1,N |
| Un utilisateur peut effectuer plusieurs actions enregistrées. | 1,N |

## 2.3 Gestion du stock

Le stock n'est pas une entité indépendante.

Il est calculé automatiquement à partir de deux éléments :

- Les récoltes enregistrées et non annulées.
- Les expéditions confirmées et non annulées.

**Formule :**

Stock disponible = Total des récoltes − Total des expéditions confirmées.

---

# 3. Modèle Logique de Données (MLD)

Le MLD transforme les entités du MCD en tables relationnelles.

Chaque table possède une clé primaire (PK). Les clés étrangères (FK) permettent de relier les tables entre elles.

## 3.1 Les tables

### A. Authentification

**ROLE**
- id_role (PK)
- nom_role
- description

**UTILISATEUR**
- id_utilisateur (PK)
- id_role (FK)
- id_ferme (FK, facultatif)
- identifiant
- mot_de_passe
- nom
- prenom
- telephone
- email
- statut

### B. Fermes et parcelles

**FERME**
- id_ferme (PK)
- code_ferme
- nom_ferme
- localisation
- superficie
- statut

**PARCELLE**
- id_parcelle (PK)
- id_ferme (FK)
- code_parcelle
- nom_parcelle
- superficie
- variete_orange
- nombre_arbres
- annee_plantation
- statut

### C. Personnel et rémunération

**OUVRIER**
- id_ouvrier (PK)
- code_ouvrier
- nom
- prenom
- telephone
- adresse
- date_embauche
- categorie_ouvrier
- statut

**AFFECTATION_OUVRIER**
- id_affectation (PK)
- id_ouvrier (FK)
- id_ferme (FK)
- date_debut
- date_fin
- motif
- cree_par (FK)

**TARIF_OUVRIER**
- id_tarif (PK)
- id_ouvrier (FK)
- mode_remuneration
- montant
- date_debut
- date_fin
- cree_par (FK)

### D. Présences et corrections

**FICHE_PRESENCE**
- id_fiche (PK)
- id_ferme (FK)
- date_fiche
- statut
- transmis_par (FK)
- valide_par (FK)
- observation

**PRESENCE**
- id_presence (PK)
- id_fiche (FK)
- id_ouvrier (FK)
- date_presence
- statut_presence
- heure_arrivee
- heure_depart
- heure_debut_appliquee
- heure_fin_appliquee
- absence_justifiee
- justification_acceptee
- observation

**DEMANDE_CORRECTION**
- id_demande_correction (PK)
- id_presence (FK)
- demande_par (FK)
- motif
- description
- document_id (FK, facultatif)
- statut
- traitee_par (FK)
- commentaire_rh

### E. Salaires et paiements

**SALAIRE**
- id_salaire (PK)
- id_ouvrier (FK)
- id_ferme (FK)
- id_tarif (FK)
- periode_debut
- periode_fin
- montant_base
- montant_prime
- montant_total
- detail_calcul

**PAIEMENT_SALAIRE**
- id_paiement_salaire (PK)
- id_salaire (FK)
- montant_paye
- date_paiement
- mode_paiement
- reference
- enregistre_par (FK)

### F. Récoltes

**RECOLTE**
- id_recolte (PK)
- id_parcelle (FK)
- reference
- date_recolte
- quantite_kg
- qualite
- statut
- observation
- cree_par (FK)

### G. Clients et expéditions

**CLIENT**
- id_client (PK)
- code_client
- nom_client
- telephone
- email
- adresse
- ville
- statut

**EXPEDITION**
- id_expedition (PK)
- id_ferme (FK)
- id_client (FK)
- reference
- date_expedition
- quantite_kg
- quantite_facturee
- prix_unitaire
- montant_total
- statut_expedition
- motif_refus
- confirme_par (FK)
- cree_par (FK)
- observation

**PAIEMENT_EXPEDITION**
- id_paiement (PK)
- id_expedition (FK)
- montant_paye
- date_paiement
- mode_paiement
- reference
- enregistre_par (FK)

### H. Dépenses

**DEMANDE_DEPENSE**
- id_demande (PK)
- id_ferme (FK)
- reference
- sujet
- categorie
- montant_estime
- description
- justification
- statut
- cree_par (FK)
- traitee_par (FK)
- commentaire_comptable

**DEPENSE**
- id_depense (PK)
- id_ferme (FK)
- id_demande (FK, facultatif)
- reference
- categorie
- description
- montant
- date_depense
- mode_paiement
- statut
- enregistre_par (FK)

### I. Gestion commune

**DOCUMENT**
- id_document (PK)
- nom_fichier
- chemin_fichier
- extension
- type_mime
- taille_octets
- type_operation
- id_operation
- ajoute_par (FK)

**NOTIFICATION**
- id_notification (PK)
- id_utilisateur (FK)
- titre
- message
- type_notification
- lu
- date_creation

**JOURNAL_ACTION**
- id_action (PK)
- id_utilisateur (FK)
- id_ferme (FK, facultatif)
- action
- type_operation
- id_operation
- description
- date_action

**PARAMETRE_SYSTEME**
- id_parametre (PK)
- cle
- valeur
- description
- modifie_par (FK)

---

# 4. Modèle Physique de Données (MPD)

Le MPD représente la structure réelle de la base de données dans PostgreSQL.

Il définit les types de données, les clés primaires, les clés étrangères et les contraintes.

## 4.1 Types de données utilisés

| Type PostgreSQL | Utilisation |
|---|---|
| BIGINT | Identifiants |
| VARCHAR(n) | Textes de longueur limitée |
| TEXT | Textes longs |
| INTEGER | Nombres entiers |
| NUMERIC(12,2) | Montants et quantités |
| DATE | Dates |
| TIME | Horaires |
| TIMESTAMPTZ | Dates et heures de traçabilité |
| BOOLEAN | Valeurs Vrai/Faux |
| JSONB | Détails de calcul structurés |

## 4.2 Principales contraintes

- Chaque table possède une clé primaire.
- Les clés étrangères assurent les relations entre les tables.
- Les identifiants et références importantes doivent être uniques.
- Les montants et les quantités doivent être positifs.
- Les données obligatoires utilisent NOT NULL.
- Les données facultatives peuvent accepter NULL.
- Les données historiques ne doivent pas être supprimées physiquement.

## 4.3 Règles particulières

**Utilisateurs :**
- Chaque utilisateur possède un seul rôle.
- L'administrateur, le RH et le comptable ne sont pas associés à une ferme.
- Le responsable est associé à une ferme.
- Un seul responsable actif est autorisé par ferme.

**Ouvriers :**
- Un ouvrier ne peut avoir qu'une seule affectation active à la fois.
- Les anciennes affectations sont conservées.
- Chaque changement de tarif crée un nouvel enregistrement historique.

**Présences :**
- Un ouvrier ne peut avoir qu'une seule présence par date.
- Les horaires appliqués sont conservés pour préserver l'historique.

**Salaires :**
- Un salaire est calculé pour une période donnée.
- Un salaire peut recevoir plusieurs paiements.
- Le reste à payer est calculé automatiquement.

**Expéditions :**
- Une expédition doit être confirmée par le comptable.
- La quantité physique et la quantité facturée sont séparées.
- Le montant total est calculé automatiquement.
- Le total des paiements ne peut pas dépasser le montant de l'expédition.

**Dépenses :**
- Une dépense peut être créée directement ou provenir d'une demande acceptée.
- Une demande ne peut être liée qu'à une seule dépense.

---

# 5. Implémentation SQL

L'implémentation SQL consiste à créer la base de données ORANGIA dans PostgreSQL à partir du MPD.

## 5.1 Création de la base de données

```sql
CREATE DATABASE orangia;
```

## 5.2 Création des tables

Les tables sont créées dans l'ordre suivant afin de respecter les relations entre les clés étrangères :

1. ROLE
2. FERME
3. UTILISATEUR
4. PARCELLE
5. OUVRIER
6. AFFECTATION_OUVRIER
7. TARIF_OUVRIER
8. FICHE_PRESENCE
9. PRESENCE
10. DEMANDE_CORRECTION
11. SALAIRE
12. PAIEMENT_SALAIRE
13. RECOLTE
14. CLIENT
15. EXPEDITION
16. PAIEMENT_EXPEDITION
17. DEMANDE_DEPENSE
18. DEPENSE
19. DOCUMENT
20. NOTIFICATION
21. JOURNAL_ACTION
22. PARAMETRE_SYSTEME

## 5.3 Insertion des rôles

```sql
INSERT INTO ROLE (nom_role, description)
VALUES
('ADMINISTRATEUR', 'Gestion générale du système'),
('RESPONSABLE', 'Gestion des activités de la ferme'),
('RH', 'Gestion du personnel et des salaires'),
('COMPTABLE', 'Gestion financière et comptabilité');
```

## 5.4 Création de la vue du stock

La vue `V_STOCK_FERME` permet de calculer automatiquement le stock disponible de chaque ferme.

```sql
CREATE VIEW V_STOCK_FERME AS
SELECT
    f.id_ferme,
    f.nom_ferme,

    COALESCE(r.total_recoltes, 0)
    - COALESCE(e.total_expeditions, 0)
    AS stock_disponible

FROM FERME f

LEFT JOIN (
    SELECT
        p.id_ferme,
        SUM(r.quantite_kg) AS total_recoltes
    FROM RECOLTE r
    JOIN PARCELLE p
        ON r.id_parcelle = p.id_parcelle
    WHERE r.statut = 'ENREGISTREE'
    GROUP BY p.id_ferme
) r ON f.id_ferme = r.id_ferme

LEFT JOIN (
    SELECT
        id_ferme,
        SUM(quantite_kg) AS total_expeditions
    FROM EXPEDITION
    WHERE statut_expedition = 'CONFIRMEE'
    GROUP BY id_ferme
) e ON f.id_ferme = e.id_ferme;
```

## 5.5 Vérification de la base de données

Pour afficher les tables créées :

```sql
SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public'
AND table_type = 'BASE TABLE'
ORDER BY table_name;
```

Pour vérifier les rôles :

```sql
SELECT * FROM ROLE;
```

Pour consulter le stock :

```sql
SELECT * FROM V_STOCK_FERME;
```

## 5.6 Conclusion

L'implémentation SQL permet de créer et de vérifier la base de données ORANGIA avec PostgreSQL.

Elle représente la dernière étape du Database Design et prépare l'intégration avec l'application Java.

---

# 6. Conclusion générale

Le Database Design d'ORANGIA suit cinq étapes :

1. Analyse des besoins.
2. Modèle Conceptuel de Données (MCD).
3. Modèle Logique de Données (MLD).
4. Modèle Physique de Données (MPD).
5. Implémentation SQL.

Cette conception permet d'organiser les informations de manière claire, d'éviter les répétitions inutiles et de garantir les relations entre les différentes tables.

Elle constitue la référence pour le développement de la base de données PostgreSQL et son intégration dans l'application ORANGIA selon l'architecture MVC — DAO — Service.