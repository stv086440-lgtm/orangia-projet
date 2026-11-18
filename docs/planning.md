# PLANIFICATION.md
# Répartition des tâches et planning de développement — Orangia

## 1. Présentation générale du projet

**Orangia** est une application desktop de gestion d'une exploitation agricole spécialisée dans la production d'oranges.

L'application permet de gérer :
- Les utilisateurs et les rôles.
- Les fermes et les parcelles.
- Les ouvriers et les présences.
- Les récoltes et les expéditions.
- Les clients.
- Les dépenses et les demandes de dépenses.
- Les paiements des expéditions.
- Les salaires.
- Les tableaux de bord, les notifications et les rapports.

### 1.1 Technologies utilisées

| Technologie | Utilisation |
|---|---|
| Java | Langage de programmation |
| JavaFX | Interfaces graphiques |
| FXML | Structure des interfaces |
| CSS | Mise en forme des interfaces |
| Maven | Gestion du projet et des dépendances |
| PostgreSQL | Base de données |
| pgAdmin | Administration de la base de données |
| JDBC | Communication avec PostgreSQL |
| JUnit | Tests unitaires |
| Git & GitHub | Gestion des versions et collaboration |

### 1.2 Architecture technique

Le projet adopte une architecture MVC organisée en couches :

**JavaFX / FXML → Controller → Service → DAO → PostgreSQL**

Les responsabilités sont réparties ainsi :

- **Model :** représente les entités et les objets métier.
- **View :** contient les interfaces FXML et les fichiers CSS.
- **Controller :** reçoit les actions de l'utilisateur et transmet les demandes aux Services.
- **Service :** contient les règles métier, les contrôles et les traitements.
- **DAO :** assure les opérations d'accès aux données dans PostgreSQL.
- **Database :** contient la connexion JDBC et les scripts SQL.
- **Security :** gère l'authentification, les mots de passe et la session.
- **Validation :** regroupe les validations des données.
- **Reports :** gère la génération des rapports PDF et CSV.

**Règle obligatoire :** aucune requête SQL directement dans un Controller. Toute règle métier doit être placée dans un Service.

---

## 2. Organisation de l'équipe

L'équipe est composée de six membres. Chaque membre est responsable d'un ensemble de fonctionnalités et doit réaliser leur développement complet : interface, Controller, Service, DAO, Model si nécessaire, validation et tests.

| Membre | Rôle dans le projet | Responsabilité principale |
|---|---|---|
| Membre 1 | Chef de projet | Gestion financière, tableaux de bord et intégration |
| Membre 2 | Développeur Backend / Sécurité | Socle technique, authentification et utilisateurs |
| Membre 3 | Développeur | Gestion des fermes et des parcelles |
| Membre 4 | Développeur | Espace RH et gestion des salaires |
| Membre 5 | Développeur | Espace Responsable : récoltes et expéditions |
| Membre 6 | Développeur | Clients, documents, notifications et fonctionnalités partagées |

### 2.1 Responsabilités du Chef de projet

Le Membre 1 assure également :
- La coordination de l'équipe.
- La validation de l'architecture.
- La vérification des fonctionnalités avant intégration.
- La gestion des Pull Requests.
- La résolution des conflits Git.
- Le contrôle de la cohérence entre les différents espaces.
- La validation finale des fonctionnalités.

Le Chef de projet participe également au développement de fonctionnalités, mais conserve une vision globale du projet.

---

## 3. Méthode de développement adoptée

### 3.1 Développement espace par espace

Le développement ne sera pas organisé en séparant le Frontend, le Backend et la Base de données entre les membres.

L'équipe travaillera selon une approche **Full-Stack par fonctionnalité et par espace**.

Exemple :

Pour la fonctionnalité Gestion des récoltes, le membre responsable doit réaliser :
1. La conception de l'interface FXML.
2. Le Controller associé.
3. Le Service contenant les règles métier.
4. Le Model Recolte si nécessaire.
5. L'interface DAO et son implémentation.
6. Les opérations PostgreSQL nécessaires.
7. La validation des données.
8. Les tests.
9. L'intégration dans l'espace concerné.

Une fonctionnalité est considérée comme terminée uniquement lorsque toutes ses couches fonctionnent correctement.

### 3.2 Ordre de développement des espaces

Le développement suivra cet ordre :

1. Socle technique et authentification.
2. Espace Administrateur.
3. Espace Responsable de Ferme.
4. Espace RH.
5. Espace Comptable.
6. Intégration générale et tests finaux.

**Pourquoi cet ordre ?**

- L'authentification constitue la base de tous les espaces.
- L'Administrateur configure les utilisateurs, les fermes, les parcelles et les paramètres.
- Le Responsable enregistre les opérations agricoles et transmet les informations nécessaires.
- Le RH traite les présences transmises et calcule les salaires.
- Le Comptable traite les demandes, les dépenses et les opérations financières, notamment les expéditions.

Les fonctionnalités communes peuvent être préparées pendant le socle technique, mais elles seront intégrées au fur et à mesure dans les espaces concernés.

---

# 4. Répartition détaillée des missions

## 4.1 Membre 1 — Chef de projet : Gestion financière et pilotage

### Responsabilité principale

Développer les fonctionnalités financières de l'Espace Comptable et assurer la coordination technique et l'intégration générale.

### Fonctionnalités attribuées

- Gestion des dépenses réelles.
- Consultation et traitement des demandes de dépenses.
- Gestion des paiements des expéditions.
- Suivi des montants payés et restants.
- Tableau de bord selon les indicateurs de chaque espace.
- Participation à l'intégration des rapports financiers.

### Classes et fichiers prévus

**Model**
- `Depense.java`
- `DemandeDepense.java`
- `PaiementExpedition.java`

**DAO**
- `DepenseDAO.java`
- `DepenseDAOImpl.java`
- `DemandeDepenseDAO.java`
- `DemandeDepenseDAOImpl.java`
- `PaiementExpeditionDAO.java`
- `PaiementExpeditionDAOImpl.java`

**Service**
- `DepenseService.java`
- `DemandeDepenseService.java`
- `PaiementExpeditionService.java`
- `DashboardService.java`

**Controller**
- `DepenseController.java`
- `DemandeDepenseController.java`
- `PaiementExpeditionController.java`
- `DashboardController.java`

**View**
- `DepensesView.fxml`
- `DemandesDepensesView.fxml`
- `PaiementsExpeditionsView.fxml`
- Les vues Dashboard nécessaires aux différents espaces.

**Validation et tests**
- `DepenseValidator.java`
- `DepenseServiceTest.java`
- `PaiementExpeditionServiceTest.java`

### Règles métier importantes

- Une demande acceptée ne devient pas automatiquement une dépense réelle.
- Le Comptable enregistre la dépense après l'achat.
- Un paiement est lié à une expédition.
- Une expédition peut avoir plusieurs paiements.
- Le total des paiements validés ne peut pas dépasser le montant total de l'expédition.
- Le montant restant est calculé automatiquement.
- Les coûts salariaux proviennent des données RH : aucun système de paie parallèle ne doit être créé dans l'espace Comptable.

---

## 4.2 Membre 2 — Socle technique, authentification et utilisateurs

### Responsabilité principale

Mettre en place les composants techniques communs et développer l'authentification ainsi que la gestion des comptes utilisateurs.

### Fonctionnalités attribuées

- Connexion et authentification.
- Choix de l'espace.
- Vérification des identifiants.
- Gestion des rôles.
- Gestion des comptes utilisateurs.
- Activation et désactivation des comptes.
- Réinitialisation des mots de passe par l'Administrateur.
- Gestion de la session.
- Redirection vers l'espace autorisé.

### Classes et fichiers prévus

**Database**
- `DatabaseConnection.java`

**Security**
- `PasswordHasher.java`
- `SessionManager.java`

**Model**
- `Utilisateur.java`
- `Role.java`

**DAO**
- `UtilisateurDAO.java`
- `UtilisateurDAOImpl.java`

**Service**
- `AuthService.java`
- `UtilisateurService.java`

**Controller**
- `LoginController.java`
- `WelcomeController.java`
- `UtilisateurController.java`

**View**
- `WelcomeView.fxml`
- `LoginView.fxml`
- `UtilisateursView.fxml`

**Tests**
- `AuthServiceTest.java`
- `UtilisateurServiceTest.java`

### Règles métier importantes

- Les quatre rôles sont : Administrateur, Responsable de Ferme, Agent RH et Comptable.
- Tous les rôles utilisent les mêmes champs de connexion : Identifiant et Mot de passe.
- Le choix de l'espace ne donne pas automatiquement le droit d'y accéder.
- Le rôle réel est vérifié à partir du compte enregistré dans PostgreSQL.
- Chaque Responsable est affecté à une seule ferme.
- RH et Comptable travaillent sur l'ensemble des fermes.
- L'Administrateur est responsable de la création et de la gestion des comptes utilisateurs.
- Les mots de passe sont stockés sous forme hachée, jamais en clair.

**Important :** le Responsable, le RH et le Comptable peuvent consulter leur profil, mais ne peuvent pas modifier leurs informations personnelles ou leurs informations de compte. Cette gestion appartient à l'Administrateur.

---

## 4.3 Membre 3 — Gestion des fermes et des parcelles

### Responsabilité principale

Développer les fonctionnalités de configuration agricole accessibles à l'Administrateur.

### Fonctionnalités attribuées

- Ajouter une ferme.
- Modifier les informations d'une ferme.
- Consulter les fermes.
- Activer ou désactiver une ferme.
- Ajouter des parcelles.
- Modifier les informations des parcelles.
- Consulter les parcelles.
- Désactiver une parcelle lorsque cela est nécessaire.

### Classes et fichiers prévus

**Model**
- `Ferme.java`
- `Parcelle.java`

**DAO**
- `FermeDAO.java`
- `FermeDAOImpl.java`
- `ParcelleDAO.java`
- `ParcelleDAOImpl.java`

**Service**
- `FermeService.java`
- `ParcelleService.java`

**Controller**
- `FermeController.java`
- `ParcelleController.java`

**View**
- `FermesView.fxml`
- `ParcellesView.fxml`

**Validation et tests**
- `FermeValidator.java`
- `ParcelleValidator.java`
- `FermeServiceTest.java`
- `ParcelleServiceTest.java`

### Règles métier importantes

- La gestion des fermes et des parcelles appartient exclusivement à l'Administrateur.
- Chaque ferme possède ses propres parcelles.
- Chaque Responsable est affecté à une seule ferme.
- Le Responsable ne peut consulter et gérer que les opérations de sa ferme.
- Les fermes ou parcelles ayant des opérations historiques ne doivent pas être supprimées physiquement sans contrôle. La désactivation est privilégiée.

---

## 4.4 Membre 4 — Espace RH : Personnel, présences et salaires

### Responsabilité principale

Développer entièrement l'Espace RH, depuis la gestion des ouvriers jusqu'au traitement des présences et des salaires.

### Fonctionnalités attribuées

- Tableau de bord RH.
- Gestion du personnel.
- Gestion des affectations des ouvriers aux fermes.
- Consultation et traitement des présences transmises par les Responsables.
- Correction des erreurs de pointage avec traçabilité.
- Validation des présences.
- Calcul des salaires.
- Suivi des paiements.
- Génération des rapports RH.

### Classes et fichiers prévus

**Model**
- `Ouvrier.java`
- `Presence.java`
- `PaiementSalaire.java`

**DAO**
- `OuvrierDAO.java`
- `OuvrierDAOImpl.java`
- `PresenceDAO.java`
- `PresenceDAOImpl.java`
- `PaiementSalaireDAO.java`
- `PaiementSalaireDAOImpl.java`

**Service**
- `OuvrierService.java`
- `PresenceService.java`
- `PaiementSalaireService.java`
- `RapportRHService.java`

**Controller**
- `OuvrierController.java`
- `PresenceController.java`
- `PaiementSalaireController.java`
- `RapportRHController.java`

**View**
- `DashboardRHView.fxml`
- `OuvriersView.fxml`
- `PresencesView.fxml`
- `SalairesView.fxml`
- `RapportsRHView.fxml`

**Validation et tests**
- `OuvrierValidator.java`
- `PresenceValidator.java`
- `OuvrierServiceTest.java`
- `PresenceServiceTest.java`
- `PaiementSalaireServiceTest.java`

### Règles métier importantes

- RH est global et travaille sur toutes les fermes.
- RH gère les dossiers professionnels des ouvriers.
- RH peut modifier l'affectation d'un ouvrier d'une ferme à une autre.
- Le changement d'affectation ne supprime pas son historique de présence ou de paiement.
- Les ouvriers ne possèdent pas de compte utilisateur.
- Cabran et Naïb sont des ouvriers ordinaires, pas des rôles de l'application.
- Le Responsable désigne le Cabran et le Naïb dans son organisation de terrain.
- Le Cabran prépare la feuille de présence papier.
- Le Responsable vérifie les informations, enregistre les absences et transmet la feuille au RH.
- RH vérifie et valide les présences.
- Les dimanches et les jours de repos ne sont pas considérés comme des absences.
- Les horaires de travail sont configurés par l'Administrateur et utilisés par RH.
- Les anciennes présences conservent les horaires appliqués au moment de leur enregistrement.
- Les changements de tarif ne modifient pas les anciens calculs validés.
- Les règles de calcul du salaire et de la prime du Cabran doivent être centralisées dans les Services.

---

## 4.5 Membre 5 — Espace Responsable : Récoltes et expéditions

### Responsabilité principale

Développer les fonctionnalités opérationnelles de l'Espace Responsable de Ferme.

### Fonctionnalités attribuées

- Tableau de bord du Responsable.
- Gestion des récoltes.
- Consultation du stock disponible.
- Gestion opérationnelle des expéditions.
- Enregistrement des feuilles de présence et transmission au RH.
- Création des demandes de dépenses.
- Consultation de l'historique des opérations.

### Classes et fichiers prévus

**Model**
- `Recolte.java`
- `Expedition.java`
- `Presence.java` — utilisation du Model partagé avec RH.
- `DemandeDepense.java` — utilisation du Model partagé avec Comptable.

**DAO**
- `RecolteDAO.java`
- `RecolteDAOImpl.java`
- `ExpeditionDAO.java`
- `ExpeditionDAOImpl.java`

**Service**
- `RecolteService.java`
- `ExpeditionService.java`
- `StockAgricoleService.java`
- `TransmissionPresenceService.java`

**Controller**
- `RecolteController.java`
- `ExpeditionController.java`
- `TransmissionPresenceController.java`

**View**
- `DashboardResponsableView.fxml`
- `RecoltesView.fxml`
- `ExpeditionsView.fxml`
- `PresencesView.fxml`
- `HistoriqueResponsableView.fxml`

**Validation et tests**
- `RecolteValidator.java`
- `ExpeditionValidator.java`
- `RecolteServiceTest.java`
- `ExpeditionServiceTest.java`

### Règles métier importantes

- Chaque Responsable travaille uniquement sur sa ferme.
- Une récolte est liée à une parcelle.
- Plusieurs récoltes peuvent être enregistrées sur la même parcelle le même jour.
- Une récolte enregistrée et non annulée augmente automatiquement le stock.
- Le stock est calculé à partir des récoltes et des expéditions confirmées.
- Le Responsable crée et gère les informations opérationnelles des expéditions.
- Le Comptable est le seul à confirmer une expédition.
- Le stock est diminué uniquement après confirmation.
- Le Responsable ne voit aucune information financière liée aux expéditions.
- Une modification d'une expédition déjà confirmée doit repasser par le processus de confirmation du Comptable si elle affecte les données opérationnelles ou le stock.
- Le Responsable prépare les feuilles de présence et transmet les données au RH.
- Le Responsable peut créer une demande de dépense, mais ne peut ni l'accepter ni la refuser.

---

## 4.6 Membre 6 — Clients et fonctionnalités communes

### Responsabilité principale

Développer le module indépendant de gestion des clients et les composants communs nécessaires aux différents espaces.

### Fonctionnalités attribuées

- Gestion des clients.
- Recherche et consultation des clients.
- Ajout et modification des clients.
- Désactivation des clients.
- Gestion des documents joints aux opérations.
- Gestion des notifications.
- Participation à l'intégration des fonctionnalités communes.

### Classes et fichiers prévus

**Model**
- `Client.java`
- `Document.java`
- `Notification.java`

**DAO**
- `ClientDAO.java`
- `ClientDAOImpl.java`
- `DocumentDAO.java`
- `DocumentDAOImpl.java`
- `NotificationDAO.java`
- `NotificationDAOImpl.java`

**Service**
- `ClientService.java`
- `DocumentService.java`
- `NotificationService.java`

**Controller**
- `ClientController.java`
- `DocumentController.java`
- `NotificationController.java`

**View**
- `ClientsView.fxml`
- `DocumentsView.fxml`
- Les composants d'affichage des notifications.

**Tests**
- `ClientServiceTest.java`
- `DocumentServiceTest.java`
- `NotificationServiceTest.java`

### Règles métier importantes

- Le module Client est indépendant techniquement.
- Le Responsable accède à la gestion des clients depuis son espace Expéditions.
- Le Comptable accède à la gestion des clients depuis son espace Gestion des clients et suivi commercial.
- Les deux rôles peuvent consulter, ajouter, modifier, rechercher et désactiver un client.
- Un client associé à des opérations historiques ne doit pas être supprimé physiquement.
- Les documents sont associés aux opérations concernées.
- Les notifications sont accessibles selon le rôle et les autorisations de l'utilisateur.

**Attention :** les fonctionnalités communes doivent être développées et intégrées progressivement avec les espaces concernés, et non constituer un nouvel espace indépendant dans la navigation.

---

# 5. Planning de développement

Le planning suit une progression séquentielle : un espace doit atteindre son niveau de validation avant de passer au suivant.

## Phase 0 — Préparation du socle technique

**Objectif :** préparer un projet fonctionnel et une base commune pour toute l'équipe.

Travaux :
- Vérifier le dépôt GitHub.
- Initialiser le projet Maven.
- Créer la structure des packages.
- Configurer JavaFX et JDBC.
- Configurer la connexion PostgreSQL.
- Préparer le premier schéma de base de données.
- Développer les classes communes nécessaires.
- Mettre en place l'authentification et la gestion de session.
- Créer les premières interfaces de choix d'espace et de connexion.

**Responsables principaux :** Membre 2 et Chef de projet.

**Livrable :** projet Maven exécutable, connexion PostgreSQL fonctionnelle et authentification initiale.

## Phase 1 — Développement complet de l'Espace Administrateur

**Objectif :** obtenir un espace Admin fonctionnel avant de passer au Responsable.

Travaux :
- Tableau de bord Admin.
- Gestion des utilisateurs et des rôles.
- Gestion des fermes et des parcelles.
- Supervision globale.
- Historique et rapports.
- Paramètres système.
- Configuration des horaires de travail.
- Sauvegarde et restauration de la base de données.

Répartition principale :
- Membre 2 : utilisateurs et comptes.
- Membre 3 : fermes et parcelles.
- Membre 1 : tableau de bord et intégration.
- Membre 6 : documents, notifications et composants communs.
- Membre 4 et Membre 5 : préparation des dépendances de leurs futurs espaces, sans commencer à développer des écrans d'autres espaces en parallèle.

**Livrable :** Espace Administrateur intégré, testé et validé.

## Phase 2 — Développement complet de l'Espace Responsable

**Objectif :** permettre au Responsable de gérer les opérations quotidiennes de sa ferme.

Travaux :
- Tableau de bord.
- Gestion des récoltes.
- Calcul automatique du stock agricole.
- Gestion opérationnelle des expéditions.
- Gestion des clients depuis Expéditions.
- Enregistrement et transmission des présences.
- Création des demandes de dépenses.
- Historique des opérations.
- Gestion des documents associés.

Répartition principale :
- Membre 5 : récoltes, expéditions et transmissions.
- Membre 6 : module Clients et documents.
- Membre 1 : demandes de dépenses et intégration des règles partagées.
- Membre 2 : sécurité et contrôle d'accès.
- Membre 3 : support sur les données Ferme et Parcelle.

**Livrable :** Espace Responsable opérationnel, avec ses fonctionnalités et ses contrôles d'accès validés.

## Phase 3 — Développement complet de l'Espace RH

**Objectif :** traiter les données du personnel, les présences transmises et les salaires.

Travaux :
- Tableau de bord RH.
- Gestion du personnel.
- Affectation des ouvriers aux fermes.
- Consultation des feuilles transmises.
- Correction et validation des présences.
- Calcul des salaires.
- Suivi des paiements.
- Gestion de la prime du Cabran.
- Rapports RH.

Répartition principale :
- Membre 4 : développement complet du module RH.
- Membre 1 : intégration des indicateurs et des coûts salariaux agrégés.
- Membre 2 : sécurité et contrôle d'accès.
- Membre 6 : documents et notifications.
- Membre 5 : vérification de la compatibilité entre les transmissions du Responsable et le traitement RH.

**Livrable :** Espace RH fonctionnel, avec un workflow complet entre Responsable et RH.

## Phase 4 — Développement complet de l'Espace Comptable

**Objectif :** finaliser la gestion financière et le suivi commercial.

Travaux :
- Tableau de bord Comptable.
- Consultation des récoltes et du stock.
- Gestion des clients.
- Consultation et confirmation des expéditions.
- Gestion des paiements des expéditions.
- Gestion des dépenses réelles.
- Acceptation ou refus des demandes de dépenses.
- Consultation des coûts salariaux agrégés.
- Historique et rapports Comptables.

Répartition principale :
- Membre 1 : dépenses, demandes, paiements et tableau de bord.
- Membre 6 : clients, documents et notifications.
- Membre 5 : intégration du workflow opérationnel des expéditions.
- Membre 4 : intégration des coûts salariaux RH.
- Membre 2 : sécurité et autorisations.

**Livrable :** Espace Comptable complet, avec les règles financières et les validations nécessaires.

## Phase 5 — Intégration générale et tests finaux

**Objectif :** vérifier le fonctionnement de l'application complète.

Travaux :
- Vérification de la navigation entre les espaces.
- Vérification des permissions par rôle.
- Vérification de la cohérence des données.
- Tests des workflows entre les espaces.
- Tests des opérations sensibles.
- Vérification des transactions PostgreSQL.
- Correction des erreurs.
- Vérification de la génération des PDF et CSV.
- Vérification des sauvegardes et restaurations.
- Préparation de la version finale.

**Livrable :** version stable et complète de Orangia.

---

# 6. Workflow général de l'application

## 6.1 Workflow des récoltes et du stock

1. Le Responsable sélectionne une parcelle de sa ferme.
2. Il enregistre une récolte avec sa date et sa quantité en kilogrammes.
3. Le système vérifie les données.
4. La récolte est enregistrée.
5. Le stock agricole disponible augmente automatiquement.
6. Le Comptable peut consulter les récoltes et le stock global.
7. Une récolte annulée est exclue du calcul du stock.

## 6.2 Workflow des expéditions

1. Le Responsable crée une expédition.
2. Il sélectionne un client.
3. Il renseigne la date et la quantité à expédier.
4. Le système vérifie que le stock disponible est suffisant.
5. L'expédition reste en attente de confirmation.
6. Le Comptable consulte les informations opérationnelles.
7. Le Comptable confirme ou ne confirme pas l'expédition.
8. Après confirmation, le stock est diminué.
9. Le Comptable renseigne les informations financières.
10. Il peut enregistrer un ou plusieurs paiements.
11. Le système calcule le montant payé et le montant restant.

## 6.3 Workflow des présences et des salaires

1. Le Cabran prépare la feuille de présence papier.
2. Le Responsable vérifie la feuille.
3. Il enregistre les absences.
4. Il transmet les données au RH.
5. RH consulte les présences transmises.
6. RH corrige les erreurs si nécessaire, avec traçabilité.
7. RH valide les présences.
8. Le système utilise les données validées pour le calcul des salaires.
9. RH enregistre et suit les paiements des salaires.
10. Le Comptable peut consulter les coûts salariaux agrégés.

## 6.4 Workflow des demandes de dépenses

1. Le Responsable crée une demande.
2. Il indique le sujet, la catégorie, le montant estimé et la justification.
3. La demande est transmise au Comptable.
4. Le Comptable accepte ou refuse la demande.
5. En cas de refus, il indique le motif.
6. Après l'achat, le Comptable enregistre la dépense réelle.
7. Il peut associer la dépense à la demande correspondante.
8. L'acceptation d'une demande ne crée jamais automatiquement une dépense.

---

# 7. Organisation de l'architecture du projet

Structure proposée :

```text
orangia/
│
├── pom.xml
├── README.md
├── planification.md
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/orangia/
│   │   │       │
│   │   │       ├── Main.java
│   │   │       │
│   │   │       ├── config/
│   │   │       │   └── AppConfig.java
│   │   │       │
│   │   │       ├── database/
│   │   │       │   └── DatabaseConnection.java
│   │   │       │
│   │   │       ├── model/
│   │   │       │   ├── Utilisateur.java
│   │   │       │   ├── Role.java
│   │   │       │   ├── Ferme.java
│   │   │       │   ├── Parcelle.java
│   │   │       │   ├── Ouvrier.java
│   │   │       │   ├── Presence.java
│   │   │       │   ├── PaiementSalaire.java
│   │   │       │   ├── Recolte.java
│   │   │       │   ├── Expedition.java
│   │   │       │   ├── Client.java
│   │   │       │   ├── Depense.java
│   │   │       │   ├── DemandeDepense.java
│   │   │       │   ├── PaiementExpedition.java
│   │   │       │   ├── Document.java
│   │   │       │   └── Notification.java
│   │   │       │
│   │   │       ├── dao/
│   │   │       │   ├── interfaces/
│   │   │       │   └── impl/
│   │   │       │
│   │   │       ├── service/
│   │   │       │   ├── AuthService.java
│   │   │       │   ├── UtilisateurService.java
│   │   │       │   ├── FermeService.java
│   │   │       │   ├── ParcelleService.java
│   │   │       │   ├── OuvrierService.java
│   │   │       │   ├── PresenceService.java
│   │   │       │   ├── PaiementSalaireService.java
│   │   │       │   ├── RecolteService.java
│   │   │       │   ├── ExpeditionService.java
│   │   │       │   ├── StockAgricoleService.java
│   │   │       │   ├── ClientService.java
│   │   │       │   ├── DepenseService.java
│   │   │       │   ├── DemandeDepenseService.java
│   │   │       │   ├── PaiementExpeditionService.java
│   │   │       │   ├── NotificationService.java
│   │   │       │   └── DashboardService.java
│   │   │       │
│   │   │       ├── controller/
│   │   │       │
│   │   │       ├── security/
│   │   │       │   ├── SessionManager.java
│   │   │       │   └── PasswordHasher.java
│   │   │       │
│   │   │       ├── validation/
│   │   │       │
│   │   │       ├── reports/
│   │   │       │
│   │   │       └── util/
│   │   │
│   │   └── resources/
│   │       ├── fxml/
│   │       │   ├── auth/
│   │       │   ├── admin/
│   │       │   ├── responsable/
│   │       │   ├── rh/
│   │       │   └── comptable/
│   │       │
│   │       ├── css/
│   │       ├── images/
│   │       └── database/
│   │           └── migrations/
│   │
│   └── test/
│       └── java/
│           └── com/orangia/
│               ├── dao/
│               └── service/
│
└── docs/
    ├── conception/
    ├── database/
    └── architecture/
```

Cette structure est une organisation logique. Les fichiers et packages seront créés progressivement, au moment où les fonctionnalités correspondantes seront développées.

---

# 8. Règles de gestion de la base de données

Pour éviter les doublons et les incohérences :

1. Une seule entité `Utilisateur` pour les comptes de connexion.
2. Une seule entité `Ouvrier` pour les employés agricoles.
3. Les ouvriers ne sont pas des utilisateurs de l'application.
4. Une seule entité `Ferme` et une seule entité `Parcelle`.
5. Une seule entité `Presence`, utilisée dans le workflow Responsable → RH.
6. Une seule entité `PaiementSalaire`, gérée par RH.
7. Une seule entité `Client`, partagée entre Responsable et Comptable.
8. Une seule entité `Expedition`, créée par Responsable et confirmée par Comptable.
9. Une seule entité `Depense` pour les dépenses réelles.
10. Une entité distincte `DemandeDepense` pour les demandes préalables.
11. Une entité `PaiementExpedition` pour les paiements des expéditions.
12. Les tableaux de bord sont calculés à partir des données existantes et ne nécessitent pas une entité `Dashboard`.

### Gestion du stock agricole

Le stock disponible est calculé selon la règle :

**Stock disponible = Récoltes enregistrées et non annulées − Expéditions confirmées et non annulées.**

Il n'est pas nécessaire de créer une entité de stock modifiable manuellement pour les oranges.

Les tables `ProduitStock` et `MouvementStock` relatives aux intrants agricoles ne font pas partie du périmètre retenu.

### Affectation des utilisateurs aux fermes

- Un Responsable est affecté à une seule ferme.
- RH, Comptable et Administrateur sont globaux.
- L'affectation d'un Responsable peut être représentée par une référence `ferme_id` dans son compte utilisateur.
- Il n'est pas nécessaire de créer une table `utilisateur_ferme` pour gérer plusieurs fermes par utilisateur dans le fonctionnement retenu.

---

# 9. Organisation du travail avec Git et GitHub

### 9.1 Branches principales

```text
main
dev
feature/nom-fonctionnalite
```

- `main` : version stable du projet.
- `dev` : branche d'intégration des fonctionnalités validées.
- `feature/...` : branche de travail de chaque fonctionnalité.

### 9.2 Workflow Git

Chaque membre doit :

1. Récupérer les dernières modifications de `dev`.
2. Créer sa branche de fonctionnalité.
3. Développer et tester sa fonctionnalité.
4. Effectuer des commits clairs.
5. Envoyer sa branche sur GitHub.
6. Créer une Pull Request vers `dev`.
7. Faire vérifier le code.
8. Corriger les remarques éventuelles.
9. Attendre la validation avant le Merge.

Exemple :

```bash
git checkout dev
git pull origin dev

git checkout -b feature/gestion-recoltes

# Développement et tests

git add .
git commit -m "feat: implement harvest management"

git push -u origin feature/gestion-recoltes
```

**Important :** aucun membre ne doit modifier directement `main`. Les modifications passent par les Pull Requests.

### 9.3 Règles de collaboration

- Une fonctionnalité doit avoir un responsable clairement identifié.
- Les fichiers partagés doivent être coordonnés avant modification.
- Aucun membre ne doit créer une deuxième version d'une entité déjà existante.
- Les modifications du schéma PostgreSQL doivent être communiquées à l'équipe.
- Chaque membre doit expliquer les dépendances de sa fonctionnalité.
- Le Chef de projet valide l'intégration avant le Merge.
- Les conflits Git doivent être résolus proprement, sans supprimer le travail d'un autre membre.

---

# 10. Conditions de validation d'une fonctionnalité

Une fonctionnalité est considérée comme terminée lorsque :

- [ ] L'interface FXML est réalisée.
- [ ] Le Controller est fonctionnel.
- [ ] Le Service contient les règles métier.
- [ ] Le DAO fonctionne correctement.
- [ ] Les données sont enregistrées dans PostgreSQL.
- [ ] Les validations sont appliquées.
- [ ] Les autorisations sont respectées.
- [ ] Les erreurs sont correctement gérées.
- [ ] Les tests nécessaires sont effectués.
- [ ] La fonctionnalité est intégrée dans l'espace concerné.
- [ ] Le Chef de projet a vérifié le résultat.

---

# 11. Principes à respecter pendant tout le projet

1. Respecter les quatre rôles et leurs permissions.
2. Respecter l'ordre de développement espace par espace.
3. Ne pas développer une fonctionnalité hors du périmètre validé sans accord de l'équipe.
4. Ne pas dupliquer les entités ou les règles métier.
5. Respecter l'architecture MVC et la séparation des couches.
6. Ne jamais mettre du SQL dans les Controllers.
7. Ne pas mélanger les responsabilités du Responsable, du RH et du Comptable.
8. Préserver les historiques des opérations.
9. Tester chaque fonctionnalité avant son intégration.
10. Maintenir une seule version cohérente de la base de données.

---

# 12. Résultat attendu

À la fin du développement, Orangia devra disposer de quatre espaces complets et cohérents :

- **Administrateur :** configuration, utilisateurs, fermes, parcelles, supervision et paramètres.
- **Responsable de Ferme :** opérations agricoles, récoltes, expéditions, présences et demandes.
- **Agent RH :** personnel, affectations, présences, salaires et rapports RH.
- **Comptable :** stock agricole en consultation, clients, confirmation des expéditions, paiements, dépenses et rapports financiers.

L'ensemble de ces espaces devra fonctionner avec une seule base PostgreSQL, une architecture MVC commune, des entités partagées et un workflow cohérent entre les rôles.

**Objectif final :** livrer une application desktop professionnelle, fonctionnelle, maintenable et conforme au cahier des charges d'Orangia.