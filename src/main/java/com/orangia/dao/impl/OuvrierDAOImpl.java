package com.orangia.dao.impl;

import com.orangia.dao.interfaces.OuvrierDAO;
import com.orangia.database.DatabaseConfig;
import com.orangia.model.Ouvrier;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OuvrierDAOImpl implements OuvrierDAO {

    private Connection openConnection() throws SQLException {
        return DriverManager.getConnection(
                DatabaseConfig.URL,
                DatabaseConfig.USER,
                DatabaseConfig.PASSWORD
        );
    }

    @Override
    public Ouvrier save(Ouvrier ouvrier) {
        String sql = """
                INSERT INTO ouvrier (code_ouvrier, nom, prenom, telephone, adresse, date_embauche, categorie_ouvrier, statut)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, ouvrier.getCodeOuvrier());
            ps.setString(2, ouvrier.getNom());
            ps.setString(3, ouvrier.getPrenom());
            ps.setString(4, ouvrier.getTelephone());
            ps.setString(5, ouvrier.getAdresse());
            ps.setDate(6, ouvrier.getDateEmbauche() != null ? Date.valueOf(ouvrier.getDateEmbauche()) : null);
            ps.setString(7, ouvrier.getCategorieOuvrier());
            ps.setString(8, ouvrier.getStatut() != null ? ouvrier.getStatut() : "ACTIF");

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    ouvrier.setIdOuvrier(rs.getLong(1));
                }
            }
            return ouvrier;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement de l'ouvrier", e);
        }
    }

    @Override
    public void update(Ouvrier ouvrier) {
        String sql = """
                UPDATE ouvrier
                SET code_ouvrier = ?, nom = ?, prenom = ?, telephone = ?, adresse = ?,
                    date_embauche = ?, categorie_ouvrier = ?, statut = ?
                WHERE id_ouvrier = ?
                """;
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ouvrier.getCodeOuvrier());
            ps.setString(2, ouvrier.getNom());
            ps.setString(3, ouvrier.getPrenom());
            ps.setString(4, ouvrier.getTelephone());
            ps.setString(5, ouvrier.getAdresse());
            ps.setDate(6, ouvrier.getDateEmbauche() != null ? Date.valueOf(ouvrier.getDateEmbauche()) : null);
            ps.setString(7, ouvrier.getCategorieOuvrier());
            ps.setString(8, ouvrier.getStatut());
            ps.setLong(9, ouvrier.getIdOuvrier());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de l'ouvrier id=" + ouvrier.getIdOuvrier(), e);
        }
    }

    @Override
    public Optional<Ouvrier> findById(Long idOuvrier) {
        String sql = "SELECT * FROM ouvrier WHERE id_ouvrier = ?";
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idOuvrier);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de l'ouvrier id=" + idOuvrier, e);
        }
    }

    @Override
    public Optional<Ouvrier> findByCode(String codeOuvrier) {
        String sql = "SELECT * FROM ouvrier WHERE code_ouvrier = ?";
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codeOuvrier);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de l'ouvrier code=" + codeOuvrier, e);
        }
    }

    @Override
    public List<Ouvrier> findByFermeId(Long idFerme) {
        String sql = """
                SELECT DISTINCT o.*
                FROM ouvrier o
                INNER JOIN affectation_ouvrier a ON o.id_ouvrier = a.id_ouvrier
                WHERE a.id_ferme = ?
                ORDER BY o.id_ouvrier
                """;
        List<Ouvrier> list = new ArrayList<>();
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
            throw new RuntimeException("Erreur lors de la recherche des ouvriers pour la ferme id=" + idFerme, e);
        }
    }

    @Override
    public List<Ouvrier> findAll() {
        String sql = "SELECT * FROM ouvrier ORDER BY id_ouvrier";
        List<Ouvrier> list = new ArrayList<>();
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de tous les ouvriers", e);
        }
    }

    @Override
    public void updateStatut(Long idOuvrier, String statut) {
        String sql = "UPDATE ouvrier SET statut = ? WHERE id_ouvrier = ?";
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, statut);
            ps.setLong(2, idOuvrier);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du statut de l'ouvrier id=" + idOuvrier, e);
        }
    }

    private Ouvrier mapRow(ResultSet rs) throws SQLException {
        Ouvrier o = new Ouvrier();
        o.setIdOuvrier(rs.getLong("id_ouvrier"));
        o.setCodeOuvrier(rs.getString("code_ouvrier"));
        o.setNom(rs.getString("nom"));
        o.setPrenom(rs.getString("prenom"));
        o.setTelephone(rs.getString("telephone"));
        o.setAdresse(rs.getString("adresse"));
        Date dateEmbauche = rs.getDate("date_embauche");
        if (dateEmbauche != null) {
            o.setDateEmbauche(dateEmbauche.toLocalDate());
        }
        o.setCategorieOuvrier(rs.getString("categorie_ouvrier"));
        o.setStatut(rs.getString("statut"));
        return o;
    }
}