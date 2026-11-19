# 🍊 Orangia
<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?logo=openjdk" />
  <img src="https://img.shields.io/badge/JavaFX-21-blue" />
  <img src="https://img.shields.io/badge/PostgreSQL-18-blue?logo=postgresql" />
  <img src="https://img.shields.io/badge/Maven-Build-red?logo=apachemaven" />
  <img src="https://img.shields.io/badge/Platform-Desktop-green" />
</p>
## 📑 Table des matières

- [À propos du projet](#-à-propos-du-projet)
- [Objectifs](#-objectifs-du-projet)
- [Équipe](#-équipe-du-projet)
- [Rôles des utilisateurs](#-rôles-des-utilisateurs)
- [Technologies](#-technologies-utilisées)
- [Architecture](#-architecture-du-projet)
- [Structure du projet](#-structure-du-projet)
- [Modules fonctionnels](#-modules-fonctionnels)
- [Documentation](#-documentation)
- [Installation](#-installation)
- [État d'avancement](#-état-davancement-du-projet)
- [Licence](#-licence)

## À propos du projet

**Orangia** est une application desktop de gestion des fermes d'oranges, développée en Java.

L'application a pour objectif de centraliser la gestion des exploitations agricoles d'agrumes au sein d'une seule plateforme.

Elle permet de regrouper les différents aspects de la gestion agricole :

- Gestion des fermes
- Gestion des parcelles
- Gestion des ouvriers
- Gestion des présences
- Gestion des récoltes
- Gestion des expéditions
- Gestion des dépenses
- Gestion comptable
- Rapports et statistiques
- Gestion des utilisateurs et des permissions

---

## 🎯 Objectifs du projet

- Digitaliser la gestion agricole.
- Centraliser les données des exploitations.
- Assurer le suivi des récoltes.
- Optimiser la gestion des ouvriers.
- Améliorer le suivi financier.
- Générer des rapports et des statistiques fiables.

---

## 👥 Équipe du projet

### Chef de projet

- Ilyas el Koutou

### Membres de l'équipe

- Moncif Zouhair
- Fatine Chehoun
- Idriss Kabel
- Bassma Bouaou
- Imane El Guettabi

---

## 👤 Rôles des utilisateurs

Le système prévoit quatre rôles principaux :

- **Administrateur**
- **Responsable de Ferme**
- **Agent RH**
- **Comptable**

Chaque rôle dispose de permissions spécifiques selon ses responsabilités.

### 🔐 Authentification

Les comptes utilisateurs sont créés uniquement par l'administrateur.

Aucune inscription publique n'est autorisée.

---

## ⚙️ Technologies utilisées

### Backend
- Java
- JDBC

### Frontend
- JavaFX
- FXML

### Base de données
- PostgreSQL

### Gestion de projet
- Git
- GitHub

### Build Tool
- Maven

### Génération de rapports
- OpenPDF

---

## 🏗️ Architecture du projet

Le projet suit une architecture multicouche permettant de séparer les responsabilités :

```text
JavaFX View
     ↓
Controller
     ↓
Service
     ↓
DAO
     ↓
PostgreSQL
```

### Design Patterns utilisés

- **MVC** : séparation entre le modèle, la vue et le contrôleur.
- **DAO** : gestion des accès aux données.
- **Service Layer** : centralisation de la logique métier.

---

## 📂 Structure du projet

```text
orangia-projet/
│
├── docs/
│   └── diagrammes/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── orangia/
│   │   │           ├── controller/
│   │   │           ├── dao/
│   │   │           │   ├── impl/
│   │   │           │   └── interfaces/
│   │   │           ├── database/
│   │   │           ├── exceptions/
│   │   │           ├── models/
│   │   │           ├── security/
│   │   │           ├── service/
│   │   │           ├── util/
│   │   │           └── validation/
│   │   │
│   │   └── resources/
│   │       ├── css/
│   │       └── fxml/
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── orangia/
│                   ├── dao/
│                   └── service/
│
├── pom.xml
├── .gitignore
└── README.md
```

---

## 🚜 Modules fonctionnels

Le projet prévoit les modules suivants :

- Authentification
- Gestion des utilisateurs
- Gestion des rôles et des permissions
- Gestion des fermes
- Gestion des parcelles
- Gestion des ouvriers
- Gestion des présences
- Gestion des récoltes
- Gestion des expéditions
- Gestion des dépenses
- Gestion des stocks
- Gestion comptable
- Rapports et statistiques
- Notifications
- Journal d'audit
- Sauvegarde et restauration

---

## 📚 Documentation

La documentation du projet est organisée dans le dossier `docs/`.

Elle comprend notamment :

- Cahier des charges
- Planning du projet
- Architecture logicielle
- Diagrammes de cas d'utilisation (Use Cases)

---
## 💻 Installation et configuration

### Prérequis

Avant de lancer le projet, il faut installer :

- JDK 21
- Apache Maven
- PostgreSQL 18
- Visual Studio Code (ou un autre IDE Java)
 
### Exécution du projet

Pour lancer l'application, exécuter la commande suivante :

```bash
mvn javafx:run

### Cloner le projet

```bash
git clone URL_DU_REPOSITORY

## 🚀 État d'avancement du projet

| Phase | Statut |
|---|---|
| Analyse | ✅ Terminée |
| Cahier des charges | ✅ Terminé |
| Planning | ✅ Terminé |
| Architecture | ✅ Terminée |
| Use Cases | ✅ Terminés |
| Structure de la base de données | ✅ Créée |
| Connexion Java / PostgreSQL | ✅ Testée |
| Développement Backend | ⏳ En cours |
| Développement JavaFX | ⏳ En cours |
| Tests | ⏳ À réaliser |
| Livraison | ⏳ À réaliser |

---

## 🔗 Dépôt GitHub

Le code source du projet est disponible sur GitHub :

[Orangia - Repository](https://github.com/stv086440-lgtm/orangia-projet)

## 📄 Licence

Ce projet est réalisé dans le cadre d'un projet académique.

Année universitaire : 2026-2027.

Tous droits réservés aux membres de l'équipe du projet Orangia.

