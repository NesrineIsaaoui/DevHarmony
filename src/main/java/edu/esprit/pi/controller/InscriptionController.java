package edu.esprit.pi.controller;

import edu.esprit.pi.MainFx;
import edu.esprit.pi.model.Eleve;
import edu.esprit.pi.model.Enseigant;
import edu.esprit.pi.model.Parent;
import edu.esprit.pi.model.User;
import edu.esprit.pi.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

public class InscriptionController {
    UserService us = new UserService();

    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfemail;
    @FXML
    private PasswordField tfmdp;
    @FXML
    private PasswordField tfconfirmmdp;
    @FXML
    private RadioButton rbtEnseignant;

    @FXML
    private RadioButton rbtEleve;

    @FXML
    private RadioButton rbtParent;

    @FXML
    private Button btnconfirmer;
    @FXML
    private Button btnSeconnecter;

    @FXML
    private StackPane stck;

    @FXML
    void NavPageLogin(ActionEvent event) throws IOException {
        javafx.scene.Parent menu = FXMLLoader.load(MainFx.class.getResource("fxml/SeConnecter.fxml"));
        stck.getChildren().removeAll();
        stck.getChildren().setAll(menu);
    }


    @FXML
    void inscrire(javafx.event.ActionEvent event) throws SQLException {
        if (tfemail.getText().equals("")
                || tfnom.getText().equals("")
                || tfprenom.getText().equals("")
                || tfmdp.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Contrôle de saisie");
            alert.setContentText("vous pouvez les remplir soigneusement");
            alert.setHeaderText("Tous les champs sont requis");
            alert.showAndWait();
            tfnom.setText("");
            tfprenom.setText("");
            tfemail.setText("");
            tfmdp.setText("");
            tfconfirmmdp.setText("");
            return;
        }
        if (!tfemail.getText().contains("@") || !tfemail.getText().contains(".")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Contrôle de saisie");
            alert.setContentText("Respecter format mail");
            alert.setHeaderText("Warning Alert");
            alert.showAndWait();
            tfnom.setText("");
            tfprenom.setText("");
            tfemail.setText("");
            tfmdp.setText("");
            tfconfirmmdp.setText("");
            return;
        }
        if (tfmdp.getText().length() < 8) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Contrôle de saisie");
            alert.setContentText("le Mot de passe doit contenir au moins 9 caractères");
            alert.setHeaderText("Warning Alert");
            alert.showAndWait();
            tfnom.setText("");
            tfprenom.setText("");
            tfemail.setText("");
            tfmdp.setText("");
            tfconfirmmdp.setText("");
            return;
        }
        if (!tfmdp.getText().equals(tfconfirmmdp.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("mots de passe ne correspondent pas");
            alert.setContentText("mot de passe et confirmer le mot de mot de passe devraient avoir le même contenu");
            alert.setHeaderText("Warning Alert");
            alert.showAndWait();
            tfnom.setText("");
            tfprenom.setText("");
            tfemail.setText("");
            tfmdp.setText("");
            tfconfirmmdp.setText("");
            return;
        }
        if (us.userExist(tfemail.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("l'utilisateur existe déjà");
            alert.setContentText("Il y a déjà un utilisateur avec cet e-mail");
            alert.setHeaderText("Warning Alert");
            alert.showAndWait();
            tfnom.setText("");
            tfprenom.setText("");
            tfemail.setText("");
            tfmdp.setText("");
            tfconfirmmdp.setText("");
            return;
        }
        String role;
        User u1;
        if (rbtEnseignant.isSelected()) {
            role = "Enseignant";
            u1 = new Enseigant(tfnom.getText(), tfprenom.getText(), role, tfemail.getText(), tfmdp.getText());
            us.addEnseignant((Enseigant) u1);

        } else if (rbtParent.isSelected()) {
            role = "Parent";
            u1 = new Parent(tfnom.getText(), tfprenom.getText(), role, tfemail.getText(), tfmdp.getText());
            us.addParent((Parent) u1);
        } else {
            role = "Eleve";
            u1 = new Eleve(tfnom.getText(), tfprenom.getText(), role, tfemail.getText(), tfmdp.getText());
            us.addEleve((Eleve) u1);
        }
        //Confirmer Mail
        SendConfirmEmailCode(tfemail.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Validation E-mail");
        alert.setHeaderText("Check your email please ");
        alert.show();
        try {
            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("fxml/ConfirmerMail.fxml"));
            javafx.scene.Parent menu = loader.load();
            ConfirmerMailController confirmerMailController = loader.getController();
            confirmerMailController.setEmail(tfemail.getText());
            Scene ConfirmerMailController = new Scene(menu, 1043, 730);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(ConfirmerMailController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void SendConfirmEmailCode(String recipientEmail) {
        int randomcode = new Random().nextInt(900000) + 100000;
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
            message.setSubject("Confirm Email Request");
            message.setText("Code: " + randomcode);
            Transport.send(message);

            us.storeCodeConfirmEmailInDatabase(recipientEmail, String.valueOf(randomcode));

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void ConnectionWithGmail() {

    }


}
