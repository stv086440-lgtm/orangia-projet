# Répartition des Tâches et Planning de l'Équipe - Orangia

## 1. Vue d'Ensemble de l'Équipe (6 Membres)

| Membre | Rôle Projet | Module Attribué | Entités Clés | Complexité |
| :--- | :--- | :--- | :--- | :---: |
| **Membre 1** | **Chef de Projet** | **Finances (Dépenses) & Dashboard** | `Depense`, `Dashboard` | ★★★☆☆ |
| **Membre 2** | Développeur Backend / Sécurité | **Socle, Authentification & Utilisateurs** | `Utilisateur`, `Role`, `Session` | ★★★☆☆ |
| **Membre 3** | Développeur | **Exploitation Agricole (Fermes & Parcelles)** | `Ferme`, `Parcelle` | ★★☆☆☆ |
| **Membre 4** | Développeur | **Ressources Humaines & Salaires** | `Ouvrier`, `Presence`, `PaiementSalaire` | ★★★★☆ |
| **Membre 5** | Développeur | **Production & Vente (Récoltes & Expéditions)** | `Recolte`, `Client`, `Expedition` | ★★★★☆ |
| **Membre 6** | Développeur | **Stock d'Intrants & Notifications** | `ProduitStock`, `MouvementStock`, `Notification` | ★★★☆☆ |

---

## 2. Fiches Détaillées des Missions par Membre

---

### Membre 1 (Chef de Projet) : Finances (Dépenses) & Pilotage Dashboard
* **Mission principale :** Gérer les flux financiers de dépenses, consolider les indicateurs de pilotage, et superviser l'intégration globale de l'application.
* **Classes & Fichiers assignés :**
  - **Model :** `model/Depense.java`
  - **DAO :** `dao/interfaces/DepenseDAO.java`, `dao/impl/DepenseDAOImpl.java`
  - **Service :** `service/DepenseService.java`, `service/DashboardService.java`
  - **Controller :** `controller/DepenseController.java`, `controller/DashboardController.java`
  - **Validation :** `validation/DepenseValidator.java`
  - **Vues :** `resources/fxml/DepensesView.fxml`, `resources/fxml/DashboardView.fxml`
  - **Tests :** `service/DepenseServiceTest.java`
* **Rôle transverse :** Revue de code Git (PR), arbitrage technique, validation des merges.

---

### Membre 2 : Socle Technique, Authentification & Gestion des Utilisateurs
* **Mission principale :** Mettre en place la persistance de base, la sécurité (hachage), la session utilisateur, et la gestion des comptes et affectations de fermes.
* **Classes & Fichiers assignés :**
  - **Base & Sécurité :** `database/DatabaseConnection.java`, `security/SessionManager.java`, `security/PasswordHasher.java`
  - **Model :** `model/Utilisateur.java`, `model/Role.java` (Enum à 4 rôles)
  - **DAO :** `dao/interfaces/UtilisateurDAO.java`, `dao/impl/UtilisateurDAOImpl.java` (avec table `utilisateur_ferme`)
  - **Service :** `service/AuthService.java`, `service/UtilisateurService.java`
  - **Controller :** `controller/LoginController.java`, `controller/UtilisateurController.java`
  - **Vues :** `resources/fxml/LoginView.fxml`, `resources/fxml/UtilisateursView.fxml`
  - **Tests :** `dao/UtilisateurDAOTest.java`, `service/AuthServiceTest.java`

---

### Membre 3 : Exploitation Agricole (Fermes & Parcelles)
* **Mission principale :** Permettre la configuration des domaines agricoles et le découpage parcellaire (superficie, variétés d'oranges, nombre d'arbres).
* **Classes & Fichiers assignés :**
  - **Model :** `model/Ferme.java`, `model/Parcelle.java`
  - **DAO :** `dao/interfaces/FermeDAO.java`, `dao/impl/FermeDAOImpl.java`, `dao/interfaces/ParcelleDAO.java`, `dao/impl/ParcelleDAOImpl.java`
  - **Service :** `service/FermeService.java`, `service/ParcelleService.java`
  - **Controller :** `controller/FermeController.java`, `controller/ParcelleController.java`
  - **Validation :** `validation/FermeValidator.java`
  - **Vues :** `resources/fxml/FermesView.fxml`, `resources/fxml/ParcellesView.fxml`
  - **Tests :** `dao/FermeDAOTest.java`

---

### Membre 4 : Ressources Humaines (Ouvriers, Présences & Salaires)
* **Mission principale :** Administrer le personnel agricole, réaliser le pointage quotidien des présences, calculer et émettre les paiements de salaires.
* **Classes & Fichiers assignés :**
  - **Model :** `model/Ouvrier.java`, `model/Presence.java`, `model/PaiementSalaire.java`
  - **DAO :** `dao/interfaces/OuvrierDAO.java`, `dao/impl/OuvrierDAOImpl.java`, `dao/interfaces/PresenceDAO.java`, `dao/impl/PresenceDAOImpl.java`, `dao/interfaces/PaiementSalaireDAO.java`, `dao/impl/PaiementSalaireDAOImpl.java`
  - **Service :** `service/OuvrierService.java`, `service/PresenceService.java`, `service/PaiementService.java`
  - **Controller :** `controller/OuvrierController.java`, `controller/PresenceController.java`, `controller/PaiementController.java`
  - **Validation :** `validation/OuvrierValidator.java`
  - **Vues :** `resources/fxml/OuvriersView.fxml`, `resources/fxml/PresencesView.fxml`, `resources/fxml/PaiementsView.fxml`
  - **Tests :** `dao/OuvrierDAOTest.java`

---

### Membre 5 : Production & Commercialisation (Récoltes, Clients & Expéditions)
* **Mission principale :** Gérer l'enregistrement des cueillettes d'oranges, le portefeuille clients (stations de conditionnement) et la création des expéditions avec contrôle strict des stocks.
* **Classes & Fichiers assignés :**
  - **Model :** `model/Recolte.java`, `model/Client.java`, `model/Expedition.java`
  - **DAO :** `dao/interfaces/RecolteDAO.java`, `dao/impl/RecolteDAOImpl.java`, `dao/interfaces/ClientDAO.java`, `dao/impl/ClientDAOImpl.java`, `dao/interfaces/ExpeditionDAO.java`, `dao/impl/ExpeditionDAOImpl.java` (gestion de `expedition_recolte`)
  - **Service :** `service/RecolteService.java`, `service/ClientService.java`, `service/ExpeditionService.java`
  - **Controller :** `controller/RecolteController.java`, `controller/ClientController.java`, `controller/ExpeditionController.java`
  - **Validation :** `validation/RecolteValidator.java`, `validation/ExpeditionValidator.java`
  - **Vues :** `resources/fxml/RecoltesView.fxml`, `resources/fxml/ClientsView.fxml`, `resources/fxml/ExpeditionsView.fxml`
  - **Tests :** `dao/RecolteDAOTest.java`, `service/ExpeditionServiceTest.java`

---

### Membre 6 : Gestion du Stock d'Intrants & Notifications
* **Mission principale :** Assurer le suivi des stocks de consommables agricoles (engrais, carburant, matériel), gérer les mouvements d'entrée/sortie et lever les alertes automatiques de seuil critique.
* **Classes & Fichiers assignés :**
  - **Model :** `model/ProduitStock.java`, `model/MouvementStock.java`, `model/Notification.java`
  - **DAO :** `dao/interfaces/ProduitStockDAO.java`, `dao/impl/ProduitStockDAOImpl.java`, `dao/interfaces/MouvementStockDAO.java`, `dao/impl/MouvementStockDAOImpl.java`, `dao/interfaces/NotificationDAO.java`, `dao/impl/NotificationDAOImpl.java`
  - **Service :** `service/StockService.java`, `service/NotificationService.java`
  - **Controller :** `controller/StockController.java`
  - **Vues :** `resources/fxml/StockView.fxml`
  - **Tests :** `service/StockServiceTest.java`

---

## 3. Démarche de Travail et Jalons (Phases)

* **Phase 1 : Socle et Modèles (Jours 1 - 2)**
  - Initialisation de la base PostgreSQL (`database/migrations/001_schema.sql`).
  - Implémentation des classes du package `model/` et de la connexion JDBC (`DatabaseConnection`).
* **Phase 2 : Développement DAO & CRUD (Jours 3 - 5)**
  - Chaque membre développe et teste unitairement ses interfaces et implémentations DAO.
* **Phase 3 : Logique Métier & Écrans FXML (Jours 6 - 8)**
  - Implémentation des Services (vérifications et règles métier).
  - Conception des interfaces FXML et des Contrôleurs associés.
* **Phase 4 : Intégration, Dashboard & Rapports (Jours 9 - 10)**
  - Intégration des vues dans la navigation principale.
  - Finalisation du Dashboard par le Chef de Projet et édition des rapports PDF.
  - Tests d'ensemble de bout en bout.
