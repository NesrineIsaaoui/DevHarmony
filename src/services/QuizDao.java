/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import database.Database;
import entities.Question;
import entities.Quiz;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 */
public class QuizDao{
    
    private static QuizDao instance ;
    private Statement st ;
    private ResultSet rs ;
    
    private QuizDao(){
        Database db = Database.getInstance() ;
        try {
            st = db.getConnection().createStatement() ;
        } catch (SQLException ex) {
            Logger.getLogger(QuizDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static QuizDao getInstance(){
        if(instance==null) 
            instance=new QuizDao();
        return instance;
    }

    public void insertQuiz(Quiz q,int idFormateur) {
        String query = "INSERT INTO quiz(id_formateur,sujet) VALUES("+ idFormateur +",'"+ q.getSujet() +"')" ;
        try {
            st.executeUpdate(query );
           
        } catch (SQLException ex) {
            Logger.getLogger(QuizDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         QuestionDao qdao = QuestionDao.getInstance() ;
         Quiz qz = getQuizbySujet(q.getSujet(), idFormateur) ;
            for (Question question : q.getQuestions()) {
                qdao.insertQuestion(question, qz.getId(), "quiz");
            }
        
    }

    public void deleteQuiz(Quiz q) {
      String query = "DELETE from quiz WHERE id = "+ q.getId() +" ";
      Quiz quiz = this.getQuizById(q.getId()) ;
      if(quiz != null){
          try {
              QuestionDao questDao = QuestionDao.getInstance() ;
              for(Question question : q.getQuestions()){
                  questDao.deleteQuestionByIdParent(question,"quiz", q.getId());
              }
              st.executeUpdate(query );
          } catch (SQLException ex) {
              Logger.getLogger(QuizDao.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
    }

    public List<Quiz> displayAllQuiz(int idFormateur) {
        String query = "SELECT *FROM quiz WHERE id_formateur = "+ idFormateur+" " ;
        ArrayList<Quiz> listQuiz = new ArrayList<>() ;       
        try {
            rs = st.executeQuery(query) ;
            QuestionDao qdao = QuestionDao.getInstance() ;
            while(rs.next()){
                ArrayList<Question> questions =  qdao.displayAllQuestions("quiz", rs.getInt(1)) ;
                Quiz quiz = new Quiz(rs.getInt(1), rs.getString(3), questions);
                listQuiz.add(quiz) ;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      return listQuiz ;
    }
    
    public List<Quiz> getAllQuiz(String value) {
        String query = "SELECT *FROM quiz where sujet LIKE '%"+value+"%' " ;
        ArrayList<Quiz> listQuiz = new ArrayList<>() ;       
        try {
            rs = st.executeQuery(query) ;
            QuestionDao qdao = QuestionDao.getInstance() ;
            while(rs.next()){
                ArrayList<Question> questions =  qdao.displayAllQuestions("quiz", rs.getInt(1)) ;
                Quiz quiz = new Quiz(rs.getInt(1), rs.getString(3), questions);
                quiz.setIdFormateur(rs.getInt("id_formateur"));
                listQuiz.add(quiz) ;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      return listQuiz ;
    }
    
     public ObservableList<Quiz> displayQuizList(int idFormateur) {
        String query = "SELECT *FROM quiz WHERE id_formateur = "+ idFormateur+" " ;
        ObservableList<Quiz> listQuiz = FXCollections.observableArrayList() ;       
        try {
            rs = st.executeQuery(query) ;
            QuestionDao qdao = QuestionDao.getInstance() ;
            while(rs.next()){
                ArrayList<Question> questions = qdao.displayAllQuestions("quiz", rs.getInt("id")) ;
                Quiz quiz = new Quiz(rs.getInt("id"), rs.getString("sujet"),questions);
                listQuiz.add(quiz) ;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      return listQuiz ;
    }
     
    public ObservableList<Quiz> displayQuizList( ) {
        String query = "SELECT *FROM quiz " ;
        ObservableList<Quiz> listQuiz = FXCollections.observableArrayList() ;       
        try {
            rs = st.executeQuery(query) ;
            QuestionDao qdao = QuestionDao.getInstance() ;
            while(rs.next()){
                ArrayList<Question> questions = qdao.displayAllQuestions("quiz", rs.getInt("id")) ;
                Quiz quiz = new Quiz(rs.getInt("id"), rs.getString("sujet"),questions);
                listQuiz.add(quiz) ;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      return listQuiz ;
    }

    public Quiz getQuizById(int id) {
        String query = "SELECT *FROM quiz WHERE id = "+id+" " ;
        Quiz quiz = new Quiz() ;
        try {
            rs = st.executeQuery(query) ;
            QuestionDao qdao = QuestionDao.getInstance() ;
            while (rs.next()) {  
                ArrayList<Question> questions = (ArrayList<Question>) qdao.displayAllQuestions("quiz", rs.getInt(1)) ;
                quiz.setId(rs.getInt(1));
                quiz.setSujet(rs.getString(3));
                quiz.setQuestions(questions);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDao.class.getName()).log(Level.SEVERE, null, ex);
        }
       return quiz ;
    }

    public boolean updateQuiz(Quiz q, int idFromateur) {
        String query = "UPDATE quiz SET sujet = '"+ q.getSujet() +"' WHERE id = "+ q.getId() +"" ;
        try {
            int updatedRow = st.executeUpdate(query );
           /* QuestionDao qdao = QuestionDao.getInstance() ;
            for(Question question : q.getQuestions()){
                qdao.updateQuestionByIdParent(question, "quiz", q.getId()) ;
            }*/
            if(updatedRow > 0)
                return true ;
        } catch (SQLException ex) {
            Logger.getLogger(QuizDao.class.getName()).log(Level.SEVERE, null, ex);
        }
      return false ;
    }
    
    public Quiz getQuizbySujet(String sujet,int idFormateur){
        Quiz q = new Quiz() ;
        String query = "SELECT *from quiz WHERE sujet='"+ sujet +"' and id_formateur="+ idFormateur +"" ;
        try {
            rs = st.executeQuery(query) ;
            while(rs.next()){
                q.setId(rs.getInt("id"));
                q.setSujet(rs.getString("sujet"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDao.class.getName()).log(Level.SEVERE, null, ex);
        }
       return q ;
    }
    
}
