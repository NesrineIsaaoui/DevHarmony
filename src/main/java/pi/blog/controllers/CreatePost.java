package pi.blog.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import pi.blog.HelloApplication;
import pi.blog.models.Publication;
import pi.blog.services.PublicationRepository;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class CreatePost implements Initializable {

   
    @FXML
    private TextField title;

    @FXML
    private TextArea description;


    private final PublicationRepository publicationRepository;


    public CreatePost() {
        publicationRepository = new PublicationRepository();
    }

    @FXML
    void onSave(ActionEvent event) throws IOException {
 
        if (title.getText().isEmpty() ||description.getText().isEmpty()  ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Test");
            alert.setContentText("Check Fields Again");
            alert.showAndWait();
        }
        else {

            String titre = title.getText();
 
             String disc=description.getText();

            Date d1=new Date();
            Publication e = new Publication(titre ,disc,d1,2);
            publicationRepository.save(e);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Publication Added Successfully.");
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pi/blog/AffichagePostFront.fxml"));
            Parent root = loader.load();
            // Show the Modif.fxml interface
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("blog");

            stage.show();
        }
    }

    @FXML
    void onCancel(ActionEvent event) throws IOException {

        Stage stage = (Stage) title.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/pi/blog/AffichagePostFront.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        //.close();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
