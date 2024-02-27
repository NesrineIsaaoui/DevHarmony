/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author oussa
 */
public class Formulaire {
    int id ; 
    String nom_eleve , email , etat ,diplome ; 

    public Formulaire() {
    }

    public Formulaire(String etat,int id) {
        this.etat = etat;
        this.id = id ;
    }

    public Formulaire(int id, String nom_eleve, String email, String etat, String diplome) {
        this.id = id;
        this.nom_eleve = nom_eleve;
        this.email = email;
        this.etat = etat;
        this.diplome = diplome;
    }

    public Formulaire(String nom_eleve, String email, String etat , String diplome) {
        this.nom_eleve = nom_eleve;
        this.email = email;
        this.etat = etat;
        this.diplome=diplome ; 
    }

    public String getDiplome() {
        return diplome;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_eleve() {
        return nom_eleve;
    }

    public void setNom_eleve(String nom_eleve) {
        this.nom_eleve = nom_eleve;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
    
    
}
