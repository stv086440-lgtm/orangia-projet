package com.orangia.dao.impl;

import com.orangia.dao.interfaces.JournalActionDAO;
import com.orangia.database.BaseDAO;
import com.orangia.model.JournalAction;
import com.orangia.model.Role;
import com.orangia.model.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Implémentation JDBC de {@link JournalActionDAO}.
 *
 * <p>Le journal est {@code append-only} : seules l'écriture et la consultation sont
 * exposées. Aucune méthode de modification ou de suppression n'est ajoutée.</p>
 *
 * <p>Le Model {@link JournalAction} contient cinq champs absents du schéma actuel :
 * {@code module}, {@code anciennes_valeurs}, {@code nouvelles_valeurs},
 * {@code resultat} et {@code motif}. Aucune migration n'est réalisée ici : le mapping
 * est conservé intégralement dans le code et l'évolution SQL nécessaire est
 * documentée dans le rapport final.</p>
 *
 * <p>Évolution SQL à prévoir ultérieurement, sans modification des migrations
 * existantes :</p>
 * <pre>
 * ALTER TABLE JOURNAL_ACTION
 *     ADD COLUMN module VARCHAR(100),
 *     ADD COLUMN anciennes_valeurs TEXT,
 *     ADD COLUMN nouvelles_valeurs TEXT,
 *     ADD COLUMN resultat VARCHAR(20),
 *     ADD COLUMN motif TEXT;
 * </pre>
 *
 * <p>Convention de période : {@code findByDateRange} applique l'intervalle
 * semi-ouvert {@code [debut, fin[} décrit par l'interface. Une borne {@code null}
 * est traitée comme « non filtrée ».</p>
 */
public class JournalActionDAOImpl extends BaseDAO implements JournalActionDAO {

    private static final String SELECT_JOURNAL =
            "SELECT j.id_action AS j_id_action, " +
            "j.id_utilisateur AS j_id_utilisateur, " +
            "j.id_ferme AS j_id_ferme, " +
            "j.action AS j_action, " +
            "j.module AS j_module, " +
            "j.type_operation AS j_type_operation, " +
            "j.id_operation AS j_id_operation, " +
            "j.description AS j_description, " +
            "j.date_action AS j_date_action, " +
            "j.anciennes_valeurs AS j_anciennes_valeurs, " +
            "j.nouvelles_valeurs AS j_nouvelles_valeurs, " +
            "j.resultat AS j_resultat, " +
            "j.motif AS j_motif, " +
            "u.id_utilisateur AS u_id_utilisateur, " +
            "r.nom_role AS r_nom_role, " +
            "u.id_ferme AS u_id_ferme, " +
            "u.identifiant AS u_identifiant, " +
            "u.mot_de_passe AS u_mot_de_passe, " +
            "u.code_acces_hash AS u_code_acces_hash, " +
            "u.nom AS u_nom, " +
            "u.prenom AS u_prenom, " +
            "u.telephone AS u_telephone, " +
            "u.email AS u_email, " +
            "u.statut AS u_statut, " +
            "u.date_creation AS u_date_creation, " +
            "u.derniere_connexion AS u_derniere_connexion " +
            "FROM JOURNAL_ACTION j " +
            "LEFT JOIN UTILISATEUR u ON u.id_utilisateur = j.id_utilisateur " +
            "LEFT JOIN ROLE r ON r.id_role = u.id_role ";

    private static final String TRI_HISTORIQUE =
            "ORDER BY j.date_action DESC, j.id_action DESC";

    private static final String FIND_ALL =
            SELECT_JOURNAL + TRI_HISTORIQUE;

    private static final String FIND_BY_UTILISATEUR =
            SELECT_JOURNAL + "WHERE j.id_utilisateur = ? " + TRI_HISTORIQUE;

    private static final String INSERT_JOURNAL_AVEC_DATE =
            "INSERT INTO JOURNAL_ACTION " +
            "(id_utilisateur, id_ferme, action, module, type_operation, id_operation, " +
            "description, date_action, anciennes_valeurs, nouvelles_valeurs, resultat, motif) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
            "RETURNING id_action";

    private static final String INSERT_JOURNAL_SANS_DATE =
            "INSERT INTO JOURNAL_ACTION " +
            "(id_utilisateur, id_ferme, action, module, type_operation, id_operation, " +
            "description, anciennes_valeurs, nouvelles_valeurs, resultat, motif) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
            "RETURNING id_action";

    @Override
    public JournalAction save(JournalAction journalAction) throws SQLException {
        boolean avecDate = journalAction.getDateAction() != null;
        String sql = avecDate ? INSERT_JOURNAL_AVEC_DATE : INSERT_JOURNAL_SANS_DATE;

        try (Connection connexion = getConnection();
             PreparedStatement statement = connexion.prepareStatement(sql)) {

            int position = 1;
            position = binderValeurs(statement, journalAction, position, avecDate);

            try (ResultSet resultat = statement.executeQuery()) {
                if (resultat.next()) {
                    journalAction.setIdAction(resultat.getLong("id_action"));
                }
            }
        }

        return journalAction;
    }

    @Override
    public List<JournalAction> findAll() throws SQLException {
        List<JournalAction> actions = new ArrayList<>();

        try (Connection connexion = getConnection();
             PreparedStatement statement = connexion.prepareStatement(FIND_ALL);
             ResultSet resultat = statement.executeQuery()) {

            while (resultat.next()) {
                actions.add(mapJournalAction(resultat));
            }
        }

        return actions;
    }

    @Override
    public List<JournalAction> findByUtilisateur(Long idUtilisateur) throws SQLException {
        List<JournalAction> actions = new ArrayList<>();

        try (Connection connexion = getConnection();
             PreparedStatement statement = connexion.prepareStatement(FIND_BY_UTILISATEUR)) {

            setNullableLong(statement, 1, idUtilisateur);

            try (ResultSet resultat = statement.executeQuery()) {
                while (resultat.next()) {
                    actions.add(mapJournalAction(resultat));
                }
            }
        }

        return actions;
    }

    @Override
    public List<JournalAction> findByDateRange(
            OffsetDateTime debut,
            OffsetDateTime fin
    ) throws SQLException {

        StringBuilder sql = new StringBuilder(SELECT_JOURNAL);
        List<OffsetDateTime> bornes = new ArrayList<>();

        if (debut != null) {
            sql.append("WHERE j.date_action >= ? ");
            bornes.add(debut);
        }

        if (fin != null) {
            sql.append(bornes.isEmpty() ? "WHERE " : "AND ");
            sql.append("j.date_action < ? ");
            bornes.add(fin);
        }

        sql.append(TRI_HISTORIQUE);

        List<JournalAction> actions = new ArrayList<>();

        try (Connection connexion = getConnection();
             PreparedStatement statement = connexion.prepareStatement(sql.toString())) {

            for (int index = 0; index < bornes.size(); index++) {
                setNullableOffsetDateTime(statement, index + 1, bornes.get(index));
            }

            try (ResultSet resultat = statement.executeQuery()) {
                while (resultat.next()) {
                    actions.add(mapJournalAction(resultat));
                }
            }
        }

        return actions;
    }

    private static int binderValeurs(
            PreparedStatement statement,
            JournalAction journalAction,
            int positionDepart,
            boolean avecDate
    ) throws SQLException {

        int position = positionDepart;
        setNullableLong(statement, position++, idUtilisateur(journalAction.getUtilisateur()));
        setNullableLong(statement, position++, journalAction.getIdFerme());
        setNullableString(statement, position++, journalAction.getAction());
        setNullableString(statement, position++, journalAction.getModule());
        setNullableString(statement, position++, journalAction.getTypeOperation());
        setNullableLong(statement, position++, journalAction.getIdOperation());
        setNullableString(statement, position++, journalAction.getDescription());

        if (avecDate) {
            setNullableOffsetDateTime(statement, position++, journalAction.getDateAction());
        }

        setNullableString(statement, position++, journalAction.getAnciennesValeurs());
        setNullableString(statement, position++, journalAction.getNouvellesValeurs());
        setNullableString(statement, position++, journalAction.getResultat());
        setNullableString(statement, position, journalAction.getMotif());

        return position;
    }

    private static JournalAction mapJournalAction(ResultSet resultat) throws SQLException {
        JournalAction journalAction = new JournalAction();

        journalAction.setIdAction(resultat.getObject("j_id_action", Long.class));
        journalAction.setUtilisateur(mapUtilisateur(resultat, "u_"));
        journalAction.setAction(resultat.getString("j_action"));
        journalAction.setModule(resultat.getString("j_module"));
        journalAction.setTypeOperation(resultat.getString("j_type_operation"));
        journalAction.setIdOperation(resultat.getObject("j_id_operation", Long.class));
        journalAction.setIdFerme(resultat.getObject("j_id_ferme", Long.class));
        journalAction.setDateAction(
                resultat.getObject("j_date_action", OffsetDateTime.class)
        );
        journalAction.setDescription(resultat.getString("j_description"));
        journalAction.setAnciennesValeurs(resultat.getString("j_anciennes_valeurs"));
        journalAction.setNouvellesValeurs(resultat.getString("j_nouvelles_valeurs"));
        journalAction.setResultat(resultat.getString("j_resultat"));
        journalAction.setMotif(resultat.getString("j_motif"));

        return journalAction;
    }

    private static Utilisateur mapUtilisateur(ResultSet resultat, String prefixe)
            throws SQLException {

        Long id = resultat.getObject(prefixe + "id_utilisateur", Long.class);
        if (id == null) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setIdUtilisateur(id);
        utilisateur.setRole(mapRole(resultat.getString("r_nom_role")));
        utilisateur.setIdFerme(resultat.getObject(prefixe + "id_ferme", Long.class));
        utilisateur.setIdentifiant(resultat.getString(prefixe + "identifiant"));
        utilisateur.setMotDePasseHash(resultat.getString(prefixe + "mot_de_passe"));
        utilisateur.setCodeAccesHash(resultat.getString(prefixe + "code_acces_hash"));
        utilisateur.setNom(resultat.getString(prefixe + "nom"));
        utilisateur.setPrenom(resultat.getString(prefixe + "prenom"));
        utilisateur.setTelephone(resultat.getString(prefixe + "telephone"));
        utilisateur.setEmail(resultat.getString(prefixe + "email"));
        utilisateur.setStatut(resultat.getString(prefixe + "statut"));
        utilisateur.setDateCreation(
                resultat.getObject(prefixe + "date_creation", OffsetDateTime.class)
        );
        utilisateur.setDerniereConnexion(
                resultat.getObject(prefixe + "derniere_connexion", OffsetDateTime.class)
        );

        return utilisateur;
    }

    private static Role mapRole(String nomRole) {
        if (nomRole == null) {
            return null;
        }

        String normalise = nomRole.trim().toUpperCase(Locale.ROOT);
        if ("ADMIN".equals(normalise)) {
            return Role.ADMINISTRATEUR;
        }

        return Role.valueOf(normalise);
    }

    private static Long idUtilisateur(Utilisateur utilisateur) {
        return utilisateur == null ? null : utilisateur.getIdUtilisateur();
    }

    private static void setNullableLong(
            PreparedStatement statement,
            int index,
            Long valeur
    ) throws SQLException {
        if (valeur == null) {
            statement.setNull(index, Types.BIGINT);
        } else {
            statement.setLong(index, valeur);
        }
    }

    private static void setNullableString(
            PreparedStatement statement,
            int index,
            String valeur
    ) throws SQLException {
        if (valeur == null) {
            statement.setNull(index, Types.VARCHAR);
        } else {
            statement.setString(index, valeur);
        }
    }

    private static void setNullableOffsetDateTime(
            PreparedStatement statement,
            int index,
            OffsetDateTime valeur
    ) throws SQLException {
        if (valeur == null) {
            statement.setNull(index, Types.TIMESTAMP_WITH_TIMEZONE);
        } else {
            statement.setObject(index, valeur);
        }
    }
}
