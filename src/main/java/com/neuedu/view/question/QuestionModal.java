package com.neuedu.view.question;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.neuedu.model.Question;
import com.neuedu.model.Service;
import com.neuedu.util.CheckUtil;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class QuestionModal {
    private Service service;
    private Stage questionModal;
    private VBox modalRoot;
    private GridPane fieldGridPane;
    private JFXTextField stemFld;
    private JFXTextField formFld;
    private JFXTextField choiceAFld;
    private JFXTextField choiceBFld;
    private JFXTextField choiceCFld;
    private JFXButton confirmButton;
    private Text warnText;
    public QuestionModal(Service service){
        this.service = service;
        init();
        lay();
    }
    private void init(){
        questionModal = new Stage();
        modalRoot = new VBox();
        fieldGridPane = new GridPane();
        stemFld = new JFXTextField();
        formFld = new JFXTextField();
        choiceAFld = new JFXTextField();
        choiceBFld = new JFXTextField();
        choiceCFld = new JFXTextField();
        confirmButton = new JFXButton("确认");
        warnText = new Text();
    }
    private void lay(){
        fieldGridPane.add(new Label("题干"),0,0);
        fieldGridPane.add(new Label(("类型")),0,1);
        fieldGridPane.add(new Label("选项A"),0,2);

        fieldGridPane.add(stemFld,1,0,3,1);
        fieldGridPane.add(formFld, 1, 1);
        fieldGridPane.add(choiceAFld, 1, 2);

        fieldGridPane.add(new Label("选项B"),2,1);
        fieldGridPane.add(new Label("选项C"),2,2);

        fieldGridPane.add(choiceBFld,3,1);
        fieldGridPane.add(choiceCFld,3,2);

        fieldGridPane.setHgap(20);
        fieldGridPane.setVgap(20);
        fieldGridPane.setAlignment(Pos.CENTER);
        fieldGridPane.setPadding(new Insets(50,30,30,30));

        modalRoot.getChildren().add(fieldGridPane);
        modalRoot.getChildren().add(confirmButton);
        modalRoot.setAlignment(Pos.CENTER);

        VBox.setMargin(confirmButton,new Insets(30));
        modalRoot.getChildren().add(warnText);
        VBox.setMargin(warnText, new Insets(30,0,0,0));
        warnText.setFill(Color.RED);

    }

    public void addQuestion(){
        confirmButton.setOnAction(e ->{
            String stem = stemFld.getText();
            String form = formFld.getText();
            String choiceA = choiceAFld.getText();
            String choiceB= choiceBFld.getText();
            String choiceC = choiceCFld.getText();


                if(!check(stem, form,choiceA,choiceB,choiceC)){
                    return;
                }
                boolean success = true;
                success = service.addQuestion(stem,form,choiceA,choiceB,choiceC);
                if(success) {
                    warnText.setText("新增成功");
                }
                else {
                    warnText.setText("新增失败");
                }
            }


        );

        Scene scene = new Scene(modalRoot);
        questionModal.setScene(scene);
        scene.getStylesheets().add("modal.css");
        questionModal.setTitle("确认");
        questionModal.showAndWait();
    }

    public void updateQuestion(Question oldQuestion){
        stemFld.setText(oldQuestion.getStem());
        formFld.setText(oldQuestion.getForm());
        choiceAFld.setText(oldQuestion.getChoiceA());
        choiceBFld.setText(oldQuestion.getChoiceB());
        choiceCFld.setText(oldQuestion.getChoiceC());
        confirmButton.setOnAction(e ->{
                    String stem = stemFld.getText();
                    String form = formFld.getText();
                    String choiceA = choiceAFld.getText();
                    String choiceB= choiceBFld.getText();
                    String choiceC = choiceCFld.getText();


                    if(!check(stem, form,choiceA,choiceB,choiceC)){
                        return;
                    }
                    boolean success = true;
                    success = service.updateQuestion(oldQuestion,stem,form,choiceA,choiceB,choiceC);
                    if(success) {
                        warnText.setText("修改成功");
                    }
                    else {
                        warnText.setText("修改失败");
                    }
                }


        );

        Scene scene = new Scene(modalRoot);
        
        questionModal.setScene(scene);
        scene.getStylesheets().add("modal.css");
        questionModal.setTitle("修改问题");
        questionModal.showAndWait();
    }


    private boolean check(String stem, String form, String choiceA, String choiceB,String choiceC) {
        boolean result = true;
        String text = "";
        if (!CheckUtil.checkNotBlank(stem)){
            text += "题干 ";
            result = false;
        }
        if (!CheckUtil.checkNotBlank(form)){
            text += "类型 ";
            result = false;
        }

        if(!CheckUtil.checkNotBlank(choiceA)){
            text += "选项A ";
            result = false;
        }
        if(!CheckUtil.checkDescription(choiceB)){
            text += "选项B ";
            result = false;
        }
        if(!CheckUtil.checkDescription(choiceC)){
            text += "选项C ";
            result = false;
        }
        if(result == false){
            warnText.setText(text+"格式有误");
        }
        return result;

    }


}
