/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import database.Database;
import entities.ListNote;
import entities.Note;
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
public class NoteDao {
    private static NoteDao instance ;
    private Statement st ;
    private ResultSet rs ;
    
    private NoteDao(){
        Database db=Database.getInstance();
        try {
            st=db.getConnection().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static NoteDao getInstance(){
        if(instance == null)
            instance =  new NoteDao() ;
        
        return instance ;
    }
    
    public boolean insertNote(Note n){
        Note note = getNoteById(n.getIdEtudiant(), n.getIdTest()) ;
        if(note == null){
            String query = "INSERT INTO note (id_etudiant,id_test,note_obtenue) "
                    + "VALUES("+n.getIdEtudiant()+","+n.getIdTest()+","+n.getNoteObtnue()+")" ;
            try {
                System.out.println("queey"+query);
                st.executeUpdate(query );
            } catch (SQLException ex) {
                Logger.getLogger(NoteDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true ;
        }
        else{
            if(note.getNoteObtnue() < n.getNoteObtnue()){
                updateNote(n.getNoteObtnue(), note.getId()) ;
            }
            return false ;
        }
    } 
    
    public Note getNoteById(int idEtudiant,int idTest){
        Note n = null;
        String query = "SELECT *FROM note WHERE id_test="+ idTest +" and id_etudiant = "+ idEtudiant +"" ;
        try {
            rs = st.executeQuery(query) ;
            while(rs.next()){
                n = new Note() ;
                n.setId(rs.getInt("id"));
                n.setIdTest(rs.getInt("id_test"));
                n.setIdEtudiant(rs.getInt("id_etudiant"));
                n.setNoteObtnue(rs.getInt("note_obtenue"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(NoteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(n != null)
            return  n ;
        
      return null ;
    }
    
    public boolean updateNote(int note,int id){
       String query = "UPDATE note set note_obtenue = "+note+" where id = "+id+"" ;
        try {
            int updatedRow = st.executeUpdate(query) ;
            if(updatedRow > 0)
                return true ;
            
        } catch (SQLException ex) {
            Logger.getLogger(NoteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false ;
    }
    
    public List<Note> getAllNote(){
        List<Note> list = new ArrayList<>() ;
        String query = "SELECT *from note" ;
        try {
            rs = st.executeQuery(query );
            while(rs.next()){
                Note n = new Note() ;
                n.setId(rs.getInt("id"));
                n.setIdEtudiant(rs.getInt("id_etudiant"));
                n.setIdTest(rs.getInt("id_test"));
                n.setNoteObtnue(rs.getInt("note_obtenue"));
                list.add(n );
            }
        } catch (SQLException ex) {
            Logger.getLogger(NoteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
      return list ;
    }
    
    public void deleteNote(int id){
        String query = "DELETE FROM note WHERE id = "+id+" " ;
        try {
            st.executeUpdate(query );
        } catch (SQLException ex) {
            Logger.getLogger(NoteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public ObservableList<ListNote>  getAllNote(int idFormateur){
         ObservableList<ListNote> list = FXCollections.observableArrayList() ;
         String query = "SELECT u.id,sujet,nom,prenom,note_obtenue,titre FROM user u inner join note n on u.id = n.id_etudiant "
                 + " inner join test t on n.id_test = t.id inner join formation f on f.id = t.id_formation where "
                 + " t.id_formateur="+idFormateur+" order by nom";
        try {
            rs = st.executeQuery(query) ;
            while (rs.next()) {  
                String nom = rs.getString("nom");/* +" "+  rs.getString("prenom") ;*/
                ListNote note = new ListNote(nom, rs.getInt("note_obtenue"), rs.getString("sujet"), rs.getString("titre")) ;
                note.setIdEtudiant(rs.getInt("u.id"));
                list.add(note) ;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        return list ;
     }
        
     public ObservableList<ListNote>  getNoteByUserId(int idFormateur,int idEtudiant){
         ObservableList<ListNote> list = FXCollections.observableArrayList() ;
         String query = "SELECT sujet,nom,prenom,note_obtenue,titre FROM user u inner join note n on u.id = n.id_etudiant "
                 + " inner join test t on n.id_test = t.id inner join formation f on f.id = t.id_formation where "
                 + " t.id_formateur="+idFormateur+" and n.id_etudiant = "+idEtudiant+" order by nom";
        try {
            rs = st.executeQuery(query) ;
            while (rs.next()) {  
                String nom = rs.getString("nom");/* +" "+  rs.getString("prenom") ;*/
                ListNote note = new ListNote(nom, rs.getInt("note_obtenue"), rs.getString("sujet"), rs.getString("titre")) ;
                note.setIdEtudiant(idEtudiant);
                list.add(note) ;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        return list ;
     }
}
