package com.orangia.model;

import java.time.OffsetDateTime;

/**
 * Notification destinée à un utilisateur (table NOTIFICATION).
 *
 * <p>Les noms des propriétés reprennent les colonnes SQL
 * {@code type_notification} et {@code date_creation}. La date est un
 * {@link OffsetDateTime}, conforme au type TIMESTAMPTZ de la colonne et à la
 * convention déjà utilisée par {@code Utilisateur} et {@code Ferme}.</p>
 */
public class Notification {

    private Long idNotification;
    private Long idUtilisateur;
    private String titre;
    private String message;
    private String typeNotification;
    private boolean lu;
    private OffsetDateTime dateCreation;

    public Notification() {
    }

    public Notification(Long idNotification, Long idUtilisateur, String titre,
                        String message, String typeNotification, boolean lu,
                        OffsetDateTime dateCreation) {
        this.idNotification = idNotification;
        this.idUtilisateur = idUtilisateur;
        this.titre = titre;
        this.message = message;
        this.typeNotification = typeNotification;
        this.lu = lu;
        this.dateCreation = dateCreation;
    }

    public Long getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(Long idNotification) {
        this.idNotification = idNotification;
    }

    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTypeNotification() {
        return typeNotification;
    }

    public void setTypeNotification(String typeNotification) {
        this.typeNotification = typeNotification;
    }

    public boolean isLu() {
        return lu;
    }

    public void setLu(boolean lu) {
        this.lu = lu;
    }

    public OffsetDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(OffsetDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
}
