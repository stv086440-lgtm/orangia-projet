package com.orangia.model;

import java.util.Objects;

public class DemandeCorrection {

    private Long idDemandeCorrection;
    private Long idPresence;
    private Long demandePar;
    private String motif;
    private String description;
    private Long documentId;
    private String statut;
    private Long traiteePar;
    private String commentaireRh;

    public DemandeCorrection() {
    }

    public DemandeCorrection(Long idDemandeCorrection, Long idPresence, Long demandePar,
                             String motif, String description, Long documentId,
                             String statut, Long traiteePar, String commentaireRh) {
        this.idDemandeCorrection = idDemandeCorrection;
        this.idPresence = idPresence;
        this.demandePar = demandePar;
        this.motif = motif;
        this.description = description;
        this.documentId = documentId;
        this.statut = statut;
        this.traiteePar = traiteePar;
        this.commentaireRh = commentaireRh;
    }

    public Long getIdDemandeCorrection() { return idDemandeCorrection; }
    public void setIdDemandeCorrection(Long idDemandeCorrection) { this.idDemandeCorrection = idDemandeCorrection; }

    public Long getIdPresence() { return idPresence; }
    public void setIdPresence(Long idPresence) { this.idPresence = idPresence; }

    public Long getDemandePar() { return demandePar; }
    public void setDemandePar(Long demandePar) { this.demandePar = demandePar; }

    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getDocumentId() { return documentId; }
    public void setDocumentId(Long documentId) { this.documentId = documentId; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public Long getTraiteePar() { return traiteePar; }
    public void setTraiteePar(Long traiteePar) { this.traiteePar = traiteePar; }

    public String getCommentaireRh() { return commentaireRh; }
    public void setCommentaireRh(String commentaireRh) { this.commentaireRh = commentaireRh; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DemandeCorrection that)) return false;
        return Objects.equals(idDemandeCorrection, that.idDemandeCorrection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDemandeCorrection);
    }

    @Override
    public String toString() {
        return "DemandeCorrection{" +
                "idDemandeCorrection=" + idDemandeCorrection +
                ", idPresence=" + idPresence +
                ", demandePar=" + demandePar +
                ", motif='" + motif + '\'' +
                ", statut='" + statut + '\'' +
                ", traiteePar=" + traiteePar +
                '}';
    }
}