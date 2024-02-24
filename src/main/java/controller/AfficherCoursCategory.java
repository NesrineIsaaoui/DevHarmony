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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Cours;
import models.CoursCategory;
import services.ServiceCoursCategory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AfficherCoursCategory  implements Initializable {


    @FXML
    private Button mod;


    private ImageView returnToLocation;

    @FXML
    private AnchorPane nh;

    @FXML
    private Button supprimer;

    @FXML
    private Button returnToAdd;

    static int id;
    static String categoryName;

    @FXML
    private ImageView fx_return;

    @FXML
    private ListView<CoursCategory> affichercategory;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ListView<CoursCategory> list1 = affichercategory;
        ServiceCoursCategory inter = new ServiceCoursCategory();
        List<CoursCategory> list2 = inter.afficherCoursCategory();
        for (int i = 0; i < list2.size(); i++) {
            CoursCategory C = list2.get(i);
            list1.getItems().add(C);

        }
    }

    @FXML
    void mod(ActionEvent event) {
        ListView<CoursCategory> list = affichercategory;
        ServiceCoursCategory inter = new ServiceCoursCategory();
        int selectedIndex = list.getSelectionModel().getSelectedIndex();
        CoursCategory A = list.getSelectionModel().getSelectedItem();
        id = A.getId();
        categoryName = A.getCategoryName();
        try {

            Parent page1
                    = FXMLLoader.load(getClass().getResource("/Modifiercategory.fxml"));
            Scene scene = new Scene(page1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Location_categoryController.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    @FXML
    void supprimercategory(ActionEvent event) {

        ListView<CoursCategory> list1 = affichercategory;
        ServiceCoursCategory inter = new ServiceCoursCategory();
        int selectedIndex = list1.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            CoursCategory A = list1.getSelectionModel().getSelectedItem();
            System.out.println(A.getId());

            // Créez une boîte de dialogue de confirmation
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de la suppression");
            confirmationAlert.setHeaderText("Êtes-vous sûr de vouloir supprimer cette catégory ?");
            // Ajoutez des boutons Oui et Non à la boîte de dialogue
            confirmationAlert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            // Affichez la boîte de dialogue et attendez la réponse de l'utilisateur
            ButtonType userChoice = confirmationAlert.showAndWait().orElse(ButtonType.NO);
            if (userChoice == ButtonType.YES) {
                // Supprimez l'élément uniquement si l'utilisateur a cliqué sur Oui
                inter.supprimerCoursCategory(A.getId());
                list1.getItems().remove(selectedIndex);
            }
        } else {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            //  System.out.println("Veuillez sélectionner une catégorie à supprimer.");
            confirmationAlert.setHeaderText("Veuillez sélectionner une categorie à supprimer.?");
            // Ajoutez des boutons Oui et Non à la boîte de dialogue
            confirmationAlert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            // Affichez la boîte de dialogue et attendez la réponse de l'utilisateur
            ButtonType userChoice = confirmationAlert.showAndWait().orElse(ButtonType.NO);
        }

    }

    @FXML
    private void returnToLocation(MouseEvent event) {
        try {
            FXMLLoader loader = createFXMLLoader("/Location_category.fxml");
            Parent root = loader.load();
            nh.getChildren().setAll(root);
        } catch (IOException ex) {
            System.out.println("Erreur lors du chargement de la vue : " + ex.getMessage());
        }
    }

    private FXMLLoader createFXMLLoader(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlPath));
        return loader;
    }


    @FXML
    void returnToAdd(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCoursCategory.fxml"));
        try {
            Parent root = loader.load();
            nh.getChildren().setAll(root);

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }



}

