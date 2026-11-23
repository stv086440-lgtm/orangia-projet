
package com.orangia.dao.interfaces;

import com.orangia.model.Recolte;
import java.time.LocalDate;
import java.util.List;

public interface RecolteDAO {

    Recolte findById(long idRecolte);

    List<Recolte> findAll();

    List<Recolte> findByFermeId(long idFerme);

    List<Recolte> findByParcelleId(long idParcelle);

    List<Recolte> findByPeriod(LocalDate dateDebut, LocalDate dateFin);

    void save(Recolte recolte);

    void update(Recolte recolte);

    void cancel(long idRecolte, String motifAnnulation);
}
