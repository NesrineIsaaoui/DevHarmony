package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import models.Cours;
import models.CoursCategory;
import services.ServiceCours;
import services.ServiceCoursCategory;
import utils.DataSource;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;

public class AfficherCours implements Initializable {
    @FXML
    private ImageView returnToAffiche;
    @FXML
    private VBox vbox1;
    @FXML
    private Button returnToAdd;

    @FXML
    private PieChart pieChart;


    @FXML
    private AnchorPane nh;
    private ServiceCours scom = new ServiceCours();
    private ServiceCoursCategory scomCat = new ServiceCoursCategory();
    private Cours cours;
    private int selectedCoursId = -1;
    private Map<Integer, Integer> avisStats;

    @Override
    public void initialize(URL url, ResourceBundle rb) {


        List<Cours> coursCategories = scom.afficherCours();
        vbox1.setFillWidth(true);

        for (Cours cours : coursCategories) {
            Pane pane = new Pane();
            pane.setPrefHeight(62.0);
            pane.setMinHeight(62.0);
            pane.setPrefWidth(840.0);

            Label label1 = new Label("ID: " + cours.getId());
            label1.setLayoutX(15.0);
            label1.setLayoutY(18.0);
            label1.setFont(new Font(14.0));

            Label label2 = new Label(cours.getCoursName());
            label2.setLayoutX(130.0);
            label2.setLayoutY(18.0);
            label2.setPrefHeight(25.0);
            label2.setPrefWidth(200.0);
            label2.setFont(new Font(16.0));

            Label label3 = new Label(cours.getCoursDescription());
            label3.setLayoutX(250.0);
            label3.setLayoutY(18.0);
            label3.setPrefHeight(25.0);
            label3.setPrefWidth(200.0);
            label3.setFont(new Font(16.0));

            Label label5 = new Label(String.valueOf(cours.getCoursPrix()));
            label5.setLayoutX(380.0);
            label5.setLayoutY(18.0);
            label5.setPrefHeight(25.0);
            label5.setPrefWidth(200.0);
            label5.setFont(new Font(16.0));
            // Récupérer le nom de la catégorie
            CoursCategory category = scomCat.getCoursCategoryById(cours.getIdCategory());
            String categoryName = category.getCategoryName();

            Label label6 = new Label(categoryName);
            label6.setLayoutX(480.0);
            label6.setLayoutY(18.0);
            label6.setPrefHeight(25.0);
            label6.setPrefWidth(200.0);
            label6.setFont(new Font(16.0));
            ImageView imageView1 = new ImageView();
            Image image1 = new Image(getClass().getResourceAsStream("/poubelle.gif"));
            imageView1.setImage(image1);
            imageView1.setFitHeight(25.0);
            imageView1.setFitWidth(25.0);
            imageView1.setLayoutX(580.0);
            imageView1.setLayoutY(20.0);
            imageView1.setPickOnBounds(true);
            imageView1.setPreserveRatio(true);
            imageView1.setOnMouseClicked((MouseEvent event) -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmer la suppression");
                alert.setHeaderText("Êtes-vous sûr de supprimer cette categorie ?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Pane parent = (Pane) pane.getParent();
                    scom.supprimerCours(cours.getId());
                    coursCategories.remove(cours);
                    parent.getChildren().remove(pane);
                }
            });

            ImageView imageView2 = new ImageView();
            Image image2 = new Image(getClass().getResourceAsStream("/redaction.gif"));
            imageView2.setImage(image2);
            imageView2.setFitHeight(25.0);
            imageView2.setFitWidth(25.0);
            imageView2.setLayoutX(600.0);
            imageView2.setLayoutY(20.0);
            imageView2.setPickOnBounds(true);
            imageView2.setPreserveRatio(true);
            imageView2.setOnMouseClicked((MouseEvent event) -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierCours.fxml"));
                    Parent root = loader.load();

                    // Récupérer le contrôleur de la fenêtre de modification
                    ModifierCours modifierController = loader.getController();

                    // Transmettre les données nécessaires au contrôleur de modification
                    modifierController.initData(cours.getId(), cours.getCoursName(), cours.getCoursDescription(), cours.getCoursImage(), cours.getCoursPrix(), cours.getIdCategory());

                    // Changer la scène
                    vbox1.getScene().setRoot(root);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            Line line = new Line();
            line.setStrokeWidth(0.4);
            line.setStartX(-411.0);
            line.setStartY(9.400012969970703);
            line.setEndX(429.0);
            line.setEndY(9.400012969970703);
            line.setLayoutX(250.0);
            line.setLayoutY(53.0);

            pane.getChildren().addAll(label1, label2, label3, label5, label6, imageView1, imageView2, line);
            pane.setOnMouseClicked(event -> {
                selectedCoursId = cours.getId();
                updatePieChart();
            });
            vbox1.getChildren().add(pane);
        }
        vbox1.setSpacing(5);


        vbox1.setFillWidth(true);

        for (Cours cours : coursCategories) {
            // Code pour afficher les informations sur chaque cours
            vbox1.setFillWidth(true);
            // Code pour afficher les informations sur chaque cours
            // Ajouter les statistiques d'avis au PieChart
            updatePieChartForCours(cours);
        }
        vbox1.setSpacing(5);
    }

    private void updatePieChartForCours(Cours cours) {
        // Ajouter un gestionnaire d'événements à chaque Pane
        Pane pane = new Pane();
        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            selectedCoursId = cours.getId();
            updatePieChart();
        });

        // Autres éléments de la création du Pane...

        vbox1.getChildren().add(pane);
    }

    private void updatePieChart() {
        if (selectedCoursId == -1) {
            pieChart.getData().clear();
            return;
        }

        avisStats = scom.getAvisStats(selectedCoursId);
        pieChart.getData().clear();
        for (Map.Entry<Integer, Integer> entry : avisStats.entrySet()) {
            PieChart.Data data = new PieChart.Data("Etoiles " + entry.getKey(), entry.getValue());
            pieChart.getData().add(data);
        }
    }


    @FXML
    private void returnToAffiche(MouseEvent event) {
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
    void returnToAdd(MouseEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCours.fxml"));
        try {
            Parent root = loader.load();
            nh.getChildren().setAll(root);

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }


    @FXML
    private void excelmth(MouseEvent event) {
        Connection cnx = DataSource.getInstance().getConnection();

        try {
            String filename = "C:\\Users\\LENOVO\\IdeaProjects\\GestionCours\\src\\main\\resources\\data.xls";
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("new sheet");
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell((short) 0).setCellValue("id");
            rowhead.createCell((short) 1).setCellValue("coursName");
            rowhead.createCell((short) 2).setCellValue("etoiles");
            rowhead.createCell((short) 4).setCellValue("Retour");

            ServiceCours sa = new ServiceCours();
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery("select * from avis");
            int i = 1;
            while (rs.next()) {
                HSSFRow row = sheet.createRow((short) i);

                int courseId = rs.getInt("cours_id");
                Cours cours = sa.getCourseById(courseId);
                String coursName = cours.getCoursName();
                row.createCell((short) 0).setCellValue(rs.getString("id"));
                row.createCell((short) 1).setCellValue(coursName);
                row.createCell((short) 2).setCellValue(rs.getString("etoiles"));

                double averageStars = sa.getEtoiles(courseId);

                // Déterminez les informations de retour du cours
                String feedback = sa.getCourseFeedback(averageStars);
                row.createCell((short) 4).setCellValue(feedback);

                i++;
            }

            FileOutputStream fileOut = new FileOutputStream(filename);
            hwb.write(fileOut);
            fileOut.close();
            System.out.println("Votre fichier Excel a été généré !");
            File file = new File(filename);
            if (file.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(file);
                }
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}