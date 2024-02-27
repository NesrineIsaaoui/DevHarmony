/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluation;

import Entities.Formulaire;
import Service.ServiceFormulaire;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;

/**
 * FXML Controller class
 *
 * @author oussa
 */
public class QuizzController implements Initializable {

    @FXML
    private CheckBox t1;
    @FXML
    private CheckBox f1;
    @FXML
    private CheckBox t2;
    @FXML
    private CheckBox f2;
    @FXML
    private CheckBox t3;
    @FXML
    private CheckBox f3;
 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void calculate() throws IOException{
    int sum = 0 ; 
    if((f1.isSelected())  && (f2.isSelected()) && (t3.isSelected())){
    sum=100 ; } 
    else if ((f1.isSelected()) && f2.isSelected() ) {
    sum=66;
    }
    else if ((f1.isSelected()) && t3.isSelected() ) {
    sum=66;
    }
     else if ((f2.isSelected()) && t3.isSelected() ) {
    sum=66;
    }
    else if ((f1.isSelected()) ) {
    sum=33;
    }
    else if ((f2.isSelected()) ) {
    sum=33;
    }
    else if ((t3.isSelected()) ) {
    sum=33;
    }
    else {sum=0;}
    
    if (sum>50) 
    {  Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Test Result");
        alert.setHeaderText("You have passed the test");
        alert.setContentText("Your score is "+sum+"%");
        
        Optional<ButtonType> btn = alert.showAndWait();
        
         Formulaire c =new Formulaire("Validee",Formulaire_ajoutController.id);
         ServiceFormulaire sc=new ServiceFormulaire();
         sc.modifieretat(c);}
    else if ( sum <50) {Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Test Result");
        alert.setHeaderText("You have failed the test");
        alert.setContentText("Your score is "+sum+"%");
          Optional<ButtonType> btn = alert.showAndWait();
          
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("formation.fxml"));
        Parent root = loader.load();
        t1.getScene().setRoot(root);
        
      
         
        
         }
    
    
    
    
    
    
    }
}
