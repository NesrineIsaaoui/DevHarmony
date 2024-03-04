/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import database.Database;
import entities.Question;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 */
public class QuestionDao {
    
    private static QuestionDao instance ;
    private Statement st ;
    private ResultSet rs ;
    
    private QuestionDao(){
        Database db=Database.getInstance();
        try {
            st=db.getConnection().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static QuestionDao getInstance(){
        if(instance==null) 
            instance=new QuestionDao();
        return instance;
    }

    public void insertQuestion(Question q,int idParent,String type) {
        String query = "" ;
        if(type.equals("quiz")){
            query += "INSERT INTO  `questionquiz` (id_quiz,designation,reponse_correcte,reponse_fausse1,reponse_fausse2,reponse_fausse3,note) "
                    + "VALUES("+ idParent +",'"+ q.getQuestionPosee() +"','"+ q.getReponseCorrecte() +"','"+
                q.getReponseFausse1() +"','"+ q.getReponseFausse2() +"','"+ q.getReponseFausse3() +"',"+ q.getNote() +")" ;
        }
        else if(type.equals("test")){
            query += "INSERT INTO  `questiontest` (id_test,designation,reponse_correcte,reponse_fausse1,reponse_fausse2,reponse_fausse3,note) "
                    + "VALUES("+ idParent +",'"+ q.getQuestionPosee() +"','"+ q.getReponseCorrecte() +"','"+
                q.getReponseFausse1() +"','"+ q.getReponseFausse2() +"','"+ q.getReponseFausse3() +"',"+ q.getNote() +")" ;
        }
        
        try {
            st.executeUpdate(query) ;
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDao.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    public void deleteQuestion(Question q,String type) {
        String query = "" ;
        if(type.equals("quiz"))
        {
            query += "DELETE FROM `questionquiz` WHERE id = "+ q.getId() +"" ;
        }
        else if(type.equals("test")){
            query += "DELETE FROM `questiontest` WHERE id = "+ q.getId() +"" ;
        }
        
        Question quest = this.getQuestionById(q.getId(), type);
        
        if(quest != null){
            try {
                st.executeUpdate(query) ;
            } catch (SQLException ex) {
                Logger.getLogger(QuestionDao.class.getName()).log(Level.SEVERE,null,ex);
            }
        }
        
    }
    
    public void deleteQuestionByIdParent(Question q,String type,int idParent) {
        String query = "" ;
        if(type.equals("quiz"))
        {
            query += "DELETE FROM `questionquiz` WHERE id = "+ q.getId() +" and id_quiz ="+ idParent +" ";
        }
        else if(type.equals("test")){
            query += "DELETE FROM `questiontest` WHERE id = "+ q.getId() +" and id_test ="+ idParent +" " ;
        }
        
        Question quest = this.getQuestionById(q.getId(), type);
        
        if(quest != null){
            try {
                st.executeUpdate(query) ;
            } catch (SQLException ex) {
                Logger.getLogger(QuestionDao.class.getName()).log(Level.SEVERE,null,ex);
            }
        }
        
    }

    public ArrayList<Question> displayAllQuestions(String type,int idParent) {
        String query = "" ;
        ArrayList<Question> questions = new ArrayList<>() ;
        
        if(type.equals("quiz"))
        {
            query += "SELECT * FROM `questionquiz` WHERE id_quiz = "+ idParent +"" ;
        }
        else if(type.equals("test")){
            query += "SELECT * FROM `questiontest` WHERE id_test = "+ idParent +"" ;
        }
        
        try {
            rs = st.executeQuery(query) ;
            while (rs.next()) {
                Question q = new Question(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)
                        , rs.getString(7), rs.getInt(8)) ;
                q.setType(type); 
                questions.add(q) ;               
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDao.class.getName()).log(Level.SEVERE,null,ex);
        }
        return questions ;
    }
    
    public ObservableList<Question> displayQuestionsList(String type,int idParent) {
        String query = "" ;
        ObservableList<Question> questions = FXCollections.observableArrayList() ;
        
        if(type.equals("quiz"))
        {
            query += "SELECT * FROM `questionquiz` WHERE id_quiz = "+ idParent +"" ;
        }
        else if(type.equals("test")){
            query += "SELECT * FROM `questiontest` WHERE id_test = "+ idParent +"" ;
        }
        
        try {
            rs = st.executeQuery(query) ;
            while (rs.next()) {
                Question q = new Question(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)
                        , rs.getString(7), rs.getInt(8)) ;
                q.setType(type);
                questions.add(q) ;               
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDao.class.getName()).log(Level.SEVERE,null,ex);
        }
        return questions ;
    }

    public Question getQuestionById(int id,String type) {
        String query = "" ;
        Question q = new Question();
        if(type.equals("quiz"))
        {
            query += "SELECT * FROM `questionquiz` WHERE id = "+ id +"" ;
        }
        else if(type.equals("test")){
            query += "SELECT * FROM `questiontest` WHERE id = "+ id +"" ;
        }
        
        try {
            rs = st.executeQuery(query) ;
            while (rs.next()) {
                q.setId(rs.getInt(1)) ;
                q.setQuestionPosee(rs.getString(3)) ;
                q.setReponseCorrecte(rs.getString(4)) ;
                q.setReponseFausse1(rs.getString(5)) ;
                q.setReponseFausse2(rs.getString(6)) ;
                q.setReponseFausse3(rs.getString(7)) ;
                q.setType(type);
                q.setNote(rs.getInt(8));
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDao.class.getName()).log(Level.SEVERE,null,ex);
        }
      return q ; 
    }

    public boolean updateQuestionByIdParent(Question q,String type,int idParent) {
        String query = "" ;
        if(type.equals("quiz")){
            query += "UPDATE questionquiz SET id_quiz = "+ idParent +", designation = '"+ q.getQuestionPosee() +
                    "', reponse_correcte = '"+ q.getReponseCorrecte() +"', reponse_fausse1 = '"+ q.getReponseFausse1() +
                    "',reponse_fausse2 = '"+ q.getReponseFausse2() +"', reponse_fausse3 = '"+ q.getReponseFausse3() +
                    "', note = "+ q.getNote() +" WHERE id = "+ q.getId()+" " ;
        }
        else if(type.equals("test")){
             query += "UPDATE questiontest SET id_quiz = "+ idParent +", designation = '"+ q.getQuestionPosee() +
                    "', reponse_correcte = '"+ q.getReponseCorrecte() +"', reponse_fausse1 = '"+ q.getReponseFausse1() +
                    "',reponse_fausse2 = '"+ q.getReponseFausse2() +"', reponse_fausse3 = '"+ q.getReponseFausse3() +
                    "', note = "+ q.getNote() +" WHERE id = "+ q.getId()+" " ;
        }
        
        try {
            if(st.executeUpdate(query) > 0){
                return true ;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDao.class.getName()).log(Level.SEVERE,null,ex);
        }
      return false ;
    }
    
    public boolean updateQuestion(Question q,String type) {
        String query = "" ;
        if(type.equals("quiz")){
            query += "UPDATE questionquiz SET  designation = '"+ q.getQuestionPosee() +
                    "', reponse_correcte = '"+ q.getReponseCorrecte() +"', reponse_fausse1 = '"+ q.getReponseFausse1() +
                    "',reponse_fausse2 = '"+ q.getReponseFausse2() +"', reponse_fausse3 = '"+ q.getReponseFausse3() +
                    "', note = "+ q.getNote() +" WHERE id = "+ q.getId()+" " ;
        }
        else if(type.equals("test")){
             query += "UPDATE questiontest SET designation = '"+ q.getQuestionPosee() +
                    "', reponse_correcte = '"+ q.getReponseCorrecte() +"', reponse_fausse1 = '"+ q.getReponseFausse1() +
                    "',reponse_fausse2 = '"+ q.getReponseFausse2() +"', reponse_fausse3 = '"+ q.getReponseFausse3() +
                    "', note = "+ q.getNote() +" WHERE id = "+ q.getId()+" " ;
        }
        
        try {
            if(st.executeUpdate(query) > 0){
                return true ;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDao.class.getName()).log(Level.SEVERE,null,ex);
        }
      return false ;
    }
      
}
