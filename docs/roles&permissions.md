# Rôles & Permissions - Orangia

## Objectif

Ce document définit les rôles utilisateurs et leurs permissions dans l'application Orangia.

---

# 1. Administrateur

## Gestion Comptes 

Créer compte
Modifier compte
Réinitialiser mot de passe
Affecter rôle
Désactiver compte

## Description

L'administrateur possède un accès complet à l'application.

## Permissions


### Rôles

- Attribuer les rôles
- Modifier les permissions
- Consulter les rôles

### Fermes

- Ajouter une ferme
- Modifier une ferme
- Désactiver une ferme
- Assigner un responsable

### Parcelles

- Créer
- Modifier
- Désactiver
- Consulter

### Ouvriers

- Consulter
- Modifier
- Désactiver

### Récoltes

- Consulter
- Ajouter
- Modifier

### Expéditions

- Consulter
- Créer
- Modifier
- Valider
- Annuler

### Dépenses

- Consulter
- Ajouter
- Modifier

### Administration

- Consulter le journal d'audit
- Consulter toutes les notifications
- Sauvegarder la base de données
- Restaurer la base de données
- Gérer les paramètres système

---

# 2. Responsable de Ferme

## Description

Gère uniquement les fermes qui lui sont attribuées.

Créer compte ❌
Modifier compte ❌

Parcelles ✅
Récoltes ✅
Expéditions ✅

## Permissions

### Fermes

- Consulter ses fermes
- Consulter les statistiques

### Parcelles

- Créer
- Modifier
- Désactiver
- Consulter

### Ouvriers

- Consulter les ouvriers de sa ferme

### Récoltes

- Ajouter
- Modifier
- Consulter

### Expéditions

- Créer
- Modifier
- Consulter
- Annuler

### Dépenses

- Ajouter
- Modifier
- Consulter

### Rapports

- Générer les rapports de sa ferme

---

# Restrictions

- Ne peut pas voir les autres fermes.
- Ne peut pas gérer les utilisateurs.
- Ne peut pas modifier les rôles.
- Ne peut pas accéder aux paramètres système.

---

# 3. Agent RH

## Description

Responsable de la gestion du personnel.

Créer compte ❌
Modifier compte ❌
Réinitialiser mot de passe ❌

Gestion Ouvriers ✅
Gestion Présences ✅

## Permissions

### Ouvriers

- Ajouter
- Modifier
- Désactiver
- Rechercher
- Consulter

### Présences

- Enregistrer
- Modifier
- Corriger
- Consulter

### RH

- Gérer les congés
- Consulter les historiques
- Générer les rapports RH

---

# Restrictions

- Aucun accès aux revenus.
- Aucun accès aux paramètres.
- Aucun accès à la sauvegarde.
- Aucun accès aux rôles utilisateurs.

---

# 4. Comptable

## Description

Responsable du suivi financier.

Créer compte ❌
Modifier compte ❌

Finances ✅
Rapports ✅

## Permissions

### Finances

- Consulter les dépenses
- Consulter les revenus
- Consulter les paiements
- Consulter les impayés

### Rapports

- Générer les bilans
- Générer les rapports financiers
- Exporter PDF
- Exporter CSV

---

# Restrictions

- Ne peut pas modifier les utilisateurs.
- Ne peut pas modifier les rôles.
- Ne peut pas accéder aux paramètres système.
- Ne peut pas gérer les présences.

---

# Tableau Résumé

| Module       | Admin | Responsable | RH  | Comptable|
|--------------|-------|-------------|-----|----------|
| Utilisateurs | ✅    |     ❌     | ❌ |   ❌     |
| Rôles        | ✅    |     ❌     | ❌ |   ❌     |
| Fermes       | ✅    |     ✅     | ❌ |   👁️     |
| Parcelles    | ✅    |     ✅     | ❌ |   👁️     |
| Ouvriers     | ✅    |     👁️     | ✅ |   ❌     |
| Présences    | ✅    |     👁️     | ✅ |   ❌     |
| Récoltes     | ✅    |     ✅     | 👁️ |   👁️     |
| Expéditions  | ✅    |     ✅     | ❌ |   👁️     |
| Dépenses     | ✅    |     ✅     | ❌ |   ✅     |
| Rapports     | ✅    |     ✅     | ✅ |   ✅     |
| Audit        | ✅    |     ❌     | ❌ |   ❌     |
| Paramètres   | ✅    |     ❌     | ❌ |   ❌     |