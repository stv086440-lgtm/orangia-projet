package com.orangia.dao.interfaces;

import com.orangia.model.DemandeCorrection;
import java.util.List;
import java.util.Optional;

public interface DemandeCorrectionDAO {

    DemandeCorrection save(DemandeCorrection demande);

    void update(DemandeCorrection demande);

    Optional<DemandeCorrection> findById(Long idDemandeCorrection);

    List<DemandeCorrection> findByPresenceId(Long idPresence);

    List<DemandeCorrection> findByStatut(String statut);

    List<DemandeCorrection> findAll();
}
