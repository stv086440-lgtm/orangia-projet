package com.orangia.model;

import java.time.LocalDate;
import java.util.Objects;

public class FichePresence {

    private Long idFiche;
    private Long idFerme;
    private LocalDate dateFiche;
    private String statut;
    private Long transmisPar;
    private Long validePar;
    private String observation;

    public FichePresence() {
    }

    public FichePresence(Long idFiche, Long idFerme, LocalDate dateFiche,
                         String statut, Long transmisPar, Long validePar,
                         String observation) {
        this.idFiche = idFiche;
        this.idFerme = idFerme;
        this.dateFiche = dateFiche;
        this.statut = statut;
        this.transmisPar = transmisPar;
        this.validePar = validePar;
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