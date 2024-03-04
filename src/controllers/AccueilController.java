/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Session;
import entities.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Dahmani
 */
public class AccueilController implements Initializable {

     @FXML
    private Button accueilBtn;
    @FXML
    private Button profilBtn;
    @FXML
    private Button formationsBtn;
    @FXML
    private Button test_quizBtn;
    @FXML
    private Button chatBtn;
    @FXML
    private Button deconnectBtn;
    @FXML
    private Label welcomeLabel;
    
    User currentUser;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
    }

    public void initData(User u){
        this.currentUser=u;
        welcomeLabel.setText("Bienvenue "+currentUser.getNom());
    }

    @FXML
    private void handleAccueilBtn(ActionEvent event) {
    }

    @FXML
    private void HandleAccueilBtn(ActionEvent event) {
    }

    @FXML
    private void handleTestQuizBtn(ActionEvent event) {
        try {
             
            if(currentUser.getRole().equals("Etudiant")){
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/TestEtudiant.fxml"));
                 Stage stage = new Stage(StageStyle.DECORATED);
                stage.setScene(
                        new Scene(loader.load())
                );
                stage.setTitle("E-SENPAI | E-Learning Platform");
                stage.setResizable(false);

               TestEtudiantController controller = loader.getController() ;
               controller.initData(currentUser);
               
               Session  s = new Session() ;
               Session.setUser(currentUser);

                Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                oldStage.close();

                stage.show();
            }
            else if(currentUser.getRole().toLowerCase().equals("admin")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/TestQuizAdmin.fxml"));
                 Stage stage = new Stage(StageStyle.DECORATED);
                stage.setScene(
                        new Scene(loader.load())
                );
                stage.setTitle("E-SENPAI | E-Learning Platform");
                stage.setResizable(false);

                TestQuizAdminController controller = loader.getController();
                controller.initData(currentUser);

                Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                oldStage.close();

                stage.show();
            }
            else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Test.fxml"));
                 Stage stage = new Stage(StageStyle.DECORATED);
                stage.setScene(
                        new Scene(loader.load())
                );
                stage.setTitle("E-SENPAI | E-Learning Platform");
                stage.setResizable(false);

                TestController controller = loader.getController();
                controller.initData(currentUser);

                Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                oldStage.close();

                stage.show();
            }
           // loader = new FXMLLoader(getClass().getResource("/views/Test.fxml"));
           

        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleChatBtn(ActionEvent event) {
    }

    @FXML
    private void handleDeconnectBtn(ActionEvent event) {
    }
    
}
