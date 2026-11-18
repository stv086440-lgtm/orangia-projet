# ARCHITECTURE GLOBALE — ORANGIA

**Application desktop de gestion d'une exploitation agricole spécialisée dans la production d'oranges**

| Élément | Choix |
|---|---|
| Langage | Java 21 |
| Interface graphique | JavaFX 24 + FXML + CSS |
| Architecture | MVC + Service + DAO |
| Base de données | PostgreSQL |
| Accès aux données | JDBC |
| Gestion du projet | Maven |
| Tests | JUnit 5 |
| IDE | Visual Studio Code |
| Nom du projet | ORANGIA |

---

# 1. Présentation du projet

ORANGIA est une application desktop destinée à faciliter la gestion d'une exploitation agricole spécialisée dans la production d'oranges.

L'application centralise la gestion des fermes, des parcelles, des ouvriers, des présences, des récoltes, des expéditions, des clients et des opérations financières.

Elle repose sur quatre espaces de travail indépendants, chacun disposant de ses propres fonctionnalités et autorisations.

## 1.1 Objectifs

- Centraliser les informations des exploitations agricoles.
- Faciliter le suivi des récoltes et des expéditions.
- Organiser la gestion du personnel et des présences.
- Assurer le suivi des dépenses et des recettes.
- Calculer automatiquement le stock disponible.
- Protéger les informations selon les responsabilités de chaque utilisateur.
- Générer des rapports et conserver l'historique des opérations.

## 1.2 Périmètre fonctionnel

L'application comprend :

1. Authentification et gestion des utilisateurs.
2. Gestion des fermes et des parcelles.
3. Gestion des ouvriers et des affectations.
4. Gestion des présences et des corrections.
5. Gestion des salaires et des paiements.
6. Gestion des récoltes.
7. Gestion des clients.
8. Gestion des expéditions.
9. Gestion des dépenses et des demandes de dépenses.
10. Gestion des documents et notifications.
11. Journalisation des actions.
12. Paramètres système et sauvegardes.
13. Tableaux de bord et rapports.

**Principe concernant le stock :** le stock d'oranges est calculé à partir des récoltes et des expéditions confirmées. Il ne s'agit pas d'un stock saisi manuellement. La gestion des stocks d'intrants agricoles n'est pas comprise dans le périmètre.

---

# 2. Technologies utilisées

| Technologie | Utilisation |
|---|---|
| Java 21 | Logique et développement |
| JavaFX 24 | Interface desktop |
| FXML | Structure des interfaces |
| CSS | Mise en forme graphique |
| PostgreSQL | Stockage des données |
| JDBC | Communication Java / PostgreSQL |
| Maven | Dépendances et compilation |
| JUnit 5 | Tests unitaires |
| Git / GitHub | Versionnement et collaboration |

---

# 3. Architecture technique

ORANGIA adopte une architecture en couches basée sur MVC, complétée par les couches Service et DAO.

## 3.1 Schéma général

```text
                  UTILISATEUR
                       |
                       v
                 VIEW (FXML)
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
                JDBC / DATABASE
                       |
                       v
                  POSTGRESQL
```

Le Model représente les données métier manipulées par les différentes couches.

## 3.2 Responsabilité des couches

### View — FXML

La View représente l'interface graphique.

Elle contient :
- Les formulaires.
- Les tableaux.
- Les boutons.
- Les menus.
- Les fenêtres de dialogue.
- Les composants visuels.

Elle ne contient aucune règle métier ni requête SQL.

### Controller — Java

Le Controller assure la communication entre l'interface et la couche Service.

Il est responsable de :
- Récupérer les informations saisies.
- Déclencher les actions demandées.
- Appeler les méthodes des Services.
- Actualiser les données affichées.
- Afficher les messages de résultat.

Il ne contient pas de requêtes SQL et ne doit pas porter les règles métier principales.

### Service — Java

Le Service contient les règles métier de l'application.

Il est responsable de :
- Valider les opérations métier.
- Vérifier les autorisations.
- Contrôler les conditions nécessaires avant une opération.
- Coordonner plusieurs DAO si nécessaire.
- Gérer les transactions métier.
- Garantir la cohérence des données.

### DAO — Java

Le DAO assure l'accès à la base de données.

Il contient :
- Les requêtes SQL.
- Les opérations INSERT.
- Les opérations UPDATE.
- Les opérations SELECT.
- Les opérations DELETE autorisées.
- La conversion des résultats SQL en objets Model.

### Model — Java

Le Model représente les objets métier de l'application.

Exemples :
- Utilisateur
- Ferme
- Parcelle
- Ouvrier
- Presence
- Recolte
- Client
- Expedition
- Depense
- Salaire

---

# 4. Gestion des espaces et des rôles

ORANGIA comporte quatre espaces de travail :

1. Administrateur
2. Responsable de Ferme
3. Agent RH
4. Comptable

Chaque espace possède ses propres droits.

## 4.1 Administrateur

L'Administrateur est responsable de la configuration générale de l'application.

Ses fonctionnalités :

- Tableau de bord.
- Gestion des utilisateurs et des espaces.
- Gestion des fermes et des parcelles.
- Supervision globale.
- Historique et rapports.
- Paramètres système.

L'Administrateur peut :
- Créer et gérer les comptes.
- Activer et désactiver les utilisateurs.
- Réinitialiser les mots de passe.
- Attribuer un Responsable à une ferme.
- Créer et gérer les fermes et parcelles.
- Consulter les informations globales.
- Consulter les journaux d'activité.
- Configurer les horaires de travail.
- Effectuer des sauvegardes et restaurations.

La supervision globale est en consultation : elle ne donne pas à l'Administrateur le droit de modifier les opérations métier des autres espaces.

## 4.2 Responsable de Ferme

Chaque Responsable est associé à une seule ferme.

Il ne peut consulter et gérer que les données de sa propre ferme.

Ses fonctionnalités :

- Tableau de bord.
- Récoltes.
- Expéditions.
- Présences et demandes.
- Historique.

Il peut :
- Enregistrer les récoltes.
- Consulter le stock disponible de sa ferme.
- Créer et gérer les opérations d'expédition.
- Consulter et gérer les clients nécessaires aux expéditions.
- Saisir les présences des ouvriers.
- Transmettre les présences au service RH.
- Soumettre des demandes de correction.
- Créer des demandes de dépenses.
- Consulter son historique opérationnel.

Il ne peut pas :
- Consulter les prix des expéditions.
- Consulter les montants financiers des expéditions.
- Consulter les paiements ou soldes des clients.
- Confirmer financièrement une expédition.
- Gérer les comptes utilisateurs.
- Gérer directement les ouvriers et leurs affectations.

## 4.3 Agent RH

L'Agent RH est global : il intervient sur toutes les fermes.

Ses fonctionnalités :

- Tableau de bord RH.
- Gestion du personnel.
- Pointage et gestion des salaires.
- Génération des rapports RH.

Il peut :
- Créer et gérer les dossiers des ouvriers.
- Affecter les ouvriers aux fermes.
- Transférer un ouvrier d'une ferme à une autre.
- Conserver l'historique des affectations.
- Consulter et valider les présences transmises.
- Traiter les demandes de correction.
- Gérer les justificatifs.
- Calculer les salaires.
- Enregistrer les paiements des salaires.
- Générer les rapports RH.

Les ouvriers ne possèdent pas de comptes de connexion à l'application.

## 4.4 Comptable

Le Comptable est global : il intervient sur toutes les fermes.

Ses fonctionnalités :

- Tableau de bord.
- Gestion des récoltes et du stock.
- Gestion des clients et suivi commercial.
- Gestion des dépenses et des demandes de dépenses.
- Historique et rapports.

Il peut :
- Consulter les récoltes.
- Consulter le stock disponible.
- Consulter et gérer les clients.
- Confirmer ou refuser les expéditions.
- Gérer les paiements des expéditions.
- Consulter les recettes et les bilans.
- Traiter les demandes de dépenses.
- Enregistrer les dépenses réellement effectuées.
- Générer les rapports financiers.

Il ne peut pas :
- Modifier les récoltes.
- Modifier les quantités opérationnelles des expéditions.
- Créer ou annuler une expédition.
- Gérer les ouvriers et leurs salaires.
- Modifier les informations de présence.

---

# 5. Authentification et navigation

## 5.1 Parcours de connexion obligatoire

Au lancement de l'application, l'utilisateur arrive sur un écran de sélection de l'espace de travail.

```text
                  LANCEMENT ORANGIA
                         |
                         v
              CHOIX DE L'ESPACE
                         |
          +--------------+--------------+
          |              |              |
          v              v              v
    ADMINISTRATEUR  RESPONSABLE       AGENT RH
          |              |              |
          +--------------+--------------+
                         |
                         v
                     COMPTABLE
```

Chaque espace possède son propre écran de connexion.

Le formulaire contient uniquement :
- Identifiant.
- Mot de passe.

Le système vérifie :
1. L'existence du compte.
2. La validité du mot de passe.
3. L'état actif du compte.
4. La correspondance entre le rôle du compte et l'espace sélectionné.

Si un compte appartient à un autre espace, l'accès est refusé avec le message :

`Ce compte n'appartient pas à l'espace sélectionné.`

Après une authentification réussie, l'utilisateur est redirigé vers son interface.

## 5.2 Création des comptes

La création des comptes n'est pas disponible sur l'écran public de connexion.

Seul l'Administrateur peut créer et gérer les comptes depuis :

`Administrateur → Utilisateurs & espaces`

## 5.3 Structure d'authentification

```text
controller/
└── auth/
    ├── WorkspaceSelectionController.java
    └── LoginController.java

service/
└── AuthService.java

dao/
└── UtilisateurDAO.java

model/
└── Utilisateur.java

resources/
└── fxml/
    └── auth/
        ├── WorkspaceSelectionView.fxml
        └── LoginView.fxml
```

---

# 6. Règles métier communes

## 6.1 Gestion des fermes

- Chaque ferme possède ses propres parcelles.
- Chaque Responsable est affecté à une seule ferme.
- RH et Comptable ont une vision globale.
- Les données opérationnelles d'un Responsable sont filtrées selon sa ferme.

## 6.2 Gestion des ouvriers

- Les ouvriers ne disposent pas de comptes utilisateurs.
- RH gère les dossiers des ouvriers.
- RH peut transférer un ouvrier entre fermes.
- Les historiques de présence et de salaire sont conservés après un transfert.
- Cabran et Naïb restent des ouvriers ordinaires dans le système, avec des règles salariales particulières.

## 6.3 Présences

- Le Responsable saisit les présences et les transmet à RH.
- RH valide les fiches et traite les demandes de correction.
- Après transmission, le Responsable ne peut plus modifier directement la fiche.
- Les dimanches et jours de repos ne sont pas considérés comme des absences.
- Les horaires de travail sont configurés par l'Administrateur.
- Chaque présence historique conserve les horaires appliqués au moment du traitement.

## 6.4 Salaires

- RH est responsable du calcul et du paiement des salaires.
- Le salaire journalier d'un ouvrier absent un jour ouvrable est nul pour cette journée.
- Le salaire mensuel fixe n'est pas proratisé sans règle contractuelle explicite.
- Les paiements partiels sont possibles et conservés dans l'historique.
- Le Comptable peut consulter uniquement les coûts salariaux agrégés nécessaires aux bilans.

Règle particulière de Cabran :
- Son bonus est configurable séparément du salaire de base.
- Jusqu'à deux absences injustifiées dans le mois, le bonus est conservé.
- À partir de trois absences injustifiées, le bonus du mois est perdu.
- Les justificatifs médicaux acceptés ne comptent pas comme absences injustifiées.

## 6.5 Récoltes

- Le Responsable enregistre les récoltes.
- Plusieurs récoltes peuvent être enregistrées pour une même parcelle le même jour.
- Une récolte enregistrée et non annulée augmente immédiatement le stock.
- Une récolte annulée reste conservée dans l'historique avec son motif.
- Le stock ne doit jamais devenir négatif.

## 6.6 Expéditions

Le processus est partagé entre Responsable et Comptable.

**Responsable :**
- Crée l'expédition.
- Renseigne les informations opérationnelles.
- Modifie les informations opérationnelles selon les règles.
- Ne voit jamais les informations financières.

**Comptable :**
- Vérifie les informations.
- Confirme ou refuse l'expédition.
- Gère les informations financières et les paiements.

Règles :
- Une expédition non confirmée ne réduit pas le stock.
- Une expédition confirmée réduit le stock.
- L'annulation d'une expédition confirmée restaure le stock.
- Une modification opérationnelle après confirmation impose une nouvelle confirmation comptable.
- Les anciens paiements ne sont pas modifiés automatiquement.
- La confirmation vérifie le stock disponible de manière transactionnelle pour éviter les expéditions supérieures au stock réel.

## 6.7 Dépenses

- Le Responsable soumet une demande de dépense.
- Le Comptable accepte ou refuse la demande avec un commentaire.
- L'acceptation ne crée pas automatiquement une dépense réelle.
- Le Comptable enregistre la dépense après l'achat.
- Une dépense réelle peut être liée à sa demande d'origine.

## 6.8 Stock

Le stock disponible est calculé automatiquement :

`Stock disponible = Total des récoltes valides - Total des expéditions confirmées non annulées`

Le stock est calculé par ferme.

Il n'existe pas de saisie manuelle du stock disponible.

## 6.9 Clients

- Les clients sont partagés entre Responsable et Comptable.
- Ils peuvent être ajoutés, consultés, modifiés, recherchés et désactivés.
- Un client lié à des opérations historiques ne doit pas être supprimé physiquement.

## 6.10 Documents, notifications et historique

- Les documents peuvent être liés à une opération métier.
- Les notifications informent les utilisateurs des actions nécessaires.
- Le journal d'action conserve les opérations importantes.
- Les opérations historiques doivent rester consultables.
- Les journaux ne doivent pas être modifiables par les utilisateurs ordinaires.

---

# 7. Conception de la base de données

La base de données est centralisée dans PostgreSQL.

Elle est conçue autour de 22 tables métier et d'une vue calculée pour le stock.

## 7.1 Tables principales

| N° | Table | Rôle |
|---:|---|---|
| 1 | ROLE | Rôles des utilisateurs |
| 2 | UTILISATEUR | Comptes de connexion |
| 3 | FERME | Exploitations agricoles |
| 4 | PARCELLE | Parcelles des fermes |
| 5 | OUVRIER | Dossiers du personnel |
| 6 | AFFECTATION_OUVRIER | Historique des affectations |
| 7 | TARIF_OUVRIER | Historique des tarifs |
| 8 | FICHE_PRESENCE | Fiches de présence transmises |
| 9 | PRESENCE | Présences quotidiennes |
| 10 | DEMANDE_CORRECTION | Corrections de présence |
| 11 | SALAIRE | Salaires calculés |
| 12 | PAIEMENT_SALAIRE | Paiements des salaires |
| 13 | RECOLTE | Récoltes enregistrées |
| 14 | CLIENT | Clients |
| 15 | EXPEDITION | Expéditions |
| 16 | PAIEMENT_EXPEDITION | Paiements clients |
| 17 | DEMANDE_DEPENSE | Demandes de dépenses |
| 18 | DEPENSE | Dépenses réellement effectuées |
| 19 | DOCUMENT | Documents et pièces jointes |
| 20 | NOTIFICATION | Notifications |
| 21 | JOURNAL_ACTION | Historique des actions |
| 22 | PARAMETRE_SYSTEME | Paramètres de l'application |

## 7.2 Vue calculée

`V_STOCK_FERME`

Cette vue calcule le stock disponible par ferme à partir des récoltes valides et des expéditions confirmées non annulées.

Elle ne remplace pas les tables métier : elle fournit une projection calculée.

## 7.3 Tables et éléments historiques éventuels

Les éléments suivants ne font pas partie des 22 tables du modèle cible :

- EXPEDITION_RECOLTE
- PRODUIT_STOCK
- MOUVEMENT_STOCK
- UTILISATEUR_FERME
- PERMISSION
- ROLE_PERMISSION

Ils doivent être audités avant toute décision de conservation ou de migration. Ils ne doivent pas être supprimés automatiquement.

Le rôle est représenté en Java par une classe `Role` contenant des constantes String. Les autorisations de navigation sont définies statiquement dans l'application. La table `ROLE_PERMISSION` n'est pas utilisée dans le fonctionnement actuel prévu.

---

# 8. Structure globale du projet — MVC

La structure ci-dessous représente l'organisation cible du code source.

```text
orangia/
│
├── pom.xml
├── README.md
├── .gitignore
│
├── docs/
│   ├── architecture.md
│   ├── cahier-des-charges.md
│   ├── conception-bdd.md
│   ├── diagrammes/
│   │   ├── diagramme-classes.png
│   │   ├── diagramme-use-case.png
│   │   ├── diagramme-sequence.png
│   │   └── diagramme-mcd.png
│   └── rapports/
│
├── database/
│   ├── migrations/
│   │   ├── 001_initial_schema.sql
│   │   ├── 002_authentication.sql
│   │   ├── 003_farms_and_parcels.sql
│   │   ├── 004_clients_and_shipments.sql
│   │   ├── 005_human_resources.sql
│   │   ├── 006_harvests.sql
│   │   └── 007_expenses_and_final_constraints.sql
│   │
│   ├── views/
│   │   └── v_stock_ferme.sql
│   │
│   ├── seeds/
│   │   ├── roles.sql
│   │   └── initial_data.sql
│   │
│   └── README.md
│
├── src/
│   │
│   ├── main/
│   │   │
│   │   ├── java/
│   │   │   └── ma/
│   │   │       └── orangia/
│   │   │
│   │   │           ├── MainApp.java
│   │   │           │
│   │   │           ├── config/
│   │   │           │   ├── DatabaseConnection.java
│   │   │           │   ├── AppConfig.java
│   │   │           │   └── SessionManager.java
│   │   │           │
│   │   │           ├── navigation/
│   │   │           │   ├── NavigationManager.java
│   │   │           │   └── Route.java
│   │   │           │
│   │   │           ├── model/
│   │   │           │   ├── Role.java
│   │   │           │   ├── Utilisateur.java
│   │   │           │   ├── Ferme.java
│   │   │           │   ├── Parcelle.java
│   │   │           │   ├── Ouvrier.java
│   │   │           │   ├── AffectationOuvrier.java
│   │   │           │   ├── TarifOuvrier.java
│   │   │           │   ├── FichePresence.java
│   │   │           │   ├── Presence.java
│   │   │           │   ├── DemandeCorrection.java
│   │   │           │   ├── Salaire.java
│   │   │           │   ├── PaiementSalaire.java
│   │   │           │   ├── Recolte.java
│   │   │           │   ├── Client.java
│   │   │           │   ├── Expedition.java
│   │   │           │   ├── PaiementExpedition.java
│   │   │           │   ├── DemandeDepense.java
│   │   │           │   ├── Depense.java
│   │   │           │   ├── Document.java
│   │   │           │   ├── Notification.java
│   │   │           │   ├── JournalAction.java
│   │   │           │   └── ParametreSysteme.java
│   │   │           │
│   │   │           ├── dao/
│   │   │           │   ├── UtilisateurDAO.java
│   │   │           │   ├── FermeDAO.java
│   │   │           │   ├── ParcelleDAO.java
│   │   │           │   ├── OuvrierDAO.java
│   │   │           │   ├── AffectationOuvrierDAO.java
│   │   │           │   ├── TarifOuvrierDAO.java
│   │   │           │   ├── FichePresenceDAO.java
│   │   │           │   ├── PresenceDAO.java
│   │   │           │   ├── DemandeCorrectionDAO.java
│   │   │           │   ├── SalaireDAO.java
│   │   │           │   ├── PaiementSalaireDAO.java
│   │   │           │   ├── RecolteDAO.java
│   │   │           │   ├── ClientDAO.java
│   │   │           │   ├── ExpeditionDAO.java
│   │   │           │   ├── PaiementExpeditionDAO.java
│   │   │           │   ├── DemandeDepenseDAO.java
│   │   │           │   ├── DepenseDAO.java
│   │   │           │   ├── DocumentDAO.java
│   │   │           │   ├── NotificationDAO.java
│   │   │           │   ├── JournalActionDAO.java
│   │   │           │   └── ParametreSystemeDAO.java
│   │   │           │
│   │   │           ├── dao/
│   │   │           │   └── impl/
│   │   │           │       ├── UtilisateurDAOImpl.java
│   │   │           │       ├── FermeDAOImpl.java
│   │   │           │       ├── ParcelleDAOImpl.java
│   │   │           │       ├── OuvrierDAOImpl.java
│   │   │           │       ├── AffectationOuvrierDAOImpl.java
│   │   │           │       ├── TarifOuvrierDAOImpl.java
│   │   │           │       ├── FichePresenceDAOImpl.java
│   │   │           │       ├── PresenceDAOImpl.java
│   │   │           │       ├── DemandeCorrectionDAOImpl.java
│   │   │           │       ├── SalaireDAOImpl.java
│   │   │           │       ├── PaiementSalaireDAOImpl.java
│   │   │           │       ├── RecolteDAOImpl.java
│   │   │           │       ├── ClientDAOImpl.java
│   │   │           │       ├── ExpeditionDAOImpl.java
│   │   │           │       ├── PaiementExpeditionDAOImpl.java
│   │   │           │       ├── DemandeDepenseDAOImpl.java
│   │   │           │       ├── DepenseDAOImpl.java
│   │   │           │       ├── DocumentDAOImpl.java
│   │   │           │       ├── NotificationDAOImpl.java
│   │   │           │       ├── JournalActionDAOImpl.java
│   │   │           │       └── ParametreSystemeDAOImpl.java
│   │   │           │
│   │   │           ├── service/
│   │   │           │   ├── AuthService.java
│   │   │           │   ├── UtilisateurService.java
│   │   │           │   ├── FermeService.java
│   │   │           │   ├── ParcelleService.java
│   │   │           │   ├── OuvrierService.java
│   │   │           │   ├── AffectationOuvrierService.java
│   │   │           │   ├── TarifOuvrierService.java
│   │   │           │   ├── PresenceService.java
│   │   │           │   ├── FichePresenceService.java
│   │   │           │   ├── DemandeCorrectionService.java
│   │   │           │   ├── SalaireService.java
│   │   │           │   ├── RecolteService.java
│   │   │           │   ├── StockService.java
│   │   │           │   ├── ClientService.java
│   │   │           │   ├── ExpeditionService.java
│   │   │           │   ├── PaiementExpeditionService.java
│   │   │           │   ├── DemandeDepenseService.java
│   │   │           │   ├── DepenseService.java
│   │   │           │   ├── DocumentService.java
│   │   │           │   ├── NotificationService.java
│   │   │           │   ├── JournalActionService.java
│   │   │           │   ├── RapportService.java
│   │   │           │   ├── BackupService.java
│   │   │           │   └── ParametreSystemeService.java
│   │   │           │
│   │   │           ├── controller/
│   │   │           │   │
│   │   │           │   ├── auth/
│   │   │           │   │   ├── WorkspaceSelectionController.java
│   │   │           │   │   └── LoginController.java
│   │   │           │   │
│   │   │           │   ├── admin/
│   │   │           │   │   ├── AdminDashboardController.java
│   │   │           │   │   ├── UtilisateursController.java
│   │   │           │   │   ├── FermesController.java
│   │   │           │   │   ├── ParcellesController.java
│   │   │           │   │   ├── SupervisionController.java
│   │   │           │   │   ├── HistoriqueAdminController.java
│   │   │           │   │   └── ParametresSystemeController.java
│   │   │           │   │
│   │   │           │   ├── responsable/
│   │   │           │   │   ├── ResponsableDashboardController.java
│   │   │           │   │   ├── RecoltesController.java
│   │   │           │   │   ├── ExpeditionsController.java
│   │   │           │   │   ├── PresencesResponsableController.java
│   │   │           │   │   ├── DemandesDepenseController.java
│   │   │           │   │   └── HistoriqueResponsableController.java
│   │   │           │   │
│   │   │           │   ├── rh/
│   │   │           │   │   ├── RHDashboardController.java
│   │   │           │   │   ├── GestionPersonnelController.java
│   │   │           │   │   ├── AffectationsController.java
│   │   │           │   │   ├── PresencesRHController.java
│   │   │           │   │   ├── CorrectionsController.java
│   │   │           │   │   ├── SalairesController.java
│   │   │           │   │   └── RapportsRHController.java
│   │   │           │   │
│   │   │           │   ├── comptable/
│   │   │           │   │   ├── ComptableDashboardController.java
│   │   │           │   │   ├── RecoltesStockController.java
│   │   │           │   │   ├── ClientsController.java
│   │   │           │   │   ├── ExpeditionsComptableController.java
│   │   │           │   │   ├── PaiementsExpeditionController.java
│   │   │           │   │   ├── DepensesController.java
│   │   │           │   │   ├── DemandesDepensesController.java
│   │   │           │   │   └── RapportsComptableController.java
│   │   │           │   │
│   │   │           │   └── common/
│   │   │           │       ├── ProfileController.java
│   │   │           │       ├── NotificationsController.java
│   │   │           │       └── DocumentsController.java
│   │   │           │
│   │   │           ├── security/
│   │   │           │   ├── PasswordHasher.java
│   │   │           │   ├── AccessControl.java
│   │   │           │   └── RoleAccess.java
│   │   │           │
│   │   │           ├── validation/
│   │   │           │   ├── UtilisateurValidator.java
│   │   │           │   ├── FermeValidator.java
│   │   │           │   ├── ParcelleValidator.java
│   │   │           │   ├── OuvrierValidator.java
│   │   │           │   ├── PresenceValidator.java
│   │   │           │   ├── RecolteValidator.java
│   │   │           │   ├── ClientValidator.java
│   │   │           │   ├── ExpeditionValidator.java
│   │   │           │   └── DepenseValidator.java
│   │   │           │
│   │   │           ├── util/
│   │   │           │   ├── AlertUtil.java
│   │   │           │   ├── DateUtil.java
│   │   │           │   ├── CurrencyUtil.java
│   │   │           │   ├── FileUtil.java
│   │   │           │   └── PdfUtil.java
│   │   │           │
│   │   │           └── exception/
│   │   │               ├── ValidationException.java
│   │   │               ├── BusinessException.java
│   │   │               └── DataAccessException.java
│   │   │
│   │   └── resources/
│   │       │
│   │       ├── fxml/
│   │       │   │
│   │       │   ├── auth/
│   │       │   │   ├── WorkspaceSelectionView.fxml
│   │       │   │   └── LoginView.fxml
│   │       │   │
│   │       │   ├── admin/
│   │       │   │   ├── AdminDashboardView.fxml
│   │       │   │   ├── UtilisateursView.fxml
│   │       │   │   ├── FermesView.fxml
│   │       │   │   ├── ParcellesView.fxml
│   │       │   │   ├── SupervisionView.fxml
│   │       │   │   ├── HistoriqueAdminView.fxml
│   │       │   │   └── ParametresSystemeView.fxml
│   │       │   │
│   │       │   ├── responsable/
│   │       │   │   ├── ResponsableDashboardView.fxml
│   │       │   │   ├── RecoltesView.fxml
│   │       │   │   ├── ExpeditionsView.fxml
│   │       │   │   ├── PresencesResponsableView.fxml
│   │       │   │   ├── DemandesDepenseView.fxml
│   │       │   │   └── HistoriqueResponsableView.fxml
│   │       │   │
│   │       │   ├── rh/
│   │       │   │   ├── RHDashboardView.fxml
│   │       │   │   ├── GestionPersonnelView.fxml
│   │       │   │   ├── AffectationsView.fxml
│   │       │   │   ├── PresencesRHView.fxml
│   │       │   │   ├── CorrectionsView.fxml
│   │       │   │   ├── SalairesView.fxml
│   │       │   │   └── RapportsRHView.fxml
│   │       │   │
│   │       │   ├── comptable/
│   │       │   │   ├── ComptableDashboardView.fxml
│   │       │   │   ├── RecoltesStockView.fxml
│   │       │   │   ├── ClientsView.fxml
│   │       │   │   ├── ExpeditionsComptableView.fxml
│   │       │   │   ├── PaiementsExpeditionView.fxml
│   │       │   │   ├── DepensesView.fxml
│   │       │   │   ├── DemandesDepensesView.fxml
│   │       │   │   └── RapportsComptableView.fxml
│   │       │   │
│   │       │   └── common/
│   │       │       ├── ProfileView.fxml
│   │       │       ├── NotificationsView.fxml
│   │       │       ├── DocumentsView.fxml
│   │       │       └── components/
│   │       │           ├── Sidebar.fxml
│   │       │           ├── Header.fxml
│   │       │           ├── ConfirmDialog.fxml
│   │       │           └── EmptyState.fxml
│   │       │
│   │       ├── css/
│   │       │   ├── global.css
│   │       │   ├── theme.css
│   │       │   ├── auth.css
│   │       │   ├── dashboard.css
│   │       │   ├── tables.css
│   │       │   ├── forms.css
│   │       │   ├── dialogs.css
│   │       │   └── components/
│   │       │       ├── sidebar.css
│   │       │       ├── header.css
│   │       │       └── buttons.css
│   │       │
│   │       ├── images/
│   │       │   ├── logo/
│   │       │   ├── icons/
│   │       │   └── backgrounds/
│   │       │
│   │       └── reports/
│   │
│   └── test/
│       └── java/
│           └── ma/
│               └── orangia/
│                   ├── security/
│                   │   └── PasswordHasherTest.java
│                   ├── service/
│                   │   ├── AuthServiceTest.java
│                   │   ├── FermeServiceTest.java
│                   │   ├── OuvrierServiceTest.java
│                   │   ├── PresenceServiceTest.java
│                   │   ├── RecolteServiceTest.java
│                   │   ├── ExpeditionServiceTest.java
│                   │   ├── SalaireServiceTest.java
│                   │   └── DepenseServiceTest.java
│                   ├── validation/
│                   │   ├── FermeValidatorTest.java
│                   │   ├── ParcelleValidatorTest.java
│                   │   ├── OuvrierValidatorTest.java
│                   │   ├── PresenceValidatorTest.java
│                   │   ├── RecolteValidatorTest.java
│                   │   ├── ClientValidatorTest.java
│                   │   └── ExpeditionValidatorTest.java
│                   └── integration/
│                       ├── DatabaseConnectionTest.java
│                       ├── DatabaseCrudTest.java
│                       └── FxmlLoadingTest.java
│
└── target/
```

**Remarques sur cette arborescence :**

- Les interfaces FXML ont leurs Controllers correspondants.
- Les classes DAO sont séparées de leurs implémentations JDBC.
- Les Services contiennent les règles métier.
- Les fichiers de tests suivent les couches et fonctionnalités.
- `target/` est généré par Maven et ne doit pas être versionné.
- Les dossiers vides peuvent être conservés comme emplacements réservés, sans créer artificiellement toutes les classes.
- Les noms exacts peuvent être ajustés lors de l'implémentation, mais toute modification doit rester cohérente entre les packages, les fichiers FXML et les Controllers.

---

# 9. Organisation détaillée des fonctionnalités par espace

## 9.1 Espace Administrateur

| Fonctionnalité | Controller | View |
|---|---|---|
| Tableau de bord | AdminDashboardController | AdminDashboardView.fxml |
| Utilisateurs et espaces | UtilisateursController | UtilisateursView.fxml |
| Fermes | FermesController | FermesView.fxml |
| Parcelles | ParcellesController | ParcellesView.fxml |
| Supervision globale | SupervisionController | SupervisionView.fxml |
| Historique et rapports | HistoriqueAdminController | HistoriqueAdminView.fxml |
| Paramètres système | ParametresSystemeController | ParametresSystemeView.fxml |

Le menu principal comporte exactement six entrées :
1. Tableau de bord
2. Utilisateurs & espaces
3. Fermes & parcelles
4. Supervision globale
5. Historique & rapports
6. Paramètres système

Les éléments Profil, Notifications et Déconnexion sont accessibles dans les éléments communs de l'interface.

## 9.2 Espace Responsable

| Fonctionnalité | Controller | View |
|---|---|---|
| Tableau de bord | ResponsableDashboardController | ResponsableDashboardView.fxml |
| Récoltes | RecoltesController | RecoltesView.fxml |
| Expéditions | ExpeditionsController | ExpeditionsView.fxml |
| Présences et demandes | PresencesResponsableController | PresencesResponsableView.fxml |
| Demandes de dépenses | DemandesDepenseController | DemandesDepenseView.fxml |
| Historique | HistoriqueResponsableController | HistoriqueResponsableView.fxml |

Les informations clients nécessaires sont intégrées au module Expéditions.

Le tableau de bord comporte cinq indicateurs :
- Récoltes aujourd'hui.
- Expéditions aujourd'hui.
- Stock disponible.
- Présences envoyées.
- Demandes en attente.

Le menu principal conserve les cinq rubriques :
1. Tableau de bord
2. Récoltes
3. Expéditions
4. Présences & Demandes
5. Historique

Les demandes de dépenses sont intégrées dans la rubrique Présences & Demandes, sans ajouter une sixième entrée principale. Le module peut utiliser des onglets internes pour distinguer les deux catégories.

## 9.3 Espace RH

| Fonctionnalité | Controller | View |
|---|---|---|
| Tableau de bord RH | RHDashboardController | RHDashboardView.fxml |
| Gestion du personnel | GestionPersonnelController | GestionPersonnelView.fxml |
| Affectations | AffectationsController | AffectationsView.fxml |
| Présences | PresencesRHController | PresencesRHView.fxml |
| Corrections | CorrectionsController | CorrectionsView.fxml |
| Salaires | SalairesController | SalairesView.fxml |
| Rapports RH | RapportsRHController | RapportsRHView.fxml |

Les affectations, corrections et salaires sont des sous-fonctionnalités des quatre rubriques RH convenues. Elles ne doivent pas devenir des espaces indépendants.

## 9.4 Espace Comptable

| Fonctionnalité | Controller | View |
|---|---|---|
| Tableau de bord | ComptableDashboardController | ComptableDashboardView.fxml |
| Récoltes et stock | RecoltesStockController | RecoltesStockView.fxml |
| Clients | ClientsController | ClientsView.fxml |
| Expéditions financières | ExpeditionsComptableController | ExpeditionsComptableView.fxml |
| Paiements | PaiementsExpeditionController | PaiementsExpeditionView.fxml |
| Dépenses | DepensesController | DepensesView.fxml |
| Demandes de dépenses | DemandesDepensesController | DemandesDepensesView.fxml |
| Rapports | RapportsComptableController | RapportsComptableView.fxml |

Les paiements et les demandes de dépenses sont des sous-fonctionnalités des rubriques comptables convenues. Le menu principal conserve cinq rubriques.

---

# 10. Sécurité et contrôle d'accès

La sécurité est appliquée dans les Services et dans les requêtes DAO, pas uniquement dans l'interface.

## 10.1 Gestion des mots de passe

- Les mots de passe ne sont jamais enregistrés en clair.
- Utilisation de PBKDF2 pour le hachage.
- Les mots de passe ne sont jamais affichés dans les journaux.
- La réinitialisation est réservée à l'Administrateur.

## 10.2 Contrôle des rôles

La classe `Role.java` contient les constantes des rôles :

- ADMINISTRATEUR
- RESPONSABLE
- RH
- COMPTABLE

La classe `AccessControl.java` contrôle les droits d'accès.

La classe `RoleAccess.java` définit les menus et fonctionnalités autorisés par rôle.

## 10.3 Isolation des données

- Le Responsable ne peut accéder qu'à sa propre ferme.
- RH et Comptable peuvent accéder aux données globales autorisées.
- Le Responsable ne peut pas récupérer les informations financières des expéditions, même en contournant l'interface.
- Les DAO doivent proposer des requêtes adaptées aux droits de chaque espace.

## 10.4 Profil utilisateur

Les utilisateurs peuvent consulter leur profil.

Seul l'Administrateur peut gérer les informations de compte et les rôles des autres utilisateurs. Les autres rôles ne peuvent pas modifier leurs propres informations de compte depuis leur profil.

---

# 11. Gestion des erreurs et validation

Chaque fonctionnalité doit disposer de validations adaptées.

Les validations sont effectuées principalement dans les classes `Validator` et les Services.

Exemples :
- Champs obligatoires.
- Formats des données.
- Valeurs numériques positives.
- Dates cohérentes.
- Contrôle des doublons.
- Contrôle du stock disponible.
- Contrôle des droits.
- Vérification des statuts avant modification.

Les messages destinés à l'utilisateur doivent être clairs et compréhensibles.

La classe `AlertUtil.java` centralise les alertes et les dialogues standards.

---

# 12. Tests et qualité du code

Chaque fonctionnalité doit être testée avant son intégration.

## 12.1 Tests unitaires

Les tests unitaires couvrent :
- Les validations.
- Les règles métier des Services.
- Les droits d'accès.
- Les calculs.
- Les changements de statut.
- Les cas d'erreur.

## 12.2 Tests d'intégration

Les tests d'intégration couvrent :
- La connexion PostgreSQL.
- Les opérations CRUD.
- Les contraintes de la base de données.
- Les transactions.
- Les relations entre tables.
- Le chargement des interfaces FXML.

## 12.3 Règles de test

- Utiliser une base de test séparée de la base de production.
- Ne jamais exécuter des tests destructifs sur la base de production.
- Tester les scénarios normaux et les scénarios d'erreur.
- Vérifier les effets d'une opération sur les autres fonctionnalités.
- Exécuter les tests avant chaque Pull Request.

---

# 13. Gestion des migrations de la base de données

Toutes les modifications de la base doivent être versionnées.

Chaque migration doit :
- Avoir un numéro unique.
- Avoir un nom explicite.
- Être documentée.
- Être testée.
- Préserver les données historiques.
- Être réexécutable sans provoquer de doublons lorsque cela est nécessaire.

Les noms des fichiers de migration présentés dans l'arborescence représentent le plan cible. Ils doivent être réconciliés avec l'état réel des fichiers et de la base avant exécution.

**Important :** la présence d'un fichier SQL vide ne signifie pas que la migration a été exécutée. Il faut vérifier séparément le contenu des fichiers et l'état réel de PostgreSQL.

Aucune migration ne doit supprimer une table ou des données historiques sans validation préalable.

---

# 14. Organisation du développement en équipe

Le développement suit obligatoirement le principe :

**ESPACE PAR ESPACE → FONCTIONNALITÉ PAR FONCTIONNALITÉ.**

Chaque membre prend en charge une fonctionnalité complète de bout en bout :

- Base de données et migrations nécessaires.
- Model.
- DAO et implémentation.
- Service.
- Controller.
- FXML.
- CSS.
- Tests.

Le travail ne doit pas être divisé en équipes séparées Frontend, Backend et Database.

## 14.1 Répartition des phases

| Phase | Travail |
|---|---|
| Phase 0 | Fondations techniques et authentification |
| Phase 1 | Espace Administrateur |
| Phase 2 | Espace Responsable |
| Phase 3 | Espace RH |
| Phase 4 | Espace Comptable |
| Phase 5 | Intégration globale et tests finaux |

Les dépendances entre fonctionnalités doivent être respectées.

Une fonctionnalité doit être terminée, testée et intégrée avant de passer à la suivante lorsque cela est nécessaire.

## 14.2 Règle de validation

Une fonctionnalité est considérée comme terminée lorsque :

1. Sa structure de base de données est correcte.
2. Son Model est opérationnel.
3. Son DAO fonctionne.
4. Ses règles métier sont implémentées dans le Service.
5. Son Controller fonctionne.
6. Son interface FXML est intégrée.
7. Son CSS est cohérent avec le design global.
8. Ses tests passent.
9. Elle ne casse pas les fonctionnalités existantes.
10. Son code est prêt à être intégré dans la branche principale.

---

# 15. Git et GitHub

GitHub est utilisé pour centraliser le code et suivre le travail de chaque membre.

## 15.1 Branches

- `main` : version stable du projet.
- `dev` : branche de développement et d'intégration.
- Branches `feature/...` : développement des fonctionnalités.
- Branches `fix/...` : correction des bugs.

Exemples :

```text
main
│
└── dev
    │
    ├── feature/authentication
    ├── feature/admin-fermes
    ├── feature/admin-utilisateurs
    ├── feature/responsable-recoltes
    ├── feature/responsable-expeditions
    ├── feature/rh-personnel
    ├── feature/rh-presences
    └── feature/comptable-depenses
```

## 15.2 Règles de collaboration

- Chaque membre travaille sur sa branche.
- Chaque fonctionnalité est développée dans une branche dédiée.
- Les changements sont commités régulièrement.
- Les membres récupèrent les dernières modifications de `dev`.
- Les Pull Requests sont vérifiées avant fusion.
- La branche `main` reste stable.
- Aucun membre ne doit écraser le travail d'un autre.

## 15.3 Convention des commits

Exemples :

```text
feat(auth): add workspace selection
feat(admin): implement farm management
feat(rh): add attendance validation
feat(responsable): implement harvest management
fix(stock): prevent negative stock
test(recolte): add service tests
docs(architecture): update project structure
```

---

# 16. Principes fondamentaux du projet

Les règles suivantes doivent être respectées pendant toute la durée du développement :

1. Respecter l'architecture MVC + Service + DAO.
2. Ne jamais placer de requêtes SQL dans les Controllers.
3. Ne jamais placer les règles métier principales dans les Controllers.
4. Centraliser les validations et les règles métier dans les Services.
5. Respecter les autorisations de chaque espace.
6. Isoler les données de chaque ferme pour les Responsables.
7. Ne jamais exposer les informations financières au Responsable.
8. Préserver les historiques des opérations.
9. Ne jamais supprimer physiquement les données ayant un historique métier important.
10. Ne jamais modifier automatiquement les anciens paiements lors d'une modification opérationnelle.
11. Éviter les fonctionnalités inutiles et les complications qui ne répondent pas au cahier des charges.
12. Conserver une interface cohérente entre les quatre espaces.
13. Tester chaque fonctionnalité avant son intégration.
14. Ne jamais écraser le travail déjà réalisé par un autre membre.
15. Documenter les modifications importantes de l'architecture et de la base de données.

---

# Conclusion

Cette architecture constitue la référence technique et organisationnelle du projet ORANGIA.

Elle définit les couches de développement, la structure des packages, les fichiers principaux, les responsabilités des quatre espaces, les règles métier, la base de données, les tests et la collaboration GitHub.

Toute nouvelle fonctionnalité doit respecter cette organisation et les décisions validées dans le cahier des charges.

**L'objectif est de construire une application cohérente, sécurisée, maintenable et professionnelle, sans complexité inutile.**