package com.orangia.dao.interfaces;

import com.orangia.model.Presence;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PresenceDAO {

    Presence save(Presence presence);

    boolean update(Presence presence);

    Optional<Presence> findById(Long idPresence);

    Optional<Presence> findByOuvrierAndDate(Long idOuvrier, LocalDate datePresence);

    List<Presence> findByFicheId(Long idFiche);

    List<Presence> findByDate(LocalDate datePresence);
}