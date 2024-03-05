package controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import test.MainFX;
import models.Commentaire;
import models.Publication;
import services.CommentaireRepository;
import services.PublicationRepository;


import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class AffichagePostFrontControllers implements Initializable {

    @FXML
    private ListView<Publication> publicationsListView;
    private FilteredList<Publication> filteredPublicationss;

    @FXML
    private TextField filtre;
    @FXML
    private TextField weightField;

    @FXML
    private TextField heightField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        publicationsListView.setCellFactory(new PublicationsCellFactory());

        PublicationRepository a = new PublicationRepository();

        List<Publication> publicationss = a.findAllWithUserDetails();

// Convert the List to an ObservableList
        ObservableList<Publication> observableList = FXCollections.observableList(publicationss);

// Set the items in the ListView
        publicationsListView.setItems(observableList);
        // Créer la FilteredList
        filteredPublicationss = new FilteredList<>(FXCollections.observableList(publicationss));

// Initialiser votre ListView
        publicationsListView.setItems(filteredPublicationss);
    }


    public class PublicationsCellFactory implements Callback<ListView<Publication>, ListCell<Publication>> {

        @Override
        public ListCell<Publication> call(ListView<Publication> param) {
            return new ListCell<Publication>() {

                @Override
                protected void updateItem(Publication item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setGraphic(null);
                        setText(null);
                    } else {

                        HBox cellContent = createCellContent(item);
                        setGraphic(cellContent);
                    }
                }
            };
        }
        private void addHoverEffect(Button button) {
            String hoverStyle = "-fx-background-color: #9767ff;";
            button.setOnMouseEntered(e -> button.setStyle(button.getStyle() + hoverStyle));
            button.setOnMouseExited(e -> button.setStyle(button.getStyle().replace(hoverStyle, "")));
        }
        private void applyDialogStyle(DialogPane dialogPane) {
            dialogPane.getStylesheets().add(getClass().getResource("/AlertStyle.css").toExternalForm());
        }

        private HBox createCellContent(Publication publication) {


            Label titleLabel = new Label("Details des publications:");
            titleLabel.setStyle("-fx-font-size: 18; -fx-text-fill: blue;"); // Increase font size
            Label typeLabel = new Label("Titre: " + publication.getTitre());
            typeLabel.setStyle("-fx-font-size: 16;"); // Increase font size
            Label dateLabel = new Label("Contenu: " + publication.getContenu());
            dateLabel.setStyle("-fx-font-size: 16;"); // Increase font size
            Label statutLabel = new Label("DatePublication: " + publication.getDatePublication());
            statutLabel.setStyle("-fx-font-size: 16;"); // Increase font size
            Label nameLabel = new Label("UserName: " +publication.getRole()+" : " +publication.getUserName() );
            nameLabel.setStyle("-fx-font-size: 16;"); // Increase font size
            Button reponseButton = new Button(" Voir les commentaire ");
            reponseButton.setStyle("-fx-font-size: 18; -fx-text-fill: white; -fx-background-color: #b281FF;");
            addHoverEffect(reponseButton);
            Button updatedBtn = new Button("Modifier Post ");
            updatedBtn.setStyle("-fx-font-size: 18; -fx-text-fill: white; -fx-background-color: #b281FF;");
            addHoverEffect(updatedBtn);
            Button deletebtn = new Button("Delete ");
            deletebtn.setStyle("-fx-font-size: 18; -fx-text-fill: white; -fx-background-color: #b281FF;");
            addHoverEffect(deletebtn);
            deletebtn.setOnAction(event -> handleDeleteButton(publication));



            updatedBtn.setOnAction(event -> {
                Dialog<Pair<String, String>> dialog = new Dialog<>();
                dialog.setTitle("Modifier la publication");
                dialog.setHeaderText(null);

                ButtonType modifierButtonType = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(modifierButtonType, ButtonType.CANCEL);

                TextField titleField = new TextField(publication.getTitre());
                TextArea contentArea = new TextArea(publication.getContenu());

                GridPane grid = new GridPane();
                grid.add(new Label("Titre:"), 0, 0);
                grid.add(titleField, 1, 0);
                grid.add(new Label("Contenu:"), 0, 1);
                grid.add(contentArea, 1, 1);

                dialog.getDialogPane().setContent(grid);

                Node modifierButton = dialog.getDialogPane().lookupButton(modifierButtonType);
                modifierButton.setDisable(true);

                titleField.textProperty().addListener((observable, oldValue, newValue) -> {
                    modifierButton.setDisable(newValue.trim().isEmpty());
                });

                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == modifierButtonType) {
                        return new Pair<>(titleField.getText(), contentArea.getText());
                    }
                    return null;
                });

                Optional<Pair<String, String>> result = dialog.showAndWait();

                result.ifPresent(pair -> {
                    String modifiedTitle =titleField.getText();
                    String modifiedContent = contentArea.getText();
                    publication.setContenu(modifiedContent);
                    publication.setTitre(modifiedTitle);
                PublicationRepository a =new PublicationRepository();
                 a.update(publication);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Publication mise a jour!.");
                    DialogPane dialogPane = alert.getDialogPane();
                    applyDialogStyle(dialogPane);
                    dialogPane.getStylesheets().add(getClass().getResource("/Style.css").toExternalForm());
                    alert.showAndWait();
        publicationsListView.refresh();
                });
            });

                reponseButton.setOnAction(event -> {
                    CommentaireRepository serviceCommentaire =new CommentaireRepository();
                    ObservableList<Commentaire> commentaires = serviceCommentaire.findAllById(publication.getId());

                    Dialog<Void> dialog = new Dialog<>();
                    dialog.setTitle("Commentaires de la publication: " + publication.getTitre());
                    dialog.setHeaderText("Comments:");

                    ListView<String> listView = new ListView<>();
                    listView.setPrefHeight(700);

                    for (Commentaire commentaire : commentaires) {
                        String commentText = commentaire.getUserName() +": " + commentaire.getRole() + ": " + commentaire.getContenu() + " - " + commentaire.getDateCommentaire();
                        listView.getItems().add(commentText);
                    }

                    TextArea newCommentField = new TextArea();
                    newCommentField.setPromptText("Ajouter un commentaire");

                    Button addCommentButton = new Button("Ajouter un commentaire");
                    addCommentButton.setOnAction(addEvent -> {
                        if (newCommentField.getText().isEmpty()   ) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("ERROR");
                            alert.setHeaderText("Commentaire vide!");
                            alert.setContentText("Veuillez vérifier votre commentaire!");
                            DialogPane dialogPane = alert.getDialogPane();
                            dialogPane.getStylesheets().add(getClass().getResource("/Style.css").toExternalForm());
                            alert.showAndWait();
                        }
                        else {
                            String newCommentText = newCommentField.getText();

                            Commentaire c = new Commentaire();
                            c.setContenu(newCommentText);
                            c.setPublicationId(publication.getId());
                            c.setUtilisateurId(publication.getUtilisateurID());
                            c.setDateCommentaire(new Date());
                            serviceCommentaire.save(c);
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Success");
                            alert.setHeaderText(null);
                            alert.setContentText("Commentaire ajouté.");
                            DialogPane dialogPane = alert.getDialogPane();
                            applyDialogStyle(dialogPane);
                            dialogPane.getStylesheets().add(getClass().getResource("/Style.css").toExternalForm());
                            alert.showAndWait();
                            // Add the new comment to the ListView and the database
                            // (Add your logic here to save the comment to the database)
//                        String userName = "User"; // Replace with the actual user name
//                        String role = "Role"; // Replace with the actual role
//                        String commentText = userName + ": " + role + ": " + newCommentText + " - " + LocalDateTime.now();
//
                            commentaires.add(c);

                            String newComment = c.getUserName() + ": " + c.getRole() + ": " +
                                    c.getContenu() + " - " + c.getDateCommentaire();
                            listView.getItems().add(newComment);
                            newCommentField.clear(); // Clear the text field after adding the comment
                        }
                    });

                    VBox content = new VBox(10);
                    content.getChildren().addAll(listView, newCommentField, addCommentButton);
                    dialog.getDialogPane().setContent(content);

                    dialog.getDialogPane().setPrefSize(600, 500); // Set the preferred width and height

                    ButtonType closeButton = new ButtonType("Fermer", ButtonBar.ButtonData.OK_DONE);
                    dialog.getDialogPane().getButtonTypes().add(closeButton);

                    dialog.showAndWait();
                });


            VBox detailsBox = new VBox(5);
            detailsBox.getChildren().addAll(titleLabel, typeLabel, dateLabel, statutLabel,nameLabel);

            HBox cellContent = new HBox(10);
            cellContent.getChildren().addAll( detailsBox, reponseButton,updatedBtn,deletebtn);

            return cellContent;

        }
        private void handleDeleteButton(Publication publication) {
            System.out.println("Suppression de la publication: " + publication);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Supprimer la Publication");
            alert.setContentText("Etes-vous de vouloir supprimer cette publication?");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/Style.css").toExternalForm());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                PublicationRepository repository = new PublicationRepository();
                repository.deleteById(publication.getId());



                List<Publication> items = repository.findAllWithUserDetails();
                ObservableList<Publication> observableList = FXCollections.observableList(items);
                publicationsListView.setItems(observableList);



                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                alert1.setTitle("Success");
                alert1.setHeaderText(null);
                alert1.setContentText("Publication supprimée.");
                DialogPane dialogPane1 = alert1.getDialogPane();
                applyDialogStyle(dialogPane1);
                alert1.showAndWait();
            }
        }

        }



    @FXML
    public void handleSearch(KeyEvent event) {
        String searchTerm = filtre.getText().toLowerCase();

        Predicate<Publication> filterByStatut = publication -> {
            String statut = publication.getTitre().toLowerCase();
            return statut.contains(searchTerm);
        };

        filteredPublicationss.setPredicate(filterByStatut);
    }


    @FXML
    public void Back(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) filtre.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/Blog-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Blog");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }


    }

    @FXML
    public void create(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) filtre.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/CreatePublication.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Blog!");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }



    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        ButtonType okButton = new ButtonType("OK", ButtonType.OK.getButtonData());
        alert.getButtonTypes().setAll(okButton);

        alert.showAndWait();

    }

}



