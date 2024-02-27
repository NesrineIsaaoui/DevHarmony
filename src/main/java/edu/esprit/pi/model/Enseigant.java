package edu.esprit.pi.model;

public class Enseigant extends User {
    public Enseigant(int id, String nom, String prenom, String role, String email, String pwd) {
        super(id,nom, prenom, role, email, pwd);
    }
    public Enseigant(String nom, String prenom, String role, String email, String pwd) {
        super(nom, prenom, role, email, pwd);
    }
}
