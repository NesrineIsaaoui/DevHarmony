package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {
    @Override
    public void start(Stage primaryStage) {

        Parent root;
        try {
      root = FXMLLoader.load(getClass().getResource("/Location_category.fxml"));
           //root = FXMLLoader.load(getClass().getResource("/AfficherCoursFront.fxml"));

            Scene scene = new Scene(root, 1000, 600);

            primaryStage.setTitle("EduWave");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
