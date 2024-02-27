package edu.esprit.pi.model;

public class Parent extends User {

    public Parent(int id, String nom, String prenom, String role, String email, String pwd) {
        super(id,nom, prenom, role, email, pwd);
    }
    public Parent(String nom, String prenom, String role, String email, String pwd) {
        super(nom, prenom, role, email, pwd);
    }


}
