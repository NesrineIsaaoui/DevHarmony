package edu.esprit.pi.controller;

import edu.esprit.pi.MainFx;
import edu.esprit.pi.model.User;
import edu.esprit.pi.service.UserService;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static edu.esprit.pi.controller.SeconnecterController.idCurrentUser;

public class HomeEleveController implements Initializable {
    @FXML
    private ImageView imageview;

    @FXML
    private Text tnom;

    @FXML
    private Text tprenom;

    @FXML
    private Text trole;

    @FXML
    private Button bprofile;

    @FXML
    private Button bcour;

    @FXML
    private Button bdéconnect;

    @FXML
    private VBox recherche;
    @FXML
    private StackPane stck;
    @FXML
    private ImageView PreviewImage;
    UserService Us = new UserService();
    private User currentUser;

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
        javafx.scene.Parent menu = FXMLLoader.load(MainFx.class.getResource("fxml/SeConnecter.fxml"));
        stck.getChildren().removeAll();
        stck.getChildren().setAll(menu);
    }
    @FXML
    private void NavEditProfil(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("fxml/EditProfile.fxml"));
        Parent menu = loader.load();
        Scene editProfileScene = new Scene(menu, 1043, 730);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(editProfileScene);
    }

}
