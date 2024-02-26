package Controllers;

import Models.Reservation;
import Services.ReservationService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class table {

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

    @FXML
    private Button insertButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    private ObservableList<Reservation> reservations;

    private ReservationService reservationService;

    @FXML
    private void initialize() {
        // Initialize the table and columns
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
            ReservationController addReservationController = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Add Reservation");
            stage.setScene(new Scene(root));
            stage.show();

            // You can also close the current stage if needed
            // Stage currentStage = (Stage) insertButton.getScene().getWindow();
            // currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void updateReservation() {
        Reservation selectedReservation = tableView.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {

            try {
                URL fxmlUrl = getClass().getResource("/updatereservation.fxml");
                System.out.println("FXML URL: " + fxmlUrl);
                if (fxmlUrl == null) {
                    throw new IOException("FXML file not found");
                }

                FXMLLoader loader = new FXMLLoader(fxmlUrl);
                Parent root = loader.load();
                UpdateReservationController updateController = loader.getController();
                updateController.setReservationService(reservationService);
                updateController.setUserTableView(tableView);
                updateController.setSelectedReservation(selectedReservation);
                Stage stage = new Stage();
                stage.setTitle("Update Reservation");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void deleteReservation() {
        // This is a placeholder method, implement your logic to delete a reservation from the database
        Reservation selectedReservation = tableView.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            // Delete the reservation from the database
            reservationService.deleteEntity(selectedReservation.getId());

            // Remove from the observable list
            reservations.remove(selectedReservation);
        }
    }

}
