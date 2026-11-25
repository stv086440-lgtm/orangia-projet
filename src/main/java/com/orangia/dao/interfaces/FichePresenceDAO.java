package com.orangia.dao.interfaces;

import com.orangia.model.FichePresence;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FichePresenceDAO {

    FichePresence save(FichePresence fiche);

    void update(FichePresence fiche);

    Optional<FichePresence> findById(Long idFiche);

    /**
     * Recherche la fiche d'une ferme pour une date, quel que soit son statut.
     *
     * @param idFerme   identifiant de la ferme
     * @param dateFiche date de la fiche
     * @return la fiche trouvée, ou {@link Optional#empty()} sinon
     */
    Optional<FichePresence> findByFermeAndDate(Long idFerme, LocalDate dateFiche);

    /**
     * Recherche la fiche active d'une ferme pour une date.
     *
     * <p>Méthode explicitement prévue par le planning. Les statuts considérés
     * comme « actifs » doivent être validés avec le Chef de projet : le
     * planning et l'architecture indiquent BROUILLON, TRANSMISE,
     * EN_TRAITEMENT, VALIDEE, RETOURNEE, tandis que DB-designe.md indique
     * BROUILLON, TRANSMISE, VALIDEE, A_CORRIGER.</p>
     *
     * @param idFerme   identifiant de la ferme
     * @param dateFiche date de la fiche
     * @return la fiche active trouvée, ou {@link Optional#empty()} sinon
     */
    Optional<FichePresence> findFicheActiveByFermeAndDate(Long idFerme, LocalDate dateFiche);

    List<FichePresence> findByFermeId(Long idFerme);

    List<FichePresence> findAll();

    void updateStatut(Long idFiche, String statut);
}
