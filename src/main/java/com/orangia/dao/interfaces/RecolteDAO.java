package com.orangia.dao.interfaces;

import com.orangia.model.Recolte;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RecolteDAO {

    Optional<Recolte> findById(Long idRecolte);

    List<Recolte> findAll();

    List<Recolte> findByFermeId(Long idFerme);

    List<Recolte> findByParcelleId(Long idParcelle);

    List<Recolte> findByPeriod(LocalDate dateDebut, LocalDate dateFin);

    Recolte save(Recolte recolte);

    void update(Recolte recolte);

    void cancel(Long idRecolte, String motifAnnulation);
}
