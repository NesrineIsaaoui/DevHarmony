package Controllers;

import Models.Reservation;
import Services.ReservationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.function.Predicate;

public class ReservationViewUserController {

    @FXML
    private TableView<Reservation> tableView;

    @FXML
    private TableColumn<Reservation, Integer> idColumn;

    @FXML
    private TableColumn<Reservation, Integer> userIdColumn;

    @FXML
    private TableColumn<Reservation, Integer> courseIdColumn;

    @FXML
    private TableColumn<Reservation, Boolean> statusColumn;

    @FXML
    private TableColumn<Reservation, String> dateColumn;

    @FXML
    private TableColumn<Reservation, Integer> promoIdColumn;

    @FXML
    private TableColumn<Reservation, Float> priceColumn;

    private ObservableList<Reservation> reservations;

    private ReservationService reservationService;
    @FXML
    private TextField chid;
    @FXML
    private Button returntohome;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_cours"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("resStatus"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));
        promoIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_codepromo"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("prixd"));

        reservationService = new ReservationService();
        // Fetch reservations for the specified user ID (e.g., user ID 1)
        reservations = FXCollections.observableArrayList(reservationService.myReservation(1));

        // Set the data to the table
        tableView.setItems(reservations);

        chid.textProperty().addListener((observable, oldValue, newValue) -> filterTable(newValue));
    }

    private void filterTable(String keyword) {
        // Filter the table based on the search keyword
        List<Reservation> filteredList = reservations.stream()
                .filter(filterByKeyword(keyword))
                .collect(Collectors.toList());

        // Update the table with the filtered list
        tableView.setItems(FXCollections.observableArrayList(filteredList));
    }

    private Predicate<Reservation> filterByKeyword(String keyword) {
        // Define your filtering logic here
        return reservation ->
                String.valueOf(reservation.getId()).contains(keyword) ||
                        String.valueOf(reservation.getId_user()).contains(keyword) ||
                        String.valueOf(reservation.getId_cours()).contains(keyword) ||
                        String.valueOf(reservation.isResStatus()).contains(keyword) ||
                        String.valueOf(reservation.getDateReservation()).contains(keyword) ||
                        String.valueOf(reservation.getId_codepromo()).contains(keyword) ||
                        String.valueOf(reservation.getPrixd()).contains(keyword);
    }
    @FXML
    private void insertReservation() {
        try {
            URL fxmlUrl = getClass().getResource("/addreservationcours.fxml");
            if (fxmlUrl == null) {
                throw new IOException("FXML file not found");
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            // If you have a controller for addreservation.fxml, you can get it using:
            AddReservationCoursController addReservationController = loader.getController();

            // Get the reference to the current stage
            Stage currentStage = (Stage) tableView.getScene().getWindow();

            // Close the current stage
            currentStage.close();

            // Open the new stage
            Stage stage = new Stage();
            stage.setTitle("EDUWAVE");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }







    @FXML
    private void deleteReservation() {
        Reservation selectedReservation = tableView.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            // Show a confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete Reservation");
            alert.setContentText("Are you sure you want to delete this reservation?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // User clicked OK, proceed with deletion
                reservationService.deleteEntity(selectedReservation.getId());

                // Remove from the observable list
                reservations.remove(selectedReservation);

                // Refresh the TableView
                tableView.refresh();
            }
        }
    }


    @FXML
    public void pay(ActionEvent actionEvent) {
        try {
            // Calculate the sum of reservations with status true
            float sum = calculateSumOfReservations();

            // Get the current stage
            Stage currentStage = (Stage) returntohome.getScene().getWindow();

            // Close the current window
            currentStage.close();

            // Load the new window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Payment.fxml"));
            Parent root = loader.load();

            // Pass the sum to the PayController
            PayController payController = loader.getController();
            payController.setSum(sum);

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


    private float calculateSumOfReservations() {
        float sum = 0;
        for (Reservation reservation : reservations) {
            if (reservation.isResStatus()) {
                sum += reservation.getPrixd();
            }
        }
        return sum;
    }
    @FXML
    public void returntohome(ActionEvent actionEvent) {

        try {
            // Get the current stage
            Stage currentStage = (Stage) returntohome.getScene().getWindow();

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
