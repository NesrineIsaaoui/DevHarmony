package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.CoursCategory;
import service.ServiceCoursCategory;

import java.io.IOException;

public class Modifiercategory {
    @FXML
    private Button Modifer;
    @FXML
    private TextField fx_categoriename;
    @FXML
    private AnchorPane nh;

    private int categoryId; // Nouvelle variable pour stocker l'ID

    public void initialize() {
        // Ne pas initialiser le champ de texte ici
    }

    // Nouvelle méthode pour initialiser l'ID
    public void initData(int categoryId, String oldCoursName) {
        this.categoryId = categoryId;
        fx_categoriename.setText(oldCoursName);
    }

    @FXML
    void Modifier(ActionEvent event) {
        ServiceCoursCategory inter = new ServiceCoursCategory();
        String categoryName = fx_categoriename.getText();
        if (categoryName.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Erreur : veuillez donner un nom de catégorie.");
            alert.show();
        } else {
            CoursCategory A = new CoursCategory(categoryId, categoryName);
            inter.modifierCoursCategory(A);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Information dialog");
            alert.setHeaderText(null);
            alert.setContentText("Catégorie modifiée avec succès !");
            alert.show();
        }
    }

    @FXML
    private void returnToAffiche(MouseEvent event) {
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


}
