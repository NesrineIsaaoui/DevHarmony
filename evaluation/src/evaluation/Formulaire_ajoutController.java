/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluation;

import Entities.Formulaire;
import Service.ServiceFormulaire;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * FXML Controller class
 *
 * @author oussa
 */
public class Formulaire_ajoutController implements Initializable {

    @FXML
    private TextField nom_text;
    @FXML
    private TextField email_text;
    
    @FXML
    private ComboBox etat_text;
    public static int id  ; 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<String> list = FXCollections.observableArrayList("Oui","Non");
        etat_text.setItems(list);
    
    }    
     @FXML
    private void ajouterf(ActionEvent event) throws Exception {
        
        ServiceFormulaire sf =new ServiceFormulaire();
        String verif ;
        String email = email_text.getText() , nom = nom_text.getText() ;
        if(controlSaisie()){
            if(isValidEmail(email_text.getText())){
                if(verifUserChamps()){
        if (etat_text.getSelectionModel().getSelectedItem().toString().equals("Oui")){
        verif = "Valideé" ; 
        Formulaire f= new Formulaire( nom_text.getText(),  email_text.getText(),verif ,etat_text.getSelectionModel().getSelectedItem().toString() );
             sf.ajouterf(f); 
             sendMail(email, nom);
             
        }
        else {
        verif = "N'est pas valideé" ; 
        Formulaire f= new Formulaire( nom_text.getText(),  email_text.getText(),verif ,etat_text.getSelectionModel().getSelectedItem().toString() );
             sf.ajouterf(f); 
             
        id = sf.getid(nom_text.getText()) ; 
    
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Quizz.fxml"));
        Parent root = loader.load();
        etat_text.getScene().setRoot(root);
    
        
        }
              
                
           
              
       
        nom_text.clear();
        email_text.clear();
         
        etat_text.getSelectionModel().clearSelection();       
    }}}}
    
    public boolean controlSaisie(){
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Erreur");
         alert.setHeaderText("Erreur de saisie");
         

        if(checkIfStringContainsNumber(nom_text.getText())){
            alert.setContentText("Le nom ne doit pas contenir des chiffres");
            alert.showAndWait();
            return false;
        }
        
        
        
        return true;
    }
    
    public  void sendMail(String recepient, String lastname) throws Exception {
        System.out.println("Preparing to send email");
        Properties properties = new Properties();

        //Enable authentication
        properties.put("mail.smtp.auth", "true");
        //Set TLS encryption enabled
        properties.put("mail.smtp.starttls.enable", "true");
        //Set SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //Set smtp port
        properties.put("mail.smtp.port", "587");

        //Your gmail address
        String myAccountEmail = "oussama.jridi1@esprit.tn"; //mail
        //Your gmail password
        String password = "191JMT2269";

        //Create a session with account credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        //Prepare email message
        Message message = prepareMessage(session, myAccountEmail, recepient, lastname);

        //Send mail
        Transport.send(message);
    }

    private Message prepareMessage(Session session, String myAccountEmail, String recepient, String LastName) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Congratulations your account has been verified");
            String htmlCode = "Cher/Chere "+LastName+", <br/>"
            		+ "You have signed up succssufully<br/>. <br/>"
            				+ "Cordialement,<br/> Esprit";
            message.setContent(htmlCode, "text/html");
            return message;
        } catch (Exception ex) {
        	System.out.print(ex.getMessage());
        }
        return null;
    }
    public boolean controlSaisie1(){
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Erreur");
         alert.setHeaderText("Erreur de saisie");
        
        if(isValidEmail(email_text.getText())){
        alert.setContentText("email does not match");
            alert.showAndWait();
            return false;
        }
        
        return true;
    }
    
    public boolean checkIfNumber(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Erreur");
         alert.setHeaderText("Erreur de saisie");
         

       return true;
    }
    
    public boolean checkIfStringContainsNumber(String str){
        for (int i=0; i<str.length();i++){
            if(str.contains("0") || str.contains("1") || str.contains("2") || str.contains("3") || str.contains("4") || str.contains("5") || str.contains("6") || str.contains("7") || str.contains("8") || str.contains("9")){
                return true;
            }
        }
        return false;
    }


public boolean verifUserChamps() {
        int verif = 0;
        String style = " -fx-border-color: red;";

        String styledefault = "-fx-border-color: green;";

   
       
        nom_text.setStyle(styledefault);
        email_text.setStyle(styledefault);
        etat_text.setStyle(styledefault);
     
       
 

        if (nom_text.getText().equals("")) {
            nom_text.setStyle(style);
            verif = 1;
        }
       
        if ( email_text.getText().equals("")) {
             email_text.setStyle(style);
            verif = 1;
        }
         
        if (etat_text.getSelectionModel().getSelectedItem().toString().equals("")) {
            etat_text.setStyle(style);
            verif = 1;
        }
       
        
       
        if (verif == 0) {
            return true;
        }
        Alert al = new Alert(Alert.AlertType.ERROR);
        al.setTitle("Alert");
        al.setContentText("Verifier les champs");
        al.setHeaderText(null);
        al.show() ; 
        
        return false;
    }
 public static boolean isValidEmail(String email) {
        // Define regular expression pattern for email validation
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        
        // Check if email matches pattern
        Matcher matcher = pattern.matcher(email);
        
        
        return matcher.matches();
    }
}
