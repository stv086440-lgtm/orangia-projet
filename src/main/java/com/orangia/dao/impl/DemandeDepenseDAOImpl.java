package com.orangia.dao.impl;

import com.orangia.database.BaseDAO;
import com.orangia.dao.interfaces.DemandeDepenseDAO;
import com.orangia.exception.DatabaseException;
import com.orangia.model.DemandeDepense;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DemandeDepenseDAOImpl extends BaseDAO implements DemandeDepenseDAO {

    private DemandeDepense map(ResultSet rs) throws SQLException {
        DemandeDepense d = new DemandeDepense();
        d.setIdDemande(rs.getLong("id_demande"));
        d.setIdFerme(rs.getLong("id_ferme"));
        d.setReference(rs.getString("reference"));
        d.setSujet(rs.getString("sujet"));
        d.setCategorie(rs.getString("categorie"));
        d.setMontantEstime(rs.getBigDecimal("montant_estime"));
        d.setDescription(rs.getString("description"));
        d.setJustification(rs.getString("justification"));
        d.setStatut(rs.getString("statut"));
        d.setCreePar(rs.getLong("cree_par"));
        d.setTraiteePar((Long) rs.getObject("traitee_par"));
        d.setCommentaireComptable(rs.getString("commentaire_comptable"));
        return d;
    }

    @Override
    public Optional<DemandeDepense> findById(Long id) {
        try (Connection c = getConnection();
                PreparedStatement ps = c.prepareStatement("SELECT * FROM DEMANDE_DEPENSE WHERE id_demande = ?")) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erreur lecture demande de depense", e);
        }
    }

    @Override
    public List<DemandeDepense> findAll() {
        List<DemandeDepense> list = new ArrayList<>();
        try (Connection c = getConnection();
                PreparedStatement ps = c.prepareStatement("SELECT * FROM DEMANDE_DEPENSE ORDER BY id_demande DESC");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erreur lecture demandes de depenses", e);
        }
        return list;
    }

    @Override
    public List<DemandeDepense> findByFermeId(Long idFerme) {
        List<DemandeDepense> list = new ArrayList<>();
        try (Connection c = getConnection();
                PreparedStatement ps = c.prepareStatement(
                        "SELECT * FROM DEMANDE_DEPENSE WHERE id_ferme = ? ORDER BY id_demande DESC")) {
            ps.setLong(1, idFerme);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erreur lecture demandes de la ferme", e);
        }
        return list;
    }

    @Override
    public List<DemandeDepense> findByStatut(String statut) {
        List<DemandeDepense> list = new ArrayList<>();
        try (Connection c = getConnection();
                PreparedStatement ps = c.prepareStatement(
                        "SELECT * FROM DEMANDE_DEPENSE WHERE statut = ? ORDER BY id_demande DESC")) {
            ps.setString(1, statut);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erreur lecture demandes par statut", e);
        }
        return list;
    }

    @Override
    public List<DemandeDepense> findPending() {
        return findByStatut("EN_ATTENTE");
    }

    @Override
    public void save(DemandeDepense demande) {
        String sql = "INSERT INTO DEMANDE_DEPENSE (id_ferme, reference, sujet, categorie, montant_estime, "
                + "description, justification, statut, cree_par, traitee_par, commentaire_comptable) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            fill(ps, demande);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erreur creation demande de depense", e);
        }
    }

    @Override
    public void update(DemandeDepense demande) {
        String sql = "UPDATE DEMANDE_DEPENSE SET id_ferme = ?, reference = ?, sujet = ?, categorie = ?, "
                + "montant_estime = ?, description = ?, justification = ?, statut = ?, cree_par = ?, "
                + "traitee_par = ?, commentaire_comptable = ? WHERE id_demande = ?";
        try (Connection c = getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            fill(ps, demande);
            ps.setLong(12, demande.getIdDemande());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erreur modification demande de depense", e);
        }
    }

    private void fill(PreparedStatement ps, DemandeDepense d) throws SQLException {
        ps.setLong(1, d.getIdFerme());
        ps.setString(2, d.getReference());
        ps.setString(3, d.getSujet());
        ps.setString(4, d.getCategorie());
        ps.setBigDecimal(5, d.getMontantEstime());
        ps.setString(6, d.getDescription());
        ps.setString(7, d.getJustification());
        ps.setString(8, d.getStatut() == null ? "EN_ATTENTE" : d.getStatut());
        ps.setLong(9, d.getCreePar());
        if (d.getTraiteePar() != null) {
            ps.setLong(10, d.getTraiteePar());
        } else {
            ps.setNull(10, Types.BIGINT);
        }
        ps.setString(11, d.getCommentaireComptable());
    }
}
