package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import model.Reservation;
import service.ReservationService;
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

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_cours"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("resStatus"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));
        promoIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_codepromo"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("prixd"));
        paidcol.setCellValueFactory(new PropertyValueFactory<>("paidStatus"));

        tableView.setItems(Data);


        FilteredList<Reservation> filteredData = new FilteredList<>(Data, p -> true);

        chid.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(reservation -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
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


        SortedList<Reservation> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedData);

    }


    @FXML
    void saveReservation(ActionEvent event) {
        Reservation selectedReservation = tableView.getSelectionModel().getSelectedItem();

        if (selectedReservation != null) {
            selectedReservation.setResStatus(statusRadioButton.isSelected());
            if (!statusRadioButton.isSelected()) {
                selectedReservation.setResStatus(false);
            }
            reservationService.updateEntity(selectedReservation);

            tableView.refresh();

            clearUI();
        }
    }

    private void clearUI() {
        chid.clear();

        saveButton.setDisable(true);

        tableView.getSelectionModel().clearSelection();
        statusRadioButton.setSelected(false);

        saveButton.setDisable(false);
    }


    @FXML
    public void promospage(ActionEvent actionEvent) {
        try {
            Stage currentStage = (Stage) saveButton.getScene().getWindow();

            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/adminajoutcodep.fxml"));
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
    public void calanderpage(ActionEvent actionEvent) {
    }

    @FXML
    public void profileadmin(ActionEvent actionEvent) {
        try {
            URL fxmlUrl = getClass().getResource("/HomeEnseignant.fxml");
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


    @FXML
    public void piechart(ActionEvent actionEvent) {
        try {
            Stage currentStage = (Stage) saveButton.getScene().getWindow();

            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reservationchart.fxml"));
            Parent root = loader.load();

            ReservationChartController reservationChartController = loader.getController();

            ReservationService reservationService = new ReservationService();
            ObservableList<Reservation> reservations = FXCollections.observableArrayList(reservationService.getAllReservations());

            reservationChartController.setReservations(reservations);

            Stage newStage = new Stage();
            newStage.setTitle("EDUWAVE");
            newStage.setScene(new Scene(root));

            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void fileexcel(ActionEvent actionEvent) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Reservations");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Reservation ID");
            headerRow.createCell(1).setCellValue("User ID");
            headerRow.createCell(2).setCellValue("Course ID");
            headerRow.createCell(3).setCellValue("Status");
            headerRow.createCell(4).setCellValue("Reservation Date");
            headerRow.createCell(5).setCellValue("Promo ID");
            headerRow.createCell(6).setCellValue("Price");

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

            try (FileOutputStream fileOut = new FileOutputStream("reservations.xlsx")) {
                workbook.write(fileOut);
            }

            workbook.close();

            System.out.println("Excel file created successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}