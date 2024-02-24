package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.CoursCategory;
import services.ServiceCoursCategory;

import java.io.IOException;

public class AjouterCoursCategory {

    @FXML
    private Button ajouter_categorie;

    @FXML
    private TextField fx_categorie;

    @FXML
    private AnchorPane nh;
    @FXML
    private ImageView returnTo;

    @FXML
    private ImageView returnToAffiche;
    @FXML
    void ajouter_categorie(ActionEvent event) {
        String categoryName=fx_categorie.getText();

        if (categoryName.length()==0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("information Dialog");
            alert.setHeaderText(null);

            alert.setContentText("erreur donner category name");
            alert.show();

        }
        else
        {
            CoursCategory C = new CoursCategory(categoryName);
            ServiceCoursCategory c1 = new ServiceCoursCategory();
            c1.ajouterCoursCategory(C);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert .setTitle("information dialog");
            alert.setHeaderText(null);
            alert.setContentText("Category ajouter avec succes !");
            alert.show();
        }

    }


    @FXML
    private void returnToAffiche(MouseEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCoursCategory.fxml"));
        try {
            Parent root = loader.load();
            nh.getChildren().setAll(root);

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }


}
