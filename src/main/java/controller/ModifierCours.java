package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Cours;
import model.CoursCategory;
import service.ServiceCours;
import service.ServiceCoursCategory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifierCours implements Initializable {

    @FXML
    private Button modifierBtn;  // Changed from Modifer to modifierBtn

    @FXML
    private AnchorPane nh;

    private int id;

    @FXML
    private Button btnAjouter;

    @FXML
    private Label file_path;

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

    public void initData(int id, String coursName, String coursDescription, String coursImage, int coursPrix, int idCategory) {
        this.id = id;
        fx_nom.setText(coursName);
        fx_description.setText(coursDescription);
        file_path.setText(coursImage);
        fx_prix.setText(String.valueOf(coursPrix));
        categorieCours.setValue(getCategoryNameById(idCategory));
    }

    private String getCategoryNameById(int categoryId) {
        ServiceCoursCategory sc = new ServiceCoursCategory();
        CoursCategory category = sc.getCoursCategoryById(categoryId);
        return (category != null) ? category.getCategoryName() : null;
    }

    @FXML
    void uploadimageHandler(MouseEvent event) {
        FileChooser open = new FileChooser();
        Stage stage = (Stage) nh.getScene().getWindow();
        File file = open.showOpenDialog(stage);
        if (file != null) {
            String path = file.getAbsolutePath();
            file_path.setText(path);
            Image image = new Image(file.toURI().toString(), 500, 500, false, true);
            imagev.setImage(image);
        } else {
            System.out.println("NO DATA EXIST!");
        }
    }

    @FXML
    void modifierBtnHandler(ActionEvent event) {
        ServiceCours inter = new ServiceCours();
        String categoryName = fx_nom.getText();
        if (categoryName.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Erreur : veuillez donner un nom de cours.");
            alert.show();
        } else {
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
            Cours cours = new Cours(id, fx_nom.getText(), fx_description.getText(), file_path.getText(), Integer.parseInt(fx_prix.getText()), idCategory);
            inter.modifierCours(cours);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Information dialog");
            alert.setHeaderText(null);
            alert.setContentText("Cours modifiée avec succès !");
            alert.show();
        }
    }

    @FXML
    private void returnToAffiche(MouseEvent event) {
        try {
            FXMLLoader loader = createFXMLLoader("/AfficherCours.fxml");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
}
