package com.orangia.dao.interfaces;

import com.orangia.model.PaiementExpedition;
import java.util.List;
import java.util.Optional;

/**
 * Accès aux opérations financières liées aux expéditions.
 *
 * <p>Aucune méthode de modification ou de suppression n'est exposée : les
 * remboursements et annulations financières sont enregistrés comme opérations
 * distinctes.</p>
 */
public interface PaiementExpeditionDAO {

    Optional<PaiementExpedition> findById(Long idPaiement);

    List<PaiementExpedition> findAll();

    List<PaiementExpedition> findByExpeditionId(Long idExpedition);

    PaiementExpedition save(PaiementExpedition paiement);
}
