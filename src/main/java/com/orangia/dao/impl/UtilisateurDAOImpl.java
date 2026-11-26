package com.orangia.dao.impl;

import com.orangia.dao.interfaces.UtilisateurDAO;
import com.orangia.database.BaseDAO;
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
 * Implémentation JDBC de {@link UtilisateurDAO}.
 *
 * <p>Toutes les opérations sont paramétrées et utilisent {@code try-with-resources}.
 * Aucune règle métier, aucun contrôle de permission et aucune journalisation ne sont
 * réalisés ici : ces responsabilités appartiennent à la couche Service.</p>
 *
 * <p>Mapping assumé à partir du Planning v3.4 et du schéma existant :</p>
 * <ul>
 *     <li>{@code Utilisateur.motDePasseHash} ↔ {@code UTILISATEUR.mot_de_passe}
 *         (le nom de colonne historique est conservé, aucune migration SQL n'est faite ici) ;</li>
 *     <li>{@code Utilisateur.role} ↔ {@code UTILISATEUR.id_role} + {@code ROLE.nom_role} ;</li>
 *     <li>{@code ACTIF} / {@code INACTIF} sont les valeurs utilisées pour
 *         {@link #activate(Long)} et {@link #deactivate(Long)} ; la documentation
 *         fonctionnelle ne les énumère pas explicitement, seul {@code ACTIF} apparaît
 *         dans les données initiales.</li>
 * </ul>
 *
 * <p>Remarque de compatibilité : le Planning v3.4 nomme le rôle administrateur
 * {@code ADMIN}, tandis que {@link Role}, le schéma et les données initiales utilisent
 * {@code ADMINISTRATEUR}. Le mapping accepte les deux libellés en lecture et écrit
 * systématiquement la valeur de l'enum Java.</p>
 */
public class UtilisateurDAOImpl extends BaseDAO implements UtilisateurDAO {

    private static final String STATUT_ACTIF = "ACTIF";
    private static final String STATUT_INACTIF = "INACTIF";

    private static final String SELECT_UTILISATEUR =
            "SELECT u.id_utilisateur AS u_id_utilisateur, " +
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
            "FROM UTILISATEUR u " +
            "JOIN ROLE r ON r.id_role = u.id_role ";

    private static final String FIND_BY_ID =
            SELECT_UTILISATEUR + "WHERE u.id_utilisateur = ?";

    private static final String FIND_BY_IDENTIFIANT =
            SELECT_UTILISATEUR + "WHERE u.identifiant = ?";

    private static final String FIND_ALL =
            SELECT_UTILISATEUR + "ORDER BY u.id_utilisateur";

    private static final String FIND_RESPONSABLE_ACTIF =
            SELECT_UTILISATEUR +
            "WHERE u.id_ferme = ? " +
            "AND r.nom_role = ? " +
            "AND u.statut = ?";

    private static final String INSERT_UTILISATEUR =
            "INSERT INTO UTILISATEUR " +
            "(id_role, id_ferme, identifiant, mot_de_passe, code_acces_hash, " +
            "nom, prenom, telephone, email, statut, derniere_connexion) " +
            "VALUES ((SELECT id_role FROM ROLE WHERE nom_role = ?), " +
            "?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
            "RETURNING id_utilisateur";

    private static final String UPDATE_UTILISATEUR =
            "UPDATE UTILISATEUR SET " +
            "identifiant = ?, " +
            "id_role = (SELECT id_role FROM ROLE WHERE nom_role = ?), " +
            "id_ferme = ?, " +
            "nom = ?, " +
            "prenom = ?, " +
            "telephone = ?, " +
            "email = ?, " +
            "statut = ? " +
            "WHERE id_utilisateur = ?";

    private static final String UPDATE_STATUT =
            "UPDATE UTILISATEUR SET statut = ? WHERE id_utilisateur = ?";

    private static final String UPDATE_MOT_DE_PASSE =
            "UPDATE UTILISATEUR SET mot_de_passe = ? WHERE id_utilisateur = ?";

    private static final String UPDATE_CODE_ACCES =
            "UPDATE UTILISATEUR SET code_acces_hash = ? WHERE id_utilisateur = ?";

    private static final String UPDATE_FERME =
            "UPDATE UTILISATEUR SET id_ferme = ? WHERE id_utilisateur = ?";

    private static final String UPDATE_DERNIERE_CONNEXION =
            "UPDATE UTILISATEUR SET derniere_connexion = ? WHERE id_utilisateur = ?";

    @Override
    public Optional<Utilisateur> findById(Long idUtilisateur) throws SQLException {
        try (Connection connexion = getConnection();
             PreparedStatement statement = connexion.prepareStatement(FIND_BY_ID)) {

            setNullableLong(statement, 1, idUtilisateur);

            try (ResultSet resultat = statement.executeQuery()) {
                if (resultat.next()) {
                    return Optional.of(mapUtilisateur(resultat, "u_"));
                }
                return Optional.empty();
            }
        }
    }

    @Override
    public Optional<Utilisateur> findByIdentifiant(String identifiant) throws SQLException {
        try (Connection connexion = getConnection();
             PreparedStatement statement = connexion.prepareStatement(FIND_BY_IDENTIFIANT)) {

            setNullableString(statement, 1, identifiant);

            try (ResultSet resultat = statement.executeQuery()) {
                if (resultat.next()) {
                    return Optional.of(mapUtilisateur(resultat, "u_"));
                }
                return Optional.empty();
            }
        }
    }

    @Override
    public List<Utilisateur> findAll() throws SQLException {
        List<Utilisateur> utilisateurs = new ArrayList<>();

        try (Connection connexion = getConnection();
             PreparedStatement statement = connexion.prepareStatement(FIND_ALL);
             ResultSet resultat = statement.executeQuery()) {

            while (resultat.next()) {
                utilisateurs.add(mapUtilisateur(resultat, "u_"));
            }
        }

        return utilisateurs;
    }

    @Override
    public Utilisateur save(Utilisateur utilisateur) throws SQLException {
        try (Connection connexion = getConnection();
             PreparedStatement statement = connexion.prepareStatement(INSERT_UTILISATEUR)) {

            int parametre = 1;
            statement.setString(parametre++, nomRole(utilisateur.getRole()));
            setNullableLong(statement, parametre++, utilisateur.getIdFerme());
            statement.setString(parametre++, utilisateur.getIdentifiant());
            statement.setString(parametre++, utilisateur.getMotDePasseHash());
            setNullableString(statement, parametre++, utilisateur.getCodeAccesHash());
            statement.setString(parametre++, utilisateur.getNom());
            statement.setString(parametre++, utilisateur.getPrenom());
            setNullableString(statement, parametre++, utilisateur.getTelephone());
            setNullableString(statement, parametre++, utilisateur.getEmail());
            statement.setString(parametre++, utilisateur.getStatut());
            setNullableOffsetDateTime(statement, parametre, utilisateur.getDerniereConnexion());

            try (ResultSet resultat = statement.executeQuery()) {
                if (resultat.next()) {
                    utilisateur.setIdUtilisateur(resultat.getLong("id_utilisateur"));
                }
            }
        }

        return utilisateur;
    }

    @Override
    public void update(Utilisateur utilisateur) throws SQLException {
        try (Connection connexion = getConnection();
             PreparedStatement statement = connexion.prepareStatement(UPDATE_UTILISATEUR)) {

            int parametre = 1;
            statement.setString(parametre++, utilisateur.getIdentifiant());
            statement.setString(parametre++, nomRole(utilisateur.getRole()));
            setNullableLong(statement, parametre++, utilisateur.getIdFerme());
            statement.setString(parametre++, utilisateur.getNom());
            statement.setString(parametre++, utilisateur.getPrenom());
            setNullableString(statement, parametre++, utilisateur.getTelephone());
            setNullableString(statement, parametre++, utilisateur.getEmail());
            statement.setString(parametre++, utilisateur.getStatut());
            setNullableLong(statement, parametre, utilisateur.getIdUtilisateur());

            statement.executeUpdate();
        }
    }

    @Override
    public void deactivate(Long idUtilisateur) throws SQLException {
        updateStatut(idUtilisateur, STATUT_INACTIF);
    }

    @Override
    public void activate(Long idUtilisateur) throws SQLException {
        updateStatut(idUtilisateur, STATUT_ACTIF);
    }

    @Override
    public void updatePasswordHash(Long idUtilisateur, String motDePasseHash) throws SQLException {
        try (Connection connexion = getConnection();
             PreparedStatement statement = connexion.prepareStatement(UPDATE_MOT_DE_PASSE)) {

            statement.setString(1, motDePasseHash);
            setNullableLong(statement, 2, idUtilisateur);
            statement.executeUpdate();
        }
    }

    @Override
    public void updateCodeAccesHash(Long idUtilisateur, String codeAccesHash) throws SQLException {
        try (Connection connexion = getConnection();
             PreparedStatement statement = connexion.prepareStatement(UPDATE_CODE_ACCES)) {

            setNullableString(statement, 1, codeAccesHash);
            setNullableLong(statement, 2, idUtilisateur);
            statement.executeUpdate();
        }
    }

    @Override
    public Optional<Utilisateur> findResponsableActifByFermeId(Long idFerme) throws SQLException {
        try (Connection connexion = getConnection();
             PreparedStatement statement = connexion.prepareStatement(FIND_RESPONSABLE_ACTIF)) {

            setNullableLong(statement, 1, idFerme);
            statement.setString(2, Role.RESPONSABLE.name());
            statement.setString(3, STATUT_ACTIF);

            try (ResultSet resultat = statement.executeQuery()) {
                if (resultat.next()) {
                    return Optional.of(mapUtilisateur(resultat, "u_"));
                }
                return Optional.empty();
            }
        }
    }

    @Override
    public void assignFerme(Long idUtilisateur, Long idFerme) throws SQLException {
        try (Connection connexion = getConnection();
             PreparedStatement statement = connexion.prepareStatement(UPDATE_FERME)) {

            setNullableLong(statement, 1, idFerme);
            setNullableLong(statement, 2, idUtilisateur);
            statement.executeUpdate();
        }
    }

    @Override
    public void updateDerniereConnexion(
            Long idUtilisateur,
            OffsetDateTime derniereConnexion
    ) throws SQLException {
        try (Connection connexion = getConnection();
             PreparedStatement statement = connexion.prepareStatement(UPDATE_DERNIERE_CONNEXION)) {

            setNullableOffsetDateTime(statement, 1, derniereConnexion);
            setNullableLong(statement, 2, idUtilisateur);
            statement.executeUpdate();
        }
    }

    private void updateStatut(Long idUtilisateur, String statut) throws SQLException {
        try (Connection connexion = getConnection();
             PreparedStatement statement = connexion.prepareStatement(UPDATE_STATUT)) {

            statement.setString(1, statut);
            setNullableLong(statement, 2, idUtilisateur);
            statement.executeUpdate();
        }
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

    private static String nomRole(Role role) {
        return role == null ? null : role.name();
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
