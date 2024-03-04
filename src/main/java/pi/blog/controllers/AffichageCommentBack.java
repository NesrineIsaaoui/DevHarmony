package pi.blog.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import pi.blog.models.Commentaire;
import pi.blog.services.CommentaireRepository;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AffichageCommentBack implements Initializable {

    @FXML
    private TableView<Commentaire> tableView;
    @FXML
    private TableColumn<Commentaire, String> message;
    @FXML
    private TableColumn<Commentaire, String> TitrePub;
    @FXML
    private TableColumn<String, String> userName;
    @FXML
    private TableColumn<Commentaire, String> date;
    @FXML
    private TextField filtre;

    CommentaireRepository a = new CommentaireRepository();
    public static Commentaire pr;
    ObservableList<Commentaire> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        show();
    }

    public void show() {
        obList = a.findAll();
        TitrePub.setCellValueFactory(new PropertyValueFactory<>("titrePublication"));
        userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        message.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        date.setCellValueFactory(new PropertyValueFactory<>("dateCommentaire"));

        tableView.setItems(obList);
    }

    @FXML
    private void Back(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/pi/blog/Admin.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        ;
    }

    @FXML
    private void supprimer(ActionEvent event) {
        Commentaire selectedLN = tableView.getSelectionModel().getSelectedItem();
        if (selectedLN == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de supprimer !! ",
                    "Veuillez selectionner une poste à supprimer !");
        }
        if (selectedLN != null) {
            if (showConfirmationDialog("Confirmation de suppression",
                    "Voulez-vous vraiment supprimer ce Commentaire ?",
                    "Cliquez sur OK pour confirmer la suppression.")) {
                a.deleteById(selectedLN.getId());
                show();
                showAlert(Alert.AlertType.INFORMATION, "Information", null,
                        "Commentaire supprimée avec succès!");
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/pi/blog/AffichageCommentBack.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                ;
            }
        }
    }

    @FXML
    public void handleSearch(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String searchText = filtre.getText().trim();
            if (searchText.isEmpty()) {
                tableView.setItems(obList);
            } else {
                ObservableList<Commentaire> filteredList = FXCollections.observableArrayList();
                boolean CommentaireFound = false;
                for (Commentaire b : obList) {
                    if ((b.getTitrePublication().toLowerCase().contains(searchText.toLowerCase()))) {
                        filteredList.add(b);
                        CommentaireFound = true;
                    }
                }
                if (!CommentaireFound) {
                    showAlert(Alert.AlertType.INFORMATION, "Commentaire non trouvee ",
                            "Aucun Commentaire ne correspond à votre recherche",
                            "Veuillez essayer une autre recherche.");
                }
                tableView.setItems(filteredList);
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        applyDialogStyle(alert.getDialogPane());
        alert.showAndWait();
    }

    private boolean showConfirmationDialog(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        applyDialogStyle(alert.getDialogPane());
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private void applyDialogStyle(DialogPane dialogPane) {
        dialogPane.getStylesheets().add(getClass().getResource("/pi/blog/AlertStyle.css").toExternalForm());
    }
}
