package com.orangia.dao.impl;

import com.orangia.dao.interfaces.PaiementSalaireDAO;
import com.orangia.model.PaiementSalaire;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PaiementSalaireDAOImpl implements PaiementSalaireDAO {

    private final Connection connection;

    private static final String INSERT_SQL = """
            INSERT INTO paiement_salaire (
                id_salaire,
                montant_paye,
                date_paiement,
                mode_paiement,
                reference,
                enregistre_par
            )
            VALUES (?, ?, ?, ?, ?, ?)
            RETURNING id_paiement_salaire
            """;

    private static final String SELECT_BY_ID_SQL = """
            SELECT id_paiement_salaire,
                   id_salaire,
                   montant_paye,
                   date_paiement,
                   mode_paiement,
                   reference,
                   enregistre_par
            FROM paiement_salaire
            WHERE id_paiement_salaire = ?
            """;

    private static final String SELECT_BY_SALAIRE_ID_SQL = """
            SELECT id_paiement_salaire,
                   id_salaire,
                   montant_paye,
                   date_paiement,
                   mode_paiement,
                   reference,
                   enregistre_par
            FROM paiement_salaire
            WHERE id_salaire = ?
            ORDER BY date_paiement DESC, id_paiement_salaire DESC
            """;

    private static final String SELECT_ALL_SQL = """
            SELECT id_paiement_salaire,
                   id_salaire,
                   montant_paye,
                   date_paiement,
                   mode_paiement,
                   reference,
                   enregistre_par
            FROM paiement_salaire
            ORDER BY date_paiement DESC, id_paiement_salaire DESC
            """;

    private static final String SELECT_BY_PERIOD_SQL = """
            SELECT id_paiement_salaire,
                   id_salaire,
                   montant_paye,
                   date_paiement,
                   mode_paiement,
                   reference,
                   enregistre_par
            FROM paiement_salaire
            WHERE date_paiement BETWEEN ? AND ?
            ORDER BY date_paiement ASC, id_paiement_salaire ASC
            """;

    private static final String SUM_MONTANT_BY_SALAIRE_ID_SQL = """
            SELECT COALESCE(SUM(montant_paye), 0) AS total_paye
            FROM paiement_salaire
            WHERE id_salaire = ?
            """;

    public PaiementSalaireDAOImpl(Connection connection) {
        this.connection = Objects.requireNonNull(connection, "La connexion JDBC ne peut pas être nulle.");
    }

    @Override
    public PaiementSalaire save(PaiementSalaire paiement) {
        Objects.requireNonNull(paiement, "Le paiement de salaire ne peut pas être nul.");

        try (PreparedStatement stmt = connection.prepareStatement(INSERT_SQL)) {
            bindStatement(stmt, paiement);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    paiement.setIdPaiementSalaire(getNullableLong(rs, "id_paiement_salaire"));
                }
            }
            return paiement;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du paiement de salaire.", e);
        }
    }

    @Override
    public Optional<PaiementSalaire> findById(Long idPaiementSalaire) {
        if (idPaiementSalaire == null) {
            return Optional.empty();
        }

        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID_SQL)) {
            stmt.setLong(1, idPaiementSalaire);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche du paiement de salaire par identifiant.", e);
        }
    }

    @Override
    public List<PaiementSalaire> findBySalaireId(Long idSalaire) {
        List<PaiementSalaire> paiements = new ArrayList<>();
        if (idSalaire == null) {
            return paiements;
        }

        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_SALAIRE_ID_SQL)) {
            stmt.setLong(1, idSalaire);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    paiements.add(mapRow(rs));
                }
            }
            return paiements;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche des paiements par salaire.", e);
        }
    }

    @Override
    public List<PaiementSalaire> findAll() {
        List<PaiementSalaire> paiements = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                paiements.add(mapRow(rs));
            }
            return paiements;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de tous les paiements de salaire.", e);
        }
    }

    @Override
    public List<PaiementSalaire> findByPeriod(LocalDate dateDebut, LocalDate dateFin) {
        List<PaiementSalaire> paiements = new ArrayList<>();
        if (dateDebut == null || dateFin == null) {
            return paiements;
        }

        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_PERIOD_SQL)) {
            stmt.setDate(1, Date.valueOf(dateDebut));
            stmt.setDate(2, Date.valueOf(dateFin));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    paiements.add(mapRow(rs));
                }
            }
            return paiements;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche des paiements de salaire par période.", e);
        }
    }

    @Override
    public BigDecimal getTotalPayeBySalaireId(Long idSalaire) {
        if (idSalaire == null) {
            return BigDecimal.ZERO;
        }

        try (PreparedStatement stmt = connection.prepareStatement(SUM_MONTANT_BY_SALAIRE_ID_SQL)) {
            stmt.setLong(1, idSalaire);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    BigDecimal total = rs.getBigDecimal("total_paye");
                    return total != null ? total : BigDecimal.ZERO;
                }
            }
            return BigDecimal.ZERO;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du calcul du total payé pour le salaire.", e);
        }
    }

    private void bindStatement(PreparedStatement stmt, PaiementSalaire paiement) throws SQLException {
        setNullableLong(stmt, 1, paiement.getIdSalaire());
        if (paiement.getMontantPaye() != null) {
            stmt.setBigDecimal(2, paiement.getMontantPaye());
        } else {
            stmt.setNull(2, Types.NUMERIC);
        }
        if (paiement.getDatePaiement() != null) {
            stmt.setDate(3, Date.valueOf(paiement.getDatePaiement()));
        } else {
            stmt.setNull(3, Types.DATE);
        }
        stmt.setString(4, paiement.getModePaiement());
        stmt.setString(5, paiement.getReference());
        setNullableLong(stmt, 6, paiement.getEnregistrePar());
    }

    private PaiementSalaire mapRow(ResultSet rs) throws SQLException {
        PaiementSalaire paiement = new PaiementSalaire();
        paiement.setIdPaiementSalaire(getNullableLong(rs, "id_paiement_salaire"));
        paiement.setIdSalaire(getNullableLong(rs, "id_salaire"));
        paiement.setMontantPaye(rs.getBigDecimal("montant_paye"));
        Date datePaiement = rs.getDate("date_paiement");
        paiement.setDatePaiement(datePaiement != null ? datePaiement.toLocalDate() : null);
        paiement.setModePaiement(rs.getString("mode_paiement"));
        paiement.setReference(rs.getString("reference"));
        paiement.setEnregistrePar(getNullableLong(rs, "enregistre_par"));
        return paiement;
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
}