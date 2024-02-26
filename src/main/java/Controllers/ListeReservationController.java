package Controllers;

import Models.Reservation;
import Services.ReservationService;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ListeReservationController implements Initializable {
    private int id;

    private int id_user;

    private int id_cours;
    private boolean resStatus;
    private LocalDateTime dateReservation;
    private int id_codepromo;

    private float prixd;

    @javafx.fxml.FXML
    private ImageView fx_return;
    @javafx.fxml.FXML
    private Button mod;
    @javafx.fxml.FXML
    private Button supprimer;
    @javafx.fxml.FXML
    private AnchorPane nh;
    @javafx.fxml.FXML
    private ListView afficherreservation;
    @javafx.fxml.FXML
    private Button returnToAdd;

    @javafx.fxml.FXML
    public void mod(ActionEvent actionEvent) {
        ListView<Reservation> list = afficherreservation;
        ReservationService inter = new ReservationService();
        int selectedIndex = list.getSelectionModel().getSelectedIndex();
        Reservation A = list.getSelectionModel().getSelectedItem();

        id = A.getId();
        id_cours=A.getId_cours();
        dateReservation=A.getDateReservation();
        resStatus=A.isResStatus();
        prixd=A.getPrixd();
        id_user=A.getId_user();
        id_codepromo=A.getId_codepromo();
        try {

            Parent page1
                    = FXMLLoader.load(getClass().getResource("/ModifierCours.fxml"));
            Scene scene = new Scene(page1);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {

        }
    }

    @javafx.fxml.FXML
    public void returnToLocation(Event event) {
    }

    @javafx.fxml.FXML
    public void supprimer_cours(ActionEvent actionEvent) {
        ListView<Reservation> list1 = afficherreservation;
        ReservationService inter = new ReservationService();
        int selectedIndex = list1.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Reservation A = list1.getSelectionModel().getSelectedItem();
            System.out.println(A.getId());
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de la suppression");
            confirmationAlert.setHeaderText("Êtes-vous sûr de vouloir supprimer cet reservation ?");
            confirmationAlert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            ButtonType userChoice = confirmationAlert.showAndWait().orElse(ButtonType.NO);
            if (userChoice == ButtonType.YES) {
                inter.deleteEntity(A.getId());
                list1.getItems().remove(selectedIndex);
            }
        } else {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setHeaderText("Veuillez sélectionner une reservation à supprimer.?");
            confirmationAlert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            ButtonType userChoice = confirmationAlert.showAndWait().orElse(ButtonType.NO);

        }
    }

    @javafx.fxml.FXML
    public void returnToAdd(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/addreservationcours.fxml"));
        try {
            Parent root = loader.load();
            nh.getChildren().setAll(root);

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ListView<Reservation> list1= afficherreservation;
        ReservationService inter = new ReservationService();
        List<Reservation> list2 = inter.myReservation(1);
        for (int i = 0; i < list2.size(); i++) {
            Reservation C = list2.get(i);
            list1.getItems().add(C);

        }
    }






}



