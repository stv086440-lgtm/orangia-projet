package com.orangia.dao.impl;

import com.orangia.database.BaseDAO;
import com.orangia.dao.interfaces.DepenseDAO;
import com.orangia.exception.DatabaseException;
import com.orangia.model.Depense;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DepenseDAOImpl extends BaseDAO implements DepenseDAO {

    private Depense map(ResultSet rs) throws SQLException {
        Depense d = new Depense();
        d.setIdDepense(rs.getLong("id_depense"));
        d.setIdFerme(rs.getLong("id_ferme"));
        d.setIdDemande((Long) rs.getObject("id_demande"));
        d.setReference(rs.getString("reference"));
        d.setCategorie(rs.getString("categorie"));
        d.setDescription(rs.getString("description"));
        d.setMontant(rs.getBigDecimal("montant"));
        Date dt = rs.getDate("date_depense");
        if (dt != null) {
            d.setDateDepense(dt.toLocalDate());
        }
        d.setModePaiement(rs.getString("mode_paiement"));
        d.setStatut(rs.getString("statut"));
        d.setEnregistrePar((Long) rs.getObject("enregistre_par"));
        return d;
    }

    @Override
    public Depense findById(Long idDepense) {
        try (Connection c = getConnection();
                PreparedStatement ps = c.prepareStatement("SELECT * FROM DEPENSE WHERE id_depense = ?")) {
            ps.setLong(1, idDepense);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erreur lecture depense", e);
        }
    }

    @Override
    public List<Depense> findAll() {
        List<Depense> list = new ArrayList<>();
        try (Connection c = getConnection();
                PreparedStatement ps = c.prepareStatement("SELECT * FROM DEPENSE ORDER BY date_depense DESC");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erreur lecture depenses", e);
        }
        return list;
    }

    @Override
    public List<Depense> findByFermeId(Long idFerme) {
        List<Depense> list = new ArrayList<>();
        try (Connection c = getConnection();
                PreparedStatement ps = c.prepareStatement(
                        "SELECT * FROM DEPENSE WHERE id_ferme = ? ORDER BY date_depense DESC")) {
            ps.setLong(1, idFerme);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erreur lecture depenses de la ferme", e);
        }
        return list;
    }

    @Override
    public List<Depense> findByPeriod(LocalDate dateDebut, LocalDate dateFin) {
        List<Depense> list = new ArrayList<>();
        try (Connection c = getConnection();
                PreparedStatement ps = c.prepareStatement(
                        "SELECT * FROM DEPENSE WHERE date_depense BETWEEN ? AND ? ORDER BY date_depense DESC")) {
            ps.setDate(1, Date.valueOf(dateDebut));
            ps.setDate(2, Date.valueOf(dateFin));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erreur lecture depenses par periode", e);
        }
        return list;
    }

    @Override
    public void save(Depense depense) {
        String sql = "INSERT INTO DEPENSE (id_ferme, id_demande, reference, categorie, description, "
                + "montant, date_depense, mode_paiement, statut, enregistre_par) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            fill(ps, depense);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erreur creation depense", e);
        }
    }

    @Override
    public void update(Depense depense) {
        String sql = "UPDATE DEPENSE SET id_ferme = ?, id_demande = ?, reference = ?, categorie = ?, "
                + "description = ?, montant = ?, date_depense = ?, mode_paiement = ?, statut = ?, "
                + "enregistre_par = ? WHERE id_depense = ?";
        try (Connection c = getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            fill(ps, depense);
            ps.setLong(11, depense.getIdDepense());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erreur modification depense", e);
        }
    }

    private void fill(PreparedStatement ps, Depense d) throws SQLException {
        ps.setLong(1, d.getIdFerme());
        if (d.getIdDemande() != null) {
            ps.setLong(2, d.getIdDemande());
        } else {
            ps.setNull(2, Types.BIGINT);
        }
        ps.setString(3, d.getReference());
        ps.setString(4, d.getCategorie());
        ps.setString(5, d.getDescription());
        ps.setBigDecimal(6, d.getMontant());
        ps.setDate(7, d.getDateDepense() == null ? null : Date.valueOf(d.getDateDepense()));
        ps.setString(8, d.getModePaiement());
        ps.setString(9, d.getStatut() == null ? "VALIDEE" : d.getStatut());
        if (d.getEnregistrePar() != null) {
            ps.setLong(10, d.getEnregistrePar());
        } else {
            ps.setNull(10, Types.BIGINT);
        }
    }
}
