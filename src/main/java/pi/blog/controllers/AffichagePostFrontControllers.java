package pi.blog.controllers;

 
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import pi.blog.HelloApplication;
import pi.blog.models.Commentaire;
import pi.blog.models.Publication;
import pi.blog.services.CommentaireRepository;
import pi.blog.services.PublicationRepository;


import java.io.File;
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

        List<Publication> publicationss = a.findAllWithUserDetails() ;

        // Set the items in the ListView
        publicationsListView.getItems().addAll(publicationss);
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
                        // Create a custom cell with image, text, and a button
                        HBox cellContent = createCellContent(item);
                        setGraphic(cellContent);
                    }
                }
            };
        }

        private HBox createCellContent(Publication publication) {


            Label titleLabel = new Label("Publication Details:");
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
            Button updatedBtn = new Button("Modifier Post ");
            updatedBtn.setStyle("-fx-font-size: 18; -fx-text-fill: white; -fx-background-color: #b281FF;");
// Set an action for the button
            updatedBtn.setOnAction(event -> {
                // Create a dialog
                Dialog<Pair<String, String>> dialog = new Dialog<>();
                dialog.setTitle("Modifier le Post");
                dialog.setHeaderText(null);

                // Set the button types
                ButtonType modifierButtonType = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(modifierButtonType, ButtonType.CANCEL);

                // Create form controls (TextFields for title and content)
                TextField titleField = new TextField(publication.getTitre());
                TextArea contentArea = new TextArea(publication.getContenu());

                // Add the form controls to the dialog
                GridPane grid = new GridPane();
                grid.add(new Label("Titre:"), 0, 0);
                grid.add(titleField, 1, 0);
                grid.add(new Label("Contenu:"), 0, 1);
                grid.add(contentArea, 1, 1);

                dialog.getDialogPane().setContent(grid);

                // Enable/Disable login button depending on whether a title was entered
                Node modifierButton = dialog.getDialogPane().lookupButton(modifierButtonType);
                modifierButton.setDisable(true);

                // Listen for changes in the title field and enable/disable the modifier button accordingly
                titleField.textProperty().addListener((observable, oldValue, newValue) -> {
                    modifierButton.setDisable(newValue.trim().isEmpty());
                });

                // Set the result converter for the dialog
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == modifierButtonType) {
                        return new Pair<>(titleField.getText(), contentArea.getText());
                    }
                    return null;
                });

                // Show the dialog and wait for the user's input
                Optional<Pair<String, String>> result = dialog.showAndWait();

                // Process the result if the user clicked "Modifier"
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
                    alert.setContentText("Post updated   Successfully.");
                    alert.showAndWait();
        publicationsListView.refresh();
                });
            });

                reponseButton.setOnAction(event -> {
                    // Retrieve comments for the specific publication
                    CommentaireRepository serviceCommentaire =new CommentaireRepository();
                    ObservableList<Commentaire> commentaires = serviceCommentaire.findAllById(publication.getId());

                    // Create a dialog
                    Dialog<Void> dialog = new Dialog<>();
                    dialog.setTitle("Commentaires de la publication: " + publication.getTitre());
                    dialog.setHeaderText("Comment:");

                    // Create a ListView to display comments
                    ListView<String> listView = new ListView<>();
                    listView.setPrefHeight(500);

                    // Add comments to the ListView
                    for (Commentaire commentaire : commentaires) {
                        String commentText = commentaire.getUserName() +": " + commentaire.getRole() + ": " + commentaire.getContenu() + " - " + commentaire.getDateCommentaire();
                        listView.getItems().add(commentText);
                    }

                    TextArea newCommentField = new TextArea();
                    newCommentField.setPromptText("Ajouter un commentaire");

                    // Create a Button for adding a new comment
                    Button addCommentButton = new Button("Ajouter un commentaire");
                    addCommentButton.setOnAction(addEvent -> {
                        if (newCommentField.getText().isEmpty()   ) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("ERROR");
                            alert.setHeaderText("Test");
                            alert.setContentText("Check Fields Again");
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
                            alert.setContentText("Comment Added Successfully.");
                            alert.showAndWait();
                            // Add the new comment to the ListView and the database
                            // (Add your logic here to save the comment to the database)
//                        String userName = "User"; // Replace with the actual user name
//                        String role = "Role"; // Replace with the actual role
//                        String commentText = userName + ": " + role + ": " + newCommentText + " - " + LocalDateTime.now();
//
                            commentaires.add(c);

                            // Update the ListView
                            String newComment = c.getUserName() + ": " + c.getRole() + ": " +
                                    c.getContenu() + " - " + c.getDateCommentaire();
                            listView.getItems().add(newComment);
                            // Clear the text field after adding the comment
                            newCommentField.clear(); // Clear the text field after adding the comment
                        }
                    });

                    // Set the content of the dialog
                    VBox content = new VBox(10);
                    content.getChildren().addAll(listView, newCommentField, addCommentButton);
                    dialog.getDialogPane().setContent(content);

                    // Set the preferred size of the DialogPane
                    dialog.getDialogPane().setPrefSize(600, 500); // Set the preferred width and height

                    // Add a close button to the dialog
                    ButtonType closeButton = new ButtonType("Fermer", ButtonBar.ButtonData.OK_DONE);
                    dialog.getDialogPane().getButtonTypes().add(closeButton);

                    // Show the dialog
                    dialog.showAndWait();
                });


            VBox detailsBox = new VBox(5);
            detailsBox.getChildren().addAll(titleLabel, typeLabel, dateLabel, statutLabel,nameLabel);

            HBox cellContent = new HBox(10);
            cellContent.getChildren().addAll( detailsBox, reponseButton,updatedBtn);

            return cellContent;
        }

    }

    @FXML
    public void handleSearch(KeyEvent event) {
        // Récupérer le texte de la recherche
        String searchTerm = filtre.getText().toLowerCase();

        // Créer un prédicat pour filtrer par statut
        Predicate<Publication> filterByStatut = publication -> {
            String statut = publication.getTitre().toLowerCase();
            return statut.contains(searchTerm);
        };

        // Appliquer le prédicat au FilteredList
        filteredPublicationss.setPredicate(filterByStatut);
    }


    @FXML
    public void Back(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) filtre.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/pi/sport/youssef_pi//User.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
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
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/pi/blog/CreatePublication.fxml"));
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

        // Add OK button to the alert
        ButtonType okButton = new ButtonType("OK", ButtonType.OK.getButtonData());
        alert.getButtonTypes().setAll(okButton);

        // Show the alert and wait for user response
        alert.showAndWait();

    }

}



