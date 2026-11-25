package com.orangia.dao.interfaces;

import com.orangia.model.Ouvrier;
import java.util.List;
import java.util.Optional;

public interface OuvrierDAO {

    Ouvrier save(Ouvrier ouvrier);

    void update(Ouvrier ouvrier);

    Optional<Ouvrier> findById(Long idOuvrier);

    Optional<Ouvrier> findByCode(String codeOuvrier);

    List<Ouvrier> findAll();

    /**
     * Retourne les ouvriers affectés à une ferme, toutes périodes confondues.
     *
     * <p>Le lien passe par {@code AFFECTATION_OUVRIER} ; le filtrage de la
     * ferme du Responsable reste à la charge des Services.</p>
     *
     * @param idFerme identifiant de la ferme
     * @return les ouvriers concernés, vide si aucun
     */
    List<Ouvrier> findByFermeId(Long idFerme);

    void updateStatut(Long idOuvrier, String statut);
}
