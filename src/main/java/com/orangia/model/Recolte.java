package com.orangia.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Récolte d'une parcelle (table RECOLTE).
 *
 * <p>{@code quantiteKg} correspond à la colonne SQL {@code quantite_kg},
 * conformément à la convention déjà utilisée par {@code Expedition}. Le motif
 * d'annulation est prévu par le planning (§ Membre 5) mais n'a pas encore de
 * colonne dans {@code 001_schema.sql} ; il est conservé et fera l'objet d'une
 * migration dédiée.</p>
 */
public class Recolte {

    private Long idRecolte;
    private Parcelle parcelle;
    private String reference;
    private LocalDate dateRecolte;
    private BigDecimal quantiteKg;
    private String qualite;
    private String statut;
    private String observation;
    private String motifAnnulation;
    private Utilisateur utilisateurCreateur;

    public Recolte() {
    }

    public Recolte(Long idRecolte, Parcelle parcelle, String reference,
                   LocalDate dateRecolte, BigDecimal quantiteKg,
                   String qualite, String statut, String observation,
                   String motifAnnulation, Utilisateur utilisateurCreateur) {
        this.idRecolte = idRecolte;
        this.parcelle = parcelle;
        this.reference = reference;
        this.dateRecolte = dateRecolte;
        this.quantiteKg = quantiteKg;
        this.qualite = qualite;
        this.statut = statut;
        this.observation = observation;
        this.motifAnnulation = motifAnnulation;
        this.utilisateurCreateur = utilisateurCreateur;
    }

    // ---------- Getters ----------

    public Long getIdRecolte() {
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

    public BigDecimal getQuantiteKg() {
        return quantiteKg;
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

    public void setIdRecolte(Long idRecolte) {
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

    public void setQuantiteKg(BigDecimal quantiteKg) {
        this.quantiteKg = quantiteKg;
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