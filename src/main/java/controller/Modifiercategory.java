package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.CoursCategory;
import services.ServiceCoursCategory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Modifiercategory {
    private ListView<CoursCategory> affichercategorie;
    @FXML
    private ImageView returnToAffiche;
    @FXML
    private Button Modifer;

    @FXML
    private TextField fx_categoriename;

    @FXML
    private AnchorPane nh;
    @FXML
    private Button returnTo;
    public void initialize(URL url, ResourceBundle rb) {
        fx_categoriename.setText(AfficherCoursCategory.categoryName);
    }
    @FXML
    void Modifier(ActionEvent event) {
        ServiceCoursCategory inter = new ServiceCoursCategory();
        String categoryName = fx_categoriename.getText();
        if (categoryName.length()==0)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("information Dialog");
            alert.setHeaderText(null);

            alert.setContentText("erreur donner category name");
            alert.show();

        }else{

            CoursCategory A = new CoursCategory(AfficherCoursCategory.id,categoryName);

            inter.modifierCoursCategory(A);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert .setTitle("information dialog");
            alert.setHeaderText(null);
            alert.setContentText("Category modifier avec succes !");
            alert.show();
        }
    }






    @FXML
    private void returnToAffiche(MouseEvent event) {
        try {
            FXMLLoader loader = createFXMLLoader("/AfficherCoursCategory.fxml");
            Parent root = loader.load();
            nh.getChildren().setAll(root);
        } catch (IOException ex) {
            System.out.println("Erreur lors du chargement de la vue : " + ex.getMessage());
        }
    }
    private FXMLLoader createFXMLLoader(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlPath));
        return loader;
    }


}
