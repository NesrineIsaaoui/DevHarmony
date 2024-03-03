package edu.esprit.pi.controller;

import edu.esprit.pi.MainFx;
import edu.esprit.pi.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.sql.SQLException;

public class ConfirmerMailController {
    @FXML
    TextField tfcodeConfirmEmail;
    @FXML
    private StackPane stck;
    UserService us = new UserService();
    private String email;
    public void setEmail(String email) {
        this.email = email;
    }
    @FXML
    private void HandleConfirmCode() {
        String code = tfcodeConfirmEmail.getText();
        System.out.println("Button Confirmer clicked");
        System.out.println("info "+email +" "+code);
        try {
            if (us.verifyConfirmationCode(email, code)) {
                us.updateCodeConfirmEmailStatus(email);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirm Email");
                alert.setContentText("Email has Confirmed");
                alert.setHeaderText("Email Confirmed");
                alert.showAndWait();
                javafx.scene.Parent menu = FXMLLoader.load(MainFx.class.getResource("fxml/SeConnecter.fxml"));
                stck.getChildren().removeAll();
                stck.getChildren().setAll(menu);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText("Code incorrect");
                alert.setHeaderText("Warning Alert");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void NavPageConnexion() throws IOException {
        javafx.scene.Parent menu = FXMLLoader.load(MainFx.class.getResource("fxml/SeConnecter.fxml"));
        stck.getChildren().removeAll();
        stck.getChildren().setAll(menu);
    }
}
