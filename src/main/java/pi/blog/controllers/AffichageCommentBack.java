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



    CommentaireRepository a = new CommentaireRepository();
    public static  Commentaire pr ;

    @FXML
    private TextField filtre ;
    ObservableList< Commentaire> obList= FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        show();

    }

    public void show() {
         obList = a.findAll();
        TitrePub.setCellValueFactory(new PropertyValueFactory<>("titrePublication"));

        userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        message.setCellValueFactory(new PropertyValueFactory<>("contenu"));
       // type.setCellValueFactory(new PropertyValueFactory<>("type"));

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
        };
    }

    @FXML
    private void supprimer(ActionEvent event) {
        Commentaire selectedLN =  tableView.getSelectionModel().getSelectedItem();
        if (selectedLN == null) {
            // Afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Impossible de supprimer !! ");
            alert.setContentText("Veuillez selectionner une poste Ã  supprimer !");
            alert.showAndWait();
        }
        if(selectedLN != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Voulez-vous vraiment supprimer ce Commentaire ?");
            alert.setContentText("Cliquez sur OK pour confirmer la suppression.");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // L'utilisateur a confirmé la suppression, vous pouvez maintenant supprimer la réservation.
                 a.deleteById(selectedLN.getId());
                show(); // Peut-être une mise à jour de l'interface utilisateur après la suppression
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Information");
                alert1.setHeaderText(null);
                alert1.setContentText("Commentaire supprimée avec succès!");
                alert1.showAndWait();

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/pi/blog/AffichageCommentBack.fxml"));
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
                ObservableList<Commentaire> filteredList = FXCollections.observableArrayList();
                boolean CommentaireFound = false;
                for (Commentaire b : obList) {
                    // search for name or description
                    if ((b.getTitrePublication().toLowerCase().contains(searchText.toLowerCase())))
                    {
                        filteredList.add(b);
                        CommentaireFound = true;
                    }
                }
                if (!CommentaireFound) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Commentaire non trouvee ");
                    alert.setHeaderText("Aucun Commentaire ne correspond � votre recherche");
                    alert.setContentText("Veuillez essayer une autre recherche.");
                    alert.showAndWait();
                }
                tableView.setItems(filteredList);
            }
        }
    }








}


