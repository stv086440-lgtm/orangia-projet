package com.orangia.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Salaire {

    private Long idSalaire;
    private Long idOuvrier;
    private Long idFerme;
    private Long idTarif;
    private LocalDate periodeDebut;
    private LocalDate periodeFin;
    private BigDecimal montantBase;
    private BigDecimal montantPrime;
    private BigDecimal montantTotal;
    private String detailCalcul;

    public Salaire() {
    }

    public Salaire(Long idSalaire, Long idOuvrier, Long idFerme, Long idTarif,
                   LocalDate periodeDebut, LocalDate periodeFin,
                   BigDecimal montantBase, BigDecimal montantPrime,
                   BigDecimal montantTotal, String detailCalcul) {
        this.idSalaire = idSalaire;
        this.idOuvrier = idOuvrier;
        this.idFerme = idFerme;
        this.idTarif = idTarif;
        this.periodeDebut = periodeDebut;
        this.periodeFin = periodeFin;
        this.montantBase = montantBase;
        this.montantPrime = montantPrime;
        this.montantTotal = montantTotal;
        this.detailCalcul = detailCalcul;
    }

    public Long getIdSalaire() { return idSalaire; }
    public void setIdSalaire(Long idSalaire) { this.idSalaire = idSalaire; }

    public Long getIdOuvrier() { return idOuvrier; }
    public void setIdOuvrier(Long idOuvrier) { this.idOuvrier = idOuvrier; }

    public Long getIdFerme() { return idFerme; }
    public void setIdFerme(Long idFerme) { this.idFerme = idFerme; }

    public Long getIdTarif() { return idTarif; }
    public void setIdTarif(Long idTarif) { this.idTarif = idTarif; }

    public LocalDate getPeriodeDebut() { return periodeDebut; }
    public void setPeriodeDebut(LocalDate periodeDebut) { this.periodeDebut = periodeDebut; }

    public LocalDate getPeriodeFin() { return periodeFin; }
    public void setPeriodeFin(LocalDate periodeFin) { this.periodeFin = periodeFin; }

    public BigDecimal getMontantBase() { return montantBase; }
    public void setMontantBase(BigDecimal montantBase) { this.montantBase = montantBase; }

    public BigDecimal getMontantPrime() { return montantPrime; }
    public void setMontantPrime(BigDecimal montantPrime) { this.montantPrime = montantPrime; }

    public BigDecimal getMontantTotal() { return montantTotal; }
    public void setMontantTotal(BigDecimal montantTotal) { this.montantTotal = montantTotal; }

    public String getDetailCalcul() { return detailCalcul; }
    public void setDetailCalcul(String detailCalcul) { this.detailCalcul = detailCalcul; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Salaire salaire)) return false;
        return Objects.equals(idSalaire, salaire.idSalaire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSalaire);
    }

    @Override
    public String toString() {
        return "Salaire{" +
                "idSalaire=" + idSalaire +
                ", idOuvrier=" + idOuvrier +
                ", idFerme=" + idFerme +
                ", periodeDebut=" + periodeDebut +
                ", periodeFin=" + periodeFin +
                ", montantTotal=" + montantTotal +
                '}';
    }
}