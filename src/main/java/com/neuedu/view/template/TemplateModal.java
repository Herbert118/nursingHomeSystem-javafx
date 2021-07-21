package com.neuedu.view.template;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.neuedu.model.Service;
import com.neuedu.model.Template;
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

public class TemplateModal {
    private Service service;
    private Stage TemplateModal;
    private VBox modalRoot;
    private GridPane fieldGridPane;
    private JFXTextField nameFld;
    private JFXTextField temTypeFld;
    private JFXButton confirmButton;
    private Text warnText;
    private String title;
    public TemplateModal(Service service,String title){
        this.service = service;
        this.title = title;
        init();
        lay();
    }
    private void init(){
        TemplateModal = new Stage();
        modalRoot = new VBox();
        fieldGridPane = new GridPane();
        nameFld = new JFXTextField();
        temTypeFld = new JFXTextField();
        confirmButton = new JFXButton("确认");
        warnText = new Text();
    }
    private void lay(){
        fieldGridPane.add(new Label("名称"),0,0);
        fieldGridPane.add(new Label(("类型")),0,1);

        fieldGridPane.add(nameFld,1,0);
        fieldGridPane.add(temTypeFld,1,1);

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

    public void addTemplate(){
        confirmButton.setOnAction(e ->{
            String stem = nameFld.getText();
            String form = temTypeFld.getText();
            

                if(!check(stem, form)){
                    return;
                }
                boolean success = true;
                success = service.addTemplate(stem,form);
                if(success) {
                    warnText.setText("新增成功");
                }
                else {
                    warnText.setText("新增失败");
                }
            }


        );

        Scene scene = new Scene(modalRoot);
        scene.getStylesheets().add("modal.css");
        TemplateModal.setScene(scene);
        TemplateModal.setTitle(title);
        TemplateModal.showAndWait();
    }

    public void updateTemplate(Template oldTemplate){

        nameFld.setText(oldTemplate.getName());
        temTypeFld.setText(oldTemplate.getTemType());
        confirmButton.setOnAction(e ->{
                    String stem = nameFld.getText();
                    String form = temTypeFld.getText();


                    if(!check(stem, form)){
                        return;
                    }
                    boolean success = true;
                    success = service.updateTemplate(oldTemplate,stem,form);
                    if(success) {
                        warnText.setText("修改成功");
                    }
                    else {
                        warnText.setText("修改失败");
                    }
                }


        );

        Scene scene = new Scene(modalRoot);
        scene.getStylesheets().add("modal.css");
        TemplateModal.setScene(scene);
        TemplateModal.setTitle(title);
        TemplateModal.showAndWait();
    }


    private boolean check(String name, String temType) {
        boolean result = true;
        String text = "";
        if (!CheckUtil.checkNotBlank(name)){
            text += "名称 ";
            result = false;
        }
        if (!CheckUtil.checkNotBlank(temType)){
            text += "类型 ";
            result = false;
        }

        if(result == false){
            warnText.setText(text+"格式有误");
        }
        return result;

    }


}
