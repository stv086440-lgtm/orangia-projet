
package com.orangia.model;

import java.time.LocalDateTime;

public class Notification {

    private int idNotification;
    private int idDestinataire;
    private String type;
    private String message;
    private LocalDateTime date;
    private boolean lu;

    public Notification() {
    }

    public Notification(int idNotification, int idDestinataire,
            String type, String message,
            LocalDateTime date, boolean lu) {
        this.idNotification = idNotification;
        this.idDestinataire = idDestinataire;
        this.type = type;
        this.message = message;
        this.date = date;
        this.lu = lu;
    }

    public int getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(int idNotification) {
        this.idNotification = idNotification;
    }

    public int getIdDestinataire() {
        return idDestinataire;
    }

    public void setIdDestinataire(int idDestinataire) {
        this.idDestinataire = idDestinataire;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isLu() {
        return lu;
    }

    public void setLu(boolean lu) {
        this.lu = lu;
    }
}
