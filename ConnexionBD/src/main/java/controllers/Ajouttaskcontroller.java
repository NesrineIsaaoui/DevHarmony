package controllers;

import entities.Plan;
import entities.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import services.ServicePlan;
import services.ServiceTask;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.util.List;


public class Ajouttaskcontroller {
    private final ServicePlan p = new ServicePlan();
    private final ServiceTask ta = new ServiceTask();

    @FXML
    private ImageView redlist;

    @FXML
    private ImageView redadd;

    @FXML
    void handleRedListClick() {
        try {
            // Load the affichetask.fxml file
            Parent afficheTaskParent = FXMLLoader.load(getClass().getResource("/affichetask.fxml"));
            Scene afficheTaskScene = new Scene(afficheTaskParent);

            // Get the stage information
            Stage window = (Stage) redlist.getScene().getWindow();
            window.setScene(afficheTaskScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleRedAddClick() {
        try {
            // Load the ajouttask.fxml file
            Parent ajoutTaskParent = FXMLLoader.load(getClass().getResource("/ajouttask.fxml"));
            Scene ajoutTaskScene = new Scene(ajoutTaskParent);

            // Get the stage information
            Stage window = (Stage) redadd.getScene().getWindow();
            window.setScene(ajoutTaskScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private TextField nmplan;

    @FXML
    private Button submit1;

    @FXML
    private TextField datetask;

    @FXML
    private TextField etattask;

    @FXML
    private TextField nmcour;

    @FXML
    private ChoiceBox<String> planbelong;

    @FXML
    private Button submit;

    @FXML
    void initialize() {
        try {
            // Load names of available planners into the ChoiceBox
            List<String> plannerNames = ta.getAllPlannerNames();
            planbelong.getItems().addAll(plannerNames);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void ajouterp(ActionEvent event) {
        Plan plan = new Plan();
        plan.setNom(nmplan.getText());}


    @FXML
    void ajouter(ActionEvent event) {
        // Create a Task object and set its properties
        Task task = new Task();
        task.setNomcour(nmcour.getText());
        // Convert the string date to java.sql.Date
        try {
            Date date = Date.valueOf(datetask.getText());
            task.setDate(date);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return; // Return if date format is invalid
        }
        task.setEtat(etattask.getText());

        // Fetch the plan ID based on the selected plan name
        try {
            String selectedPlanName = planbelong.getSelectionModel().getSelectedItem();
            int planId = ta.getPlanIdByName(selectedPlanName);
            task.setIdplan(planId);
        } catch (Exception e) {
            e.printStackTrace();
            return; // Return if plan ID retrieval fails
        }

        // Call the ajouter() method with the Task object
        try {
            ta.ajouter(task);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
