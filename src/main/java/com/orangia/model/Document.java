package com.orangia.model;

import java.time.LocalDateTime;

/**
 * Métadonnées d'un document stocké (table DOCUMENT).
 *
 * <p>{@code typeOperation}/{@code idOperation} désignent l'entité métier
 * associée (expédition, fiche de présence, demande de correction, demande de
 * dépense ou dépense). {@code typeDocument} conserve le type propre au document
 * prévu par le planning. Les champs {@code dateCreation}, {@code typeDocument}
 * et {@code extension}/{@code typeMime}/{@code tailleOctets} sont conservés :
 * le schéma actuel ne contient pas encore tous ces champs et fera l'objet
 * d'une migration séparée.</p>
 */
public class Document {

    private Long idDocument;
    private String typeDocument;
    private String nomFichier;
    private String cheminFichier;
    private String extension;
    private String typeMime;
    private Long tailleOctets;
    private String typeOperation;
    private Long idOperation;
    private Long ajoutePar;
    private LocalDateTime dateCreation;

    public Document() {
    }

    public Document(Long idDocument, String typeDocument, String nomFichier,
                    String cheminFichier, String extension, String typeMime,
                    Long tailleOctets, String typeOperation, Long idOperation,
                    Long ajoutePar, LocalDateTime dateCreation) {
        this.idDocument = idDocument;
        this.typeDocument = typeDocument;
        this.nomFichier = nomFichier;
        this.cheminFichier = cheminFichier;
        this.extension = extension;
        this.typeMime = typeMime;
        this.tailleOctets = tailleOctets;
        this.typeOperation = typeOperation;
        this.idOperation = idOperation;
        this.ajoutePar = ajoutePar;
        this.dateCreation = dateCreation;
    }

    public Long getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(Long idDocument) {
        this.idDocument = idDocument;
    }

    public String getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(String typeDocument) {
        this.typeDocument = typeDocument;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public String getCheminFichier() {
        return cheminFichier;
    }

    public void setCheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getTypeMime() {
        return typeMime;
    }

    public void setTypeMime(String typeMime) {
        this.typeMime = typeMime;
    }

    public Long getTailleOctets() {
        return tailleOctets;
    }

    public void setTailleOctets(Long tailleOctets) {
        this.tailleOctets = tailleOctets;
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
    }

    public Long getIdOperation() {
        return idOperation;
    }

    public void setIdOperation(Long idOperation) {
        this.idOperation = idOperation;
    }

    public Long getAjoutePar() {
        return ajoutePar;
    }

    public void setAjoutePar(Long ajoutePar) {
        this.ajoutePar = ajoutePar;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
}
