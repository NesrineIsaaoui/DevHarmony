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
import java.sql.SQLException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Modiftaskcontroller {

    @FXML
    private ImageView redlist;

    @FXML
    private ImageView redadd;

    @FXML
    private TextField datetask;

    @FXML
    private TextField etattask;

    @FXML
    private TextField nmcour;

    @FXML
    private ChoiceBox<?> planbelong;

    @FXML
    private Button submit;

    private Task task;
    private final ServiceTask serviceTask = new ServiceTask();

    /*public void initializeTask(Task task) {
        this.task = task;
        // Populate the fields with task data
        nmcour.setText(task.getNomcour());
        datetask.setText(task.getDate().toString()); // You may need to format the date
        etattask.setText(task.getEtat());
        // Assuming you have a method to fetch plan names
        try {
            List<String> planNames = serviceTask.getAllPlannerNames();
            planbelong.getItems().addAll(planNames);
            planbelong.setValue(task.getPlanName()); // Set the default value
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }*/

   /*@FXML
    void modifier(ActionEvent event) {
        // Update task data with the values from the fields

        task.setNomcour(nmcour.getText());
        task.setDate(Date.valueOf(datetask.getText())); // Assuming the date is in yyyy-MM-dd format
        task.setEtat(etattask.getText());
        task.setIdplan(serviceTask.getPlanIdByName(planbelong.getValue()));

        // Call the service to modify the task in the database
        try {
            serviceTask.modifier(task);
            // Close the modify interface after modification
            submit.getScene().getWindow().hide();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }*/
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
}
