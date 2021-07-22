package com.neuedu.view.bed;

import java.time.LocalDate;

import com.jfoenix.controls.JFXButton;
import com.neuedu.model.Bed;
import com.neuedu.model.Patient;
import com.neuedu.model.Service;
import com.neuedu.view.component.Alert;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChoosePatModal {
    private Stage choosePatientModal;
    private VBox modalRoot;
    private HBox btnBox;
    private Bed bed;
    private TableView<Patient> paTable;
    private TableColumn<Patient,String> nameCol;
    private TableColumn<Patient,String> ageCol;
    private TableColumn<Patient,String> sexCol;
    private GridPane pickerGridPane;
    private DatePicker startTimePicker;
    private DatePicker endTimePicker;
    private JFXButton confirmBtn;
    private Service service;
    private Text warnText;
    public ChoosePatModal(Bed bed, Service service){
        this.bed = bed;
        this.service = service;
        init();
        lay();
    }
    private void init() {
        choosePatientModal = new Stage();
        modalRoot = new VBox();
        btnBox = new HBox();
        paTable = new TableView<>();
        confirmBtn = new JFXButton("确认");
        warnText = new Text("");
        pickerGridPane = new GridPane();
        startTimePicker = new DatePicker();
        endTimePicker = new DatePicker();
        
        nameCol = new TableColumn<Patient, String>("姓名");
        ageCol = new TableColumn<Patient, String>("年龄");
        sexCol = new TableColumn<Patient, String>("性别");
        
        

        paTable.setItems(service.getUnsettledPatientList());
        paTable.getColumns().addAll(nameCol,ageCol,sexCol);
        //获取未入住的病人
        //TODO:add check here
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        sexCol.setCellValueFactory(new PropertyValueFactory<>("sex"));
        

    }

    private void lay() {
        modalRoot.getChildren().add(paTable);
        modalRoot.getChildren().add(pickerGridPane);
        modalRoot.getChildren().add(btnBox);
        modalRoot.getChildren().add(warnText);
        modalRoot.setSpacing(20);
        modalRoot.setPadding(new Insets(20,20,50,20));
        

        pickerGridPane.add(new Label("入住时间"),0,0);
        pickerGridPane.add(new Label("出院时间"),0,1);
        pickerGridPane.add(startTimePicker,1,0);
        pickerGridPane.add(endTimePicker,1,1);
        pickerGridPane.setPadding(new Insets(20));

        btnBox.getChildren().add(confirmBtn);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setSpacing(20);
        btnBox.setPadding(new Insets(20));
        
        

    }
    public void moveIn(){
        TableView.TableViewSelectionModel<Patient> tvsm = paTable.getSelectionModel();
        confirmBtn.setOnAction(e->{
            Patient patient = tvsm.getSelectedItem();
            String text1 = "病人";
            LocalDate startTime = startTimePicker.getValue();
            LocalDate endTime = endTimePicker.getValue();

            if(patient == null){
                Alert.showAlert("请先选择一个病人");
                return;
            }
            if(startTime.isAfter(endTime)) {
            	Alert.showAlert("时间有误!");
            	return;
            	
            }

                boolean success = service.moveIn(bed,patient,startTime,endTime);
                if(success) {
                    text1 += patient.getName()+" ";
                    warnText.setText(text1+"入住成功");
                }
                else {
                    text1 += patient.getName()+"入住失败";
                    warnText.setText(text1+"入住成功");
                }



        });
        
        Scene scene = new Scene(modalRoot);
        scene.getStylesheets().add("modal.css");
        choosePatientModal.setTitle("选择病人");
        choosePatientModal.setScene(scene);
        choosePatientModal.showAndWait();
    }
    
   

}
