package com.orangia.dao.interfaces;

import com.orangia.model.Depense;
import java.time.LocalDate;
import java.util.List;

public interface DepenseDAO {

    // Rechercher une dépense par son ID
    Depense findById(int idDepense);

    // Récupérer toutes les dépenses
    List<Depense> findAll();

    // Récupérer les dépenses d'une ferme
    List<Depense> findByFermeId(int idFerme);

    // Rechercher les dépenses pendant une période
    List<Depense> findByPeriod(LocalDate dateDebut, LocalDate dateFin);

    // Ajouter une dépense
    void save(Depense depense);

    // Modifier une dépense
    void update(Depense depense);
}