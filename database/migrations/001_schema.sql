-- =====================================================
-- ORANGIA
-- 001_schema.sql
-- PostgreSQL
-- =====================================================

-- =====================================================
-- AUTHENTIFICATION
-- =====================================================

CREATE TABLE ROLE (
    id_role BIGSERIAL PRIMARY KEY,
    nom_role VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE FERME (
    id_ferme BIGSERIAL PRIMARY KEY,
    code_ferme VARCHAR(30) NOT NULL UNIQUE,
    nom_ferme VARCHAR(100) NOT NULL,
    localisation VARCHAR(255) NOT NULL,
    superficie NUMERIC(10,2) NOT NULL CHECK(superficie > 0),
    statut VARCHAR(20) NOT NULL,
    date_creation TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE UTILISATEUR (
    id_utilisateur BIGSERIAL PRIMARY KEY,

    id_role BIGINT NOT NULL,
    id_ferme BIGINT NULL,

    identifiant VARCHAR(100) NOT NULL UNIQUE,

    mot_de_passe TEXT NOT NULL,
    code_acces_hash TEXT NULL,

    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,

    telephone VARCHAR(20),
    email VARCHAR(150),

    statut VARCHAR(20) NOT NULL,

    date_creation TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    derniere_connexion TIMESTAMPTZ,

    CONSTRAINT fk_utilisateur_role
        FOREIGN KEY (id_role)
        REFERENCES ROLE(id_role),

    CONSTRAINT fk_utilisateur_ferme
        FOREIGN KEY (id_ferme)
        REFERENCES FERME(id_ferme)
);

-- =====================================================
-- PARCELLES
-- =====================================================

CREATE TABLE PARCELLE (
    id_parcelle BIGSERIAL PRIMARY KEY,

    id_ferme BIGINT NOT NULL,

    code_parcelle VARCHAR(30) NOT NULL,
    nom_parcelle VARCHAR(100) NOT NULL,

    superficie NUMERIC(10,2) NOT NULL CHECK(superficie > 0),

    variete_orange VARCHAR(100),

    nombre_arbres INTEGER CHECK(nombre_arbres >= 0),

    annee_plantation INTEGER,

    statut VARCHAR(20) NOT NULL,

    CONSTRAINT fk_parcelle_ferme
        FOREIGN KEY(id_ferme)
        REFERENCES FERME(id_ferme),

    CONSTRAINT uk_parcelle
        UNIQUE(id_ferme, code_parcelle)
);

-- =====================================================
-- OUVRIERS
-- =====================================================

CREATE TABLE OUVRIER (
    id_ouvrier BIGSERIAL PRIMARY KEY,

    code_ouvrier VARCHAR(30) NOT NULL UNIQUE,

    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,

    telephone VARCHAR(20),

    adresse TEXT,

    date_embauche DATE NOT NULL,

    categorie_ouvrier VARCHAR(20) NOT NULL,

    statut VARCHAR(20) NOT NULL
);

CREATE TABLE AFFECTATION_OUVRIER (
    id_affectation BIGSERIAL PRIMARY KEY,

    id_ouvrier BIGINT NOT NULL,
    id_ferme BIGINT NOT NULL,

    date_debut DATE NOT NULL,
    date_fin DATE,

    motif TEXT,

    cree_par BIGINT,

    CONSTRAINT fk_affectation_ouvrier
        FOREIGN KEY(id_ouvrier)
        REFERENCES OUVRIER(id_ouvrier),

    CONSTRAINT fk_affectation_ferme
        FOREIGN KEY(id_ferme)
        REFERENCES FERME(id_ferme),

    CONSTRAINT fk_affectation_createur
        FOREIGN KEY(cree_par)
        REFERENCES UTILISATEUR(id_utilisateur)
);

CREATE TABLE TARIF_OUVRIER (
    id_tarif BIGSERIAL PRIMARY KEY,

    id_ouvrier BIGINT NOT NULL,

    mode_remuneration VARCHAR(20) NOT NULL,

    montant NUMERIC(12,2) NOT NULL
        CHECK(montant >= 0),

    date_debut DATE NOT NULL,
    date_fin DATE,

    CONSTRAINT fk_tarif_ouvrier
        FOREIGN KEY(id_ouvrier)
        REFERENCES OUVRIER(id_ouvrier)
);

-- =====================================================
-- PRESENCES
-- =====================================================

CREATE TABLE FICHE_PRESENCE (
    id_fiche BIGSERIAL PRIMARY KEY,

    id_ferme BIGINT NOT NULL,

    date_fiche DATE NOT NULL,

    statut VARCHAR(20) NOT NULL,

    transmis_par BIGINT,
    valide_par BIGINT,

    observation TEXT,

    CONSTRAINT fk_fiche_ferme
        FOREIGN KEY(id_ferme)
        REFERENCES FERME(id_ferme),

    CONSTRAINT fk_fiche_transmis
        FOREIGN KEY(transmis_par)
        REFERENCES UTILISATEUR(id_utilisateur),

    CONSTRAINT fk_fiche_valide
        FOREIGN KEY(valide_par)
        REFERENCES UTILISATEUR(id_utilisateur),

    CONSTRAINT uk_fiche_jour
        UNIQUE(id_ferme, date_fiche)
);

CREATE TABLE PRESENCE (
    id_presence BIGSERIAL PRIMARY KEY,

    id_fiche BIGINT NOT NULL,

    id_ouvrier BIGINT NOT NULL,

    date_presence DATE NOT NULL,

    statut_presence VARCHAR(20) NOT NULL,

    heure_arrivee TIME,
    heure_depart TIME,

    heure_debut_appliquee TIME,
    heure_fin_appliquee TIME,

    absence_justifiee BOOLEAN DEFAULT FALSE,
    justification_acceptee BOOLEAN DEFAULT FALSE,

    observation TEXT,

    CONSTRAINT fk_presence_fiche
        FOREIGN KEY(id_fiche)
        REFERENCES FICHE_PRESENCE(id_fiche),

    CONSTRAINT fk_presence_ouvrier
        FOREIGN KEY(id_ouvrier)
        REFERENCES OUVRIER(id_ouvrier),

    CONSTRAINT uk_presence_jour
        UNIQUE(id_ouvrier, date_presence)
);

-- =====================================================
-- DOCUMENTS
-- =====================================================

CREATE TABLE DOCUMENT (
    id_document BIGSERIAL PRIMARY KEY,

    nom_fichier VARCHAR(255) NOT NULL,

    chemin_fichier TEXT NOT NULL,

    extension VARCHAR(10),

    type_mime VARCHAR(100),

    taille_octets BIGINT,

    type_operation VARCHAR(50),

    id_operation BIGINT,

    ajoute_par BIGINT,

    CONSTRAINT fk_document_user
        FOREIGN KEY(ajoute_par)
        REFERENCES UTILISATEUR(id_utilisateur)
);

-- =====================================================
-- CORRECTIONS
-- =====================================================

CREATE TABLE DEMANDE_CORRECTION (
    id_demande_correction BIGSERIAL PRIMARY KEY,

    id_presence BIGINT NOT NULL,

    demande_par BIGINT NOT NULL,

    motif VARCHAR(150) NOT NULL,

    description TEXT NOT NULL,

    document_id BIGINT,

    statut VARCHAR(20) NOT NULL,

    traitee_par BIGINT,

    commentaire_rh TEXT,

    CONSTRAINT fk_correction_presence
        FOREIGN KEY(id_presence)
        REFERENCES PRESENCE(id_presence),

    CONSTRAINT fk_correction_demandeur
        FOREIGN KEY(demande_par)
        REFERENCES UTILISATEUR(id_utilisateur),

    CONSTRAINT fk_correction_document
        FOREIGN KEY(document_id)
        REFERENCES DOCUMENT(id_document),

    CONSTRAINT fk_correction_rh
        FOREIGN KEY(traitee_par)
        REFERENCES UTILISATEUR(id_utilisateur)
);

-- =====================================================
-- SALAIRES
-- =====================================================

CREATE TABLE SALAIRE (
    id_salaire BIGSERIAL PRIMARY KEY,

    id_ouvrier BIGINT NOT NULL,

    id_ferme BIGINT NOT NULL,

    id_tarif BIGINT NOT NULL,

    periode_debut DATE NOT NULL,
    periode_fin DATE NOT NULL,

    montant_base NUMERIC(12,2) NOT NULL,
    montant_prime NUMERIC(12,2) NOT NULL DEFAULT 0,
    montant_total NUMERIC(12,2) NOT NULL,

    detail_calcul JSONB,

    CONSTRAINT fk_salaire_ouvrier
        FOREIGN KEY(id_ouvrier)
        REFERENCES OUVRIER(id_ouvrier),

    CONSTRAINT fk_salaire_ferme
        FOREIGN KEY(id_ferme)
        REFERENCES FERME(id_ferme),

    CONSTRAINT fk_salaire_tarif
        FOREIGN KEY(id_tarif)
        REFERENCES TARIF_OUVRIER(id_tarif)
);

CREATE TABLE PAIEMENT_SALAIRE (
    id_paiement_salaire BIGSERIAL PRIMARY KEY,

    id_salaire BIGINT NOT NULL,

    montant_paye NUMERIC(12,2) NOT NULL,

    date_paiement DATE NOT NULL,

    mode_paiement VARCHAR(30),

    reference VARCHAR(100),

    enregistre_par BIGINT,

    CONSTRAINT fk_paiement_salaire
        FOREIGN KEY(id_salaire)
        REFERENCES SALAIRE(id_salaire),

    CONSTRAINT fk_paiement_salaire_user
        FOREIGN KEY(enregistre_par)
        REFERENCES UTILISATEUR(id_utilisateur)
);

-- =====================================================
-- RECOLTES
-- =====================================================

CREATE TABLE RECOLTE (
    id_recolte BIGSERIAL PRIMARY KEY,

    id_parcelle BIGINT NOT NULL,

    reference VARCHAR(40) NOT NULL UNIQUE,

    date_recolte DATE NOT NULL,

    quantite_kg NUMERIC(12,2) NOT NULL
        CHECK(quantite_kg > 0),

    qualite VARCHAR(20),

    statut VARCHAR(20) NOT NULL,

    observation TEXT,

    cree_par BIGINT,

    CONSTRAINT fk_recolte_parcelle
        FOREIGN KEY(id_parcelle)
        REFERENCES PARCELLE(id_parcelle),

    CONSTRAINT fk_recolte_user
        FOREIGN KEY(cree_par)
        REFERENCES UTILISATEUR(id_utilisateur)
);
-- =====================================================
-- CLIENTS
-- =====================================================

CREATE TABLE CLIENT (
    id_client BIGSERIAL PRIMARY KEY,

    code_client VARCHAR(30) NOT NULL UNIQUE,

    nom_client VARCHAR(150) NOT NULL,

    telephone VARCHAR(20),

    email VARCHAR(150),

    adresse TEXT,

    ville VARCHAR(100),

    statut VARCHAR(20) NOT NULL
);

-- =====================================================
-- EXPEDITIONS
-- =====================================================

CREATE TABLE EXPEDITION (
    id_expedition BIGSERIAL PRIMARY KEY,

    id_ferme BIGINT NOT NULL,
    id_client BIGINT NOT NULL,

    reference VARCHAR(40) NOT NULL UNIQUE,

    date_expedition DATE NOT NULL,

    quantite_kg NUMERIC(12,2) NOT NULL
        CHECK(quantite_kg > 0),

    quantite_facturee NUMERIC(12,2)
        CHECK(quantite_facturee >= 0),

    prix_unitaire NUMERIC(12,2)
        CHECK(prix_unitaire >= 0),

    montant_total NUMERIC(16,2)
        CHECK(montant_total >= 0),

    statut_expedition VARCHAR(30) NOT NULL,

    statut_confirmation VARCHAR(25) NOT NULL,

    motif_refus TEXT,

    confirme_par BIGINT,

    cree_par BIGINT NOT NULL,

    observation TEXT,

    CONSTRAINT fk_expedition_ferme
        FOREIGN KEY(id_ferme)
        REFERENCES FERME(id_ferme),

    CONSTRAINT fk_expedition_client
        FOREIGN KEY(id_client)
        REFERENCES CLIENT(id_client),

    CONSTRAINT fk_expedition_confirme
        FOREIGN KEY(confirme_par)
        REFERENCES UTILISATEUR(id_utilisateur),

    CONSTRAINT fk_expedition_createur
        FOREIGN KEY(cree_par)
        REFERENCES UTILISATEUR(id_utilisateur)
);

CREATE TABLE PAIEMENT_EXPEDITION (
    id_paiement BIGSERIAL PRIMARY KEY,

    id_expedition BIGINT NOT NULL,

    montant_paye NUMERIC(12,2) NOT NULL
        CHECK(montant_paye > 0),

    date_paiement DATE NOT NULL,

    mode_paiement VARCHAR(30),

    reference VARCHAR(100),

    enregistre_par BIGINT,

    CONSTRAINT fk_paiement_expedition
        FOREIGN KEY(id_expedition)
        REFERENCES EXPEDITION(id_expedition),

    CONSTRAINT fk_paiement_expedition_user
        FOREIGN KEY(enregistre_par)
        REFERENCES UTILISATEUR(id_utilisateur)
);

-- =====================================================
-- DEPENSES
-- =====================================================

CREATE TABLE DEMANDE_DEPENSE (
    id_demande BIGSERIAL PRIMARY KEY,

    id_ferme BIGINT NOT NULL,

    reference VARCHAR(40) NOT NULL UNIQUE,

    sujet VARCHAR(150) NOT NULL,

    categorie VARCHAR(80),

    montant_estime NUMERIC(12,2)
        CHECK(montant_estime >= 0),

    description TEXT,

    justification TEXT,

    statut VARCHAR(20) NOT NULL,

    cree_par BIGINT NOT NULL,

    traitee_par BIGINT,

    commentaire_comptable TEXT,

    CONSTRAINT fk_demande_depense_ferme
        FOREIGN KEY(id_ferme)
        REFERENCES FERME(id_ferme),

    CONSTRAINT fk_demande_depense_createur
        FOREIGN KEY(cree_par)
        REFERENCES UTILISATEUR(id_utilisateur),

    CONSTRAINT fk_demande_depense_traitement
        FOREIGN KEY(traitee_par)
        REFERENCES UTILISATEUR(id_utilisateur)
);

CREATE TABLE DEPENSE (
    id_depense BIGSERIAL PRIMARY KEY,

    id_ferme BIGINT NOT NULL,

    id_demande BIGINT,

    reference VARCHAR(40) NOT NULL UNIQUE,

    categorie VARCHAR(80),

    description TEXT,

    montant NUMERIC(12,2) NOT NULL
        CHECK(montant >= 0),

    date_depense DATE NOT NULL,

    mode_paiement VARCHAR(30),

    statut VARCHAR(20),

    enregistre_par BIGINT,

    CONSTRAINT fk_depense_ferme
        FOREIGN KEY(id_ferme)
        REFERENCES FERME(id_ferme),

    CONSTRAINT fk_depense_demande
        FOREIGN KEY(id_demande)
        REFERENCES DEMANDE_DEPENSE(id_demande),

    CONSTRAINT fk_depense_user
        FOREIGN KEY(enregistre_par)
        REFERENCES UTILISATEUR(id_utilisateur)
);

-- =====================================================
-- NOTIFICATIONS
-- =====================================================

CREATE TABLE NOTIFICATION (
    id_notification BIGSERIAL PRIMARY KEY,

    id_utilisateur BIGINT NOT NULL,

    titre VARCHAR(150) NOT NULL,

    message TEXT NOT NULL,

    type_notification VARCHAR(50),

    lu BOOLEAN NOT NULL DEFAULT FALSE,

    date_creation TIMESTAMPTZ NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_notification_user
        FOREIGN KEY(id_utilisateur)
        REFERENCES UTILISATEUR(id_utilisateur)
);

-- =====================================================
-- JOURNAL ACTION
-- =====================================================

CREATE TABLE JOURNAL_ACTION (
    id_action BIGSERIAL PRIMARY KEY,

    id_utilisateur BIGINT NOT NULL,

    id_ferme BIGINT,

    action VARCHAR(100) NOT NULL,

    type_operation VARCHAR(50),

    id_operation BIGINT,

    description TEXT,

    date_action TIMESTAMPTZ NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_journal_user
        FOREIGN KEY(id_utilisateur)
        REFERENCES UTILISATEUR(id_utilisateur),

    CONSTRAINT fk_journal_ferme
        FOREIGN KEY(id_ferme)
        REFERENCES FERME(id_ferme)
);

-- =====================================================
-- PARAMETRES SYSTEME
-- =====================================================

CREATE TABLE PARAMETRE_SYSTEME (
    id_parametre BIGSERIAL PRIMARY KEY,

    cle VARCHAR(100) NOT NULL UNIQUE,

    valeur TEXT,

    description TEXT,

    modifie_par BIGINT,

    CONSTRAINT fk_parametre_user
        FOREIGN KEY(modifie_par)
        REFERENCES UTILISATEUR(id_utilisateur)
);

-- =====================================================
-- INDEXES
-- =====================================================

CREATE INDEX idx_utilisateur_role
    ON UTILISATEUR(id_role);

CREATE INDEX idx_utilisateur_ferme
    ON UTILISATEUR(id_ferme);

CREATE INDEX idx_presence_fiche
    ON PRESENCE(id_fiche);

CREATE INDEX idx_recolte_parcelle
    ON RECOLTE(id_parcelle);

CREATE INDEX idx_expedition_ferme
    ON EXPEDITION(id_ferme);

CREATE INDEX idx_expedition_client
    ON EXPEDITION(id_client);

CREATE INDEX idx_notification_user
    ON NOTIFICATION(id_utilisateur);

CREATE INDEX idx_journal_user
    ON JOURNAL_ACTION(id_utilisateur);