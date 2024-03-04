/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import database.Database;
import entities.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.ObservableList;

/**
 *
 */
public class UserService {
    
    private static UserService instance;
    private Statement st;
    private ResultSet rs;
    
    private UserService() {
        Database cs=Database.getInstance();
        try {
            st=cs.getConnection().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static UserService getInstance(){
        if(instance==null) 
            instance=new UserService();
        return instance;
    }
    
    //méthodes de crud
    
    /*public ObservableList<User> getAdmins() {
        
    }*/
        public List<User> getAllUsers() {
        String req="select * from user";
        List<User> list=new ArrayList<>();
        
        try {
            rs=st.executeQuery(req);
            while(rs.next()){
                User p=new User();
                p.setId(rs.getInt(1));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setLogin(rs.getString("login"));
                p.setPassword(rs.getString("password"));
                p.setStatus(rs.getString("status"));
                p.setRole(rs.getString("role"));
                list.add(p);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public void insertEtudiant(User o) {
        String req="insert into user (nom,prenom,date_naissance,sexe,email,role,login,password,status,photo_profil,biography) values ('"+o.getNom()+"','"+o.getPrenom()+"','"+o.getDate_naissance()+"','"+o.getSexe()+"','"+o.getEmail()+"','Etudiant','"+o.getLogin()+"','"+o.getPassword()+"','Approuvé','','')";
        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    }
    
    public void insertFormateur(User o) {
        String req="insert into user (nom,prenom,date_naissance,sexe,email,role,login,password,status,photo_profil,biography) values ('"+o.getNom()+"','"+o.getPrenom()+"','"+o.getDate_naissance()+"','"+o.getSexe()+"','"+o.getEmail()+"','Formateur','"+o.getLogin()+"','"+o.getPassword()+"','En attente','','')";
        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
           
        }
        
    }
    
    public boolean isValid(String email) 
    { 
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) 
            return false; 
        return pat.matcher(email).matches(); 
    }
    
    public User getUserById(int id){
        User u = new User();
        String query = "select *from user where id = "+id+"" ;
        try {
           rs = st.executeQuery(query );
            while(rs.next()){
                u.setNom(rs.getString("nom"));
                u.setId(rs.getInt("id"));
                u.setPrenom(rs.getString("prenom"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u ;
    }
    
//    public User getUserByLogin(String login){
//        String req = "";
//    }
}
