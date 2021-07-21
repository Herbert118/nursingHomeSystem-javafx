package com.neuedu.view.template;

import com.jfoenix.controls.JFXButton;
import com.neuedu.model.Question;
import com.neuedu.model.Service;
import com.neuedu.model.Template;
import com.neuedu.view.component.Alert;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChooseQuesModal {
    private Stage quesModal;
    private VBox modalRoot;
    private HBox btnBox;
    private Template template;
    private TableView<Question> quesTable;
    private TableColumn<Question,String> idCol;
    private TableColumn<Question,String> stemCol;
    private TableColumn<Question,String> formCol;
    private JFXButton confirmBtn;
    private Service service;
    private Text warnText;
    public ChooseQuesModal(Template template, Service service){
        this.template = template;
        this.service = service;
        init();
        lay();
    }
    private void init() {
        quesModal = new Stage();
        modalRoot = new VBox();
        btnBox = new HBox();
        quesTable = new TableView<>();
        idCol = new TableColumn<>("id");
        stemCol = new TableColumn<>("题干");
        formCol = new TableColumn<>("类型");
        confirmBtn = new JFXButton("确认");
        warnText = new Text("");

        quesTable.setItems(service.getQuestionList());
        quesTable.getColumns().addAll(idCol,stemCol,formCol);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        stemCol.setCellValueFactory(new PropertyValueFactory<>("stem"));
        formCol.setCellValueFactory(new PropertyValueFactory<>("form"));

        



        
    }
    
    private void lay() {
        modalRoot.getChildren().add(quesTable);
        modalRoot.getChildren().add(btnBox);
        modalRoot.getChildren().add(warnText);
        modalRoot.setSpacing(20);
        modalRoot.setPadding(new Insets(20,20,50,20));
        
        btnBox.getChildren().add(confirmBtn);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setSpacing(20);
        btnBox.setPadding(new Insets(20));
        
    }
    public void addQuesToTem(){
    	TableView.TableViewSelectionModel tvsm = quesTable.getSelectionModel();
    	confirmBtn.setOnAction(e->{
            ObservableList<Question> questions = tvsm.getSelectedItems();
            String text1 = "问题";
            String text2 = "问题";
            if(questions.isEmpty()){
                Alert.showAlert("请先选择一个问题");
            }
            for(Question question:questions){
                boolean success = service.addQuestionToTem(template,question);
                if(success) {
                	text1 += question.getId()+" ";
                }
                else {
                	text2 += question.getId()+" ";
                }
                
            }
            warnText.setText(text1+"新增成功"+"\n"+text2+"新增失败");
        });
    	
        Scene scene = new Scene(modalRoot);
        scene.getStylesheets().add("modal.css");
        quesModal.setScene(scene);
        quesModal.setTitle("选择问题");
        quesModal.showAndWait();
    }

}
