/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Formation;
import entities.ListNote;
import entities.Question;
import entities.Quiz;
import entities.Test;
import entities.User;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import services.NoteDao;
import services.QuestionDao;
import services.QuizDao;
import services.TestDao;

/**
 * FXML Controller class
 *
 */
public class TestController implements Initializable {
     User currentUser;
     Quiz q = null;
     int currentQuestionIndex = 0;
    @FXML
    private TextField tfSujet;
    @FXML
    private TextField tfQposee;
    @FXML
    private TextField tfRcorrecte;
    @FXML
    private TextField tfMrep1;
    @FXML
    private TextField tfMrep2;
    @FXML
    private TextField tfMrep3;
    @FXML
    private TextField tfnote;
    @FXML
    private RadioButton rbtn1;
    @FXML
    private RadioButton rbtn3;
    @FXML
    private RadioButton rbtn2;
    @FXML
    private RadioButton rbtn4;
    @FXML
    private Button btnPrev;
    @FXML
    private Button btnNext;
    @FXML
    private Button btnAddQuestion;
    @FXML
    private Button btnAddTest;
    @FXML
    private Label labelQuestion;
    private TableColumn<Quiz, Integer> colidQuiz;
    private TableColumn<Quiz, Integer> colIdF;
    @FXML
    private TableColumn<Quiz, String> colSujet;
    private TableColumn<Question, Integer> colIdquestion;
    @FXML
    private TableColumn<Question, String> colQuiz;
    @FXML
    private TableColumn<Question, String> colQposee;
    @FXML
    private TableColumn<Question, String> colRep;
    @FXML
    private TableColumn<Question, String> colMrep1;
    @FXML
    private TableColumn<Question, String> colMrep2;
    @FXML
    private TableColumn<Question, String> colMrep3;
    @FXML
    private TableColumn<Question, Integer> colNote;
    @FXML
    private TableView<Quiz> tvQuiz;
    @FXML
    private TableView<Question> tvQuestion;
    @FXML
    private TextField tfSujetShow;
    @FXML
    private TextField tfQposeeShow;
    @FXML
    private TextField tfRcorrShow;
    @FXML
    private TextField tfMrep1Show;
    @FXML
    private TextField tfMrep2Show;
    @FXML
    private TextField tfMrep3Show;
    @FXML
    private TextField tfMrepNoteShow;
    @FXML
    private Button btnUpdateItem;
    @FXML
    private Button btnDeleteItem;
    @FXML
    private ComboBox<String> comboApplyOn;
    @FXML
    private Tab listQuiz;
    @FXML
    private Button btnUpdateAdd;
    @FXML
    private Tab tabAdd;
    @FXML
    private ComboBox<String> comboType;
    @FXML
    private ComboBox<String> comboFormation;
    @FXML
    private Tab stat;
    @FXML
    private TableView<ListNote> tvNote;
    @FXML
    private TableColumn<ListNote, String> colSujetNote;
    @FXML
    private TableColumn<ListNote, String> colEtudStat;
    @FXML
    private TableColumn<ListNote, Integer> colNoteStat;
    @FXML
    private TableColumn<ListNote, String> colFormation;
    @FXML
    private TextField tfSearch;
    @FXML
    private ComboBox<Integer> comboHeure;
    @FXML
    private ComboBox<Integer> comboMinutes;
    @FXML
    private Label labelDuree;
    @FXML
    private ComboBox<Integer> comboHeureUpdate;
    @FXML
    private ComboBox<Integer> comboMinUpdate;
    @FXML
    private TableColumn<Quiz, String> colDuree;
    @FXML
    private LineChart<String, Number> lineChart;
    @FXML
    private NumberAxis y;
    @FXML
    private CategoryAxis x;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private NumberAxis Ynote;
    @FXML
    private CategoryAxis Xsujet;
    @FXML
    private TableView<Test> tvTestNote;
    @FXML
    private TableColumn<Test, String> colTestNote;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        labelDuree.setVisible(false);
        comboHeure.setVisible(false);
        comboMinutes.setVisible(false);
        comboHeureUpdate.setVisible(false);
        comboMinUpdate.setVisible(false);
    }   
    
    public void initData(User u){
        this.currentUser=u;   
        ObservableList<String> options = FXCollections.observableArrayList("Quiz","Test");
        comboType.setItems(options);
        comboFormation.setVisible(false);
        btnAddTest.setDisable(true);      
    }

    @FXML
    private void prevQuestion(ActionEvent event) {
        if(q != null){
            if(currentQuestionIndex > 0 && currentQuestionIndex < q.getQuestions().size()){
                currentQuestionIndex -= 1 ;
                showAddedQuestion(currentQuestionIndex);
            }
            else{
                showAddedQuestion(0);
            }
        }     
    }

    @FXML
    private void nextQuestion(ActionEvent event) {
        if(q != null){
            if(currentQuestionIndex >=0 && currentQuestionIndex < q.getQuestions().size()-1){
                currentQuestionIndex += 1 ;
                showAddedQuestion(currentQuestionIndex);
            }else{
                showAddedQuestion(q.getQuestions().size()-1);
            }
        }
        
    }

    @FXML
    private void addQuestion(ActionEvent event) {
        int idTest = 0 ;
        int duree = 0 ;
        if(comboType.getValue() == null){
            showAlertMessageError("Ajouter Quiz/Test", "Veuillez choisir le type de devoir");
        }
        else{
            if(comboType.getValue().toLowerCase().equals("test")){
                if(comboFormation.getValue() == null){
                    showAlertMessageError("Ajouter Quiz/Test", "Veuillez choisir la formation");
                }
                else if(comboHeure.getValue() == null || comboMinutes.getValue() == null){
                    showAlertMessageError("Ajouter Quiz/Test", "Veuillez renseigner la durée");
                }
                else{
                    TestDao tdao = TestDao.getInstance() ;
                    Formation f = tdao.getFormationbyTitre(comboFormation.getValue()) ;
                    idTest = f.getId() ;
                    duree = (comboHeure.getValue()*3600) + (comboMinutes.getValue()*60) ;
                }
            }
            
            if((comboFormation.getValue() != null) || (comboType.getValue()!= null)){
                
                String sujet = tfSujet.getText() ;
                String qPosee = tfQposee.getText() ;
                String rCorrecte = tfRcorrecte.getText() ;
                String mRep1 = tfMrep1.getText() ;
                String mRep2 = tfMrep2.getText() ;
                String mRep3 = tfMrep3.getText() ;                
                
                if(!qPosee.isEmpty() && !rCorrecte.isEmpty() && !mRep1.isEmpty() && !mRep2.isEmpty() && !mRep3.isEmpty()
                        && !tfnote.getText().isEmpty()){
                    q.setSujet(tfSujet.getText());

                    if(!sujet.isEmpty())
                        tfSujet.setEditable(false);

                    try {
                        
                        q.setIdFormation(idTest);
                        q.setSujet(sujet);
                        q.setIdFormateur(currentUser.getId());
                        if(q instanceof Test)
                             ((Test)q).setDuree(duree);
                        

                        int note = Integer.parseInt(tfnote.getText()) ;
                        Question question = new Question(qPosee, rCorrecte, mRep1, mRep2, mRep3, note) ; 

                        if(!q.verifierQuestion(question)){
                                if(q.addQuestion(question )){
                                 showAddedQuestion(q.getQuestions().size()-1);
                                 currentQuestionIndex = q.getQuestions().size() - 1 ;
                                 btnAddTest.setDisable(false);
                            }
                            else{
                                q.getQuestion(currentQuestionIndex).setQuestionPosee(qPosee);
                                q.getQuestion(currentQuestionIndex).setReponseCorrecte(rCorrecte);
                                q.getQuestion(currentQuestionIndex).setReponseFausse1(mRep1);
                                q.getQuestion(currentQuestionIndex).setReponseFausse2(mRep2);
                                q.getQuestion(currentQuestionIndex).setReponseFausse3(mRep3);
                                q.getQuestion(currentQuestionIndex).setNote(note);

                            }

                            tfQposee.setText("");
                            tfRcorrecte.setText("");
                            tfMrep1.setText("");
                            tfMrep2.setText("");
                            tfMrep3.setText("");
                            tfnote.setText("");


                            }else{
                                showAlertMessageError("Ajouter Quiz","la question existe dejà!");
                         }
                        } catch (NumberFormatException e) {
                                showAlertMessageError("Ajouter Quiz", "la note doit etre numique > 0!");
                     }                      
                }else{
                    showAlertMessageError("Ajouter Quiz", "veuillez remplir tous les champs!");
                }               
                
            }
        }
     
    }
    
    private void showAlertMessageError(String title,String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.show();
    }
    
    private void showAlertMessageInfo(String title,String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.show();
    }  
    
    @FXML
    private void addTest(ActionEvent event) {
        if(q.getQuestions().isEmpty() || q.getQuestions().size() < 3){
            showAlertMessageError("Ajouter Quiz/Test", "Entrez au moins 3 questions pour enregistrer!");
        }else{
            if(comboType.getValue().toLowerCase().equals("quiz")){
                QuizDao qdao = QuizDao.getInstance() ;
                qdao.insertQuiz(q, currentUser.getId());         
                showAlertMessageInfo("Ajouter Quiz", "Quiz/Test ajouter avec succès") ;
            }
            
            if(comboType.getValue().toLowerCase().equals("test")){
                TestDao tdao = TestDao.getInstance();
                tdao.insertTest((Test)q, currentUser.getId());         
                showAlertMessageInfo("Ajouter Test", "Test ajouter avec succès") ;
            }
            
            
            q = null ;
            
            labelQuestion.setText("");
            rbtn1.setText("");
            rbtn2.setText("");
            rbtn3.setText("");
            rbtn4.setText("");
            
            tfSujet.setEditable(true);
            tfSujet.setText("");
            tfQposee.setText("");
            tfRcorrecte.setText("");
            tfMrep1.setText("");
            tfMrep2.setText("");
            tfMrep3.setText("");
            tfnote.setText("");
            
            comboFormation.setVisible(false);
            comboType.setValue(null); 
            
        }
    }
    
    public void showAddedQuestion(int index){
        Question question = q.getQuestion(index ) ;
        labelQuestion.setText(question.getQuestionPosee());
        rbtn1.setText(question.getReponseCorrecte());
        rbtn1.setSelected(true);
        rbtn2.setText(question.getReponseFausse1());
        rbtn3.setText(question.getReponseFausse2());
        rbtn4.setText(question.getReponseFausse3());
    }
    
     @FXML
    private void displayTest(Event event) {
        showTest();
        ObservableList<String> options = FXCollections.observableArrayList("Quiz","Test","Question");
        comboApplyOn.setItems(options);
    }
    
    public void showTest(){
       QuizDao qdao = QuizDao.getInstance() ;
       ObservableList<Quiz> allQuiz = qdao.displayQuizList(currentUser.getId());
       
       TestDao tdao = TestDao.getInstance() ;
       ObservableList<Test> listTest = tdao.displayTestList(currentUser.getId());
       
       allQuiz.addAll(listTest );
       
       tvQuiz.setItems(allQuiz);
       colDuree.setCellValueFactory(new PropertyValueFactory<>("temps"));
       colSujet.setCellValueFactory(new PropertyValueFactory<>("sujet"));
       
        ArrayList<Question> questions = new ArrayList<>() ;
       
       for(Quiz quiz : allQuiz){
          questions.addAll(quiz.getQuestions()) ;
           //System.out.println(quiz.getClass().getSimpleName());
       }
       
       ObservableList<Question> listQuestion = FXCollections.observableArrayList();
       listQuestion.addAll(questions) ;
       afficherQuestion(listQuestion);
    }
    
    private void afficherQuestion(ObservableList<Question> listQuestion){
        tvQuestion.setItems(listQuestion);
        colQuiz.setCellValueFactory(new PropertyValueFactory<>("type"));
        colQposee.setCellValueFactory(new PropertyValueFactory<>("questionPosee"));
        colRep.setCellValueFactory(new PropertyValueFactory<>("reponseCorrecte"));
        colMrep1.setCellValueFactory(new PropertyValueFactory<>("reponseFausse1"));
        colMrep2.setCellValueFactory(new PropertyValueFactory<>("reponseFausse2"));
        colMrep3.setCellValueFactory(new PropertyValueFactory<>("reponseFausse3"));
        colNote.setCellValueFactory(new PropertyValueFactory<>("note"));
    }

    @FXML
    private void updateTest(ActionEvent event) {
        String option = comboApplyOn.getValue() ;
        if(option == null){  
            showAlertMessageError("Update Quiz", "veullez selectionner l'objet !!!");
        }
        else{
            if(option.toLowerCase().equals("question")){
                  String qPosee = tfQposeeShow.getText() ;
                  String rCorrecte = tfRcorrShow.getText() ;
                  String mRep1 = tfMrep1Show.getText() ;
                  String mRep2 = tfMrep2Show.getText() ;
                  String mRep3 = tfMrep3Show.getText() ;
                  
                  if(!qPosee.isEmpty() && !rCorrecte.isEmpty() && !mRep1.isEmpty() && !mRep2.isEmpty() && !mRep3.isEmpty()
                            && !tfMrepNoteShow.getText().isEmpty()){                      

                        int note = Integer.parseInt(tfMrepNoteShow.getText()) ;
                        MouseEvent e = null ;
                        Question selectedQuestion = showSelectedItem(e); 
                        Question question = new Question(qPosee, rCorrecte, mRep1, mRep2, mRep3, note) ;
                        question.setId(selectedQuestion.getId());
                        QuestionDao qqdao = QuestionDao.getInstance() ;
                        if(selectedQuestion.getType().toLowerCase().equals("test"))
                            qqdao.updateQuestion(question, "test") ;
                        else
                            qqdao.updateQuestion(question, "quiz") ;
                        showTest();
                        
                        showAlertMessageInfo("update Question", "Question mise à jour !");

                        tfQposeeShow.setText("");
                        tfRcorrShow.setText("");
                        tfMrep1Show.setText("");
                        tfMrep2Show.setText("");
                        tfMrep3Show.setText("");
                        tfMrepNoteShow.setText("");

                    }else{
                        showAlertMessageError("modifier Question", "veuillez remplir tous les champs!");
                    }                 
             }
            
            if((option.toLowerCase().equals("quiz")) || (option.toLowerCase().equals("test"))){
                 if(tfSujetShow.getText().isEmpty()){       
                     showAlertMessageError("Update Quiz", "veuillez selectionner le quiz à modifier !!!");
                }
                else{
                     Quiz quiz ;
                     Test test ;
                     MouseEvent e = null ;
                     if(showSelectedQuiz(e).getClass().getSimpleName().toLowerCase().equals("quiz")){
                         quiz = showSelectedQuiz(e) ;
                         quiz.setSujet(tfSujetShow.getText());
                          QuizDao qdao = QuizDao.getInstance() ;
                        if(qdao.updateQuiz(quiz, currentUser.getId())){   
                            showAlertMessageInfo("update Quiz", "Quiz mis à jour !");
                            showTest();
                        }
                     }
                     else if(showSelectedQuiz(e).getClass().getSimpleName().toLowerCase().equals("test")){                       
                         test = (Test)showSelectedQuiz(e) ;
                         test.setSujet(tfSujetShow.getText());
                         int duree = (comboHeureUpdate.getValue()*3600) + (comboMinUpdate.getValue()*60) ;
                         test.setDuree(duree);
                         TestDao tdao = TestDao.getInstance() ;
                        if(tdao.updateTest(test, currentUser.getId())){
                            showAlertMessageInfo("update Test", "test mis à jour !");
                            showTest();
                            comboHeureUpdate.setValue(null);
                            comboMinUpdate.setValue(null);
                         }
                     }
                 }
            }
            
        }          
    }

    @FXML
    private void deleteTest(ActionEvent event) {
         String option = comboApplyOn.getValue() ;
        if(option == null){
            showAlertMessageError("Update Quiz", "veuillez selectionner l'objet !!!");
        }
        else{ 
            
            if(option.toLowerCase().equals("question")){
                        String qPosee = tfQposeeShow.getText() ;
                        String rCorrecte = tfRcorrShow.getText() ;
                        String mRep1 = tfMrep1Show.getText() ;
                        String mRep2 = tfMrep2Show.getText() ;
                        String mRep3 = tfMrep3Show.getText() ;                  

                        if(qPosee.isEmpty() && rCorrecte.isEmpty() && mRep1.isEmpty() && mRep2.isEmpty() && mRep3.isEmpty()
                                  && tfnote.getText().isEmpty()){                      
                              showAlertMessageError("Delete Question", "veullez selectionner la question à supprimer !!!");
                        }
                        else{
                              MouseEvent ev = null ;
                              Question question = showSelectedItem(ev);

                              Alert.AlertType Atype = Alert.AlertType.CONFIRMATION ;
                              Alert a = new Alert(Atype,"" );
                              a.initModality(Modality.APPLICATION_MODAL);
                              a.getDialogPane().setContentText("Cette action suprimera la question");
                              a.getDialogPane().setHeaderText("Supprimer Question");
                             // alert.show(); 

                              Optional<ButtonType> resultat =  a.showAndWait() ;
                              if(resultat.get() == ButtonType.OK){
                                  QuestionDao qdao = QuestionDao.getInstance();
                                  qdao.deleteQuestion(question, question.getType());
                                  showTest();

                                  tfQposeeShow.setText("");
                                  tfRcorrShow.setText("");
                                  tfMrep1Show.setText("");
                                  tfMrep2Show.setText("");
                                  tfMrep3Show.setText("");
                                  tfMrepNoteShow.setText("");

                          }                 
                      }             
              }
            else{
                if(tfSujetShow.getText().isEmpty()){  
                    showAlertMessageError("Delete Quiz", "veullez selectionner le quiz à supprimer !!!");
                }
             else{                                         
                        MouseEvent e = null ;
                        Quiz quiz = showSelectedQuiz(e);

                        Alert.AlertType type = Alert.AlertType.CONFIRMATION ;
                        Alert alert = new Alert(type,"" );
                        alert.initModality(Modality.APPLICATION_MODAL);
                        alert.getDialogPane().setContentText("Cette action suprimera le quiz et toutes ces questions");
                        alert.getDialogPane().setHeaderText("Supprimer Quiz");
                       // alert.show(); 

                        Optional<ButtonType> result =  alert.showAndWait() ;
                        if(result.get() == ButtonType.OK){
                            if(option.toLowerCase().equals("quiz")){
                                QuizDao qdao = QuizDao.getInstance() ;
                                qdao.deleteQuiz(quiz);
                                showTest();
                            }

                            if(option.toLowerCase().equals("test")){
                                TestDao tdao = TestDao.getInstance() ;
                                tdao.deleteTest((Test)quiz);
                                showTest();
                            }

                            tfQposeeShow.setText("");
                            tfRcorrShow.setText("");
                            tfMrep1Show.setText("");
                            tfMrep2Show.setText("");
                            tfMrep3Show.setText("");
                            tfMrepNoteShow.setText("");

                         }                           

                }
            }
                 
        }
    }

    @FXML
    private Question showSelectedItem(MouseEvent event) {
        Question question = tvQuestion.getSelectionModel().getSelectedItem() ;
       if(question != null){
        tfQposeeShow.setText(question.getQuestionPosee());
        tfRcorrShow.setText(question.getReponseCorrecte());
        tfMrep1Show.setText(question.getReponseFausse1());
        tfMrep2Show.setText(question.getReponseFausse2());
        tfMrep3Show.setText(question.getReponseFausse3());
        tfMrepNoteShow.setText(question.getNote()+"");
       }
     
       return question ;                  
    }

    @FXML
    private Quiz showSelectedQuiz(MouseEvent event) {
        Quiz quiz = tvQuiz.getSelectionModel().getSelectedItem() ;
        if(quiz != null){
            if(quiz.getClass().getSimpleName().toLowerCase().equals("test")){          
                ObservableList<Integer> heures = FXCollections.observableArrayList();
                ObservableList<Integer> minutes = FXCollections.observableArrayList();

                int i;
                for(i = 0; i < 24; i++){
                    heures.add(i) ;
                }

                for(i = 0; i < 60; i++){
                    minutes.add(i) ;
                }
                int duree = ((Test)quiz).getDuree() ;
                int heure = duree / 3600 ;
                int min = (duree%3600)/60 ;
                comboHeureUpdate.setItems(heures);
                comboMinUpdate.setItems(minutes);
                comboHeureUpdate.setVisible(true);
                comboMinUpdate.setVisible(true);
             }
        }      
        else{
            comboHeureUpdate.setVisible(false);
            comboMinUpdate.setVisible(false);
        }
        ArrayList<Question> questions = quiz.getQuestions() ;
        ObservableList<Question> listQuestion = FXCollections.observableArrayList() ;
        listQuestion.addAll(questions) ;
        afficherQuestion(listQuestion);
      //  Question quest = questions.get(0) ;
       
       tfSujetShow.setText(quiz.getSujet());
       /*tfQposeeShow.setText(quest.getQuestionPosee());
       tfRcorrShow.setText(quest.getReponseCorrecte());
       tfMrep1Show.setText(quest.getReponseFausse1());
       tfMrep2Show.setText(quest.getReponseFausse2());
       tfMrep3Show.setText(quest.getReponseFausse3());
       tfMrepNoteShow.setText(quest.getNote()+"");*/
       
       return quiz ;
        
    }   

    @FXML
    private void updateAddQuestion(ActionEvent event) {
        if(tfSujetShow.getText().isEmpty()){
            showAlertMessageError("Ajouter Question", "veullez selectionner le quiz où ajouter la question !!!");
        }
        else{
            Quiz quiz = showSelectedQuiz(null) ;
            
            String qPosee = tfQposeeShow.getText() ;
            String rCorrecte = tfRcorrShow.getText() ;
            String mRep1 = tfMrep1Show.getText() ;
            String mRep2 = tfMrep2Show.getText() ;
            String mRep3 = tfMrep3Show.getText() ;
            
            boolean exists = false ;
            for(Question qq : quiz.getQuestions()){
                if(qq.getQuestionPosee().equals(qPosee) && qq.getReponseCorrecte().equals(rCorrecte) &&
                        qq.getReponseFausse1().equals(mRep1) && qq.getReponseFausse2().equals(mRep2)){
                    exists = true ;
                   // break ;
                }
            }
            
            if(exists){
                showAlertMessageError("Ajouter Question", "la question existe déjà!!!");
            }
            if(!exists){
                if(!qPosee.isEmpty() && !rCorrecte.isEmpty() && !mRep1.isEmpty() && !mRep2.isEmpty() && !mRep3.isEmpty()
                            && !tfMrepNoteShow.getText().isEmpty()){
                    int note = Integer.parseInt(tfMrepNoteShow.getText()) ;
                    
                    Question question = new Question(qPosee, rCorrecte, mRep1, mRep2, mRep3, note) ;
                    QuestionDao qdao = QuestionDao.getInstance() ;
                    qdao.insertQuestion(question, quiz.getId(), "quiz");
                    showTest(); 
                    
                    tfQposeeShow.setText("");
                    tfRcorrShow.setText("");
                    tfMrep1Show.setText("");
                    tfMrep2Show.setText("");
                    tfMrep3Show.setText("");
                    tfMrepNoteShow.setText("");
                    tfSujetShow.setText("");
                }
                else{
                    showAlertMessageError("Ajouter Question", "veuillez rensigner tous les champs!!!");
                }
            }
        }
    }

    @FXML
    private void changeComboType(ActionEvent event) {
       if(comboType.getValue().toLowerCase().equals("test")){
           
           q = new Test() ;
           ObservableList<String> options = FXCollections.observableArrayList();
           TestDao tdao = TestDao.getInstance() ;
           List<Formation> formations =  tdao.getFormations(currentUser.getId()) ;
           for(Formation f : formations){
               options.add(f.getTitre()) ;
           }
           comboFormation.setItems(options);
           comboFormation.setVisible(true);
           
           ObservableList<Integer> heures = FXCollections.observableArrayList();
           ObservableList<Integer> minutes = FXCollections.observableArrayList();
           
           int i;
           for(i = 0; i < 24; i++){
               heures.add(i) ;
           }
           
           for(i = 0; i < 60; i++){
               minutes.add(i) ;
           }
           
           labelDuree.setVisible(true);
           comboHeure.setItems(heures);
           comboMinutes.setItems(minutes);
           comboHeure.setVisible(true);
           comboMinutes.setVisible(true);
       }
       else if(comboType.getValue().toLowerCase().equals("quiz")){
           q = new Quiz() ;
           comboFormation.setVisible(false);
           comboHeure.setVisible(false);
           comboMinutes.setVisible(false);
           labelDuree.setVisible(false);
           comboMinutes.setVisible(false);
       }
       
    }

    @FXML
    private void changeComboFormation(ActionEvent event) {     
    }
    
    private void setNoteToTableView(){
        NoteDao ndao = NoteDao.getInstance() ;
        ObservableList<ListNote> list = ndao.getAllNote(currentUser.getId() );
        
        ListNote note = list.get(0) ;
        ObservableList<ListNote> listUser = ndao.getNoteByUserId(currentUser.getId(), note.getIdEtudiant()) ;
        setDataToLineChart(listUser);
        
        tvNote.setItems(list);
        colSujetNote.setCellValueFactory(new PropertyValueFactory<>("sujet"));
        colFormation.setCellValueFactory(new PropertyValueFactory<>("formation"));
        colEtudStat.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNoteStat.setCellValueFactory(new PropertyValueFactory<>("noteObtnue"));
    }
    
    private void setDataToLineChart(ObservableList<ListNote> list){
        ObservableList<String> test = FXCollections.observableArrayList( );
        XYChart.Series s = new XYChart.Series<>() ;
         for(ListNote l : list){
             s.getData().add(new XYChart.Data<>(l.getSujet(),l.getNoteObtnue())) ; 
             test.add(l.getSujet()) ;
          }
         
        x.setCategories(test);
        s.setName(list.get(0).getNom());
        lineChart.getData().addAll(s);
    }

    @FXML
    private void displayNoteTableView(Event event) {
        setNoteToTableView() ;
        TestDao tdao = TestDao.getInstance() ;
        ObservableList<Test> listTest = tdao.displayTestList(currentUser.getId());
        tvTestNote.setItems(listTest);
        colTestNote.setCellValueFactory(new PropertyValueFactory<>("sujet"));
        setDataToBarChart(listTest.get(0));
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
    private void searchQuestion(KeyEvent event) {
    }

    @FXML
    private void getClickedNote(MouseEvent event) {
        lineChart.getData().clear();
        NoteDao ndao = NoteDao.getInstance() ;
        ListNote note = tvNote.getSelectionModel().getSelectedItem() ;
        if(note != null){
            ObservableList<ListNote> listUser = ndao.getNoteByUserId(currentUser.getId(), note.getIdEtudiant()) ;
            setDataToLineChart(listUser);
        }
      }     

    @FXML
    private void getClickedTest(MouseEvent event) {
        Test t = tvTestNote.getSelectionModel().getSelectedItem() ;
        if(t != null){
            barChart.getData().clear();
            setDataToBarChart(t);
        }
    }
      
}
