/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.User;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author Dahmani
 */
public class AuthentificationController implements Initializable {

    @FXML
    private TextField loginInput;
    @FXML
    private TextField passwordInput;
    @FXML
    private Button authentifierBt;
    @FXML
    private Hyperlink inscrireBtn;
    @FXML
    private Label erreurLabel;

    private boolean connected = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void handleAuthentifierBt(ActionEvent event) {
        UserService us = UserService.getInstance();
        List<User> users = us.getAllUsers();
        if (event.getSource() == authentifierBt) {
            String login = loginInput.getText();
            String passwd = passwordInput.getText();

            for (User u : users) {
                if (u.getLogin().equals(login) && u.getPassword().equals(passwd)) {
                    if (u.getRole().toLowerCase().equals("admin")) {
                        System.out.println("Admin connecté");
                        connected = true;
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Accueil.fxml"));
                            Stage stage = new Stage(StageStyle.DECORATED);
                            stage.setScene(
                                    new Scene(loader.load())
                            );
                            stage.setTitle("E-SENPAI | E-Learning Platform");
                            stage.setResizable(false);

                            AccueilController controller = loader.getController();
                            controller.initData(u);

                            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            oldStage.close();

                            stage.show();

                        } catch (IOException ex) {
                            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        if (u.getRole().toLowerCase().equals("formateur")) {
                            if (u.getStatus().toLowerCase().equals("en attente")) {
                                System.out.println("formateur en attente");

                            } else {
                                System.out.println("formateur connecté");
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Accueil.fxml"));
                                    Stage stage = new Stage(StageStyle.DECORATED);
                                    stage.setScene(
                                            new Scene(loader.load())
                                    );
                                    stage.setTitle("E-SENPAI | E-Learning Platform");
                                    stage.setResizable(false);

                                    AccueilController controller = loader.getController();
                                    controller.initData(u);

                                    Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    oldStage.close();

                                    stage.show();

                                } catch (IOException ex) {
                                    Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            connected = true;
                        } else {
                            System.out.println("Etudiant connecté");
                            connected = true;
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Accueil.fxml"));
                                Stage stage = new Stage(StageStyle.DECORATED);
                                stage.setScene(
                                        new Scene(loader.load())
                                );
                                stage.setTitle("E-SENPAI | E-Learning Platform");
                                stage.setResizable(false);

                                AccueilController controller = loader.getController();
                                controller.initData(u);

                                Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                oldStage.close();

                                stage.show();

                            } catch (IOException ex) {
                                Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

                }

            }
            if (!connected) {
                erreurLabel.setText("Vérifier Login & Mot De Passe");
            }
        }
    }

    @FXML
    private void handleInscrireAction(ActionEvent event) {

        try {
            Parent page1 = FXMLLoader.load(getClass().getResource("/views/Inscription.fxml"));
            Scene scene = new Scene(page1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
