package controller;

import model.CategorieCodePromo;
import service.CategorieCodePromoService;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CreateCodePromoController {

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

    public CreateCodePromoController() {
        categorieCodePromoService = new CategorieCodePromoService();
    }

    @javafx.fxml.FXML
    public void initialize() {
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
            // Handle deletion logic here
            codePromoData.remove(selectedCodePromo);
            System.out.println("Supprimer Code Promo: " + selectedCodePromo.getCode());
            // Delete from the database if needed
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
              showErrorAlert("Invalid Input", "Invalid input format for value or number of users. Please enter valid values.");
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

                    // Check if the code already exists in the data excluding the selected code promo
                    boolean isUniqueCode = codePromoData.stream()
                            .filter(promo -> promo.getId() != selectedCodePromo.getId())
                            .noneMatch(promo -> promo.getCode().equals(code));

                    if (isUniqueCode) {
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
                showErrorAlert("Invalid Input", "Invalid input format for value or number of users. Please enter valid values.");
            }
        }
    }




    private boolean isCodePromoCodeExists(String code) {
        return categorieCodePromoService.getCategorieByCode(code) != null;
    }

    private boolean isCodePromoCodeExists(String code, CategorieCodePromo excludedCodePromo) {
        CategorieCodePromo existingCodePromo = categorieCodePromoService.getCategorieByCode(code);
        return existingCodePromo != null && !existingCodePromo.equals(excludedCodePromo);
    }


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

            return true;
        }


    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


}
