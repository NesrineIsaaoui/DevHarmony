package edu.esprit.pi.controller;

import edu.esprit.pi.MainFx;
import edu.esprit.pi.model.Eleve;
import edu.esprit.pi.model.Enseigant;
import edu.esprit.pi.model.Parent;
import edu.esprit.pi.model.User;
import edu.esprit.pi.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;
import java.sql.SQLException;

public class InscriptionController   {
    UserService us = new UserService();

    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfemail;
    @FXML
    private PasswordField tfmdp;
    @FXML
    private PasswordField tfconfirmmdp;
    @FXML
    private RadioButton rbtEnseignant;

    @FXML
    private RadioButton rbtEleve;

    @FXML
    private RadioButton rbtParent;

    @FXML
    private Button btnconfirmer;
    @FXML
    private Button btnSeconnecter;
    @FXML
    private StackPane stck;

    @FXML
    void NavPageLogin(ActionEvent event) throws IOException {
        javafx.scene.Parent menu = FXMLLoader.load(MainFx.class.getResource("fxml/SeConnecter.fxml"));
        stck.getChildren().removeAll();
        stck.getChildren().setAll(menu);
    }


    @FXML
     void inscrire(javafx.event.ActionEvent event) throws SQLException {
        if (tfemail.getText().equals("")
                || tfnom.getText().equals("")
                || tfprenom.getText().equals("")
                || tfmdp.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champ vide");
            alert.setContentText("vous pouvez les remplir soigneusement");
            alert.setHeaderText("Tous les champs sont requis");
            alert.showAndWait();
            tfnom.setText("");
            tfprenom.setText("");
            tfemail.setText("");
            tfmdp.setText("");
            tfconfirmmdp.setText("");
            return;
        }
        if(!tfmdp.getText().equals(tfconfirmmdp.getText()) ){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("mots de passe ne correspondent pas");
            alert.setContentText("mot de passe et confirmer le mot de mot de passe devraient avoir le même contenu");
            alert.setHeaderText("Warning Alert");
            alert.showAndWait();
            tfnom.setText("");
            tfprenom.setText("");
            tfemail.setText("");
            tfmdp.setText("");
            tfconfirmmdp.setText("");
            return;
        }
        if(us.userExist(tfemail.getText())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("l'utilisateur existe déjà");
            alert.setContentText("Il y a déjà un utilisateur avec cet e-mail");
            alert.setHeaderText("Warning Alert");
            alert.showAndWait();
            tfnom.setText("");
            tfprenom.setText("");
            tfemail.setText("");
            tfmdp.setText("");
            tfconfirmmdp.setText("");
            return;
        }
        String role;
        User u1;
        if(rbtEnseignant.isSelected()){
            role = "Enseignant" ;
            u1 = new Enseigant(tfnom.getText(),tfprenom.getText(),role,tfemail.getText(),tfmdp.getText());
            us.addEnseignant((Enseigant) u1);

    } else if (rbtParent.isSelected()) {
            role = "Parent";
            u1 = new Parent(tfnom.getText(),tfprenom.getText(),role,tfemail.getText(),tfmdp.getText());
            us.addParent((Parent) u1);
        } else{
            role = "Eleve";
            u1 = new Eleve(tfnom.getText(),tfprenom.getText(),role,tfemail.getText(),tfmdp.getText());
            us.addEleve((Eleve) u1);
            }
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setTitle("Compte créé avec succès");
         alert.setHeaderText("Votre Compte a été créé avec succès");
         alert.showAndWait();
         try {
             FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("fxml/SeConnecter.fxml"));
             javafx.scene.Parent menu = loader.load();
             Scene editProfileScene = new Scene(menu, 1043, 730);
             Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
             stage.setScene(editProfileScene);
         } catch (IOException e) {
             e.printStackTrace();
         }

}




}
