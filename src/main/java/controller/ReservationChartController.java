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
        // Fetch reservations using your ReservationService or your data retrieval logic
        ReservationService reservationService = new ReservationService(); // Replace with your actual service
        reservations = FXCollections.observableArrayList(reservationService.getAllReservations());

        // Populate the PieChart
        populatePieChart();

        // Add event handler for mouse moved to show labels
        userCoursePieChart.getData().forEach(data ->
                data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, this::showLabel));

        // Set up ComboBox
        timePeriodComboBox.setItems(FXCollections.observableArrayList("Year", "Month", "Day"));
        timePeriodComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateBarChart(newValue); // Refresh the BarChart when the time period changes
                updateTotalRevenueLabel(newValue); // Update the total revenue label
            }
        });

        // Populate the BarChart initially with the default time period, e.g., "Month"
        populateBarChart("Month");
        updateTotalRevenueLabel("Month"); // Update the total revenue label initially
    }

    private void showLabel(MouseEvent event) {
        if (event.getSource() instanceof PieChart.Data) {
            PieChart.Data dataNode = (PieChart.Data) event.getSource();

            // Use lookup to find the text node associated with the PieChart.Data
            Text chartText = (Text) dataNode.getNode().lookup(".chart-pie-label");

            // Set label text to display the number of users
            label.setText(chartText.getText());
        }
    }
    private void updateTotalRevenueLabel(String timePeriod) {
        // Fetch revenues based on the time period (Year, Month, or Day)
        ReservationService reservationService = new ReservationService();
        List<Reservation> allReservations = reservationService.getAllReservations();
        Map<String, Float> revenueData = calculateRevenueData(allReservations, timePeriod);

        // Get the revenue for the selected time period
        Float selectedPeriodRevenue = revenueData.get(timePeriod);

        // Update the total revenue label
        totalRevenueLabel.setText("Total Revenue for " + timePeriod + ": " + (selectedPeriodRevenue != null ? String.format("%.2f$", selectedPeriodRevenue) : "N/A"));
    }

    public void setReservations(ObservableList<Reservation> reservations) {
        this.reservations = reservations;
        // Call the method to populate the PieChart with the updated reservations
        populatePieChart();
    }

    private void populatePieChart() {
        // Group reservations by course ID and count the number of users for each course
        Map<Integer, Long> courseUserCount = reservations.stream()
                .filter(Reservation::isResStatus) // Assuming only successful reservations are considered
                .collect(Collectors.groupingBy(Reservation::getId_cours, Collectors.counting()));

        // Create PieChart.Data for each course
        ObservableList<PieChart.Data> pieChartData = courseUserCount.entrySet().stream()
                .map(entry -> new PieChart.Data("Course " + entry.getKey() + "\n" + entry.getValue() + " users", entry.getValue()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        // Set data to the PieChart
        userCoursePieChart.setData(pieChartData);

        // Customize labels
        customizePieChartLabels();

    }

    private void customizePieChartLabels() {
        for (PieChart.Data data : userCoursePieChart.getData()) {
            Text chartText = (Text) data.getNode().lookup(".chart-pie-label");
            if (chartText != null) {
                chartText.setStyle("-fx-font-size: 10px;"); // Customize font size if needed
            }
        }
    }

    private void populateBarChart(String timePeriod) {
        // Fetch revenues based on the time period (Year, Month, or Day)
        ReservationService reservationService = new ReservationService();
        List<Reservation> allReservations = reservationService.getAllReservations();
        Map<String, Float> revenueData = calculateRevenueData(allReservations, timePeriod);

        // Create BarChart.Series
        BarChart.Series<String, Float> series = new BarChart.Series<>();
        series.setName("Revenue");

        // Add data to the series
        revenueData.forEach((period, revenue) -> series.getData().add(new BarChart.Data<>(period, revenue)));

        // Set data to the BarChart
        revenues.getData().setAll(series);

        // Show data labels on top of each bar
        showDataLabels(revenues);

        // Calculate total revenue
        float totalRevenue = (float) revenueData.values().stream().mapToDouble(value -> value).sum();

// Update the total revenue label
        totalRevenueLabel.setText("Total Revenue: " + String.format("%.2f$", totalRevenue));

// Update the total revenue label based on the selected time period
        updateTotalRevenueLabel(timePeriod);

    }

    private void showDataLabels(BarChart<String, Float> chart) {
        // Display data labels on top of each bar
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
            // Get the current stage
            Stage currentStage = (Stage) label.getScene().getWindow();

            // Close the current window
            currentStage.close();

            // Load the new window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/updatereservation.fxml"));
            Parent root = loader.load();

            // Create the new stage
            Stage newStage = new Stage();
            newStage.setTitle("EDUWAVE");
            newStage.setScene(new Scene(root));

            // Show the new stage
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
