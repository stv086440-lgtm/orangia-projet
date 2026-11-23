# ORANGIA — PLANIFICATION DÉTAILLÉE DU DÉVELOPPEMENT

**Version : 3.4 — Novembre 2026**  
**Projet : ORANGIA — Application de gestion de ferme agricole**  
**Équipe : 6 personnes (1 Chef de projet + 5 développeurs)**  
**Méthode : Développement par Sprints**  
**Architecture : MVC + DAO + Service**  
**Technologies : Java 21, JavaFX 21, FXML, CSS, Maven 3.9.x, JUnit 5, PostgreSQL, JDBC, GitHub**

---

# 1. ORGANISATION DE L'ÉQUIPE

## Membre 1 — Chef de projet

**Rôle : Coordination, supervision et intégration générale.**

Responsabilités :
- Organiser et planifier les Sprints.
- Répartir les tâches entre les cinq développeurs.
- Suivre l'avancement de chaque membre.
- Gérer le dépôt GitHub et les branches.
- Vérifier les Pull Requests.
- Contrôler le respect de l'architecture.
- Vérifier la cohérence entre les différents modules.
- Organiser les réunions et les revues de code.
- Identifier et résoudre les conflits d'intégration.
- Organiser les tests globaux.
- Valider les livrables de chaque Sprint.
- Maintenir la documentation du projet.
- creation data base et configuration . 
- creation la strecture de projet (folder) .
- Configurer le `pom.xml` : `maven.compiler.release` à 21, dépendances (javafx-controls, javafx-fxml, postgresql, junit-jupiter), plugins (maven-compiler-plugin, javafx-maven-plugin, maven-surefire-plugin), versions centralisées dans les propriétés Maven.
- Gérer les migrations de base de données : numéro unique, nom explicite, description, date, objectif, procédure de vérification, registre des migrations réellement exécutées, aucune modification d'une migration déjà appliquée, test sur la base `orangia_test` avant la base principale.
- Préparer l'environnement de test (base `orangia_test`, port 5433) et vérifier que les tests ne se connectent jamais à la base principale.
- Attribuer les fichiers des packages communs `exception/`, `validation/` et `util/` (voir Sprint 1).

**Participation au développement :**

Le Membre 1 ne prend en charge aucun développement de fonctionnalités et ne possède aucun fichier Java, FXML ou CSS à coder, à l'exception de deux fichiers de fondation sans logique métier dans le package `exception/` : `BusinessException.java` et `DatabaseException.java`.

Il intervient uniquement dans la coordination, la vérification, la revue, les tests communs et l'intégration.

## Membre 2 — Authentification, Sécurité et Administration

Responsable de :
- Authentification des utilisateurs.
- Authentification spécifique de l'Administrateur.
- Gestion des utilisateurs.
- Gestion des rôles et autorisations.
- Gestion des sessions.
- Sécurité des mots de passe.
- Code alphanumérique de l'Administrateur.
- Paramètres système.
- Journalisation des actions.
- Sauvegarde et restauration de la base de données (optionnel : Sprint 7 ou Version 2.0).
- Supervision administrative.
- Protection contre les tentatives de connexion répétées.
- Réinitialisation des mots de passe et du code alphanumérique (sans jamais pouvoir les consulter).
- Consultation du profil de chaque utilisateur.

## Membre 3 — Gestion des Fermes et Parcelles

Responsable de :
- Gestion des fermes.
- Gestion des parcelles.
- Informations agricoles.
- Affectation des parcelles aux fermes.
- Consultation des informations des fermes.
- Désactivation sécurisée des fermes et parcelles.
- Respect des relations entre les fermes et les autres modules.
- Attribution d'un Responsable principal actif unique à chaque ferme (en coordination avec le Membre 2).

## Membre 4 — Gestion des Ressources Humaines

Responsable de :
- Gestion des ouvriers.
- Affectation des ouvriers aux fermes.
- Transfert des ouvriers entre fermes.
- Historique des affectations.
- Gestion des tarifs et rémunérations.
- Fiches de présence.
- Présences et absences.
- Demandes de correction.
- Justifications.
- Calcul des salaires.
- Gestion des paiements de salaires.
- Règles particulières de rémunération.
- Rapports RH.
- Réception et traitement des demandes de correction soumises par le Responsable de Ferme.
- Fourniture des coûts salariaux agrégés au Comptable (lecture agrégée uniquement).

## Membre 5 — Production et Gestion des Expéditions

Responsable de :
- Gestion des récoltes.
- Annulation et correction des récoltes selon les règles définies.
- Calcul du stock disponible.
- Gestion opérationnelle des expéditions.
- Suivi du cycle de vie des expéditions.
- Transmission des expéditions au Comptable.
- Gestion des confirmations, refus et annulations dans le respect des permissions.
- Consultation des informations opérationnelles.
- Espace Responsable de Ferme.
- Soumission des demandes de correction de présence (via le CorrectionService du Membre 4).
- Gestion des informations opérationnelles des clients (via le ClientService du Membre 6, sans accès aux montants financiers).
- Rattachement des bons de livraison aux expéditions (via le DocumentService du Membre 6).
- Lecture du stock disponible via la vue `V_STOCK_FERME`.

**Important :** le Membre 5 est le propriétaire technique du module Expédition, y compris ses opérations de confirmation effectuées par le Comptable. Les autorisations du Comptable restent obligatoirement contrôlées par les Services.

## Membre 6 — Comptabilité, Clients et Services communs

Responsable de :
- Gestion des clients.
- Gestion des demandes de dépenses.
- Acceptation et refus des demandes de dépenses.
- Gestion des dépenses réelles.
- Gestion des paiements des expéditions.
- Consultation des informations financières.
- Rapports financiers.
- Dashboard Comptable.
- Services de documents et notifications.
- Composants communs nécessaires à la partie Comptabilité.
- Consultation des récoltes et du stock en lecture seule.
- Consultation des coûts salariaux agrégés.
- Remboursements et annulations financières des paiements d'expéditions.

---

# 2. STRUCTURE GÉNÉRALE DU PROJET

Tous les membres doivent respecter la structure officielle existante du projet.

Le package principal reste `com.orangia`.

```text
src/
└── main/
    ├── java/
    │   └── com/orangia/
    │       ├── Main.java
    │       ├── model/
    │       ├── dao/
    │       │   ├── interfaces/
    │       │   ├── impl/
    │       │   └── base/
    │       ├── service/
    │       ├── security/
    │       ├── controller/
    │       ├── validation/
    │       ├── database/
    │       ├── exception/
    │       └── util/
    │
    └── resources/
        ├── com/orangia/
        │   ├── view/        (fichiers FXML)
        │   ├── css/
        │   ├── images/
        │   └── fonts/
        └── database/
            └── migrations/

src/test/java/com/orangia/
├── dao/
├── service/
├── security/
├── validation/
└── integration/

docs/
├── architecture.md
├── roles&permissions.md
├── planning.md
├── DB-designe.md
└── ...

pom.xml
README.md
.gitignore
```

**Règles importantes :**
- Le package principal reste `com.orangia`.
- Aucun membre ne crée un deuxième package principal.
- Les Models et DAO Interfaces sont centralisés dans les dossiers communs.
- Chaque membre travaille uniquement sur les fichiers qui lui sont attribués.
- Les fichiers déjà présents dans le projet doivent être complétés ou corrigés, pas dupliqués.
- Les fichiers partagés doivent être validés avant modification.
- La configuration PostgreSQL et la connexion JDBC sont déjà préparées : elles ne constituent pas une nouvelle tâche de développement.

## Conventions de nommage

| Élément | Convention | Exemple |
|---|---|---|
| Classes Java | PascalCase | `OuvrierService` |
| Méthodes | camelCase | `ajouterOuvrier()` |
| Variables | camelCase | `nomOuvrier` |
| Interfaces DAO | Nom + DAO | `OuvrierDAO` |
| Implémentations DAO | Nom + DAO + Impl | `OuvrierDAOImpl` |
| Fichiers FXML | PascalCase + View (convention utilisée dans ce planning) | `OuvriersView.fxml` |
| Fichiers CSS | kebab-case | `dashboard.css` |

**Remarque :** l'Architecture indique le kebab-case pour les fichiers FXML (ex. `liste-ouvriers.fxml`). Le Chef de projet doit trancher entre les deux conventions avant le Sprint 6 et aligner les deux documents.

## Configuration technique et environnements

**pom.xml (Membre 1)**
- Propriété de compilation : `<maven.compiler.release>21</maven.compiler.release>`.
- Dépendances : javafx-controls, javafx-fxml, postgresql, junit-jupiter.
- Plugins : maven-compiler-plugin, javafx-maven-plugin, maven-surefire-plugin.
- Versions (Java 21, JavaFX 21, JUnit 5, Maven 3.9.x) centralisées dans les propriétés Maven.

**Environnements de base de données**

| Environnement | Base | Port |
|---|---|---|
| Principal / développement | `orangia` | 5432 |
| Tests | `orangia_test` | 5433 |

- Les identifiants de connexion sont configurés séparément pour chaque environnement.
- Aucun identifiant n'est écrit dans le code ; les fichiers de configuration contenant des secrets ne sont pas versionnés.
- Les tests ne se connectent jamais à la base principale par défaut.

**Règles techniques JDBC et JavaFX**
- Les requêtes JDBC et les opérations longues ne s'exécutent jamais sur le JavaFX Application Thread : utiliser `Task`, un Service JavaFX ou un `ExecutorService`.
- Les connexions JDBC sont fermées correctement.
- Les transactions gèrent explicitement commit et rollback.

---

# 3. SPRINT 1 — MODELS + DAO INTERFACES

**Durée estimée : 2 à 3 jours** 

## Objectif

Préparer la structure métier de l'application en créant ou complétant les Models et les DAO Interfaces.

Pendant ce Sprint :
- Définir les attributs des entités.
- Définir les constructeurs nécessaires.
- Préparer les getters et setters.
- Définir les relations entre les entités.
- Définir les méthodes des DAO Interfaces.
- Vérifier la cohérence avec la conception de la base de données.

**Interdictions pendant ce Sprint :**
- Pas de DAOImpl.
- Pas de requêtes JDBC dans les DAO Interfaces.
- Pas de Services.
- Pas de Controllers.
- Pas de FXML.
- Pas de CSS.
- Pas de logique SQL dans les Models.

## Membre 2 — Authentification et Administration    (  moncif  )

### Models

```text
src/main/java/com/orangia/model/
├── Role.java
├── Utilisateur.java
├── ParametreSysteme.java
└── JournalAction.java
```

**Role.java**
- Définir les quatre rôles :
  - ADMIN
  - RESPONSABLE
  - RH
  - COMPTABLE
- Les rôles et permissions sont définis statiquement dans Java.
- Ne pas créer un système dynamique de permissions en base de données sans validation de l'architecture.

**Utilisateur.java**
- idUtilisateur.
- identifiant.
- nom.
- prénom.
- email.
- mot de passe hashé.
- rôle.
- statut du compte.
- ferme associée pour le Responsable uniquement, selon le modèle validé.
- hash du code alphanumérique (codeAccesHash) : obligatoire pour le rôle ADMIN, NULL pour les autres rôles, jamais enregistré en clair.

**ParametreSysteme.java**
- idParametre.
- nom du paramètre.
- valeur.
- date de modification.
- utilisateur ayant effectué la modification.

**JournalAction.java**
- idAction.
- utilisateur concerné.
- type d'action.
- entité concernée.
- identifiant de l'entité.
- date de l'action.
- description.
- module concerné.
- anciennes valeurs et nouvelles valeurs lorsque nécessaire.
- résultat de l'action.
- motif lorsque nécessaire.

### DAO Interfaces

```text
src/main/java/com/orangia/dao/interfaces/
├── UtilisateurDAO.java
├── ParametreSystemeDAO.java
└── JournalActionDAO.java
```

Méthodes à prévoir :

**UtilisateurDAO**
- findById()
- findByIdentifiant()
- findAll()
- save()
- update()
- deactivate()
- updatePasswordHash()
- updateCodeAccesHash()
- findResponsableActifByFermeId()
- assignFerme()

**ParametreSystemeDAO**
- findByName()
- findAll()
- save()
- update()

**JournalActionDAO**
- save()
- findAll()
- findByUtilisateur()
- findByDateRange()

**Important :** aucun RoleDAO n'est nécessaire si les rôles restent définis statiquement dans Java. `Role.java` est conservé ; `RoleDAO.java`, `RoleDAOImpl.java` et `RoleService.java` ne sont pas créés.

**Migration liée à l'authentification Administrateur :** le champ `code_acces_hash` doit exister dans la table UTILISATEUR (obligatoire pour le compte Administrateur, NULL pour les autres rôles). Si le champ n'existe pas déjà, la migration est préparée avec le Chef de projet, testée sur `orangia_test`, puis appliquée sur la base principale. Le compte Administrateur existant reçoit son code selon une procédure d'initialisation contrôlée.

## Membre 3 — Gestion des fermes et parcelles ( imanee & fatine )

### Models

```text
src/main/java/com/orangia/model/
├── Ferme.java
└── Parcelle.java
```

**Ferme.java**
- idFerme.
- code.
- nom.
- localisation.
- superficie.
- statut.

**Parcelle.java**
- idParcelle.
- code.
- nom.
- superficie.
- variété d'orange.
- nombre d'arbres.
- année de plantation.
- ferme associée.

### DAO Interfaces

```text
src/main/java/com/orangia/dao/interfaces/
├── FermeDAO.java
└── ParcelleDAO.java
```

Méthodes à prévoir :

**FermeDAO**
- findById()
- findAll()
- save()
- update()
- deactivate()

**ParcelleDAO**
- findById()
- findAll()
- findByFermeId()
- save()
- update()
- deactivate()

## Membre 4 — Ressources Humaines   ( imanee & fatine )

### Models

```text
src/main/java/com/orangia/model/
├── Ouvrier.java
├── AffectationOuvrier.java
├── TarifOuvrier.java
├── FichePresence.java
├── Presence.java
├── DemandeCorrection.java
├── Salaire.java
└── PaiementSalaire.java
```

**Ouvrier.java**
- Informations personnelles.
- Informations professionnelles.
- Date d'embauche.
- Statut.
- Informations nécessaires à la rémunération.

**AffectationOuvrier.java**
- Ouvrier concerné.
- Ferme concernée.
- Date de début.
- Date de fin.
- Historique des transferts.

**TarifOuvrier.java**
- Ouvrier concerné.
- Type de rémunération.
- Montant.
- Date de début d'application.
- Date de fin d'application.

**FichePresence.java**
- Date de la fiche.
- Ferme concernée.
- Responsable concerné.
- Statut de validation : BROUILLON, TRANSMISE, EN_TRAITEMENT, VALIDEE, RETOURNEE.
- Horaires applicables.

**Presence.java**
- Ouvrier concerné.
- Fiche de présence.
- Heure d'arrivée.
- Heure de départ.
- Statut de présence.
- Observation.

**DemandeCorrection.java**
- Présence concernée.
- Motif.
- Justification.
- Document justificatif éventuel.
- Demandeur (Responsable à l'origine de la demande).
- Statut.
- Décision RH.

**Salaire.java**
- Ouvrier.
- Période de calcul.
- Montant calculé.
- Détails du calcul.
- Statut.
- Valeurs utilisées pour le calcul.

**PaiementSalaire.java**
- Salaire concerné.
- Date du paiement.
- Montant payé.
- Mode de paiement.
- Référence éventuelle.

### DAO Interfaces

```text
src/main/java/com/orangia/dao/interfaces/
├── OuvrierDAO.java
├── AffectationOuvrierDAO.java
├── TarifOuvrierDAO.java
├── FichePresenceDAO.java
├── PresenceDAO.java
├── DemandeCorrectionDAO.java
├── SalaireDAO.java
└── PaiementSalaireDAO.java
```

Méthodes à prévoir :
- findById()
- findAll()
- findByFermeId()
- findByOuvrierId()
- save()
- update()
- findHistorique()
- findByPeriod()
- findFicheActiveByFermeAndDate() (FichePresenceDAO)
- findCoutAgregeParPeriode() (SalaireDAO, lecture agrégée pour le Comptable)

## Membre 5 — Production et Expéditions     (idriss & bassma )

### Models

```text
src/main/java/com/orangia/model/
├── Recolte.java
└── Expedition.java
```

**Recolte.java**
- idRecolte.
- parcelle concernée.
- date de récolte.
- quantité récoltée.
- statut.
- motif d'annulation.
- utilisateur créateur.

**Expedition.java**
- idExpedition.
- ferme concernée.
- client concerné.
- quantité physique.
- date.
- statut du cycle de vie : BROUILLON, EN_ATTENTE_CONFIRMATION, CONFIRMEE, REFUSEE, REVISION_EN_ATTENTE, ANNULATION_EN_ATTENTE, ANNULEE (statut unique aligné sur l'Architecture, à valider avec le schéma PostgreSQL existant ; il remplace le statut opérationnel et le statut de confirmation séparés).
- prix unitaire nullable.
- quantité facturée nullable.
- montant total calculé.
- informations de confirmation.
- informations de refus.
- informations de modification et d'annulation.
- utilisateur créateur.
- dates de création et de modification.
- observation.
- bon de livraison (référence vers un Document).
- historique des versions : l'ancienne version confirmée reste consultable après une révision.

### DAO Interfaces

```text
src/main/java/com/orangia/dao/interfaces/
├── RecolteDAO.java
├── ExpeditionDAO.java
└── StockDAO.java
```

Méthodes à prévoir :

**RecolteDAO**
- findById()
- findAll()
- findByFermeId()
- findByParcelleId()
- findByPeriod()
- save()
- update()
- cancel()

**ExpeditionDAO**
- findById()
- findAll()
- findByFermeId()
- findByClientId()
- findByStatus()
- findPendingConfirmation()
- save()
- update()
- cancel()
- findVersionsByExpeditionId()

**StockDAO**
- findStockByFermeId() (lecture de la vue `V_STOCK_FERME`)
- findAllStock()

**Vue V_STOCK_FERME :** le Membre 5 prépare le script de la vue (migration de vues, ex. `003_views.sql`, dans le dossier de migrations retenu). La vue calcule le stock par ferme à partir des récoltes valides et des quantités physiques des expéditions confirmées non annulées. Elle ne remplace pas les contrôles transactionnels réalisés pendant les opérations. Le Chef de projet valide la migration.

**Important :** le Membre 5 est le propriétaire unique de `Expedition.java` et `ExpeditionDAO.java`. Les autres membres ne doivent pas créer de versions concurrentes.

## Membre 6 — Comptabilité et services communs  (idriss & bassma )

### Models

```text
src/main/java/com/orangia/model/
├── Client.java
├── PaiementExpedition.java
├── DemandeDepense.java
├── Depense.java
├── Document.java
└── Notification.java
```

**Client.java**
- idClient.
- nom ou raison sociale.
- téléphone.
- adresse.
- ville.
- notes.
- statut.

**PaiementExpedition.java**
- idPaiement.
- expédition concernée.
- type d'opération : PAIEMENT, REMBOURSEMENT ou ANNULATION_FINANCIERE.
- date du paiement.
- montant.
- mode de paiement.
- référence.

**DemandeDepense.java**
- idDemande.
- ferme concernée.
- responsable demandeur.
- motif.
- montant estimé.
- statut de décision.
- commentaire du Comptable.
- date de demande.
- date de décision.

**Depense.java**
- idDepense.
- catégorie.
- ferme concernée.
- montant réel.
- date.
- description.
- justificatif éventuel.
- utilisateur ayant enregistré la dépense.
- demande de dépense liée (nullable).

**Document.java**
- idDocument.
- type de document.
- nom.
- chemin ou référence de stockage.
- date de création.
- utilisateur créateur.
- entité associée (type et identifiant) : expédition, fiche de présence, demande de correction, demande de dépense ou dépense réelle.

**Notification.java**
- idNotification.
- destinataire.
- type.
- message.
- date.
- statut de lecture.

### DAO Interfaces

```text
src/main/java/com/orangia/dao/interfaces/
├── ClientDAO.java
├── PaiementExpeditionDAO.java
├── DemandeDepenseDAO.java
├── DepenseDAO.java
├── DocumentDAO.java
└── NotificationDAO.java
```

Méthodes à prévoir :
- findById()
- findAll()
- findByFermeId()
- findByClientId()
- save()
- update()
- findByPeriod()
- findByEntite() (DocumentDAO)
- findByExpeditionId() (PaiementExpeditionDAO)
- findByDemandeId() (DepenseDAO)

## Composants transverses (exceptions, validation, utilitaires)

Les packages `exception/`, `validation/` et `util/` sont communs à tous les membres. Chaque fichier a un propriétaire unique. L'attribution ci-dessous est proposée et doit être validée par le Chef de projet avant le début du Sprint 1.

| Package | Fichiers | Propriétaire | Échéance |
|---|---|---|---|
| exception/ | BusinessException, DatabaseException | Membre 1 (Chef de projet) | Sprint 1, avant le Sprint 2 |
| exception/ | AuthenticationException, AuthorizationException | Membre 2 | Sprint 1, avant le Sprint 2 |
| validation/ | InputValidator, LoginValidator | Membre 2 | Sprint 3 |
| validation/ | FermeValidator, ParcelleValidator | Membre 3 | Sprint 3 |
| validation/ | OuvrierValidator, PresenceValidator, SalaireValidator | Membre 4 | Sprint 3 |
| validation/ | FormValidator | Membre 6 | Sprint 3 |
| util/ | DateUtils, CurrencyUtils, AlertUtil | Membre 6 | Sprint 3 |
| util/ | PdfGenerator | Membre 6 | Sprint 3, avant les rapports |
| util/ | NavigationManager | Membre 2 | Sprint 5 |

Règles :
- Les autres membres utilisent ces composants sans les modifier ; toute modification passe par le propriétaire.
- Les erreurs techniques ne sont jamais affichées directement à l'utilisateur.
- Aucun mot de passe ni code alphanumérique n'apparaît dans les messages d'erreur ou les logs.

## Travail du Chef de projet pendant Sprint 1

- Suivre chaque membre.
- Vérifier les noms des classes.
- Vérifier les attributs et les types.
- Vérifier les relations entre les entités.
- Vérifier qu'il n'existe pas de classes en double.
- Vérifier la cohérence entre les DAO Interfaces et le schéma validé.
- Organiser les Pull Requests.
- Valider les interfaces avant le début de leur implémentation.
- Configurer le `pom.xml` (Java 21, JavaFX 21, JUnit 5, plugins).
- Préparer le registre des migrations et valider les migrations de ce Sprint (`code_acces_hash`, vue `V_STOCK_FERME`).
- Préparer la base de test `orangia_test` (port 5433).
- Valider l'attribution des fichiers des packages `exception/`, `validation/` et `util/`.

## Livrables du Sprint 1

- Models créés ou complétés.
- DAO Interfaces créées ou complétées.
- Relations entre entités vérifiées.
- Types et noms harmonisés.
- Projet compilable.
- Aucun DAOImpl ou Service développé prématurément.
- `pom.xml` configuré pour Java 21 et JavaFX 21.
- Exceptions applicatives créées.
- Registre des migrations en place.

---

# 4. SPRINT 2 — DAO IMPLEMENTATIONS + JDBC

**Durée estimée : 3 à 5 jours**

## Objectif

Implémenter les DAO Interfaces et réaliser les opérations d'accès aux données PostgreSQL.

La configuration de la base et la connexion JDBC étant déjà préparées, ce Sprint concerne uniquement l'implémentation des accès aux données métier.

## Membre 2 — Authentification et Administration

Fichiers :

```text
src/main/java/com/orangia/dao/impl/
├── UtilisateurDAOImpl.java
├── ParametreSystemeDAOImpl.java
└── JournalActionDAOImpl.java
```

Responsabilités :
- Recherche par identifiant.
- Récupération des utilisateurs.
- Création des comptes.
- Modification des comptes.
- Désactivation des comptes.
- Récupération et modification des paramètres.
- Enregistrement des actions dans le journal.
- Consultation de l'historique.
- Mise à jour du hash du mot de passe et du hash du code alphanumérique.
- Recherche du Responsable actif d'une ferme.

## Membre 3 — Fermes et parcelles

Fichiers :

```text
src/main/java/com/orangia/dao/impl/
├── FermeDAOImpl.java
└── ParcelleDAOImpl.java
```

Responsabilités :
- CRUD des fermes.
- CRUD des parcelles.
- Recherche des parcelles par ferme.
- Recherche des fermes.
- Désactivation sans perte de l'historique.
- Vérification des relations entre les entités.

## Membre 4 — Ressources Humaines

Fichiers :

```text
src/main/java/com/orangia/dao/impl/
├── OuvrierDAOImpl.java
├── AffectationOuvrierDAOImpl.java
├── TarifOuvrierDAOImpl.java
├── FichePresenceDAOImpl.java
├── PresenceDAOImpl.java
├── DemandeCorrectionDAOImpl.java
├── SalaireDAOImpl.java
└── PaiementSalaireDAOImpl.java
```

Responsabilités :
- Gestion des ouvriers.
- Historique des affectations.
- Transfert entre fermes.
- Historique des tarifs.
- Récupération du tarif applicable à une période.
- Gestion des fiches et présences.
- Récupération des absences.
- Gestion des corrections et justifications.
- Récupération des salaires.
- Historique des paiements.

## Membre 5 — Production et Expéditions

Fichiers :

```text
src/main/java/com/orangia/dao/impl/
├── RecolteDAOImpl.java
├── ExpeditionDAOImpl.java
└── StockDAOImpl.java
```

Responsabilités :
- Enregistrement des récoltes.
- Recherche par ferme et parcelle.
- Historique des récoltes.
- Gestion des annulations.
- Création des expéditions.
- Recherche des expéditions en attente.
- Modification des informations autorisées.
- Récupération des expéditions confirmées, refusées ou annulées.
- Conservation de l'historique opérationnel et financier.
- Conservation des versions d'une expédition révisée.
- Lecture du stock par ferme via la vue `V_STOCK_FERME`.

## Membre 6 — Comptabilité et services communs

Fichiers :

```text
src/main/java/com/orangia/dao/impl/
├── ClientDAOImpl.java
├── PaiementExpeditionDAOImpl.java
├── DemandeDepenseDAOImpl.java
├── DepenseDAOImpl.java
├── DocumentDAOImpl.java
└── NotificationDAOImpl.java
```

Responsabilités :
- Gestion des clients.
- Enregistrement des paiements d'expéditions.
- Récupération des demandes de dépenses.
- Enregistrement des décisions du Comptable.
- Enregistrement des dépenses réelles.
- Récupération des dépenses par ferme et période.
- Gestion des documents liés à leur entité (expédition, fiche de présence, demande de correction, demande de dépense, dépense).
- Gestion des notifications.

## Livrables du Sprint 2

- Tous les DAOImpl attribués sont implémentés.
- Les opérations JDBC fonctionnent.
- Les requêtes SQL sont paramétrées.
- Les ressources JDBC sont correctement fermées.
- Les opérations multi-étapes utilisent des transactions lorsque nécessaire.
- Les erreurs d'accès aux données sont correctement gérées.
- Le projet compile.

---

# 5. SPRINT 3 — SERVICES, SÉCURITÉ ET RÈGLES MÉTIER

**Durée estimée : 4 à 6 jours**

## Objectif

Développer les Services qui contiennent les règles métier et assurent la communication entre les Controllers et les DAO.

**Règle fondamentale :** les règles métier et les contrôles de permissions ne doivent jamais être placés uniquement dans les Controllers.

## Membre 2 — Authentification et Administration

Fichiers :

```text
src/main/java/com/orangia/security/
├── PasswordHasher.java
├── AdminCodeHasher.java
├── SessionManager.java
├── AccessControl.java
├── RoleAccess.java
└── AuthorizationService.java

src/main/java/com/orangia/service/
├── AuthService.java
├── UtilisateurService.java
├── ParametreSystemeService.java
├── JournalActionService.java
└── AuditService.java

src/main/java/com/orangia/validation/
├── InputValidator.java
└── LoginValidator.java
```

Responsabilités :

**Authentification**
- Vérification de l'identifiant.
- Vérification du mot de passe hashé.
- Création et fermeture de session.
- Vérification du statut du compte.
- Vérification du rôle réel du compte.
- Protection contre les tentatives répétées : limitation temporaire, délai progressif entre les tentatives, journalisation des échecs et procédure de déblocage contrôlée (le suivi des tentatives, en mémoire ou en base, est décidé avec le Chef de projet ; s'il est en base, une migration est coordonnée).

**Authentification Admin**
- L'espace Administrateur utilise :
  - Identifiant.
  - Mot de passe.
  - Code alphanumérique.
- Le code alphanumérique est vérifié séparément.
- Le code est stocké sous forme hashée, jamais en clair.
- L'absence ou l'invalidité du code doit empêcher l'accès à l'espace Admin.

**Sécurité**
- Gestion des sessions.
- Vérification des autorisations (AccessControl, RoleAccess et AuthorizationService ; pas de doublon avec un éventuel PermissionChecker).
- Isolation des données : les Services déterminent la ferme du Responsable à partir de la session et ne font jamais confiance à un identifiant de ferme transmis par l'interface.
- Protection des opérations sensibles.
- Journalisation des actions importantes avec les champs minimaux : utilisateur, action, module, entité, identifiant de l'entité, date et heure, anciennes et nouvelles valeurs, résultat, motif.
- Journalisation des événements de sécurité : connexions réussies, tentatives échouées, accès à un espace non autorisé, réinitialisations de mots de passe, modifications du code Administrateur, désactivations de comptes.
- Aucun mot de passe ni code alphanumérique dans le journal.

**Gestion des comptes**
- Réinitialisation des mots de passe et du code alphanumérique par l'Administrateur.
- L'Administrateur ne peut jamais consulter un mot de passe ou un code existant.
- Changement du code contrôlé et journalisé.

**Profil**
- Chaque utilisateur peut consulter les informations de son profil ; les modifications dépendent du rôle.

**Paramètres système**
- Gestion des horaires généraux de travail.
- Heure de début et heure de fin.
- Les horaires sont utilisés par RH lors du traitement des présences et salaires.
- Les horaires appliqués aux anciennes fiches doivent rester conservés dans leur historique.
- Jours de repos configurables (dimanches et autres jours définis) : ils ne sont pas comptabilisés comme absences.
- Bonus mensuel de Cabran configurable par l'Administrateur (montant et seuil d'absences injustifiées).

**Journal et audit**
- JournalActionService : enregistrement centralisé des actions dans JOURNAL_ACTION via JournalActionDAO. Tous les Services l'appellent pour journaliser leurs opérations importantes et les événements de sécurité.
- AuditService : consultation de l'historique, filtres (utilisateur, module, entité, période, ferme) et supervision ; il ne modifie jamais les anciennes entrées du journal.
- Les anciennes entrées du journal ne peuvent pas être modifiées par les utilisateurs ordinaires.

**Validation**
- InputValidator et LoginValidator : validations réutilisables (champs obligatoires, formats, identifiant de connexion, longueur et format du code alphanumérique).

**Sauvegarde et restauration**
- Fonctionnalité optionnelle : elle n'est pas développée dans ce Sprint (voir la section « Optionnel » du Sprint 7). Si le temps manque, elle est reportée en Version 2.0.

## Membre 3 — Fermes et parcelles

Fichiers :

```text
src/main/java/com/orangia/service/
├── FermeService.java
└── ParcelleService.java

src/main/java/com/orangia/validation/
├── FermeValidator.java
└── ParcelleValidator.java
```

Responsabilités :
- Validation des informations des fermes.
- Validation des informations des parcelles.
- Contrôle des superficies.
- Gestion des relations ferme-parcelle.
- Validation des saisies via FermeValidator et ParcelleValidator, appelés par les Services.
- Vérification des codes uniques.
- Désactivation sécurisée.
- Protection de l'historique.
- Attribution d'un Responsable à une ferme : un Responsable actif n'est associé qu'à une seule ferme, et une ferme n'a qu'un seul Responsable principal actif à la fois (en utilisant les méthodes d'UtilisateurDAO du Membre 2).

## Membre 4 — Ressources Humaines

Fichiers :

```text
src/main/java/com/orangia/service/
├── OuvrierService.java
├── AffectationService.java
├── TarifService.java
├── FichePresenceService.java
├── PresenceService.java
├── CorrectionService.java
├── SalaireService.java
├── PaiementService.java
└── RapportRHService.java

src/main/java/com/orangia/validation/
├── OuvrierValidator.java
├── PresenceValidator.java
└── SalaireValidator.java
```

Responsabilités :
- Gestion des ouvriers.
- Affectation des ouvriers aux fermes.
- Transfert des ouvriers entre fermes.
- Conservation de l'historique des affectations.
- Gestion des tarifs et rémunérations.
- Préparation des fiches de présence.
- Traitement des présences et absences.
- Traitement des demandes de correction.
- Vérification des justificatifs.
- Calcul des salaires.
- Gestion des paiements.
- Génération des rapports RH.
- Validation des saisies via OuvrierValidator, PresenceValidator et SalaireValidator, appelés par les Services.

**Règles particulières :**

- Le transfert d'un ouvrier ne supprime jamais son historique de présence ou de salaire.
- Les horaires configurés par l'Administrateur sont utilisés dans le traitement RH.
- Les valeurs historiques appliquées aux calculs doivent être conservées.
- Cabran : salaire de base supérieur et bonus configurable.
- Le bonus de Cabran est conservé jusqu'au seuil convenu d'absences injustifiées ; à partir de trois absences injustifiées, il est supprimé pour la période concernée.
- Les absences justifiées acceptées et les jours de repos ne sont pas comptabilisés comme absences injustifiées.
- Naïb : rémunération spécifique, sans bonus Cabran.
- Cabran et Naïb restent des ouvriers ordinaires et ne possèdent pas de comptes utilisateurs dans l'application.

**Règles des fiches de présence :**

- Statuts : BROUILLON, TRANSMISE, EN_TRAITEMENT, VALIDEE, RETOURNEE.
- Une fiche représente une journée pour une ferme ; une seule fiche active par ferme et par date est autorisée.
- Après transmission, le Responsable ne peut plus modifier directement la fiche.
- Les corrections passent par une DemandeCorrection soumise par le Responsable (via le CorrectionService) et traitée par RH.
- Les dimanches et les jours de repos configurés ne sont pas des absences.
- Les justificatifs acceptés sont conservés.

**Coûts salariaux agrégés :** SalaireService fournit au Comptable les coûts salariaux agrégés par période et par ferme, sans donner accès aux dossiers individuels RH ni aux paiements de salaires.

## Membre 5 — Production et Expéditions

Fichiers :

```text
src/main/java/com/orangia/service/
├── RecolteService.java
├── StockService.java
└── ExpeditionService.java
```

Responsabilités :

**Récoltes**
- Création des récoltes.
- Modification selon les permissions.
- Validation des quantités.
- Annulation avec motif obligatoire.
- Conservation de l'historique.

**Stock**
- Calcul automatique du stock disponible.
- Consultation du stock par ferme.
- Consultation du stock par période si nécessaire.
- Vérification de la disponibilité avant une expédition.

**Règle de calcul du stock :**

Stock disponible = Récoltes valides − Quantités physiques des expéditions confirmées et non annulées.

- Utiliser la quantité physique expédiée pour déduire le stock.
- Ne jamais utiliser la quantité facturée pour calculer le stock.
- Aucun ajustement manuel du stock.
- Le stock ne doit jamais devenir négatif.
- Le stock est lu par ferme via la vue `V_STOCK_FERME` ; la vue ne remplace pas les contrôles transactionnels réalisés pendant les opérations.

**Expéditions**
- Création d'une expédition par le Responsable.
- Vérification de la quantité disponible.
- Transmission au Comptable.
- Gestion des modifications autorisées.
- Gestion du refus.
- Gestion de la confirmation.
- Gestion des annulations.
- Conservation de l'historique des décisions.

**Cycle de vie et statuts de l'expédition :**

- Statuts : BROUILLON, EN_ATTENTE_CONFIRMATION, CONFIRMEE, REFUSEE, REVISION_EN_ATTENTE, ANNULATION_EN_ATTENTE, ANNULEE.
- Les transitions entre statuts sont centralisées dans ExpeditionService.
- Une expédition non confirmée ou refusée ne réduit pas le stock.
- Une expédition confirmée réduit le stock et ne peut pas être modifiée directement.
- Une modification opérationnelle d'une expédition confirmée passe par une révision (REVISION_EN_ATTENTE) et exige une nouvelle confirmation ; l'ancienne version confirmée reste consultable.
- Une annulation passe par ANNULATION_EN_ATTENTE ; une expédition annulée restaure la quantité physique précédemment déduite.
- Une annulation après paiement nécessite un traitement financier par le Comptable ; les paiements historiques ne sont jamais supprimés automatiquement.
- Le stock est contrôlé de façon transactionnelle lors de la confirmation : deux confirmations simultanées ne doivent jamais permettre un stock négatif.
- Le bon de livraison est rattaché à l'expédition via le DocumentService (Membre 6).

**Services d'autres membres appelés depuis l'espace Responsable :**

- Corrections de présence : soumission via le CorrectionService (Membre 4).
- Clients : informations opérationnelles via le ClientService (Membre 6), sans jamais exposer les montants financiers ni les paiements.
- Documents : bons de livraison via le DocumentService (Membre 6).

**Important :** le Responsable gère les opérations de sa propre ferme. Le Comptable intervient sur la confirmation et les informations financières selon ses permissions.

## Membre 6 — Comptabilité et services communs

Fichiers :

```text
src/main/java/com/orangia/service/
├── ClientService.java
├── DemandeDepenseService.java
├── DepenseService.java
├── PaiementExpeditionService.java
├── DocumentService.java
├── NotificationService.java
├── RapportService.java
└── DashboardService.java
```

Responsabilités :

**Clients**
- Gestion des informations générales.
- Modification des coordonnées.
- Consultation de l'historique.
- Conservation des clients liés à des opérations historiques.
- Séparation des informations : le Responsable accède uniquement aux informations opérationnelles, le Comptable gère aussi les informations financières.

**Demandes de dépenses**
- Consultation des demandes.
- Acceptation ou refus.
- Enregistrement du commentaire de décision.
- Conservation de l'historique.

**Dépenses**
- Enregistrement des dépenses réelles.
- Catégorisation.
- Association à la ferme concernée.
- Consultation par période.
- Gestion des justificatifs.
- Liaison facultative avec la demande de dépense initiale.

**Paiements**
- Enregistrement des paiements d'expéditions.
- Consultation des paiements.
- Historique financier.
- Le total net des paiements ne peut pas dépasser le montant dû.
- Les remboursements et annulations financières sont enregistrés comme opérations distinctes ; les paiements historiques ne sont jamais modifiés ni supprimés pour corriger une erreur.
- Traitement financier d'une annulation d'expédition déjà payée.
- Transaction protégeant le contrôle du solde contre les paiements simultanés.

**Rapports et Dashboard**
- Indicateurs financiers.
- Recettes.
- Dépenses.
- Paiements.
- Bilans par période.
- Informations globales nécessaires au Dashboard Comptable.
- Rapports PDF selon les besoins définis.
- Coûts salariaux agrégés (fournis par le Membre 4).
- Consultation des récoltes et du stock en lecture seule (via RecolteService et StockService du Membre 5).

**Documents**
- Association de chaque document à l'entité concernée : expéditions, fiches de présence, demandes de correction, demandes de dépenses, dépenses réelles.
- Stockage des fichiers dans un emplacement contrôlé, distinct du code source ; la base conserve les métadonnées et la référence de stockage.

**Règle importante :** l'acceptation d'une demande de dépense ne crée pas automatiquement une dépense réelle. La dépense doit être enregistrée séparément après sa réalisation.

## Livrables du Sprint 3

- Services implémentés.
- Règles métier appliquées.
- Contrôle des permissions dans les Services.
- Gestion des erreurs.
- Transactions pour les opérations sensibles.
- Validation des données.
- Aucune logique métier importante uniquement dans les Controllers.
- Les Services déterminent la ferme à partir de la session.
- Fichiers `validation/` et `util/` créés par leurs propriétaires.

---

# 6. SPRINT 4 — TESTS BACKEND, INTÉGRATION ET SÉCURITÉ

**Durée estimée : 2 à 3 jours (à réévaluer avec l'ajout des tests d'intégration, de sécurité et de concurrence)**

## Objectif

Vérifier les Models, DAO et Services avant de commencer le développement des interfaces graphiques.

**Environnement :** les tests s'exécutent sur la base `orangia_test` (port 5433) et jamais sur la base principale. Les dossiers de tests sont `dao/`, `service/`, `security/`, `validation/` et `integration/`.

## Membre 2 — Tests Authentification et Administration

Tests :
- Login Admin avec des informations correctes.
- Login Admin avec un code alphanumérique incorrect.
- Login sans code Admin.
- Login Responsable.
- Login RH.
- Login Comptable.
- Mot de passe incorrect.
- Identifiant inexistant.
- Compte désactivé.
- Mauvais espace sélectionné.
- Accès non autorisé.
- Vérification des permissions.
- Modification des paramètres système.
- Journalisation des actions via JournalActionService.
- Consultation du journal via AuditService.
- Un Responsable ne peut pas accéder à l'espace Administrateur.
- Le code alphanumérique et les mots de passe ne sont jamais stockés en clair.
- Les sessions sont invalidées après la déconnexion.
- Limitation des tentatives de connexion répétées.
- Réinitialisation d'un mot de passe et du code Administrateur, avec journalisation.
- Consultation du profil.

## Membre 3 — Tests Fermes et Parcelles

Tests :
- Création d'une ferme.
- Modification.
- Désactivation.
- Création d'une parcelle.
- Modification.
- Désactivation.
- Recherche des parcelles d'une ferme.
- Contrôle des données invalides.
- Vérification des relations.
- Attribution d'un Responsable à une ferme : un seul Responsable principal actif par ferme.
- Un Responsable ne peut pas consulter les données d'une autre ferme.

## Membre 4 — Tests RH

Tests :
- Création d'un ouvrier.
- Modification d'un ouvrier.
- Affectation à une ferme.
- Transfert vers une autre ferme.
- Vérification de l'historique.
- Modification des tarifs.
- Récupération du tarif applicable.
- Création d'une fiche de présence.
- Enregistrement d'une présence.
- Enregistrement d'une absence.
- Demande de correction.
- Validation ou refus d'une justification.
- Calcul des salaires.
- Paiement d'un salaire.
- Vérification des cas particuliers Cabran et Naïb.
- Vérification de la conservation de l'historique.
- Une seule fiche active par ferme et par date.
- Statuts de fiche et verrouillage après transmission.
- Demande de correction soumise par le Responsable.
- Les dimanches et jours de repos ne sont pas des absences.
- RH ne peut pas modifier les quantités d'une expédition.
- Coûts salariaux agrégés fournis au Comptable.

## Membre 5 — Tests Production et Expéditions

Tests :
- Création d'une récolte.
- Modification.
- Annulation avec motif.
- Calcul du stock.
- Création d'une expédition.
- Vérification du stock disponible.
- Transmission au Comptable.
- Refus d'une expédition.
- Confirmation d'une expédition.
- Annulation d'une expédition confirmée selon les règles.
- Vérification de la quantité physique déduite du stock.
- Vérification de l'impossibilité d'obtenir un stock négatif.
- Cohérence de la vue `V_STOCK_FERME` avec le calcul du stock.
- Révision d'une expédition confirmée : nouvelle confirmation requise, ancienne version conservée.
- Annulation d'une expédition payée : traitement financier requis.
- Un Responsable ne peut pas consulter les paiements ni confirmer financièrement une expédition.
- Un Responsable ne peut pas consulter une autre ferme.
- Informations des clients limitées aux données opérationnelles pour le Responsable.

## Membre 6 — Tests Comptabilité

Tests :
- Création d'un client.
- Modification d'un client.
- Création d'une demande de dépense.
- Acceptation ou refus.
- Vérification qu'une demande acceptée ne crée pas automatiquement une dépense.
- Enregistrement d'une dépense réelle.
- Enregistrement d'un paiement d'expédition.
- Consultation de l'historique financier.
- Vérification des calculs.
- Vérification des rapports et indicateurs.
- Un Comptable ne peut pas modifier une récolte.
- Remboursement et annulation financière enregistrés comme opérations distinctes.
- Le total net des paiements ne dépasse jamais le montant dû.
- Association des documents aux entités concernées.
- Consultation des récoltes et du stock en lecture seule.

## Tests d'intégration

Coordonnés par le Chef de projet, écrits par chaque membre pour ses propres DAO et Services.

- Connexion à PostgreSQL de test.
- Requêtes JDBC de chaque DAOImpl.
- Contraintes : PRIMARY KEY, FOREIGN KEY, NOT NULL, UNIQUE, CHECK.
- Transactions : commit et rollback.
- Conservation des historiques.
- Création des comptes et authentification des quatre rôles.
- Interactions entre modules.

Le chargement des fichiers FXML est testé au Sprint 7.

## Tests de concurrence

- Deux confirmations simultanées d'expéditions sur le même stock : le stock ne devient jamais négatif (Membre 5).
- Deux paiements simultanés sur la même expédition : le total net ne dépasse jamais le montant dû (Membre 6).

## Chef de projet

Responsabilités :
- Organiser les tests communs.
- Vérifier les résultats.
- Suivre les bugs.
- Prioriser les corrections.
- Vérifier les interactions entre les modules.
- Valider le Backend avant le développement des Controllers.
- Vérifier que la base `orangia_test` est utilisée par tous les tests.
- Vérifier que les migrations sont testées sur `orangia_test`.

## Livrables du Sprint 4

- Tests Backend réussis.
- Bugs bloquants corrigés.
- Règles métier vérifiées.
- Permissions vérifiées.
- Backend stable.
- Tests d'intégration, de sécurité d'accès et de concurrence réussis.
- Tests exécutés uniquement sur `orangia_test`.

---

# 7. SPRINT 5 — CONTROLLERS JAVAFX

**Durée estimée : 4 à 5 jours**

## Objectif

Créer les Controllers qui assurent la communication entre les interfaces FXML et les Services.

Les Controllers doivent :
- Récupérer les événements de l'interface.
- Appeler les Services.
- Afficher les résultats.
- Gérer les messages d'erreur.
- Contrôler les interactions de l'utilisateur.

**Interdictions :**
- Pas de requêtes SQL dans les Controllers.
- Pas de logique métier complexe.
- Pas de contournement des permissions.
- Aucune requête JDBC ni opération longue sur le JavaFX Application Thread (utiliser `Task`, un Service JavaFX ou un `ExecutorService`).

## Membre 2 — Authentification et Administration

Fichiers :

```text
src/main/java/com/orangia/controller/
├── LoginController.java
├── WelcomeController.java
├── UtilisateurController.java
├── DashboardAdminController.java
├── SupervisionController.java
├── HistoriqueAdminController.java
├── ParametresController.java
└── ProfilController.java
```

Responsabilités :
- Choix de l'espace.
- Connexion des utilisateurs.
- Connexion spécifique de l'Administrateur avec code alphanumérique.
- Gestion des utilisateurs.
- Dashboard Admin.
- Supervision.
- Historique des actions.
- Paramètres système.
- Réinitialisation des mots de passe et du code Administrateur (via UtilisateurController).
- Consultation du profil.

## Membre 3 — Fermes et Parcelles

Fichiers :

```text
src/main/java/com/orangia/controller/
├── FermeController.java
└── ParcelleController.java
```

Responsabilités :
- Affichage des fermes.
- Création et modification.
- Désactivation.
- Attribution du Responsable à une ferme.
- Affichage des parcelles.
- Création et modification.
- Recherche par ferme.

## Membre 4 — Ressources Humaines

Fichiers :

```text
src/main/java/com/orangia/controller/
├── OuvrierController.java
├── AffectationController.java
├── TarifController.java
├── PresenceController.java
├── CorrectionController.java
├── SalaireController.java
├── PaiementSalaireController.java
├── DashboardRHController.java
└── RapportRHController.java
```

Responsabilités :
- Gestion des ouvriers.
- Affectations et transferts.
- Gestion des tarifs.
- Gestion des présences.
- Validation des corrections.
- Calcul et consultation des salaires.
- Paiements.
- Dashboard RH.
- Rapports RH.

## Membre 5 — Responsable de Ferme et Production

Fichiers :

```text
src/main/java/com/orangia/controller/
├── RecolteController.java
├── ExpeditionController.java
├── StockController.java
├── ExpeditionConfirmationController.java
├── PresenceTransmissionController.java
├── CorrectionResponsableController.java
├── ClientResponsableController.java
├── DemandeDepenseResponsableController.java
├── DashboardResponsableController.java
└── HistoriqueResponsableController.java
```

Responsabilités :
- Gestion des récoltes.
- Consultation du stock.
- Création et suivi des expéditions.
- Transmission des expéditions au Comptable.
- Écrans nécessaires au traitement des confirmations selon les permissions.
- Transmission des fiches de présence.
- Consultation des demandes de dépenses.
- Dashboard Responsable avec cinq indicateurs : récoltes aujourd'hui, expéditions aujourd'hui, stock disponible, présences envoyées, demandes en attente.
- Historique des opérations de la ferme.
- Soumission des demandes de correction de présence (appel du CorrectionService).
- Gestion des informations opérationnelles des clients (appel du ClientService, sans données financières).
- Ajout du bon de livraison aux expéditions (appel du DocumentService).

## Membre 6 — Comptabilité

Fichiers :

```text
src/main/java/com/orangia/controller/
├── ClientController.java
├── PaiementController.java
├── DepenseController.java
├── DemandeDepenseComptableController.java
├── RapportController.java
├── DocumentController.java
├── NotificationController.java
├── RecoltesStockComptableController.java
└── DashboardController.java
```

Responsabilités :
- Gestion des clients.
- Consultation financière des clients.
- Paiements des expéditions.
- Gestion des dépenses.
- Traitement des demandes de dépenses.
- Rapports financiers.
- Documents.
- Notifications.
- Dashboard Comptable.
- Consultation des récoltes et du stock en lecture seule (RecoltesStockComptableController, appelle RecolteService et StockService).
- Consultation des coûts salariaux agrégés.

**Attention :** le Membre 5 reste propriétaire du module technique Expédition. Le Controller du Comptable doit appeler le Service correspondant pour confirmer ou refuser une expédition, sans créer un deuxième module Expédition.

## Livrables du Sprint 5

- Controllers créés.
- Services correctement appelés.
- Navigation préparée.
- Événements gérés.
- Gestion des erreurs d'interface.
- Respect des permissions.
- Opérations longues exécutées hors du JavaFX Application Thread.

---

# 8. SPRINT 6 — FXML + CSS

**Durée estimée : 5 à 7 jours**

## Objectif

Créer les interfaces graphiques complètes de l'application et connecter les écrans aux Controllers.

Les interfaces doivent respecter :
- Le thème visuel ORANGIA.
- Une présentation professionnelle.
- Une navigation cohérente.
- Les permissions de chaque rôle.
- Les composants communs de l'application.

## Membre 2 — Authentification et Administration

FXML :

```text
src/main/resources/com/orangia/view/
├── WelcomeView.fxml
├── LoginView.fxml
├── AdminLoginView.fxml
├── UtilisateursView.fxml
├── DashboardAdminView.fxml
├── SupervisionView.fxml
├── HistoriqueAdminView.fxml
├── ParametresView.fxml
└── ProfilView.fxml
```

CSS :

```text
src/main/resources/com/orangia/css/
├── auth.css
└── admin.css
```

Responsabilités :
- Écran de choix des espaces.
- Écran de connexion classique.
- Écran Admin avec code alphanumérique.
- Gestion des utilisateurs.
- Dashboard Admin.
- Supervision.
- Historique.
- Paramètres.
- Réinitialisation des mots de passe et du code Administrateur.
- Profil utilisateur.

## Membre 3 — Fermes et Parcelles

FXML :

```text
src/main/resources/com/orangia/view/
├── FermesView.fxml
└── ParcellesView.fxml
```

CSS :

```text
src/main/resources/com/orangia/css/
└── fermes.css
```

Responsabilités :
- Liste des fermes.
- Formulaire d'ajout et modification.
- Détails d'une ferme.
- Attribution du Responsable à une ferme.
- Liste des parcelles.
- Formulaires de parcelles.
- Recherche et filtres.

## Membre 4 — Ressources Humaines

FXML :

```text
src/main/resources/com/orangia/view/
├── OuvriersView.fxml
├── AffectationsView.fxml
├── TarifsView.fxml
├── PresencesView.fxml
├── CorrectionsView.fxml
├── SalairesView.fxml
├── PaiementsSalairesView.fxml
├── DashboardRHView.fxml
└── RapportsRHView.fxml
```

CSS :

```text
src/main/resources/com/orangia/css/
└── rh.css
```

Responsabilités :
- Gestion du personnel.
- Formulaires des ouvriers.
- Affectations et transferts.
- Historique.
- Tarifs.
- Présences et absences.
- Corrections et justifications.
- Salaires.
- Paiements.
- Dashboard RH.
- Rapports RH.

## Membre 5 — Responsable de Ferme et Production

FXML :

```text
src/main/resources/com/orangia/view/
├── RecoltesView.fxml
├── ExpeditionsView.fxml
├── StockView.fxml
├── ConfirmationExpeditionView.fxml
├── PresenceTransmissionView.fxml
├── CorrectionsResponsableView.fxml
├── ClientsResponsableView.fxml
├── DemandesDepensesResponsableView.fxml
├── DashboardResponsableView.fxml
└── HistoriqueResponsableView.fxml
```

CSS :

```text
src/main/resources/com/orangia/css/
└── responsable.css
```

Responsabilités :
- Gestion des récoltes.
- Consultation du stock.
- Création et suivi des expéditions.
- État des expéditions.
- Transmission des présences.
- Consultation des demandes de dépenses.
- Dashboard du Responsable avec ses cinq indicateurs.
- Demandes de correction de présence.
- Clients (informations opérationnelles uniquement).
- Historique opérationnel.

## Membre 6 — Comptabilité

FXML :

```text
src/main/resources/com/orangia/view/
├── ClientsView.fxml
├── ClientsFinanciersView.fxml
├── PaiementsView.fxml
├── DepensesView.fxml
├── DemandesDepensesComptableView.fxml
├── RapportsView.fxml
├── DocumentsView.fxml
├── NotificationsView.fxml
├── RecoltesStockView.fxml
└── DashboardView.fxml
```

CSS :

```text
src/main/resources/com/orangia/css/
└── comptable.css
```

Responsabilités :
- Gestion des clients.
- Informations financières des clients.
- Paiements des expéditions.
- Dépenses.
- Demandes de dépenses.
- Rapports financiers.
- Documents.
- Notifications.
- Dashboard Comptable.
- Récoltes et stock en lecture seule.
- Coûts salariaux agrégés.

## Composants communs

Les composants communs doivent être attribués par le Chef de projet avant leur développement.

Fichiers concernés :

```text
src/main/resources/com/orangia/view/
├── MainLayoutView.fxml
├── SidebarView.fxml
├── TopbarView.fxml
└── ConfirmationDialog.fxml

src/main/resources/com/orangia/css/
├── style.css
└── components.css
```

**Règle :** un seul propriétaire par composant commun. Aucun membre ne doit modifier les composants partagés sans coordination.

## Livrables du Sprint 6

- Interfaces FXML créées.
- CSS appliqué.
- Navigation fonctionnelle.
- Formulaires et tableaux opérationnels.
- Controllers correctement associés.
- Affichage des données réelles.
- Permissions respectées dans les espaces.

---

# 9. SPRINT 7 — INTÉGRATION FINALE ET TESTS GLOBAUX

**Durée estimée : 3 à 5 jours**

## Objectif

Assembler les différents modules, vérifier les interactions entre les rôles et préparer une version stable de l'application.

## Membre 1 — Chef de projet

- Organiser l'intégration générale.
- Vérifier les Pull Requests.
- Contrôler les conflits.
- Vérifier la cohérence de l'architecture.
- Vérifier la navigation entre les espaces.
- Organiser les tests globaux.
- Suivre les bugs.
- Valider les corrections.
- Vérifier la documentation.
- Préparer la version finale.
- Vérifier que les migrations sont appliquées sur `orangia_test` puis sur la base principale.

## Membre 2 — Authentification et Administration

- Tester les quatre espaces.
- Tester le code alphanumérique Admin.
- Vérifier les permissions.
- Tester les paramètres système.
- Vérifier le journal des actions (JournalActionService et AuditService).

## Membre 3 — Fermes et Parcelles

- Tester les fermes.
- Tester les parcelles.
- Vérifier les relations entre les entités.
- Vérifier les accès aux données des fermes.
- Tester les opérations de désactivation.

## Membre 4 — Ressources Humaines

- Tester les ouvriers.
- Tester les transferts.
- Vérifier l'historique RH.
- Tester les présences et corrections.
- Vérifier les calculs des salaires.
- Tester les paiements.
- Vérifier les cas particuliers Cabran et Naïb.

## Membre 5 — Production et Expéditions

- Tester les récoltes.
- Tester le stock.
- Tester les expéditions.
- Tester la transmission au Comptable.
- Tester les confirmations et refus.
- Vérifier les annulations.
- Vérifier la quantité physique déduite du stock.
- Vérifier les accès du Responsable à sa propre ferme.

## Membre 6 — Comptabilité

- Tester les clients.
- Tester les demandes de dépenses.
- Tester les dépenses réelles.
- Tester les paiements.
- Tester les informations financières.
- Tester les rapports.
- Tester le Dashboard Comptable.
- Vérifier les interactions avec les expéditions.

## Optionnel — Sauvegarde et restauration (Sprint 7 ou Version 2.0)

Cette fonctionnalité n'est réalisée que si les vérifications finales ci-dessous sont terminées et si le temps le permet. Sinon, elle est reportée en Version 2.0. Elle ne bloque pas la livraison.

Propriétaire : Membre 2.

```text
src/main/java/com/orangia/service/
├── BackupService.java
└── RestoreService.java

src/main/java/com/orangia/controller/
└── SauvegardeController.java

src/main/resources/com/orangia/view/
└── SauvegardeView.fxml
```

Règles :
- Sauvegarde automatique quotidienne.
- Sauvegarde manuelle par l'Administrateur.
- Conservation des sept dernières sauvegardes.
- Confirmation obligatoire avant restauration.
- Journalisation des sauvegardes et des restaurations via JournalActionService.
- Tests sur la base `orangia_test`, jamais sur la base principale.

## Vérifications finales

- Tous les modules fonctionnent.
- Les quatre rôles respectent leurs permissions.
- Le Responsable accède uniquement aux données de sa ferme.
- RH et Comptable disposent de leur périmètre global.
- Les opérations financières sont cohérentes.
- Les expéditions respectent leur cycle de vie.
- Aucun stock négatif.
- Aucun historique métier important n'est supprimé.
- Les opérations sensibles utilisent des transactions.
- Les interfaces sont cohérentes.
- Aucun bug bloquant.
- Projet compilable et exécutable.
- Documentation mise à jour.
- Les fichiers FXML se chargent sans erreur (test d'intégration).
- Les tests de sécurité d'accès sont réussis.
- Aucun mot de passe ni code alphanumérique en clair dans la base, les logs ou les messages d'erreur.

---

# 10. WORKFLOW DE DÉVELOPPEMENT

Pour chaque fonctionnalité, respecter l'ordre suivant :

```text
Model
  ↓
DAO Interface
  ↓
DAO Implementation
  ↓
Service
  ↓
Tests Backend
  ↓
Controller
  ↓
FXML
  ↓
CSS
  ↓
Intégration
```

Chaque membre respecte cet ordre pour son module.

Les interfaces communes et les dépendances doivent être validées avant que les membres commencent à développer des composants qui les utilisent.

**Important :** les cinq développeurs peuvent avancer sur des modules différents, mais ils doivent respecter les contrats des classes partagées.

---

# 11. WORKFLOW GITHUB OBLIGATOIRE

## Branches principales

Conserver les branches et conventions déjà établies dans le dépôt Orangia.

Organisation de référence :

```text
main
  └── DEV
       ├── feature/auth-admin
       ├── feature/fermes-parcelles
       ├── feature/rh
       ├── feature/production-expeditions
       └── feature/comptabilite
```

Les noms des branches doivent rester cohérents avec les branches réellement présentes dans le dépôt.

## Règles de travail

1. Chaque membre travaille sur sa branche.
2. Aucun membre ne pousse directement sur `main`.
3. Aucun membre ne pousse directement sur `DEV`.
4. Chaque fonctionnalité terminée passe par une Pull Request vers `DEV`.
5. Le Chef de projet vérifie le code avant le Merge.
6. Chaque Merge doit être testé.
7. `main` reçoit uniquement les versions validées.
8. Les fichiers partagés ne sont pas modifiés sans coordination.
9. Les commits doivent être clairs et descriptifs.

## Cycle de travail

```text
Récupérer DEV
    ↓
Mettre à jour sa branche
    ↓
Développer sa tâche
    ↓
Tester localement
    ↓
git add
    ↓
git commit
    ↓
git push
    ↓
Pull Request vers DEV
    ↓
Code Review
    ↓
Validation du Chef de projet
    ↓
Merge
```

## Convention des commits

Exemples :

```text
feat(model): add Ferme and Parcelle models
feat(dao): implement OuvrierDAO
feat(service): add PresenceService
feat(security): add Admin code verification
feat(controller): add LoginController
feat(ui): create login interface
fix(auth): correct workspace validation
fix(stock): prevent negative stock
refactor(dao): improve resource handling
docs: update development planning
```

---

# 12. RÈGLES IMPORTANTES POUR ÉVITER LES CONFLITS

1. Un seul propriétaire par fichier.
2. Aucun membre ne crée une classe déjà attribuée à un autre.
3. Les noms des attributs et méthodes doivent être validés avant leur utilisation par plusieurs modules.
4. Les modifications de la base de données doivent être coordonnées.
5. Les fichiers partagés ne sont pas modifiés sans accord.
6. Les Controllers ne contiennent pas de SQL.
7. Les règles métier sont placées dans les Services.
8. Les DAO s'occupent uniquement de l'accès aux données.
9. Les permissions sont vérifiées dans les Services, pas uniquement dans l'interface.
10. Les historiques métier sont conservés.
11. Les données d'une ferme ne doivent pas être accessibles à un Responsable d'une autre ferme.
12. RH et Comptable travaillent sur l'ensemble des fermes selon leurs permissions.
13. Les opérations financières doivent être séparées des opérations physiques.
14. Une demande de dépense acceptée n'est pas automatiquement une dépense réelle.
15. La quantité physique d'une expédition est utilisée pour le calcul du stock.
16. Le code alphanumérique Admin ne doit jamais être enregistré en clair.
17. Les migrations SQL doivent être coordonnées et versionnées.
18. Ne pas réintroduire les anciennes tables de permissions dynamiques si l'architecture validée utilise des permissions statiques en Java.
19. Les Services déterminent la ferme du Responsable à partir de la session ; ils ne font jamais confiance à un identifiant de ferme transmis par l'interface.
20. Les tests ne se connectent jamais à la base principale : ils utilisent `orangia_test` (port 5433).
21. Une migration appliquée n'est jamais modifiée ; les corrections passent par une nouvelle migration, testée sur `orangia_test` avant la base principale.
22. Les requêtes JDBC et les opérations longues ne s'exécutent pas sur le JavaFX Application Thread.
23. Les fichiers des packages `exception/`, `validation/` et `util/` ont un propriétaire unique attribué par le Chef de projet.
24. Le stock est calculé exclusivement via la vue `V_STOCK_FERME`.
25. Aucune table `PRODUIT_STOCK` ne doit être créée.
26. Aucune table `MOUVEMENT_STOCK` ne doit être créée.
27. Les rôles sont statiques dans Java et ne sont pas administrables depuis l'application.
28. Toute modification du schéma SQL passe par une migration versionnée.
29. Les fichiers FXML, CSS et images sont placés uniquement sous `src/main/resources/com/orangia/` ; les anciens chemins `resources/fxml/` et `resources/css/` ne sont plus utilisés.

---

# 13. CONDITIONS DE VALIDATION DE CHAQUE SPRINT

Un Sprint n'est pas terminé simplement parce que les fichiers existent.

Il est validé lorsque :

- Les fichiers attribués sont créés ou complétés.
- Le code compile.
- Les méthodes sont réellement implémentées.
- Les dépendances sont respectées.
- Les tests prévus sont réussis.
- Les règles métier sont respectées.
- La Pull Request est revue.
- Le code est intégré dans `DEV`.
- Aucun conflit bloquant n'est présent.

**Règle finale :** aucun Sprint ne doit être déclaré terminé sans validation du Chef de projet.

---

# 14. RÉSULTAT ATTENDU

À la fin du Sprint 7, ORANGIA doit disposer de :

- Une authentification sécurisée.
- Une authentification Admin avec code alphanumérique.
- Quatre espaces avec leurs autorisations.
- Une gestion des utilisateurs.
- Une gestion des fermes et parcelles.
- Une gestion RH complète.
- Une gestion des présences et salaires.
- Une gestion des récoltes.
- Un stock calculé automatiquement.
- Une gestion complète des expéditions.
- Une gestion des clients.
- Une gestion des demandes de dépenses.
- Une gestion des dépenses réelles.
- Une gestion des paiements.
- Des Dashboards adaptés aux rôles.
- Des rapports.
- Des notifications et documents.
- Une base PostgreSQL intégrée, avec la vue `V_STOCK_FERME` et les migrations versionnées.
- Une architecture MVC + DAO + Service cohérente.
- Des tests unitaires, d'intégration, de sécurité d'accès et de concurrence réussis.
- Une application intégrée, testée et stable.

---

**ORANGIA — De la conception à une application complète de gestion agricole.**