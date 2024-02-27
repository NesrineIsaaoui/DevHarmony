package controllers;

import entities.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import services.ServiceTask;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Affichetaskcontroller {

    @FXML
    private ImageView redlist;

    @FXML
    private ImageView redadd;

    @FXML
    private TableView<Task> tab;

    @FXML
    private TableColumn<Task, String> colCour;

    @FXML
    private TableColumn<Task, String> colDate;

    @FXML
    private TableColumn<Task, String> colEtat;

    @FXML
    private TableColumn<Task, String> colPlan;

    @FXML
    private TableColumn<Task, Void> colModifier; // New column for modifier button

    @FXML
    private TableColumn<Task, Void> colDelete; // New column for delete button

    private final ServiceTask serviceTask = new ServiceTask();

    @FXML
    void initialize() {
        // Set cell value factories for table columns
        colCour.setCellValueFactory(cellData -> cellData.getValue().nomcourProperty());
        colDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty().asString());
        colEtat.setCellValueFactory(cellData -> cellData.getValue().etatProperty());
        colPlan.setCellValueFactory(cellData -> cellData.getValue().planNameProperty());

        // Load task data into TableView
        try {
            List<Task> taskList = serviceTask.afficher();
            tab.getItems().addAll(taskList);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException
        }

        // Add modifier button column
        colModifier.setCellFactory(param -> new TableCell<>() {
            private final Button modifierButton = new Button();

            {
                // Set the modifier button image
                Image modifierImage = new Image(getClass().getResourceAsStream("/edit.png"));
                ImageView imageView = new ImageView(modifierImage);
                imageView.setFitWidth(16);
                imageView.setFitHeight(16);
                modifierButton.setGraphic(imageView);

                // Handle button click
              /*  modifierButton.setOnAction(event -> {
                    Task task = getTableView().getItems().get(getIndex());
                    openModifier(task);
                });*/
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(modifierButton);
                }
            }
        });

        // Add delete button column
        colDelete.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button();

            {
                // Set the delete button image
                Image deleteImage = new Image(getClass().getResourceAsStream("/delete.png"));
                ImageView imageView = new ImageView(deleteImage);
                imageView.setFitWidth(16);
                imageView.setFitHeight(16);
                deleteButton.setGraphic(imageView);

                // Handle button click
                deleteButton.setOnAction(event -> {
                    Task task = getTableView().getItems().get(getIndex());
                    deleteTask(task);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }

    // Method to open modifier dialog
    /*private void openModifier(Task task) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modiftask.fxml"));
            Parent root = loader.load();
            Modiftaskcontroller controller = loader.getController();
            controller.setTask(task);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    // Method to delete task
    private void deleteTask(Task task) {
        try {
            serviceTask.supprimer(task);
            // Refresh TableView after deletion if needed
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }

    @FXML
    void handleRedListClick() {
        // Your code for handling red list click
    }

    @FXML
    void handleRedAddClick() {
        // Your code for handling red add click
    }
}
