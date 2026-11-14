# Architecture Globale d'ORANGIA

# 1. Présentation du projet

Orangia est une application desktop de gestion d'une exploitation agricole spécialisée dans la production des oranges.

L'application permet de centraliser les principales opérations de la ferme :

- Gestion des fermes et des parcelles.
- Gestion des ouvriers et des présences.
- Gestion des récoltes.
- Gestion des clients et des expéditions.
- Gestion des dépenses.
- Gestion simplifiée du stock.
- Gestion des paiements des salaires.
- Tableau de bord et rapports PDF.

**Objectif :** développer une application simple à utiliser, professionnelle, fonctionnelle et facile à maintenir.

---

# 2. Technologies utilisées

| Technologie | Utilisation |
|------------|------------|
| Java | Langage de programmation |
| JavaFX | Interface graphique |
| FXML | Structure des interfaces |
| CSS | Design et styles |
| Maven | Gestion du projet et des dépendances |
| PostgreSQL | Base de données |
| JDBC | Communication avec la base de données |
| JUnit | Tests unitaires |
| Git / GitHub | Gestion du code et travail en équipe |

---

# 3. Architecture générale

L'application utilise une architecture MVC avec une séparation simple des responsabilités.

```text
              UTILISATEUR
                   |
                   v
          VIEW (JavaFX / FXML)
                   |
                   v
              CONTROLLER
                   |
                   v
                SERVICE
                   |
                   v
                  DAO
                   |
                   v
              PostgreSQL
```

Le Model représente les objets métier utilisés dans les différentes parties de l'application.

---

## 3.1 Model

Contient les classes représentant les principales entités :

- Utilisateur
- Role
- Ferme
- Parcelle
- Ouvrier
- Presence
- Recolte
- Client
- Expedition
- Depense
- ProduitStock
- MouvementStock
- PaiementSalaire
- Notification
- JournalAction

---

## 3.2 View

Les interfaces graphiques sont réalisées avec JavaFX et FXML.

Chaque écran possède son fichier FXML et son fichier CSS lorsque nécessaire.

### Exemples

- LoginView.fxml
- DashboardView.fxml
- FermesView.fxml
- ParcellesView.fxml
- OuvriersView.fxml
- PresencesView.fxml
- RecoltesView.fxml
- ExpeditionsView.fxml
- DepensesView.fxml
- StockView.fxml
- PaiementsView.fxml
- RapportsView.fxml

---

## 3.3 Controller

Le Controller reçoit les actions de l'utilisateur et communique avec les Services.

### Exemples

- LoginController
- DashboardController
- FermeController
- ParcelleController
- OuvrierController
- PresenceController
- RecolteController
- ExpeditionController
- DepenseController
- StockController
- PaiementController
- RapportController

---

## 3.4 Service

Les Services contiennent les règles métier et les vérifications nécessaires.

### Exemples

- AuthService
- FermeService
- OuvrierService
- PresenceService
- RecolteService
- ExpeditionService
- DepenseService
- StockService
- PaiementService
- RapportService

Le Service vérifie les données avant de demander au DAO d'effectuer les opérations nécessaires.

---

## 3.5 DAO

Les DAO assurent les opérations de communication avec PostgreSQL.

Deux parties simples :

- `dao/interfaces` : interfaces des DAO.
- `dao/impl` : implémentations SQL des DAO.

### Exemple

```text
dao/

├── interfaces/
│   ├── UtilisateurDAO.java
│   ├── FermeDAO.java
│   ├── OuvrierDAO.java
│   ├── RecolteDAO.java
│   └── ...

└── impl/
    ├── UtilisateurDAOImpl.java
    ├── FermeDAOImpl.java
    ├── OuvrierDAOImpl.java
    ├── RecolteDAOImpl.java
    └── ...
```

Il n'est pas nécessaire de créer une architecture DAO générique ou plusieurs couches supplémentaires.

---

# 4. Gestion des utilisateurs et des rôles

L'application utilise exactement quatre rôles :

| Rôle | Responsabilités |
|--------|--------|
| ADMINISTRATEUR | Gestion globale de l'application |
| RESPONSABLE_FERME | Gestion des fermes et des opérations agricoles |
| AGENT_RH | Gestion des ouvriers, présences et paiements des salaires |
| COMPTABLE | Gestion des dépenses, recettes et rapports financiers |

Le rôle est représenté dans Java par un Enum `Role`.

Les utilisateurs peuvent être associés à plusieurs fermes.

La relation entre Utilisateur et Ferme est donc **Many-to-Many**, à travers la table :

```text
utilisateur_ferme
```

### Règles principales

- L'administrateur possède un accès global.
- Le responsable de ferme travaille uniquement sur les fermes qui lui sont affectées.
- L'agent RH travaille sur les données RH des fermes autorisées.
- Le comptable accède aux informations financières selon son rôle.
- Les contrôles d'accès sont réalisés dans les Services.

Il n'est pas nécessaire de créer un système dynamique de permissions.

### La connexion utilise

- `SessionManager` : conserve l'utilisateur connecté.
- `PasswordHasher` : permet de vérifier les mots de passe de manière sécurisée.

---

# 5. Modules fonctionnels

## 5.1 Authentification

### Fonctionnalités

- Connexion.
- Déconnexion.
- Vérification des identifiants.
- Redirection vers l'interface adaptée au rôle.

---

## 5.2 Gestion des fermes et parcelles

### Ferme

- Ajouter une ferme.
- Modifier les informations.
- Consulter les fermes.
- Activer ou désactiver une ferme.

### Parcelle

- Ajouter une parcelle.
- Modifier ses informations.
- Associer une parcelle à une ferme.
- Consulter la superficie, la variété d'orange, le nombre d'arbres et l'année de plantation.

---

## 5.3 Gestion des ouvriers et présences

### Ouvriers

- Ajouter un ouvrier.
- Modifier ses informations.
- Consulter la liste des ouvriers.
- Activer ou désactiver un ouvrier.

### Présences

- Enregistrer une présence.
- Enregistrer une absence.
- Consulter les présences par date.
- Filtrer par ouvrier ou par ferme.

L'application conserve un historique simple des présences.

---

## 5.4 Gestion des récoltes

### Fonctionnalités

- Enregistrer une récolte.
- Associer la récolte à une parcelle.
- Enregistrer la date.
- Enregistrer la quantité récoltée.
- Consulter l'historique des récoltes.
- Consulter les quantités par parcelle et par période.

---

## 5.5 Gestion des clients et expéditions

Les clients représentent principalement les stations de conditionnement qui reçoivent les oranges.

### Clients

- Ajouter un client.
- Modifier ses informations.
- Consulter la liste des clients.

### Expéditions

- Créer une expédition.
- Sélectionner un client.
- Enregistrer la date et les quantités expédiées.
- Associer les quantités expédiées aux récoltes concernées.
- Enregistrer le prix ou la recette.
- Suivre le statut de paiement.
- Consulter l'historique des expéditions.

Une expédition représente la sortie commerciale des oranges vers un client.

Il n'est donc pas nécessaire de créer un module Vente séparé si ces informations sont déjà gérées par l'expédition.

---

## 5.6 Gestion des dépenses

### Fonctionnalités

- Ajouter une dépense.
- Modifier une dépense.
- Choisir une catégorie.
- Associer la dépense à une ferme.
- Enregistrer le montant et la date.
- Consulter l'historique.
- Filtrer par catégorie et par période.

### Exemples de catégories

- Matériel agricole.
- Engrais.
- Transport.
- Entretien.
- Main-d'œuvre.
- Autres dépenses.

---

## 5.7 Gestion simplifiée du stock

Le stock reste un module léger.

### Fonctionnalités

- Ajouter un produit.
- Modifier un produit.
- Consulter les quantités disponibles.
- Enregistrer une entrée.
- Enregistrer une sortie.
- Afficher une alerte lorsque la quantité est inférieure au seuil minimum.

### Exemples

- Engrais.
- Produits de traitement.
- Matériel agricole.
- Carburant.

Il ne s'agit pas d'un système complet de gestion commerciale ou d'un ERP.

Le stock de matériel et de fournitures reste distinct des quantités d'oranges récoltées.

---

## 5.8 Paiements des salaires

### Gestion simplifiée

- Consulter les ouvriers.
- Enregistrer un paiement.
- Enregistrer la date et le montant.
- Consulter l'historique des paiements.
- Générer un reçu simple.

Il n'est pas nécessaire de développer un système complexe de paie, de calcul fiscal ou de gestion complète des bulletins de salaire.

---

## 5.9 Dashboard

Le Dashboard est l'un des éléments importants pour rendre Orangia professionnelle.

### Indicateurs simples

- Nombre de fermes.
- Nombre de parcelles.
- Nombre d'ouvriers.
- Total des récoltes.
- Total des expéditions.
- Total des dépenses.
- Recettes enregistrées.
- Stock faible.

### Visualisations possibles

- Évolution des récoltes par mois.
- Comparaison des dépenses et recettes.
- Répartition des récoltes par parcelle.

Les informations affichées dépendent du rôle de l'utilisateur.

---

## 5.10 Rapports PDF

### Fonctionnalités

- Rapport des récoltes.
- Rapport des dépenses.
- Rapport des présences.
- Rapport des expéditions.
- Rapport financier simplifié.

Les rapports peuvent être filtrés par période et par ferme lorsque cela est pertinent.

# 6. Base de données

La base de données utilisée est PostgreSQL.

Les principales tables fonctionnelles sont :

| Domaine             | Tables   |
|---------------------|----------|
| Utilisateurs | role, utilisateur, utilisateur_ferme |
| Exploitation | ferme, parcelle |
| Ressources humaines | ouvrier, presence, paiement_salaire |
| Production | recolte |
| Commercial | client, expedition, expedition_recolte |
| Finances | depense |
| Stock | produit_stock, mouvement_stock |
| Suivi | notification, journal_action |

Les tables doivent respecter les relations et contraintes nécessaires.

## Règles importantes

- Un utilisateur peut être affecté à plusieurs fermes.
- Une ferme peut contenir plusieurs parcelles.
- Une parcelle peut avoir plusieurs récoltes.
- Une expédition peut concerner plusieurs récoltes.
- `expedition_recolte` permet de conserver les quantités prélevées sur chaque récolte.
- Une expédition est associée à un client.
- Un mouvement de stock en sortie ne doit pas dépasser la quantité disponible.
- Les quantités expédiées ne doivent pas dépasser les quantités disponibles dans les récoltes concernées.

> **Important :**
>
> La base PostgreSQL existante doit être conservée.
>
> Toute modification nécessaire sera faite progressivement après comparaison avec le modèle actuel.
>
> Aucune suppression ou recréation globale des tables sans validation et sauvegarde.

---

# 7. Connexion à la base de données

Une classe simple :

```java
DatabaseConnection.java
```

Elle permet d'obtenir une connexion JDBC à PostgreSQL.

## Principes

- Éviter une connexion globale partagée pendant toute la durée de l'application.
- Ouvrir et fermer les connexions correctement.
- Utiliser une transaction lorsqu'une opération concerne plusieurs écritures liées.
- Ne jamais bloquer le thread JavaFX avec une opération SQL longue.

La configuration réelle de la base de données ne doit pas être publiée sur GitHub.

---

# 8. Notifications et journal d'activité

## Notifications

Notifications simples pour :

- Stock faible.
- Nouvelles opérations importantes.
- Alertes utiles à l'utilisateur.

## Journal d'activité

Conserve les actions importantes :

- Création.
- Modification.
- Suppression logique lorsque nécessaire.
- Connexion et déconnexion si utile.

Le journal d'activité est différent des logs techniques.

---

# 9. Organisation du projet Maven

## Structure recommandée

```text
orangia/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/orangia/
│   │   │       ├── Main.java
│   │   │       │
│   │   │       ├── model/
│   │   │       ├── controller/
│   │   │       │
│   │   │       ├── service/
│   │   │       │
│   │   │       ├── dao/
│   │   │       │   ├── interfaces/       
│   │   │       │   └── impl/
│   │   │       │
│   │   │       ├── database/
│   │   │       ├── security/
│   │   │       ├── validation/
│   │   │       ├── exception/
│   │   │       └── util/
│   │   │
│   │   └── resources/
│   │       ├── fxml/
│   │       ├── css/
│   │       ├── images/
│   │       ├── icons/
│   │       └── reports/
│   │
│   └── test/
│       ├── java/
│       └── resources/
│
├── database/
│   ├── migrations/
│   ├── scripts/
│   └── backups/
│
├── docs/
│
├── pom.xml
├── .gitignore
└── README.md
```

Les dossiers peuvent être créés progressivement.

Il ne faut pas créer toutes les classes vides simplement pour remplir l'arborescence.

---
### ----<<<<<<  Structure recommandée Globale   >>>>>>----- ###
orangia/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/orangia/
│   │   │       │
│   │   │       ├── Main.java
│   │   │       │
│   │   │       ├── model/
│   │   │       │   ├── Utilisateur.java
│   │   │       │   ├── Role.java
│   │   │       │   │
│   │   │       │   ├── Ferme.java
│   │   │       │   ├── Parcelle.java
│   │   │       │   │
│   │   │       │   ├── Ouvrier.java
│   │   │       │   ├── Presence.java
│   │   │       │   ├── PaiementSalaire.java
│   │   │       │   │
│   │   │       │   ├── Recolte.java
│   │   │       │   ├── Client.java
│   │   │       │   ├── Expedition.java
│   │   │       │   │
│   │   │       │   ├── Depense.java
│   │   │       │   │
│   │   │       │   ├── ProduitStock.java
│   │   │       │   ├── MouvementStock.java
│   │   │       │   │
│   │   │       │   ├── Notification.java
│   │   │       │   └── JournalAction.java
│   │   │       │
│   │   │       ├── dao/
│   │   │       │   ├── interfaces/
│   │   │       │   │   ├── UtilisateurDAO.java
│   │   │       │   │   │
│   │   │       │   │   ├── FermeDAO.java
│   │   │       │   │   ├── ParcelleDAO.java
│   │   │       │   │   │
│   │   │       │   │   ├── OuvrierDAO.java
│   │   │       │   │   ├── PresenceDAO.java
│   │   │       │   │   ├── PaiementSalaireDAO.java
│   │   │       │   │   │
│   │   │       │   │   ├── RecolteDAO.java
│   │   │       │   │   ├── ClientDAO.java
│   │   │       │   │   ├── ExpeditionDAO.java
│   │   │       │   │   │
│   │   │       │   │   ├── DepenseDAO.java
│   │   │       │   │   │
│   │   │       │   │   ├── ProduitStockDAO.java
│   │   │       │   │   ├── MouvementStockDAO.java
│   │   │       │   │   │
│   │   │       │   │   ├── NotificationDAO.java
│   │   │       │   │   └── JournalActionDAO.java
│   │   │       │   │
│   │   │       │   └── impl/
│   │   │       │       ├── UtilisateurDAOImpl.java
│   │   │       │       │
│   │   │       │       ├── FermeDAOImpl.java
│   │   │       │       ├── ParcelleDAOImpl.java
│   │   │       │       │
│   │   │       │       ├── OuvrierDAOImpl.java
│   │   │       │       ├── PresenceDAOImpl.java
│   │   │       │       ├── PaiementSalaireDAOImpl.java
│   │   │       │       │
│   │   │       │       ├── RecolteDAOImpl.java
│   │   │       │       ├── ClientDAOImpl.java
│   │   │       │       ├── ExpeditionDAOImpl.java
│   │   │       │       │
│   │   │       │       ├── DepenseDAOImpl.java
│   │   │       │       │
│   │   │       │       ├── ProduitStockDAOImpl.java
│   │   │       │       ├── MouvementStockDAOImpl.java
│   │   │       │       │
│   │   │       │       ├── NotificationDAOImpl.java
│   │   │       │       └── JournalActionDAOImpl.java
│   │   │       │
│   │   │       ├── service/
│   │   │       │   ├── AuthService.java
│   │   │       │   │
│   │   │       │   ├── FermeService.java
│   │   │       │   ├── ParcelleService.java
│   │   │       │   │
│   │   │       │   ├── OuvrierService.java
│   │   │       │   ├── PresenceService.java
│   │   │       │   ├── PaiementService.java
│   │   │       │   │
│   │   │       │   ├── RecolteService.java
│   │   │       │   ├── ClientService.java
│   │   │       │   ├── ExpeditionService.java
│   │   │       │   │
│   │   │       │   ├── DepenseService.java
│   │   │       │   │
│   │   │       │   ├── StockService.java
│   │   │       │   │
│   │   │       │   ├── DashboardService.java
│   │   │       │   ├── RapportService.java
│   │   │       │   └── NotificationService.java
│   │   │       │
│   │   │       ├── controller/
│   │   │       │   ├── LoginController.java
│   │   │       │   ├── DashboardController.java
│   │   │       │   │
│   │   │       │   ├── FermeController.java
│   │   │       │   ├── ParcelleController.java
│   │   │       │   │
│   │   │       │   ├── OuvrierController.java
│   │   │       │   ├── PresenceController.java
│   │   │       │   ├── PaiementController.java
│   │   │       │   │
│   │   │       │   ├── RecolteController.java
│   │   │       │   ├── ClientController.java
│   │   │       │   ├── ExpeditionController.java
│   │   │       │   │
│   │   │       │   ├── DepenseController.java
│   │   │       │   │
│   │   │       │   ├── StockController.java
│   │   │       │   │
│   │   │       │   └── RapportController.java
│   │   │       │
│   │   │       ├── database/
│   │   │       │   └── DatabaseConnection.java
│   │   │       │
│   │   │       ├── security/
│   │   │       │   ├── SessionManager.java
│   │   │       │   └── PasswordHasher.java
│   │   │       │
│   │   │       ├── validation/
│   │   │       │   ├── FermeValidator.java
│   │   │       │   ├── OuvrierValidator.java
│   │   │       │   ├── RecolteValidator.java
│   │   │       │   └── ExpeditionValidator.java
│   │   │       │
│   │   │       ├── exception/
│   │   │       │   ├── BusinessException.java
│   │   │       │   ├── AuthenticationException.java
│   │   │       │   ├── AuthorizationException.java
│   │   │       │   ├── DatabaseException.java
│   │   │       │   └── ResourceNotFoundException.java
│   │   │       │
│   │   │       └── util/
│   │   │           ├── AlertUtil.java
│   │   │           ├── DateUtil.java
│   │   │           └── ReportUtil.java
│   │   │
│   │   └── resources/
│   │       ├── fxml/
│   │       │   ├── LoginView.fxml
│   │       │   ├── DashboardView.fxml
│   │       │   │
│   │       │   ├── FermesView.fxml
│   │       │   ├── ParcellesView.fxml
│   │       │   │
│   │       │   ├── OuvriersView.fxml
│   │       │   ├── PresencesView.fxml
│   │       │   ├── PaiementsView.fxml
│   │       │   │
│   │       │   ├── RecoltesView.fxml
│   │       │   ├── ClientsView.fxml
│   │       │   ├── ExpeditionsView.fxml
│   │       │   │
│   │       │   ├── DepensesView.fxml
│   │       │   │
│   │       │   ├── StockView.fxml
│   │       │   │
│   │       │   └── RapportsView.fxml
│   │       │
│   │       ├── css/
│   │       │   └── style.css
│   │       │
│   │       ├── images/
│   │       ├── icons/
│   │       └── reports/
│   │
│   └── test/
│       ├── java/
│       │   └── com/orangia/
│       │       ├── dao/
│       │       │   ├── UtilisateurDAOTest.java
│       │       │   ├── FermeDAOTest.java
│       │       │   ├── OuvrierDAOTest.java
│       │       │   └── RecolteDAOTest.java
│       │       └── service/
│       │           ├── AuthServiceTest.java
│       │           ├── ExpeditionServiceTest.java
│       │           └── StockServiceTest.java
│       └── resources/
│
├── database/
│   ├── migrations/
│   │   ├── 001_schema.sql
│   │   └── 002_seed.sql
│   ├── scripts/
│   │   └── 003_views.sql
│   └── backups/
│
├── docs/
│
├── pom.xml
├── .gitignore
└── README.md

# 10. Validation et gestion des erreurs

L'application doit vérifier les données avant leur enregistrement.

## Exemples

- Champs obligatoires non vides.
- Quantités positives.
- Montants positifs ou nuls selon le cas.
- Dates valides.
- Identifiants uniques.
- Vérification des relations entre les entités.
- Vérification des droits d'accès.

Les messages d'erreur doivent être compréhensibles pour l'utilisateur.

---

# 11. Tests

Les tests doivent se concentrer sur les fonctions importantes.

## Tests principaux

- Connexion et authentification.
- Vérification des rôles.
- Ajout et modification des données.
- Enregistrement des récoltes.
- Vérification des quantités expédiées.
- Calcul des quantités disponibles en stock.
- Enregistrement des dépenses et paiements.

Des tests manuels des interfaces et des opérations principales sont également nécessaires.

---

# 12. Principes de développement

Pour respecter le délai et garder une application simple :

1. Une fonctionnalité doit être réellement opérationnelle avant de passer à la suivante.
2. Éviter les classes et les couches inutiles.
3. Éviter les systèmes de permissions dynamiques.
4. Éviter les modules commerciaux complexes.
5. Réutiliser les composants graphiques lorsque cela est utile.
6. Garder les contrôleurs simples.
7. Placer les règles métier dans les Services.
8. Garder les requêtes SQL dans les DAO.
9. Tester les opérations importantes.
10. Ne jamais modifier la base existante sans examiner les conséquences.

---

# 13. Priorités de réalisation

| Priorité | Fonctionnalités |
|----------|----------------|
| Essentielle | Architecture MVC, connexion PostgreSQL, authentification |
| Essentielle | Fermes, parcelles, ouvriers, présences |
| Essentielle | Récoltes, expéditions, dépenses |
| Importante | Clients, stock simplifié, paiements des salaires |
| Importante | Dashboard et rapports PDF |
| Finition | Notifications, journal d'activité, amélioration visuelle |

Les fonctionnalités essentielles doivent être terminées avant d'investir du temps dans les éléments de finition.

---

# 14. Résultat attendu

À la fin du développement, Orangia doit être :

- Une application desktop JavaFX fonctionnelle.
- Connectée à PostgreSQL.
- Organisée selon une architecture MVC simple.
- Utilisable par les quatre rôles.
- Capable de gérer les principales opérations d'une exploitation agricole.
- Dotée d'un Dashboard professionnel.
- Capable de générer des rapports PDF.
- Facile à présenter, tester et maintenir.

> **Principe final :**
>
> Mieux vaut une application simple, cohérente et entièrement fonctionnelle qu'une application très ambitieuse avec plusieurs modules inachevés.