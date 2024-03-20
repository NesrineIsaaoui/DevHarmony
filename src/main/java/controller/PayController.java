package controller;

import model.Reservation;
import service.ReservationService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import com.stripe.net.RequestOptions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
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
        if (validateInput()) {
            String token = createTestToken();

            if (token != null) {
                updatePaidStatusForSelectedReservations();
                showConfirmationDialog();
                showPaymentSuccessWindow();
            }
        }
    }


    private boolean validateInput() {
        if (cardNumberField.getText().isEmpty() || expiryDateField.getText().isEmpty() || cvvField.getText().isEmpty()) {
            showAlert("Input Error", "Please fill in all the required fields.");
            return false;
        }


        return true;
    }

    private void showPaymentSuccessWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PaymentSuccess.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Payment Successful");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void updatePaidStatusForSelectedReservations() {
        try {
            ReservationService reservationService = new ReservationService();

            List<Reservation> selectedReservations = reservationService.getReservationsByStatus(true);

            for (Reservation reservation : selectedReservations) {
                reservationService.addPaidStatus(reservation.getId(), true);
                reservation.setResStatus(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    @FXML
    public void returntoreservations(ActionEvent actionEvent) {

        try {
            URL fxmlUrl = getClass().getResource("/ReservationUserView.fxml");
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