/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.User;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import services.UserService;

/**
 * FXML Controller class
 *
 */
public class InscriptionController implements Initializable {

    @FXML
    private Button inscrireBtn;
    @FXML
    private Hyperlink authentifierBtn;
    @FXML
    private RadioButton rbHomme;
    @FXML
    private ToggleGroup rbGender;
    @FXML
    private RadioButton rbFemme;

    String sexe="Homme";

    @FXML
    private DatePicker inputDateN;
    @FXML
    private TextField inputNom;
    @FXML
    private TextField inputPrenom;
    @FXML
    private TextField inputEmail;
    @FXML
    private TextField inputLogin;
    @FXML
    private TextField inputPassword;
    @FXML
    private TextField inputVPassword;
    @FXML
    private CheckBox chkFormateur;
    @FXML
    private Label erreurLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleInscrireBt(ActionEvent event) {

        LocalDate dateN = inputDateN.getValue();
        

        if (inputNom.getText().equals("") || inputPrenom.getText().equals("") || dateN.toString().equals("") || inputEmail.getText().equals("") || inputLogin.getText().equals("") || inputPassword.getText().equals("") || inputVPassword.getText().equals("")) {
            erreurLabel.setText("Vérifier que tous les champs sont remplis !");
        } else {
            UserService us = UserService.getInstance();
            if (!us.isValid(inputEmail.getText())) {
                erreurLabel.setText("Vérifier votre adresse Mail !");
            } else {
                if (inputPassword.getText().equals(inputVPassword.getText())) {
                    User u = new User();

                    u.setNom(inputNom.getText());
                    u.setPrenom(inputPrenom.getText());
                    u.setSexe(sexe);
                    u.setDate_naissance(dateN.toString());
                    u.setEmail(inputEmail.getText());
                    u.setLogin(inputLogin.getText());
                    u.setPassword(inputPassword.getText());
                    if (chkFormateur.isSelected()) {
                        us.insertFormateur(u);
                        //Show Dialog of wait approval
                    } else {
                        us.insertEtudiant(u);
                        //redirect to home page
                    }
                    erreurLabel.setText("");
                } else {
                    erreurLabel.setText("Vérifier votre mot de passe !");
                }
            }
        }
    }

    @FXML
    private void handleAuthentifierBtn(ActionEvent event) {

        try {
            Parent page1 = FXMLLoader.load(getClass().getResource("/views/Authentification.fxml"));
            Scene scene = new Scene(page1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(InscriptionController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void genderSelected(ActionEvent event) {
        if (rbHomme.isSelected()) {
            sexe = "Homme";
        }
        if (rbFemme.isSelected()) {
            sexe = "Femme";
        }
    }

}
