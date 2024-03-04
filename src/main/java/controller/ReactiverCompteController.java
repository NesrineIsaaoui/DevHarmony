package controller;

import service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import test.MainFX;

import java.io.IOException;
import java.sql.SQLException;


public class ReactiverCompteController {
    @FXML
    private StackPane stck;
    UserService Us = new UserService();
    private Integer phoneNumber;
    private String email;
    @FXML
    private TextField tfreseactiver;

    public void setPhone(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @FXML
    private void Handlereactiver() throws SQLException {
        Integer pin = Integer.valueOf(tfreseactiver.getText());

        if (tfreseactiver.getText().isEmpty()) {
            showAlert("Empty Field", "Please enter the PIN code.");
            return;
        }

        if (!Us.verifyReactiverAccountCode(email, pin)) {
            showAlert("Incorrect PIN", "The entered PIN does not match. Please try again.");
            return;
        }

        Us.InvertStatus(email);

        try {
            NavPageConnexion();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void NavPageConnexion() throws IOException {
        javafx.scene.Parent menu = FXMLLoader.load(MainFX.class.getResource("/SeConnecter.fxml"));
        stck.getChildren().removeAll();
        stck.getChildren().setAll(menu);
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


}

