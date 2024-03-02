package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import services.ServiceCours;

import java.net.URL;
import java.util.ResourceBundle;

public class StatsController implements Initializable {

    @FXML
    private PieChart pieChart;

    private final ServiceCours serviceCours = new ServiceCours();
    private int coursId; // Ajout de cette ligne

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Ne rien faire pour le moment dans l'initialisation.
    }

    // Ajout de la méthode updatePieChartData avec le paramètre coursId
    public void updatePieChartData(int coursId) {
        int oneStar = serviceCours.getNombreAvisByRating(coursId, 1);
        int twoStar = serviceCours.getNombreAvisByRating(coursId, 2);
        int threeStar = serviceCours.getNombreAvisByRating(coursId, 3);
        int fourStar = serviceCours.getNombreAvisByRating(coursId, 4);
        int fiveStar = serviceCours.getNombreAvisByRating(coursId, 5);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("1 étoile", oneStar),
                new PieChart.Data("2 étoiles", twoStar),
                new PieChart.Data("3 étoiles", threeStar),
                new PieChart.Data("4 étoiles", fourStar),
                new PieChart.Data("5 étoiles", fiveStar)
        );

        pieChart.setData(pieChartData);
    }
}
