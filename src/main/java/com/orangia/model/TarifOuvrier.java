package com.orangia.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class TarifOuvrier {

    private Long idTarif;
    private Long idOuvrier;
    private String modeRemuneration;
    private BigDecimal montant;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    public TarifOuvrier() {
    }

    public TarifOuvrier(Long idTarif, Long idOuvrier, String modeRemuneration,
                        BigDecimal montant, LocalDate dateDebut, LocalDate dateFin) {
        this.idTarif = idTarif;
        this.idOuvrier = idOuvrier;
        this.modeRemuneration = modeRemuneration;
        this.montant = montant;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Long getIdTarif() { return idTarif; }
    public void setIdTarif(Long idTarif) { this.idTarif = idTarif; }

    public Long getIdOuvrier() { return idOuvrier; }
    public void setIdOuvrier(Long idOuvrier) { this.idOuvrier = idOuvrier; }

    public String getModeRemuneration() { return modeRemuneration; }
    public void setModeRemuneration(String modeRemuneration) { this.modeRemuneration = modeRemuneration; }

    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TarifOuvrier that)) return false;
        return Objects.equals(idTarif, that.idTarif);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTarif);
    }

    @Override
    public String toString() {
        return "TarifOuvrier{" +
                "idTarif=" + idTarif +
                ", idOuvrier=" + idOuvrier +
                ", modeRemuneration='" + modeRemuneration + '\'' +
                ", montant=" + montant +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }
}