package com.orangia.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class PaiementSalaire {

    private Long idPaiementSalaire;
    private Long idSalaire;
    private BigDecimal montantPaye;
    private LocalDate datePaiement;
    private String modePaiement;
    private String reference;
    private Long enregistrePar;

    public PaiementSalaire() {
    }

    public PaiementSalaire(Long idPaiementSalaire, Long idSalaire, BigDecimal montantPaye,
                           LocalDate datePaiement, String modePaiement, String reference,
                           Long enregistrePar) {
        this.idPaiementSalaire = idPaiementSalaire;
        this.idSalaire = idSalaire;
        this.montantPaye = montantPaye;
        this.datePaiement = datePaiement;
        this.modePaiement = modePaiement;
        this.reference = reference;
        this.enregistrePar = enregistrePar;
    }

    public Long getIdPaiementSalaire() {
        return idPaiementSalaire;
    }

    public void setIdPaiementSalaire(Long idPaiementSalaire) {
        this.idPaiementSalaire = idPaiementSalaire;
    }

    public Long getIdSalaire() {
        return idSalaire;
    }

    public void setIdSalaire(Long idSalaire) {
        this.idSalaire = idSalaire;
    }

    public BigDecimal getMontantPaye() {
        return montantPaye;
    }

    public void setMontantPaye(BigDecimal montantPaye) {
        this.montantPaye = montantPaye;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Long getEnregistrePar() {
        return enregistrePar;
    }

    public void setEnregistrePar(Long enregistrePar) {
        this.enregistrePar = enregistrePar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaiementSalaire that)) return false;
        return Objects.equals(idPaiementSalaire, that.idPaiementSalaire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPaiementSalaire);
    }

    @Override
    public String toString() {
        return "PaiementSalaire{" +
                "idPaiementSalaire=" + idPaiementSalaire +
                ", idSalaire=" + idSalaire +
                ", montantPaye=" + montantPaye +
                ", datePaiement=" + datePaiement +
                ", reference='" + reference + '\'' +
                '}';
    }
}