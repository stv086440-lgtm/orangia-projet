package com.orangia.dao.impl;

import com.orangia.dao.interfaces.ParametreSystemeDAO;
import com.orangia.database.BaseDAO;
import com.orangia.model.ParametreSysteme;
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
import java.util.Optional;

/**
 * Implémentation JDBC de {@link ParametreSystemeDAO}.
 *
 * <p>Le Planning v3.4 et le Model {@link ParametreSysteme} imposent une date de
 * modification. La colonne {@code date_modification} n'existe pas encore dans
 * {@code PARAMETRE_SYSTEME}. Aucune migration n'est réalisée ici : le mapping est
 * conservé tel quel dans le code et l'évolution SQL nécessaire est documentée
 * dans le rapport final.</p>
 *
 * <p>Évolution SQL à prévoir ultérieurement, sans modification des migrations
 * existantes :</p>
 * <pre>
 * ALTER TABLE PARAMETRE_SYSTEME
 *     ADD COLUMN date_modification TIMESTAMPTZ;
 * </pre>
 *
 * <p>La clé fonctionnelle {@code cle} est bien le « nom du paramètre » visé par
 * {@link #findByName(String)}. L'utilisateur de modification est joint pour
 * mapper intégralement {@code modifiePar}.</p>
 */
public class ParametreSystemeDAOImpl extends BaseDAO implements ParametreSystemeDAO {

    private static final String SELECT_PARAMETRE =
            "SELECT p.id_parametre AS p_id_parametre, " +
            "p.cle AS p_cle, " +
            "p.valeur AS p_valeur, " +
            "p.description AS p_description, " +
            "p.modifie_par AS p_modifie_par, " +
            "p.date_modification AS p_date_modification, " +
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
            "FROM PARAMETRE_SYSTEME p " +
            "LEFT JOIN UTILISATEUR u ON u.id_utilisateur = p.modifie_par " +
            "LEFT JOIN ROLE r ON r.id_role = u.id_role ";

    private static final String FIND_BY_NAME =
            SELECT_PARAMETRE + "WHERE p.cle = ?";

    private static final String FIND_ALL =
            SELECT_PARAMETRE + "ORDER BY p.cle";

    private static final String INSERT_PARAMETRE =
            "INSERT INTO PARAMETRE_SYSTEME " +
            "(cle, valeur, description, modifie_par, date_modification) " +
            "VALUES (?, ?, ?, ?, ?) " +
            "RETURNING id_parametre";

    private static final String UPDATE_PARAMETRE =
            "UPDATE PARAMETRE_SYSTEME SET " +
            "cle = ?, " +
            "valeur = ?, " +
            "description = ?, " +
            "modifie_par = ?, " +
            "date_modification = ? " +
            "WHERE id_parametre = ?";

    @Override
    public Optional<ParametreSysteme> findByName(String nom) throws SQLException {
        try (Connection connexion = getConnection();
             PreparedStatement statement = connexion.prepareStatement(FIND_BY_NAME)) {

            setNullableString(statement, 1, nom);

            try (ResultSet resultat = statement.executeQuery()) {
                if (resultat.next()) {
                    return Optional.of(mapParametre(resultat));
                }
                return Optional.empty();
            }
        }
    }

    @Override
    public List<ParametreSysteme> findAll() throws SQLException {
        List<ParametreSysteme> parametres = new ArrayList<>();

        try (Connection connexion = getConnection();
             PreparedStatement statement = connexion.prepareStatement(FIND_ALL);
             ResultSet resultat = statement.executeQuery()) {

            while (resultat.next()) {
                parametres.add(mapParametre(resultat));
            }
        }

        return parametres;
    }

    @Override
    public ParametreSysteme save(ParametreSysteme parametre) throws SQLException {
        try (Connection connexion = getConnection();
             PreparedStatement statement = connexion.prepareStatement(INSERT_PARAMETRE)) {

            int position = 1;
            statement.setString(position++, parametre.getCle());
            setNullableString(statement, position++, parametre.getValeur());
            setNullableString(statement, position++, parametre.getDescription());
            setNullableLong(statement, position++, idUtilisateur(parametre.getModifiePar()));
            setNullableOffsetDateTime(statement, position, parametre.getDateModification());

            try (ResultSet resultat = statement.executeQuery()) {
                if (resultat.next()) {
                    parametre.setIdParametre(resultat.getLong("id_parametre"));
                }
            }
        }

        return parametre;
    }

    @Override
    public void update(ParametreSysteme parametre) throws SQLException {
        try (Connection connexion = getConnection();
             PreparedStatement statement = connexion.prepareStatement(UPDATE_PARAMETRE)) {

            int position = 1;
            statement.setString(position++, parametre.getCle());
            setNullableString(statement, position++, parametre.getValeur());
            setNullableString(statement, position++, parametre.getDescription());
            setNullableLong(statement, position++, idUtilisateur(parametre.getModifiePar()));
            setNullableOffsetDateTime(statement, position++, parametre.getDateModification());
            setNullableLong(statement, position, parametre.getIdParametre());

            statement.executeUpdate();
        }
    }

    private static ParametreSysteme mapParametre(ResultSet resultat) throws SQLException {
        ParametreSysteme parametre = new ParametreSysteme();

        parametre.setIdParametre(resultat.getObject("p_id_parametre", Long.class));
        parametre.setCle(resultat.getString("p_cle"));
        parametre.setValeur(resultat.getString("p_valeur"));
        parametre.setDescription(resultat.getString("p_description"));
        parametre.setModifiePar(mapUtilisateur(resultat, "u_"));
        parametre.setDateModification(
                resultat.getObject("p_date_modification", OffsetDateTime.class)
        );

        return parametre;
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
