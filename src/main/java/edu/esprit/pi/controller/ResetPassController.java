package edu.esprit.pi.controller;

import edu.esprit.pi.MainFx;
import edu.esprit.pi.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ResetPassController implements Initializable {
    @FXML
    private StackPane stck;
    @FXML
    private TextField tfresetcode;

    @FXML
    private PasswordField tfmdp;

    @FXML
    private PasswordField tfconfirmmdp;
    private String userEmail;

    private UserService us = new UserService();

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void NavPageConnexion(MouseEvent event) throws IOException {
        javafx.scene.Parent menu = FXMLLoader.load(MainFx.class.getResource("fxml/SeConnecter.fxml"));
        stck.getChildren().removeAll();
        stck.getChildren().setAll(menu);
    }

    @FXML
    void HandleChangePass(ActionEvent event) {
        String resetCode = tfresetcode.getText();
        String newPassword = tfmdp.getText();
        String confirmPassword = tfconfirmmdp.getText();

        try {
            // Check if the reset code matches the one stored in the database
            if (!us.checkResetCode(userEmail, Integer.parseInt(resetCode))) {
                showAlert("Invalid Reset Code", "The provided reset code is not valid.");
                return;
            }

            // Check if the new password and confirm password match
            if (!newPassword.equals(confirmPassword)) {
                showAlert("Password Mismatch", "New password and confirm password do not match.");
                return;
            }

            us.updatePassword(userEmail, newPassword);

            showAlertSuccess("Password Changed", "Your password has been successfully changed.");

            // Navigate back to the login page
            javafx.scene.Parent menu = FXMLLoader.load(MainFx.class.getResource("fxml/SeConnecter.fxml"));
            stck.getChildren().removeAll();
            stck.getChildren().setAll(menu);

        } catch (NumberFormatException e) {
            showAlert("Invalid Reset Code", "Please enter a valid numeric reset code.");
        } catch (IOException e) {
            showAlert("Error", "An error occurred while processing your request.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void showAlertSuccess(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void HandleConfirmCode(ActionEvent actionEvent) {
    }
}
