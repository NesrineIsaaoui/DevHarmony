package controller;

import model.CategorieCodePromo;
import model.Cours;
import model.Reservation;
import model.User;
import service.CategorieCodePromoService;
import service.CodePromoService;
import service.ReservationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.FloatStringConverter;

import static controller.SeconnecterController.idCurrentUser;

public class AddReservationCoursController implements Initializable {

    @FXML
    private TextField priceField;

    @FXML
    private TextField promoCodeField;

    @FXML
    private TextField discountedPriceField;

    private CodePromoService codePromoService;
    private CategorieCodePromoService categorieCodePromoService;
    @FXML
    private TextField codeavailble;
    private boolean isReserverButtonClicked = false;
    @FXML
    private Button returntocours;
    private int loggedInUserId;

    public void setLoggedInUserId(int userId) {
        this.loggedInUserId = userId;
    }
    public AddReservationCoursController() {
        codePromoService = new CodePromoService();
        categorieCodePromoService = new CategorieCodePromoService();
    }
    private Cours selectedCours;

    public void setCours(Cours cours) {
        this.selectedCours = cours;
        priceField.setText(String.valueOf(selectedCours.getCoursPrix()));

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupInputValidation();
        updateValidCodeField();
        if (selectedCours != null) {
            setCours(selectedCours);
        }

    }
    private void setupInputValidation() {
        setupFloatInputValidation(priceField);

        promoCodeField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isReserverButtonClicked && newValue.trim().isEmpty()) {
                showAlert("Invalid Input", "Promo code cannot be empty.");
                promoCodeField.setText(oldValue);
            }
        });

        setupFloatInputValidation(discountedPriceField);
    }

    private void setupFloatInputValidation(TextField textField) {
        StringConverter<Float> converter = new FloatStringConverter();
        TextFormatter<Float> textFormatter = new TextFormatter<>(converter, 0.0f,
                c -> {
                    if (c.getControlNewText().matches("-?\\d*\\.?\\d*")) {
                        return c;
                    } else {
                        return null;
                    }
                });
        textField.setTextFormatter(textFormatter);
    }

    @FXML
    void applyPromoCode(ActionEvent event) {
        float price = Float.parseFloat(priceField.getText().trim());
        String promoCode = promoCodeField.getText().trim();

        if (isValidPromoCode(promoCode, price)) {
            float discountedPrice = applyPromoCode(promoCode, price);
            displayDiscountedPrice(discountedPrice);

            updateNbUsers(promoCode);
        } else {
            showAlert("Invalid Promo Code", "The entered promo code is not valid.");
        }
    }
    @FXML
    void addReservation(ActionEvent event) {
        try {
            if (loggedInUserId == 0) {
                showAlert("Authentication Error", "User not authenticated. Please log in.");
                return;
            }


            isReserverButtonClicked = true;
            float originalPrice = Float.parseFloat(priceField.getText().trim());
            String promoCode = promoCodeField.getText().trim();
            float discountedPrice = Float.parseFloat(discountedPriceField.getText().trim());

            if (originalPrice <= 0 || discountedPrice <= 0) {
                showAlert("Invalid Input", "Please enter valid numeric values for the price and discounted price.");
                return;
            }

            Reservation reservation = new Reservation();
            reservation.setId_user(loggedInUserId);  // user ID
            reservation.setId_cours(selectedCours.getId());
            reservation.setResStatus(false);
            reservation.setDateReservation(LocalDateTime.now());
            reservation.setPaidStatus(false);

            CategorieCodePromoService categorieCodePromoService = new CategorieCodePromoService();
            int promoCodeId = categorieCodePromoService.getIdByCode(promoCode);

            reservation.setId_codepromo(promoCodeId); //  promo code ID
            reservation.setPrixd(discountedPrice);

            ReservationService reservationService = new ReservationService();
            reservationService.addEntity(reservation);

            showAlert("Reservation Added", "Reservation added successfully.");
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText(null);
            confirmation.setContentText("Do you want to see your reservations?");

            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReservationUserView.fxml"));
                        Parent root = loader.load();
                        Stage newStage = new Stage();
                        newStage.setScene(new Scene(root));
                        newStage.show();

                        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        currentStage.hide();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter valid numeric values for the price and discounted price.");
        } finally {
            isReserverButtonClicked = false;
        }
    }


    private boolean isValidPromoCode(String promoCode, float originalPrice) {
        CategorieCodePromo category = categorieCodePromoService.getCategorieByCode(promoCode);
        return category != null && category.getNb_users() > 0 && originalPrice > 0;
    }

    private float applyPromoCode(String promoCode, float originalPrice) {
        CategorieCodePromo category = categorieCodePromoService.getCategorieByCode(promoCode);
        if (category != null) {
            float discountPercentage = category.getValue();
            return originalPrice * (1 - discountPercentage);
        }
        return originalPrice;
    }

    private void updateNbUsers(String promoCode) {
        CategorieCodePromo category = categorieCodePromoService.getCategorieByCode(promoCode);
        if (category != null && category.getNb_users() > 0) {
            category.setNb_users(category.getNb_users() - 1);
            categorieCodePromoService.updateCategorieCodePromo(category);
            updateValidCodeField();
        }
    }

    private void displayDiscountedPrice(float discountedPrice) {
        discountedPriceField.setText(String.valueOf(discountedPrice));
    }

    private void updateValidCodeField() {
        String validCode = fetchValidCodeFromDatabase();
        codeavailble.setText(validCode);
    }

    private String fetchValidCodeFromDatabase() {
        List<CategorieCodePromo> allPromoCodes = categorieCodePromoService.getAllCategories();
        for (CategorieCodePromo promoCode : allPromoCodes) {
            if (promoCode.getNb_users() > 0) {
                return promoCode.getCode();
            }
        }
        return "NoValidCodesAvailable";
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @FXML
    public void returntocourslist(ActionEvent actionEvent) {
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
