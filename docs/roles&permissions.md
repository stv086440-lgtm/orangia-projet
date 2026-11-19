# ORANGIA — RÔLES & PERMISSIONS

**Version : 3.0 — Version finale**
**Statut : Référence officielle**
**Application : ORANGIA**
**Architecture : Java 21 / JavaFX / PostgreSQL**

---

# 1. Objectif

Ce document définit les rôles, responsabilités, autorisations et restrictions des utilisateurs de l'application ORANGIA.

Il constitue la référence officielle pour l'implémentation des contrôles d'accès dans :

- Controller
- Service
- DAO
- Interface utilisateur

---

# 2. Principes fondamentaux

- L'application comporte exactement quatre rôles.
- Chaque utilisateur possède un seul rôle.
- Les ouvriers ne possèdent aucun compte utilisateur.
- Un Responsable est affecté à une seule ferme.
- Une ferme possède au maximum un Responsable actif.
- RH et Comptable disposent d'un accès global.
- Les permissions sont définies statiquement dans le code Java.
- Les tables PERMISSION et ROLE_PERMISSION ne sont pas utilisées.
- Les données historiques sont conservées.
- Les contrôles d'accès doivent être appliqués dans les Services et DAO.

---

# 3. Les rôles

## 3.1 Administrateur

### Mission

Gérer le système, les utilisateurs, les fermes et les paramètres globaux.

### Autorisations

- Créer les comptes utilisateurs.
- Modifier les comptes.
- Activer ou désactiver les comptes.
- Affecter les rôles.
- Affecter les responsables aux fermes.
- Gérer les fermes.
- Gérer les parcelles.
- Configurer les paramètres système.
- Consulter les statistiques globales.
- Consulter le journal d'audit.
- Consulter les historiques.
- Effectuer les sauvegardes et restaurations.

### Restrictions

- Ne remplace pas le Responsable.
- Ne remplace pas RH.
- Ne remplace pas le Comptable.
- Ne modifie pas directement les opérations métier pour contourner les règles de validation.

---

## 3.2 Responsable de Ferme

### Mission

Gérer les opérations quotidiennes de la ferme qui lui est attribuée.

### Autorisations

#### Récoltes

- Ajouter une récolte.
- Modifier une récolte selon les règles métier.
- Annuler une récolte avec motif.
- Consulter l'historique des récoltes.

#### Expéditions

- Créer une expédition.
- Modifier les informations opérationnelles autorisées.
- Consulter les expéditions.
- Transmettre l'expédition au Comptable.
- Demander une révision ou une annulation selon le statut.

#### Clients

- Ajouter un client.
- Modifier les informations générales.
- Désactiver un client.
- Consulter les clients.

#### Présences

- Préparer les fiches de présence.
- Enregistrer les absences.
- Transmettre les fiches à RH.
- Créer une demande de correction.
- Consulter les réponses RH.

#### Demandes de dépenses

- Créer une demande.
- Consulter les demandes.
- Annuler une demande selon les règles métier.

#### Consultation

- Consulter sa ferme.
- Consulter les parcelles de sa ferme.
- Consulter le stock de sa ferme.
- Consulter les ouvriers affectés à sa ferme.
- Générer des rapports opérationnels.

### Restrictions

Le Responsable ne peut pas :

- Accéder à une autre ferme.
- Gérer les comptes utilisateurs.
- Gérer les rôles.
- Gérer les paramètres système.
- Modifier les parcelles.
- Créer une ferme.
- Modifier une ferme.
- Modifier les affectations des ouvriers.
- Valider les présences.
- Calculer les salaires.
- Payer les salaires.
- Confirmer les expéditions.
- Voir les informations financières des expéditions.
- Voir les paiements des clients.
- Enregistrer des paiements.
- Enregistrer des dépenses réelles.

---

## 3.3 Agent RH

### Mission

Gérer le personnel, les présences et les salaires pour toutes les fermes.

### Autorisations

#### Personnel

- Créer un ouvrier.
- Modifier un ouvrier.
- Désactiver un ouvrier.
- Consulter les ouvriers.
- Gérer les affectations.
- Effectuer les transferts.

#### Tarifs

- Créer un tarif.
- Modifier les tarifs par historisation.
- Consulter l'historique des tarifs.

#### Présences

- Consulter les fiches.
- Valider les présences.
- Demander des corrections.
- Traiter les demandes de correction.
- Accepter ou refuser les justificatifs.

#### Salaires

- Calculer les salaires.
- Générer les salaires.
- Consulter les détails de calcul.
- Enregistrer les paiements.
- Consulter l'historique des paiements.

#### Rapports

- Générer les rapports RH.
- Consulter les statistiques RH.

### Consultation

- Consulter les fermes.
- Consulter les parcelles.

### Restrictions

RH ne peut pas :

- Gérer les comptes utilisateurs.
- Attribuer les rôles.
- Modifier les paramètres système.
- Effectuer les sauvegardes.
- Créer les expéditions.
- Confirmer les expéditions.
- Gérer les dépenses.
- Gérer les paiements clients.

---

## 3.4 Comptable

### Mission

Assurer le suivi financier et comptable global.

### Autorisations

#### Expéditions

- Consulter les expéditions.
- Vérifier le stock.
- Définir le prix unitaire.
- Définir la quantité facturée.
- Confirmer une expédition.
- Refuser une expédition.
- Consulter l'historique des confirmations.

#### Paiements clients

- Enregistrer les paiements.
- Consulter les paiements.
- Consulter les créances.
- Consulter les soldes.

#### Dépenses

- Accepter une demande de dépense.
- Refuser une demande.
- Enregistrer une dépense réelle.
- Modifier une dépense selon les règles métier.
- Annuler une dépense selon les règles métier.

#### Rapports

- Générer les rapports financiers.
- Générer les bilans.
- Consulter les coûts salariaux agrégés.

#### Consultation

- Consulter les récoltes.
- Consulter les stocks.
- Consulter les fermes.
- Consulter les parcelles.

### Restrictions

Le Comptable ne peut pas :

- Gérer les comptes utilisateurs.
- Gérer les rôles.
- Modifier les fermes.
- Modifier les parcelles.
- Modifier les récoltes.
- Modifier la quantité physique d'une expédition.
- Modifier les présences.
- Modifier les affectations.
- Calculer les salaires individuels.
- Enregistrer les paiements de salaires.

---

# 4. Matrice des permissions

| Fonctionnalité | Admin | Responsable | RH | Comptable |
|---|---|---|---|---|
| Comptes utilisateurs | G | — | — | — |
| Attribution des rôles | G | — | — | — |
| Fermes | G | C | C | C |
| Parcelles | G | C | C | C |
| Ouvriers | S | C | G | — |
| Affectations | S | — | G | — |
| Tarifs ouvriers | S | — | G | — |
| Fiches de présence | S | D | T | — |
| Demandes de correction | S | D | T | — |
| Salaires | S | — | G | C (agrégé) |
| Paiements salariaux | S | — | G | C (agrégé) |
| Récoltes | S | G | — | C |
| Stock | S | C | — | C |
| Clients (général) | S | G | — | G |
| Clients (financier) | S | — | — | G |
| Expéditions opérationnelles | S | G | — | C |
| Confirmation expéditions | S | — | — | V |
| Informations financières expédition | S | — | — | G |
| Paiements clients | S | — | — | G |
| Demandes de dépenses | S | D | — | V |
| Dépenses réelles | S | — | — | G |
| Documents | S | Selon autorisation | Selon autorisation | Selon autorisation |
| Notifications | S | Personnelles | Personnelles | Personnelles |
| Journal d'audit | G | — | — | — |
| Paramètres système | G | — | — | — |
| Sauvegardes | G | — | — | — |
| Rapports opérationnels | S | G | — | C |
| Rapports RH | S | — | G | C (agrégé) |
| Rapports financiers | S | — | — | G |

### Légende

- G = Gestion complète.
- C = Consultation.
- C (agrégé) = Consultation globale sans détails individuels.
- D = Demande / Transmission.
- T = Traitement / Validation.
- V = Validation / Confirmation.
- S = Supervision en lecture seule.
- — = Aucun accès.

---

# 5. Règles de sécurité

## Séparation des données

- Le Responsable accède uniquement à sa ferme.
- RH dispose d'un accès global RH.
- Comptable dispose d'un accès global financier.
- Administrateur dispose d'une supervision globale.

## Contrôles obligatoires

Chaque opération doit vérifier :

1. L'identité de l'utilisateur.
2. Son rôle.
3. Son périmètre d'accès.
4. L'état de l'opération.
5. Les règles métier associées.

## Protection des informations financières

Les données financières des expéditions ne doivent jamais être retournées aux requêtes destinées au Responsable.

Le masquage d'un bouton JavaFX ne constitue jamais une protection de sécurité suffisante.

Les contrôles doivent être appliqués dans :

- Service
- DAO
- Controller
- Interface

## Traçabilité

Doivent être journalisés :

- Créations
- Modifications
- Annulations
- Confirmations
- Validations
- Paiements
- Transferts
- Modifications de paramètres
- Sauvegardes
- Restaurations

---

# 6. Décision finale

Ce document constitue la référence officielle des rôles et permissions de l'application ORANGIA.

Toute modification future doit être validée avant implémentation.

**Statut : FINAL**