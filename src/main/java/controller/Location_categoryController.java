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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCoursCategory.fxml"));
        try {
            Parent root = loader.load();
            nh.getChildren().setAll(root);

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void goToCours(MouseEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCours.fxml"));
        try {
            Parent root = loader.load();
            nh.getChildren().setAll(root);

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
