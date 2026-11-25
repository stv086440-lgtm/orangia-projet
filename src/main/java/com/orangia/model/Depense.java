package com.orangia.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Dépense réellement effectuée (table DEPENSE).
 *
 * <p>Les champs sont alignés sur {@code 001_schema.sql} et sur le planning
 * (Membre 6). {@code idDemande} est facultatif : une dépense réelle peut, sans
 * y être obligée, provenir d'une demande acceptée. Le justificatif prévu par le
 * planning et l'architecture est conservé ici comme référence de stockage ; sa
 * colonne manque encore dans le schéma SQL actuel.</p>
 */
public class Depense {

    private Long idDepense;
    private Long idFerme;
    private Long idDemande;
    private String reference;
    private String categorie;
    private String description;
    private BigDecimal montant;
    private LocalDate dateDepense;
    private String modePaiement;
    private String statut;
    private Long enregistrePar;
    private String justificatif;

    public Depense() {
    }

    public Depense(Long idDepense, Long idFerme, Long idDemande, String reference,
                   String categorie, String description, BigDecimal montant,
                   LocalDate dateDepense, String modePaiement, String statut,
                   Long enregistrePar, String justificatif) {
        this.idDepense = idDepense;
        this.idFerme = idFerme;
        this.idDemande = idDemande;
        this.reference = reference;
        this.categorie = categorie;
        this.description = description;
        this.montant = montant;
        this.dateDepense = dateDepense;
        this.modePaiement = modePaiement;
        this.statut = statut;
        this.enregistrePar = enregistrePar;
        this.justificatif = justificatif;
    }

    public Long getIdDepense() {
        return idDepense;
    }

    public void setIdDepense(Long idDepense) {
        this.idDepense = idDepense;
    }

    public Long getIdFerme() {
        return idFerme;
    }

    public void setIdFerme(Long idFerme) {
        this.idFerme = idFerme;
    }

    public Long getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(Long idDemande) {
        this.idDemande = idDemande;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public LocalDate getDateDepense() {
        return dateDepense;
    }

    public void setDateDepense(LocalDate dateDepense) {
        this.dateDepense = dateDepense;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Long getEnregistrePar() {
        return enregistrePar;
    }

    public void setEnregistrePar(Long enregistrePar) {
        this.enregistrePar = enregistrePar;
    }

    public String getJustificatif() {
        return justificatif;
    }

    public void setJustificatif(String justificatif) {
        this.justificatif = justificatif;
    }
}
