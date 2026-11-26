
package com.orangia.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Depense {

    // Attributs
    private int idDepense;
    private String categorie;
    private int idFerme;
    private BigDecimal montantReel;
    private LocalDate date;
    private String description;
    private String justificatif;
    private int idUtilisateurCreateur;

    // Constructeur vide
    public Depense() {
    }

    // Constructeur complet
    public Depense(int idDepense, String categorie, int idFerme,
                   BigDecimal montantReel, LocalDate date,
                   String description, String justificatif,
                   int idUtilisateurCreateur) {

        this.idDepense = idDepense;
        this.categorie = categorie;
        this.idFerme = idFerme;
        this.montantReel = montantReel;
        this.date = date;
        this.description = description;
        this.justificatif = justificatif;
        this.idUtilisateurCreateur = idUtilisateurCreateur;
    }

    // Getters et Setters

    public int getIdDepense() {
        return idDepense;
    }

    public void setIdDepense(int idDepense) {
        this.idDepense = idDepense;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public int getIdFerme() {
        return idFerme;
    }

    public void setIdFerme(int idFerme) {
        this.idFerme = idFerme;
    }

    public BigDecimal getMontantReel() {
        return montantReel;
    }

    public void setMontantReel(BigDecimal montantReel) {
        this.montantReel = montantReel;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJustificatif() {
        return justificatif;
    }

    public void setJustificatif(String justificatif) {
        this.justificatif = justificatif;
    }

    public int getIdUtilisateurCreateur() {
        return idUtilisateurCreateur;
    }

    public void setIdUtilisateurCreateur(int idUtilisateurCreateur) {
        this.idUtilisateurCreateur = idUtilisateurCreateur;
    }
}
