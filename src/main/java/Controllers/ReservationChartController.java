package Controllers;

import Services.ReservationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import Models.Reservation;

import java.util.Map;
import java.util.stream.Collectors;

public class ReservationChartController {

    @FXML
    private PieChart userCoursePieChart;

    private ObservableList<Reservation> reservations;

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
    }

    // Event handler to show label on mouse move
    private void showLabel(MouseEvent event) {
        PieChart.Data dataNode = (PieChart.Data) event.getSource();

        // Use lookup to find the text node associated with the PieChart.Data
        Text chartText = (Text) dataNode.getNode().lookup(".chart-pie-label");

        // Set label text to display the number of users
        label.setText(chartText.getText());
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

}
