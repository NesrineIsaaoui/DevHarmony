package controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import model.User;
import service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.mindrot.jbcrypt.BCrypt;
import test.MainFX;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;
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
    private  String ACCOUNT_SID = "AC50977c3984b064ffb395e2b40e287ed9";
    private  String AUTH_TOKEN = "6d280075529319b45a9a1c0d3fdaf2b6";
    private  String TWILIO_PHONE_NUMBER = "+16178602915";
    @FXML
    private void initialize() {
        tfemail.setText(getRememberedEmail());
        tfmdp.setText(getRememberedPassword());
    }

    public void setData(User us) {
        this.CurrentUser = us;
        tfemail.setText(us.getEmail());
        tfmdp.setText(us.getPwd());
    }

    @FXML
    private void NavPageInscription(ActionEvent event) throws IOException {
        javafx.scene.Parent menu = FXMLLoader.load(MainFX.class.getResource("/Inscription.fxml"));
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

    private void rememberCredentials(String email, String password) {
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        prefs.put("email", email);
        prefs.put("password", password);
    }
    public static String getRememberedEmail() {
        Preferences prefs = Preferences.userNodeForPackage(SeconnecterController.class);
        return prefs.get("email", "");
    }
    public static String getRememberedPassword() {
        Preferences prefs = Preferences.userNodeForPackage(SeconnecterController.class);
        return prefs.get("password", "");
    }
    public static void clearRememberedCredentials() {
        Preferences prefs = Preferences.userNodeForPackage(SeconnecterController.class);
        prefs.remove("email");
        prefs.remove("password");
    }

    @FXML
    void submit(ActionEvent event) throws SQLException, IOException {
        if (tfemail.getText().isEmpty() || tfmdp.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champ vide");
            alert.setHeaderText("Tous les champs sont requis");
            alert.showAndWait();
            return;
        }

        User user = Us.getByEmail(tfemail.getText());

        if (user != null && user.getPwd() != null  && BCrypt.checkpw(tfmdp.getText(), user.getPwd())) {
            if (user.getStatus().equals("Desactive")) {
                System.out.println("desavtive");
                Alert reactivateAlert = new Alert(Alert.AlertType.CONFIRMATION);
                reactivateAlert.setTitle("Account Deactivated");
                reactivateAlert.setHeaderText("Your account is currently deactivated. Do you want to reactivate and log in?");
                Optional<ButtonType> result = reactivateAlert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    sendVerificationCode(user.getEmail(),user.getNum_tel());
                    FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("/ReactiverCompte.fxml"));
                    Parent menu = loader.load();
                    ReactiverCompteController reactiverController = loader.getController();
                    reactiverController.setEmail(tfemail.getText());
                    reactiverController.setPhone(user.getNum_tel());
                    stck.getChildren().removeAll();
                    stck.getChildren().setAll(menu);
                    return;
                } else {
                    return;
                }
            }
            if (Objects.equals(user.getConfirmCode(),"verified")) {
                idCurrentUser = user.getId();
                roleCurrentUser = user.getRole();
                if (checkRememberMe.isSelected()) {
                    rememberCredentials(tfemail.getText(), tfmdp.getText());
                }
                    // Navigate to the appropriate home based on the user's role
                    if (user.getRole().equals("Parent")) {
                        navigateToHome("/HomeParent.fxml");
                    } else if (user.getRole().equals("Eleve")) {
                        navigateToHome("/HomeEleve.fxml");
                    } else if (user.getRole().equals("Enseignant")) {
                        navigateToHome("/HomeEnseignant.fxml");
                    }

            } else {
                Alert confirmEmailAlert = new Alert(Alert.AlertType.WARNING);
                confirmEmailAlert.setTitle("Email Not Confirmed");
                confirmEmailAlert.setHeaderText("Please confirm your email before logging in.");
                confirmEmailAlert.show();

                FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("/ConfirmerMail.fxml"));
                Parent confirmEmailParent = loader.load();
                Scene confirmEmailScene = new Scene(confirmEmailParent, 1043, 730);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(confirmEmailScene);
                ConfirmerMailController controller = loader.getController();
                controller.setEmail(user.getEmail());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champ invalid");
            alert.setHeaderText("L'un des champs est incorrect");
            alert.showAndWait();
        }
    }

    public void sendVerificationCode(String email,Integer phoneNumber) {
        int randomcode = new Random().nextInt(900000) + 100000;

        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message message = Message.creator(
                            new PhoneNumber("+216" + phoneNumber),
                            new PhoneNumber(TWILIO_PHONE_NUMBER),
                            "Your verification code: " + randomcode)
                    .create();
            System.out.println("Message SID: " + message.getSid());
            Us.storeCodeReactiverAccount(email,randomcode);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to send message.");
        }
    }

    private void navigateToHome(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainFX.class.getResource(fxmlPath));
        Parent menu = loader.load();
        stck.getChildren().removeAll();
        stck.getChildren().setAll(menu);
    }

    @FXML
    private void NavForgetPass(MouseEvent event) throws IOException {
        javafx.scene.Parent menu = FXMLLoader.load(MainFX.class.getResource("/ForgetPass.fxml"));
        stck.getChildren().removeAll();
        stck.getChildren().setAll(menu);
    }


}
