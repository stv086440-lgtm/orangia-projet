
package com.orangia.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaiementExpedition {

    private Long idPaiement;
    private Long idExpedition;
    private BigDecimal montantPaye;
    private LocalDate datePaiement;
    private String modePaiement;
    private String reference;
    private Long enregistrePar;
    private String statutPaiement;

    public PaiementExpedition() {
    }

    public Long getIdPaiement() {
        return idPaiement;
    }

    public void setIdPaiement(Long idPaiement) {
        this.idPaiement = idPaiement;
    }

    public Long getIdExpedition() {
        return idExpedition;
    }

    public void setIdExpedition(Long idExpedition) {
        this.idExpedition = idExpedition;
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

    public String getStatutPaiement() {
        return statutPaiement;
    }

    public void setStatutPaiement(String statutPaiement) {
        this.statutPaiement = statutPaiement;
    }
}
