package com.orangia.dao.impl;

import com.orangia.dao.interfaces.SalaireDAO;
import com.orangia.model.Salaire;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class SalaireDAOImpl implements SalaireDAO {

    private final Connection connection;

    private static final String INSERT_SQL = """
            INSERT INTO salaire (
                id_ouvrier,
                id_ferme,
                id_tarif,
                periode_debut,
                periode_fin,
                montant_base,
                montant_prime,
                montant_total,
                detail_calcul
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id_salaire
            """;

    private static final String UPDATE_SQL = """
            UPDATE salaire
            SET id_ouvrier = ?,
                id_ferme = ?,
                id_tarif = ?,
                periode_debut = ?,
                periode_fin = ?,
                montant_base = ?,
                montant_prime = ?,
                montant_total = ?,
                detail_calcul = ?
            WHERE id_salaire = ?
            """;

    private static final String SELECT_BY_ID_SQL = """
            SELECT id_salaire,
                   id_ouvrier,
                   id_ferme,
                   id_tarif,
                   periode_debut,
                   periode_fin,
                   montant_base,
                   montant_prime,
                   montant_total,
                   detail_calcul
            FROM salaire
            WHERE id_salaire = ?
            """;

    private static final String SELECT_BY_OUVRIER_ID_SQL = """
            SELECT id_salaire,
                   id_ouvrier,
                   id_ferme,
                   id_tarif,
                   periode_debut,
                   periode_fin,
                   montant_base,
                   montant_prime,
                   montant_total,
                   detail_calcul
            FROM salaire
            WHERE id_ouvrier = ?
            ORDER BY periode_debut DESC, id_salaire DESC
            """;

    private static final String SELECT_BY_FERME_ID_SQL = """
            SELECT id_salaire,
                   id_ouvrier,
                   id_ferme,
                   id_tarif,
                   periode_debut,
                   periode_fin,
                   montant_base,
                   montant_prime,
                   montant_total,
                   detail_calcul
            FROM salaire
            WHERE id_ferme = ?
            ORDER BY periode_debut DESC, id_salaire DESC
            """;

    private static final String SELECT_BY_PERIOD_SQL = """
            SELECT id_salaire,
                   id_ouvrier,
                   id_ferme,
                   id_tarif,
                   periode_debut,
                   periode_fin,
                   montant_base,
                   montant_prime,
                   montant_total,
                   detail_calcul
            FROM salaire
            WHERE periode_debut >= ?
              AND periode_fin <= ?
            ORDER BY periode_debut ASC, id_salaire ASC
            """;

    private static final String SELECT_ALL_SQL = """
            SELECT id_salaire,
                   id_ouvrier,
                   id_ferme,
                   id_tarif,
                   periode_debut,
                   periode_fin,
                   montant_base,
                   montant_prime,
                   montant_total,
                   detail_calcul
            FROM salaire
            ORDER BY periode_debut DESC, id_salaire DESC
            """;

    private static final String COUT_AGREGE_PAR_PERIODE_SQL = """
            SELECT id_ferme,
                   COALESCE(SUM(montant_total), 0) AS cout_total
            FROM salaire
            WHERE periode_debut >= ?
              AND periode_fin <= ?
              AND id_ferme IS NOT NULL
            GROUP BY id_ferme
            ORDER BY id_ferme ASC
            """;

    public SalaireDAOImpl(Connection connection) {
        this.connection = Objects.requireNonNull(connection, "La connexion JDBC ne peut pas être nulle.");
    }

    @Override
    public Salaire save(Salaire salaire) {
        Objects.requireNonNull(salaire, "Le salaire ne peut pas être nul.");

        try (PreparedStatement stmt = connection.prepareStatement(INSERT_SQL)) {
            bindStatement(stmt, salaire);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    salaire.setIdSalaire(getNullableLong(rs, "id_salaire"));
                }
            }
            return salaire;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du salaire.", e);
        }
    }

    @Override
    public void update(Salaire salaire) {
        Objects.requireNonNull(salaire, "Le salaire ne peut pas être nul.");
        Objects.requireNonNull(salaire.getIdSalaire(), "L'identifiant du salaire est requis pour la mise à jour.");

        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_SQL)) {
            bindStatement(stmt, salaire);
            stmt.setLong(10, salaire.getIdSalaire());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du salaire.", e);
        }
    }

    @Override
    public Optional<Salaire> findById(Long idSalaire) {
        if (idSalaire == null) {
            return Optional.empty();
        }

        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID_SQL)) {
            stmt.setLong(1, idSalaire);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche du salaire par identifiant.", e);
        }
    }

    @Override
    public List<Salaire> findByOuvrierId(Long idOuvrier) {
        List<Salaire> salaires = new ArrayList<>();
        if (idOuvrier == null) {
            return salaires;
        }

        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_OUVRIER_ID_SQL)) {
            stmt.setLong(1, idOuvrier);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    salaires.add(mapRow(rs));
                }
            }
            return salaires;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche des salaires par ouvrier.", e);
        }
    }

    @Override
    public List<Salaire> findByFermeId(Long idFerme) {
        List<Salaire> salaires = new ArrayList<>();
        if (idFerme == null) {
            return salaires;
        }

        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_FERME_ID_SQL)) {
            stmt.setLong(1, idFerme);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    salaires.add(mapRow(rs));
                }
            }
            return salaires;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche des salaires par ferme.", e);
        }
    }

    @Override
    public List<Salaire> findByPeriod(LocalDate dateDebut, LocalDate dateFin) {
        List<Salaire> salaires = new ArrayList<>();
        if (dateDebut == null || dateFin == null) {
            return salaires;
        }

        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_PERIOD_SQL)) {
            stmt.setDate(1, Date.valueOf(dateDebut));
            stmt.setDate(2, Date.valueOf(dateFin));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    salaires.add(mapRow(rs));
                }
            }
            return salaires;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche des salaires par période.", e);
        }
    }

    @Override
    public List<Salaire> findAll() {
        List<Salaire> salaires = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                salaires.add(mapRow(rs));
            }
            return salaires;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de tous les salaires.", e);
        }
    }

    @Override
    public Map<Long, BigDecimal> findCoutAgregeParPeriode(LocalDate dateDebut, LocalDate dateFin) {
        Map<Long, BigDecimal> coutsParFerme = new LinkedHashMap<>();
        if (dateDebut == null || dateFin == null) {
            return coutsParFerme;
        }

        try (PreparedStatement stmt = connection.prepareStatement(COUT_AGREGE_PAR_PERIODE_SQL)) {
            stmt.setDate(1, Date.valueOf(dateDebut));
            stmt.setDate(2, Date.valueOf(dateFin));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Long idFerme = getNullableLong(rs, "id_ferme");
                    if (idFerme != null) {
                        BigDecimal cout = rs.getBigDecimal("cout_total");
                        coutsParFerme.put(idFerme, cout != null ? cout : BigDecimal.ZERO);
                    }
                }
            }
            return coutsParFerme;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du calcul des coûts salariaux agrégés par période.", e);
        }
    }

    private void bindStatement(PreparedStatement stmt, Salaire salaire) throws SQLException {
        setNullableLong(stmt, 1, salaire.getIdOuvrier());
        setNullableLong(stmt, 2, salaire.getIdFerme());
        setNullableLong(stmt, 3, salaire.getIdTarif());
        setNullableDate(stmt, 4, salaire.getPeriodeDebut());
        setNullableDate(stmt, 5, salaire.getPeriodeFin());
        setNullableBigDecimal(stmt, 6, salaire.getMontantBase());
        setNullableBigDecimal(stmt, 7, salaire.getMontantPrime());
        setNullableBigDecimal(stmt, 8, salaire.getMontantTotal());
        if (salaire.getDetailCalcul() != null) {
            stmt.setObject(9, salaire.getDetailCalcul(), Types.OTHER);
        } else {
            stmt.setNull(9, Types.OTHER);
        }
    }

    private Salaire mapRow(ResultSet rs) throws SQLException {
        Salaire salaire = new Salaire();
        salaire.setIdSalaire(getNullableLong(rs, "id_salaire"));
        salaire.setIdOuvrier(getNullableLong(rs, "id_ouvrier"));
        salaire.setIdFerme(getNullableLong(rs, "id_ferme"));
        salaire.setIdTarif(getNullableLong(rs, "id_tarif"));
        salaire.setPeriodeDebut(getNullableLocalDate(rs, "periode_debut"));
        salaire.setPeriodeFin(getNullableLocalDate(rs, "periode_fin"));
        salaire.setMontantBase(rs.getBigDecimal("montant_base"));
        salaire.setMontantPrime(rs.getBigDecimal("montant_prime"));
        salaire.setMontantTotal(rs.getBigDecimal("montant_total"));
        salaire.setDetailCalcul(rs.getString("detail_calcul"));
        return salaire;
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

    private void setNullableDate(PreparedStatement stmt, int parameterIndex, LocalDate value) throws SQLException {
        if (value != null) {
            stmt.setDate(parameterIndex, Date.valueOf(value));
        } else {
            stmt.setNull(parameterIndex, Types.DATE);
        }
    }

    private LocalDate getNullableLocalDate(ResultSet rs, String columnLabel) throws SQLException {
        Date date = rs.getDate(columnLabel);
        return date != null ? date.toLocalDate() : null;
    }

    private void setNullableBigDecimal(PreparedStatement stmt, int parameterIndex, BigDecimal value) throws SQLException {
        if (value != null) {
            stmt.setBigDecimal(parameterIndex, value);
        } else {
            stmt.setNull(parameterIndex, Types.NUMERIC);
        }
    }
}