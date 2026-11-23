package com.orangia.dao.interfaces;

import com.orangia.model.Salaire;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SalaireDAO {

    Salaire save(Salaire salaire);

    boolean update(Salaire salaire);

    Optional<Salaire> findById(Long idSalaire);

    List<Salaire> findByOuvrierId(Long idOuvrier);

    List<Salaire> findByPeriode(LocalDate dateDebut, LocalDate dateFin);

    List<Salaire> findAll();
}