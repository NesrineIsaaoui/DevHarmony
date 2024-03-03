package pi.blog.models;

import java.util.Date;

public class Publication {
    private int id;
    private String titre;
    private String contenu;
    private Date datePublication;
    private int utilisateurID;
    private String userName;
private  String role;
    public Publication(int id, String titre, String contenu, Date datePublication, int utilisateurID, String userName) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.datePublication = datePublication;
        this.utilisateurID = utilisateurID;
        this.userName = userName;
    }

    public Publication(int id, String titre, String contenu, Date datePublication, int utilisateurID) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.datePublication = datePublication;
        this.utilisateurID = utilisateurID;
    }
    public Publication(  String titre, String contenu, Date datePublication, int utilisateurID) {

        this.titre = titre;
        this.contenu = contenu;
        this.datePublication = datePublication;
        this.utilisateurID = utilisateurID;
    }

    public Publication(String titre, String contenu, Date datePublication) {
        this.titre = titre;
        this.contenu = contenu;
        this.datePublication = datePublication;
    }

    public Publication(String titre, String contenu, Date datePublication, int utilisateurID, String userName) {
        this.titre = titre;
        this.contenu = contenu;
        this.datePublication = datePublication;
        this.utilisateurID = utilisateurID;
        this.userName = userName;
    }

    public Publication(int id,String titre, String contenu, Date datePublication, int utilisateurID, String userName, String role) {
         this.id=id;
        this.titre = titre;
        this.contenu = contenu;
        this.datePublication = datePublication;
        this.utilisateurID = utilisateurID;
        this.userName = userName;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(Date datePublication) {
        this.datePublication = datePublication;
    }

    public int getUtilisateurID() {
        return utilisateurID;
    }

    public void setUtilisateurID(int utilisateurID) {
        this.utilisateurID = utilisateurID;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", contenu='" + contenu + '\'' +
                ", datePublication=" + datePublication +
                ", utilisateurID=" + utilisateurID +
                ", userName='" + userName + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
