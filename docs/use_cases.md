# ORANGIA — USE CASES

**Version : 1.0**
**Statut : Final**
**Application : ORANGIA**

---

# 1. Authentification

## UC-01 : Sélectionner un espace

### Acteur principal
Utilisateur

### Description
Permet à l'utilisateur de sélectionner l'espace auquel il souhaite accéder.

### Espaces disponibles

- Administrateur
- Responsable de Ferme
- Agent RH
- Comptable

### Résultat

Le système affiche l'écran de connexion correspondant à l'espace choisi.

---

## UC-02 : Se connecter

### Acteur principal
Utilisateur

### Préconditions

- Le compte existe.
- Le compte est actif.

### Scénario principal

1. L'utilisateur saisit son identifiant.
2. L'utilisateur saisit son mot de passe.
3. Le système vérifie les informations.
4. Le système vérifie le rôle.
5. Le système vérifie que le rôle correspond à l'espace choisi.
6. Le système ouvre l'espace correspondant.

### Postconditions

- Session ouverte.
- Date de dernière connexion mise à jour.

---

## UC-03 : Demander une réinitialisation de mot de passe

### Acteur principal
Utilisateur

### Acteur secondaire
Administrateur

### Scénario principal

1. L'utilisateur clique sur « Mot de passe oublié ? ».
2. Il saisit son identifiant.
3. Le système enregistre la demande.
4. Une notification est envoyée à l'Administrateur.
5. L'Administrateur vérifie l'identité de l'utilisateur.
6. L'Administrateur réinitialise le mot de passe.
7. Le mot de passe temporaire devient :

   `CIN@0000`

8. L'utilisateur reçoit le nouveau mot de passe.
9. Lors de la prochaine connexion, le système oblige l'utilisateur à choisir un nouveau mot de passe.

### Postconditions

- Demande tracée dans le journal.
- Mot de passe changé.

---

## UC-04 : Se déconnecter

### Acteur principal
Utilisateur

### Résultat

- Fermeture de session.
- Retour à l'écran de sélection des espaces.

---

# 2. Administration

## UC-05 : Gérer les utilisateurs

### Acteur principal
Administrateur

### Actions

- Créer un utilisateur.
- Modifier un utilisateur.
- Activer un utilisateur.
- Désactiver un utilisateur.
- Réinitialiser un mot de passe.

---

## UC-06 : Affecter un Responsable à une ferme

### Acteur principal
Administrateur

### Règles

- Un Responsable possède une seule ferme.
- Une ferme possède un seul Responsable actif.

---

## UC-07 : Gérer les fermes

### Acteur principal
Administrateur

### Actions

- Ajouter une ferme.
- Modifier une ferme.
- Désactiver une ferme.
- Consulter les fermes.

---

## UC-08 : Gérer les parcelles

### Acteur principal
Administrateur

### Actions

- Ajouter une parcelle.
- Modifier une parcelle.
- Désactiver une parcelle.
- Consulter les parcelles.

---

## UC-09 : Gérer les paramètres système

### Acteur principal
Administrateur

### Actions

- Modifier les heures de travail.
- Modifier la prime Cabran.
- Modifier le seuil des absences autorisées.

---

## UC-10 : Consulter les journaux

### Acteur principal
Administrateur

### Actions

- Consulter les actions enregistrées.
- Rechercher une opération.
- Consulter les historiques.

---

# 3. Responsable de Ferme

## UC-11 : Consulter les informations de sa ferme

### Acteur principal
Responsable

### Actions

- Consulter sa ferme.
- Consulter ses parcelles.
- Consulter ses statistiques.

---

## UC-12 : Enregistrer une récolte

### Acteur principal
Responsable

### Actions

- Choisir une parcelle.
- Saisir la date.
- Saisir la quantité récoltée.
- Valider l'enregistrement.

---

## UC-13 : Annuler une récolte

### Acteur principal
Responsable

### Conditions

- Motif obligatoire.

### Résultat

- Récolte conservée.
- Exclue du calcul du stock.

---

## UC-14 : Créer une expédition

### Acteur principal
Responsable

### Actions

- Sélectionner un client.
- Choisir une date.
- Saisir la quantité physique.
- Ajouter des observations.
- Envoyer l'expédition au Comptable.

---

## UC-15 : Consulter le stock

### Acteur principal
Responsable

### Résultat

Le système affiche le stock calculé de sa ferme.

---

## UC-16 : Gérer les clients

### Acteur principal
Responsable

### Actions

- Ajouter un client.
- Modifier les informations générales.
- Désactiver un client.
- Consulter les clients.

---

## UC-17 : Préparer une fiche de présence

### Acteur principal
Responsable

### Actions

- Sélectionner la date.
- Sélectionner les ouvriers.
- Enregistrer les présences.
- Transmettre la fiche à RH.

---

## UC-18 : Créer une demande de correction

### Acteur principal
Responsable

### Actions

- Sélectionner une présence.
- Ajouter le motif.
- Envoyer la demande.

---

## UC-19 : Créer une demande de dépense

### Acteur principal
Responsable

### Actions

- Définir l'objet.
- Définir le montant estimé.
- Ajouter une justification.
- Envoyer la demande.

---

# 4. Ressources Humaines

## UC-20 : Gérer les ouvriers

### Acteur principal
RH

### Actions

- Ajouter un ouvrier.
- Modifier un ouvrier.
- Désactiver un ouvrier.
- Consulter les ouvriers.

---

## UC-21 : Affecter un ouvrier

### Acteur principal
RH

### Actions

- Affecter à une ferme.
- Modifier l'affectation.

### Règle

Une seule affectation active à la fois.

---

## UC-22 : Transférer un ouvrier

### Acteur principal
RH

### Résultat

- Historique conservé.
- Nouvelle affectation créée.

---

## UC-23 : Gérer les tarifs

### Acteur principal
RH

### Actions

- Créer un tarif.
- Modifier le tarif.
- Consulter l'historique.

---

## UC-24 : Valider les présences

### Acteur principal
RH

### Actions

- Vérifier la fiche.
- Valider.
- Retourner pour correction.

---

## UC-25 : Traiter une demande de correction

### Acteur principal
RH

### Actions

- Accepter.
- Refuser.
- Ajouter un commentaire.

---

## UC-26 : Calculer les salaires

### Acteur principal
RH

### Actions

- Sélectionner la période.
- Lancer le calcul.
- Générer les salaires.

---

## UC-27 : Enregistrer un paiement de salaire

### Acteur principal
RH

### Actions

- Sélectionner un salaire.
- Enregistrer le paiement.
- Mettre à jour le montant restant.

---

# 5. Comptabilité

## UC-28 : Vérifier une expédition

### Acteur principal
Comptable

### Actions

- Contrôler le stock.
- Vérifier les informations.

---

## UC-29 : Confirmer une expédition

### Acteur principal
Comptable

### Résultat

- Stock diminué.
- Expédition confirmée.

---

## UC-30 : Refuser une expédition

### Acteur principal
Comptable

### Conditions

- Motif obligatoire.

---

## UC-31 : Enregistrer un paiement client

### Acteur principal
Comptable

### Actions

- Sélectionner l'expédition.
- Enregistrer le paiement.
- Mettre à jour le solde.

---

## UC-32 : Traiter une demande de dépense

### Acteur principal
Comptable

### Actions

- Accepter.
- Refuser.
- Ajouter un commentaire.

---

## UC-33 : Enregistrer une dépense réelle

### Acteur principal
Comptable

### Actions

- Créer une dépense.
- Lier à une demande acceptée si nécessaire.
- Ajouter les justificatifs.

---

## UC-34 : Consulter les rapports financiers

### Acteur principal
Comptable

### Actions

- Consulter les recettes.
- Consulter les dépenses.
- Consulter les créances.
- Générer des rapports.

---

# 6. Services communs

## UC-35 : Gérer les documents

### Acteurs

- Responsable
- RH
- Comptable

### Actions

- Ajouter un document.
- Consulter un document.
- Télécharger un document.

---

## UC-36 : Consulter les notifications

### Acteurs

- Tous les utilisateurs

### Actions

- Consulter.
- Marquer comme lue.

---

## UC-37 : Consulter les historiques

### Acteurs

- Selon les permissions

### Actions

- Rechercher.
- Filtrer.
- Consulter les détails.

---

# 7. Calcul automatique du stock

## UC-38 : Consulter le stock calculé

### Acteurs

- Responsable
- Comptable
- Administrateur

### Formule

Stock disponible =
Total des récoltes enregistrées
−
Total des expéditions confirmées non annulées

### Règles

- Aucun stock manuel.
- Aucun mouvement manuel.
- Calcul automatique par ferme.

---

# Fin du document
