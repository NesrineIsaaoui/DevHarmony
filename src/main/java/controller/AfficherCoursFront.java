    package controller;

    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.fxml.Initializable;
    import javafx.geometry.Insets;
    import javafx.geometry.Pos;
    import javafx.scene.Node;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.scene.effect.DropShadow;
    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;
    import javafx.scene.input.MouseEvent;
    import javafx.scene.layout.AnchorPane;
    import javafx.scene.layout.BorderPane;
    import javafx.scene.layout.HBox;
    import javafx.scene.layout.VBox;
    import javafx.scene.paint.Color;
    import javafx.scene.text.Text;
    import javafx.stage.Stage;
    import model.Cours;
    import service.ServiceCours;
    import test.MainFX;

    import java.io.File;
    import java.io.IOException;
    import java.net.URL;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.ResourceBundle;

    import static controller.SeconnecterController.idCurrentUser;

    public class AfficherCoursFront implements Initializable {
        ServiceCours sa = new ServiceCours();
        // Déclarez une liste de HBox pour chaque cours
        private List<HBox> coursRatingBoxes = new ArrayList<>();
        // Variable pour stocker la dernière ratingBox cliquée
        private HBox lastRatingBox;

        @FXML
        private VBox vbox1;

        @FXML
        private AnchorPane nh;

        private boolean isDarkMode = false;
        @FXML
        private Button resbtn;
        @FXML
        private Text trole;
        @FXML
        private Button btnprofil;
        @FXML
        private Button btncours;
        @FXML
        private ToggleButton switchMode;

        @FXML
        private void switchMode() {
            if (isDarkMode) {
                vbox1.getStyleClass().remove("dark-mode");
                vbox1.getStyleClass().add("light-mode");
            } else {
                vbox1.getStyleClass().remove("light-mode");
                vbox1.getStyleClass().add("dark-mode");
            }

            isDarkMode = !isDarkMode;
        }

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

            // Ajoutez un HBox pour contenir les étoiles
            HBox ratingBox = createRatingBox(cours);
            coursePane.setRight(ratingBox);

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

            try {
                // Load the new FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/addreservationcours.fxml"));
                Parent root = loader.load();

                // Get the controller of the new FXML file
                AddReservationCoursController addReservationController = loader.getController();

                // Get the controller instance and set the user ID
                addReservationController.setLoggedInUserId(idCurrentUser);
                // Pass any necessary data to the controller if needed
                // For example, you can pass the selected 'Cours' object
                addReservationController.setCours(cours);

                // Create a new stage
                Stage newStage = new Stage();
                newStage.setTitle("Add Reservation");
                newStage.setScene(new Scene(root));

                // Show the new stage
                newStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        // Créez un HBox pour contenir les étoiles
        private HBox createRatingBox(Cours cours) {
            HBox ratingBox = new HBox();
            ratingBox.setSpacing(5);
            ratingBox.setAlignment(Pos.CENTER);

            // Créez une ImageView pour chaque étoile
            ImageView star1 = createStarImageView(cours, 1);
            ImageView star2 = createStarImageView(cours, 2);
            ImageView star3 = createStarImageView(cours, 3);
            ImageView star4 = createStarImageView(cours, 4);
            ImageView star5 = createStarImageView(cours, 5);

            // Ajoutez les étoiles à la liste
            ratingBox.getChildren().addAll(star1, star2, star3, star4, star5);

            // Ajoutez le HBox à la liste
            coursRatingBoxes.add(ratingBox);

            return ratingBox;
        }

        // Créez une ImageView pour une étoile
        private ImageView createStarImageView(Cours cours, int rating) {
            ImageView star = new ImageView();
            star.setFitHeight(30.0);
            star.setFitWidth(30.0);
            star.setPreserveRatio(true);
            star.setImage(new Image("/starvide.png"));

            // Ajoutez un événement de clic pour l'étoile
            star.setOnMouseClicked(event -> handleRatingClick(cours, rating, (HBox) star.getParent()));

            return star;
        }

        // Gérez le clic sur une étoile
        private void handleRatingClick(Cours cours, int rating, HBox ratingBox) {
            // Faites quelque chose avec le rating, par exemple, sauvegardez-le dans la base de données
            sa.addAvis(cours.getId(), rating);

            // Affichez un message pour informer l'utilisateur que son avis a été enregistré
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Avis enregistré");
            alert.setHeaderText(null);
            alert.setContentText("Votre avis a été enregistré avec succès.");

            // Changez la couleur des étoiles en fonction du rating
            for (int i = 0; i < ratingBox.getChildren().size(); i++) {
                ImageView star = (ImageView) ratingBox.getChildren().get(i);
                if (i < rating) {
                    star.setImage(new Image("/star2.png"));
                } else {
                    star.setImage(new Image("/starvide.png"));
                }
            }

            alert.showAndWait();
        }



        private FXMLLoader createFXMLLoader(String fxmlPath) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            return loader;
        }
        @FXML
        private void ReturnToHomeParent(MouseEvent event) {
            try {
                FXMLLoader loader = createFXMLLoader("/HomeParent.fxml");
                Parent root = loader.load();
                nh.getChildren().setAll(root);
            } catch (IOException ex) {
                System.out.println("Erreur lors du chargement de la vue : " + ex.getMessage());
            }
        }

        @FXML
        private void NavConnexion(ActionEvent event) throws IOException {
            SeconnecterController.clearRememberedCredentials();
            javafx.scene.Parent menu = FXMLLoader.load(MainFX.class.getResource("/SeConnecter.fxml"));
            nh.getChildren().removeAll();
            nh.getChildren().setAll(menu);
        }
        @FXML
        private void NavEditProfil(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("/EditProfile.fxml"));
            Parent menu = loader.load();
            Scene editProfileScene = new Scene(menu, 1043, 730);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(editProfileScene);
        }

        @FXML
        private void goToFrontGestionCours(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("/AfficherCoursFront.fxml"));
            Parent menu = loader.load();
            Scene editProfileScene = new Scene(menu);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(editProfileScene);
        }

        @FXML
        public void gotolistreservations(ActionEvent actionEvent) {

            try {
                URL fxmlUrl = getClass().getResource("/ReservationUserView.fxml");
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
        public void CoursAchetes(ActionEvent actionEvent) {
            try {
                URL fxmlUrl = getClass().getResource("/MesCoursP.fxml");
                System.out.println("FXML URL: " + fxmlUrl);
                FXMLLoader loader = new FXMLLoader(fxmlUrl);
                Parent root = loader.load();
                mescours mescours = loader.getController();

                mescours.setLoggedInUserId(idCurrentUser);

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
