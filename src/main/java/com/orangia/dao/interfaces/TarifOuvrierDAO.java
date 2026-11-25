package com.orangia.dao.interfaces;

import com.orangia.model.TarifOuvrier;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TarifOuvrierDAO {

    TarifOuvrier save(TarifOuvrier tarif);

    void update(TarifOuvrier tarif);

    Optional<TarifOuvrier> findById(Long idTarif);

    List<TarifOuvrier> findByOuvrierId(Long idOuvrier);

    Optional<TarifOuvrier> findApplicableTarif(Long idOuvrier, LocalDate date);

    List<TarifOuvrier> findAll();
}
