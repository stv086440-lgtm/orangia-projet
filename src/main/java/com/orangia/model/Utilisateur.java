package com.orangia.model;

import java.time.OffsetDateTime;

//class reprenste chaque utlisateur d'orangia 
public class Utilisateur {

    private Long idUtilisateur;
    private Role role;
    private Long idFerme;
    private String identifiant;
    private String motDePasseHash;
    private String codeAccesHash;
    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private String statut;
    private OffsetDateTime dateCreation;
    private OffsetDateTime derniereConnexion;

    /**
     * Constructeur sans arguments, utilisé notamment par le mapping JDBC/DAO.
     */
    public Utilisateur() {
    }

    /**
     * Constructeur complet.
     *
     * @param idUtilisateur     identifiant généré par la base, null avant persistance
     * @param role              rôle prédéfini du compte
     * @param idFerme           ferme associée, null pour les rôles à accès global
     * @param identifiant       identifiant unique de connexion
     * @param motDePasseHash    hash du mot de passe, jamais le mot de passe en clair
     * @param codeAccesHash     hash du code alphanumérique Administrateur, sinon null
     * @param nom               nom de l'utilisateur
     * @param prenom            prénom de l'utilisateur
     * @param telephone         numéro de téléphone, nullable
     * @param email             adresse électronique, nullable
     * @param statut            statut du compte
     * @param dateCreation      date de création, générée par la base si absente
     * @param derniereConnexion date de dernière connexion, nullable
     */
    public Utilisateur(Long idUtilisateur, Role role, Long idFerme, String identifiant,
            String motDePasseHash, String codeAccesHash, String nom, String prenom,
            String telephone, String email, String statut, OffsetDateTime dateCreation,
            OffsetDateTime derniereConnexion) {
        this.idUtilisateur = idUtilisateur;
        this.role = role;
        this.idFerme = idFerme;
        this.identifiant = identifiant;
        this.motDePasseHash = motDePasseHash;
        this.codeAccesHash = codeAccesHash;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.derniereConnexion = derniereConnexion;
    }

    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getIdFerme() {
        return idFerme;
    }

    public void setIdFerme(Long idFerme) {
        this.idFerme = idFerme;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getMotDePasseHash() {
        return motDePasseHash;
    }

    public void setMotDePasseHash(String motDePasseHash) {
        this.motDePasseHash = motDePasseHash;
    }

    public String getCodeAccesHash() {
        return codeAccesHash;
    }

    public void setCodeAccesHash(String codeAccesHash) {
        this.codeAccesHash = codeAccesHash;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public OffsetDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(OffsetDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public OffsetDateTime getDerniereConnexion() {
        return derniereConnexion;
    }

    public void setDerniereConnexion(OffsetDateTime derniereConnexion) {
        this.derniereConnexion = derniereConnexion;
    }
}
