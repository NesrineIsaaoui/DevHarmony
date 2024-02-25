package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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

        vbox1.setSpacing(10);
        vbox1.setAlignment(Pos.CENTER);

        int coursCount = coursList.size();
        int coursesPerRow = 3;

        for (int i = 0; i < coursCount; i += coursesPerRow) {
            HBox row = createRow();

            for (int j = i; j < Math.min(i + coursesPerRow, coursCount); j++) {
                Cours cours = coursList.get(j);
                BorderPane coursePane = createCoursePane(cours);
                row.getChildren().add(coursePane);
            }

            vbox1.getChildren().add(row);
        }
    }

    private HBox createRow() {
        HBox row = new HBox();
        row.setSpacing(10);
        row.setAlignment(Pos.CENTER);
        return row;
    }

    private BorderPane createCoursePane(Cours cours) {
        BorderPane coursePane = new BorderPane();
        coursePane.getStyleClass().add("course-pane");
        coursePane.setPrefSize(200, 300);
        coursePane.setPadding(new Insets(10));
        coursePane.setEffect(new DropShadow(5.0, Color.gray(0.5)));

        Label courseNameLabel = new Label(cours.getCoursName());
        courseNameLabel.getStyleClass().add("course-name-label");

        ImageView image = createArticleImage(cours);
        Label price = createArticlePrice(cours);

        VBox imageAndPriceContainer = new VBox(image, price);
        imageAndPriceContainer.setAlignment(Pos.CENTER);

        coursePane.setTop(courseNameLabel);
        coursePane.setAlignment(courseNameLabel, Pos.CENTER);
        coursePane.setCenter(imageAndPriceContainer);

        ImageView reservationImageView = createCartImageView(cours);
        coursePane.setBottom(reservationImageView);
        coursePane.setAlignment(reservationImageView, Pos.BOTTOM_RIGHT);

        VBox.setMargin(coursePane, new Insets(0, 0, 10, 0));

        return coursePane;
    }

    private ImageView createArticleImage(Cours cours) {
        ImageView image = new ImageView();
        image.setFitHeight(150.0);
        image.setFitWidth(200.0);
        image.setPreserveRatio(true);

        try {
            File uploadedFile = new File(cours.getCoursImage());
            String fileUrl = uploadedFile.toURI().toString();
            Image imageSource = new Image(fileUrl);
            image.setImage(imageSource);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de l'image pour : " + cours.getCoursImage());
        }

        return image;
    }

    private Label createArticlePrice(Cours cours) {
        Label price = new Label();
        price.setText("PRIX : " + (float) cours.getCoursPrix() + " DT");
        price.getStyleClass().add("price-label");
        return price;
    }

    private ImageView createCartImageView(Cours cours) {
        ImageView cartImageView = new ImageView();
        cartImageView.setFitHeight(30.0);
        cartImageView.setFitWidth(30.0);
        cartImageView.setPreserveRatio(true);

        Image cartImage = new Image(getClass().getResourceAsStream("/reservation.png"));
        cartImageView.setImage(cartImage);

        Tooltip tooltip = new Tooltip("Réserver le cours");
        Tooltip.install(cartImageView, tooltip);

        cartImageView.setOnMouseClicked(event -> handleCartClick(cours));

        return cartImageView;
    }

    private void handleCartClick(Cours cours) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cours réservé");
        alert.setHeaderText(null);
        alert.setContentText("Vous avez réservé le cours : " + cours.getCoursName());
        alert.showAndWait();
    }
}
