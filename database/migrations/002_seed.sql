-- =====================================================
-- ORANGIA
-- 002_seed.sql
-- Données initiales
-- =====================================================

-- =====================================================
-- ROLES
-- =====================================================

INSERT INTO ROLE (nom_role, description)
VALUES
('ADMINISTRATEUR', 'Administration générale du système'),
('RESPONSABLE', 'Responsable de ferme'),
('RH', 'Gestion des ressources humaines'),
('COMPTABLE', 'Gestion financière');

-- =====================================================
-- PARAMETRES SYSTEME
-- =====================================================

INSERT INTO PARAMETRE_SYSTEME
(
    cle,
    valeur,
    description,
    modifie_par
)
VALUES

(
    'HEURE_DEBUT_TRAVAIL',
    '08:00',
    'Heure officielle de début de travail',
    NULL
),

(
    'HEURE_FIN_TRAVAIL',
    '17:00',
    'Heure officielle de fin de travail',
    NULL
),

(
    'PRIME_CABRAN',
    '500',
    'Prime mensuelle Cabran',
    NULL
),

(
    'NOMBRE_ABSENCES_MAX_PRIME_CABRAN',
    '2',
    'Nombre maximum d absences injustifiees',
    NULL
);

-- =====================================================
-- FERME DEMO
-- =====================================================

INSERT INTO FERME
(
    code_ferme,
    nom_ferme,
    localisation,
    superficie,
    statut
)
VALUES
(
    'FRM-001',
    'Ferme Principale',
    'Agadir',
    100.00,
    'ACTIVE'
);

-- =====================================================
-- ADMINISTRATEUR
-- =====================================================

INSERT INTO UTILISATEUR
(
    id_role,
    id_ferme,
    identifiant,
    mot_de_passe,
    code_acces_hash,
    nom,
    prenom,
    telephone,
    email,
    statut
)
SELECT
    r.id_role,
    NULL,
    'admin',
    'A_REMPLACER_PAR_HASH',
    'A_REMPLACER_PAR_HASH_CODE',
    'Administrateur',
    'Systeme',
    NULL,
    NULL,
    'ACTIF'
FROM ROLE r
WHERE r.nom_role = 'ADMINISTRATEUR';

-- =====================================================
-- CLIENT DEMO
-- =====================================================

INSERT INTO CLIENT
(
    code_client,
    nom_client,
    telephone,
    email,
    adresse,
    ville,
    statut
)
VALUES
(
    'CLI-001',
    'Client Demonstration',
    '0600000000',
    'client@example.com',
    'Adresse Demonstration',
    'Agadir',
    'ACTIF'
);

-- =====================================================
-- OUVRIERS
-- =====================================================

INSERT INTO OUVRIER
(
    code_ouvrier,
    nom,
    prenom,
    telephone,
    adresse,
    date_embauche,
    categorie_ouvrier,
    statut
)
VALUES

(
    'OUV-001',
    'AIT ALI',
    'Mohamed',
    '0600000001',
    'Agadir',
    '2025-01-15',
    'NORMAL',
    'ACTIF'
),

(
    'OUV-002',
    'EL FASSI',
    'Youssef',
    '0600000002',
    'Taroudant',
    '2024-11-10',
    'CABRAN',
    'ACTIF'
),

(
    'OUV-003',
    'AMRANI',
    'Hassan',
    '0600000003',
    'Ait Melloul',
    '2023-08-01',
    'NAIB',
    'ACTIF'
);

-- =====================================================
-- AFFECTATIONS
-- =====================================================

INSERT INTO AFFECTATION_OUVRIER
(
    id_ouvrier,
    id_ferme,
    date_debut,
    date_fin,
    motif,
    cree_par
)
SELECT
    o.id_ouvrier,
    f.id_ferme,
    (CURRENT_DATE - INTERVAL '180 DAY')::DATE,
    NULL,
    'Affectation initiale',
    u.id_utilisateur
FROM OUVRIER o
CROSS JOIN FERME f
CROSS JOIN UTILISATEUR u
WHERE o.code_ouvrier = 'OUV-001'
AND f.code_ferme = 'FRM-001'
AND u.identifiant = 'admin';

INSERT INTO AFFECTATION_OUVRIER
(
    id_ouvrier,
    id_ferme,
    date_debut,
    date_fin,
    motif,
    cree_par
)
SELECT
    o.id_ouvrier,
    f.id_ferme,
    (CURRENT_DATE - INTERVAL '120 DAY')::DATE,
    NULL,
    'Affectation initiale',
    u.id_utilisateur
FROM OUVRIER o
CROSS JOIN FERME f
CROSS JOIN UTILISATEUR u
WHERE o.code_ouvrier = 'OUV-002'
AND f.code_ferme = 'FRM-001'
AND u.identifiant = 'admin';

INSERT INTO AFFECTATION_OUVRIER
(
    id_ouvrier,
    id_ferme,
    date_debut,
    date_fin,
    motif,
    cree_par
)
SELECT
    o.id_ouvrier,
    f.id_ferme,
    (CURRENT_DATE - INTERVAL '90 DAY')::DATE,
    NULL,
    'Affectation initiale',
    u.id_utilisateur
FROM OUVRIER o
CROSS JOIN FERME f
CROSS JOIN UTILISATEUR u
WHERE o.code_ouvrier = 'OUV-003'
AND f.code_ferme = 'FRM-001'
AND u.identifiant = 'admin';

-- =====================================================
-- TARIFS
-- =====================================================

INSERT INTO TARIF_OUVRIER
(
    id_ouvrier,
    mode_remuneration,
    montant,
    date_debut,
    date_fin
)
SELECT
    o.id_ouvrier,
    'JOURNALIER',
    120.00,
    (CURRENT_DATE - INTERVAL '180 DAY')::DATE,
    NULL
FROM OUVRIER o
WHERE o.code_ouvrier = 'OUV-001';

INSERT INTO TARIF_OUVRIER
(
    id_ouvrier,
    mode_remuneration,
    montant,
    date_debut,
    date_fin
)
SELECT
    o.id_ouvrier,
    'MENSUEL',
    3500.00,
    (CURRENT_DATE - INTERVAL '120 DAY')::DATE,
    NULL
FROM OUVRIER o
WHERE o.code_ouvrier = 'OUV-002';

INSERT INTO TARIF_OUVRIER
(
    id_ouvrier,
    mode_remuneration,
    montant,
    date_debut,
    date_fin
)
SELECT
    o.id_ouvrier,
    'MENSUEL',
    4000.00,
    (CURRENT_DATE - INTERVAL '90 DAY')::DATE,
    NULL
FROM OUVRIER o
WHERE o.code_ouvrier = 'OUV-003';

-- =====================================================
-- PARCELLES
-- =====================================================

INSERT INTO PARCELLE
(
    id_ferme,
    code_parcelle,
    nom_parcelle,
    superficie,
    variete_orange,
    nombre_arbres,
    annee_plantation,
    statut
)
SELECT
    f.id_ferme,
    'PAR-001',
    'Parcelle Nord',
    12.50,
    'Navel',
    450,
    2019,
    'ACTIVE'
FROM FERME f
WHERE f.code_ferme = 'FRM-001';

INSERT INTO PARCELLE
(
    id_ferme,
    code_parcelle,
    nom_parcelle,
    superficie,
    variete_orange,
    nombre_arbres,
    annee_plantation,
    statut
)
SELECT
    f.id_ferme,
    'PAR-002',
    'Parcelle Sud',
    15.00,
    'Maroc Late',
    600,
    2018,
    'ACTIVE'
FROM FERME f
WHERE f.code_ferme = 'FRM-001';

-- =====================================================
-- RECOLTES
-- =====================================================

INSERT INTO RECOLTE
(
    id_parcelle,
    reference,
    date_recolte,
    quantite_kg,
    qualite,
    statut,
    observation,
    cree_par
)
SELECT
    p.id_parcelle,
    'REC-2026-001',
    (CURRENT_DATE - INTERVAL '10 DAY')::DATE,
    8500.00,
    'A',
    'ENREGISTREE',
    'Récolte principale',
    u.id_utilisateur
FROM PARCELLE p
CROSS JOIN UTILISATEUR u
WHERE p.code_parcelle = 'PAR-001'
AND u.identifiant = 'admin';

INSERT INTO RECOLTE
(
    id_parcelle,
    reference,
    date_recolte,
    quantite_kg,
    qualite,
    statut,
    observation,
    cree_par
)
SELECT
    p.id_parcelle,
    'REC-2026-002',
    (CURRENT_DATE - INTERVAL '5 DAY')::DATE,
    7200.00,
    'A',
    'ENREGISTREE',
    'Deuxieme recolte',
    u.id_utilisateur
FROM PARCELLE p
CROSS JOIN UTILISATEUR u
WHERE p.code_parcelle = 'PAR-001'
AND u.identifiant = 'admin';

INSERT INTO RECOLTE
(
    id_parcelle,
    reference,
    date_recolte,
    quantite_kg,
    qualite,
    statut,
    observation,
    cree_par
)
SELECT
    p.id_parcelle,
    'REC-2026-003',
    (CURRENT_DATE - INTERVAL '2 DAY')::DATE,
    9100.00,
    'A',
    'ENREGISTREE',
    'Récolte excellente',
    u.id_utilisateur
FROM PARCELLE p
CROSS JOIN UTILISATEUR u
WHERE p.code_parcelle = 'PAR-002'
AND u.identifiant = 'admin';