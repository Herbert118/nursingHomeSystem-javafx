package com.neuedu.view.patient;

import com.jfoenix.controls.JFXButton;
import com.neuedu.model.Patient;
import com.neuedu.model.Service;
import com.neuedu.model.Template;
import com.neuedu.view.component.Alert;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChooseTemModal {
    private Stage chooseTemModal;
    private VBox modalRoot;
    private HBox btnBox;
    private Patient Patient;
    private TableView<Template> temTable;
    private TableColumn<Template,String> idCol;
    private TableColumn<Template,String> nameCol;
    private TableColumn<Template,String> temTypeCol;
    private JFXButton confirmBtn;
    private Service service;
    private Text warnText;
    private Template resultTemplate;
    public ChooseTemModal( Service service){
        this.service = service;
        init();
        lay();
    }
    private void init() {
        chooseTemModal = new Stage();
        modalRoot = new VBox();
        btnBox = new HBox();
        temTable = new TableView<>();
        idCol = new TableColumn<>("id");
        nameCol = new TableColumn<>("名称");
        temTypeCol = new TableColumn<>("类型");
        confirmBtn = new JFXButton("确认");
        warnText = new Text("");

        temTable.setItems(service.getTemplateList());
        temTable.getColumns().addAll(idCol,nameCol,temTypeCol);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        temTypeCol.setCellValueFactory(new PropertyValueFactory<>("temType"));

        



        
    }
    
    private void lay() {
        modalRoot.getChildren().add(temTable);
        modalRoot.getChildren().add(btnBox);
        modalRoot.getChildren().add(warnText);
        modalRoot.setSpacing(20);
        modalRoot.setPadding(new Insets(20,20,50,20));
        btnBox.getChildren().add(confirmBtn);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setSpacing(20);
        btnBox.setPadding(new Insets(20));
        
    }
    public Template chooseTem(){
    	TableView.TableViewSelectionModel<Template> tvsm = temTable.getSelectionModel();
    	confirmBtn.setOnAction(e->{
            Template template = tvsm.getSelectedItem();
            String text1 = "模板 ";
            if(template == null){
                Alert.showAlert("请先选择一个模板");
                return;
            }
            resultTemplate = template;
            warnText.setText(text1 + template.getName());
    	});
        Scene scene = new Scene(modalRoot);
        scene.getStylesheets().add("modal.css");
        chooseTemModal.setScene(scene);
        chooseTemModal.setTitle("选择模板");
        chooseTemModal.showAndWait();
        return resultTemplate;
    }

}
