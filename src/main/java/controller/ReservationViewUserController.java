package controller;

import javafx.scene.Node;
import model.Reservation;
import service.ReservationService;
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

import static controller.SeconnecterController.idCurrentUser;

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
    private TableColumn paidcol;
    @FXML
    private Button paybtn;

    public void setLoggedInUserId(int userId) {
        idCurrentUser = userId;
    }


    @FXML
    private void initialize() {
        if (idCurrentUser == 0) {
            showAlert("Authentication Error", "User not authenticated. Please log in.");
            return;
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_cours"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("resStatus"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));
        promoIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_codepromo"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("prixd"));
        paidcol.setCellValueFactory(new PropertyValueFactory<>("paidStatus"));

        reservationService = new ReservationService();
        reservations = FXCollections.observableArrayList(reservationService.getReservationsByStatusAndUser(false,idCurrentUser));

        tableView.setItems(reservations);

        chid.textProperty().addListener((observable, oldValue, newValue) -> filterTable(newValue));

        boolean hasTrueStatusReservations = reservations.stream().anyMatch(Reservation::isResStatus);

        paybtn.setDisable(!hasTrueStatusReservations);
    }
/*
    @FXML
    private void initialize() {
       
            // Check if the user is authenticated
            if (loggedInUserId == 0) {
                showAlert("Authentication Error", "User not authenticated. Please log in.");
                return;
            }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_cours"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("resStatus"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));
        promoIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_codepromo"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("prixd"));
        paidcol.setCellValueFactory(new PropertyValueFactory<>("paidStatus"));
        reservationService = new ReservationService();
        reservations = FXCollections.observableArrayList(reservationService.getReservationsByStatusAndUser(false, loggedInUserId));

        // Set the data to the table
        tableView.setItems(reservations);

        chid.textProperty().addListener((observable, oldValue, newValue) -> filterTable(newValue));

        // Check if there are reservations with a status of true
        boolean hasTrueStatusReservations = reservations.stream().anyMatch(Reservation::isResStatus);

        // Enable or disable the "Pay" button based on the condition
        paybtn.setDisable(!hasTrueStatusReservations);
    }
*/
    private void showAlert(String authenticationError, String s) {
    }

    private void filterTable(String keyword) {
        List<Reservation> filteredList = reservations.stream()
                .filter(filterByKeyword(keyword))
                .collect(Collectors.toList());

        tableView.setItems(FXCollections.observableArrayList(filteredList));
    }

    private Predicate<Reservation> filterByKeyword(String keyword) {
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
            Stage currentStage = (Stage) returntohome.getScene().getWindow();

            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCoursFront.fxml"));
            Parent root = loader.load();

            Stage newStage = new Stage();
            newStage.setTitle("EDUWAVE");
            newStage.setScene(new Scene(root));

            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }







    @FXML
    private void deleteReservation() {
        Reservation selectedReservation = tableView.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete Reservation");
            alert.setContentText("Are you sure you want to delete this reservation?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                reservationService.deleteEntity(selectedReservation.getId());

                reservations.remove(selectedReservation);

                tableView.refresh();
            }
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
    public void pay(ActionEvent actionEvent) {
        try {
            float sum = calculateSumOfReservations();

            Stage currentStage = (Stage) returntohome.getScene().getWindow();

            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Payment.fxml"));
            Parent root = loader.load();

            PayController payController = loader.getController();
            payController.setSum(sum);



            Stage newStage = new Stage();
            newStage.setTitle("EDUWAVE");
            newStage.setScene(new Scene(root));

            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    public void returntohome(ActionEvent actionEvent) {
        try {
            URL fxmlUrl = getClass().getResource("/AfficherCoursFront.fxml");
            System.out.println("FXML URL: " + fxmlUrl);
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("EDUWAVE");

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}