package com.orangia.dao.interfaces;

import com.orangia.model.FichePresence;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FichePresenceDAO {

    FichePresence save(FichePresence fiche);

    boolean update(FichePresence fiche);

    Optional<FichePresence> findById(Long idFiche);

    Optional<FichePresence> findByFermeAndDate(Long idFerme, LocalDate dateFiche);

    List<FichePresence> findByFermeId(Long idFerme);

    List<FichePresence> findAll();

    boolean updateStatut(Long idFiche, String statut);
}