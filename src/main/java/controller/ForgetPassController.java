package controller;

import service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import test.MainFX;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

public class ForgetPassController {

    @FXML
    private StackPane stck;

    @FXML
    private TextField tfemail;

    private UserService us = new UserService();

    @FXML
    private void HandleSend(ActionEvent event) {
        String recipientEmail = tfemail.getText();

        try {
            if (!us.userExist(recipientEmail)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("User Not Found");
                alert.setHeaderText("User with the provided email does not exist.");
                alert.showAndWait();
                return;
            }

            int randomCode = generateRandomCode();

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("liliajemai2@gmail.com", "corr oldg pptm lmjh");
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("liliajemai2@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
                message.setSubject("Password Reset Request");
                message.setText("Code: " + randomCode);
                Transport.send(message);

                us.storeRandomCodeInDatabase(recipientEmail, randomCode);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Password Reset Email Sent");
                alert.setHeaderText("Check your email Now.");
                alert.show();

                FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("/ResetPass.fxml"));
                javafx.scene.Parent menu = loader.load();
                ResetPassController resetPassController = loader.getController();
                resetPassController.setUserEmail(recipientEmail);
                stck.getChildren().removeAll();
                stck.getChildren().setAll(menu);

            } catch (MessagingException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Sending Email");
                alert.setHeaderText("Failed to send password reset email.");
                alert.setContentText("Please check your internet connection and try again.");
                alert.showAndWait();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int generateRandomCode() {
        // Generate a random 6-digit code
        return new Random().nextInt(900000) + 100000;
    }

    @FXML
    private void NavPageConnexion(MouseEvent event) throws IOException {
        javafx.scene.Parent menu = FXMLLoader.load(MainFX.class.getResource("/SeConnecter.fxml"));
        stck.getChildren().removeAll();
        stck.getChildren().setAll(menu);
    }

}
