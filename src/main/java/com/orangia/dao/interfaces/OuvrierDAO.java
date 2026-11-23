package com.orangia.dao.interfaces;

import com.orangia.model.Ouvrier;

import java.util.List;
import java.util.Optional;

public interface OuvrierDAO {

    Ouvrier save(Ouvrier ouvrier);

    boolean update(Ouvrier ouvrier);

    Optional<Ouvrier> findById(Long idOuvrier);

    Optional<Ouvrier> findByCode(String codeOuvrier);

    List<Ouvrier> findAll();

    boolean updateStatut(Long idOuvrier, String statut);
}