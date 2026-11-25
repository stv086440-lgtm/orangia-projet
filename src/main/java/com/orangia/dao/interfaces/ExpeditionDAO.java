package com.orangia.dao.interfaces;

import com.orangia.model.Expedition;
import java.util.List;
import java.util.Optional;

public interface ExpeditionDAO {

    Optional<Expedition> findById(Long idExpedition);

    List<Expedition> findAll();

    List<Expedition> findByFermeId(Long idFerme);

    List<Expedition> findByClientId(Long idClient);

    List<Expedition> findByStatus(String statut);

    List<Expedition> findPendingConfirmation();

    Expedition save(Expedition expedition);

    void update(Expedition expedition);

    void cancel(Long idExpedition);

    /**
     * Retourne les versions d'une expédition révisée, y compris l'ancienne
     * version confirmée qui doit rester consultable.
     *
     * @param idExpedition identifiant de l'expédition
     * @return les versions trouvées, vide si aucune
     */
    List<Expedition> findVersionsByExpeditionId(Long idExpedition);
}
