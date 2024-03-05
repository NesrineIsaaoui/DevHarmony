package controller;

import javafx.scene.layout.AnchorPane;
import model.User;
import service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import test.MainFX;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static controller.SeconnecterController.idCurrentUser;

public class HomeParentController implements Initializable {
    @FXML
    private Text tnom;
    @FXML
    private Text tprenom;
    @FXML
    private Text trole;
    private User currentUser;
    @FXML
    private StackPane stck;
    @FXML
    private ImageView PreviewImage;

    @FXML
    private AnchorPane nh;
    UserService Us = new UserService();
    @FXML
    private Button btnres;
    @FXML
    private Button btnprofil;
    @FXML
    private Button btncours;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            currentUser = Us.getById(idCurrentUser);
            setUser(currentUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setUser(User user) {
        if (user != null) {
            tnom.setText(user.getNom());
            tprenom.setText(user.getPrenom());
            trole.setText(user.getRole());

            File imageFile = new File("C:\\xampp\\htdocs\\pi\\" + user.getImage());
            Image image = new Image(imageFile.toURI().toString());
            PreviewImage.setImage(image);
        }
    }
    @FXML
    private void NavConnexion(ActionEvent event) throws IOException {
        SeconnecterController.clearRememberedCredentials();
        javafx.scene.Parent menu = FXMLLoader.load(MainFX.class.getResource("/SeConnecter.fxml"));
        stck.getChildren().removeAll();
        stck.getChildren().setAll(menu);
    }
    @FXML
    private void NavEditProfil(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("/EditProfile.fxml"));
        Parent menu = loader.load();
        Scene editProfileScene = new Scene(menu, 1043, 730);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(editProfileScene);
    }

    private FXMLLoader createFXMLLoader(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlPath));
        return loader;
    }
    @FXML
    void goToFrontGestionCours(ActionEvent event) {
        try {
            FXMLLoader loader = createFXMLLoader("/AfficherCoursFront.fxml");
            Parent root = loader.load();
            nh.getChildren().setAll(root);
        } catch (IOException ex) {
            System.out.println("Erreur lors du chargement de la vue : " + ex.getMessage());
        }



    }

    @FXML
    private void gotoreservationslist(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReservationUserView.fxml"));
            Parent root = loader.load();

            // Get the controller of the new FXML file
            ReservationViewUserController reservationController = loader.getController();

            // Set the user ID in the ReservationViewUserController
            reservationController.setLoggedInUserId(idCurrentUser);

            stck.getChildren().setAll(root);
        } catch (IOException ex) {
            System.out.println("Error loading the view: " + ex.getMessage());
        }
    }
}
