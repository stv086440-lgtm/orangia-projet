
package com.orangia.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Opération financière liée à une expédition (table PAIEMENT_EXPEDITION).
 *
 * <p>{@code typeOperation} distingue PAIEMENT, REMBOURSEMENT et
 * ANNULATION_FINANCIERE (planning, § Membre 6 ; architecture, § 6.7). Les
 * paiements historiques ne sont jamais modifiés ni supprimés pour corriger une
 * erreur : une opération distincte est enregistrée.</p>
 */
public class PaiementExpedition {

    private Long idPaiement;
    private Long idExpedition;
    private String typeOperation;
    private BigDecimal montantPaye;
    private LocalDate datePaiement;
    private String modePaiement;
    private String reference;
    private Long enregistrePar;

    public PaiementExpedition() {
    }

    public PaiementExpedition(Long idPaiement, Long idExpedition, String typeOperation,
                              BigDecimal montantPaye, LocalDate datePaiement,
                              String modePaiement, String reference, Long enregistrePar) {
        this.idPaiement = idPaiement;
        this.idExpedition = idExpedition;
        this.typeOperation = typeOperation;
        this.montantPaye = montantPaye;
        this.datePaiement = datePaiement;
        this.modePaiement = modePaiement;
        this.reference = reference;
        this.enregistrePar = enregistrePar;
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

    public String getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
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
}
