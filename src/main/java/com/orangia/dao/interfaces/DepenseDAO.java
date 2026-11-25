package com.orangia.dao.interfaces;

import com.orangia.model.Depense;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DepenseDAO {

    Optional<Depense> findById(Long idDepense);

    List<Depense> findAll();

    List<Depense> findByFermeId(Long idFerme);

    List<Depense> findByPeriod(LocalDate dateDebut, LocalDate dateFin);

    List<Depense> findByDemandeId(Long idDemande);

    Depense save(Depense depense);

    void update(Depense depense);
}
