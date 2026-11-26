package com.orangia.dao.impl;

import com.orangia.dao.interfaces.FermeDAO;
import com.orangia.model.Ferme;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FermeDAOImpl implements FermeDAO {

    private final Connection connection;

    private static final String INSERT_SQL = """
            INSERT INTO ferme (
                code_ferme,
                nom_ferme,
                localisation,
                superficie,
                statut
            )
            VALUES (?, ?, ?, ?, ?)
            RETURNING id_ferme, date_creation
            """;

    private static final String UPDATE_SQL = """
            UPDATE ferme
            SET code_ferme = ?,
                nom_ferme = ?,
                localisation = ?,
                superficie = ?,
                statut = ?
            WHERE id_ferme = ?
            RETURNING id_ferme, code_ferme, nom_ferme, localisation, superficie, statut, date_creation
            """;

    private static final String SELECT_BY_ID_SQL = """
            SELECT id_ferme,
                   code_ferme,
                   nom_ferme,
                   localisation,
                   superficie,
                   statut,
                   date_creation
            FROM ferme
            WHERE id_ferme = ?
            """;

    private static final String SELECT_ALL_SQL = """
            SELECT id_ferme,
                   code_ferme,
                   nom_ferme,
                   localisation,
                   superficie,
                   statut,
                   date_creation
            FROM ferme
            ORDER BY id_ferme ASC
            """;

    private static final String DEACTIVATE_SQL = """
            UPDATE ferme
            SET statut = ?
            WHERE id_ferme = ?
            """;

    public FermeDAOImpl(Connection connection) {
        this.connection = Objects.requireNonNull(connection, "La connexion JDBC ne peut pas être nulle.");
    }

    @Override
    public Optional<Ferme> findById(Long idFerme) {
        if (idFerme == null) {
            return Optional.empty();
        }

        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID_SQL)) {
            stmt.setLong(1, idFerme);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de la ferme par identifiant.", e);
        }
    }

    @Override
    public List<Ferme> findAll() {
        List<Ferme> fermes = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                fermes.add(mapRow(rs));
            }
            return fermes;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de toutes les fermes.", e);
        }
    }

    @Override
    public Ferme save(Ferme ferme) {
        Objects.requireNonNull(ferme, "La ferme ne peut pas être nulle.");

        try (PreparedStatement stmt = connection.prepareStatement(INSERT_SQL)) {
            bindStatement(stmt, ferme);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ferme.setIdFerme(getNullableLong(rs, "id_ferme"));
                    ferme.setDateCreation(getNullableOffsetDateTime(rs, "date_creation"));
                }
            }
            return ferme;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement de la ferme.", e);
        }
    }

    @Override
    public Ferme update(Ferme ferme) {
        Objects.requireNonNull(ferme, "La ferme ne peut pas être nulle.");
        Objects.requireNonNull(ferme.getIdFerme(), "L'identifiant de la ferme est requis pour la mise à jour.");

        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_SQL)) {
            bindStatement(stmt, ferme);
            stmt.setLong(6, ferme.getIdFerme());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
            return ferme;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la ferme.", e);
        }
    }

    @Override
    public boolean deactivate(Long idFerme) {
        if (idFerme == null) {
            return false;
        }

        try (PreparedStatement stmt = connection.prepareStatement(DEACTIVATE_SQL)) {
            stmt.setString(1, "INACTIVE");
            stmt.setLong(2, idFerme);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la désactivation de la ferme.", e);
        }
    }

    private void bindStatement(PreparedStatement stmt, Ferme ferme) throws SQLException {
        stmt.setString(1, ferme.getCodeFerme());
        stmt.setString(2, ferme.getNomFerme());
        stmt.setString(3, ferme.getLocalisation());
        if (ferme.getSuperficie() != null) {
            stmt.setBigDecimal(4, ferme.getSuperficie());
        } else {
            stmt.setNull(4, Types.NUMERIC);
        }
        stmt.setString(5, ferme.getStatut());
    }

    private Ferme mapRow(ResultSet rs) throws SQLException {
        Ferme ferme = new Ferme();
        ferme.setIdFerme(getNullableLong(rs, "id_ferme"));
        ferme.setCodeFerme(rs.getString("code_ferme"));
        ferme.setNomFerme(rs.getString("nom_ferme"));
        ferme.setLocalisation(rs.getString("localisation"));
        ferme.setSuperficie(rs.getBigDecimal("superficie"));
        ferme.setStatut(rs.getString("statut"));
        ferme.setDateCreation(getNullableOffsetDateTime(rs, "date_creation"));
        return ferme;
    }

    private Long getNullableLong(ResultSet rs, String columnLabel) throws SQLException {
        long value = rs.getLong(columnLabel);
        return rs.wasNull() ? null : value;
    }

    private OffsetDateTime getNullableOffsetDateTime(ResultSet rs, String columnLabel) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnLabel);
        return timestamp != null ? timestamp.toInstant().atOffset(OffsetDateTime.now().getOffset()) : null;
    }
}