package edu.esprit.pi.controller;

import edu.esprit.pi.MainFx;
import edu.esprit.pi.model.User;
import edu.esprit.pi.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.prefs.Preferences;
import java.sql.SQLException;
import java.util.Optional;

public class SeconnecterController {
    @FXML
    private TextField tfemail;
    @FXML
    private PasswordField tfmdp;
    @FXML
    private Button Seconnecterbtn2;
    @FXML
    private  Button btnlogin;
    @FXML
    private Button btnconfirmer;
    @FXML
    private Button btnCnxGmail;
    @FXML
    private CheckBox checkRememberMe;
    public static int idCurrentUser;
    public static String roleCurrentUser;
    private User CurrentUser;
    UserService Us = new UserService();
    @FXML
    private StackPane stck;

    public void setData(User us) {
        this.CurrentUser = us;
        tfemail.setText(us.getEmail());
        tfmdp.setText(us.getPwd());

    }
    @FXML
    private void NavPageInscription(ActionEvent event) throws IOException {
        javafx.scene.Parent menu = FXMLLoader.load(MainFx.class.getResource("fxml/Inscription.fxml"));
        stck.getChildren().removeAll();
        stck.getChildren().setAll(menu);
    }
    public boolean showConfirmationDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    Preferences preferences;
    boolean rememberpreference;
    public void rememberMe(){
        preferences=Preferences.userNodeForPackage(this.getClass());
        rememberpreference= preferences.getBoolean("rememberMe",Boolean.valueOf(""));
        if (rememberpreference){
            tfemail.setText(preferences.get("email",""));
            tfmdp.setText(preferences.get("pwd",""));
            checkRememberMe.setSelected(rememberpreference);

        }

    }

    @FXML
    void submit(ActionEvent event) throws SQLException, IOException {
        if (tfemail.getText().equals("") || tfmdp.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champ vide");
            alert.setHeaderText("Tous les champs sont requis");
            alert.showAndWait();
            System.out.println("1");
            return;
        }

        User u = Us.getByEmail(tfemail.getText());

        if (u != null && BCrypt.checkpw(tfmdp.getText(), u.getPwd())) {
            idCurrentUser = u.getId();
            roleCurrentUser = u.getRole();
            rememberMe();
            if (u.getStatus().equals("Desactive")) {
                if (showConfirmationDialog("Your account is currently deactivated. Do you want to reactivate and log in?")) {
                    Us.InvertStatus(u.getEmail());
                }
            }if (checkRememberMe.isSelected() && !rememberpreference){
                preferences.put("email",tfemail.getText());
                preferences.put("pwd",tfmdp.getText());
                preferences.putBoolean("rememberMe",true);

            } else if (!checkRememberMe.isSelected() && rememberpreference) {
                preferences.put("email","");
                preferences.put("pwd","");
                preferences.putBoolean("rememberMe",false);
                
            } else {
                if (u.getRole().equals("Parent")) {
                    navigateToHome("fxml/HomeParent.fxml");
                } else if (u.getRole().equals("Eleve")) {
                    navigateToHome("fxml/HomeEleve.fxml");
                } else if (u.getRole().equals("Enseignant")) {
                    navigateToHome("fxml/HomeEnseignant.fxml");
                }
            }
        } else {
            // Password verification failed
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champ invalid");
            alert.setHeaderText("L'un des champs est incorrect");
            alert.showAndWait();
            System.out.println("2");
        }

    }

    private void navigateToHome(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource(fxmlPath));
        Parent menu = loader.load();
        stck.getChildren().removeAll();
        stck.getChildren().setAll(menu);
    }

    @FXML
    private void NavForgetPass(MouseEvent event) throws IOException {
        javafx.scene.Parent menu = FXMLLoader.load(MainFx.class.getResource("fxml/ForgetPass.fxml"));
        stck.getChildren().removeAll();
        stck.getChildren().setAll(menu);
    }



}
