package Controllers;

import Models.CategorieCodePromo;
import Models.Reservation;
import Services.CategorieCodePromoService;
import Services.CodePromoService;
import Services.ReservationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import javafx.util.converter.FloatStringConverter;

import java.net.URL;
import java.util.ResourceBundle;
public class ReservationController implements Initializable {

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

    public ReservationController() {
        codePromoService = new CodePromoService();
        categorieCodePromoService = new CategorieCodePromoService();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupInputValidation();
        updateValidCodeField();

    }
    private void setupInputValidation() {
        // Validate priceField (allow only positive floats)
        setupFloatInputValidation(priceField);

        // Validate promoCodeField (allow any non-empty string)
        promoCodeField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isReserverButtonClicked && newValue.trim().isEmpty()) {
                showAlert("Invalid Input", "Promo code cannot be empty.");
                promoCodeField.setText(oldValue);
            }
        });

        // Validate discountedPriceField (allow only positive floats)
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

            // Update nb_users in CategorieCodePromo table
            updateNbUsers(promoCode);
        } else {
            showAlert("Invalid Promo Code", "The entered promo code is not valid.");
        }
    }

    @FXML
    void addReservation(ActionEvent event) {
        try {
            // Set the flag to indicate "Reserver" button is clicked
            isReserverButtonClicked = true;
            // Get the necessary data from the UI
            float originalPrice = Float.parseFloat(priceField.getText().trim());
            String promoCode = promoCodeField.getText().trim();
            float discountedPrice = Float.parseFloat(discountedPriceField.getText().trim());

            // Create a Reservation object and set its properties
            Reservation reservation = new Reservation();
            reservation.setId_user(1);
            reservation.setId_cours(1);
            reservation.setResStatus(false); // Set the reservation status
            reservation.setDateReservation(LocalDateTime.now()); // Set the current date and time

            // Use CategorieCodePromoService to get the id of the promo code
            CategorieCodePromoService categorieCodePromoService = new CategorieCodePromoService();
            int promoCodeId = categorieCodePromoService.getIdByCode(promoCode);

            reservation.setId_codepromo(promoCodeId); // Set the promo code ID
            reservation.setPrixd(discountedPrice);

            // Add the reservation using your ReservationService
            ReservationService reservationService = new ReservationService();
            reservationService.addEntity(reservation);

            // Display a success message
            showAlert("Reservation Added", "Reservation added successfully.");
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter valid numeric values for the price and discounted price.");
        } finally {
            // Reset the flag after processing
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


}
