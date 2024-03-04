package models;

import java.util.Date;

public class Commentaire {
    private int id;
    private String contenu;
    private Date dateCommentaire;
    private int utilisateurId;
    private int publicationId;
    private String userName;
    private String titrePublication;
    private String role;

    public Commentaire(int id, String contenu, Date dateCommentaire, int utilisateurId, int publicationId) {
        this.id = id;
        this.contenu = contenu;
        this.dateCommentaire = dateCommentaire;
        this.utilisateurId = utilisateurId;
        this.publicationId = publicationId;
    }

    public Commentaire(String contenu, Date dateCommentaire, int utilisateurId, int publicationId) {
        this.contenu = contenu;
        this.dateCommentaire = dateCommentaire;
        this.utilisateurId = utilisateurId;
        this.publicationId = publicationId;
    }

    public Commentaire(String contenu, Date dateCommentaire, int utilisateurId) {
        this.contenu = contenu;
        this.dateCommentaire = dateCommentaire;
        this.utilisateurId = utilisateurId;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "id=" + id +
                ", contenu='" + contenu + '\'' +
                ", dateCommentaire=" + dateCommentaire +
                ", utilisateurId=" + utilisateurId +
                ", publicationId=" + publicationId +
                ", userName='" + userName + '\'' +
                ", titrePublication='" + titrePublication + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public Commentaire(int id, String contenu, Date dateCommentaire, int utilisateurId, int publicationId, String userName, String titrePublication) {
        this.id = id;
        this.contenu = contenu;
        this.dateCommentaire = dateCommentaire;
        this.utilisateurId = utilisateurId;
        this.publicationId = publicationId;
        this.userName = userName;
        this.titrePublication = titrePublication;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Commentaire() {
    }

    public Commentaire(int id, String contenu, Date dateCommentaire, int utilisateurId, int publicationId, String userName, String titrePublication, String role) {
        this.id = id;
        this.contenu = contenu;
        this.dateCommentaire = dateCommentaire;
        this.utilisateurId = utilisateurId;
        this.publicationId = publicationId;
        this.userName = userName;
        this.titrePublication = titrePublication;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitrePublication() {
        return titrePublication;
    }

    public void setTitrePublication(String titrePublication) {
        this.titrePublication = titrePublication;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDateCommentaire() {
        return dateCommentaire;
    }

    public void setDateCommentaire(Date dateCommentaire) {
        this.dateCommentaire = dateCommentaire;
    }

    public int getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public int getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(int publicationId) {
        this.publicationId = publicationId;
    }
}
