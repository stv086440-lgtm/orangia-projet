package com.orangia.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Ferme {
    private Long idFerme;
    private String codeFerme;
    private String nomFerme;
    private String localisation;
    private BigDecimal superficie;
    private String statut;
    private OffsetDateTime dateCreation;

    public Ferme() {
    }

    public Ferme(Long idFerme, String codeFerme, String nomFerme, String localisation,
                 BigDecimal superficie, String statut, OffsetDateTime dateCreation) {
        this.idFerme = idFerme;
        this.codeFerme = codeFerme;
        this.nomFerme = nomFerme;
        this.localisation = localisation;
        this.superficie = superficie;
        this.statut = statut;
        this.dateCreation = dateCreation;
    }

    public Long getIdFerme() { return idFerme; }
    public void setIdFerme(Long idFerme) { this.idFerme = idFerme; }

    public String getCodeFerme() { return codeFerme; }
    public void setCodeFerme(String codeFerme) { this.codeFerme = codeFerme; }

    public String getNomFerme() { return nomFerme; }
    public void setNomFerme(String nomFerme) { this.nomFerme = nomFerme; }

    public String getLocalisation() { return localisation; }
    public void setLocalisation(String localisation) { this.localisation = localisation; }

    public BigDecimal getSuperficie() { return superficie; }
    public void setSuperficie(BigDecimal superficie) { this.superficie = superficie; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public OffsetDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(OffsetDateTime dateCreation) { this.dateCreation = dateCreation; }

    @Override
    public String toString() {
        return nomFerme + " (" + codeFerme + ")";
    }
}
