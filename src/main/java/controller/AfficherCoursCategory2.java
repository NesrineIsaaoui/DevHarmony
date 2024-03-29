package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import model.CoursCategory;
import service.ServiceCoursCategory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AfficherCoursCategory2 implements Initializable {
    @FXML
    private ImageView returnToAffiche;
    @FXML
    private VBox vbox1;
    @FXML
    private Button returnToAdd;
    @FXML
    private AnchorPane nh;
    ServiceCoursCategory scom = new ServiceCoursCategory();
    CoursCategory coursCategory;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<CoursCategory> coursCategories = scom.afficherCoursCategory();
        vbox1.setFillWidth(true);

        for (CoursCategory coursCategory : coursCategories) {
            Pane pane = new Pane();
            pane.setPrefHeight(62.0);
            pane.setMinHeight(62.0);
            pane.setPrefWidth(840.0);

            Label label1 = new Label("ID: " + coursCategory.getId());
            label1.setLayoutX(15.0);
            label1.setLayoutY(18.0);
            label1.setFont(new Font(14.0));

            Label label2 = new Label(coursCategory.getCategoryName());
            label2.setLayoutX(150.0);
            label2.setLayoutY(18.0);
            label2.setPrefHeight(25.0);
            label2.setPrefWidth(200.0);
            label2.setFont(new Font(16.0));

            ImageView imageView1 = new ImageView();
            Image image1 = new Image(getClass().getResourceAsStream("/poubelle.gif"));
            imageView1.setImage(image1);
            imageView1.setFitHeight(25.0);
            imageView1.setFitWidth(25.0);
            imageView1.setLayoutX(280);
            imageView1.setLayoutY(20.0);
            imageView1.setPickOnBounds(true);
            imageView1.setPreserveRatio(true);
            imageView1.setOnMouseClicked((MouseEvent event) -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmer la suppression");
                alert.setHeaderText("Êtes-vous sûr de supprimer cette categorie ?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    Pane parent = (Pane) pane.getParent();
                    scom.supprimerCoursCategory(coursCategory.getId());
                    coursCategories.remove(coursCategory);
                    parent.getChildren().remove(pane);
                }
            });

            ImageView imageView2 = new ImageView();
            Image image2 = new Image(getClass().getResourceAsStream("/redaction.gif"));
            imageView2.setImage(image2);
            imageView2.setFitHeight(25.0);
            imageView2.setFitWidth(25.0);
            imageView2.setLayoutX(300);
            imageView2.setLayoutY(20.0);
            imageView2.setPickOnBounds(true);
            imageView2.setPreserveRatio(true);
            imageView2.setOnMouseClicked((MouseEvent event) -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Modifiercategory.fxml"));
                    Parent root = loader.load();

                    // Récupérer le contrôleur de la fenêtre de modification
                    Modifiercategory modifierController = loader.getController();

                    // Transmettre les données nécessaires au contrôleur de modification
                    modifierController.initData(coursCategory.getId(), coursCategory.getCategoryName());

                    // Changer la scène
                    vbox1.getScene().setRoot(root);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            Line line = new Line();
            line.setStrokeWidth(0.4);
            line.setStartX(-411.0);
            line.setStartY(9.400012969970703);
            line.setEndX(429.0);
            line.setEndY(9.400012969970703);
            line.setLayoutX(250.0);
            line.setLayoutY(53.0);

            pane.getChildren().addAll(label1, label2, imageView1, imageView2, line);
            vbox1.getChildren().add(pane);
        }
        vbox1.setSpacing(5);
    }

    @FXML
    private void returnToAffiche(MouseEvent event) {
        try {
            FXMLLoader loader = createFXMLLoader("/Location_category.fxml");
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
    void returnToAdd(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCoursCategory.fxml"));
        try {
            Parent root = loader.load();
            nh.getChildren().setAll(root);

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
