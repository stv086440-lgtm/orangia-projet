package com.orangia.dao.interfaces;

import com.orangia.model.Salaire;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SalaireDAO {

    Salaire save(Salaire salaire);

    void update(Salaire salaire);

    Optional<Salaire> findById(Long idSalaire);

    List<Salaire> findByOuvrierId(Long idOuvrier);

    List<Salaire> findByFermeId(Long idFerme);

    List<Salaire> findByPeriod(LocalDate dateDebut, LocalDate dateFin);

    List<Salaire> findAll();

    /**
     * Retourne les coûts salariaux agrégés par ferme pour une période.
     *
     * <p>Lecture agrégée destinée au Comptable : aucune donnée individuelle RH
     * ni paiement de salaire n'est exposé.</p>
     *
     * @param dateDebut borne inférieure de la période, incluse
     * @param dateFin   borne supérieure de la période, incluse
     * @return une map {@code idFerme -> montant total}, vide si aucun salaire
     */
    Map<Long, BigDecimal> findCoutAgregeParPeriode(LocalDate dateDebut, LocalDate dateFin);
}
