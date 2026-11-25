package com.orangia.dao.interfaces;

import com.orangia.model.Parcelle;
import java.util.List;
import java.util.Optional;

public interface ParcelleDAO {

    Optional<Parcelle> findById(Long idParcelle);

    List<Parcelle> findAll();

    List<Parcelle> findByFermeId(Long idFerme);

    Parcelle save(Parcelle parcelle);

    void update(Parcelle parcelle);

    boolean deactivate(Long idParcelle);
}
