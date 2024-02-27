package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import Models.Reservation;
import Services.ReservationService;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminUpdateResStatusController implements Initializable {

    @FXML
    private TableView<Reservation> tableView;

    @FXML
    private RadioButton statusRadioButton;

    @FXML
    private TextField chid;

    @FXML
    private Button saveButton;

    private ReservationService reservationService;
    @FXML
    private Button profile;
    @FXML
    private TableColumn courseIdColumn;
    @FXML
    private Button calander;
    @FXML
    private TableColumn promoIdColumn;
    @FXML
    private TableColumn userIdColumn;
    @FXML
    private Button reservations;
    @FXML
    private TableColumn statusColumn;
    @FXML
    private TableColumn dateColumn;
    @FXML
    private Button promos;
    @FXML
    private TableColumn priceColumn;
    @FXML
    private TableColumn idColumn;
    private ObservableList<Reservation> Data;

    public AdminUpdateResStatusController() {
        this.reservationService = new ReservationService();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Data = FXCollections.observableArrayList(reservationService.getAllReservations());

        // Set cell value factories for each column
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_cours"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("resStatus"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));
        promoIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_codepromo"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("prixd"));

        // Set the items in the TableView
        tableView.setItems(Data);


        // Create a filtered list to hold the filtered data
        FilteredList<Reservation> filteredData = new FilteredList<>(Data, p -> true);

        chid.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(reservation -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Show all data when search field is empty
                }

                String lowerCaseFilter = newValue.toLowerCase();
                return String.valueOf(reservation.getId()).toLowerCase().contains(lowerCaseFilter)
                        || String.valueOf(reservation.getId_user()).toLowerCase().contains(lowerCaseFilter)
                        || String.valueOf(reservation.getId_cours()).toLowerCase().contains(lowerCaseFilter)
                        || String.valueOf(reservation.isResStatus()).toLowerCase().contains(lowerCaseFilter)
                        || reservation.getDateReservation().toString().toLowerCase().contains(lowerCaseFilter)
                        || String.valueOf(reservation.getId_codepromo()).toLowerCase().contains(lowerCaseFilter)
                        || String.valueOf(reservation.getPrixd()).toLowerCase().contains(lowerCaseFilter);

            });
        });


        // Wrap the filtered data in a SortedList
        SortedList<Reservation> sortedData = new SortedList<>(filteredData);

        // Bind the SortedList comparator to the TableView comparator
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        // Set the items in the TableView to the sorted and filtered data
        tableView.setItems(sortedData);

    }


    @FXML
    void saveReservation(ActionEvent event) {
        // Retrieve the selected reservation from the TableView
        Reservation selectedReservation = tableView.getSelectionModel().getSelectedItem();

        if (selectedReservation != null) {
            // Update the status based on the RadioButton
            selectedReservation.setResStatus(statusRadioButton.isSelected());
// If statusRadioButton is not selected, set status to false
            if (!statusRadioButton.isSelected()) {
                selectedReservation.setResStatus(false);
            }
            // Update the reservation in the database
            reservationService.updateEntity(selectedReservation);

            // Refresh the TableView to reflect the changes
            tableView.refresh();

            // Clear the UI components and disable the saveButton
            clearUI();
        }
    }

    private void clearUI() {
        chid.clear();
        // Clear other UI components as needed

        // Disable the saveButton
        saveButton.setDisable(true);

        // Clear the selection in the TableView
        tableView.getSelectionModel().clearSelection();
        statusRadioButton.setSelected(false);

        saveButton.setDisable(false);
    }

    @FXML
    public void reservationscharts(ActionEvent actionEvent) {
    }
@FXML
public void promospage(ActionEvent actionEvent) {
    try {
        // Get the current stage
        Stage currentStage = (Stage) saveButton.getScene().getWindow();

        // Close the current window
        currentStage.close();

        // Load the new window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/adminajoutcodep.fxml"));
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


    @FXML
    public void calanderpage(ActionEvent actionEvent) {
    }

    @FXML
    public void profileadmin(ActionEvent actionEvent) {

        try {
            // Get the current stage
            Stage currentStage = (Stage) saveButton.getScene().getWindow();

            // Close the current window
            currentStage.close();

            // Load the new window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addreservationcours.fxml"));
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
