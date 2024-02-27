/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluation;

import Entities.Formulaire;
import Service.ServiceFormulaire;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import com.pdfjet.A4;
import com.pdfjet.Cell;
import com.pdfjet.CoreFont;
import com.pdfjet.Font;
import com.pdfjet.PDF;
import com.pdfjet.Page;
import com.pdfjet.Table;

/**
 * FXML Controller class
 *
 * @author oussa
 */
public class AdminformulaireController implements Initializable {
    @FXML
    private TableView<Formulaire> table_formulaire;
    @FXML
    private TableColumn<String, Formulaire> nom_colone;
    @FXML
    private TableColumn<String, Formulaire> email_colone;
    @FXML
    private TableColumn<String, Formulaire> etat_colone;
    
    @FXML
    private TextField nom_text;
    @FXML
    private TextField email_text;
    @FXML
    private TextField id_text;
    
    @FXML
    private ComboBox etat_text;
    
    @FXML
    private TableColumn<String, Formulaire> diplome_colone;
    @FXML
    private ComboBox diplome_text;
   
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<String> list = FXCollections.observableArrayList("valide","n'est pas valide");
        etat_text.setItems(list);
        ObservableList<String> list1 = FXCollections.observableArrayList("Oui","Non");
        diplome_text.setItems(list1);
        populateTable() ;
        
        //populateTable() ; 
    }    
    public void populateTable(){
        ServiceFormulaire ser= new ServiceFormulaire();
        
        List<Formulaire> li =ser.ListClasse() ;
        ObservableList<Formulaire> data = FXCollections.observableArrayList(li);
                  

        
          
        nom_colone.setCellValueFactory(
                new PropertyValueFactory<>("nom_eleve"));
 
       
        email_colone.setCellValueFactory(
                new PropertyValueFactory<>("email"));
       etat_colone.setCellValueFactory(  
                new PropertyValueFactory<>("etat")) ; 
       diplome_colone.setCellValueFactory(  
                new PropertyValueFactory<>("diplome")) ; 
        
        
        
        
        
        table_formulaire.setItems(data);
    } 
    @FXML
    private void supprimerc(ActionEvent event) {
        
        
         ServiceFormulaire sc=new ServiceFormulaire();
        Formulaire c =new Formulaire();
         
        c.setId(Integer.parseInt(id_text.getText()));
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmation de Suppression !");
        alert.setContentText("Voulez-Vous Vraiment Supprimer");
        
        Optional<ButtonType> btn = alert.showAndWait();
        if(btn.get() == ButtonType.OK){
            sc.supprimerc(c);
           // populateTable();
            populateTable() ;
            
        nom_text.clear();
        email_text.clear();
         
        etat_text.getSelectionModel().clearSelection();
        diplome_text.getSelectionModel().clearSelection();
        
        }
        else{
            alert.close();
        } 
       
    }
    
    @FXML
    private void modifierc(ActionEvent event) {
        
            ServiceFormulaire sc = new ServiceFormulaire(); 
             
            
        Formulaire c = new Formulaire(Integer.parseInt(id_text.getText()) ,nom_text.getText(),email_text.getText(), etat_text.getSelectionModel().getSelectedItem().toString(),diplome_text.getSelectionModel().getSelectedItem().toString());
        

        sc.modifierc(c);
        populateTable();
        nom_text.clear();
        email_text.clear();
         
        etat_text.getSelectionModel().clearSelection();
        diplome_text.getSelectionModel().clearSelection();
        
        
       
        
        
        
    }
    @FXML
    private void getselected(MouseEvent event) {
        
   Formulaire c = new Formulaire() ; 
   c=table_formulaire.getSelectionModel().getSelectedItem();  
   nom_text.setText(c.getNom_eleve()); 
   email_text.setText(c.getEmail()); 
   etat_text.setValue(c.getEtat());
   diplome_text.setValue(c.getDiplome());
   id_text.setText(String.valueOf(c.getId()));
 //  gc.setValue(c.getGenre_c()); 
   
   
 } 
    @FXML
    private void printpdf(ActionEvent event) throws FileNotFoundException, Exception {
                File out = new File("tableformulaire.pdf") ; 
        FileOutputStream fos = new FileOutputStream(out) ;
        PDF pdf = new PDF(fos) ; 
        Page page = new Page(pdf, A4.PORTRAIT)  ; 
        Font f1 = new Font(pdf, CoreFont.HELVETICA_BOLD) ;
        Font f2 = new Font(pdf, CoreFont.HELVETICA) ;
        Table table = new Table() ; 
        List<List<Cell>> tabledata = new ArrayList<List<Cell>>() ;       
        List<Cell> tablerow = new ArrayList<Cell>() ; 
        Cell cell = new Cell(f1,nom_colone.getText());
        tablerow.add(cell) ; 
        
          cell = new Cell(f1,email_colone.getText());
        tablerow.add(cell) ;
        cell = new Cell(f1,diplome_colone.getText());
        tablerow.add(cell) ;
        
        cell = new Cell(f1,etat_colone.getText());
        tablerow.add(cell) ;
        
       
        
    tabledata.add(tablerow) ; 

   Formulaire co = new Formulaire(); 
   co=table_formulaire.getSelectionModel().getSelectedItem();  
        Cell nc = new Cell(f2, co.getNom_eleve()) ; 
        Cell prc = new Cell(f2,co.getEmail()) ;
         
        
        
        Cell gc = new Cell(f2,co.getDiplome()) ; 
        Cell lc = new Cell(f2,co.getEtat()) ; 
        
        tablerow = new ArrayList<Cell>() ; 
        tablerow.add(nc) ; tablerow.add(prc) ; tablerow.add(gc) ; tablerow.add(lc) ;  
        
    tabledata.add(tablerow) ; 
    table.setData(tabledata);
    table.setPosition(10f, 60f);
    table.setColumnWidth(0, 90f); 
    table.setColumnWidth(1, 90f); 
    table.setColumnWidth(2, 90f); 
    table.setColumnWidth(3, 90f); 
    table.setColumnWidth(4, 250f);  
    
    
    while(true){
    table.drawOn(page) ; 
    if(!table.hasMoreData()){
    table.resetRenderedPagesCount(); 
    break ; 
    
    }
    
    page=new Page(pdf,A4.PORTRAIT) ; 
    
    
    }
    
    pdf.flush();
    fos.close(); 
        System.out.println("saved to "+out.getAbsolutePath());
        
        
    }

    
}
