package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Cours;
import models.CoursCategory;
import services.ServiceCours;
import services.ServiceCoursCategory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AjouterCours implements Initializable {

    @FXML
    private Button btnAjouter;

    @FXML
    private Label file_path;

    @FXML
    private AnchorPane nh;

    @FXML
    private ComboBox<String> categorieCours;

    @FXML
    private TextArea fx_description;

    @FXML
    private TextField fx_nom;

    @FXML
    private TextField fx_prix;

    @FXML
    private ImageView imagev;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> list = FXCollections.observableArrayList();
        ServiceCoursCategory sc = new ServiceCoursCategory();
        ObservableList<CoursCategory> obList = FXCollections.observableArrayList();
        obList = sc.afficherCategory2();

        categorieCours.getItems().clear();

        for (CoursCategory nameCat : obList) {
            list.add(nameCat.getCategoryName());
        }

        categorieCours.setItems(list);
    }
    private FXMLLoader createFXMLLoader(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlPath));
        return loader;
    }
    @FXML
    private void ReturnToAfficherCours(MouseEvent event) {
        try {
            FXMLLoader loader = createFXMLLoader("/AfficherCours.fxml");
            Parent root = loader.load();
            nh.getChildren().setAll(root);
        } catch (IOException ex) {
            System.out.println("Erreur lors du chargement de la vue : " + ex.getMessage());
        }
    }

    @FXML
    void uploadimageHandler(MouseEvent event) {
        FileChooser open = new FileChooser();
        Stage stage = (Stage) nh.getScene().getWindow();
        File file = open.showOpenDialog(stage);
        if (file != null) {
            String path = file.getAbsolutePath(); // Utilisez getAbsolutePath() au lieu de getName()
            file_path.setText(path);
            Image image = new Image(file.toURI().toString(), 500, 500, false, true);
            imagev.setImage(image);
        } else {
            System.out.println("NO DATA EXIST!");
        }
    }
    @FXML
    void ajouterCours(javafx.event.ActionEvent event) {
        String coursName = fx_nom.getText();
        String coursDescription = fx_description.getText();
        int coursPrix = 0;



        if (coursName.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Erreur : Veuillez entrer un nom de cours.");
            alert.show();
            return;
        }

        if (coursDescription.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Erreur : Veuillez entrer une description de cours.");
            alert.show();
            return;
        }
        try {
            coursPrix = Integer.parseInt(fx_prix.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Erreur : Veuillez entrer un nombre valide pour le prix.");
            alert.show();
            return;
        }
        if (coursPrix < 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Erreur : Le prix du cours doit être positif.");
            alert.show();
            return;
        }

        ServiceCoursCategory sc = new ServiceCoursCategory();
        int idCategory = sc.getIdCategorieByName(categorieCours.getValue());

        if (idCategory == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Erreur : Catégorie non trouvée.");
            alert.show();
            return;
        }

        ServiceCours ss = new ServiceCours();
        ss.ajouterCours(new Cours(coursName, coursDescription, file_path.getText(), coursPrix, idCategory));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success Message");
        alert.setHeaderText(null);
        alert.setContentText("Cours ajouté avec succès !");
        alert.showAndWait();
    }

    }
