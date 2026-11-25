package com.orangia.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Fiche de présence quotidienne d'une ferme.
 *
 * <p>Les horaires applicables à la fiche sont demandés par le planning
 * (§ Membre 4). Le schéma SQL actuel ne les contient pas encore au niveau de
 * FICHE_PRESENCE : ils figurent seulement dans PRESENCE.</p>
 *
 * <p>Conflit de statuts non tranché par les documents : planning (§ Membre 4
 * et § « Règles des fiches de présence ») et architecture (§ 6.3) indiquent
 * BROUILLON, TRANSMISE, EN_TRAITEMENT, VALIDEE, RETOURNEE, tandis que
 * DB-designe.md (§ D. FICHE_PRESENCE) indique BROUILLON, TRANSMISE, VALIDEE,
 * A_CORRIGER. Aucune valeur n'est imposée ici : à valider avec le chef de
 * projet.</p>
 */
public class FichePresence {

    private Long idFiche;
    private Long idFerme;
    private LocalDate dateFiche;
    private String statut;
    private Long transmisPar;
    private Long validePar;
    private LocalTime heureDebutAppliquee;
    private LocalTime heureFinAppliquee;
    private String observation;

    public FichePresence() {
    }

    public FichePresence(Long idFiche, Long idFerme, LocalDate dateFiche,
                         String statut, Long transmisPar, Long validePar,
                         LocalTime heureDebutAppliquee, LocalTime heureFinAppliquee,
                         String observation) {
        this.idFiche = idFiche;
        this.idFerme = idFerme;
        this.dateFiche = dateFiche;
        this.statut = statut;
        this.transmisPar = transmisPar;
        this.validePar = validePar;
        this.heureDebutAppliquee = heureDebutAppliquee;
        this.heureFinAppliquee = heureFinAppliquee;
        this.observation = observation;
    }

    public Long getIdFiche() { return idFiche; }
    public void setIdFiche(Long idFiche) { this.idFiche = idFiche; }

    public Long getIdFerme() { return idFerme; }
    public void setIdFerme(Long idFerme) { this.idFerme = idFerme; }

    public LocalDate getDateFiche() { return dateFiche; }
    public void setDateFiche(LocalDate dateFiche) { this.dateFiche = dateFiche; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public Long getTransmisPar() { return transmisPar; }
    public void setTransmisPar(Long transmisPar) { this.transmisPar = transmisPar; }

    public Long getValidePar() { return validePar; }
    public void setValidePar(Long validePar) { this.validePar = validePar; }

    public LocalTime getHeureDebutAppliquee() { return heureDebutAppliquee; }
    public void setHeureDebutAppliquee(LocalTime heureDebutAppliquee) {
        this.heureDebutAppliquee = heureDebutAppliquee;
    }

    public LocalTime getHeureFinAppliquee() { return heureFinAppliquee; }
    public void setHeureFinAppliquee(LocalTime heureFinAppliquee) {
        this.heureFinAppliquee = heureFinAppliquee;
    }

    public String getObservation() { return observation; }
    public void setObservation(String observation) { this.observation = observation; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FichePresence that)) return false;
        return Objects.equals(idFiche, that.idFiche);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFiche);
    }

    @Override
    public String toString() {
        return "FichePresence{" +
                "idFiche=" + idFiche +
                ", idFerme=" + idFerme +
                ", dateFiche=" + dateFiche +
                ", statut='" + statut + '\'' +
                ", transmisPar=" + transmisPar +
                ", validePar=" + validePar +
                ", observation='" + observation + '\'' +
                '}';
    }
}
