
package com.orangia.dao.interfaces;

import com.orangia.model.PaiementExpedition;
import java.util.List;
import java.util.Optional;

public interface PaiementExpeditionDAO {

    Optional<PaiementExpedition> findById(Long id);

    List<PaiementExpedition> findAll();

    List<PaiementExpedition> findByExpeditionId(Long idExpedition);

    void save(PaiementExpedition paiement);
}
