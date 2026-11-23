package com.orangia.dao.interfaces;

import com.orangia.model.AffectationOuvrier;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AffectationOuvrierDAO {

    AffectationOuvrier save(AffectationOuvrier affectation);

    boolean update(AffectationOuvrier affectation);

    Optional<AffectationOuvrier> findById(Long idAffectation);

    List<AffectationOuvrier> findByOuvrierId(Long idOuvrier);

    List<AffectationOuvrier> findByFermeId(Long idFerme);

    Optional<AffectationOuvrier> findActiveByOuvrierAndDate(Long idOuvrier, LocalDate date);

    List<AffectationOuvrier> findAll();
}
