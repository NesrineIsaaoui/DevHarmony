/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class Database {
    
    private Connection cnx;
    private static Database instance;
    
    private Database(){
        String url = "jdbc:mysql://127.0.0.1:3306/quizapp";
        try {
            cnx = DriverManager.getConnection(url,"root","");
            System.out.println("Connection établie");
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Database getInstance(){
        if(instance==null){
            instance = new Database();
        }
        return instance;
    }
    
    public Connection getConnection(){
        return cnx;
    }
    
}
