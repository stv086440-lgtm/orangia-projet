
package com.orangia.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Expedition {

    private Long idExpedition;
    private Long idFerme;
    private Long idClient;
    private String reference;
    private LocalDate dateExpedition;
    private BigDecimal quantiteKg;
    private BigDecimal quantiteFacturee;
    private BigDecimal prixUnitaire;
    private BigDecimal montantTotal;
    private String statutExpedition;
    private String statutConfirmation;
    private String motifRefus;
    private Long confirmePar;
    private Long creePar;
    private String observation;

    public Expedition() {
    }

    public Long getIdExpedition() {
        return idExpedition;
    }

    public void setIdExpedition(Long idExpedition) {
        this.idExpedition = idExpedition;
    }

    public Long getIdFerme() {
        return idFerme;
    }

    public void setIdFerme(Long idFerme) {
        this.idFerme = idFerme;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LocalDate getDateExpedition() {
        return dateExpedition;
    }

    public void setDateExpedition(LocalDate dateExpedition) {
        this.dateExpedition = dateExpedition;
    }

    public BigDecimal getQuantiteKg() {
        return quantiteKg;
    }

    public void setQuantiteKg(BigDecimal quantiteKg) {
        this.quantiteKg = quantiteKg;
    }

    public BigDecimal getQuantiteFacturee() {
        return quantiteFacturee;
    }

    public void setQuantiteFacturee(BigDecimal quantiteFacturee) {
        this.quantiteFacturee = quantiteFacturee;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public BigDecimal getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(BigDecimal montantTotal) {
        this.montantTotal = montantTotal;
    }

    public String getStatutExpedition() {
        return statutExpedition;
    }

    public void setStatutExpedition(String statutExpedition) {
        this.statutExpedition = statutExpedition;
    }

    public String getStatutConfirmation() {
        return statutConfirmation;
    }

    public void setStatutConfirmation(String statutConfirmation) {
        this.statutConfirmation = statutConfirmation;
    }

    public String getMotifRefus() {
        return motifRefus;
    }

    public void setMotifRefus(String motifRefus) {
        this.motifRefus = motifRefus;
    }

    public Long getConfirmePar() {
        return confirmePar;
    }

    public void setConfirmePar(Long confirmePar) {
        this.confirmePar = confirmePar;
    }

    public Long getCreePar() {
        return creePar;
    }

    public void setCreePar(Long creePar) {
        this.creePar = creePar;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
