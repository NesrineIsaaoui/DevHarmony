package Controllers;

import Models.CategorieCodePromo;
import Services.CategorieCodePromoService;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class AdminAjoutCodePromoController {

    @javafx.fxml.FXML
    private Button ajouter;
    @javafx.fxml.FXML
    private Button supprimer;
    @javafx.fxml.FXML
    private Button modifier;
    @javafx.fxml.FXML
    private TableView<CategorieCodePromo> CodePromoTable;
    @javafx.fxml.FXML
    private TableColumn<CategorieCodePromo,Integer> Id;
    @javafx.fxml.FXML
    private TableColumn<CategorieCodePromo,Integer> nb_users;
    @javafx.fxml.FXML
    private TableColumn<CategorieCodePromo,String> Code;
    @javafx.fxml.FXML
    private TableColumn<CategorieCodePromo,Float> value;
    private ObservableList<CategorieCodePromo> codePromoData;
    private CategorieCodePromoService categorieCodePromoService;
    @javafx.fxml.FXML
    private TextField codeField;
    @javafx.fxml.FXML
    private TextField valueField;
    @javafx.fxml.FXML
    private TextField nbUsersField;
    @javafx.fxml.FXML
    private TextField codem;

    @javafx.fxml.FXML
    private TextField nbm;
    @javafx.fxml.FXML
    private TextField valm;
    @javafx.fxml.FXML
    private TextField chid;
    @javafx.fxml.FXML
    private Label code;
    @javafx.fxml.FXML
    private Label code1;
    @javafx.fxml.FXML
    private Label val1;
    @javafx.fxml.FXML
    private Button calander;
    @javafx.fxml.FXML
    private Button promos;
    @javafx.fxml.FXML
    private Label valeur;
    @javafx.fxml.FXML
    private Button profile;
    @javafx.fxml.FXML
    private Label nbuser;
    @javafx.fxml.FXML
    private Button reservations;
    @javafx.fxml.FXML
    private Label nb1;

    public AdminAjoutCodePromoController() {
        categorieCodePromoService = new CategorieCodePromoService();
    }

    @javafx.fxml.FXML
    public void initialize() {
        // Initialize data for the TableView (fetch from the database)
        codePromoData = FXCollections.observableArrayList(categorieCodePromoService.getAllCategories());

        // Set cell value factories for each column
        Id.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        Code.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCode()));
        value.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getValue()).asObject());
        nb_users.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNb_users()).asObject());

        // Set the items in the TableView
        CodePromoTable.setItems(codePromoData);
        CodePromoTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFieldsForModification();
            }
        });
        // Create a filtered list to hold the filtered data
        FilteredList<CategorieCodePromo> filteredData = new FilteredList<>(codePromoData, p -> true);

        chid.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(codePromo -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Show all data when search field is empty
                }

                String lowerCaseFilter = newValue.toLowerCase();

                return String.valueOf(codePromo.getId()).toLowerCase().contains(lowerCaseFilter)
                        || codePromo.getCode().toLowerCase().contains(lowerCaseFilter)
                        || String.valueOf(codePromo.getValue()).toLowerCase().contains(lowerCaseFilter)
                        || String.valueOf(codePromo.getNb_users()).toLowerCase().contains(lowerCaseFilter);
            });
        });


        // Wrap the filtered data in a SortedList
        SortedList<CategorieCodePromo> sortedData = new SortedList<>(filteredData);

        // Bind the SortedList comparator to the TableView comparator
        sortedData.comparatorProperty().bind(CodePromoTable.comparatorProperty());

        // Set the items in the TableView to the sorted and filtered data
        CodePromoTable.setItems(sortedData);
    }
    @javafx.fxml.FXML
    public void populateFieldsForModification() {
        // Get the selected code promo from the TableView
        CategorieCodePromo selectedCodePromo = CodePromoTable.getSelectionModel().getSelectedItem();

        if (selectedCodePromo != null) {
            // Set the values to the text fields for modification
            codem.setText(selectedCodePromo.getCode());
            valm.setText(String.valueOf(selectedCodePromo.getValue()));
            nbm.setText(String.valueOf(selectedCodePromo.getNb_users()));
        }
    }

    @javafx.fxml.FXML
    public void supprimerCode(ActionEvent actionEvent) {
        CategorieCodePromo selectedCodePromo = CodePromoTable.getSelectionModel().getSelectedItem();
        if (selectedCodePromo != null) {
            codePromoData.remove(selectedCodePromo);
            System.out.println("Supprimer Code Promo: " + selectedCodePromo.getCode());
            categorieCodePromoService.deleteCategorieCodePromo(selectedCodePromo.getId());
        }
    }


    @javafx.fxml.FXML
    public void ajouterCode(ActionEvent actionEvent) {
        String code = codeField.getText();
        String valueText = valueField.getText();
        String nbUsersText = nbUsersField.getText();

        if (isValidInput(code, valueText, nbUsersText)) {
            try {
                int nbUsers = Integer.parseInt(nbUsersText);
                float value = Float.parseFloat(valueText);
                if (value <= 0 || nbUsers<0 ) {

                    showErrorAlert("Invalid Input", "Value must be a positive number.");
                    return;
                }
                // Check if the code already exists in the data
                if (!isCodePromoCodeExists(code)) {
                    // Add the new code promo to the TableView
                    CategorieCodePromo newCodePromo = new CategorieCodePromo(code, value, nbUsers);
                    CategorieCodePromoService newCodePromos = new CategorieCodePromoService();

                    // Check if the code exists before adding
                    CategorieCodePromo existingCodePromo = newCodePromos.getCategorieByCode(code);
                    if (existingCodePromo == null) {
                        // Add the code to the database and get the generated ID
                        int generatedId = newCodePromos.addCategorieCodePromoId(newCodePromo);

                        // Set the generated ID
                        newCodePromo.setId_code(generatedId);

                        codePromoData.add(newCodePromo);

                        // Clear input fields
                        codeField.clear();
                        valueField.clear();
                        nbUsersField.clear();

                        System.out.println("Ajouter Code Promo: " + code + " with ID: " + generatedId);
                    } else {
                        showErrorAlert("Code Promo Error", "Code Promo with code " + code + " already exists.");
                    }
                } else {
                    showErrorAlert("Code Promo Error", "Code Promo with code " + code + " already exists.");
                }
            } catch (NumberFormatException e) {
                showErrorAlert("Invalid Input", "Invalid input format . Please enter valid values.");
            }
        }
    }

    @FXML
    public void modifierCode(ActionEvent actionEvent) {
        CategorieCodePromo selectedCodePromo = CodePromoTable.getSelectionModel().getSelectedItem();
        if (selectedCodePromo != null) {
            try {
                // Get values from the text fields
                String code = codem.getText();
                String valueText = valm.getText();
                String nbUsersText = nbm.getText();

                if (isValidInput(code, valueText, nbUsersText)) {
                    int nbUsers = Integer.parseInt(nbUsersText);
                    float value = Float.parseFloat(valueText);

                    if (value <= 0 || nbUsers < 0) {
                        showErrorAlert("Invalid Input", "Value and Number of Users must be positive numbers.");
                        return;
                    }

                    // Check if the code already exists in the data excluding the selected code promo
                    if (!isCodePromoCodeExists(code, selectedCodePromo)) {
                        // Update the selected code promo
                        selectedCodePromo.setCode(code);
                        selectedCodePromo.setValue(value);
                        selectedCodePromo.setNb_users(nbUsers);

                        // Update the TableView and the database
                        int selectedIndex = CodePromoTable.getSelectionModel().getSelectedIndex();
                        codePromoData.set(selectedIndex, selectedCodePromo);
                        categorieCodePromoService.updateCategorieCodePromo(selectedCodePromo);

                        // Clear input fields
                        codem.clear();
                        valm.clear();
                        nbm.clear();

                        System.out.println("Modifier Code Promo: " + selectedCodePromo.getCode());
                    } else {
                        showErrorAlert("Code Promo Error", "Code Promo with code " + code + " already exists.");
                    }
                }
            } catch (NumberFormatException e) {
                showErrorAlert("Invalid Input", "Invalid input format. Please enter valid values.");
            }
        }
    }

    private boolean isCodePromoCodeExists(String code, CategorieCodePromo excludedCodePromo) {
        // Get all code promos excluding the selected code promo
        List<CategorieCodePromo> allCodePromosExceptSelected = codePromoData.stream()
                .filter(promo -> promo.getId() != excludedCodePromo.getId())
                .collect(Collectors.toList());

        // Check if the code already exists in the remaining code promos
        return allCodePromosExceptSelected.stream().anyMatch(promo -> promo.getCode().equals(code));
    }

    private boolean isCodePromoCodeExists(String code) {
        return categorieCodePromoService.getCategorieByCode(code) != null;
    }




    // Helper method to check if the input values are valid
    private boolean isValidInput(String code, String value, String nbUsers) {
        if (code.isEmpty() || value.isEmpty() || nbUsers.isEmpty()) {
            showErrorAlert("Invalid Input", "All fields must be filled.");
            return false;
        }

        // Additional validation for integer fields
        try {
            Integer.parseInt(nbUsers);
        } catch (NumberFormatException e) {
            showErrorAlert("Invalid Input", "Number of users must be an integer.");
            return false;
        }
        //float
        try {
            float floatValue = Float.parseFloat(value);
            if (floatValue <= 0) {
                showErrorAlert("Invalid Input", "Value must be a positive number.");
                return false;
            }
        } catch (NumberFormatException e) {
            showErrorAlert("Invalid Input", "Value must be a valid number.");
            return false;
        }

        return true;
    }


    // Helper method to show an error alert
    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @javafx.fxml.FXML  /// a verifier
    public void reservationscharts(ActionEvent actionEvent) {

        try {
            URL fxmlUrl = getClass().getResource("/updatereservation.fxml");
            System.out.println("FXML URL: " + fxmlUrl);
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the current stage
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("EDUWAVE");

            // Set the new scene in the stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @javafx.fxml.FXML
    public void promospage(ActionEvent actionEvent) {



    }

    @javafx.fxml.FXML
    public void calanderpage(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void profileadmin(ActionEvent actionEvent) {
        try {
            // Get the current stage
            Stage currentStage = (Stage) ajouter.getScene().getWindow();

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

