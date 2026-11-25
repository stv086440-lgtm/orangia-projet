package com.orangia.model;

import java.time.OffsetDateTime;

/**
 * Paramètre applicatif du système ORANGIA.
 *
 * <p>Objet métier simple représentant une clé de configuration, sa valeur,
 * sa description et les informations de dernière modification. Le mapping avec
 * la table SQL PARAMETRE_SYSTEME est assuré par le DAO.</p>
 *
 * <p>Décision Sprint 1 : {@code dateModification} est conservé tel quel, avec
 * {@link java.time.OffsetDateTime} comme type (convention TIMESTAMPTZ du
 * projet). La colonne {@code date_modification} manque dans
 * {@code 001_schema.sql} ; son ajout doit faire l'objet d'une migration
 * dédiée, sans modifier la migration existante.</p>
 */
public class ParametreSysteme {

    private Long idParametre;
    private String cle;
    private String valeur;
    private String description;
    private Utilisateur modifiePar;
    private OffsetDateTime dateModification;

    /**
     * Constructeur sans paramètres, utilisé notamment par le mapping JDBC/DAO.
     */
    public ParametreSysteme() {
    }

    /**
     * Constructeur complet.
     *
     * @param idParametre     identifiant du paramètre
     * @param cle             clé unique du paramètre
     * @param valeur          valeur du paramètre
     * @param description     description du paramètre
     * @param modifiePar      utilisateur ayant effectué la modification
     * @param dateModification date et heure de la modification
     */
    public ParametreSysteme(Long idParametre, String cle, String valeur, String description,
            Utilisateur modifiePar, OffsetDateTime dateModification) {
        this.idParametre = idParametre;
        this.cle = cle;
        this.valeur = valeur;
        this.description = description;
        this.modifiePar = modifiePar;
        this.dateModification = dateModification;
    }

    public Long getIdParametre() {
        return idParametre;
    }

    public void setIdParametre(Long idParametre) {
        this.idParametre = idParametre;
    }

    public String getCle() {
        return cle;
    }

    public void setCle(String cle) {
        this.cle = cle;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Utilisateur getModifiePar() {
        return modifiePar;
    }

    public void setModifiePar(Utilisateur modifiePar) {
        this.modifiePar = modifiePar;
    }

    public OffsetDateTime getDateModification() {
        return dateModification;
    }

    public void setDateModification(OffsetDateTime dateModification) {
        this.dateModification = dateModification;
    }
}
