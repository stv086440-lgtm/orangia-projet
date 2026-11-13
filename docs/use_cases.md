# Use Cases - Orangia

## Description

Ce document présente les cas d'utilisation du système **Orangia** selon le rôle de chaque utilisateur, conformément à l'architecture MVC, aux entités métier et à la base de données PostgreSQL.

---

# Acteurs du système

- **Administrateur** : Supervision globale, gestion des utilisateurs, des fermes et de l'audit.
- **Responsable de Ferme** : Exploitation agricole (parcelles, récoltes, expéditions, stock d'intrants et dépenses locales).
- **Agent RH** : Gestion du personnel (ouvriers, pointage des présences et paiement des salaires).
- **Comptable** : Gestion financière (dépenses, encaissements des expéditions et bilans financiers).

---

# 1. Administrateur

## Authentification
- Se connecter
- Se déconnecter
- Modifier son mot de passe

## Gestion des Utilisateurs et Comptes
- Créer un compte utilisateur
- Attribuer un rôle (ADMINISTRATEUR, RESPONSABLE_FERME, AGENT_RH, COMPTABLE)
- Modifier un compte utilisateur
- Réinitialiser un mot de passe
- Désactiver un compte utilisateur
- Réactiver un compte utilisateur
- Affecter un utilisateur à une ou plusieurs fermes (`utilisateur_ferme`)
- Consulter la liste des utilisateurs et leurs affectations
- Rechercher et filtrer les utilisateurs

## Gestion des Fermes
- Ajouter une ferme
- Modifier une ferme
- Activer / Désactiver une ferme
- Consulter toutes les fermes et leurs parcelles

## Supervision Globale
- Consulter le tableau de bord consolidé (KPIs globaux)
- Consulter toutes les parcelles
- Consulter tous les ouvriers
- Consulter toutes les récoltes
- Consulter toutes les expéditions
- Consulter toutes les dépenses et recettes
- Consulter tous les paiements de salaires

## Audit et Administration
- Consulter le journal d'activité (`journal_action`)
- Filtrer le journal d'activité (par date, utilisateur, action)
- Consulter les notifications système
- Sauvegarder la base de données (Export SQL)
- Restaurer la base de données

## Reporting
- Générer tous les rapports d'exploitation
- Exporter les rapports en PDF
- Exporter les données en CSV

---

# 2. Responsable de Ferme

## Authentification
- Se connecter
- Se déconnecter
- Modifier son mot de passe

## Tableau de Bord Ferme
- Consulter ses fermes affectées
- Consulter les statistiques de production de ses fermes
- Visualiser les alertes de stock critique

## Gestion des Parcelles
- Ajouter une parcelle (superficie, variété, nb arbres, année)
- Modifier une parcelle
- Activer / Désactiver une parcelle
- Consulter les parcelles de ses fermes
- Consulter l'historique de production d'une parcelle

## Gestion des Récoltes
- Enregistrer une récolte (parcelle, date, quantité)
- Modifier une récolte
- Consulter l'historique des récoltes
- Rechercher et filtrer les récoltes (par variété, parcelle, période)

## Gestion des Clients
- Consulter les clients (stations de conditionnement)
- Ajouter un client
- Modifier la fiche d'un client
- Rechercher un client

## Gestion des Expéditions
- Créer une expédition
- Associer des récoltes à une expédition (`expedition_recolte`)
- Vérifier la disponibilité des quantités à expédier
- Enregistrer le prix de vente convenu
- Modifier une expédition (avant expédition)
- Consulter l'historique des expéditions de sa ferme

## Gestion Simplifiée du Stock (Intrants & Matériel)
- Ajouter un produit de stock (désignation, unité, seuil d'alerte)
- Modifier un produit de stock
- Consulter les quantités disponibles
- Enregistrer une entrée de stock (livraison)
- Enregistrer une sortie de stock (consommation)
- Consulter l'historique des mouvements de stock
- Consulter les alertes de stock faible

## Gestion des Dépenses de Ferme
- Ajouter une dépense d'exploitation terrain
- Modifier une dépense de sa ferme
- Consulter les dépenses de ses fermes

## Consultation RH & Notifications
- Consulter la liste des ouvriers affectés à ses fermes
- Consulter les présences du jour
- Consulter les notifications
- Marquer une notification comme lue

## Reporting Ferme
- Générer le rapport de production et de récolte (PDF)
- Générer le récapitulatif des expéditions (PDF)

---

# 3. Agent RH

## Authentification
- Se connecter
- Se déconnecter
- Modifier son mot de passe

## Tableau de Bord RH
- Consulter les statistiques RH (effectif actif, taux de présence)
- Consulter la masse salariale prévisionnelle du mois

## Gestion des Ouvriers
- Ajouter un ouvrier (CIN, nom, tarif journalier, ferme)
- Modifier les informations d'un ouvrier
- Désactiver un ouvrier
- Réactiver un ouvrier
- Consulter la fiche détaillée d'un ouvrier
- Rechercher et filtrer les ouvriers (par ferme, par statut)

## Gestion des Présences
- Enregistrer la feuille de présence quotidienne (Présent, Absent, Demi-journée)
- Modifier une présence (rectification d'erreur de saisie)
- Consulter l'historique des présences
- Filtrer les présences par date, ferme ou ouvrier
- Calculer le cumul des jours travaillés par période

## Gestion des Paiements des Salaires
- Consulter les ouvriers éligibles au paiement
- Calculer automatiquement le salaire (Jours travaillés × Tarif journalier)
- Enregistrer un paiement de salaire (date, montant, mode de règlement)
- Consulter l'historique des paiements
- Générer et imprimer un reçu de paiement (PDF)

## Rapports RH
- Générer la feuille d'émargement mensuelle des présences (PDF)
- Générer le rapport récapitulatif des salaires payés (PDF)
- Exporter les données RH en CSV

---

# 4. Comptable

## Authentification
- Se connecter
- Se déconnecter
- Modifier son mot de passe

## Tableau de Bord Financier
- Consulter les indicateurs financiers en temps réel (Recettes, Dépenses, Salaires)
- Consulter le résultat net d'exploitation
- Consulter les graphiques comparatifs mensuels

## Gestion des Dépenses
- Enregistrer une dépense
- Catégoriser la dépense (engrais, matériel, carburant, transport, entretien, divers)
- Associer la dépense à une ferme ou au compte général
- Modifier une dépense
- Supprimer une dépense (avec traçabilité)
- Consulter l'historique des dépenses avec filtres multicritères

## Suivi des Recettes et Expéditions
- Consulter les expéditions réalisées
- Consulter le chiffre d'affaires généré
- Mettre à jour le statut de paiement des expéditions (Non payé, Partiel, Payé)
- Suivre les créances et encours clients

## Audit de la Masse Salariale
- Consulter les paiements de salaires enregistrés par les RH
- Intégrer les charges salariales au bilan comptable

## Analyse et Rentabilité
- Analyser la rentabilité par ferme (Recettes vs Dépenses & Salaires)
- Consulter l'état du stock (évaluation de la valeur immobilisée)

## Rapports Financiers
- Générer le bilan financier périodique (PDF)
- Générer le rapport détaillé des dépenses par catégorie (PDF)
- Générer le journal des ventes et expéditions (PDF)
- Exporter les données financières en CSV

---

# Cas d'utilisation communs

Tous les utilisateurs authentifiés peuvent :
- Se connecter
- Se déconnecter
- Modifier leur propre mot de passe
- Consulter leur profil et rôle
- Recevoir et consulter les notifications qui les concernent
- Consulter les données autorisées selon leur rôle et affectation

---

# Restrictions

## Administrateur
- Accès complet et sans restriction à tout le système.

## Responsable de Ferme
- Accès strictement restreint aux fermes qui lui sont affectées.
- Aucun accès à la gestion des comptes utilisateurs.
- Aucun accès aux modules de paiement des salaires.

## Agent RH
- Accès limité aux ouvriers, présences et salaires des fermes autorisées.
- Aucun accès aux modules financiers (dépenses, recettes, bilans).
- Aucun accès aux récoltes et expéditions.

## Comptable
- Accès centré sur les flux financiers (dépenses, recettes, audit salaires).
- Aucun accès à la gestion des comptes utilisateurs.
- Aucun accès à la saisie des présences ni aux opérations techniques de récolte.

---

# Diagrammes UML à réaliser

À partir de cette spécification, les diagrammes de cas d'utilisation suivants doivent être modélisés :

1. **Diagramme Global du Système Orangia** (Vue d'ensemble avec les 4 acteurs).
2. **Diagramme du Rôle Administrateur** (Focus Comptes, Fermes, Audit).
3. **Diagramme du Rôle Responsable de Ferme** (Focus Parcelles, Récoltes, Expéditions, Stock).
4. **Diagramme du Rôle Agent RH** (Focus Ouvriers, Présences, Salaires).
5. **Diagramme du Rôle Comptable** (Focus Dépenses, Encaissements, Bilans).