package Controllers;

import Models.CategorieCodePromo;
import Services.CategorieCodePromoService;
import Services.CodePromoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.List;

public class CodePromoController {

    @FXML
    private Button btn;

    @FXML
    private TextField code;

    @FXML
    private TextField prix;

    @FXML
    private TextField dcode;
    private CodePromoService codePromoService;
    private CategorieCodePromoService categorieCodePromoService;
    @FXML
    private TextField validCodeField;



    public CodePromoController() {
        codePromoService = new CodePromoService();
        categorieCodePromoService = new CategorieCodePromoService();
    }


    @FXML
    void initialize() {
        updateValidCodeField();
    }

    @FXML
    void appliquer(ActionEvent event) {
        // Get the code and price from the text fields
        String promoCode = code.getText().trim();
        float price = Float.parseFloat(prix.getText().trim());

        // Check if the promo code is valid
        if (isValidPromoCode(promoCode, price)) {
            // Apply the promo code and update the price
            float discountedPrice = applyPromoCode(promoCode, price);
            // Display the discounted price or perform other actions
            displayDiscountedPrice(discountedPrice);

            // Update nb_users in CategorieCodePromo table
            updateNbUsers(promoCode);
            updateValidCodeField();
        } else {
            // Display an alert for an invalid promo code
            showAlert("Invalid Promo Code", "The entered promo code is not valid.");
        }
    }

    @FXML
    void updateValidCodeField() {
        String validCode = fetchValidCodeFromDatabase();
        validCodeField.setText(validCode);
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
    private boolean isValidPromoCode(String promoCode, float originalPrice) {
        // Retrieve the category for the given promo code
        CategorieCodePromo category = categorieCodePromoService.getCategorieByCode(promoCode);

        // Check if the category exists and the code can be used
        return category != null && category.getNb_users() > 0 && originalPrice > 0;
    }

    private float applyPromoCode(String promoCode, float originalPrice) {
        // Retrieve the category for the given promo code
        CategorieCodePromo category = categorieCodePromoService.getCategorieByCode(promoCode);

        // Check if the category exists
        if (category != null) {
            // Calculate the discounted price based on the discount percentage
            float discountPercentage = category.getValue();
            return originalPrice * (1 - discountPercentage);
        }

        // Default to the original price if the category is not found
        return originalPrice;
    }

    private void updateNbUsers(String promoCode) {
        // Retrieve the category for the given promo code
        CategorieCodePromo category = categorieCodePromoService.getCategorieByCode(promoCode);

        // Check if the category exists
        if (category != null && category.getNb_users() > 0) {
            // Decrement nb_users by 1 and update in the database
            category.setNb_users(category.getNb_users() - 1);
            categorieCodePromoService.updateCategorieCodePromo(category);
        }
    }

    private void displayDiscountedPrice(float discountedPrice) {
        // Update the TextField with the discounted price
        dcode.setText(String.valueOf(discountedPrice));
    }


    private void showAlert(String title, String content) {
        // Display an alert for informative messages or errors
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
