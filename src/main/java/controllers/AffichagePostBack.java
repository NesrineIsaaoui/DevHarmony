package controllers;

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
import models.Publication;
import services.PublicationRepository;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AffichagePostBack implements Initializable {


    @FXML
    private TableView<Publication> tableView;
    @FXML
    private TableColumn<Publication, String> message;
    @FXML
    private TableColumn<Publication, String> titre;
    @FXML
    private TableColumn<String, String> userName;

    @FXML
    private TableColumn<Publication, String> date;



    PublicationRepository a = new PublicationRepository();
    public static  Publication pr ;

    @FXML
    private TextField filtre ;
    ObservableList< Publication> obList= FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        show();

    }

    public void show() {
         obList = a.findAll();
        titre.setCellValueFactory(new PropertyValueFactory<>("titre"));

        userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        message.setCellValueFactory(new PropertyValueFactory<>("contenu"));
       // type.setCellValueFactory(new PropertyValueFactory<>("type"));

         date.setCellValueFactory(new PropertyValueFactory<>("datePublication"));


        tableView.setItems(obList);
    }





    @FXML
    private void Back(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Admin.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        };
    }

    @FXML
    private void supprimer(ActionEvent event) {
        Publication selectedLN =  tableView.getSelectionModel().getSelectedItem();
        if (selectedLN == null) {
            // Afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Impossible de supprimer !! ");
            alert.setContentText("Veuillez selectionner une poste Ã  supprimer !");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/pi/blog/Style.css").toExternalForm());
            alert.showAndWait();
        }
        if(selectedLN != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Voulez-vous vraiment supprimer ce Publication ?");
            alert.setContentText("Cliquez sur OK pour confirmer la suppression.");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // L'utilisateur a confirmé la suppression, vous pouvez maintenant supprimer la réservation.
                 a.deleteById(selectedLN.getId());
                show(); // Peut-être une mise à jour de l'interface utilisateur après la suppression
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Information");
                alert1.setHeaderText(null);
                alert1.setContentText("Publication supprimée avec succès!");
                alert1.showAndWait();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/AffichagePostBack.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                };
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
                ObservableList<Publication> filteredList = FXCollections.observableArrayList();
                boolean PublicationFound = false;
                for (Publication b : obList) {
                    // search for name or description
                    if ((b.getTitre().toLowerCase().contains(searchText.toLowerCase())))
                    {
                        filteredList.add(b);
                        PublicationFound = true;
                    }
                }
                if (!PublicationFound) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Publication non trouvee ");
                    alert.setHeaderText("Aucun Publication ne correspond a votre recherche");
                    alert.setContentText("Veuillez essayer une autre recherche.");
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStylesheets().add(getClass().getResource("resources/Style.css").toExternalForm());
                    alert.showAndWait();
                }
                tableView.setItems(filteredList);
            }
        }
    }








}


