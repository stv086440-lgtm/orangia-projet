package com.orangia.dao.interfaces;

import com.orangia.model.DemandeDepense;
import java.util.List;
import java.util.Optional;

public interface DemandeDepenseDAO {

    Optional<DemandeDepense> findById(Long idDemande);

    List<DemandeDepense> findAll();

    List<DemandeDepense> findByFermeId(Long idFerme);

    List<DemandeDepense> findByStatut(String statut);

    List<DemandeDepense> findPending();

    DemandeDepense save(DemandeDepense demande);

    void update(DemandeDepense demande);
}
