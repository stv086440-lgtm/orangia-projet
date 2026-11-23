package com.orangia.model;

import java.time.OffsetDateTime;

/**
 * Trace d'une action importante (table JOURNAL_ACTION).
 * Champs alignés sur planning.md : utilisateur, action, module, entité,
 * identifiant de l'entité, date, anciennes et nouvelles valeurs,
 * résultat, motif.
 * Aucune valeur de mot de passe ou de code alphanumérique ne doit y figurer.
 */
public class JournalAction {

    private Long idAction;
    private Utilisateur utilisateur;
    private String action;
    private String module;
    private String typeOperation;
    private Long idOperation;
    private Long idFerme;
    private OffsetDateTime dateAction;
    private String description;
    private String anciennesValeurs;
    private String nouvellesValeurs;
    private String resultat;   // SUCCES ou ECHEC
    private String motif;

    public JournalAction() {
    }

    public JournalAction(Long idAction, Utilisateur utilisateur, String action, String module,
                         String typeOperation, Long idOperation, Long idFerme,
                         OffsetDateTime dateAction, String description,
                         String anciennesValeurs, String nouvellesValeurs,
                         String resultat, String motif) {
        this.idAction = idAction;
        this.utilisateur = utilisateur;
        this.action = action;
        this.module = module;
        this.typeOperation = typeOperation;
        this.idOperation = idOperation;
        this.idFerme = idFerme;
        this.dateAction = dateAction;
        this.description = description;
        this.anciennesValeurs = anciennesValeurs;
        this.nouvellesValeurs = nouvellesValeurs;
        this.resultat = resultat;
        this.motif = motif;
    }

    public Long getIdAction() { return idAction; }
    public void setIdAction(Long idAction) { this.idAction = idAction; }

    public Utilisateur getUtilisateur() { return utilisateur; }
    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getModule() { return module; }
    public void setModule(String module) { this.module = module; }

    public String getTypeOperation() { return typeOperation; }
    public void setTypeOperation(String typeOperation) { this.typeOperation = typeOperation; }

    public Long getIdOperation() { return idOperation; }
    public void setIdOperation(Long idOperation) { this.idOperation = idOperation; }

    public Long getIdFerme() { return idFerme; }
    public void setIdFerme(Long idFerme) { this.idFerme = idFerme; }

    public OffsetDateTime getDateAction() { return dateAction; }
    public void setDateAction(OffsetDateTime dateAction) { this.dateAction = dateAction; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAnciennesValeurs() { return anciennesValeurs; }
    public void setAnciennesValeurs(String anciennesValeurs) { this.anciennesValeurs = anciennesValeurs; }

    public String getNouvellesValeurs() { return nouvellesValeurs; }
    public void setNouvellesValeurs(String nouvellesValeurs) { this.nouvellesValeurs = nouvellesValeurs; }

    public String getResultat() { return resultat; }
    public void setResultat(String resultat) { this.resultat = resultat; }

    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }
}