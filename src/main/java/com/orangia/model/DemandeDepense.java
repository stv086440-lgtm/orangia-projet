
package com.orangia.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DemandeDepense {

    private Long idDemande;
    private Long idFerme;
    private String reference;
    private String sujet;
    private String categorie;
    private BigDecimal montantEstime;
    private String description;
    private String justification;
    private LocalDate dateDemande;
    private LocalDate dateDecision;
    private String statut;
    private Long creePar;
    private Long traiteePar;
    private String commentaireComptable;

    public DemandeDepense() {
    }

    public Long getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(Long idDemande) {
        this.idDemande = idDemande;
    }

    public Long getIdFerme() {
        return idFerme;
    }

    public void setIdFerme(Long idFerme) {
        this.idFerme = idFerme;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public BigDecimal getMontantEstime() {
        return montantEstime;
    }

    public void setMontantEstime(BigDecimal montantEstime) {
        this.montantEstime = montantEstime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public LocalDate getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(LocalDate dateDemande) {
        this.dateDemande = dateDemande;
    }

    public LocalDate getDateDecision() {
        return dateDecision;
    }

    public void setDateDecision(LocalDate dateDecision) {
        this.dateDecision = dateDecision;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Long getCreePar() {
        return creePar;
    }

    public void setCreePar(Long creePar) {
        this.creePar = creePar;
    }

    public Long getTraiteePar() {
        return traiteePar;
    }

    public void setTraiteePar(Long traiteePar) {
        this.traiteePar = traiteePar;
    }

    public String getCommentaireComptable() {
        return commentaireComptable;
    }

    public void setCommentaireComptable(String commentaireComptable) {
        this.commentaireComptable = commentaireComptable;
    }
}
