package com.orangia.dao.interfaces;

import com.orangia.model.Document;
import java.util.List;
import java.util.Optional;

/**
 * Accès aux métadonnées des documents.
 *
 * <p>Aucune suppression physique n'est exposée : la base conserve les
 * métadonnées et la référence de stockage des documents associés aux
 * opérations métier.</p>
 */
public interface DocumentDAO {

    Optional<Document> findById(Long idDocument);

    List<Document> findAll();

    List<Document> findByType(String typeDocument);

    /**
     * Recherche les documents associés à une entité métier.
     *
     * @param typeOperation type de l'entité associée (expédition, fiche de
     *                      présence, demande de correction, demande de dépense
     *                      ou dépense réelle)
     * @param idOperation   identifiant de l'entité associée
     * @return les documents trouvés, vide si aucun
     */
    List<Document> findByEntite(String typeOperation, Long idOperation);

    Document save(Document document);

    void update(Document document);
}
