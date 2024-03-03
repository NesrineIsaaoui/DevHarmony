package Controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import com.stripe.net.RequestOptions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PayController {

    @FXML
    private TextField expiryDateField;
    @FXML
    private Text somme;
    @FXML
    private TextField cardNumberField;
    @FXML
    private AnchorPane nh;
    @FXML
    private TextField cvvField;
    private float sum;
    @FXML
    private VBox vbox;


    @FXML
    private void payNowButton(ActionEvent event) {
        String token = createTestToken();

        if (token != null) {
            showConfirmationDialog();
        }
    }

    public void setSum(float sum) {
        this.sum = sum;
        somme.setText("Somme à payer: " + String.valueOf(sum) + "$");
    }


    private String createTestToken() {
        try {
            Token token = Token.retrieve("tok_visa", RequestOptions.builder().setApiKey("sk_test_51Oj3svCXr5Afrxv4MljO3Ftvb6voQEdB9nctTfpnvuKiy8LvvQGdaP2bQO7LCIhlFhkohFlgTaJtOFFLcWwlJ7bz00d8oFypNE").build());

            return token.getId();
        } catch (StripeException e) {
            handleStripeException("Error retrieving test token", e.getMessage());
            return null;
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showInformationAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void chargeCard(String token) {
        try {
            Stripe.apiKey = "sk_test_51Oj3svCXr5Afrxv4MljO3Ftvb6voQEdB9nctTfpnvuKiy8LvvQGdaP2bQO7LCIhlFhkohFlgTaJtOFFLcWwlJ7bz00d8oFypNE"; // Replace with your actual secret key

            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", 1000); // Amount in cents (e.g., $10)
            chargeParams.put("currency", "usd");
            chargeParams.put("source", token);

            Charge charge = Charge.create(chargeParams);

            if (charge.getPaid()) {
                showInformationAlert("Payment Successful", "Your payment was successful.");
            } else {
                showAlert("Payment Error", "Payment failed. Please try again.");
            }
        } catch (StripeException e) {
            handleStripeException("Error processing payment", e.getMessage());
        }
    }

    private void handleStripeException(String title, String errorMessage) {
        showAlert("Payment Error", title + ": " + errorMessage);
    }

    private void showConfirmationDialog() {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Payment");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to make this payment?");
        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            chargeCard(createTestToken());
        }
    }
}
