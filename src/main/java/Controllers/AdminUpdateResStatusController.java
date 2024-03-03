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
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
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
    @FXML
    private Button chart;
    @FXML
    private Button excel1;
    @FXML
    private TableColumn paidcol;

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
        paidcol.setCellValueFactory(new PropertyValueFactory<>("paidStatus"));

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
                        || String.valueOf(reservation.getPrixd()).toLowerCase().contains(lowerCaseFilter)
                        || String.valueOf(reservation.isPaidStatus()).toLowerCase().contains(lowerCaseFilter);


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


    @FXML
    public void piechart(ActionEvent actionEvent) {
        try {
            // Get the current stage
            Stage currentStage = (Stage) saveButton.getScene().getWindow();

            // Close the current window
            currentStage.close();

            // Load the new window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reservationchart.fxml"));
            Parent root = loader.load();

            // Get the controller of the new window
            ReservationChartController reservationChartController = loader.getController();

            // Fetch reservations using your ReservationService or your data retrieval logic
            ReservationService reservationService = new ReservationService(); // Replace with your actual service
            ObservableList<Reservation> reservations = FXCollections.observableArrayList(reservationService.getAllReservations());

            // Pass the list of reservations to the ReservationChartController
            reservationChartController.setReservations(reservations);

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
    public void fileexcel(ActionEvent actionEvent) {
        try {
            // Create a new Workbook
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Reservations");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Reservation ID");
            headerRow.createCell(1).setCellValue("User ID");
            headerRow.createCell(2).setCellValue("Course ID");
            headerRow.createCell(3).setCellValue("Status");
            headerRow.createCell(4).setCellValue("Reservation Date");
            headerRow.createCell(5).setCellValue("Promo ID");
            headerRow.createCell(6).setCellValue("Price");

            // Fill data rows
            int rowNum = 1;
            for (Reservation reservation : Data) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(reservation.getId());
                row.createCell(1).setCellValue(reservation.getId_user());
                row.createCell(2).setCellValue(reservation.getId_cours());
                row.createCell(3).setCellValue(reservation.isResStatus());
                row.createCell(4).setCellValue(reservation.getDateReservation().toString());
                row.createCell(5).setCellValue(reservation.getId_codepromo());
                row.createCell(6).setCellValue(reservation.getPrixd());
            }

            // Save the workbook to a file
            try (FileOutputStream fileOut = new FileOutputStream("reservations.xlsx")) {
                workbook.write(fileOut);
            }

            // Close the workbook to release resources
            workbook.close();

            System.out.println("Excel file created successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}