
package com.orangia.model;

import java.time.LocalDateTime;

public class Document {

    private int idDocument;
    private String typeDocument;
    private String nom;
    private String chemin;
    private LocalDateTime dateCreation;
    private int idUtilisateurCreateur;

    public Document() {
    }

    public Document(int idDocument, String typeDocument, String nom,
            String chemin, LocalDateTime dateCreation,
            int idUtilisateurCreateur) {
        this.idDocument = idDocument;
        this.typeDocument = typeDocument;
        this.nom = nom;
        this.chemin = chemin;
        this.dateCreation = dateCreation;
        this.idUtilisateurCreateur = idUtilisateurCreateur;
    }

    public int getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(int idDocument) {
        this.idDocument = idDocument;
    }

    public String getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(String typeDocument) {
        this.typeDocument = typeDocument;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getChemin() {
        return chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public int getIdUtilisateurCreateur() {
        return idUtilisateurCreateur;
    }

    public void setIdUtilisateurCreateur(int idUtilisateurCreateur) {
        this.idUtilisateurCreateur = idUtilisateurCreateur;
    }
}
