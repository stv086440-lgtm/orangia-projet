package com.orangia.model;

import java.time.LocalDate;
import java.util.Objects;

public class AffectationOuvrier {

    private Long idAffectation;
    private Long idOuvrier;
    private Long idFerme;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String motif;
    private Long creePar;

    public AffectationOuvrier() {
    }

    public AffectationOuvrier(Long idAffectation, Long idOuvrier, Long idFerme,
                              LocalDate dateDebut, LocalDate dateFin,
                              String motif, Long creePar) {
        this.idAffectation = idAffectation;
        this.idOuvrier = idOuvrier;
        this.idFerme = idFerme;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.motif = motif;
        this.creePar = creePar;
    }

    public Long getIdAffectation() { return idAffectation; }
    public void setIdAffectation(Long idAffectation) { this.idAffectation = idAffectation; }

    public Long getIdOuvrier() { return idOuvrier; }
    public void setIdOuvrier(Long idOuvrier) { this.idOuvrier = idOuvrier; }

    public Long getIdFerme() { return idFerme; }
    public void setIdFerme(Long idFerme) { this.idFerme = idFerme; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }

    public Long getCreePar() { return creePar; }
    public void setCreePar(Long creePar) { this.creePar = creePar; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AffectationOuvrier that)) return false;
        return Objects.equals(idAffectation, that.idAffectation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAffectation);
    }

    @Override
    public String toString() {
        return "AffectationOuvrier{" +
                "idAffectation=" + idAffectation +
                ", idOuvrier=" + idOuvrier +
                ", idFerme=" + idFerme +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", motif='" + motif + '\'' +
                ", creePar=" + creePar +
                '}';
    }
}