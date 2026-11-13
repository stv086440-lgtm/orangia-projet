-- ============================================================
-- Orangia — Données Initiales (Seed)
-- Version: 002
-- Description: Rôles, permissions et compte administrateur
-- IMPORTANT: Exécuter APRÈS 001_schema.sql
-- ============================================================

-- ============================================================
-- ROLES
-- ============================================================

INSERT INTO role (nom_role, description) VALUES
('ADMINISTRATEUR',   'Accès complet au système. Crée et gère les comptes utilisateurs.'),
('RESPONSABLE_FERME','Gestion des fermes, parcelles, récoltes et expéditions.'),
('AGENT_RH',         'Gestion des ouvriers et des présences.'),
('COMPTABLE',        'Gestion financière, dépenses et rapports.')
ON CONFLICT (nom_role) DO NOTHING;

-- ============================================================
-- PERMISSIONS
-- ============================================================

INSERT INTO permission (nom_permission, description) VALUES
('GERER_UTILISATEURS', 'Créer, modifier, désactiver les comptes utilisateurs'),
('GERER_FERMES',       'Créer, modifier, archiver les fermes'),
('GERER_PARCELLES',    'Créer, modifier, archiver les parcelles'),
('GERER_OUVRIERS',     'Créer, modifier, désactiver les ouvriers'),
('GERER_PRESENCES',    'Enregistrer et modifier les présences'),
('GERER_RECOLTES',     'Enregistrer et modifier les récoltes'),
('GERER_EXPEDITIONS',  'Créer et gérer les expéditions'),
('GERER_DEPENSES',     'Enregistrer et gérer les dépenses'),
('VOIR_RAPPORTS',      'Consulter les rapports et statistiques'),
('VOIR_NOTIFICATIONS', 'Recevoir et consulter les notifications'),
('VOIR_JOURNAL',       'Consulter le journal d''audit')
ON CONFLICT (nom_permission) DO NOTHING;

-- ============================================================
-- ATTRIBUTION DES PERMISSIONS AUX ROLES
-- ============================================================

-- Administrateur : toutes les permissions
INSERT INTO role_permission (id_role, id_permission)
SELECT r.id_role, p.id_permission
FROM role r, permission p
WHERE r.nom_role = 'ADMINISTRATEUR'
ON CONFLICT DO NOTHING;

-- Responsable de Ferme
INSERT INTO role_permission (id_role, id_permission)
SELECT r.id_role, p.id_permission
FROM role r, permission p
WHERE r.nom_role = 'RESPONSABLE_FERME'
  AND p.nom_permission IN (
    'GERER_FERMES',
    'GERER_PARCELLES',
    'GERER_RECOLTES',
    'GERER_EXPEDITIONS',
    'VOIR_RAPPORTS',
    'VOIR_NOTIFICATIONS'
  )
ON CONFLICT DO NOTHING;

-- Agent RH
INSERT INTO role_permission (id_role, id_permission)
SELECT r.id_role, p.id_permission
FROM role r, permission p
WHERE r.nom_role = 'AGENT_RH'
  AND p.nom_permission IN (
    'GERER_OUVRIERS',
    'GERER_PRESENCES',
    'VOIR_NOTIFICATIONS'
  )
ON CONFLICT DO NOTHING;

-- Comptable
INSERT INTO role_permission (id_role, id_permission)
SELECT r.id_role, p.id_permission
FROM role r, permission p
WHERE r.nom_role = 'COMPTABLE'
  AND p.nom_permission IN (
    'GERER_DEPENSES',
    'VOIR_RAPPORTS',
    'VOIR_NOTIFICATIONS'
  )
ON CONFLICT DO NOTHING;

-- ============================================================
-- COMPTE ADMINISTRATEUR INITIAL
-- ============================================================
-- Mot de passe par défaut : Admin@2024
-- IMPORTANT : Changer le mot de passe dès la première connexion!
-- Hash BCrypt généré avec strength=12

INSERT INTO utilisateur (identifiant, mot_de_passe, nom, prenom, id_role)
SELECT 'admin',
       '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewdBPj/Y.YMi.gKe',  -- Admin@2024
       'Koutou',
       'Ilyas',
       r.id_role
FROM role r WHERE r.nom_role = 'ADMINISTRATEUR'
ON CONFLICT (identifiant) DO NOTHING;

-- ============================================================
-- DONNEES DE TEST (facultatif - commenter en production)
-- ============================================================

-- Ferme de test
INSERT INTO ferme (code_ferme, nom, localisation, superficie, description)
VALUES ('FRM-001', 'Ferme Principale', 'Marrakech, Maroc', 150.5, 'Ferme de test pour le développement')
ON CONFLICT (code_ferme) DO NOTHING;

-- Parcelle de test
INSERT INTO parcelle (code_parcelle, nom, superficie, variete_orange, nombre_arbres, annee_plantation, id_ferme)
SELECT 'PAR-001', 'Parcelle A', 25.0, 'Washington Navel', 500, 2010, f.id_ferme
FROM ferme f WHERE f.code_ferme = 'FRM-001'
ON CONFLICT (code_parcelle) DO NOTHING;

-- ============================================================
-- FIN DU SCRIPT
-- ============================================================
