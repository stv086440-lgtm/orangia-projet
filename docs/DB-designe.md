# Database Design - Orangia

## Objectif

Cette base de données permet la gestion complète des fermes d'oranges.

Le système permet de gérer :

- Les comptes utilisateurs
- Les fermes
- Les parcelles
- Les ouvriers
- Les présences
- Les récoltes
- Les expéditions
- Les dépenses
- Les notifications
- Les journaux d'activité

---

# Utilisateurs du Système

Seuls les utilisateurs suivants peuvent accéder à l'application :

- Administrateur
- Responsable de Ferme
- Agent RH
- Comptable

Les ouvriers ne disposent pas d'un compte applicatif.

---

# Entités Principales

## Role

Représente un rôle utilisateur.

Attributs :

- id_role
- nom_role
- description

Exemples :

- ADMINISTRATEUR
- RESPONSABLE_FERME
- AGENT_RH
- COMPTABLE

---

## Utilisateur

Représente un compte de connexion.

Attributs :

- id_utilisateur
- identifiant
- mot_de_passe
- nom
- prenom
- actif
- date_creation
- id_role

Règles :

- Un utilisateur possède un seul rôle.
- L'administrateur crée les comptes.
- Les autres utilisateurs disposent uniquement d'un accès de connexion.

---

## Ferme

Représente une exploitation agricole.

Attributs :

- id_ferme
- code_ferme
- nom
- localisation
- superficie
- description
- statut
- date_creation

Relations :

- Une ferme possède plusieurs parcelles.
- Une ferme possède plusieurs ouvriers.
- Une ferme possède plusieurs dépenses.
- Une ferme possède plusieurs expéditions.

---

## Parcelle

Représente une parcelle agricole.

Attributs :

- id_parcelle
- code_parcelle
- nom
- superficie
- variete_orange
- nombre_arbres
- annee_plantation
- statut
- id_ferme

Relations :

- Une parcelle appartient à une seule ferme.
- Une parcelle possède plusieurs récoltes.

---

## Ouvrier

Représente un ouvrier agricole.

Attributs :

- id_ouvrier
- code_ouvrier
- nom
- prenom
- telephone
- adresse
- date_embauche
- salaire_journalier
- statut
- id_ferme

Règles :

- Les ouvriers ne disposent pas d'un compte utilisateur.
- Les ouvriers sont gérés par l'Agent RH.

---

## Presence

Représente le pointage quotidien.

Attributs :

- id_presence
- date_presence
- statut
- heure_arrivee
- heure_depart
- remarque
- id_ouvrier

Statuts :

- PRESENT
- ABSENT
- RETARD
- CONGE

---

## Recolte

Représente une opération de récolte.

Attributs :

- id_recolte
- reference_recolte
- date_recolte
- quantite_kg
- qualite
- remarque
- id_parcelle

Relations :

- Une récolte appartient à une parcelle.

---

## Expedition

Représente une expédition d'oranges.

Attributs :

- id_expedition
- reference_expedition
- date_expedition
- destinataire
- quantite_kg
- prix