
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import database.Database;
import entities.Formation;
import entities.Question;
import entities.Test;
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
public class TestDao {
    
    private static TestDao instance ;
    private Statement st ; 
    private ResultSet rs ;
    
    private TestDao(){
        Database db = Database.getInstance() ;
        try {
            st = db.getConnection().createStatement() ;
        } catch (SQLException ex) {
            Logger.getLogger(TestDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static TestDao getInstance(){
        if(instance == null){
            instance = new TestDao() ;
        }
        return instance ;
    }
    
    public void insertTest(Test t,int idFormateur) {
        String query = "INSERT INTO test(id_formateur,id_formation,sujet,duree,nb_etudiant_passes,nb_etudiants_admis) "
                + "VALUES("+ idFormateur +","+ t.getIdFormation() +",'"+ t.getSujet() +"'," +t.getDuree()+ ",0,0    )" ;
        try {
            st.executeUpdate(query );
           
        } catch (SQLException ex) {
            Logger.getLogger(QuizDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         QuestionDao qdao = QuestionDao.getInstance() ;
         Test test = getTestbySujet(t.getSujet(),idFormateur);
            for (Question question : t.getQuestions()) {
                qdao.insertQuestion(question, test.getId(), "test");
            }
        
    }
    
    public void deleteTest(Test t) {
      String query = "DELETE from test WHERE id = "+ t.getId() +" ";
      Test test = this.getTestById(t.getId()) ;
      if(test != null){
          try {
              QuestionDao questDao = QuestionDao.getInstance() ;
              for(Question question : t.getQuestions()){
                  questDao.deleteQuestionByIdParent(question, "test",t.getId());
              }
              st.executeUpdate(query );
          } catch (SQLException ex) {
              Logger.getLogger(QuizDao.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
    }
    
    public List<Test> displayAllTest(int idFormateur) {
        String query = "SELECT *FROM test WHERE id_formateur = "+ idFormateur+" " ;
        ArrayList<Test> listTest = new ArrayList<>() ;       
        try {
            rs = st.executeQuery(query) ;
            QuestionDao qdao = QuestionDao.getInstance() ;
            while(rs.next()){
                ArrayList<Question> questions = (ArrayList<Question>) qdao.displayAllQuestions("test", rs.getInt(1)) ;
                Test t = new Test(rs.getInt(1), rs.getString("sujet"), questions);
                t.setIdFormateur(rs.getInt("id_formateur"));
                t.setIdFormateur(rs.getInt("id_formation"));
                t.setDuree(rs.getInt("duree"));
                int duree = rs.getInt("duree") ;
                int hours = (duree/3600) ;
                int min = ((duree%3600)/60) ;
                t.setTemps(hours+"h"+min+"min");
                listTest.add(t) ;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      return listTest ;
    }
    
    public List<Test> getAllTest(String value ) {
        String query = "SELECT *FROM test where sujet LIKE '%"+value+"%'" ;
        ArrayList<Test> listTest = new ArrayList<>() ;       
        try {
            rs = st.executeQuery(query) ;
            QuestionDao qdao = QuestionDao.getInstance() ;
            while(rs.next()){
                ArrayList<Question> questions = (ArrayList<Question>) qdao.displayAllQuestions("test", rs.getInt(1)) ;
                Test t = new Test(rs.getInt(1), rs.getString("sujet"), questions);
                t.setIdFormateur(rs.getInt("id_formateur"));
                t.setIdFormation(rs.getInt("id_formation"));
                t.setDuree(rs.getInt("duree"));
                int duree = rs.getInt("duree") ;
                int hours = (duree/3600) ;
                int min = ((duree%3600)/60) ;
                t.setTemps(hours+"h"+min+"min");
                listTest.add(t) ;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      return listTest ;
    }
    
    public ObservableList<Test> getListTest(int idFormateur ) {
        String query = "SELECT *FROM test where id_formateur="+ idFormateur +"" ;
        ObservableList<Test> listTest = FXCollections.observableArrayList() ;       
        try {
            rs = st.executeQuery(query) ;
            QuestionDao qdao = QuestionDao.getInstance() ;
            while(rs.next()){
                ArrayList<Question> questions = (ArrayList<Question>) qdao.displayAllQuestions("test", rs.getInt(1)) ;
                Test t = new Test(rs.getInt(1), rs.getString("sujet"), questions);
                t.setIdFormateur(rs.getInt("id_formateur"));
                t.setIdFormateur(rs.getInt("id_formation"));
                t.setDuree(rs.getInt("duree"));
                int duree = rs.getInt("duree") ;
                int hours = (duree/3600) ;
                int min = ((duree%3600)/60) ;
                t.setTemps(hours+"h"+min+"min");
                listTest.add(t) ;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      return listTest ;
    }
    
    public ObservableList<Test> getListTest(  ) {
        String query = "SELECT *FROM test " ;
        ObservableList<Test> listTest = FXCollections.observableArrayList() ;       
        try {
            rs = st.executeQuery(query) ;
            QuestionDao qdao = QuestionDao.getInstance() ;
            while(rs.next()){
                ArrayList<Question> questions = (ArrayList<Question>) qdao.displayAllQuestions("test", rs.getInt(1)) ;
                Test t = new Test(rs.getInt(1), rs.getString("sujet"), questions);
                t.setIdFormateur(rs.getInt("id_formateur"));
                t.setIdFormateur(rs.getInt("id_formation"));
                t.setDuree(rs.getInt("duree"));
                t.setNbEtudiantsAdmis(rs.getInt("nb_etudiants_admis"));
                t.setNbEtudiantsPasses(rs.getInt("nb_etudiant_passes"));
                int duree = rs.getInt("duree") ;
                int hours = (duree/3600) ;
                int min = ((duree%3600)/60) ;
                t.setTemps(hours+"h"+min+"min");
                listTest.add(t) ;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      return listTest ;
    }
    
    
    public Test getTestById(int id) {
        String query = "SELECT *FROM test WHERE id = "+id+" " ;
        Test test = new Test() ;
        try {
            rs = st.executeQuery(query) ;
            QuestionDao qdao = QuestionDao.getInstance() ;
            while (rs.next()) {  
                ArrayList<Question> questions = (ArrayList<Question>) qdao.displayAllQuestions("test", rs.getInt(1)) ;
                test.setId(rs.getInt(1));
                test.setSujet(rs.getString("sujet"));
                test.setQuestions(questions);
                test.setIdFormation(rs.getInt("id_formation"));
                test.setIdFormateur(rs.getInt("id_formateur"));
                test.setDuree(rs.getInt("duree"));
                int duree = rs.getInt("duree") ;
                int hours = (duree/3600) ;
                int min = ((duree%3600)/60) ;
                test.setTemps(hours+"h"+min+"min");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDao.class.getName()).log(Level.SEVERE, null, ex);
        }
       return test ;
    }
    
    public boolean updateTest(Test t, int idFromateur) {
        String query = "UPDATE test SET sujet = '"+ t.getSujet() +"',duree = "+t.getDuree()+" where id = "+t.getId()+"" ;
        try {
            int updatedRow = st.executeUpdate(query );
           /* QuestionDao qdao = QuestionDao.getInstance() ;
            for(Question question : t.getQuestions()){
                qdao.updateQuestion(question, "quiz") ;
            }*/
            if(updatedRow > 0)
                return true ;
        } catch (SQLException ex) {
            Logger.getLogger(QuizDao.class.getName()).log(Level.SEVERE, null, ex);
        }
      return false ;
    }
    
    public Test getTestbySujet(String sujet,int idFormateur){
        Test t = new Test() ;
        String query = "SELECT *from test WHERE sujet='"+ sujet +"' and id_formateur="+ idFormateur +"" ;
        try {
            rs = st.executeQuery(query) ;
            while(rs.next()){
                t.setId(rs.getInt("id"));
                t.setSujet(rs.getString("sujet"));
                t.setIdFormation(rs.getInt("id_formation"));
                t.setDuree(rs.getInt("duree"));
                int duree = rs.getInt("duree") ;
                int hours = (duree/3600) ;
                int min = ((duree%3600)/60) ;
                t.setTemps(hours+"h"+min+"min");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDao.class.getName()).log(Level.SEVERE, null, ex);
        }
       return t ;
    }
    
     public ObservableList<Test> displayTestList(int idFormateur) {
        String query = "SELECT *FROM test WHERE id_formateur = "+ idFormateur+" " ;
        ObservableList<Test> listTest = FXCollections.observableArrayList() ;       
        try {
            rs = st.executeQuery(query) ;
            QuestionDao qdao = QuestionDao.getInstance() ;
            while(rs.next()){
                ArrayList<Question> questions = qdao.displayAllQuestions("test", rs.getInt("id")) ;
                Test test = new Test(rs.getInt("id"), rs.getString("sujet"),questions);
                test.setIdFormation(rs.getInt("id_formation"));
                test.setDuree(rs.getInt("duree"));
                test.setNbEtudiantsAdmis(rs.getInt("nb_etudiants_admis"));
                test.setNbEtudiantsPasses(rs.getInt("nb_etudiant_passes"));
                int duree = rs.getInt("duree") ;
                int hours = (duree/3600) ;
                int min = ((duree%3600)/60) ;
                test.setTemps(hours+"h"+min+"min");
                listTest.add(test) ;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      return listTest ;
    }
     
    public boolean updateNbreEtudiantsPasses(int idTest){
        String query = "UPDATE test set nb_etudiant_passes = nb_etudiant_passes + 1 where id="+idTest+ "" ;
        try {
            int updatedRow = st.executeUpdate(query);
            if(updatedRow > 0)
                return true ;
        } catch (SQLException ex) {
            Logger.getLogger(TestDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false ;
    }
    
    public boolean updateNbreEtudiantsAdmis(int idTest){
        String query = "UPDATE test set nb_etudiants_admis = nb_etudiants_admis + 1 where id="+idTest+ "" ;
        try {
            int updatedRow = st.executeUpdate(query);
            if(updatedRow > 0)
                return true ;
        } catch (SQLException ex) {
            Logger.getLogger(TestDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false ;
    }
    
    public int countTest(){
        String req = "SELECT COUNT(id) FROM test";
        int sum=0;
        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                sum=rs.getInt(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sum;
    }
    
     public List<Formation> getFormations(int idFormateur){
         ArrayList<Formation> list = new ArrayList<>() ;
         String query = "select *from formation where id_formateur = "+idFormateur+"" ;
         
        try {
            rs = st.executeQuery(query) ;
            while (rs.next()) {
               Formation f = new Formation() ;
               f.setId(rs.getInt("id"));
               f.setIdFormateur(rs.getInt("id_formateur"));
               f.setTitre(rs.getString("titre"));
               f.setNote(rs.getInt("note")) ;
               f.setDescription(rs.getString("description"));
               list.add(f );
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         return list ;     
     }
     
     public Formation getFormationbyTitre(String titre){
         Formation f = new Formation() ;
         String query = "select *from formation where titre = '"+ titre +"' ";
        try {
            rs = st.executeQuery(query );
            while (rs.next()) {
               f.setId(rs.getInt("id"));
               f.setIdFormateur(rs.getInt("id_formateur"));
               f.setTitre(rs.getString("titre"));
               f.setNote(rs.getInt("note")) ;
               f.setDescription(rs.getString("description"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         return f ;
     }     
}
