package com.orangia.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Presence {

    private Long idPresence;
    private Long idFiche;
    private Long idOuvrier;
    private LocalDate datePresence;
    private String statutPresence;
    private LocalTime heureArrivee;
    private LocalTime heureDepart;
    private LocalTime heureDebutAppliquee;
    private LocalTime heureFinAppliquee;
    private boolean absenceJustifiee;
    private boolean justificationAcceptee;
    private String observation;

    public Presence() {
    }

    public Presence(Long idPresence, Long idFiche, Long idOuvrier, LocalDate datePresence,
                    String statutPresence, LocalTime heureArrivee, LocalTime heureDepart,
                    LocalTime heureDebutAppliquee, LocalTime heureFinAppliquee,
                    boolean absenceJustifiee, boolean justificationAcceptee, String observation) {
        this.idPresence = idPresence;
        this.idFiche = idFiche;
        this.idOuvrier = idOuvrier;
        this.datePresence = datePresence;
        this.statutPresence = statutPresence;
        this.heureArrivee = heureArrivee;
        this.heureDepart = heureDepart;
        this.heureDebutAppliquee = heureDebutAppliquee;
        this.heureFinAppliquee = heureFinAppliquee;
        this.absenceJustifiee = absenceJustifiee;
        this.justificationAcceptee = justificationAcceptee;
        this.observation = observation;
    }

    public Long getIdPresence() {
        return idPresence;
    }

    public void setIdPresence(Long idPresence) {
        this.idPresence = idPresence;
    }

    public Long getIdFiche() {
        return idFiche;
    }

    public void setIdFiche(Long idFiche) {
        this.idFiche = idFiche;
    }

    public Long getIdOuvrier() {
        return idOuvrier;
    }

    public void setIdOuvrier(Long idOuvrier) {
        this.idOuvrier = idOuvrier;
    }

    public LocalDate getDatePresence() {
        return datePresence;
    }

    public void setDatePresence(LocalDate datePresence) {
        this.datePresence = datePresence;
    }

    public String getStatutPresence() {
        return statutPresence;
    }

    public void setStatutPresence(String statutPresence) {
        this.statutPresence = statutPresence;
    }

    public LocalTime getHeureArrivee() {
        return heureArrivee;
    }

    public void setHeureArrivee(LocalTime heureArrivee) {
        this.heureArrivee = heureArrivee;
    }

    public LocalTime getHeureDepart() {
        return heureDepart;
    }

    public void setHeureDepart(LocalTime heureDepart) {
        this.heureDepart = heureDepart;
    }

    public LocalTime getHeureDebutAppliquee() {
        return heureDebutAppliquee;
    }

    public void setHeureDebutAppliquee(LocalTime heureDebutAppliquee) {
        this.heureDebutAppliquee = heureDebutAppliquee;
    }

    public LocalTime getHeureFinAppliquee() {
        return heureFinAppliquee;
    }

    public void setHeureFinAppliquee(LocalTime heureFinAppliquee) {
        this.heureFinAppliquee = heureFinAppliquee;
    }

    public boolean isAbsenceJustifiee() {
        return absenceJustifiee;
    }

    public void setAbsenceJustifiee(boolean absenceJustifiee) {
        this.absenceJustifiee = absenceJustifiee;
    }

    public boolean isJustificationAcceptee() {
        return justificationAcceptee;
    }

    public void setJustificationAcceptee(boolean justificationAcceptee) {
        this.justificationAcceptee = justificationAcceptee;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Presence presence)) return false;
        return Objects.equals(idPresence, presence.idPresence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPresence);
    }

    @Override
    public String toString() {
        return "Presence{" +
                "idPresence=" + idPresence +
                ", idOuvrier=" + idOuvrier +
                ", datePresence=" + datePresence +
                ", statutPresence='" + statutPresence + '\'' +
                '}';
    }
}