-- =====================================================
-- ORANGIA
-- 003_views.sql
-- Views
-- =====================================================

-- =====================================================
-- V_STOCK_FERME
-- =====================================================

CREATE OR REPLACE VIEW V_STOCK_FERME AS
SELECT
    f.id_ferme,
    f.code_ferme,
    f.nom_ferme,

    COALESCE(r.total_recoltes,0) AS total_recoltes,

    COALESCE(e.total_expeditions,0) AS total_expeditions,

    COALESCE(r.total_recoltes,0)
    -
    COALESCE(e.total_expeditions,0)
    AS stock_disponible

FROM FERME f

LEFT JOIN
(
    SELECT
        p.id_ferme,
        SUM(r.quantite_kg) total_recoltes
    FROM RECOLTE r
    JOIN PARCELLE p
      ON p.id_parcelle = r.id_parcelle
    WHERE r.statut <> 'ANNULEE'
    GROUP BY p.id_ferme
) r
ON f.id_ferme = r.id_ferme

LEFT JOIN
(
    SELECT
        id_ferme,
        SUM(quantite_kg) total_expeditions
    FROM EXPEDITION
    WHERE
        statut_expedition <> 'ANNULEE'
        AND statut_confirmation = 'CONFIRMEE'
    GROUP BY id_ferme
) e
ON f.id_ferme = e.id_ferme;

-- =====================================================
-- V_RECOLTES_PAR_FERME
-- =====================================================

CREATE OR REPLACE VIEW V_RECOLTES_PAR_FERME AS
SELECT
    f.id_ferme,
    f.nom_ferme,

    COUNT(r.id_recolte) nombre_recoltes,

    COALESCE(SUM(r.quantite_kg),0)
        AS quantite_totale_kg

FROM FERME f
LEFT JOIN PARCELLE p
    ON p.id_ferme = f.id_ferme
LEFT JOIN RECOLTE r
    ON r.id_parcelle = p.id_parcelle
    AND r.statut <> 'ANNULEE'

GROUP BY
    f.id_ferme,
    f.nom_ferme;

-- =====================================================
-- V_EXPEDITIONS_PAR_FERME
-- =====================================================

CREATE OR REPLACE VIEW V_EXPEDITIONS_PAR_FERME AS
SELECT
    f.id_ferme,
    f.nom_ferme,

    COUNT(e.id_expedition)
        AS nombre_expeditions,

    COALESCE(SUM(e.quantite_kg),0)
        AS quantite_expediee

FROM FERME f
LEFT JOIN EXPEDITION e
    ON e.id_ferme = f.id_ferme
    AND e.statut_confirmation='CONFIRMEE'
    AND e.statut_expedition<>'ANNULEE'

GROUP BY
    f.id_ferme,
    f.nom_ferme;

-- =====================================================
-- V_SOLDE_CLIENT
-- =====================================================

CREATE OR REPLACE VIEW V_SOLDE_CLIENT AS
SELECT
    c.id_client,
    c.code_client,
    c.nom_client,

    COALESCE(SUM(e.montant_total),0)
        AS montant_facture,

    COALESCE(SUM(p.montant_paye),0)
        AS montant_paye,

    COALESCE(SUM(e.montant_total),0)
    -
    COALESCE(SUM(p.montant_paye),0)
        AS reste_a_payer

FROM CLIENT c

LEFT JOIN EXPEDITION e
    ON e.id_client = c.id_client
    AND e.statut_confirmation='CONFIRMEE'

LEFT JOIN PAIEMENT_EXPEDITION p
    ON p.id_expedition = e.id_expedition

GROUP BY
    c.id_client,
    c.code_client,
    c.nom_client;

-- =====================================================
-- V_SALAIRE_RESTANT
-- =====================================================

CREATE OR REPLACE VIEW V_SALAIRE_RESTANT AS
SELECT
    s.id_salaire,

    s.id_ouvrier,

    s.montant_total,

    COALESCE(SUM(ps.montant_paye),0)
        AS total_paye,

    s.montant_total
    -
    COALESCE(SUM(ps.montant_paye),0)
        AS reste_a_payer

FROM SALAIRE s

LEFT JOIN PAIEMENT_SALAIRE ps
    ON ps.id_salaire = s.id_salaire

GROUP BY
    s.id_salaire,
    s.id_ouvrier,
    s.montant_total;

-- =====================================================
-- V_DEPENSES_PAR_FERME
-- =====================================================

CREATE OR REPLACE VIEW V_DEPENSES_PAR_FERME AS
SELECT
    f.id_ferme,
    f.nom_ferme,

    COUNT(d.id_depense)
        AS nombre_depenses,

    COALESCE(SUM(d.montant),0)
        AS montant_depenses

FROM FERME f

LEFT JOIN DEPENSE d
    ON d.id_ferme = f.id_ferme

GROUP BY
    f.id_ferme,
    f.nom_ferme;

-- =====================================================
-- V_PRESENCES_JOUR
-- =====================================================

CREATE OR REPLACE VIEW V_PRESENCES_JOUR AS
SELECT
    p.date_presence,

    COUNT(*) total_enregistrements,

    SUM(
        CASE
            WHEN p.statut_presence='PRESENT'
            THEN 1
            ELSE 0
        END
    ) presents,

    SUM(
        CASE
            WHEN p.statut_presence='ABSENT'
            THEN 1
            ELSE 0
        END
    ) absents

FROM PRESENCE p

GROUP BY
    p.date_presence;