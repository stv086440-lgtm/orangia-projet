package com.orangia.dao.impl;

import com.orangia.dao.interfaces.FichePresenceDAO;
import com.orangia.database.DatabaseConfig;
import com.orangia.model.FichePresence;

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

public class FichePresenceDAOImpl implements FichePresenceDAO {

    private Connection openConnection() throws SQLException {
        return DriverManager.getConnection(
                DatabaseConfig.URL,
                DatabaseConfig.USER,
                DatabaseConfig.PASSWORD
        );
    }

    @Override
    public FichePresence save(FichePresence fiche) {
        String sql = """
                INSERT INTO fiche_presence (id_ferme, date_fiche, statut, transmis_par, valide_par, observation)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, fiche.getIdFerme());
            ps.setDate(2, Date.valueOf(fiche.getDateFiche()));
            ps.setString(3, fiche.getStatut() != null ? fiche.getStatut() : "BROUILLON");
            if (fiche.getTransmisPar() != null) {
                ps.setLong(4, fiche.getTransmisPar());
            } else {
                ps.setNull(4, Types.BIGINT);
            }
            if (fiche.getValidePar() != null) {
                ps.setLong(5, fiche.getValidePar());
            } else {
                ps.setNull(5, Types.BIGINT);
            }
            ps.setString(6, fiche.getObservation());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    fiche.setIdFiche(rs.getLong(1));
                }
            }
            return fiche;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement de la fiche de présence", e);
        }
    }

    @Override
    public void update(FichePresence fiche) {
        String sql = """
                UPDATE fiche_presence
                SET id_ferme = ?, date_fiche = ?, statut = ?, transmis_par = ?, valide_par = ?, observation = ?
                WHERE id_fiche = ?
                """;
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, fiche.getIdFerme());
            ps.setDate(2, Date.valueOf(fiche.getDateFiche()));
            ps.setString(3, fiche.getStatut());
            if (fiche.getTransmisPar() != null) {
                ps.setLong(4, fiche.getTransmisPar());
            } else {
                ps.setNull(4, Types.BIGINT);
            }
            if (fiche.getValidePar() != null) {
                ps.setLong(5, fiche.getValidePar());
            } else {
                ps.setNull(5, Types.BIGINT);
            }
            ps.setString(6, fiche.getObservation());
            ps.setLong(7, fiche.getIdFiche());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la fiche id=" + fiche.getIdFiche(), e);
        }
    }

    @Override
    public Optional<FichePresence> findById(Long idFiche) {
        String sql = "SELECT * FROM fiche_presence WHERE id_fiche = ?";
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idFiche);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de la fiche id=" + idFiche, e);
        }
    }

    @Override
    public Optional<FichePresence> findByFermeAndDate(Long idFerme, LocalDate dateFiche) {
        String sql = "SELECT * FROM fiche_presence WHERE id_ferme = ? AND date_fiche = ?";
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idFerme);
            ps.setDate(2, Date.valueOf(dateFiche));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de la fiche pour la ferme id=" + idFerme + " à la date " + dateFiche, e);
        }
    }

    @Override
    public Optional<FichePresence> findFicheActiveByFermeAndDate(Long idFerme, LocalDate dateFiche) {
        String sql = """
                SELECT * FROM fiche_presence
                WHERE id_ferme = ?
                  AND date_fiche = ?
                  AND statut <> 'ANNULEE'
                ORDER BY id_fiche DESC
                LIMIT 1
                """;
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idFerme);
            ps.setDate(2, Date.valueOf(dateFiche));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de la fiche active pour la ferme id=" + idFerme + " à la date " + dateFiche, e);
        }
    }

    @Override
    public List<FichePresence> findByFermeId(Long idFerme) {
        String sql = "SELECT * FROM fiche_presence WHERE id_ferme = ? ORDER BY date_fiche DESC";
        List<FichePresence> list = new ArrayList<>();
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
            throw new RuntimeException("Erreur lors de la recherche des fiches pour la ferme id=" + idFerme, e);
        }
    }

    @Override
    public List<FichePresence> findAll() {
        String sql = "SELECT * FROM fiche_presence ORDER BY date_fiche DESC, id_fiche DESC";
        List<FichePresence> list = new ArrayList<>();
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de toutes les fiches de présence", e);
        }
    }

    @Override
    public void updateStatut(Long idFiche, String statut) {
        String sql = "UPDATE fiche_presence SET statut = ? WHERE id_fiche = ?";
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, statut);
            ps.setLong(2, idFiche);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du statut de la fiche id=" + idFiche, e);
        }
    }

    private FichePresence mapRow(ResultSet rs) throws SQLException {
        FichePresence f = new FichePresence();
        f.setIdFiche(rs.getLong("id_fiche"));
        f.setIdFerme(rs.getLong("id_ferme"));
        Date dateFiche = rs.getDate("date_fiche");
        if (dateFiche != null) {
            f.setDateFiche(dateFiche.toLocalDate());
        }
        f.setStatut(rs.getString("statut"));
        long transmisPar = rs.getLong("transmis_par");
        f.setTransmisPar(rs.wasNull() ? null : transmisPar);
        long validePar = rs.getLong("valide_par");
        f.setValidePar(rs.wasNull() ? null : validePar);
        f.setObservation(rs.getString("observation"));
        return f;
    }
}