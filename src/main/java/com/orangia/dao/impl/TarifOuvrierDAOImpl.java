package com.orangia.dao.impl;

import com.orangia.dao.interfaces.TarifOuvrierDAO;
import com.orangia.database.DatabaseConfig;
import com.orangia.model.TarifOuvrier;

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

public class TarifOuvrierDAOImpl implements TarifOuvrierDAO {

    private Connection openConnection() throws SQLException {
        return DriverManager.getConnection(
                DatabaseConfig.URL,
                DatabaseConfig.USER,
                DatabaseConfig.PASSWORD
        );
    }

    @Override
    public TarifOuvrier save(TarifOuvrier tarif) {
        String sql = """
                INSERT INTO tarif_ouvrier (id_ouvrier, mode_remuneration, montant, date_debut, date_fin)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, tarif.getIdOuvrier());
            ps.setString(2, tarif.getModeRemuneration());
            ps.setBigDecimal(3, tarif.getMontant());
            ps.setDate(4, Date.valueOf(tarif.getDateDebut()));
            if (tarif.getDateFin() != null) {
                ps.setDate(5, Date.valueOf(tarif.getDateFin()));
            } else {
                ps.setNull(5, Types.DATE);
            }

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    tarif.setIdTarif(rs.getLong(1));
                }
            }
            return tarif;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du tarif ouvrier", e);
        }
    }

    @Override
    public void update(TarifOuvrier tarif) {
        String sql = """
                UPDATE tarif_ouvrier
                SET id_ouvrier = ?, mode_remuneration = ?, montant = ?, date_debut = ?, date_fin = ?
                WHERE id_tarif = ?
                """;
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, tarif.getIdOuvrier());
            ps.setString(2, tarif.getModeRemuneration());
            ps.setBigDecimal(3, tarif.getMontant());
            ps.setDate(4, Date.valueOf(tarif.getDateDebut()));
            if (tarif.getDateFin() != null) {
                ps.setDate(5, Date.valueOf(tarif.getDateFin()));
            } else {
                ps.setNull(5, Types.DATE);
            }
            ps.setLong(6, tarif.getIdTarif());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du tarif id=" + tarif.getIdTarif(), e);
        }
    }

    @Override
    public Optional<TarifOuvrier> findById(Long idTarif) {
        String sql = "SELECT * FROM tarif_ouvrier WHERE id_tarif = ?";
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idTarif);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche du tarif id=" + idTarif, e);
        }
    }

    @Override
    public List<TarifOuvrier> findByOuvrierId(Long idOuvrier) {
        String sql = "SELECT * FROM tarif_ouvrier WHERE id_ouvrier = ? ORDER BY date_debut DESC";
        List<TarifOuvrier> list = new ArrayList<>();
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
            throw new RuntimeException("Erreur lors de la recherche des tarifs pour l'ouvrier id=" + idOuvrier, e);
        }
    }

    @Override
    public Optional<TarifOuvrier> findApplicableTarif(Long idOuvrier, LocalDate date) {
        String sql = """
                SELECT * FROM tarif_ouvrier
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
            throw new RuntimeException("Erreur lors de la recherche du tarif applicable pour l'ouvrier id=" + idOuvrier, e);
        }
    }

    @Override
    public List<TarifOuvrier> findAll() {
        String sql = "SELECT * FROM tarif_ouvrier ORDER BY id_tarif";
        List<TarifOuvrier> list = new ArrayList<>();
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de tous les tarifs", e);
        }
    }

    private TarifOuvrier mapRow(ResultSet rs) throws SQLException {
        TarifOuvrier t = new TarifOuvrier();
        t.setIdTarif(rs.getLong("id_tarif"));
        t.setIdOuvrier(rs.getLong("id_ouvrier"));
        t.setModeRemuneration(rs.getString("mode_remuneration"));
        t.setMontant(rs.getBigDecimal("montant"));
        Date debut = rs.getDate("date_debut");
        if (debut != null) {
            t.setDateDebut(debut.toLocalDate());
        }
        Date fin = rs.getDate("date_fin");
        if (fin != null) {
            t.setDateFin(fin.toLocalDate());
        }
        return t;
    }
}