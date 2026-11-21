# ORANGIA — PLANIFICATION DÉTAILLÉE DU DÉVELOPPEMENT

**Version : 3.1 — Novembre 2026**  
**Projet : ORANGIA — Application de gestion de ferme agricole**  
**Équipe : 6 personnes (1 Chef de projet + 5 développeurs)**  
**Méthode : Développement par Sprints**  
**Architecture : MVC + DAO + Service**  
**Technologies : Java 21, JavaFX, FXML, CSS, Maven, PostgreSQL, JDBC, GitHub**

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

**Participation au développement :**

Le Membre 1 ne prend en charge aucun développement de fonctionnalités et ne possède aucun fichier Java, FXML ou CSS à coder.

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
- Sauvegarde et restauration de la base de données.
- Supervision administrative.

## Membre 3 — Gestion des Fermes et Parcelles

Responsable de :
- Gestion des fermes.
- Gestion des parcelles.
- Informations agricoles.
- Affectation des parcelles aux fermes.
- Consultation des informations des fermes.
- Désactivation sécurisée des fermes et parcelles.
- Respect des relations entre les fermes et les autres modules.

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
        ├── fxml/
        ├── css/
        ├── images/
        └── database/
            └── migrations/

src/test/java/com/orangia/
├── dao/
└── service/

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

**Important :** aucun RoleDAO n'est nécessaire si les rôles restent définis statiquement dans Java.

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
- Statut de validation.
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
- statut opérationnel.
- statut de confirmation.
- prix unitaire nullable.
- quantité facturée nullable.
- montant total calculé.
- informations de confirmation.
- informations de refus.
- informations de modification et d'annulation.
- utilisateur créateur.
- dates de création et de modification.
- observation.

### DAO Interfaces

```text
src/main/java/com/orangia/dao/interfaces/
├── RecolteDAO.java
└── ExpeditionDAO.java
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

**Document.java**
- idDocument.
- type de document.
- nom.
- chemin ou référence.
- date de création.
- utilisateur créateur.

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

## Travail du Chef de projet pendant Sprint 1

- Suivre chaque membre.
- Vérifier les noms des classes.
- Vérifier les attributs et les types.
- Vérifier les relations entre les entités.
- Vérifier qu'il n'existe pas de classes en double.
- Vérifier la cohérence entre les DAO Interfaces et le schéma validé.
- Organiser les Pull Requests.
- Valider les interfaces avant le début de leur implémentation.

## Livrables du Sprint 1

- Models créés ou complétés.
- DAO Interfaces créées ou complétées.
- Relations entre entités vérifiées.
- Types et noms harmonisés.
- Projet compilable.
- Aucun DAOImpl ou Service développé prématurément.

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
└── ExpeditionDAOImpl.java
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
- Gestion des documents.
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
└── AuthorizationService.java

src/main/java/com/orangia/service/
├── AuthService.java
├── UtilisateurService.java
├── ParametreSystemeService.java
├── AuditService.java
├── BackupService.java
└── RestoreService.java
```

Responsabilités :

**Authentification**
- Vérification de l'identifiant.
- Vérification du mot de passe hashé.
- Création et fermeture de session.
- Vérification du statut du compte.
- Vérification du rôle réel du compte.

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
- Vérification des autorisations.
- Protection des opérations sensibles.
- Journalisation des actions importantes.

**Paramètres système**
- Gestion des horaires généraux de travail.
- Heure de début et heure de fin.
- Les horaires sont utilisés par RH lors du traitement des présences et salaires.
- Les horaires appliqués aux anciennes fiches doivent rester conservés dans leur historique.

**Sauvegarde et restauration**
- Sauvegarde automatique quotidienne.
- Sauvegarde manuelle par l'Administrateur.
- Conservation des sept dernières sauvegardes.
- Confirmation obligatoire avant restauration.
- Journalisation des opérations de restauration.

## Membre 3 — Fermes et parcelles

Fichiers :

```text
src/main/java/com/orangia/service/
├── FermeService.java
└── ParcelleService.java
```

Responsabilités :
- Validation des informations des fermes.
- Validation des informations des parcelles.
- Contrôle des superficies.
- Gestion des relations ferme-parcelle.
- Vérification des codes uniques.
- Désactivation sécurisée.
- Protection de l'historique.

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

**Règles particulières :**

- Le transfert d'un ouvrier ne supprime jamais son historique de présence ou de salaire.
- Les horaires configurés par l'Administrateur sont utilisés dans le traitement RH.
- Les valeurs historiques appliquées aux calculs doivent être conservées.
- Cabran : salaire de base supérieur et bonus configurable.
- Le bonus de Cabran est conservé jusqu'au seuil convenu d'absences injustifiées ; à partir de trois absences injustifiées, il est supprimé pour la période concernée.
- Les absences justifiées acceptées et les jours de repos ne sont pas comptabilisés comme absences injustifiées.
- Naïb : rémunération spécifique, sans bonus Cabran.
- Cabran et Naïb restent des ouvriers ordinaires et ne possèdent pas de comptes utilisateurs dans l'application.

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

**Expéditions**
- Création d'une expédition par le Responsable.
- Vérification de la quantité disponible.
- Transmission au Comptable.
- Gestion des modifications autorisées.
- Gestion du refus.
- Gestion de la confirmation.
- Gestion des annulations.
- Conservation de l'historique des décisions.

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

**Paiements**
- Enregistrement des paiements d'expéditions.
- Consultation des paiements.
- Historique financier.

**Rapports et Dashboard**
- Indicateurs financiers.
- Recettes.
- Dépenses.
- Paiements.
- Bilans par période.
- Informations globales nécessaires au Dashboard Comptable.
- Rapports PDF selon les besoins définis.

**Règle importante :** l'acceptation d'une demande de dépense ne crée pas automatiquement une dépense réelle. La dépense doit être enregistrée séparément après sa réalisation.

## Livrables du Sprint 3

- Services implémentés.
- Règles métier appliquées.
- Contrôle des permissions dans les Services.
- Gestion des erreurs.
- Transactions pour les opérations sensibles.
- Validation des données.
- Aucune logique métier importante uniquement dans les Controllers.

---

# 6. SPRINT 4 — TESTS BACKEND

**Durée estimée : 2 à 3 jours**

## Objectif

Vérifier les Models, DAO et Services avant de commencer le développement des interfaces graphiques.

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
- Journalisation des actions.
- Sauvegarde et restauration.

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

## Chef de projet

Responsabilités :
- Organiser les tests communs.
- Vérifier les résultats.
- Suivre les bugs.
- Prioriser les corrections.
- Vérifier les interactions entre les modules.
- Valider le Backend avant le développement des Controllers.

## Livrables du Sprint 4

- Tests Backend réussis.
- Bugs bloquants corrigés.
- Règles métier vérifiées.
- Permissions vérifiées.
- Backend stable.

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
└── SauvegardeController.java
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
- Sauvegarde et restauration.

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
- Dashboard Responsable.
- Historique des opérations de la ferme.

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

**Attention :** le Membre 5 reste propriétaire du module technique Expédition. Le Controller du Comptable doit appeler le Service correspondant pour confirmer ou refuser une expédition, sans créer un deuxième module Expédition.

## Livrables du Sprint 5

- Controllers créés.
- Services correctement appelés.
- Navigation préparée.
- Événements gérés.
- Gestion des erreurs d'interface.
- Respect des permissions.

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
src/main/resources/fxml/
├── WelcomeView.fxml
├── LoginView.fxml
├── AdminLoginView.fxml
├── UtilisateursView.fxml
├── DashboardAdminView.fxml
├── SupervisionView.fxml
├── HistoriqueAdminView.fxml
├── ParametresView.fxml
└── SauvegardeView.fxml
```

CSS :

```text
src/main/resources/css/
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
- Sauvegarde et restauration.

## Membre 3 — Fermes et Parcelles

FXML :

```text
src/main/resources/fxml/
├── FermesView.fxml
└── ParcellesView.fxml
```

CSS :

```text
src/main/resources/css/
└── fermes.css
```

Responsabilités :
- Liste des fermes.
- Formulaire d'ajout et modification.
- Détails d'une ferme.
- Liste des parcelles.
- Formulaires de parcelles.
- Recherche et filtres.

## Membre 4 — Ressources Humaines

FXML :

```text
src/main/resources/fxml/
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
src/main/resources/css/
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
src/main/resources/fxml/
├── RecoltesView.fxml
├── ExpeditionsView.fxml
├── StockView.fxml
├── ConfirmationExpeditionView.fxml
├── PresenceTransmissionView.fxml
├── DemandesDepensesResponsableView.fxml
├── DashboardResponsableView.fxml
└── HistoriqueResponsableView.fxml
```

CSS :

```text
src/main/resources/css/
└── responsable.css
```

Responsabilités :
- Gestion des récoltes.
- Consultation du stock.
- Création et suivi des expéditions.
- État des expéditions.
- Transmission des présences.
- Consultation des demandes de dépenses.
- Dashboard du Responsable.
- Historique opérationnel.

## Membre 6 — Comptabilité

FXML :

```text
src/main/resources/fxml/
├── ClientsView.fxml
├── ClientsFinanciersView.fxml
├── PaiementsView.fxml
├── DepensesView.fxml
├── DemandesDepensesComptableView.fxml
├── RapportsView.fxml
├── DocumentsView.fxml
├── NotificationsView.fxml
└── DashboardView.fxml
```

CSS :

```text
src/main/resources/css/
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

## Composants communs

Les composants communs doivent être attribués par le Chef de projet avant leur développement.

Fichiers concernés :

```text
src/main/resources/fxml/
├── MainLayoutView.fxml
├── SidebarView.fxml
├── TopbarView.fxml
└── ConfirmationDialog.fxml

src/main/resources/css/
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

## Membre 2 — Authentification et Administration

- Tester les quatre espaces.
- Tester le code alphanumérique Admin.
- Vérifier les permissions.
- Tester les paramètres système.
- Tester la sauvegarde et la restauration.
- Vérifier le journal des actions.

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
- Une base PostgreSQL intégrée.
- Une architecture MVC + DAO + Service cohérente.
- Une application intégrée, testée et stable.

---

**ORANGIA — De la conception à une application complète de gestion agricole.**