package com.orangia.dao.interfaces;

import com.orangia.model.Utilisateur;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Accès aux comptes utilisateurs ORANGIA.
 *
 * <p>Cette interface définit uniquement les opérations d'accès aux données.
 * Les règles métier, les contrôles d'autorisation et la journalisation
 * restent à la charge de la couche Service.</p>
 */
public interface UtilisateurDAO {

    /**
     * Recherche un utilisateur par son identifiant technique.
     *
     * @param idUtilisateur identifiant généré par la base
     * @return l'utilisateur trouvé, ou {@link Optional#empty()} sinon
     * @throws SQLException si une erreur d'accès à la base survient
     */
    Optional<Utilisateur> findById(Long idUtilisateur) throws SQLException;

    /**
     * Recherche un utilisateur par son identifiant de connexion unique.
     *
     * @param identifiant identifiant de connexion
     * @return l'utilisateur trouvé, ou {@link Optional#empty()} sinon
     * @throws SQLException si une erreur d'accès à la base survient
     */
    Optional<Utilisateur> findByIdentifiant(String identifiant) throws SQLException;

    /**
     * Retourne tous les comptes utilisateurs.
     *
     * <p>Les hashes présents dans les objets retournés ne doivent pas être
     * exposés directement à l'interface utilisateur.</p>
     *
     * @return la liste des utilisateurs, vide si aucun compte n'existe
     * @throws SQLException si une erreur d'accès à la base survient
     */
    List<Utilisateur> findAll() throws SQLException;

    /**
     * Crée un compte utilisateur.
     *
     * @param utilisateur compte à créer, avec les hashes déjà calculés
     * @return le compte créé avec son identifiant généré par la base
     * @throws SQLException si une erreur d'accès à la base survient
     */
    Utilisateur save(Utilisateur utilisateur) throws SQLException;

    /**
     * Met à jour les informations d'un compte utilisateur existant.
     *
     * @param utilisateur compte contenant les nouvelles valeurs
     * @throws SQLException si une erreur d'accès à la base survient
     */
    void update(Utilisateur utilisateur) throws SQLException;

    /**
     * Désactive un compte utilisateur sans le supprimer.
     *
     * @param idUtilisateur identifiant du compte à désactiver
     * @throws SQLException si une erreur d'accès à la base survient
     */
    void deactivate(Long idUtilisateur) throws SQLException;
    /**
     * Active un compte utilisateur.
    *
    * @param idUtilisateur identifiant du compte à activer
    * @throws SQLException si une erreur d'accès à la base survient
    */
    void activate(Long idUtilisateur) throws SQLException;
    /**
      * Remplace le hash du mot de passe d'un compte.
     *
     * @param idUtilisateur  identifiant du compte concerné
     * @param motDePasseHash nouveau hash du mot de passe, jamais le mot de passe en clair
     * @throws SQLException si une erreur d'accès à la base survient
     */
    void updatePasswordHash(Long idUtilisateur, String motDePasseHash) throws SQLException;

    /**
     * Remplace le hash du code alphanumérique Administrateur d'un compte.
     *
     * @param idUtilisateur identifiant du compte concerné
     * @param codeAccesHash nouveau hash du code, jamais le code en clair
     * @throws SQLException si une erreur d'accès à la base survient
     */
    void updateCodeAccesHash(Long idUtilisateur, String codeAccesHash) throws SQLException;

    /**
     * Recherche le Responsable actif associé à une ferme.
     *
     * @param idFerme identifiant de la ferme
     * @return le Responsable actif trouvé, ou {@link Optional#empty()} sinon
     * @throws SQLException si une erreur d'accès à la base survient
     */
    Optional<Utilisateur> findResponsableActifByFermeId(Long idFerme) throws SQLException;

    /**
     * Associe un utilisateur à une ferme.
     *
     * @param idUtilisateur identifiant du compte concerné
     * @param idFerme       identifiant de la ferme à associer
     * @throws SQLException si une erreur d'accès à la base survient
     */
    void assignFerme(Long idUtilisateur, Long idFerme) throws SQLException;

    /**
     * Enregistre la date de dernière connexion d'un compte.
     *
     * <p>Méthode complémentaire conservée : elle sert au suivi des sessions
     * sans exposer ni mot de passe ni code alphanumérique.</p>
     *
     * @param idUtilisateur     identifiant du compte concerné
     * @param derniereConnexion date et heure de la connexion
     * @throws SQLException si une erreur d'accès à la base survient
     */
    void updateDerniereConnexion(
            Long idUtilisateur,
            OffsetDateTime derniereConnexion
    ) throws SQLException;
}
