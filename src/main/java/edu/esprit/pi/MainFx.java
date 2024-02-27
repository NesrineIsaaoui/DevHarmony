package edu.esprit.pi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFx extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainFx.class.getResource("fxml/SeConnecter.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Module User!");
        stage.getIcons().add(new Image(MainFx.class.getResourceAsStream("icons/user.png")));
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}