/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Objects;

/**
 *
 */
public class Question {
    private int id ;
    private String questionPosee ;
    private String reponseCorrecte ;
    private String reponseFausse1 ;
    private String reponseFausse2 ;
    private String reponseFausse3 ;
    private int note ;
    private String type ;
    
    public Question(){
        
    }

    public Question(String questionPosee, String reponseCorrecte, String reponseFausse1, String reponseFausse2, String reponseFausse3, int note) {
        this.questionPosee = questionPosee;
        this.reponseCorrecte = reponseCorrecte;
        this.reponseFausse1 = reponseFausse1;
        this.reponseFausse2 = reponseFausse2;
        this.reponseFausse3 = reponseFausse3;
        this.note = note;
    }
    
     public Question(int id,String questionPosee, String reponseCorrecte, String reponseFausse1, String reponseFausse2, String reponseFausse3, int note) {
        this(questionPosee, reponseCorrecte, reponseFausse1, reponseFausse2, reponseFausse3, note) ;
        this.id = id ;
    }

    public int getId() {
        return id;
    }

    public String getQuestionPosee() {
        return questionPosee;
    }

    public String getReponseCorrecte() {
        return reponseCorrecte;
    }

    public String getReponseFausse1() {
        return reponseFausse1;
    }

    public String getReponseFausse2() {
        return reponseFausse2;
    }

    public String getReponseFausse3() {
        return reponseFausse3;
    }

    public int getNote() {
        return note;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestionPosee(String questionPosee) {
        this.questionPosee = questionPosee;
    }

    public void setReponseCorrecte(String reponseCorrecte) {
        this.reponseCorrecte = reponseCorrecte;
    }

    public void setReponseFausse1(String reponseFausse1) {
        this.reponseFausse1 = reponseFausse1;
    }

    public void setReponseFausse2(String reponseFausse2) {
        this.reponseFausse2 = reponseFausse2;
    }

    public void setReponseFausse3(String reponseFausse3) {
        this.reponseFausse3 = reponseFausse3;
    }

    public void setNote(int note) {
        this.note = note;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Question other = (Question) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.questionPosee, other.questionPosee)) {
            return false;
        }
        return true;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
     
    
    
}
