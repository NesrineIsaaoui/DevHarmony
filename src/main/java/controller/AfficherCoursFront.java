package controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import models.Cours;
import services.ServiceCours;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherCoursFront implements Initializable {
    ServiceCours sa = new ServiceCours();

    @FXML
    private VBox vbox1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Cours> coursList = sa.afficherCours();

        // Ajouter une marge entre les cours
        vbox1.setSpacing(10);

        // Centrer la VBox dans le conteneur parent
        vbox1.setAlignment(Pos.CENTER);

        for (Cours cours : coursList) {
            Pane articlePane = createArticlePane(cours);
            vbox1.getChildren().add(articlePane);
        }
    }

    private Pane createArticlePane(Cours cours) {
        Pane articlePane = new Pane();
        articlePane.setPrefSize(450.0, 395.0);
        articlePane.setMinHeight(360);
        articlePane.setMaxWidth(430);
        articlePane.setStyle("-fx-background-color: #f8f5f5; -fx-background-radius: 10; -fx-padding:10px;");

        DropShadow shadow = new DropShadow();
        shadow.setRadius(10.0);
        shadow.setColor(Color.gray(0.5));
        shadow.setOffsetX(2.0);
        shadow.setOffsetY(2.0);

        articlePane.setEffect(shadow);

        ImageView image = createArticleImage(cours);
        Label title = createArticleTitle(cours);
        Label price = createArticlePrice(cours);

        articlePane.getChildren().addAll(image, title, price);

        // Ajouter une marge entre les cours
        VBox.setMargin(articlePane, new Insets(0, 0, 10, 0));

        return articlePane;
    }

    private ImageView createArticleImage(Cours cours) {
        ImageView image = new ImageView();
        image.setFitHeight(218.0);
        image.setFitWidth(287.0);
        image.setLayoutX(78);
        image.setLayoutY(90.0);
        image.setPickOnBounds(true);
        image.setPreserveRatio(true);

        try {
            File uploadedFile = new File(cours.getCoursImage());
            if (uploadedFile.exists()) {
                String fileUrl = uploadedFile.toURI().toString();
                Image imageSource = new Image(fileUrl);
                image.setImage(imageSource);
            } else {
                System.out.println("Le fichier image n'existe pas : " + cours.getCoursImage());
            }
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement de l'image : " + e.getMessage());
        }

        return image;
    }

    private Label createArticleTitle(Cours cours) {
        Label title = new Label();
        title.setLayoutX(150.0);
        title.setLayoutY(47.0);
        title.setText(cours.getCoursName());
        title.setFont(Font.font("titleFont", FontWeight.BOLD, 23));
        title.setAlignment(Pos.CENTER);
        return title;
    }

    private Label createArticlePrice(Cours cours) {
        Label price = new Label();
        price.setLayoutX(280.0);
        price.setLayoutY(325.0);
        price.setText("PRIX : " + (float) cours.getCoursPrix() + " DT");
        price.setFont(Font.font("priceFont", FontWeight.BOLD, 20));
        return price;
    }
}
