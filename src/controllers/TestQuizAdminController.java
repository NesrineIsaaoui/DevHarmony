/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Question;
import entities.Quiz;
import entities.Test;
import entities.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import services.QuizDao;
import services.TestDao;

/**
 * FXML Controller class
 *
 */
public class TestQuizAdminController implements Initializable {

    @FXML
    private Label labelTotalQuiz;
    private User currentUse ;
    @FXML
    private Label labelTotalTest;
    @FXML
    private Label labelTotalPasses;
    @FXML
    private Label labelTotalAdmis;
    @FXML
    private TableView<Quiz> tvQuiz;
    @FXML
    private TableColumn<Test, String> colDuree;
    @FXML
    private TableColumn<Quiz, String> colSujet;
    @FXML
    private TableView<Question> tvQuestion;
    @FXML
    private TableColumn<Question, String> colType;
    @FXML
    private TableColumn<Question, String> colQPosee;
    @FXML
    private TableColumn<Question, String> colRcoreect;
    @FXML
    private TableColumn<Question, String> colMrep1;
    @FXML
    private TableColumn<Question, String> colMrep2;
    @FXML
    private TableColumn<Question, String> colMrep3;
    @FXML
    private TableColumn<Question, String> colNote;
    @FXML
    private BarChart<?, ?> barChart;
    @FXML
    private NumberAxis yNombre;
    @FXML
    private CategoryAxis Xsujet;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setQuizToTable();
    }    

    void initData(User currentUser) {
        this.currentUse = currentUser ;
    }
    
    private void setQuizToTable(){
        TestDao tdao = TestDao.getInstance() ;
        ObservableList<Test> listTest = tdao.getListTest() ;
        setDataToBarChart(listTest.get(0));
        
        QuizDao qdao = QuizDao.getInstance() ;
        ObservableList<Quiz> listQuiz = qdao.displayQuizList();
        
        labelTotalQuiz.setText(listQuiz.size()+"");
        labelTotalTest.setText(listTest.size()+"");
        int totalPasses = 0 ;
        int totalAdmis = 0 ;     
        for(Test t  : listTest){
            totalAdmis += t.getNbEtudiantsAdmis() ;
            totalPasses += t.getNbEtudiantsPasses() ;
        }
        
        labelTotalAdmis.setText(totalAdmis+"");
        labelTotalPasses.setText(totalPasses+"");
        
        listQuiz.addAll(listTest) ;
        tvQuiz.setItems(listQuiz);
        colDuree.setCellValueFactory(new PropertyValueFactory<>("temps"));
        colSujet.setCellValueFactory(new PropertyValueFactory<>("sujet"));
        
        
        ObservableList<Question> listQuestion = FXCollections.observableArrayList() ;
        for(Quiz q : listQuiz){
            listQuestion.addAll(q.getQuestions());
        }
        setQuestionToTable(listQuestion);
    }
    
    private void setQuestionToTable(ObservableList<Question> listQuestion){
        tvQuestion.setItems(listQuestion);
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colQPosee.setCellValueFactory(new PropertyValueFactory<>("questionPosee"));
        colRcoreect.setCellValueFactory(new PropertyValueFactory<>("reponseCorrecte"));
        colMrep1.setCellValueFactory(new PropertyValueFactory<>("reponseFausse1"));
        colMrep2.setCellValueFactory(new PropertyValueFactory<>("reponseFausse2"));
        colMrep3.setCellValueFactory(new PropertyValueFactory<>("reponseFausse3"));
        colNote.setCellValueFactory(new PropertyValueFactory<>("note"));
    }
    
     private void setDataToBarChart(Test t){
        ObservableList<String> test = FXCollections.observableArrayList("admis","passées");
        XYChart.Series s = new XYChart.Series<>() ;
        s.getData().add(new XYChart.Data<>("admis",t.getNbEtudiantsAdmis())) ;
        s.getData().add(new XYChart.Data<>("passées",t.getNbEtudiantsPasses())) ; 

        Xsujet.setCategories(test);
        s.setName(t.getSujet());
        barChart.getData().addAll(s) ;
    }

    @FXML
    private void getClickedTest(MouseEvent event) {
        Quiz q = tvQuiz.getSelectionModel().getSelectedItem() ;
        if(q != null){
            ObservableList<Question> listquestion = FXCollections.observableArrayList() ;
            listquestion.addAll(q.getQuestions()) ;
            setQuestionToTable(listquestion);
            if(q.getClass().getSimpleName().toLowerCase().equals("test")){
                Test t = (Test)q ;
                barChart.getData().clear();
                setDataToBarChart(t);
            }
        }
        
    }
}
