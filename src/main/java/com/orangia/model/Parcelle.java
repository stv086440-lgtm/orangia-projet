package com.orangia.model;

import java.math.BigDecimal;

public class Parcelle {
    private Long idParcelle;
    private Long idFerme;
    private String codeParcelle;
    private String nomParcelle;
    private BigDecimal superficie;
    private String varieteOrange;
    private Integer nombreArbres;
    private Integer anneePlantation;
    private String statut;

    public Parcelle() {
    }

    public Parcelle(Long idParcelle, Long idFerme, String codeParcelle, String nomParcelle,
                    BigDecimal superficie, String varieteOrange, Integer nombreArbres,
                    Integer anneePlantation, String statut) {
        this.idParcelle = idParcelle;
        this.idFerme = idFerme;
        this.codeParcelle = codeParcelle;
        this.nomParcelle = nomParcelle;
        this.superficie = superficie;
        this.varieteOrange = varieteOrange;
        this.nombreArbres = nombreArbres;
        this.anneePlantation = anneePlantation;
        this.statut = statut;
    }

    public Long getIdParcelle() { return idParcelle; }
    public void setIdParcelle(Long idParcelle) { this.idParcelle = idParcelle; }

    public Long getIdFerme() { return idFerme; }
    public void setIdFerme(Long idFerme) { this.idFerme = idFerme; }

    public String getCodeParcelle() { return codeParcelle; }
    public void setCodeParcelle(String codeParcelle) { this.codeParcelle = codeParcelle; }

    public String getNomParcelle() { return nomParcelle; }
    public void setNomParcelle(String nomParcelle) { this.nomParcelle = nomParcelle; }

    public BigDecimal getSuperficie() { return superficie; }
    public void setSuperficie(BigDecimal superficie) { this.superficie = superficie; }

    public String getVarieteOrange() { return varieteOrange; }
    public void setVarieteOrange(String varieteOrange) { this.varieteOrange = varieteOrange; }

    public Integer getNombreArbres() { return nombreArbres; }
    public void setNombreArbres(Integer nombreArbres) { this.nombreArbres = nombreArbres; }

    public Integer getAnneePlantation() { return anneePlantation; }
    public void setAnneePlantation(Integer anneePlantation) { this.anneePlantation = anneePlantation; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    @Override
    public String toString() {
        return nomParcelle + " (" + codeParcelle + ")";
    }
}