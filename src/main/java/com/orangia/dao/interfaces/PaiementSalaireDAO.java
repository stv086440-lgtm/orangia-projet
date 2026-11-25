package com.orangia.dao.interfaces;

import com.orangia.model.PaiementSalaire;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PaiementSalaireDAO {

    PaiementSalaire save(PaiementSalaire paiement);

    Optional<PaiementSalaire> findById(Long idPaiementSalaire);

    List<PaiementSalaire> findBySalaireId(Long idSalaire);

    List<PaiementSalaire> findAll();

    List<PaiementSalaire> findByPeriod(LocalDate dateDebut, LocalDate dateFin);

    BigDecimal getTotalPayeBySalaireId(Long idSalaire);
}
