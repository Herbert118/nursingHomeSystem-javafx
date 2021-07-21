package com.neuedu.view.question;

import java.net.URL;
import java.util.ResourceBundle;

import com.neuedu.model.Question;
import com.neuedu.model.Service;

import com.neuedu.view.component.Alert;
import com.neuedu.view.component.ConfirmAlert;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputEvent;
import javafx.scene.input.InputMethodEvent;

public class QuestionViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label title;

    @FXML
    private TextField searchFld;

    @FXML
    private TableView<Question> questionTable;

    @FXML
    private TableColumn<Question, String> idCol;

    @FXML
    private TableColumn<Question, String> stemCol;

    @FXML
    private TableColumn<Question, String> formCol;

    private Service service;
    private TableSelectionModel<Question> tsm;

    @FXML
    void addQuestion(ActionEvent event) {
        QuestionModal modal = new QuestionModal(service);
        modal.addQuestion();
        questionTable.setItems(service.getQuestionList());
    }

    @FXML
    void deleteQuestion(ActionEvent event) {
    	ObservableList<Question> questions = tsm.getSelectedItems();
    	if(questions.isEmpty()){
            Alert.showAlert("请先选择一个问题!");
    	    return;
        }
        if (!ConfirmAlert.getConfirm("您确定要删除吗?")) {
            return;
        }
        String text = "";
        for(Question question:questions) {

            if (service.deleteQuestion(question)) {

                text+=(question.getId() + "删除成功");

            } else {
                text+=(question.getId() + "删除失败");
            }
        }
        Alert.showAlert(text);
        questionTable.setItems(service.getQuestionList());
    }


    @FXML
    void updateQuestion(ActionEvent event) {
        Question question = tsm.getSelectedItem();
        if(question == null){
            Alert.showAlert("请先选择一个问题!");
            return;
        }
        QuestionModal modal = new QuestionModal(service);
        modal.updateQuestion(question);
        questionTable.setItems(service.getQuestionList());

    }
    @FXML
    void searchQuestion(InputEvent event) {
//    	String text = searchFld.getText();
//    	if(text.isEmpty()) {
//    		this.questionTable.setItems(service.getQuestionList());
//    	}
//    	else {
//    		this.questionTable.setItems(service.searchQuestion(text));
//    	}
        //considered useless
    }

    @FXML
    void initialize() {
    	service = Service.getInstance();
        check();
        initTable();
    }

    private void initTable() {
        questionTable.setItems(service.getQuestionList());
        System.out.println(service.getQuestionList());
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        stemCol.setCellValueFactory(new PropertyValueFactory<>("stem"));
        formCol.setCellValueFactory(new PropertyValueFactory<>("form"));
        
        tsm = questionTable.getSelectionModel();
        
        searchFld.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String text = searchFld.getText();
                if(text.isEmpty()) {
                    questionTable.setItems(service.getQuestionList());
                }
                else {
                    questionTable.setItems(service.searchQuestion(text));
                }
            }
        });
    }

    public void check() {
        assert title != null : "fx:id=\"title\" was not injected: check your FXML file 'questionView.fxml'.";
        assert searchFld != null : "fx:id=\"searchFld\" was not injected: check your FXML file 'questionView.fxml'.";
        assert idCol != null : "fx:id=\"idCol\" was not injected: check your FXML file 'questionView.fxml'.";
        assert stemCol != null : "fx:id=\"stemCol\" was not injected: check your FXML file 'questionView.fxml'.";
        assert formCol != null : "fx:id=\"formCol\" was not injected: check your FXML file 'questionView.fxml'.";
    }
}
