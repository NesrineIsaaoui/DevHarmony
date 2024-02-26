package Controllers;

import Models.Reservation;
import Services.ReservationService;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class UpdateReservationController {

    @FXML
    private TextField idTextField;

    @FXML
    private TextField userIdTextField;

    @FXML
    private TextField courseIdTextField;

    @FXML
    private RadioButton statusRadioButton;

    @FXML
    private DatePicker dateDatePicker;

    @FXML
    private TextField promoIdTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private Button saveButton;

    private ReservationService reservationService;

    private TableView<Reservation> userTableView;

    private Reservation selectedReservation;

    public void initialize() {
        reservationService = new ReservationService();
        initializeFields();
    }

    private void initializeFields() {
        // Initialize your fields based on selectedReservation data
        if (selectedReservation != null) {
            idTextField.setText(String.valueOf(selectedReservation.getId()));
            userIdTextField.setText(String.valueOf(selectedReservation.getId_user()));
            courseIdTextField.setText(String.valueOf(selectedReservation.getId_cours()));
            statusRadioButton.setSelected(selectedReservation.isResStatus());
            dateDatePicker.setValue(selectedReservation.getDateReservation().toLocalDate());
            promoIdTextField.setText(String.valueOf(selectedReservation.getId_codepromo()));
            priceTextField.setText(String.valueOf(selectedReservation.getPrixd()));
        }
    }

    public void setReservationService(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public void setUserTableView(TableView<Reservation> userTableView) {
        this.userTableView = userTableView;
    }

    public void setSelectedReservation(Reservation selectedReservation) {
        this.selectedReservation = selectedReservation;
    }

    @FXML
    private void saveReservation() {
        // Update the selected reservation with the new data
        selectedReservation.setId_user(Integer.parseInt(userIdTextField.getText()));
        selectedReservation.setId_cours(Integer.parseInt(courseIdTextField.getText()));
        selectedReservation.setResStatus(statusRadioButton.isSelected());
        selectedReservation.setDateReservation(dateDatePicker.getValue().atStartOfDay());
        selectedReservation.setId_codepromo(Integer.parseInt(promoIdTextField.getText()));
        selectedReservation.setPrixd(Float.parseFloat(priceTextField.getText()));

        // Update the reservation in the database
        reservationService.updateEntity(selectedReservation);

        // Close the update form
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();

        // Fetch reservations for the specified user ID again to refresh the data
        userTableView.getItems().setAll(reservationService.myReservation(selectedReservation.getId_user()));

        // Refresh the TableView
        userTableView.refresh();
    }
}
