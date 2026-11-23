package com.orangia.dao.interfaces;

import com.orangia.model.JournalAction;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Accès en écriture et en lecture au journal d'audit ORANGIA.
 *
 * <p>Le journal est {@code append-only} : seules l'écriture et la consultation
 * sont exposées. Aucune méthode de modification ou de suppression n'est définie.</p>
 */
public interface JournalActionDAO {

    /**
     * Enregistre une nouvelle entrée dans le journal.
     *
     * @param journalAction entrée à enregistrer
     * @return l'entrée créée avec son identifiant généré par la base
     * @throws SQLException si une erreur d'accès à la base survient
     */
    JournalAction save(JournalAction journalAction) throws SQLException;

    /**
     * Retourne toutes les entrées du journal.
     *
     * @return la liste des entrées, vide si le journal est vide
     * @throws SQLException si une erreur d'accès à la base survient
     */
    List<JournalAction> findAll() throws SQLException;

    /**
     * Recherche les entrées associées à un utilisateur.
     *
     * @param idUtilisateur identifiant de l'utilisateur concerné
     * @return la liste des entrées correspondantes, vide si aucune
     * @throws SQLException si une erreur d'accès à la base survient
     */
    List<JournalAction> findByUtilisateur(Long idUtilisateur) throws SQLException;

    /**
     * Recherche les entrées dont la date appartient à un intervalle semi-ouvert.
     *
     * <p>Convention retenue : {@code debut} est inclus et {@code fin} est exclu,
     * soit l'intervalle {@code [debut, fin[}. Cette convention évite les
     * chevauchements entre deux périodes consécutives.</p>
     *
     * @param debut borne inférieure incluse
     * @param fin   borne supérieure exclue
     * @return la liste des entrées correspondantes, vide si aucune
     * @throws SQLException si une erreur d'accès à la base survient
     */
    List<JournalAction> findByDateRange(
            OffsetDateTime debut,
            OffsetDateTime fin
    ) throws SQLException;
}
