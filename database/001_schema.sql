-- ============================================================
-- Orangia — Script de création de la base de données
-- Version: 001
-- SGBD: PostgreSQL
-- Description: Schéma complet de la base Orangia
-- ============================================================

-- Création de la base (exécuter séparément si nécessaire)
-- CREATE DATABASE orangia;

-- ============================================================
-- EXTENSION
-- ============================================================
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- ============================================================
-- ROLES & PERMISSIONS
-- ============================================================

CREATE TABLE IF NOT EXISTS role (
    id_role      SERIAL PRIMARY KEY,
    nom_role     VARCHAR(50)  NOT NULL UNIQUE,
    description  TEXT
);

CREATE TABLE IF NOT EXISTS permission (
    id_permission  SERIAL PRIMARY KEY,
    nom_permission VARCHAR(100) NOT NULL UNIQUE,
    description    TEXT
);

CREATE TABLE IF NOT EXISTS role_permission (
    id_role       INT NOT NULL REFERENCES role(id_role)             ON DELETE CASCADE,
    id_permission INT NOT NULL REFERENCES permission(id_permission) ON DELETE CASCADE,
    PRIMARY KEY (id_role, id_permission)
);

-- ============================================================
-- UTILISATEURS
-- ============================================================

CREATE TABLE IF NOT EXISTS utilisateur (
    id_utilisateur SERIAL PRIMARY KEY,
    identifiant    VARCHAR(50)  NOT NULL UNIQUE,
    mot_de_passe   VARCHAR(255) NOT NULL,  -- Stocké en BCrypt
    nom            VARCHAR(100) NOT NULL,
    prenom         VARCHAR(100) NOT NULL,
    actif          BOOLEAN      NOT NULL DEFAULT TRUE,
    date_creation  TIMESTAMP    NOT NULL DEFAULT NOW(),
    id_role        INT          NOT NULL REFERENCES role(id_role)
);

CREATE TABLE IF NOT EXISTS utilisateur_ferme (
    id_utilisateur INT NOT NULL REFERENCES utilisateur(id_utilisateur) ON DELETE CASCADE,
    id_ferme       INT NOT NULL,  -- FK vers ferme, ajoutée après
    PRIMARY KEY (id_utilisateur, id_ferme)
);

-- ============================================================
-- FERMES
-- ============================================================

CREATE TABLE IF NOT EXISTS ferme (
    id_ferme      SERIAL PRIMARY KEY,
    code_ferme    VARCHAR(20)   NOT NULL UNIQUE,
    nom           VARCHAR(150)  NOT NULL,
    localisation  TEXT,
    superficie    DECIMAL(10,2) CHECK (superficie >= 0),
    description   TEXT,
    statut        VARCHAR(20)   NOT NULL DEFAULT 'ACTIVE'
                  CHECK (statut IN ('ACTIVE', 'INACTIVE')),
    date_creation TIMESTAMP     NOT NULL DEFAULT NOW()
);

-- Ajout de la FK de utilisateur_ferme vers ferme
ALTER TABLE utilisateur_ferme
    ADD CONSTRAINT fk_utilisateur_ferme_ferme
    FOREIGN KEY (id_ferme) REFERENCES ferme(id_ferme) ON DELETE CASCADE;

-- ============================================================
-- PARCELLES
-- ============================================================

CREATE TABLE IF NOT EXISTS parcelle (
    id_parcelle      SERIAL PRIMARY KEY,
    code_parcelle    VARCHAR(20)   NOT NULL UNIQUE,
    nom              VARCHAR(150)  NOT NULL,
    superficie       DECIMAL(10,2) CHECK (superficie >= 0),
    variete_orange   VARCHAR(100),
    nombre_arbres    INT           CHECK (nombre_arbres >= 0),
    annee_plantation INT           CHECK (annee_plantation >= 1900 AND annee_plantation <= EXTRACT(YEAR FROM NOW())),
    statut           VARCHAR(20)   NOT NULL DEFAULT 'ACTIVE'
                     CHECK (statut IN ('ACTIVE', 'INACTIVE')),
    id_ferme         INT           NOT NULL REFERENCES ferme(id_ferme) ON DELETE RESTRICT
);

-- ============================================================
-- OUVRIERS
-- ============================================================

CREATE TABLE IF NOT EXISTS ouvrier (
    id_ouvrier        SERIAL PRIMARY KEY,
    code_ouvrier      VARCHAR(20)   NOT NULL UNIQUE,
    nom               VARCHAR(100)  NOT NULL,
    prenom            VARCHAR(100)  NOT NULL,
    telephone         VARCHAR(20),
    adresse           TEXT,
    date_embauche     DATE          NOT NULL,
    salaire_journalier DECIMAL(10,2) NOT NULL DEFAULT 0 CHECK (salaire_journalier >= 0),
    statut            VARCHAR(20)   NOT NULL DEFAULT 'ACTIF'
                      CHECK (statut IN ('ACTIF', 'INACTIF', 'SUSPENDU')),
    id_ferme          INT           NOT NULL REFERENCES ferme(id_ferme) ON DELETE RESTRICT
);

-- ============================================================
-- PRESENCES
-- ============================================================

CREATE TABLE IF NOT EXISTS presence (
    id_presence    SERIAL PRIMARY KEY,
    date_presence  DATE         NOT NULL,
    statut         VARCHAR(20)  NOT NULL
                   CHECK (statut IN ('PRESENT', 'ABSENT', 'RETARD', 'CONGE')),
    heure_arrivee  TIME,
    heure_depart   TIME,
    remarque       TEXT,
    id_ouvrier     INT          NOT NULL REFERENCES ouvrier(id_ouvrier) ON DELETE RESTRICT,
    UNIQUE (id_ouvrier, date_presence)
);

-- ============================================================
-- RECOLTES
-- ============================================================

CREATE TABLE IF NOT EXISTS recolte (
    id_recolte        SERIAL PRIMARY KEY,
    reference_recolte VARCHAR(30)   NOT NULL UNIQUE,
    date_recolte      DATE          NOT NULL,
    quantite_kg       DECIMAL(10,2) NOT NULL CHECK (quantite_kg > 0),
    qualite           VARCHAR(20)   CHECK (qualite IN ('PREMIUM', 'STANDARD', 'BASSE')),
    remarque          TEXT,
    id_parcelle       INT           NOT NULL REFERENCES parcelle(id_parcelle) ON DELETE RESTRICT
);

-- ============================================================
-- EXPEDITIONS
-- ============================================================

CREATE TABLE IF NOT EXISTS expedition (
    id_expedition         SERIAL PRIMARY KEY,
    reference_expedition  VARCHAR(30)   NOT NULL UNIQUE,
    date_expedition       DATE          NOT NULL,
    destinataire          VARCHAR(200)  NOT NULL,
    quantite_kg           DECIMAL(10,2) NOT NULL CHECK (quantite_kg > 0),
    prix                  DECIMAL(10,2) NOT NULL CHECK (prix >= 0),
    statut                VARCHAR(20)   NOT NULL DEFAULT 'EN_ATTENTE'
                          CHECK (statut IN ('EN_ATTENTE', 'EXPEDIE', 'LIVRE', 'ANNULE')),
    id_ferme              INT           NOT NULL REFERENCES ferme(id_ferme) ON DELETE RESTRICT,
    id_recolte            INT           REFERENCES recolte(id_recolte)
);

-- ============================================================
-- DEPENSES
-- ============================================================

CREATE TABLE IF NOT EXISTS depense (
    id_depense        SERIAL PRIMARY KEY,
    reference_depense VARCHAR(30)   NOT NULL UNIQUE,
    date_depense      DATE          NOT NULL,
    categorie         VARCHAR(50)   NOT NULL,
    description       TEXT,
    montant           DECIMAL(10,2) NOT NULL CHECK (montant > 0),
    id_ferme          INT           NOT NULL REFERENCES ferme(id_ferme) ON DELETE RESTRICT
);

-- ============================================================
-- NOTIFICATIONS
-- ============================================================

CREATE TABLE IF NOT EXISTS notification (
    id_notification SERIAL PRIMARY KEY,
    titre           VARCHAR(200) NOT NULL,
    message         TEXT         NOT NULL,
    type            VARCHAR(30)  NOT NULL DEFAULT 'INFO'
                    CHECK (type IN ('INFO', 'ALERTE', 'ERREUR', 'SUCCES')),
    lu              BOOLEAN      NOT NULL DEFAULT FALSE,
    date_creation   TIMESTAMP    NOT NULL DEFAULT NOW(),
    id_utilisateur  INT          REFERENCES utilisateur(id_utilisateur) ON DELETE CASCADE
);

-- ============================================================
-- JOURNAL D'AUDIT
-- ============================================================

CREATE TABLE IF NOT EXISTS journal_action (
    id_journal       SERIAL PRIMARY KEY,
    action           VARCHAR(100) NOT NULL,
    table_concernee  VARCHAR(50),
    description      TEXT,
    date_action      TIMESTAMP    NOT NULL DEFAULT NOW(),
    id_utilisateur   INT          REFERENCES utilisateur(id_utilisateur) ON DELETE SET NULL
);

-- ============================================================
-- INDEX DE PERFORMANCE
-- ============================================================

CREATE INDEX IF NOT EXISTS idx_ouvrier_ferme         ON ouvrier(id_ferme);
CREATE INDEX IF NOT EXISTS idx_parcelle_ferme        ON parcelle(id_ferme);
CREATE INDEX IF NOT EXISTS idx_presence_ouvrier_date ON presence(id_ouvrier, date_presence);
CREATE INDEX IF NOT EXISTS idx_presence_date         ON presence(date_presence);
CREATE INDEX IF NOT EXISTS idx_recolte_parcelle      ON recolte(id_parcelle);
CREATE INDEX IF NOT EXISTS idx_recolte_date          ON recolte(date_recolte);
CREATE INDEX IF NOT EXISTS idx_expedition_ferme      ON expedition(id_ferme);
CREATE INDEX IF NOT EXISTS idx_expedition_statut     ON expedition(statut);
CREATE INDEX IF NOT EXISTS idx_depense_ferme         ON depense(id_ferme);
CREATE INDEX IF NOT EXISTS idx_depense_date          ON depense(date_depense);
CREATE INDEX IF NOT EXISTS idx_journal_utilisateur   ON journal_action(id_utilisateur);
CREATE INDEX IF NOT EXISTS idx_journal_date          ON journal_action(date_action);
CREATE INDEX IF NOT EXISTS idx_notif_utilisateur     ON notification(id_utilisateur);
CREATE INDEX IF NOT EXISTS idx_notif_lu              ON notification(lu);

-- ============================================================
-- FIN DU SCRIPT
-- ============================================================
