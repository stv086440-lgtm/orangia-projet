package com.orangia.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Recolte {

    private long idRecolte;
    private Parcelle parcelle;
    private String reference;
    private LocalDate dateRecolte;
    private BigDecimal quantiteRecoltee;
    private String qualite;
    private String statut;
    private String observation;
    private String motifAnnulation;
    private Utilisateur utilisateurCreateur;

    public Recolte() {
    }

    public Recolte(long idRecolte, Parcelle parcelle, String reference,
                   LocalDate dateRecolte, BigDecimal quantiteRecoltee,
                   String qualite, String statut, String observation,
                   String motifAnnulation, Utilisateur utilisateurCreateur) {
        this.idRecolte = idRecolte;
        this.parcelle = parcelle;
        this.reference = reference;
        this.dateRecolte = dateRecolte;
        this.quantiteRecoltee = quantiteRecoltee;
        this.qualite = qualite;
        this.statut = statut;
        this.observation = observation;
        this.motifAnnulation = motifAnnulation;
        this.utilisateurCreateur = utilisateurCreateur;
    }

    // ---------- Getters ----------

    public long getIdRecolte() {
        return idRecolte;
    }

    public Parcelle getParcelle() {
        return parcelle;
    }

    public String getReference() {
        return reference;
    }

    public LocalDate getDateRecolte() {
        return dateRecolte;
    }

    public BigDecimal getQuantiteRecoltee() {
        return quantiteRecoltee;
    }

    public String getQualite() {
        return qualite;
    }

    public String getStatut() {
        return statut;
    }

    public String getObservation() {
        return observation;
    }

    public String getMotifAnnulation() {
        return motifAnnulation;
    }

    public Utilisateur getUtilisateurCreateur() {
        return utilisateurCreateur;
    }

    // ---------- Setters ----------

    public void setIdRecolte(long idRecolte) {
        this.idRecolte = idRecolte;
    }

    public void setParcelle(Parcelle parcelle) {
        this.parcelle = parcelle;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setDateRecolte(LocalDate dateRecolte) {
        this.dateRecolte = dateRecolte;
    }

    public void setQuantiteRecoltee(BigDecimal quantiteRecoltee) {
        this.quantiteRecoltee = quantiteRecoltee;
    }

    public void setQualite(String qualite) {
        this.qualite = qualite;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public void setMotifAnnulation(String motifAnnulation) {
        this.motifAnnulation = motifAnnulation;
    }

    public void setUtilisateurCreateur(Utilisateur utilisateurCreateur) {
        this.utilisateurCreateur = utilisateurCreateur;
    }
}