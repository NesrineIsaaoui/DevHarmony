/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Session;
import entities.User;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 *
 */
public class CertificatController implements Initializable {

    @FXML
    private ImageView Certificat;
    @FXML
    private Button btnTelecharger;
    @FXML
    private AnchorPane containerCertificat;
    @FXML
    private Label lbNomEtudiant;
    @FXML
    private Label lbFormation;
    @FXML
    private Label lbNomFormateur;
    @FXML
    private Button btnRetourTest;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    public void setDataCertificat(String nomEtudiant,String nomFormateur,String formation){
        lbNomEtudiant.setText(nomEtudiant);
        lbNomFormateur.setText(nomFormateur);
        lbFormation.setText(formation);
    }

    @FXML
    private void telechargerCertificat(ActionEvent event) throws IOException {
        Image img = containerCertificat.snapshot(null, null) ;
        Document document = new Document() ;
        ByteArrayOutputStream  byteOutput = new ByteArrayOutputStream();
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files", "*.pdf"));
        File file = chooser.showSaveDialog((Stage) ((Node) event.getSource()).getScene().getWindow());
        try {
            if(file != null){
                PdfWriter.getInstance(document, new FileOutputStream(file));
                ImageIO.write( SwingFXUtils.fromFXImage( img, null ), "png", byteOutput );
                com.itextpdf.text.Image  certImage;
                certImage = com.itextpdf.text.Image.getInstance( byteOutput.toByteArray() );
                certImage.scaleToFit(600, 375);
                document.open();
                document.add(certImage );
            }
          } catch (DocumentException | FileNotFoundException e) {
            Logger.getLogger(CertificatController.class.getName()).log(Level.SEVERE, null, e);
        }
      document.close();
    }

    @FXML
    private void retourAcceuilTest(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/TestEtudiant.fxml"));
                 Stage stage = new Stage(StageStyle.DECORATED);
                stage.setScene(
                        new Scene(loader.load())
                );
                stage.setTitle("E-SENPAI | E-Learning Platform");
                stage.setResizable(false);

               TestEtudiantController controller = loader.getController() ;
                Session  s = new Session() ;
                User currentUser ;
                currentUser = Session.getUser();
                controller.initData(currentUser);
               
              
                Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                oldStage.close();

                stage.show();
    }
    
}
