package com.orangia.dao.impl;

import com.orangia.dao.interfaces.AffectationOuvrierDAO;
import com.orangia.database.DatabaseConfig;
import com.orangia.model.AffectationOuvrier;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AffectationOuvrierDAOImpl implements AffectationOuvrierDAO {

    private Connection openConnection() throws SQLException {
        return DriverManager.getConnection(
                DatabaseConfig.URL,
                DatabaseConfig.USER,
                DatabaseConfig.PASSWORD
        );
    }

    @Override
    public AffectationOuvrier save(AffectationOuvrier affectation) {
        String sql = """
                INSERT INTO affectation_ouvrier (id_ouvrier, id_ferme, date_debut, date_fin, motif, cree_par)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, affectation.getIdOuvrier());
            ps.setLong(2, affectation.getIdFerme());
            ps.setDate(3, Date.valueOf(affectation.getDateDebut()));
            if (affectation.getDateFin() != null) {
                ps.setDate(4, Date.valueOf(affectation.getDateFin()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            ps.setString(5, affectation.getMotif());
            if (affectation.getCreePar() != null) {
                ps.setLong(6, affectation.getCreePar());
            } else {
                ps.setNull(6, Types.BIGINT);
            }

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    affectation.setIdAffectation(rs.getLong(1));
                }
            }
            return affectation;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement de l'affectation", e);
        }
    }

    @Override
    public void update(AffectationOuvrier affectation) {
        String sql = """
                UPDATE affectation_ouvrier
                SET id_ouvrier = ?, id_ferme = ?, date_debut = ?, date_fin = ?, motif = ?, cree_par = ?
                WHERE id_affectation = ?
                """;
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, affectation.getIdOuvrier());
            ps.setLong(2, affectation.getIdFerme());
            ps.setDate(3, Date.valueOf(affectation.getDateDebut()));
            if (affectation.getDateFin() != null) {
                ps.setDate(4, Date.valueOf(affectation.getDateFin()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            ps.setString(5, affectation.getMotif());
            if (affectation.getCreePar() != null) {
                ps.setLong(6, affectation.getCreePar());
            } else {
                ps.setNull(6, Types.BIGINT);
            }
            ps.setLong(7, affectation.getIdAffectation());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de l'affectation id=" + affectation.getIdAffectation(), e);
        }
    }

    @Override
    public Optional<AffectationOuvrier> findById(Long idAffectation) {
        String sql = "SELECT * FROM affectation_ouvrier WHERE id_affectation = ?";
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idAffectation);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de l'affectation id=" + idAffectation, e);
        }
    }

    @Override
    public List<AffectationOuvrier> findByOuvrierId(Long idOuvrier) {
        String sql = "SELECT * FROM affectation_ouvrier WHERE id_ouvrier = ? ORDER BY date_debut DESC";
        List<AffectationOuvrier> list = new ArrayList<>();
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idOuvrier);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche des affectations pour l'ouvrier id=" + idOuvrier, e);
        }
    }

    @Override
    public List<AffectationOuvrier> findByFermeId(Long idFerme) {
        String sql = "SELECT * FROM affectation_ouvrier WHERE id_ferme = ? ORDER BY date_debut DESC";
        List<AffectationOuvrier> list = new ArrayList<>();
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idFerme);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche des affectations pour la ferme id=" + idFerme, e);
        }
    }

    @Override
    public Optional<AffectationOuvrier> findActiveByOuvrierAndDate(Long idOuvrier, LocalDate date) {
        String sql = """
                SELECT * FROM affectation_ouvrier
                WHERE id_ouvrier = ?
                  AND date_debut <= ?
                  AND (date_fin IS NULL OR date_fin >= ?)
                ORDER BY date_debut DESC
                LIMIT 1
                """;
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            Date sqlDate = Date.valueOf(date);
            ps.setLong(1, idOuvrier);
            ps.setDate(2, sqlDate);
            ps.setDate(3, sqlDate);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de l'affectation active pour l'ouvrier id=" + idOuvrier, e);
        }
    }

    @Override
    public List<AffectationOuvrier> findHistorique() {
        return findAll();
    }

    @Override
    public List<AffectationOuvrier> findAll() {
        String sql = "SELECT * FROM affectation_ouvrier ORDER BY date_debut DESC, id_affectation DESC";
        List<AffectationOuvrier> list = new ArrayList<>();
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de toutes les affectations", e);
        }
    }

    private AffectationOuvrier mapRow(ResultSet rs) throws SQLException {
        AffectationOuvrier a = new AffectationOuvrier();
        a.setIdAffectation(rs.getLong("id_affectation"));
        a.setIdOuvrier(rs.getLong("id_ouvrier"));
        a.setIdFerme(rs.getLong("id_ferme"));
        Date debut = rs.getDate("date_debut");
        if (debut != null) {
            a.setDateDebut(debut.toLocalDate());
        }
        Date fin = rs.getDate("date_fin");
        if (fin != null) {
            a.setDateFin(fin.toLocalDate());
        }
        a.setMotif(rs.getString("motif"));
        long creePar = rs.getLong("cree_par");
        a.setCreePar(rs.wasNull() ? null : creePar);
        return a;
    }
}