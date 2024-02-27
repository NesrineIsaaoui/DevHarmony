/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entities.Formulaire;
import Utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author oussa
 */
public class ServiceFormulaire {
    Connection cnx;
    private PreparedStatement ste ;

    public ServiceFormulaire() {
    cnx =MyConnection.getInstance().getConnection();        
    }
    public void ajouterf(Formulaire f) {
       String req ="INSERT INTO evaluation (nom_eleve,email,diplome,etat) VALUES (?,?,?,?)";
        try {
            ste = cnx.prepareStatement(req);
            
            
            ste.setString(1,f.getNom_eleve());
            ste.setString(2,f.getEmail());
            ste.setString(3,f.getDiplome());
            ste.setString(4,f.getEtat());
            ste.executeUpdate();
            System.out.println("Formulaire ajoutée");
            
        } catch (SQLException ex) {
            System.out.println("Probléme");
            System.out.println(ex.getMessage());
      }
    }
     public List<Formulaire> ListClasse() {
        List<Formulaire> Mylist = new ArrayList<>();
        try {
            String requete = "select * from evaluation";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet e = pst.executeQuery();
            while (e.next()) {
                Formulaire pre = new Formulaire();
              
            pre.setId(e.getInt("id"));
            pre.setNom_eleve(e.getString("nom_eleve"));
            pre.setEmail(e.getString("email"));
            pre.setDiplome(e.getString("diplome"));
            
            pre.setEtat(e.getString("etat"));
           
            
            
            
                Mylist.add(pre);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Mylist;
    }
   
  public void supprimerc (Formulaire c ) {
    String requete = "DELETE FROM evaluation where id =?";
           try {
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, c.getId());
            pst.executeUpdate();
            System.out.println("Formulaire Supprimée !!!!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
    }  
  public void modifierc (Formulaire c) {
    
        try {
            String requete = "update evaluation set nom_eleve=?, email=? ,etat =? ,diplome=? where ? = id";
            PreparedStatement pre = cnx.prepareStatement(requete);
            
            pre.setString(1,c.getNom_eleve());
            pre.setString(2,c.getEmail());
            pre.setString(3, c.getEtat()); 
            pre.setString(4, c.getDiplome()); 
            pre.setInt(5, c.getId());
            
            

            pre.executeUpdate();
            System.out.println("formulaire Updated !!!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
   public void modifieretat (Formulaire c) {
    
        try {
            String requete = "update evaluation set etat=?  where ? = id";
            PreparedStatement pre = cnx.prepareStatement(requete);
            
            pre.setString(1,c.getEtat());
            pre.setInt(2, c.getId()); 
           
            
            

            pre.executeUpdate();
            System.out.println("formulaire Updated !!!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
   public int getid (String n ){ 
    int cl=0 ; 
    try {
            String requete = "select * from evaluation where ?= nom_eleve";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, n);
            ResultSet e = pst.executeQuery();
            while(e.next()){
            Formulaire pre = new Formulaire() ; 
            
            pre.setId(e.getInt("id"));
            cl=pre.getId();   }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
   
return cl ; 
    }
}
