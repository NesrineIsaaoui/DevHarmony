package pi.blog.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import pi.blog.App;

import java.io.IOException;

public class BlogViewController {

    @FXML
    private void redirectToAffichagePostFront(ActionEvent event) throws IOException {
        // Load AffichagePostFront.fxml when the button is clicked
        Scene scene = new Scene(App.loadFXML("AffichagePostFront"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
