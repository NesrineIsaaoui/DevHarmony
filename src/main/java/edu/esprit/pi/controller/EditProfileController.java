package edu.esprit.pi.controller;

import edu.esprit.pi.MainFx;
import edu.esprit.pi.model.User;
import edu.esprit.pi.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static edu.esprit.pi.controller.SeconnecterController.idCurrentUser;
import static edu.esprit.pi.controller.SeconnecterController.roleCurrentUser;

public class EditProfileController implements Initializable {

    @FXML
    private ImageView PreviewImage;
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfphone;
    @FXML
    private PasswordField tfmdp;
    @FXML
    private PasswordField tfconfirmdp;
    private File selectedImageFile;
    private static User currentUser;
    UserService Us = new UserService();
    @FXML
    private Text tdNomaffichage;
    @FXML
    private Text tfRoleAffichage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            currentUser = Us.getById(idCurrentUser);
            setUserData(currentUser);
            tfRoleAffichage.setText(currentUser.getRole());
            tdNomaffichage.setText(currentUser.getNom());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setUserData(User user) {
        if (user != null) {
            tfnom.setText(user.getNom());
            tfprenom.setText(user.getPrenom());
            tfphone.setText(String.valueOf(user.getNum_tel()));
            // Load and set image
            File imageFile = new File("C:\\xampp\\htdocs\\pi\\" + user.getImage());
            Image image = new Image(imageFile.toURI().toString());
            PreviewImage.setImage(image);
        }
    }

    @FXML
    private void handleImageUpload(ActionEvent event) throws IOException {
        File dest = new File("C:\\xampp\\htdocs\\pi");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        selectedImageFile = fileChooser.showOpenDialog(null);
        if (selectedImageFile != null) {
            FileUtils.copyFileToDirectory(selectedImageFile, dest);
            Image image = new Image(selectedImageFile.toURI().toString());
            PreviewImage.setImage(image);
        }
    }

    @FXML
    private void DesactiverProfile(ActionEvent event) {
        try {
            Us.InvertStatus(currentUser.getEmail());
            currentUser = Us.getByEmail(currentUser.getEmail());
            setUserData(currentUser);
            navigateToHome("fxml/SeConnecter.fxml");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void updateProfile(ActionEvent event) {
        if (tfnom.getText().isEmpty() || tfprenom.getText().isEmpty() || tfphone.getText().isEmpty() || tfmdp.getText().isEmpty() || tfconfirmdp.getText().isEmpty()) {
            showAlert("Please fill in all required fields.");
            return;
        }

        try {
            User updatedUser = new User();
            updatedUser.setNom(tfnom.getText());
            updatedUser.setPrenom(tfprenom.getText());
            updatedUser.setEmail(currentUser.getEmail());
            updatedUser.setNum_tel(Integer.parseInt(tfphone.getText()));
            updatedUser.setPwd(tfmdp.getText());
            if (selectedImageFile != null) {
                File dest = new File("C:\\xampp\\htdocs\\pi");
                FileUtils.copyFileToDirectory(selectedImageFile, dest);
                updatedUser.setImage(selectedImageFile.getName());
            }
            Us.update(updatedUser, currentUser.getEmail());
            currentUser = Us.getByEmail(currentUser.getEmail());
            setUserData(currentUser);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid phone number format.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void HandleBack(ActionEvent event) throws IOException {
        if (roleCurrentUser.equals("Parent")) {
            navigateToHome("fxml/HomeParent.fxml");
        } else if (roleCurrentUser.equals("Eleve")) {
            navigateToHome("fxml/HomeEleve.fxml");
        } else if (roleCurrentUser.equals("Enseignant")) {
            navigateToHome("fxml/HomeEnseignant.fxml");
        }
    }

    private void navigateToHome(String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(MainFx.class.getResource(fxmlPath));
        Scene scene = new Scene(root);
        Stage stage = (Stage) tfnom.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
