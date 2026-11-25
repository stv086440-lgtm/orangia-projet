package com.orangia.dao.interfaces;

import com.orangia.model.ParametreSysteme;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Accès aux paramètres système ORANGIA.
 *
 * <p>Cette interface définit uniquement les contrats d'accès aux données.
 * Les règles de gestion et la journalisation des modifications restent
 * à la charge de la couche Service.</p>
 *
 * <p>Le modèle {@link ParametreSysteme} conserve la propriété
 * {@code dateModification}. La colonne SQL correspondante sera ajoutée
 * ultérieurement.</p>
 */
public interface ParametreSystemeDAO {

    /**
     * Recherche un paramètre par son nom fonctionnel unique.
     *
     * <p>Le planning nomme cette opération {@code findByName()} et décrit
     * « nom du paramètre ». Le Model expose actuellement {@code cle} ; le
     * mapping JDBC doit utiliser la colonne unique correspondante sans
     * modifier le Model.</p>
     *
     * @param nom nom/clé unique du paramètre
     * @return le paramètre trouvé, ou {@link Optional#empty()} sinon
     * @throws SQLException si une erreur d'accès à la base survient
     */
    Optional<ParametreSysteme> findByName(String nom) throws SQLException;

    /**
     * Retourne tous les paramètres système.
     *
     * @return la liste des paramètres, vide si aucun paramètre n'existe
     * @throws SQLException si une erreur d'accès à la base survient
     */
    List<ParametreSysteme> findAll() throws SQLException;

    /**
     * Crée un paramètre système.
     *
     * @param parametre paramètre à créer
     * @return le paramètre créé avec son identifiant généré par la base
     * @throws SQLException si une erreur d'accès à la base survient
     */
    ParametreSysteme save(ParametreSysteme parametre) throws SQLException;

    /**
     * Met à jour un paramètre système existant.
     *
     * <p>La propriété {@code dateModification} fait partie du contrat
     * du modèle et devra être prise en charge lors de l'implémentation JDBC.</p>
     *
     * @param parametre paramètre contenant les nouvelles valeurs
     * @throws SQLException si une erreur d'accès à la base survient
     */
    void update(ParametreSysteme parametre) throws SQLException;
}
