package com.orangia.dao.interfaces;

import com.orangia.model.Ferme;
import java.util.List;
import java.util.Optional;

public interface FermeDAO {

    Optional<Ferme> findById(Long idFerme);

    List<Ferme> findAll();

    Ferme save(Ferme ferme);

    Ferme update(Ferme ferme);

    boolean deactivate(Long idFerme);
}