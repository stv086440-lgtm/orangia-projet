package com.orangia.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * Expédition d'oranges (table EXPEDITION).
 *
 * <p>Le cycle de vie est décrit par un statut unique (planning, § Membre 5,
 * et architecture, § 6.6) : BROUILLON, EN_ATTENTE_CONFIRMATION, CONFIRMEE,
 * REFUSEE, REVISION_EN_ATTENTE, ANNULATION_EN_ATTENTE, ANNULEE. Les attributs
 * {@code motifRefus}/{@code confirmePar} et {@code motifAnnulation}/
 * {@code annulePar}/{@code dateAnnulation} précisent les décisions.</p>
 *
 * <p>{@code numeroVersion} identifie la version de l'expédition : après une
 * révision, l'ancienne version confirmée doit rester consultable. La
 * persistance de l'historique et les transitions de statut relèvent des
 * couches DAO/Service ; aucune logique de ce type n'est placée dans le Model.</p>
 */
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
    private String statut;
    private String motifRefus;
    private Long confirmePar;
    private String motifAnnulation;
    private Long annulePar;
    private OffsetDateTime dateAnnulation;
    private Long creePar;
    private OffsetDateTime dateCreation;
    private OffsetDateTime dateModification;
    private int numeroVersion;
    private Long idDocumentBonLivraison;
    private String observation;

    public Expedition() {
    }

    public Expedition(Long idExpedition, Long idFerme, Long idClient, String reference,
                      LocalDate dateExpedition, BigDecimal quantiteKg,
                      BigDecimal quantiteFacturee, BigDecimal prixUnitaire,
                      BigDecimal montantTotal, String statut, String motifRefus,
                      Long confirmePar, String motifAnnulation, Long annulePar,
                      OffsetDateTime dateAnnulation, Long creePar,
                      OffsetDateTime dateCreation, OffsetDateTime dateModification,
                      int numeroVersion, Long idDocumentBonLivraison, String observation) {
        this.idExpedition = idExpedition;
        this.idFerme = idFerme;
        this.idClient = idClient;
        this.reference = reference;
        this.dateExpedition = dateExpedition;
        this.quantiteKg = quantiteKg;
        this.quantiteFacturee = quantiteFacturee;
        this.prixUnitaire = prixUnitaire;
        this.montantTotal = montantTotal;
        this.statut = statut;
        this.motifRefus = motifRefus;
        this.confirmePar = confirmePar;
        this.motifAnnulation = motifAnnulation;
        this.annulePar = annulePar;
        this.dateAnnulation = dateAnnulation;
        this.creePar = creePar;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.numeroVersion = numeroVersion;
        this.idDocumentBonLivraison = idDocumentBonLivraison;
        this.observation = observation;
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

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
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

    public String getMotifAnnulation() {
        return motifAnnulation;
    }

    public void setMotifAnnulation(String motifAnnulation) {
        this.motifAnnulation = motifAnnulation;
    }

    public Long getAnnulePar() {
        return annulePar;
    }

    public void setAnnulePar(Long annulePar) {
        this.annulePar = annulePar;
    }

    public OffsetDateTime getDateAnnulation() {
        return dateAnnulation;
    }

    public void setDateAnnulation(OffsetDateTime dateAnnulation) {
        this.dateAnnulation = dateAnnulation;
    }

    public Long getCreePar() {
        return creePar;
    }

    public void setCreePar(Long creePar) {
        this.creePar = creePar;
    }

    public OffsetDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(OffsetDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public OffsetDateTime getDateModification() {
        return dateModification;
    }

    public void setDateModification(OffsetDateTime dateModification) {
        this.dateModification = dateModification;
    }

    public int getNumeroVersion() {
        return numeroVersion;
    }

    public void setNumeroVersion(int numeroVersion) {
        this.numeroVersion = numeroVersion;
    }

    public Long getIdDocumentBonLivraison() {
        return idDocumentBonLivraison;
    }

    public void setIdDocumentBonLivraison(Long idDocumentBonLivraison) {
        this.idDocumentBonLivraison = idDocumentBonLivraison;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
