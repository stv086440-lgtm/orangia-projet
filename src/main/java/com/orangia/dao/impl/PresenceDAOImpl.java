package com.orangia.dao.impl;

import com.orangia.dao.interfaces.PresenceDAO;
import com.orangia.database.DatabaseConfig;
import com.orangia.model.Presence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PresenceDAOImpl implements PresenceDAO {

    private Connection openConnection() throws SQLException {
        return DriverManager.getConnection(
                DatabaseConfig.URL,
                DatabaseConfig.USER,
                DatabaseConfig.PASSWORD
        );
    }

    @Override
    public Presence save(Presence presence) {
        String sql = """
                INSERT INTO presence (
                    id_fiche, id_ouvrier, date_presence, statut_presence,
                    heure_arrivee, heure_depart, heure_debut_appliquee, heure_fin_appliquee,
                    absence_justifiee, justification_acceptee, observation
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, presence.getIdFiche());
            ps.setLong(2, presence.getIdOuvrier());
            ps.setDate(3, Date.valueOf(presence.getDatePresence()));
            ps.setString(4, presence.getStatutPresence());
            setTimeOrNull(ps, 5, presence.getHeureArrivee());
            setTimeOrNull(ps, 6, presence.getHeureDepart());
            setTimeOrNull(ps, 7, presence.getHeureDebutAppliquee());
            setTimeOrNull(ps, 8, presence.getHeureFinAppliquee());
            ps.setBoolean(9, presence.isAbsenceJustifiee());
            ps.setBoolean(10, presence.isJustificationAcceptee());
            ps.setString(11, presence.getObservation());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    presence.setIdPresence(rs.getLong(1));
                }
            }
            return presence;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement de la présence", e);
        }
    }

    @Override
    public void update(Presence presence) {
        String sql = """
                UPDATE presence
                SET id_fiche = ?, id_ouvrier = ?, date_presence = ?, statut_presence = ?,
                    heure_arrivee = ?, heure_depart = ?, heure_debut_appliquee = ?, heure_fin_appliquee = ?,
                    absence_justifiee = ?, justification_acceptee = ?, observation = ?
                WHERE id_presence = ?
                """;
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, presence.getIdFiche());
            ps.setLong(2, presence.getIdOuvrier());
            ps.setDate(3, Date.valueOf(presence.getDatePresence()));
            ps.setString(4, presence.getStatutPresence());
            setTimeOrNull(ps, 5, presence.getHeureArrivee());
            setTimeOrNull(ps, 6, presence.getHeureDepart());
            setTimeOrNull(ps, 7, presence.getHeureDebutAppliquee());
            setTimeOrNull(ps, 8, presence.getHeureFinAppliquee());
            ps.setBoolean(9, presence.isAbsenceJustifiee());
            ps.setBoolean(10, presence.isJustificationAcceptee());
            ps.setString(11, presence.getObservation());
            ps.setLong(12, presence.getIdPresence());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la présence id=" + presence.getIdPresence(), e);
        }
    }

    @Override
    public Optional<Presence> findById(Long idPresence) {
        String sql = "SELECT * FROM presence WHERE id_presence = ?";
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idPresence);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de la présence id=" + idPresence, e);
        }
    }

    @Override
    public List<Presence> findByOuvrierId(Long idOuvrier) {
        String sql = "SELECT * FROM presence WHERE id_ouvrier = ? ORDER BY date_presence DESC, id_presence DESC";
        List<Presence> list = new ArrayList<>();
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
            throw new RuntimeException("Erreur lors de la recherche des présences pour l'ouvrier id=" + idOuvrier, e);
        }
    }

    @Override
    public Optional<Presence> findByOuvrierAndDate(Long idOuvrier, LocalDate datePresence) {
        String sql = "SELECT * FROM presence WHERE id_ouvrier = ? AND date_presence = ?";
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idOuvrier);
            ps.setDate(2, Date.valueOf(datePresence));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de la présence pour l'ouvrier id=" + idOuvrier, e);
        }
    }

    @Override
    public List<Presence> findByFicheId(Long idFiche) {
        String sql = "SELECT * FROM presence WHERE id_fiche = ? ORDER BY id_presence";
        List<Presence> list = new ArrayList<>();
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idFiche);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche des présences pour la fiche id=" + idFiche, e);
        }
    }

    @Override
    public List<Presence> findByFermeId(Long idFerme) {
        String sql = """
                SELECT p.*
                FROM presence p
                INNER JOIN fiche_presence f ON p.id_fiche = f.id_fiche
                WHERE f.id_ferme = ?
                ORDER BY p.date_presence DESC, p.id_presence
                """;
        List<Presence> list = new ArrayList<>();
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
            throw new RuntimeException("Erreur lors de la recherche des présences pour la ferme id=" + idFerme, e);
        }
    }

    @Override
    public List<Presence> findByDate(LocalDate datePresence) {
        String sql = "SELECT * FROM presence WHERE date_presence = ? ORDER BY id_ouvrier";
        List<Presence> list = new ArrayList<>();
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(datePresence));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche des présences à la date " + datePresence, e);
        }
    }

    @Override
    public List<Presence> findByPeriod(LocalDate dateDebut, LocalDate dateFin) {
        String sql = "SELECT * FROM presence WHERE date_presence BETWEEN ? AND ? ORDER BY date_presence, id_ouvrier";
        List<Presence> list = new ArrayList<>();
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(dateDebut));
            ps.setDate(2, Date.valueOf(dateFin));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche des présences entre " + dateDebut + " et " + dateFin, e);
        }
    }

    private void setTimeOrNull(PreparedStatement ps, int index, java.time.LocalTime time) throws SQLException {
        if (time != null) {
            ps.setTime(index, Time.valueOf(time));
        } else {
            ps.setNull(index, Types.TIME);
        }
    }

    private Presence mapRow(ResultSet rs) throws SQLException {
        Presence p = new Presence();
        p.setIdPresence(rs.getLong("id_presence"));
        p.setIdFiche(rs.getLong("id_fiche"));
        p.setIdOuvrier(rs.getLong("id_ouvrier"));
        Date datePresence = rs.getDate("date_presence");
        if (datePresence != null) {
            p.setDatePresence(datePresence.toLocalDate());
        }
        p.setStatutPresence(rs.getString("statut_presence"));

        Time arrivee = rs.getTime("heure_arrivee");
        if (arrivee != null) p.setHeureArrivee(arrivee.toLocalTime());

        Time depart = rs.getTime("heure_depart");
        if (depart != null) p.setHeureDepart(depart.toLocalTime());

        Time debutApp = rs.getTime("heure_debut_appliquee");
        if (debutApp != null) p.setHeureDebutAppliquee(debutApp.toLocalTime());

        Time finApp = rs.getTime("heure_fin_appliquee");
        if (finApp != null) p.setHeureFinAppliquee(finApp.toLocalTime());

        p.setAbsenceJustifiee(rs.getBoolean("absence_justifiee"));
        p.setJustificationAcceptee(rs.getBoolean("justification_acceptee"));
        p.setObservation(rs.getString("observation"));
        return p;
    }
}