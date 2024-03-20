package controller;

import model.CategorieCodePromo;
import service.CategorieCodePromoService;
import service.CodePromoService;
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
        String promoCode = code.getText().trim();
        float price = Float.parseFloat(prix.getText().trim());

        if (isValidPromoCode(promoCode, price)) {
            float discountedPrice = applyPromoCode(promoCode, price);
            displayDiscountedPrice(discountedPrice);

            updateNbUsers(promoCode);
            updateValidCodeField();
        } else {
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
        }
    }

    private void displayDiscountedPrice(float discountedPrice) {
        dcode.setText(String.valueOf(discountedPrice));
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
