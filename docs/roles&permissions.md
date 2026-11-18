# ORANGIA — RÔLES & PERMISSIONS

**Version : 2.0 — Novembre 2026**  
**Application : Orangia — Gestion des fermes agricoles**  
**Architecture : Java 21 / JavaFX / PostgreSQL**

---

# 1. Objectif

Ce document définit les rôles, les responsabilités et les permissions des utilisateurs de l'application Orangia.

L'application comporte quatre rôles :

1. Administrateur
2. Responsable de Ferme
3. Agent RH
4. Comptable

Chaque rôle possède un espace de travail spécifique, avec des permissions adaptées à ses responsabilités.

## Principes fondamentaux

- Chaque utilisateur possède un seul rôle.
- Chaque Responsable est affecté à une seule ferme.
- RH et Comptable travaillent à l'échelle de toutes les fermes.
- Les ouvriers ne possèdent aucun compte applicatif.
- L'Administrateur gère les comptes, les fermes, les parcelles et les paramètres système.
- Le Responsable gère les opérations quotidiennes de sa ferme.
- RH gère le personnel, les présences et les salaires.
- Le Comptable gère les opérations financières et la confirmation des expéditions.
- Les données financières ne sont jamais accessibles au Responsable.
- Les permissions sont définies statiquement dans le code Java selon le rôle.
- Les tables PERMISSION et ROLE_PERMISSION ne sont pas utilisées par le système actuel.

---

# 2. Administrateur

## 2.1. Description

L'Administrateur est responsable de l'administration générale, de la configuration et de la supervision de l'application Orangia.

Il possède une visibilité globale sur les données et les activités des quatre espaces.

La supervision globale est principalement consultative : l'Administrateur ne remplace pas les responsables métier dans leurs opérations quotidiennes.

## 2.2. Gestion des utilisateurs et des comptes

- Créer un compte utilisateur.
- Modifier les informations d'un compte.
- Affecter un rôle.
- Affecter un Responsable à une ferme.
- Réinitialiser un mot de passe.
- Activer ou désactiver un compte.
- Consulter les utilisateurs.
- Consulter les informations des comptes.

Règles :
- L'Administrateur est le seul à gérer les comptes applicatifs.
- Un Responsable ne peut être affecté qu'à une seule ferme.
- Une ferme ne possède qu'un seul Responsable actif.
- RH et Comptable ne sont pas affectés individuellement à une ferme.

## 2.3. Gestion des fermes

- Ajouter une ferme.
- Modifier les informations d'une ferme.
- Activer ou désactiver une ferme.
- Consulter toutes les fermes.
- Affecter un Responsable à une ferme.

## 2.4. Gestion des parcelles

- Créer une parcelle.
- Modifier une parcelle.
- Désactiver une parcelle.
- Consulter toutes les parcelles.
- Consulter les parcelles par ferme.

## 2.5. Supervision globale

L'Administrateur peut consulter :

- Les ouvriers et leurs informations générales.
- Les présences et leurs historiques.
- Les récoltes.
- Les expéditions et leur état opérationnel.
- Les dépenses et les demandes de dépenses.
- Les salaires et les informations financières.
- Les clients.
- Les indicateurs globaux des fermes.

Cette supervision ne donne pas à l'Administrateur le rôle opérationnel de RH, de Responsable ou de Comptable.

## 2.6. Historique et rapports

- Consulter le journal d'activité.
- Consulter les actions des utilisateurs.
- Consulter les notifications de l'ensemble des utilisateurs.
- Consulter les historiques des opérations.
- Générer les rapports globaux.
- Consulter les statistiques générales.

## 2.7. Paramètres système

- Configurer l'heure de début du travail.
- Configurer l'heure de fin du travail.
- Configurer la prime de Cabran.
- Configurer le nombre maximal d'absences injustifiées autorisées pour conserver la prime.
- Gérer les paramètres généraux de l'application.
- Effectuer une sauvegarde manuelle de la base de données.
- Consulter l'historique des sauvegardes.
- Restaurer une sauvegarde après confirmation.

Règles de sauvegarde :
- Sauvegarde automatique quotidienne.
- Possibilité de sauvegarde manuelle.
- Conservation des sept dernières sauvegardes.
- Confirmation obligatoire avant une restauration.

## 2.8. Restrictions

- L'Administrateur ne remplace pas le Responsable dans la saisie des récoltes et la gestion opérationnelle des expéditions.
- Il ne remplace pas RH dans la gestion quotidienne du personnel et le calcul des salaires.
- Il ne remplace pas le Comptable dans la confirmation des expéditions et l'enregistrement des paiements.
- Il ne modifie pas les historiques métier pour contourner les règles de validation.

---

# 3. Responsable de Ferme

## 3.1. Description

Le Responsable assure la gestion opérationnelle quotidienne de la ferme qui lui est attribuée.

Il possède un accès limité à une seule ferme.

Il est responsable du suivi des récoltes, des expéditions, des présences et des demandes liées à sa ferme.

## 3.2. Gestion de sa ferme

- Consulter les informations de sa ferme.
- Consulter les statistiques de sa ferme.
- Consulter les parcelles de sa ferme.
- Consulter les informations générales des ouvriers affectés à sa ferme.

Restrictions :
- Ne peut pas créer ou modifier une ferme.
- Ne peut pas créer, modifier ou désactiver une parcelle.
- Ne peut pas consulter les autres fermes.
- Ne peut pas modifier les affectations des ouvriers.

## 3.3. Gestion des récoltes

- Ajouter une récolte.
- Consulter les récoltes de sa ferme.
- Modifier une récolte selon les règles autorisées.
- Annuler une récolte avec un motif obligatoire.
- Consulter l'historique des récoltes.

Règles :
- Une récolte est enregistrée sur une parcelle active.
- Plusieurs récoltes peuvent être enregistrées sur une même parcelle et le même jour.
- Une récolte annulée reste conservée dans l'historique.
- Les récoltes annulées ne sont pas comptabilisées dans le stock.

## 3.4. Gestion des expéditions

Le Responsable assure exclusivement la gestion opérationnelle des expéditions.

Il peut :

- Créer une expédition.
- Sélectionner un client.
- Définir la date d'expédition.
- Définir la quantité physique expédiée.
- Modifier les informations opérationnelles.
- Consulter les expéditions de sa ferme.
- Annuler une expédition selon les règles métier.
- Consulter le statut opérationnel des expéditions.

Règles :
- Une expédition est envoyée au Comptable pour confirmation.
- Une expédition non confirmée ne diminue pas le stock.
- Une modification opérationnelle après confirmation nécessite une nouvelle confirmation.
- Le Responsable ne confirme pas les expéditions.

Restrictions financières :
- Aucun accès au prix unitaire.
- Aucun accès au montant total.
- Aucun accès aux paiements.
- Aucun accès aux impayés.
- Aucun accès au statut financier.
- Aucune consultation des informations financières des clients.

Ces restrictions doivent être appliquées dans les DAO et les Services, et pas uniquement dans l'interface JavaFX.

## 3.5. Gestion des clients

La gestion des clients est partagée entre Responsable et Comptable.

Le Responsable peut :

- Ajouter un client.
- Consulter les clients.
- Modifier les informations générales d'un client.
- Rechercher un client.
- Désactiver un client.

Les clients liés à des opérations historiques ne sont pas supprimés physiquement.

## 3.6. Présences et demandes

- Consulter les ouvriers affectés à sa ferme.
- Enregistrer les présences quotidiennes.
- Enregistrer les absences et les retards.
- Enregistrer les heures d'arrivée et de départ.
- Consulter les horaires de travail applicables.
- Préparer la fiche quotidienne de présence.
- Transmettre la fiche à RH.
- Consulter l'état de transmission et de validation.
- Envoyer une demande de correction.
- Consulter la réponse de RH.

Règles :
- Le Responsable vérifie les présences à partir de la feuille quotidienne.
- Après transmission, il ne peut plus modifier directement la fiche.
- Toute correction passe par une demande traitée par RH.
- Les dimanches et jours de repos ne sont pas enregistrés comme absences.

## 3.7. Demandes de dépenses

Le Responsable peut :

- Créer une demande de dépense.
- Définir le sujet et la catégorie.
- Indiquer le montant estimé.
- Fournir une description et une justification.
- Joindre un document justificatif.
- Consulter le statut de la demande.
- Consulter le commentaire du Comptable.
- Annuler une demande selon les règles autorisées.

Règles :
- Le Responsable ne valide pas les demandes.
- Il ne crée pas de dépenses réelles.
- Il ne modifie pas les paiements.
- L'acceptation d'une demande ne crée pas automatiquement une dépense.

## 3.8. Historique et rapports

- Consulter l'historique de sa ferme.
- Consulter les récoltes précédentes.
- Consulter les expéditions.
- Consulter les fiches de présence transmises.
- Consulter les demandes de dépenses.
- Générer les rapports opérationnels de sa ferme.

Les informations financières confidentielles restent masquées.

## 3.9. Restrictions

- Aucun accès aux autres fermes.
- Aucun accès à la gestion des utilisateurs.
- Aucun accès à la gestion des rôles.
- Aucun accès aux paramètres système.
- Aucun accès à la gestion des salaires.
- Aucun accès aux paiements.
- Aucun accès aux prix et montants des expéditions.
- Aucun accès à la gestion des dépenses réelles.
- Aucune modification directe des fiches de présence transmises.

---

# 4. Agent RH

## 4.1. Description

L'Agent RH est responsable de la gestion du personnel, des présences et des rémunérations.

Son espace est global : il travaille sur l'ensemble des fermes.

Il est le seul responsable métier de la gestion administrative des ouvriers et du traitement des présences et des salaires.

## 4.2. Gestion du personnel

- Ajouter un ouvrier.
- Modifier les informations d'un ouvrier.
- Désactiver un ouvrier.
- Consulter les ouvriers.
- Rechercher un ouvrier.
- Consulter les informations professionnelles.
- Consulter l'historique d'un ouvrier.
- Gérer les catégories d'ouvriers : NORMAL, CABRAN et NAIB.

Les ouvriers ne possèdent aucun compte applicatif.

## 4.3. Gestion des affectations

- Affecter un ouvrier à une ferme.
- Transférer un ouvrier vers une autre ferme.
- Consulter les affectations actuelles.
- Consulter les anciennes affectations.
- Enregistrer le motif d'un transfert.

Règles :
- Un ouvrier ne peut avoir qu'une seule affectation active.
- Les anciennes affectations sont conservées.
- Le transfert ne modifie jamais les anciennes présences.
- Le transfert ne modifie jamais les anciens salaires.

## 4.4. Gestion des tarifs

- Définir le mode de rémunération.
- Définir le montant du tarif.
- Modifier le tarif en créant une nouvelle période.
- Consulter les tarifs actuels.
- Consulter l'historique des tarifs.

Modes de rémunération :
- HORAIRE
- JOURNALIER
- MENSUEL

Les anciens tarifs ne sont jamais écrasés.

## 4.5. Pointage et gestion des présences

- Consulter les fiches transmises par les Responsables.
- Valider les fiches de présence.
- Refuser une fiche ou demander une correction.
- Consulter les présences de toutes les fermes.
- Traiter les demandes de correction.
- Accepter ou refuser les justifications.
- Gérer les congés.
- Consulter les historiques de présence.

Statuts possibles :
- PRESENT
- ABSENT
- RETARD
- PARTIEL
- CONGE

Règles :
- RH est responsable de la validation des fiches.
- RH traite les corrections demandées.
- Les horaires appliqués à chaque présence sont conservés dans l'historique.
- Les jours de repos ne sont pas comptabilisés comme absences.

## 4.6. Gestion des salaires

- Calculer les salaires.
- Consulter les salaires par ouvrier et par ferme.
- Consulter les détails de calcul.
- Enregistrer les paiements de salaires.
- Enregistrer plusieurs paiements pour un même salaire.
- Consulter les montants payés.
- Consulter les restes à payer.
- Consulter l'historique des paiements.

RH est le seul responsable métier du calcul et du paiement des salaires.

## 4.7. Règle particulière de Cabran

- Le salaire de base est distinct de la prime.
- La prime est configurable par l'Administrateur.
- Jusqu'à deux absences injustifiées par mois : prime conservée.
- À partir de trois absences injustifiées : prime supprimée pour la période.
- Les justifications médicales acceptées ne sont pas comptabilisées.
- Les dimanches et jours de repos ne sont pas comptabilisés.

Naïb bénéficie également d'un tarif spécifique, défini dans son historique de rémunération.

## 4.8. Rapports RH

- Générer les rapports du personnel.
- Générer les rapports de présence.
- Générer les rapports d'absence et de retard.
- Générer les rapports de congés.
- Générer les rapports de salaires.
- Consulter les statistiques RH globales.

## 4.9. Restrictions

- Aucun accès à la gestion des utilisateurs applicatifs.
- Aucun accès à la gestion des rôles.
- Aucun accès aux paramètres système.
- Aucun accès aux sauvegardes et restaurations.
- Aucun accès aux revenus commerciaux.
- Aucun accès aux paiements clients.
- Aucun accès à la gestion des dépenses.
- Aucun accès à la confirmation financière des expéditions.

---

# 5. Comptable

## 5.1. Description

Le Comptable est responsable du suivi financier et comptable de l'ensemble des fermes.

Son espace est global : il peut consulter les données nécessaires de toutes les fermes.

Il gère les paiements, les recettes, les dépenses et les confirmations financières des expéditions.

## 5.2. Gestion des clients

La gestion des clients est partagée avec le Responsable.

Le Comptable peut :

- Ajouter un client.
- Consulter les clients.
- Modifier les informations générales d'un client.
- Rechercher un client.
- Désactiver un client.
- Consulter l'historique commercial.

## 5.3. Gestion des récoltes et du stock

- Consulter les récoltes de toutes les fermes.
- Consulter les quantités récoltées.
- Consulter le stock disponible par ferme.
- Consulter les quantités expédiées.
- Consulter les historiques de récoltes et d'expéditions.

Restrictions :
- Ne peut pas créer une récolte.
- Ne peut pas modifier une récolte.
- Ne peut pas annuler une récolte.
- Ne peut pas modifier manuellement le stock.

## 5.4. Confirmation et suivi des expéditions

Le Comptable est responsable de la confirmation financière des expéditions.

Il peut :

- Consulter les expéditions en attente.
- Consulter les informations opérationnelles.
- Vérifier la disponibilité du stock.
- Renseigner le prix unitaire.
- Renseigner la quantité facturée.
- Confirmer une expédition.
- Refuser une expédition avec un motif.
- Consulter les expéditions confirmées.
- Consulter les informations financières.
- Consulter les montants facturés.

Restrictions :
- Ne peut pas créer une expédition.
- Ne peut pas modifier la quantité physique expédiée.
- Ne peut pas modifier la date ou les informations opérationnelles.
- Ne peut pas annuler directement une expédition.
- Ne peut pas contourner les règles de confirmation.

Une modification opérationnelle après confirmation doit être effectuée par le Responsable et nécessite une nouvelle confirmation du Comptable.

## 5.5. Paiements et recettes

- Enregistrer les paiements des clients.
- Consulter les paiements.
- Consulter les montants facturés.
- Consulter les montants encaissés.
- Consulter les montants restants.
- Consulter les expéditions non payées.
- Consulter les expéditions partiellement payées.
- Consulter les expéditions totalement payées.
- Consulter les historiques financiers.

Le total des paiements ne peut jamais dépasser le montant total facturé.

## 5.6. Gestion des demandes de dépenses

- Consulter les demandes de toutes les fermes.
- Accepter une demande.
- Refuser une demande avec un commentaire.
- Consulter les demandes traitées.
- Consulter les historiques de demandes.

L'acceptation d'une demande ne constitue pas une dépense réelle.

## 5.7. Gestion des dépenses réelles

- Ajouter une dépense réelle.
- Modifier une dépense selon les règles autorisées.
- Consulter les dépenses.
- Rechercher les dépenses.
- Classer les dépenses par catégorie.
- Lier une dépense à une demande acceptée.
- Annuler une dépense avec un motif.
- Consulter l'historique des dépenses.

## 5.8. Rapports financiers

- Générer les bilans financiers.
- Générer les rapports des recettes.
- Générer les rapports des dépenses.
- Générer les rapports des paiements.
- Générer les rapports des impayés.
- Comparer les résultats des fermes.
- Exporter les rapports en PDF.
- Exporter les données autorisées en CSV.

Le Comptable peut consulter les coûts salariaux agrégés pour les bilans, mais ne gère pas les dossiers individuels des ouvriers ni les paiements de salaires.

## 5.9. Restrictions

- Aucun accès à la gestion des utilisateurs.
- Aucun accès à la gestion des rôles.
- Aucun accès aux paramètres système.
- Aucun accès aux sauvegardes et restaurations.
- Aucun accès à la gestion des présences.
- Aucun accès à la gestion des ouvriers.
- Aucun accès à la modification des récoltes.
- Aucun accès à la modification des quantités opérationnelles des expéditions.
- Aucun accès à la gestion des salaires individuels.

---

# 6. Tableau récapitulatif des permissions

Légende :

- **G** : Gestion complète dans le périmètre autorisé.
- **C** : Consultation uniquement.
- **O** : Gestion opérationnelle.
- **F** : Gestion financière.
- **T** : Traitement et validation.
- **S** : Supervision globale.
- **—** : Aucun accès.

| Module | Administrateur | Responsable | RH | Comptable |
|---|---|---|---|---|
| Utilisateurs | G | — | — | — |
| Rôles | Affectation | — | — | — |
| Fermes | G | C (sa ferme) | — | C |
| Parcelles | G | C (sa ferme) | — | C |
| Ouvriers | S | C (sa ferme) | G | — |
| Affectations ouvriers | S | — | G | — |
| Tarifs ouvriers | S | — | G | — |
| Fiches de présence | S | O (saisie/transmission) | T | — |
| Demandes de correction | S | Création/consultation | T | — |
| Salaires | S | — | G | C (agrégé) |
| Paiements salaires | S | — | G | C (agrégé) |
| Récoltes | S | O | — | C |
| Stock | S | C (sa ferme) | — | C |
| Clients | S | G | — | G |
| Expéditions | S | O | — | F + T |
| Paiements clients | S | — | — | G |
| Demandes de dépenses | S | Création/consultation | — | T |
| Dépenses réelles | S | — | — | G |
| Documents | S | Selon autorisation | Selon autorisation | Selon autorisation |
| Notifications | Toutes | Personnelles | Personnelles | Personnelles |
| Journal d'activité | S | — | — | — |
| Paramètres système | G | — | — | — |
| Sauvegardes | G | — | — | — |
| Rapports | Globaux | Opérationnels de sa ferme | RH globaux | Financiers globaux |

---

# 7. Règles communes de sécurité

## 7.1. Authentification

Tous les utilisateurs se connectent avec :

- Identifiant
- Mot de passe

Aucun code supplémentaire n'est demandé à l'Administrateur.

## 7.2. Profil utilisateur

- L'Administrateur peut gérer les informations de son propre profil.
- Le Responsable, RH et Comptable peuvent consulter leur profil.
- Seul l'Administrateur peut modifier les informations des comptes applicatifs.

## 7.3. Séparation des responsabilités

- Le Responsable gère les opérations de sa ferme.
- RH gère le personnel et les salaires.
- Le Comptable gère les opérations financières.
- L'Administrateur gère le système et supervise l'ensemble.

## 7.4. Protection des données financières

Les informations financières des expéditions ne doivent pas être retournées dans les requêtes DAO destinées au Responsable.

La sécurité doit être assurée dans les couches suivantes :

- Service : vérification du rôle et du périmètre.
- DAO : requêtes et projections adaptées aux permissions.
- Controller : accès uniquement aux actions autorisées.
- Interface : affichage des fonctionnalités autorisées.

## 7.5. Historique et traçabilité

Les opérations importantes doivent être enregistrées dans le journal d'activité.

Les annulations et les modifications conservent les informations historiques nécessaires.

---

# 8. Application des permissions dans l'architecture Java

Les permissions sont gérées statiquement dans le code Java.

Le système ne permet pas de modifier dynamiquement les droits d'un rôle depuis l'interface.

Chaque Service vérifie le rôle de l'utilisateur et son périmètre d'accès avant d'exécuter une opération.

Les DAO utilisent uniquement les projections nécessaires à chaque rôle.

Les Controllers ne doivent pas contenir les règles métier ni les requêtes SQL.

---

# 9. Décision finale

Ce document constitue la référence officielle des rôles et permissions de l'application Orangia.

Il doit être respecté lors de la conception et de l'implémentation :

- Des menus JavaFX.
- Des Controllers.
- Des Services.
- Des DAO.
- Des règles de sécurité.
- Des tests d'autorisation.

Toute modification des permissions doit être justifiée par une règle métier et validée avant son implémentation.