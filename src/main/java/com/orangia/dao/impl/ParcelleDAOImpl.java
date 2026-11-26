package com.orangia.dao.impl;

import com.orangia.dao.interfaces.ParcelleDAO;
import com.orangia.model.Parcelle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ParcelleDAOImpl implements ParcelleDAO {

    private final Connection connection;

    private static final String INSERT_SQL = """
            INSERT INTO parcelle (
                id_ferme,
                code_parcelle,
                nom_parcelle,
                superficie,
                variete_orange,
                nombre_arbres,
                annee_plantation,
                statut
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id_parcelle
            """;

    private static final String UPDATE_SQL = """
            UPDATE parcelle
            SET id_ferme = ?,
                code_parcelle = ?,
                nom_parcelle = ?,
                superficie = ?,
                variete_orange = ?,
                nombre_arbres = ?,
                annee_plantation = ?,
                statut = ?
            WHERE id_parcelle = ?
            """;

    private static final String SELECT_BY_ID_SQL = """
            SELECT id_parcelle,
                   id_ferme,
                   code_parcelle,
                   nom_parcelle,
                   superficie,
                   variete_orange,
                   nombre_arbres,
                   annee_plantation,
                   statut
            FROM parcelle
            WHERE id_parcelle = ?
            """;

    private static final String SELECT_ALL_SQL = """
            SELECT id_parcelle,
                   id_ferme,
                   code_parcelle,
                   nom_parcelle,
                   superficie,
                   variete_orange,
                   nombre_arbres,
                   annee_plantation,
                   statut
            FROM parcelle
            ORDER BY id_parcelle ASC
            """;

    private static final String SELECT_BY_FERME_ID_SQL = """
            SELECT id_parcelle,
                   id_ferme,
                   code_parcelle,
                   nom_parcelle,
                   superficie,
                   variete_orange,
                   nombre_arbres,
                   annee_plantation,
                   statut
            FROM parcelle
            WHERE id_ferme = ?
            ORDER BY id_parcelle ASC
            """;

    private static final String DEACTIVATE_SQL = """
            UPDATE parcelle
            SET statut = ?
            WHERE id_parcelle = ?
            """;

    public ParcelleDAOImpl(Connection connection) {
        this.connection = Objects.requireNonNull(connection, "La connexion JDBC ne peut pas être nulle.");
    }

    @Override
    public Optional<Parcelle> findById(Long idParcelle) {
        if (idParcelle == null) {
            return Optional.empty();
        }

        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID_SQL)) {
            stmt.setLong(1, idParcelle);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de la parcelle par identifiant.", e);
        }
    }

    @Override
    public List<Parcelle> findAll() {
        List<Parcelle> parcelles = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                parcelles.add(mapRow(rs));
            }
            return parcelles;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de toutes les parcelles.", e);
        }
    }

    @Override
    public List<Parcelle> findByFermeId(Long idFerme) {
        List<Parcelle> parcelles = new ArrayList<>();
        if (idFerme == null) {
            return parcelles;
        }

        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_FERME_ID_SQL)) {
            stmt.setLong(1, idFerme);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    parcelles.add(mapRow(rs));
                }
            }
            return parcelles;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche des parcelles par ferme.", e);
        }
    }

    @Override
    public Parcelle save(Parcelle parcelle) {
        Objects.requireNonNull(parcelle, "La parcelle ne peut pas être nulle.");

        try (PreparedStatement stmt = connection.prepareStatement(INSERT_SQL)) {
            bindStatement(stmt, parcelle);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    parcelle.setIdParcelle(getNullableLong(rs, "id_parcelle"));
                }
            }
            return parcelle;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement de la parcelle.", e);
        }
    }

    @Override
    public void update(Parcelle parcelle) {
        Objects.requireNonNull(parcelle, "La parcelle ne peut pas être nulle.");
        Objects.requireNonNull(parcelle.getIdParcelle(), "L'identifiant de la parcelle est requis pour la mise à jour.");

        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_SQL)) {
            bindStatement(stmt, parcelle);
            stmt.setLong(9, parcelle.getIdParcelle());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la parcelle.", e);
        }
    }

    @Override
    public boolean deactivate(Long idParcelle) {
        if (idParcelle == null) {
            return false;
        }

        try (PreparedStatement stmt = connection.prepareStatement(DEACTIVATE_SQL)) {
            stmt.setString(1, "INACTIVE");
            stmt.setLong(2, idParcelle);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la désactivation de la parcelle.", e);
        }
    }

    private void bindStatement(PreparedStatement stmt, Parcelle parcelle) throws SQLException {
        setNullableLong(stmt, 1, parcelle.getIdFerme());
        stmt.setString(2, parcelle.getCodeParcelle());
        stmt.setString(3, parcelle.getNomParcelle());
        if (parcelle.getSuperficie() != null) {
            stmt.setBigDecimal(4, parcelle.getSuperficie());
        } else {
            stmt.setNull(4, Types.NUMERIC);
        }
        stmt.setString(5, parcelle.getVarieteOrange());
        setNullableInteger(stmt, 6, parcelle.getNombreArbres());
        setNullableInteger(stmt, 7, parcelle.getAnneePlantation());
        stmt.setString(8, parcelle.getStatut());
    }

    private Parcelle mapRow(ResultSet rs) throws SQLException {
        Parcelle parcelle = new Parcelle();
        parcelle.setIdParcelle(getNullableLong(rs, "id_parcelle"));
        parcelle.setIdFerme(getNullableLong(rs, "id_ferme"));
        parcelle.setCodeParcelle(rs.getString("code_parcelle"));
        parcelle.setNomParcelle(rs.getString("nom_parcelle"));
        parcelle.setSuperficie(rs.getBigDecimal("superficie"));
        parcelle.setVarieteOrange(rs.getString("variete_orange"));
        parcelle.setNombreArbres(getNullableInteger(rs, "nombre_arbres"));
        parcelle.setAnneePlantation(getNullableInteger(rs, "annee_plantation"));
        parcelle.setStatut(rs.getString("statut"));
        return parcelle;
    }

    private void setNullableLong(PreparedStatement stmt, int parameterIndex, Long value) throws SQLException {
        if (value != null) {
            stmt.setLong(parameterIndex, value);
        } else {
            stmt.setNull(parameterIndex, Types.BIGINT);
        }
    }

    private Long getNullableLong(ResultSet rs, String columnLabel) throws SQLException {
        long value = rs.getLong(columnLabel);
        return rs.wasNull() ? null : value;
    }

    private void setNullableInteger(PreparedStatement stmt, int parameterIndex, Integer value) throws SQLException {
        if (value != null) {
            stmt.setInt(parameterIndex, value);
        } else {
            stmt.setNull(parameterIndex, Types.INTEGER);
        }
    }

    private Integer getNullableInteger(ResultSet rs, String columnLabel) throws SQLException {
        int value = rs.getInt(columnLabel);
        return rs.wasNull() ? null : value;
    }
}