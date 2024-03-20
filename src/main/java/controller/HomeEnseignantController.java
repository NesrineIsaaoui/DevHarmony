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

public class HomeEnseignantController implements Initializable {

    @FXML
    private Text tnom;

    @FXML
    private Text tprenom;

    @FXML
    private Text trole;

    @FXML
    private AnchorPane nh;

    @FXML
    private StackPane stck;
    @FXML
    private ImageView PreviewImage;
    UserService Us = new UserService();
    private User currentUser;
    @FXML
    private Button btnprofil;
    @FXML
    private Button btncommunity1;
    @FXML
    private Button btnquiz;
    @FXML
    private Button btncommunity;
    @FXML
    private Button btncours;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Id ===>>> "+idCurrentUser);
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
        //stage.centerOnScreen();
    }


    @FXML
    void goToBackGestionCours(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Location_category.fxml"));
        try {
            Parent root = loader.load();
            nh.getChildren().setAll(root);

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }


    @FXML
    public void gotoreservationback(ActionEvent actionEvent) throws IOException{

        try {
            URL fxmlUrl = getClass().getResource("/updatereservation.fxml");
            System.out.println("FXML URL: " + fxmlUrl);
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the current stage
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("EDUWAVE");

            // Set the new scene in the stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void blog(ActionEvent actionEvent) {

        try {
            URL fxmlUrl = getClass().getResource("/Blog-view.fxml");
            System.out.println("FXML URL: " + fxmlUrl);
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the current stage
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("EDUWAVE");

            // Set the new scene in the stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
