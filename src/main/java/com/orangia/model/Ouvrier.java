package com.orangia.model;

import java.time.LocalDate;
import java.util.Objects;

public class Ouvrier {

    private Long idOuvrier;
    private String codeOuvrier;
    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;
    private LocalDate dateEmbauche;
    private String categorieOuvrier;
    private String statut;

    public Ouvrier() {
    }

    public Ouvrier(Long idOuvrier, String codeOuvrier, String nom, String prenom,
                   String telephone, String adresse, LocalDate dateEmbauche,
                   String categorieOuvrier, String statut) {
        this.idOuvrier = idOuvrier;
        this.codeOuvrier = codeOuvrier;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.dateEmbauche = dateEmbauche;
        this.categorieOuvrier = categorieOuvrier;
        this.statut = statut;
    }

    public Long getIdOuvrier() {
        return idOuvrier;
    }

    public void setIdOuvrier(Long idOuvrier) {
        this.idOuvrier = idOuvrier;
    }

    public String getCodeOuvrier() {
        return codeOuvrier;
    }

    public void setCodeOuvrier(String codeOuvrier) {
        this.codeOuvrier = codeOuvrier;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public LocalDate getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(LocalDate dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    public String getCategorieOuvrier() {
        return categorieOuvrier;
    }

    public void setCategorieOuvrier(String categorieOuvrier) {
        this.categorieOuvrier = categorieOuvrier;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ouvrier ouvrier)) return false;
        return Objects.equals(idOuvrier, ouvrier.idOuvrier)
                && Objects.equals(codeOuvrier, ouvrier.codeOuvrier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOuvrier, codeOuvrier);
    }

    @Override
    public String toString() {
        return "Ouvrier{" +
                "idOuvrier=" + idOuvrier +
                ", codeOuvrier='" + codeOuvrier + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", categorieOuvrier='" + categorieOuvrier + '\'' +
                ", statut='" + statut + '\'' +
                '}';
    }
}