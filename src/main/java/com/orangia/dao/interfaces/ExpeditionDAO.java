package com.orangia.dao.interfaces;

import com.orangia.model.Expedition;
import java.util.List;
import java.util.Optional;

public interface ExpeditionDAO {

    Optional<Expedition> findById(Long id);

    List<Expedition> findAll();

    List<Expedition> findByFermeId(Long idFerme);

    List<Expedition> findByClientId(Long idClient);

    List<Expedition> findByStatus(String statut);

    List<Expedition> findPendingConfirmation();

    void save(Expedition expedition);

    void update(Expedition expedition);

    void cancel(Long idExpedition);
}