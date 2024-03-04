package pi.blog.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    @FXML
    public void openModifInterface3(ActionEvent event) {
        try {
            // Load the Modif.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pi/blog/AffichagePostBack.fxml"));
            Parent root = loader.load();
            // Show the Modif.fxml interface
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("blog");

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void openModifInterface2(ActionEvent event) {
        try {
            // Load the Modif.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pi/blog/AffichageCommentBack.fxml"));
            Parent root = loader.load();

            // Show the Modif.fxml interface
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("blog");

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void openModifInterface1(ActionEvent event) {
        try {
            // Load the Modif.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pi/blog/Stat.fxml"));
            Parent root = loader.load();

            // Show the Modif.fxml interface
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Blog");

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}