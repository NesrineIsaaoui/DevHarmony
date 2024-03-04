/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Quiz;
import entities.Test;
import entities.User;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.QuizDao;
import services.TestDao;

/**
 * FXML Controller class
 *
 */
public class CardTestController implements Initializable {

    @FXML
    private VBox testBox;
    @FXML
    private Label lbType;
    @FXML
    private Label lbSujet;
    @FXML
    private Label totalQuestion;
    @FXML
    private Label formateur;
    @FXML
    private ImageView formateurImage;
    @FXML
    private Label testId;
    
    private User currentUser ;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setCurrentUser(User u){
        currentUser = u ;
        System.out.println(u.getNom());
    }
    public void setData(Quiz q,User u){
        if(q.getClass().getSimpleName().toLowerCase().equals("quiz")){
            lbType.setText("Quiz");
            lbSujet.setText(q.getSujet());
            totalQuestion.setText(q.getQuestions().size() + "  Questions");
            formateur.setText("par Mr/Mme " + u.getNom() +" " + u.getPrenom());
            testId.setText(q.getId()+ "");
            testId.setVisible(false);
        } 
        
        if(q.getClass().getSimpleName().toLowerCase().equals("test")){
            lbType.setText("Test");
            lbSujet.setText(q.getSujet());
            totalQuestion.setText(q.getQuestions().size()+ " Questions");
            formateur.setText("par Mr/Mme " + u.getNom() +" " + u.getPrenom());
            testId.setText(q.getId()+"");
            testId.setVisible(false);
        }
    }

    @FXML
    private void getClickedQuiz(MouseEvent event) {
        int idTest = Integer.parseInt(testId.getText()) ;
        String type,sujet,nomFormateur ;
        type = lbType.getText() ;
        sujet = lbSujet.getText() ;
        nomFormateur = formateur.getText() ;
        
        
        Alert.AlertType alertType = Alert.AlertType.CONFIRMATION ;
        Alert alert = new Alert(alertType,"" );
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.getDialogPane().setContentText("Veuillez comfirmez pour passer ce " + type);
        alert.getDialogPane().setHeaderText("Passer Quiz/Test");
        
        Optional<ButtonType> result =  alert.showAndWait() ;
        Quiz quiz = null ;
        if(result.get() == ButtonType.OK){
          if(type.toLowerCase().equals("quiz")){
              
              QuizDao qdao = QuizDao.getInstance() ;
               quiz = qdao.getQuizById(idTest) ;
          }
          
          if(type.toLowerCase().equals("test")){
              
              TestDao tdao = TestDao.getInstance();
               quiz = (Test)tdao.getTestById(idTest) ;
          }
            //  System.out.println(quiz.getQuestions().size() + currentUser.getNom());
           try {   
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PasserQuiz.fxml"));
                    Stage stage = new Stage(StageStyle.DECORATED);

                     stage.setScene(
                             new Scene(loader.load())
                     );
            
                    stage.setTitle("E-SENPAI | E-Learning Platform");
                    stage.setResizable(false);
                    
                    PasserQuizController controller = loader.getController();
                    controller.setQuiz(quiz);
                   // controller.setUser(currentUser);

                    Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    
                    oldStage.close();

                    stage.show();
          
              } catch (IOException ex) {
                  Logger.getLogger(CardTestController.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
        
    }
}
