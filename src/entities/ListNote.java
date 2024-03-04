/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 */
public class ListNote {
    private int idEtudiant ;
    private String nom ;
    private int noteObtnue ;
    private String sujet ;
    private String formation ;

    public ListNote(String nom, int noteObtnue, String sujet, String formation) {
        this.nom = nom;
        this.noteObtnue = noteObtnue;
        this.sujet = sujet;
        this.formation = formation;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNoteObtnue() {
        return noteObtnue;
    }

    public void setNoteObtnue(int noteObtnue) {
        this.noteObtnue = noteObtnue;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public int getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(int idEtudiant) {
        this.idEtudiant = idEtudiant;
    }
    
    
}
