package com.orangia.dao.impl;

import com.orangia.dao.interfaces.DemandeCorrectionDAO;
import com.orangia.model.DemandeCorrection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DemandeCorrectionDAOImpl implements DemandeCorrectionDAO {

    private final Connection connection;

    private static final String INSERT_SQL = """
            INSERT INTO demande_correction (
                id_presence,
                demande_par,
                motif,
                description,
                document_id,
                statut,
                traitee_par,
                commentaire_rh
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id_demande_correction
            """;

    private static final String UPDATE_SQL = """
            UPDATE demande_correction
            SET id_presence = ?,
                demande_par = ?,
                motif = ?,
                description = ?,
                document_id = ?,
                statut = ?,
                traitee_par = ?,
                commentaire_rh = ?
            WHERE id_demande_correction = ?
            """;

    private static final String SELECT_BY_ID_SQL = """
            SELECT id_demande_correction,
                   id_presence,
                   demande_par,
                   motif,
                   description,
                   document_id,
                   statut,
                   traitee_par,
                   commentaire_rh
            FROM demande_correction
            WHERE id_demande_correction = ?
            """;

    private static final String SELECT_BY_PRESENCE_ID_SQL = """
            SELECT id_demande_correction,
                   id_presence,
                   demande_par,
                   motif,
                   description,
                   document_id,
                   statut,
                   traitee_par,
                   commentaire_rh
            FROM demande_correction
            WHERE id_presence = ?
            ORDER BY id_demande_correction DESC
            """;

    private static final String SELECT_BY_STATUT_SQL = """
            SELECT id_demande_correction,
                   id_presence,
                   demande_par,
                   motif,
                   description,
                   document_id,
                   statut,
                   traitee_par,
                   commentaire_rh
            FROM demande_correction
            WHERE statut = ?
            ORDER BY id_demande_correction DESC
            """;

    private static final String SELECT_ALL_SQL = """
            SELECT id_demande_correction,
                   id_presence,
                   demande_par,
                   motif,
                   description,
                   document_id,
                   statut,
                   traitee_par,
                   commentaire_rh
            FROM demande_correction
            ORDER BY id_demande_correction DESC
            """;

    public DemandeCorrectionDAOImpl(Connection connection) {
        this.connection = Objects.requireNonNull(connection, "La connexion JDBC ne peut pas être nulle.");
    }

    @Override
    public DemandeCorrection save(DemandeCorrection demande) {
        Objects.requireNonNull(demande, "La demande de correction ne peut pas être nulle.");

        try (PreparedStatement stmt = connection.prepareStatement(INSERT_SQL)) {
            bindStatement(stmt, demande);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    demande.setIdDemandeCorrection(rs.getLong("id_demande_correction"));
                }
            }
            return demande;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement de la demande de correction.", e);
        }
    }

    @Override
    public void update(DemandeCorrection demande) {
        Objects.requireNonNull(demande, "La demande de correction ne peut pas être nulle.");
        Objects.requireNonNull(demande.getIdDemandeCorrection(), "L'identifiant de la demande de correction est requis pour la mise à jour.");

        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_SQL)) {
            bindStatement(stmt, demande);
            stmt.setLong(9, demande.getIdDemandeCorrection());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la demande de correction.", e);
        }
    }

    @Override
    public Optional<DemandeCorrection> findById(Long idDemandeCorrection) {
        if (idDemandeCorrection == null) {
            return Optional.empty();
        }

        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID_SQL)) {
            stmt.setLong(1, idDemandeCorrection);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de la demande de correction par identifiant.", e);
        }
    }

    @Override
    public List<DemandeCorrection> findByPresenceId(Long idPresence) {
        List<DemandeCorrection> demandes = new ArrayList<>();
        if (idPresence == null) {
            return demandes;
        }

        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_PRESENCE_ID_SQL)) {
            stmt.setLong(1, idPresence);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    demandes.add(mapRow(rs));
                }
            }
            return demandes;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche des demandes de correction par présence.", e);
        }
    }

    @Override
    public List<DemandeCorrection> findByStatut(String statut) {
        List<DemandeCorrection> demandes = new ArrayList<>();
        if (statut == null || statut.isBlank()) {
            return demandes;
        }

        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_STATUT_SQL)) {
            stmt.setString(1, statut);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    demandes.add(mapRow(rs));
                }
            }
            return demandes;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche des demandes de correction par statut.", e);
        }
    }

    @Override
    public List<DemandeCorrection> findAll() {
        List<DemandeCorrection> demandes = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                demandes.add(mapRow(rs));
            }
            return demandes;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de toutes les demandes de correction.", e);
        }
    }

    private void bindStatement(PreparedStatement stmt, DemandeCorrection demande) throws SQLException {
        setNullableLong(stmt, 1, demande.getIdPresence());
        setNullableLong(stmt, 2, demande.getDemandePar());
        stmt.setString(3, demande.getMotif());
        stmt.setString(4, demande.getDescription());
        setNullableLong(stmt, 5, demande.getDocumentId());
        stmt.setString(6, demande.getStatut());
        setNullableLong(stmt, 7, demande.getTraiteePar());
        stmt.setString(8, demande.getCommentaireRh());
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

    private DemandeCorrection mapRow(ResultSet rs) throws SQLException {
        DemandeCorrection demande = new DemandeCorrection();
        demande.setIdDemandeCorrection(getNullableLong(rs, "id_demande_correction"));
        demande.setIdPresence(getNullableLong(rs, "id_presence"));
        demande.setDemandePar(getNullableLong(rs, "demande_par"));
        demande.setMotif(rs.getString("motif"));
        demande.setDescription(rs.getString("description"));
        demande.setDocumentId(getNullableLong(rs, "document_id"));
        demande.setStatut(rs.getString("statut"));
        demande.setTraiteePar(getNullableLong(rs, "traitee_par"));
        demande.setCommentaireRh(rs.getString("commentaire_rh"));
        return demande;
    }
}