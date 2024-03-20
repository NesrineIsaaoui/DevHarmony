package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.ReservationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.Reservation;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReservationChartController {

    @FXML
    private PieChart userCoursePieChart;

    private ObservableList<Reservation> reservations;

    @FXML
    private BarChart<String, Float> revenues;

    @FXML
    private ComboBox<String> timePeriodComboBox;
    @FXML
    private Label totalRevenueLabel;
    @FXML
    private Label label;

    @FXML
    private void initialize() {
        ReservationService reservationService = new ReservationService();
        reservations = FXCollections.observableArrayList(reservationService.getAllReservations());

        populatePieChart();

        userCoursePieChart.getData().forEach(data ->
                data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, this::showLabel));

        timePeriodComboBox.setItems(FXCollections.observableArrayList("Year", "Month", "Day"));
        timePeriodComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateBarChart(newValue);
                updateTotalRevenueLabel(newValue);
            }
        });

        populateBarChart("Month");
        updateTotalRevenueLabel("Month");
    }

    private void showLabel(MouseEvent event) {
        if (event.getSource() instanceof PieChart.Data) {
            PieChart.Data dataNode = (PieChart.Data) event.getSource();

            Text chartText = (Text) dataNode.getNode().lookup(".chart-pie-label");

            label.setText(chartText.getText());
        }
    }
    private void updateTotalRevenueLabel(String timePeriod) {
        ReservationService reservationService = new ReservationService();
        List<Reservation> allReservations = reservationService.getAllReservations();
        Map<String, Float> revenueData = calculateRevenueData(allReservations, timePeriod);

        Float selectedPeriodRevenue = revenueData.get(timePeriod);

        totalRevenueLabel.setText("Total Revenue for " + timePeriod + ": " + (selectedPeriodRevenue != null ? String.format("%.2f$", selectedPeriodRevenue) : "N/A"));
    }

    public void setReservations(ObservableList<Reservation> reservations) {
        this.reservations = reservations;
        populatePieChart();
    }

    private void populatePieChart() {
        Map<Integer, Long> courseUserCount = reservations.stream()
                .filter(Reservation::isResStatus)
                .collect(Collectors.groupingBy(Reservation::getId_cours, Collectors.counting()));

        ObservableList<PieChart.Data> pieChartData = courseUserCount.entrySet().stream()
                .map(entry -> new PieChart.Data("Course " + entry.getKey() + "\n" + entry.getValue() + " users", entry.getValue()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        userCoursePieChart.setData(pieChartData);

        customizePieChartLabels();

    }

    private void customizePieChartLabels() {
        for (PieChart.Data data : userCoursePieChart.getData()) {
            Text chartText = (Text) data.getNode().lookup(".chart-pie-label");
            if (chartText != null) {
                chartText.setStyle("-fx-font-size: 10px;");
            }
        }
    }

    private void populateBarChart(String timePeriod) {
        ReservationService reservationService = new ReservationService();
        List<Reservation> allReservations = reservationService.getAllReservations();
        Map<String, Float> revenueData = calculateRevenueData(allReservations, timePeriod);

        BarChart.Series<String, Float> series = new BarChart.Series<>();
        series.setName("Revenue");

        revenueData.forEach((period, revenue) -> series.getData().add(new BarChart.Data<>(period, revenue)));

        revenues.getData().setAll(series);

        showDataLabels(revenues);

        float totalRevenue = (float) revenueData.values().stream().mapToDouble(value -> value).sum();

        totalRevenueLabel.setText("Total Revenue: " + String.format("%.2f$", totalRevenue));

        updateTotalRevenueLabel(timePeriod);

    }

    private void showDataLabels(BarChart<String, Float> chart) {
        for (BarChart.Data<String, Float> data : chart.getData().get(0).getData()) {
            Text dataLabel = new Text(String.format("%.2f$", data.getYValue()));
            dataLabel.setStyle("-fx-font-size: 10px;");
            dataLabel.setMouseTransparent(true);
            data.getNode().parentProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    ((Group) newValue).getChildren().add(dataLabel);
                }
            });
        }
    }

    private Map<String, Float> calculateRevenueData(List<Reservation> reservations, String timePeriod) {
        Map<String, Float> revenueData = null;

        switch (timePeriod) {
            case "Year":
                revenueData = reservations.stream()
                        .filter(Reservation::isPaidStatus)
                        .collect(Collectors.groupingBy(
                                reservation -> String.valueOf(reservation.getDateReservation().getYear()),
                                Collectors.summingDouble(Reservation::getPrixd)
                        ))
                        .entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().floatValue()));
                break;
            case "Month":
                revenueData = reservations.stream()
                        .filter(Reservation::isPaidStatus)
                        .collect(Collectors.groupingBy(
                                reservation -> reservation.getDateReservation().toLocalDate().getMonth().toString(),
                                Collectors.summingDouble(Reservation::getPrixd)
                        ))
                        .entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().floatValue()));
                break;
            case "Day":
                revenueData = reservations.stream()
                        .filter(Reservation::isPaidStatus)
                        .collect(Collectors.groupingBy(
                                reservation -> reservation.getDateReservation().toLocalDate().toString(),
                                Collectors.summingDouble(Reservation::getPrixd)
                        ))
                        .entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().floatValue()));
                break;
            default:
                break;
        }
        return revenueData;
    }




    @FXML
    public void returntoreservations(ActionEvent actionEvent) {


        try {
            Stage currentStage = (Stage) label.getScene().getWindow();

            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/updatereservation.fxml"));
            Parent root = loader.load();

            Stage newStage = new Stage();
            newStage.setTitle("EDUWAVE");
            newStage.setScene(new Scene(root));

            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
