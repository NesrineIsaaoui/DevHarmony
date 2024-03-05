package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.Connexion_database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class MainFX extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        if (testDatabaseConnection()) {
            //User
           //FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/Blog-view.fxml"));
            //Admin
            FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/Admin.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Blog!");
            stage.setScene(scene);
            stage.show();
        }
    }
    private boolean testDatabaseConnection() {
        try {

            Connection connection = Connexion_database.getInstance().getConnection();

            if (connection != null) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {

            e.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) {
        launch();
    }
}