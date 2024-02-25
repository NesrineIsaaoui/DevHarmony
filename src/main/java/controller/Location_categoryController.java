package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Location_categoryController {
    @FXML
    private AnchorPane nh;
    @FXML
    private ImageView goToCategory;

    @FXML
    private ImageView goToCours;





    @FXML
    private void goToCategory(MouseEvent event) {
        try {
            FXMLLoader loader = createFXMLLoader("/AfficherCoursCategory2.fxml");
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



    @FXML
    private void goToCours(MouseEvent event) {
        try {
            FXMLLoader loader = createFXMLLoader("/AfficherCours.fxml");
            Parent root = loader.load();
            nh.getChildren().setAll(root);
        } catch (IOException ex) {
            System.out.println("Erreur lors du chargement de la vue : " + ex.getMessage());
        }
    }

}
