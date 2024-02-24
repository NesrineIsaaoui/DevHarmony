package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Cours;
import models.CoursCategory;
import services.ServiceCours;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AfficherCours  implements Initializable {
    static int id;
    static String coursName;
    static String coursDescription;
    static int idCategory;
    static int coursPrix;
    static String coursImage;



    @FXML
    private Button mod;
    @FXML
    private Button returnToAdd;


    @FXML
    private ImageView fx_return;
    @FXML
    private AnchorPane nh;
    @FXML
    private ListView<Cours> affichercours;
    @FXML
    private Button supprimer;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ListView<Cours> list1= affichercours;
        ServiceCours inter = new ServiceCours();
        List<Cours> list2 = inter.afficherCours();
        for (int i = 0; i < list2.size(); i++) {
        Cours C = list2.get(i);
        list1.getItems().add(C);

    }

}


    @FXML
    void mod(ActionEvent event) {
        ListView<Cours> list = affichercours;
        ServiceCours inter = new ServiceCours();
        int selectedIndex = list.getSelectionModel().getSelectedIndex();
        Cours A = list.getSelectionModel().getSelectedItem();

        id =A.getId();
        coursName=A.getCoursName();
        coursDescription=A.getCoursDescription();
        idCategory=A.getIdCategory();
        coursPrix=A.getCoursPrix();
        coursImage=A.getCoursImage();
        try {

            Parent page1
                    = FXMLLoader.load(getClass().getResource("/ModifierCours.fxml"));
            Scene scene = new Scene(page1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Location_categoryController.class.getName()).log(Level.SEVERE, null, ex);

        }
    }


    @FXML
    void supprimer_cours(ActionEvent event) {
        ListView<Cours> list1 = affichercours;
        ServiceCours inter = new ServiceCours();
        int selectedIndex = list1.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Cours A = list1.getSelectionModel().getSelectedItem();
            System.out.println(A.getId());
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de la suppression");
            confirmationAlert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce Cours ?");
            confirmationAlert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            ButtonType userChoice = confirmationAlert.showAndWait().orElse(ButtonType.NO);
            if (userChoice == ButtonType.YES) {
                inter.supprimerCours(A.getId());
                list1.getItems().remove(selectedIndex);
            }
        } else {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setHeaderText("Veuillez sélectionner un Cours à supprimer.?");
            confirmationAlert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            ButtonType userChoice = confirmationAlert.showAndWait().orElse(ButtonType.NO);

        }
    }
    @FXML
    void returnToAdd(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCours.fxml"));
        try {
            Parent root = loader.load();
            nh.getChildren().setAll(root);

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }


    @FXML
    private void returnToLocation(MouseEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Location_category.fxml"));
        try {
            Parent root = loader.load();
            nh.getChildren().setAll(root);

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    }




