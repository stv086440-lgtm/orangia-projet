# ORANGIA — ARCHITECTURE GLOBALE

*Application desktop de gestion d'une exploitation agricole spécialisée dans la production d'oranges*

*Version :* 3.1  
*Date :* Novembre 2026  
*Statut :* Référence architecturale cible  
*Base de données :* PostgreSQL  
*Langage :* Java 21 (LTS)  
*Interface graphique :* JavaFX 21  
*Architecture :* MVC + Service + DAO

---

# 1. Présentation générale

ORANGIA est une application desktop destinée à la gestion centralisée d'une entreprise agricole spécialisée dans la production et l'exploitation d'oranges.

Elle permet de gérer plusieurs fermes, leurs parcelles, les ouvriers, les présences, les récoltes, les expéditions, les clients, les dépenses et les opérations financières.

L'application comporte quatre espaces de travail correspondant aux responsabilités des utilisateurs :

1. Administrateur.
2. Responsable de Ferme.
3. Agent RH.
4. Comptable.

Chaque utilisateur accède uniquement aux fonctionnalités autorisées par son rôle.

## 1.1 Objectifs

- Centraliser les informations des différentes fermes.
- Gérer les parcelles et les opérations agricoles.
- Enregistrer et suivre les récoltes.
- Calculer automatiquement le stock disponible.
- Gérer les expéditions et les clients.
- Organiser les présences et les affectations des ouvriers.
- Calculer les salaires et suivre les paiements.
- Gérer les demandes de dépenses et les dépenses réelles.
- Assurer la traçabilité des opérations.
- Générer des rapports.
- Protéger les données selon les responsabilités des utilisateurs.

## 1.2 Périmètre fonctionnel

Le système comprend :

1. Authentification et gestion des comptes.
2. Gestion des fermes et des parcelles.
3. Gestion des ouvriers et des affectations.
4. Gestion des présences et des corrections.
5. Gestion des salaires et des paiements.
6. Gestion des récoltes.
7. Gestion des clients.
8. Gestion des expéditions.
9. Gestion des paiements clients.
10. Gestion des demandes de dépenses.
11. Gestion des dépenses réelles.
12. Gestion des documents.
13. Gestion des notifications.
14. Journalisation des actions.
15. Paramètres système.
16. Sauvegardes et restaurations.
17. Tableaux de bord et rapports.

*Limite du périmètre :* ORANGIA gère le stock d'oranges produites. La gestion des intrants agricoles, des engrais, des pesticides et du matériel n'est pas comprise dans cette version.

## 1.3 Principes fondamentaux

- Une seule application pour les quatre espaces.
- Une seule base de données centralisée.
- Une seule entité métier par objet, indépendamment du rôle.
- Les autorisations sont contrôlées dans les Services.
- Les données historiques sont conservées.
- Les opérations sensibles sont transactionnelles.
- Le stock disponible est calculé automatiquement.
- Aucun utilisateur ne doit pouvoir modifier directement une opération historique depuis une page de consultation.

---

# 2. Technologies utilisées

| Technologie | Version cible | Utilisation |
|---|---|---|
| Java | 21 LTS | Langage principal |
| JavaFX | 21 | Interface desktop |
| FXML | JavaFX 21 | Structure des interfaces |
| CSS | JavaFX CSS 21 | Design des interfaces |
| PostgreSQL | Version installée compatible | Base de données |
| JDBC | Driver PostgreSQL compatible | Communication avec la base |
| Maven | 3.9.x ou version compatible | Dépendances et compilation |
| JUnit 5 | Version compatible | Tests |
| Git | Version stable | Versionnement |
| GitHub | — | Collaboration |
| Visual Studio Code | — | Édition et développement |

## 2.1 Configuration du projet

L'environnement technique cible est basé sur :

- JDK 21.
- JavaFX 21.
- Maven 3.9.x.
- PostgreSQL.
- JDBC PostgreSQL.
- Classe principale : com.orangia.Main.

La classe Main étend javafx.application.Application.

Le projet doit être compilé et exécuté avec Java 21.

Le package racine existant com.orangia doit être conservé.

Aucun renommage global du package racine ne doit être effectué sans nécessité technique réelle.

Aucun renommage global de package ou changement de classe principale ne doit être réalisé sans vérification du code source et du pom.xml.

## 2.2 Configuration Maven

Le projet utilise notamment les dépendances suivantes :

- javafx-controls
- javafx-fxml
- postgresql
- junit-jupiter

Le pom.xml doit également contenir les plugins nécessaires :

- maven-compiler-plugin
- javafx-maven-plugin
- maven-surefire-plugin

La configuration doit être cohérente avec Java 21 et JavaFX 21.

La propriété de compilation Java doit être :

xml
<maven.compiler.release>21</maven.compiler.release>


La version exacte des dépendances doit être centralisée dans les propriétés Maven.

---

# 3. Architecture technique

ORANGIA utilise une architecture en couches basée sur MVC, complétée par Service et DAO.

## 3.1 Architecture générale

text
                 UTILISATEUR
                      |
                      v
                JAVA FX VIEW
                  FXML / CSS
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
                  JDBC / SQL
                      |
                      v
                 POSTGRESQL

              MODEL : OBJETS MÉTIER


Le Model représente les objets métier utilisés par les différentes couches.

## 3.2 View

Technologies : JavaFX 21, FXML et CSS.

Responsabilités :

- Afficher les informations.
- Présenter les formulaires.
- Afficher les tableaux.
- Proposer les actions disponibles.
- Afficher les messages.
- Assurer la navigation.

La View ne contient ni requêtes SQL ni règles métier.

## 3.3 Controller

Responsabilités :

- Récupérer les données saisies.
- Vérifier les données élémentaires de l'interface.
- Appeler les Services.
- Actualiser les composants graphiques.
- Présenter les résultats.
- Gérer les événements JavaFX.

Les Controllers ne doivent pas contenir les règles métier principales.

Ils ne doivent pas communiquer directement avec PostgreSQL.

## 3.4 Service

Le Service constitue le centre de la logique métier.

Responsabilités :

- Valider les opérations métier.
- Contrôler les autorisations.
- Vérifier les statuts.
- Appliquer les règles de gestion.
- Coordonner plusieurs DAO.
- Gérer les transactions.
- Garantir la cohérence des opérations.

Exemples :

- AuthService
- RecolteService
- ExpeditionService
- StockService
- PresenceService
- SalaireService
- DepenseService
- UtilisateurService


Les transactions doivent être exécutées au niveau de la couche Service ou d'un composant transactionnel appelé par celle-ci.

## 3.5 DAO

Le DAO assure la communication avec PostgreSQL.

Responsabilités :

- Exécuter les requêtes SQL.
- Effectuer les opérations CRUD.
- Charger les données.
- Convertir les résultats SQL en objets métier.
- Utiliser des requêtes préparées.
- Participer aux transactions avec une connexion partagée lorsque plusieurs opérations doivent être atomiques.

Les DAO ne doivent pas décider des autorisations métier.

## 3.6 Model

Le Model représente les entités métier.

Exemples :

- Utilisateur
- Role
- Ferme
- Parcelle
- Ouvrier
- AffectationOuvrier
- TarifOuvrier
- FichePresence
- Presence
- Recolte
- Client
- Expedition
- PaiementExpedition
- DemandeDepense
- Depense
- Salaire
- DemandeCorrection
- PaiementSalaire
- Document
- Notification
- JournalAction
- ParametreSysteme

## 3.7 Règles techniques supplémentaires

- Les requêtes JDBC ne doivent pas bloquer le JavaFX Application Thread.
- Les opérations longues doivent utiliser Task, Service JavaFX ou un ExecutorService.
- Les connexions JDBC doivent être correctement fermées.
- Les transactions doivent gérer explicitement commit et rollback.
- Les connexions doivent utiliser des paramètres de configuration et non des identifiants écrits directement dans le code.
- Les traitements de sécurité doivent être centralisés dans les composants dédiés.
- Les informations sensibles ne doivent jamais être enregistrées dans les logs.

---

# 4. Gestion des espaces et des rôles

ORANGIA comporte exactement quatre rôles prédéfinis.

## 4.1 Administrateur

L'Administrateur gère la configuration générale et supervise les activités de l'entreprise.

Fonctionnalités :

1. Tableau de bord.
2. Utilisateurs & espaces.
3. Fermes & parcelles.
4. Supervision globale.
5. Historique & rapports.
6. Paramètres système.

Autorisations :

- Créer et gérer les comptes.
- Activer et désactiver les comptes.
- Réinitialiser les mots de passe.
- Gérer les codes alphanumériques des comptes Administrateur selon les procédures de sécurité.
- Attribuer un Responsable à une ferme.
- Gérer les fermes.
- Gérer les parcelles.
- Consulter les opérations globales.
- Consulter les journaux.
- Configurer les paramètres.
- Effectuer les sauvegardes et restaurations.

L'Administrateur possède une visibilité globale.

La supervision est en lecture seule pour les opérations métier des autres espaces.

Il ne remplace pas le Responsable, RH ou le Comptable dans l'exécution de leurs opérations.

## 4.2 Responsable de Ferme

Chaque Responsable est associé à une seule ferme active.

Ses données sont automatiquement filtrées selon cette ferme.

Fonctionnalités :

1. Tableau de bord.
2. Récoltes.
3. Expéditions.
4. Présences & Demandes.
5. Historique.

Autorisations :

- Enregistrer les récoltes.
- Modifier les récoltes autorisées.
- Annuler les récoltes avec motif.
- Consulter le stock.
- Créer les expéditions.
- Modifier les expéditions selon leur statut.
- Soumettre une demande d'annulation ou de révision lorsque nécessaire.
- Gérer les informations opérationnelles des clients.
- Joindre les bons de livraison.
- Saisir les présences.
- Transmettre les fiches à RH.
- Soumettre des demandes de correction.
- Créer des demandes de dépenses.
- Consulter son historique.

Interdictions :

- Consulter les prix unitaires des expéditions.
- Consulter les montants financiers des clients.
- Consulter les paiements.
- Enregistrer les paiements clients.
- Confirmer financièrement une expédition.
- Gérer les dossiers RH.
- Modifier les affectations des ouvriers.
- Calculer ou payer les salaires.
- Gérer les dépenses réelles.
- Accéder aux données d'autres fermes.

## 4.3 Agent RH

RH est un espace global couvrant toutes les fermes.

Fonctionnalités :

1. Tableau de bord RH.
2. Gestion du personnel.
3. Pointage et gestion des salaires.
4. Génération des rapports RH.

Autorisations :

- Créer et gérer les dossiers des ouvriers.
- Affecter les ouvriers aux fermes.
- Transférer les ouvriers.
- Consulter les historiques d'affectation.
- Consulter les fiches de présence.
- Valider les présences.
- Traiter les demandes de correction.
- Gérer les justificatifs.
- Calculer les salaires.
- Enregistrer les paiements.
- Appliquer les règles particulières de rémunération.
- Générer les rapports RH.

Les ouvriers ne disposent pas de comptes de connexion.

RH conserve les historiques des affectations, présences et salaires.

## 4.4 Comptable

Le Comptable dispose d'un accès global aux informations financières autorisées.

Fonctionnalités :

1. Tableau de bord.
2. Récoltes & stock.
3. Clients & suivi commercial.
4. Dépenses & demandes de dépenses.
5. Historique & rapports.

Autorisations :

- Consulter les récoltes.
- Consulter le stock.
- Consulter et gérer les clients.
- Consulter les expéditions.
- Confirmer ou refuser les expéditions.
- Gérer les informations financières.
- Enregistrer les paiements clients.
- Consulter les recettes.
- Traiter les demandes de dépenses.
- Enregistrer les dépenses réelles.
- Consulter les coûts salariaux agrégés.
- Générer les rapports financiers.

Interdictions :

- Modifier les récoltes.
- Créer les expéditions à la place du Responsable.
- Modifier les quantités opérationnelles d'une expédition.
- Modifier les présences.
- Gérer les dossiers individuels RH.
- Calculer les salaires.
- Enregistrer les paiements de salaires.

---

# 5. Authentification et navigation

## 5.1 Parcours de connexion

Au lancement :

text
                LANCEMENT ORANGIA
                       |
                       v
             SÉLECTION DE L'ESPACE
                       |
          +------------+------------+
          |            |            |
          v            v            v
     ADMINISTRATEUR RESPONSABLE     RH
          |            |            |
          +------------+------------+
                       |
                       v
                   COMPTABLE


Chaque espace utilise le même mécanisme général d'authentification, mais les informations demandées dépendent du rôle sélectionné.

### 5.1.1 Authentification Administrateur

L'accès à l'espace Administrateur nécessite obligatoirement trois informations :

1. Identifiant.
2. Mot de passe.
3. Code alphanumérique.

Le code alphanumérique constitue une information de sécurité supplémentaire réservée au compte Administrateur.

*Champs du formulaire :*

| Champ | Description | Obligatoire |
|---|---|---|
| Identifiant | Identifiant unique du compte Administrateur | Oui |
| Mot de passe | Mot de passe du compte Administrateur | Oui |
| Code alphanumérique | Code personnel supplémentaire | Oui |

Le formulaire Administrateur doit être distinct du formulaire général utilisé par les autres espaces.

Le système vérifie :

1. L'existence du compte.
2. La validité du mot de passe.
3. La validité du code alphanumérique.
4. Le statut actif du compte.
5. La correspondance avec le rôle Administrateur.

L'accès est autorisé uniquement si les trois informations sont valides et si le compte est actif.

Le code alphanumérique doit être généré ou attribué de manière sécurisée lors de la création ou de la configuration du compte Administrateur.

Il ne doit jamais être enregistré en clair dans PostgreSQL.

### 5.1.2 Authentification des autres espaces

Les espaces suivants utilisent une authentification classique :

- Responsable de Ferme.
- Agent RH.
- Comptable.

L'authentification nécessite uniquement :

1. Identifiant.
2. Mot de passe.

Le système vérifie :

- L'existence du compte.
- Le mot de passe.
- Le statut actif.
- Le rôle.
- La correspondance entre le rôle et l'espace sélectionné.

Le code alphanumérique supplémentaire n'est pas demandé aux autres utilisateurs.

Un compte ne peut accéder qu'à son propre espace.

### 5.1.3 Vérification des erreurs

Messages possibles :

| Situation | Message |
|---|---|
| Identifiant incorrect | Identifiant ou informations de connexion incorrects. |
| Mot de passe incorrect | Identifiant ou informations de connexion incorrects. |
| Code incorrect | Identifiant ou informations de connexion incorrects. |
| Champ obligatoire vide | Veuillez remplir tous les champs obligatoires. |
| Compte désactivé | Ce compte est désactivé. |
| Mauvais espace | Ce compte n'appartient pas à l'espace sélectionné. |

Les erreurs d'identifiant, de mot de passe et de code Administrateur doivent utiliser un message générique afin de ne pas révéler quelle information est incorrecte.

Les tentatives de connexion doivent être journalisées selon les règles de sécurité.

### 5.2 Création des comptes

La création des comptes est réservée à l'Administrateur.

Aucune inscription publique n'est autorisée.

Pour le compte Administrateur :

- Un identifiant unique est défini.
- Un mot de passe sécurisé est défini.
- Un code alphanumérique personnel est attribué.
- Le mot de passe est stocké sous forme de hash.
- Le code alphanumérique est également stocké sous forme de hash.

Pour les autres comptes :

- Un identifiant unique est défini.
- Un mot de passe sécurisé est défini.
- Le hash du mot de passe est enregistré.

L'Administrateur ne peut jamais consulter les mots de passe ou les codes existants.

Il peut uniquement déclencher leur réinitialisation selon les procédures prévues.

### 5.3 Session

Après authentification, une session applicative conserve au minimum :

- ID utilisateur.
- Rôle.
- Statut du compte.
- ID de ferme pour le Responsable.
- Informations nécessaires au contrôle d'accès.

Le code alphanumérique et le mot de passe ne sont jamais conservés dans la session après leur vérification.

La déconnexion invalide la session et libère les ressources associées.

La session est utilisée par les Services pour déterminer les autorisations.

---

# 6. Règles métier communes

## 6.1 Gestion des fermes

- Une ferme possède plusieurs parcelles.
- Une parcelle appartient à une seule ferme.
- Un Responsable actif est affecté à une seule ferme.
- Une ferme ne possède qu'un Responsable principal actif à la fois.
- RH et Comptable disposent d'un accès global.
- Les données du Responsable sont automatiquement filtrées par sa ferme.

L'affectation du Responsable est enregistrée dans le compte utilisateur ou dans la relation retenue par le schéma existant.

## 6.2 Gestion des ouvriers

- Un ouvrier ne possède pas de compte applicatif.
- RH gère les dossiers.
- RH gère les affectations.
- Chaque affectation conserve sa période de validité.
- Un ouvrier ne peut avoir qu'une affectation principale active à la fois.
- Les transferts conservent l'historique.
- Les tarifs sont historisés.
- Cabran et Naïb restent des ouvriers ordinaires dans la base.

Le transfert d'un ouvrier ne modifie jamais les anciennes présences ou les anciens salaires.

## 6.3 Présences

Le processus est le suivant :

1. Le Responsable prépare la fiche.
2. Il sélectionne la date.
3. Il sélectionne les ouvriers de sa ferme.
4. Il coche les absences.
5. Il joint le justificatif si disponible.
6. Il transmet la fiche à RH.
7. RH vérifie et valide ou retourne la fiche.
8. Les corrections suivent une demande spécifique.

Statuts :

- BROUILLON
- TRANSMISE
- EN_TRAITEMENT
- VALIDEE
- RETOURNEE

Après transmission, le Responsable ne peut plus modifier directement la fiche.

Les corrections passent par DEMANDE_CORRECTION.

Une fiche représente une journée pour une ferme.

Une seule fiche active par ferme et par date est autorisée.

Les dimanches et jours de repos configurés ne sont pas considérés comme des absences.

Chaque enregistrement historique conserve :

- La date.
- Les horaires appliqués.
- Le statut.
- L'ouvrier.
- La ferme concernée.
- Les informations de validation.

Les justificatifs acceptés restent conservés.

## 6.4 Salaires

RH est responsable du calcul et du paiement des salaires.

Principes :

- Le salaire journalier dépend des journées rémunérables.
- Une absence injustifiée sur un jour ouvrable ne génère pas de salaire journalier.
- Le salaire mensuel fixe suit les règles contractuelles définies.
- Les paiements partiels sont autorisés.
- Les paiements historiques ne sont jamais remplacés par un nouveau calcul.
- Les tarifs applicables sont historisés.

Chaque salaire calculé conserve un instantané des paramètres utilisés : tarif, période, absences prises en compte, bonus et montant calculé.

*Règle de Cabran :*

- Jusqu'à deux absences injustifiées dans le mois : bonus conservé.
- À partir de trois absences injustifiées : bonus mensuel perdu.
- Les justificatifs médicaux acceptés ne comptent pas comme absences injustifiées.
- Les dimanches et jours de repos ne sont pas comptabilisés comme absences injustifiées.
- Le bonus est configurable par l'Administrateur.

*Règle de Naïb :*

- Sa rémunération suit ses règles spécifiques.
- La règle du bonus de Cabran ne lui est pas appliquée automatiquement.

Toute modification future des paramètres ne doit pas modifier les salaires déjà calculés.

## 6.5 Récoltes

- Une récolte appartient à une parcelle.
- Une parcelle appartient à une ferme.
- Plusieurs récoltes sont possibles pour la même parcelle et la même journée.
- La quantité doit être strictement positive.
- Une récolte valide augmente le stock.
- Une récolte annulée reste dans l'historique.
- Une récolte annulée n'est plus prise en compte dans le stock.

Toute modification importante est journalisée.

## 6.6 Expéditions

Le processus est partagé entre Responsable et Comptable.

*Responsable :*

- Crée l'expédition.
- Renseigne le client.
- Renseigne la date.
- Renseigne la quantité physique expédiée.
- Ajoute le bon de livraison.
- Modifie les informations opérationnelles autorisées.
- Soumet les demandes de révision ou d'annulation selon le statut.

*Comptable :*

- Vérifie l'expédition.
- Confirme ou refuse.
- Définit les informations financières.
- Gère les paiements.
- Traite les conséquences financières d'une annulation.

Statuts :

- BROUILLON
- EN_ATTENTE_CONFIRMATION
- CONFIRMEE
- REFUSEE
- REVISION_EN_ATTENTE
- ANNULATION_EN_ATTENTE
- ANNULEE

Règles :

1. Une expédition non confirmée ne réduit pas le stock.
2. Une expédition confirmée réduit le stock.
3. Une expédition refusée ne réduit pas le stock.
4. Une expédition annulée restaure la quantité physique précédemment déduite.
5. Une expédition confirmée ne peut pas être modifiée directement.
6. Une modification opérationnelle nécessite une révision et une nouvelle confirmation.
7. L'ancienne version confirmée reste consultable.
8. Les paiements historiques ne sont jamais supprimés automatiquement.
9. Une annulation après paiement nécessite un traitement financier par le Comptable.
10. La quantité physique expédiée est distincte de la quantité facturée.
11. Le stock est contrôlé de façon transactionnelle lors de la confirmation.
12. Deux confirmations simultanées ne doivent jamais permettre un stock négatif.

Le prix unitaire et le montant facturé sont réservés au Comptable.

La transition entre statuts est centralisée dans ExpeditionService.

## 6.7 Paiements clients

- Un paiement est toujours lié à une expédition.
- Plusieurs paiements partiels sont autorisés.
- Le total net des paiements ne peut pas dépasser le montant dû.
- Les paiements historiques ne sont pas modifiés ou supprimés pour corriger une erreur.
- Les remboursements et annulations financières sont enregistrés comme opérations distinctes.
- Le solde est calculé à partir des opérations financières valides.

Une transaction doit protéger le contrôle du solde contre les paiements simultanés.

## 6.8 Dépenses

Deux éléments métier distincts sont utilisés.

*Demande de dépense :*

- Créée par le Responsable.
- Envoyée au Comptable.
- Acceptée ou refusée avec commentaire.
- Conservée dans l'historique.

*Dépense réelle :*

- Enregistrée par le Comptable après réalisation de l'achat.
- Contient le montant réellement payé.
- Peut être liée à la demande initiale.
- Conserve son justificatif.

L'acceptation d'une demande ne crée pas automatiquement une dépense réelle.

## 6.9 Stock

Le stock est calculé par ferme.

text
STOCK DISPONIBLE =
TOTAL DES RÉCOLTES VALIDES
-
TOTAL DES EXPÉDITIONS CONFIRMÉES NON ANNULÉES


Principes :

- Le stock n'est jamais saisi manuellement.
- Une récolte valide augmente le stock.
- Une expédition confirmée diminue le stock.
- Une expédition annulée restaure le stock.
- Les modifications d'une opération confirmée passent par le processus de révision.
- Le stock ne peut jamais être négatif.

La vue V_STOCK_FERME fournit le résultat calculé.

La vue ne remplace pas les contrôles transactionnels réalisés pendant les opérations.

## 6.10 Clients

- Les clients sont partagés entre Responsable et Comptable.
- Le Responsable gère les informations opérationnelles.
- Le Comptable gère les informations financières.
- Les clients liés à des opérations historiques ne sont pas supprimés physiquement.
- Un client peut être désactivé.
- Les informations financières ne sont pas accessibles au Responsable.

## 6.11 Documents et notifications

Les documents peuvent être associés aux opérations concernées :

- Expéditions.
- Fiches de présence.
- Demandes de correction.
- Demandes de dépenses.
- Dépenses réelles.

Les fichiers sont stockés dans un emplacement contrôlé, distinct du code source.

La base conserve leurs métadonnées et leur référence de stockage.

Les notifications sont générées à la suite d'événements métier :

- Nouvelle expédition à confirmer.
- Expédition refusée.
- Fiche de présence transmise.
- Correction demandée.
- Demande de dépense acceptée ou refusée.
- Autres événements nécessaires.

Les notifications sont destinées aux utilisateurs concernés.

## 6.12 Journalisation

Les opérations importantes sont enregistrées dans JOURNAL_ACTION.

Informations minimales :

- Utilisateur.
- Action.
- Module.
- Entité concernée.
- Identifiant de l'entité.
- Date et heure.
- Anciennes valeurs et nouvelles valeurs lorsque nécessaire.
- Résultat.
- Motif lorsque nécessaire.

Le journal est consultable, mais ses anciennes entrées ne peuvent pas être modifiées par les utilisateurs ordinaires.

Les événements de sécurité sont également journalisés, notamment :

- Connexions réussies.
- Tentatives de connexion échouées.
- Tentatives d'accès à un espace non autorisé.
- Réinitialisations de mots de passe.
- Modifications du code Administrateur.
- Désactivations de comptes.

Aucune valeur de mot de passe ou de code alphanumérique ne doit apparaître dans le journal.

---

# 7. Conception de la base de données

PostgreSQL est la base de données centrale d'ORANGIA.

Le modèle métier cible présenté comprend 22 tables et une vue calculée.

## 7.1 Tables métier

| N° | Table | Responsabilité |
|---|---|---|
| 1 | ROLE | Rôles prédéfinis |
| 2 | UTILISATEUR | Comptes et authentification |
| 3 | FERME | Exploitations agricoles |
| 4 | PARCELLE | Parcelles des fermes |
| 5 | OUVRIER | Dossiers du personnel |
| 6 | AFFECTATION_OUVRIER | Historique des affectations |
| 7 | TARIF_OUVRIER | Historique des tarifs |
| 8 | FICHE_PRESENCE | Fiches quotidiennes |
| 9 | PRESENCE | Présences individuelles |
| 10 | DEMANDE_CORRECTION | Demandes de correction |
| 11 | SALAIRE | Salaires calculés |
| 12 | PAIEMENT_SALAIRE | Paiements des salaires |
| 13 | RECOLTE | Opérations de récolte |
| 14 | CLIENT | Clients |
| 15 | EXPEDITION | Expéditions |
| 16 | PAIEMENT_EXPEDITION | Paiements et remboursements |
| 17 | DEMANDE_DEPENSE | Demandes de dépenses |
| 18 | DEPENSE | Dépenses réellement effectuées |
| 19 | DOCUMENT | Métadonnées des documents |
| 20 | NOTIFICATION | Notifications |
| 21 | JOURNAL_ACTION | Traçabilité |
| 22 | PARAMETRE_SYSTEME | Paramètres applicatifs |

## 7.2 Vue calculée

V_STOCK_FERME

Elle calcule le stock disponible pour chaque ferme.

Elle utilise les récoltes valides et les quantités physiques des expéditions confirmées non annulées.

## 7.3 Relations principales

- ROLE 1,N UTILISATEUR.
- FERME 1,N PARCELLE.
- FERME 1,N AFFECTATION_OUVRIER.
- OUVRIER 1,N AFFECTATION_OUVRIER.
- OUVRIER 1,N TARIF_OUVRIER.
- FERME 1,N FICHE_PRESENCE.
- FICHE_PRESENCE 1,N PRESENCE.
- OUVRIER 1,N PRESENCE.
- PRESENCE 1,N DEMANDE_CORRECTION selon le modèle de corrections retenu.
- OUVRIER 1,N SALAIRE.
- SALAIRE 1,N PAIEMENT_SALAIRE.
- PARCELLE 1,N RECOLTE.
- FERME 1,N EXPEDITION.
- CLIENT 1,N EXPEDITION.
- EXPEDITION 1,N PAIEMENT_EXPEDITION.
- FERME 1,N DEMANDE_DEPENSE.
- FERME 1,N DEPENSE.

Les relations doivent être matérialisées par des clés étrangères et des contraintes adaptées.

## 7.4 Affectation des utilisateurs aux fermes

Le modèle retenu est volontairement simple :

- Un Responsable possède une seule ferme active.
- RH possède un accès global.
- Comptable possède un accès global.
- Administrateur possède un accès global.

L'identifiant de la ferme du Responsable peut être stocké dans UTILISATEUR.ferme_id.

Une table UTILISATEUR_FERME n'est pas nécessaire dans ce modèle.

Si les décisions métier changent ultérieurement pour permettre plusieurs affectations par utilisateur, une relation intermédiaire devra être ajoutée dans une migration dédiée.

## 7.5 Informations d'authentification

La table UTILISATEUR conserve les informations nécessaires à l'authentification.

Champs fonctionnels :

| Champ | Responsabilité |
|---|---|
| id | Identifiant du compte |
| identifiant | Identifiant unique de connexion |
| mot_de_passe_hash | Hash du mot de passe |
| code_acces_hash | Hash du code alphanumérique Administrateur |
| role_id | Rôle associé au compte |
| ferme_id | Ferme associée au Responsable |
| actif | Statut du compte |

Les noms définitifs des colonnes doivent rester cohérents avec le schéma PostgreSQL réellement existant.

Règles :

- identifiant est unique.
- mot_de_passe_hash est obligatoire.
- code_acces_hash est obligatoire pour le compte Administrateur.
- code_acces_hash reste NULL pour les autres rôles.
- Le code original n'est jamais stocké.
- Le code doit être vérifié par le Service d'authentification.
- Le changement du code doit être contrôlé et journalisé.
- Le hash du mot de passe ne peut jamais être remplacé par une valeur en clair.

L'ajout de code_acces_hash ne nécessite pas la création d'une nouvelle table.

## 7.6 Tables non incluses dans le modèle métier cible

Les tables suivantes ne sont pas nécessaires au fonctionnement minimal retenu :

- EXPEDITION_RECOLTE.
- PRODUIT_STOCK.
- MOUVEMENT_STOCK.
- UTILISATEUR_FERME.
- PERMISSION.
- ROLE_PERMISSION.

Cependant, leur présence dans une base existante doit être auditée.

Aucune de ces tables ne doit être supprimée automatiquement.

EXPEDITION_RECOLTE pourra être étudiée si la traçabilité des lots de récolte devient une exigence.

UTILISATEUR_FERME pourra être étudiée si plusieurs affectations par utilisateur deviennent nécessaires.

PRODUIT_STOCK et MOUVEMENT_STOCK ne sont pas nécessaires tant que le périmètre reste limité au stock global d'oranges calculé.

## 7.7 Contraintes générales

La base doit protéger les données à l'aide de :

- PRIMARY KEY.
- FOREIGN KEY.
- NOT NULL.
- UNIQUE.
- CHECK.
- Index adaptés.
- Transactions.

Exemples :

- Quantités strictement positives.
- Montants financiers non négatifs.
- Un identifiant de connexion unique.
- Une référence de ferme unique.
- Une affectation active unique par ouvrier.
- Une fiche active unique par ferme et par date.
- Un paiement lié à une expédition existante.

Les suppressions physiques sont interdites pour les opérations historiques importantes.

---

# 8. Structure globale du projet

La structure cible conserve le package racine réellement utilisé dans le projet :

com.orangia

text
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
│   ├── role&permission.md
│   ├── usecases.md
│   ├── planning.md
│   └── diagrammes/
│
├── database/
│   ├── migrations/
│   ├── views/
│   │   └── v_stock_ferme.sql
│   ├── seeds/
│   └── README.md
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── orangia/
│   │   │           ├── Main.java
│   │           ├── config/
│   │           ├── navigation/
│   │           ├── model/
│   │           ├── dao/
│   │           │   └── impl/
│   │           ├── service/
│   │           ├── controller/
│   │           │   ├── auth/
│   │           │   ├── admin/
│   │           │   ├── responsable/
│   │           │   ├── rh/
│   │           │   ├── comptable/
│   │           │   └── common/
│   │           ├── security/
│   │           ├── validation/
│   │           ├── util/
│   │           └── exception/
│   │
│   └── resources/
│       ├── fxml/
│       │   ├── auth/
│       │   ├── admin/
│       │   ├── responsable/
│       │   ├── rh/
│       │   ├── comptable/
│       │   └── common/
│       ├── css/
│       ├── images/
│       └── reports/
│
└── src/test/
    └── java/
        └── com/
            └── orangia/
                ├── security/
                ├── service/
                ├── validation/
                └── integration/


Cette structure est une organisation cible.

Les fichiers existants doivent être conservés et réorganisés uniquement si une migration de packages est réellement nécessaire.

## 8.1 Responsabilité des packages

- config/ : configuration générale et connexion.
- navigation/ : navigation entre les interfaces.
- model/ : objets métier.
- dao/ : interfaces d'accès aux données.
- dao/impl/ : implémentations JDBC.
- service/ : règles métier.
- controller/ : contrôleurs JavaFX.
- security/ : authentification et autorisations.
- validation/ : validations réutilisables.
- util/ : outils communs.
- exception/ : exceptions applicatives.

## 8.2 Organisation de l'authentification

Le package security/ contient les composants nécessaires à la protection des accès.

text
security/
│
├── AuthService.java
├── PasswordHasher.java
├── SessionManager.java
├── AccessControl.java
|__ RoleAccess.java
└── AuthorizationService.java

Responsabilités :

- AuthService : authentification des quatre rôles.
- PasswordHasher : hachage et vérification des mots de passe.
- SessionManager : création, consultation et invalidation des sessions.
- AccessControl : contrôle des autorisations.
- RoleAccess : définition des accès selon les rôles.

Les Controllers d'authentification se trouvent dans controller/auth/.

text
auth/
│
├── WorkspaceSelectionController.java
├── LoginController.java
└── AdminLoginController.java


Les interfaces FXML correspondantes se trouvent dans resources/fxml/auth/.

text
auth/
│
├── workspace-selection.fxml
├── login.fxml
└── admin-login.fxml


Le formulaire Administrateur est distinct du formulaire des autres utilisateurs.

## 8.3 Règles d'organisation

- Une entité métier est partagée entre les rôles.
- Les DAO sont centralisés.
- Les interfaces et implémentations DAO restent dans la même arborescence.
- Les composants communs sont réutilisés.
- Les dossiers ne sont créés que selon les besoins.
- target/ est généré par Maven et ne doit pas être versionné.
- Les fichiers de configuration contenant des secrets ne doivent pas être versionnés.
- Les composants d'authentification ne doivent pas être dupliqués dans les espaces métier.

---
### ARCHITECTURE STRUCTURELLE MVC — ORANGIA ###

**Version : 1.0**  
**Projet : ORANGIA — Application Desktop de Gestion Agricole**  
**Architecture : MVC + Service + DAO**  
**Technologies : Java 21, JavaFX, FXML, CSS, PostgreSQL, JDBC, Maven**

## 1. Structure générale du projet

```text
orangia/
│
├── pom.xml
├── README.md
├── .gitignore
│
├── database/
│   └── migrations/
│       ├── 001_schema.sql
│       ├── 002_seed.sql
│       └── 003_views.sql
│
├── src/
│   │
│   ├── main/
│   │   │
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── orangia/
│   │   │           │
│   │   │           ├── Main.java
│   │   │           │
|   |   |           |
│   │   │           ├── controller/
│   │   │           │   ├── common/
│   │   │           │   │   ├── WorkspaceController.java
│   │   │           │   │   ├── LoginController.java
│   │   │           │   │   └── MainLayoutController.java
│   │   │           │   │
│   │   │           │   ├── admin/
│   │   │           │   │   ├── AdminDashboardController.java
│   │   │           │   │   ├── UtilisateursController.java
│   │   │           │   │   ├── FermesController.java
│   │   │           │   │   ├── ParcellesController.java
│   │   │           │   │   ├── ParametresController.java
│   │   │           │   │   └── AuditController.java
│   │   │           │   │
│   │   │           │   ├── responsable/
│   │   │           │   │   ├── ResponsableDashboardController.java
│   │   │           │   │   ├── RecoltesController.java
│   │   │           │   │   ├── PresencesController.java
│   │   │           │   │   ├── DemandesDepensesController.java
│   │   │           │   │   └── ExpeditionsController.java
│   │   │           │   │
│   │   │           │   ├── rh/
│   │   │           │   │   ├── RHDashboardController.java
│   │   │           │   │   ├── OuvriersController.java
│   │   │           │   │   ├── AffectationsController.java
│   │   │           │   │   ├── ValidationPresencesController.java
│   │   │           │   │   ├── SalairesController.java
│   │   │           │   │   └── PaiementsSalairesController.java
│   │   │           │   │
│   │   │           │   └── comptable/
│   │   │           │       ├── ComptableDashboardController.java
│   │   │           │       ├── ClientsController.java
│   │   │           │       ├── StockController.java
│   │   │           │       ├── ConfirmationsExpeditionsController.java
│   │   │           │       ├── PaiementsExpeditionsController.java
│   │   │           │       ├── DepensesController.java
│   │   │           │       └── RapportsFinanciersController.java
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
│   │   │           │   ├── interfaces/
│   │   │           │   └── impl/
│   │   │           │       ├── UtilisateurDAOImpl.java
│   │   │           │       ├── FermeDAOImpl.java
│   │   │           │       ├── ParcelleDAOImpl.java
│   │   │           │       ├── OuvrierDAOImpl.java
│   │   │           │       ├── PresenceDAOImpl.java
│   │   │           │       ├── SalaireDAOImpl.java
│   │   │           │       ├── RecolteDAOImpl.java
│   │   │           │       ├── ClientDAOImpl.java
│   │   │           │       ├── ExpeditionDAOImpl.java
│   │   │           │       ├── DepenseDAOImpl.java
│   │   │           │       └── ...
│   │   │           │
│   │   │           ├── service/
│   │   │           │   ├── AuthService.java
│   │   │           │
│   │   │           │   ├── UtilisateurService.java
│   │   │           │
│   │   │           │   ├── FermeService.java
│   │   │           │   ├── ParcelleService.java
│   │   │           │
│   │   │           │   ├── OuvrierService.java
│   │   │           │   ├── AffectationService.java
│   │   │           │   ├── PresenceService.java
│   │   │           │   ├── SalaireService.java
│   │   │           │   ├── RecolteService.java
│   │   │           │   ├── StockService.java
│   │   │           │   ├── ClientService.java
│   │   │           │   ├── ExpeditionService.java
│   │   │           │   ├── PaiementService.java
│   │   │           │   ├── DepenseService.java
│   │   │           │   ├── NotificationService.java
│   │   │           │   ├── RapportService.java
│   │   │           │   └── AuditService.java
│   │   │           │
│   │   │           ├── database/
│   │   │           │   ├── DatabaseConnection.java
│   │   │           │   ├── DatabaseConfig.java
│   │   │           │   └── BaseDAO.java
│   │   │           ├── security/
│   │   │           │   ├── PasswordHasher.java
│   │   │           │   ├── SessionManager.java
│   │   │           │   ├── AuthorizationService.java
│   │   │           │   └── PermissionChecker.java
│   │   │           │
│   │   │           ├── validation/
│   │   │           │   ├── InputValidator.java
│   │   │           │   ├── LoginValidator.java
│   │   │           │   └── FormValidator.java
│   │   │           │
│   │   │           ├── exception/
│   │   │           │   ├── DatabaseException.java
│   │   │           │   ├── BusinessException.java
│   │   │           │   └── AuthenticationException.java
│   │   │           │
│   │   │           └── util/
│   │   │               ├── DateUtils.java
│   │   │               ├── CurrencyUtils.java
│   │   │               ├── PdfGenerator.java
│   │   │               └── NavigationManager.java
│   │   │
│   │   └── resources/
│   │       └── com/
│   │           └── orangia/
│   │               │
│   │               ├── view/
│   │               │   ├── common/
│   │               │   │   ├── workspace.fxml
│   │               │   │   ├── login.fxml
│   │               │   │   └── main-layout.fxml
│   │               │   │
│   │               │   ├── admin/
│   │               │   │   ├── dashboard.fxml
│   │               │   │   ├── utilisateurs.fxml
│   │               │   │   ├── fermes.fxml
│   │               │   │   ├── parcelles.fxml
│   │               │   │   └── parametres.fxml
│   │               │   │
│   │               │   ├── responsable/
│   │               │   │   ├── dashboard.fxml
│   │               │   │   ├── recoltes.fxml
│   │               │   │   ├── presences.fxml
│   │               │   │   ├── demandes-depenses.fxml
│   │               │   │   └── expeditions.fxml
│   │               │   │
│   │               │   ├── rh/
│   │               │   │   ├── dashboard.fxml
│   │               │   │   ├── ouvriers.fxml
│   │               │   │   ├── affectations.fxml
│   │               │   │   ├── presences.fxml
│   │               │   │   └── salaires.fxml
│   │               │   │
│   │               │   └── comptable/
│   │               │       ├── dashboard.fxml
│   │               │       ├── clients.fxml
│   │               │       ├── stock.fxml
│   │               │       ├── expeditions.fxml
│   │               │       ├── paiements.fxml
│   │               │       └── depenses.fxml
│   │               │
│   │               ├── css/
│   │               │   ├── global.css
│   │               │   ├── login.css
│   │               │   ├── dashboard.css
│   │               │   └── components.css
│   │               │
│   │               ├── images/
│   │               │   ├── logo/
│   │               │   ├── icons/
│   │               │   └── backgrounds/
│   │               │
│   │               └── fonts/
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── orangia/
│                   ├── service/
│                   ├── dao/
│                   ├── security/
│                   └── validation/
│
└── docs/
    ├── descreption-de-project/
    |        ├── espc contable.pdf
    |        ├── espc admin.pdf
    |        ├── espc responsable.pdf
    |        └── espc rh.pdf
    ├── diagrammes/
    |       ├── cas-d'etulisation.pdf
    |       └── D-de-classe.pdf
    ├── general/
    |       ├──cahier des charges.pdf
    |       └── orangia use case.pdf
    ├── architecture.md
    ├── role-permission.md
    ├── usecases.md
    └── database.md
```

## 2. Rôle de chaque couche

| Couche | Responsabilité |
|---|---|
| Model | Représenter les données et les objets métier |
| View | Afficher les interfaces JavaFX avec FXML |
| Controller | Recevoir les actions de l'utilisateur et gérer l'interface |
| Service | Appliquer les règles métier et gérer les opérations |
| DAO | Communiquer avec PostgreSQL via JDBC |
| Database | Gérer la connexion à la base de données |
| Security | Authentification, sessions et autorisations |
| Validation | Vérifier les données saisies |
| Util | Fonctions techniques réutilisables |

## 3. Règle de communication entre les couches

```text
Utilisateur
    │
    ▼
View (FXML)
    │
    ▼
Controller
    │
    ▼
Service
    │
    ▼
DAO
    │
    ▼
PostgreSQL
```

Le retour des données suit le chemin inverse.

**Exemple : ajouter un ouvrier**

1. `ouvriers.fxml` affiche le formulaire.
2. `OuvriersController.java` récupère les champs.
3. `OuvrierService.java` vérifie les règles métier.
4. `OuvrierDAOImpl.java` exécute la requête SQL.
5. PostgreSQL enregistre l'ouvrier.
6. Le Controller actualise l'interface.

## 4. Règles obligatoires de développement

1. Aucun SQL dans les Controllers.
2. Aucune règle métier importante dans les Controllers.
3. Les Services sont responsables des règles métier.
4. Les DAO sont responsables des requêtes SQL.
5. Les Views contiennent uniquement la structure graphique.
6. Les permissions sont vérifiées côté Service, pas uniquement en cachant les boutons.
7. Les transactions JDBC sont gérées au niveau Service lorsqu'une opération implique plusieurs requêtes.
8. Les mots de passe ne sont jamais enregistrés en clair.
9. Les fichiers FXML doivent être associés à leurs Controllers.
10. Les tests doivent couvrir les règles métier et les opérations sensibles.

## 5. Convention de nommage

| Élément | Convention | Exemple |
|---|---|---|
| Classes Java | PascalCase | `OuvrierService` |
| Méthodes | camelCase | `ajouterOuvrier()` |
| Variables | camelCase | `nomOuvrier` |
| Interfaces DAO | Nom + DAO | `OuvrierDAO` |
| Implémentations DAO | Nom + DAO + Impl | `OuvrierDAOImpl` |
| Fichiers FXML | kebab-case | `liste-ouvriers.fxml` |
| Fichiers CSS | kebab-case | `dashboard.css` |

## 6. Remarque importante

Cette structure représente l'organisation cible. Les fichiers ne doivent pas tous être créés immédiatement : chaque membre crée les packages et classes nécessaires à sa tâche, puis les intègre progressivement.

Avant toute création ou modification dans le projet existant, vérifier le package racine réellement utilisé dans le code et le `pom.xml`. Ne pas effectuer de renommage global sans audit préalable.

# 9. Organisation des fonctionnalités par espace

## 9.1 Administrateur

Menu principal :

1. Tableau de bord.
2. Utilisateurs & espaces.
3. Fermes & parcelles.
4. Supervision globale.
5. Historique & rapports.
6. Paramètres système.

Contrôleurs indicatifs :

| Fonctionnalité | Controller |
|---|---|
| Tableau de bord | AdminDashboardController |
| Utilisateurs | UtilisateursController |
| Fermes | FermesController |
| Parcelles | ParcellesController |
| Supervision | SupervisionController |
| Historique | HistoriqueAdminController |
| Paramètres | ParametresSystemeController |

Les utilisateurs, les rôles et les affectations sont gérés dans la même fonctionnalité.

Le Responsable est associé à une ferme unique.

L'authentification Administrateur reste indépendante de ces fonctionnalités et est gérée par les composants communs d'authentification.

## 9.2 Responsable

Menu principal :

1. Tableau de bord.
2. Récoltes.
3. Expéditions.
4. Présences & Demandes.
5. Historique.

Contrôleurs indicatifs :

| Fonctionnalité | Controller |
|---|---|
| Dashboard | ResponsableDashboardController |
| Récoltes | RecoltesController |
| Expéditions | ExpeditionsController |
| Présences | PresencesResponsableController |
| Demandes | DemandesResponsableController |
| Historique | HistoriqueResponsableController |

Les demandes de dépenses et les corrections de présence sont intégrées dans Présences & Demandes.

Les cinq indicateurs du Dashboard sont :

- Récoltes aujourd'hui.
- Expéditions aujourd'hui.
- Stock disponible.
- Présences envoyées.
- Demandes en attente.

## 9.3 RH

Menu principal :

1. Tableau de bord RH.
2. Gestion du personnel.
3. Pointage & salaires.
4. Rapports RH.

Contrôleurs indicatifs :

| Fonctionnalité | Controller |
|---|---|
| Dashboard | RHDashboardController |
| Personnel | GestionPersonnelController |
| Présences | PresencesRHController |
| Salaires | SalairesController |
| Rapports | RapportsRHController |

Les affectations, transferts, corrections et paiements restent des sous-fonctionnalités.

## 9.4 Comptable

Menu principal :

1. Tableau de bord.
2. Récoltes & stock.
3. Clients & suivi commercial.
4. Dépenses & demandes.
5. Historique & rapports.

Contrôleurs indicatifs :

| Fonctionnalité | Controller |
|---|---|
| Dashboard | ComptableDashboardController |
| Récoltes et stock | RecoltesStockController |
| Clients et commercial | ClientsController |
| Dépenses | DepensesController |
| Demandes | DemandesDepensesController |
| Rapports | RapportsComptableController |

Les confirmations et les paiements d'expédition sont intégrés au suivi commercial.

Les noms de classes sont indicatifs et doivent rester cohérents avec les classes déjà présentes.

---

# 10. Sécurité et contrôle d'accès

## 10.1 Authentification

L'authentification repose sur deux mécanismes selon le rôle.

*Administrateur :*

- Identifiant.
- Mot de passe.
- Code alphanumérique.

*Responsable, RH et Comptable :*

- Identifiant.
- Mot de passe.

Les mots de passe ne sont jamais enregistrés en clair.

Le mécanisme de hachage utilise une fonction adaptée, par exemple PBKDF2-HMAC-SHA256 avec un sel aléatoire et des paramètres de coût conformes aux recommandations de sécurité.


Les paramètres de hachage sont versionnés afin de permettre leur évolution.

### 10.1.1 PasswordHasher

La classe PasswordHasher centralise :

- Le hachage des mots de passe.
- La génération du sel.
- La vérification des mots de passe.
- L'évolution des paramètres.

### 10.1.2 AdminCodeHasher

La classe AdminCodeHasher centralise :


- La génération du sel.
- La vérification du code.
- L'évolution des paramètres de sécurité.

Le code original ne doit jamais être stocké en clair.

### 10.1.3 AuthService

La classe AuthService gère le processus général d'authentification.

Elle doit :

1. Recevoir les informations de connexion.
2. Identifier le compte.
3. Vérifier son statut.
4. Vérifier le rôle.
5. Vérifier le mot de passe.
6. Vérifier le code supplémentaire si le compte est Administrateur.
7. Créer la session en cas de succès.
8. Journaliser le résultat de la tentative.

L'Administrateur peut réinitialiser un mot de passe, mais ne peut jamais consulter le mot de passe existant.

Il ne peut pas non plus consulter le code alphanumérique existant.

## 10.2 Contrôle des rôles

Les classes principales sont :

- Role.java
- AccessControl.java
- RoleAccess.java

Les quatre rôles sont prédéfinis.

Les autorisations sont définies dans le code.

Il n'est pas nécessaire de développer un système dynamique de création de permissions pour cette version.

## 10.3 Isolation des données

Le Responsable ne peut accéder qu'à sa ferme.

Les Services déterminent la ferme à partir du contexte de session.

Ils ne doivent jamais faire confiance à un identifiant de ferme librement transmis par l'interface.

Les requêtes DAO appliquent les restrictions nécessaires.

Le Responsable ne doit jamais récupérer les informations financières simplement en contournant un écran.

RH et Comptable disposent d'un accès global selon leurs responsabilités.

## 10.4 Protection de PostgreSQL

L'application ne doit jamais utiliser un compte PostgreSQL superutilisateur.

Les identifiants de connexion doivent être protégés.

Le compte de l'application doit disposer uniquement des privilèges nécessaires.

Pour une connexion distante, la communication doit être protégée par TLS.

Le contrôle d'accès dans Java ne doit pas être présenté comme une protection absolue contre un utilisateur disposant d'un accès direct aux identifiants de la base ou au système de fichiers.

## 10.5 Profil

Chaque utilisateur peut consulter les informations de son profil.

Les actions de modification dépendent du rôle et des autorisations prévues.

La gestion des rôles et des comptes des autres utilisateurs reste réservée à l'Administrateur.

## 10.6 Protection contre les tentatives répétées

Les tentatives répétées de connexion doivent être contrôlées.

Le système peut appliquer :

- Une limitation temporaire des tentatives.
- Un délai progressif entre les tentatives.
- Une journalisation des échecs.
- Une procédure de déblocage contrôlée.

Ces protections doivent être appliquées au processus d'authentification sans divulguer les informations sensibles.

---

# 11. Gestion des erreurs et validation

Les validations métier sont réalisées principalement dans les Services.

Les validations réutilisables peuvent être regroupées dans validation/.

Exemples :

- Champs obligatoires.
- Formats.
- Valeurs positives.
- Dates cohérentes.
- Doublons.
- Stock disponible.
- Droits d'accès.
- Statuts.
- Relations entre les entités.
- Paiements et soldes.
- Affectations et périodes.
- Identifiants de connexion.
- Longueur et format du code alphanumérique.
- Vérification des informations d'authentification.

Les erreurs techniques ne sont jamais affichées directement à l'utilisateur.

Les messages affichés doivent être compréhensibles.

La classe AlertUtil peut centraliser les alertes JavaFX.

Les exceptions doivent être journalisées avec les informations techniques nécessaires au diagnostic.

Les mots de passe et codes alphanumériques ne doivent jamais apparaître dans les messages d'erreur ou les logs.

---

# 12. Tests et qualité

## 12.1 Tests unitaires

Ils couvrent :

- Authentification.
- Vérification des mots de passe.
- Vérification du code alphanumérique Administrateur.
- Création et invalidation des sessions.
- Autorisations.
- Calcul du stock.
- Validation des récoltes.
- Transitions d'expédition.
- Calcul des salaires.
- Règles de Cabran et Naïb.
- Paiements partiels.
- Demandes de dépenses.
- Corrections de présence.

## 12.2 Tests d'intégration

Ils couvrent :

- Connexion PostgreSQL.
- Requêtes JDBC.
- Contraintes.
- Clés étrangères.
- Transactions.
- Chargement FXML.
- Interactions entre modules.
- Conservation des historiques.
- Création des comptes.
- Authentification Administrateur.
- Authentification des autres rôles.

## 12.3 Tests de sécurité

Vérifier notamment :

- Un Administrateur ne peut pas se connecter sans code alphanumérique.
- Un Administrateur ne peut pas se connecter avec un code incorrect.
- Un Administrateur ne peut pas se connecter avec un mot de passe incorrect.
- Un Responsable ne peut pas accéder à l'espace Administrateur.
- Un compte inactif ne peut pas se connecter.
- Un utilisateur ne peut pas contourner les restrictions en modifiant les paramètres d'un écran.
- Un Responsable ne peut pas consulter une autre ferme.
- Un Responsable ne peut pas accéder aux paiements.
- Un Responsable ne peut pas confirmer financièrement une expédition.
- Un Comptable ne peut pas modifier une récolte.
- RH ne peut pas modifier les quantités d'une expédition.
- Le code alphanumérique n'est jamais stocké en clair.
- Les mots de passe ne sont jamais stockés en clair.
- Les sessions sont invalidées après déconnexion.

## 12.4 Environnements

Deux environnements sont séparés.

*Production / développement principal*

- PostgreSQL.
- Port par défaut : 5432.
- Base : orangia.

*Tests*

- PostgreSQL.
- Port : 5433.
- Base recommandée : orangia_test.

Les identifiants de connexion sont configurés séparément.

Les tests ne doivent jamais se connecter à la base principale par défaut.

Les paramètres de test doivent être explicites.

---

# 13. Migrations de la base de données

Toutes les modifications de schéma sont versionnées dans database/migrations/.

Chaque migration possède :

- Un numéro unique.
- Un nom explicite.
- Une description.
- Une date.
- Un objectif.
- Une procédure de vérification.

Une migration appliquée ne doit pas être modifiée arbitrairement.

Les corrections sont réalisées dans de nouvelles migrations.

Un registre technique doit permettre de connaître les migrations réellement exécutées.

Les fichiers SQL et l'état réel de PostgreSQL doivent être vérifiés séparément.

Les migrations existantes doivent être auditées avant toute modification.

Aucune table ou donnée historique ne doit être supprimée sans validation.

Les migrations doivent être testées sur la base de test avant leur application sur la base principale.

## 13.1 Migration liée à l'authentification Administrateur

La modification du système d'authentification nécessite l'ajout du champ suivant à la table UTILISATEUR si celui-ci n'existe pas déjà :

code_acces_hash

Ce champ permet de stocker le hash du code alphanumérique Administrateur.

Règles :

- Le champ est obligatoire pour le compte Administrateur.
- Le champ reste NULL pour les autres rôles.
- Le code original ne doit jamais être enregistré.
- Les données existantes doivent être vérifiées avant l'application de la migration.
- Le compte Administrateur existant doit recevoir un code sécurisé selon une procédure d'initialisation contrôlée.

Aucune nouvelle table métier n'est nécessaire pour cette modification.

La migration doit être testée sur orangia_test avant son application sur la base principale.

---

# 14. Organisation du développement en équipe

Le principe obligatoire est :

*ESPACE PAR ESPACE → FONCTIONNALITÉ PAR FONCTIONNALITÉ.*

Chaque membre prend en charge une fonctionnalité complète.

Une fonctionnalité comprend :

1. Base de données si nécessaire.
2. Model.
3. DAO.
4. Service.
5. Controller.
6. FXML.
7. CSS.
8. Tests.

Le travail n'est pas divisé artificiellement entre équipes Frontend, Backend et Database.

Les composants communs sont développés une seule fois et réutilisés.

## 14.1 Phases de développement

| Phase | Contenu |
|---|---|
| Phase 0 | Fondations, configuration, authentification et sécurité |
| Phase 1 | Administrateur |
| Phase 2 | Responsable |
| Phase 3 | RH |
| Phase 4 | Comptable |
| Phase 5 | Intégration et tests finaux |

Les dépendances métier doivent être respectées.

Les modules partagés ne doivent pas être dupliqués pour chaque espace.

La Phase 0 doit notamment finaliser :

- Configuration Java 21 et JavaFX 21.
- Configuration PostgreSQL et JDBC.
- Structure générale du projet.
- Authentification des quatre rôles.
- Authentification renforcée Administrateur.
- Gestion des sessions.
- Contrôle des rôles.
- Navigation entre les espaces.

## 14.2 Validation d'une fonctionnalité

Une fonctionnalité est terminée lorsque :

1. Le modèle de données est cohérent.
2. Le Model fonctionne.
3. Le DAO fonctionne.
4. Le Service applique les règles métier.
5. Les autorisations sont vérifiées.
6. Le Controller fonctionne.
7. La View est intégrée.
8. Le CSS respecte le design global.
9. Les tests passent.
10. La fonctionnalité ne casse pas les autres modules.
11. Le code est prêt à être intégré dans dev.

---

# 15. Git et GitHub

## 15.1 Branches

- main : version stable.
- dev : développement et intégration.
- feature/... : nouvelles fonctionnalités.
- fix/... : corrections.

Exemple :

text
main
 |
 dev
 |
 +-- feature/authentication
 |
 +-- feature/admin-fermes
 |
 +-- feature/admin-utilisateurs
 |
 +-- feature/responsable-recoltes
 |
 +-- feature/responsable-expeditions
 |
 +-- feature/rh-personnel
 |
 +-- feature/rh-presences
 |
 +-- feature/comptable-depenses


## 15.2 Règles de collaboration

- Chaque membre travaille sur sa branche.
- Chaque fonctionnalité possède une branche dédiée.
- Les commits sont réguliers.
- Les membres récupèrent les dernières modifications de dev.
- Les Pull Requests sont vérifiées.
- main reste stable.
- Les modifications des fichiers partagés sont coordonnées.
- Les conflits sont résolus avant la fusion.
- Chaque fusion doit être testée.

## 15.3 Commits

bash
feat(auth): add workspace selection
feat(auth): add administrator alphanumeric code
feat(admin): implement farm management
feat(admin): add user management
feat(responsable): add harvest management
feat(responsable): add expedition management
feat(rh): implement attendance validation
feat(comptable): add expense management
fix(stock): prevent negative stock
test(auth): add administrator authentication tests
test(expedition): add transaction tests
docs(architecture): update architecture


---

# 16. Principes fondamentaux

1. Utiliser Java 21 LTS et JavaFX 21.
2. Respecter MVC + Service + DAO.
3. Ne jamais mettre de SQL dans les Controllers.
4. Centraliser les règles métier dans les Services.
5. Contrôler les autorisations à chaque opération sensible.
6. Isoler les données des Responsables par ferme.
7. Ne jamais exposer les informations financières au Responsable.
8. Utiliser trois informations d'authentification pour l'Administrateur.
9. Utiliser deux informations d'authentification pour les autres utilisateurs.
10. Ne jamais stocker les mots de passe en clair.
11. Ne jamais stocker le code alphanumérique en clair.
12. Conserver les historiques.
13. Ne jamais supprimer physiquement une opération métier importante.
14. Protéger les transactions.
15. Ne jamais permettre un stock négatif.
16. Ne jamais modifier automatiquement les anciens paiements.
17. Ne pas dupliquer les entités métier par rôle.
18. Tester avant intégration.
19. Respecter le travail des autres membres.
20. Documenter les décisions techniques.
21. Vérifier le code existant avant toute modification structurelle.
22. Préserver les migrations appliquées.
23. Maintenir une cohérence entre Architecture, Use Cases, Permissions et Database Design.

---

# 17. Documents de référence

L'architecture globale doit rester cohérente avec :

- cahier-des-charges.md
- role&permission.md
- usecases.md
- conception-bdd.md
- planning.md
- pom.xml
- Le code source réellement présent dans le dépôt.
- Les migrations réellement exécutées dans PostgreSQL.

En cas de contradiction, les décisions métier validées doivent être réconciliées avant modification du code.

Une modification de l'architecture doit être documentée et validée par l'équipe.

Les documents de référence doivent également respecter les versions techniques suivantes :

- Java 21.
- JavaFX 21.
- PostgreSQL.
- JDBC.
- Maven 3.9.x.

---

# CONCLUSION

ORANGIA repose sur une architecture desktop structurée, avec quatre espaces de travail, une base de données centralisée et une séparation claire des responsabilités.

L'application utilise *Java 21 (LTS), JavaFX 21, PostgreSQL, JDBC et Maven*.

L'authentification de l'Administrateur bénéficie d'une protection supplémentaire reposant sur trois informations obligatoires :

- Identifiant.
- Mot de passe.
- Code alphanumérique.

Les autres utilisateurs accèdent à leurs espaces à l'aide de leur identifiant et de leur mot de passe.

Les informations d'authentification sont protégées par des mécanismes de hachage adaptés.

L'architecture MVC + Service + DAO permet de séparer l'interface, les règles métier et l'accès aux données.

Les opérations sensibles, particulièrement les expéditions, les stocks, les présences et les paiements, sont protégées par des règles métier et des transactions.

La structure proposée doit être implémentée progressivement, sans supprimer ni remplacer arbitrairement les éléments déjà développés.

*Objectif final : construire ORANGIA comme une application cohérente, sécurisée, maintenable et professionnelle, en respectant les besoins réels de l'exploitation agricole et sans complexité inutile.*
<!-- Architecture update - 18/11/2026 -->
<!-- TEST UPDATE -->