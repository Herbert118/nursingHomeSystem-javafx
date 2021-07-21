package com.neuedu.view.special;

import java.time.Duration;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.neuedu.model.Patient;
import com.neuedu.model.Service;
import com.neuedu.model.SpecialRoom;
import com.neuedu.view.component.Alert;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ApplyPaModal {
    private Stage choosePatientModal;
    private VBox modalRoot;
    private HBox btnBox;
    private SpecialRoom specialRoom;
    private TableView<Patient> paTable;
    private TableColumn<Patient,String> nameCol;
    private TableColumn<Patient,String> ageCol;
    private TableColumn<Patient,String> sexCol;
    private GridPane boxGridPane;
    
    private JFXButton confirmBtn;
    private Service service;
    private Text warnText;
	private JFXComboBox<Integer> hourBox;
	private JFXComboBox<Integer> minuteBox;
    public ApplyPaModal(SpecialRoom specialRoom, Service service){
        this.specialRoom = specialRoom;
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
        boxGridPane = new GridPane();
        hourBox = new JFXComboBox<Integer>();
        minuteBox = new JFXComboBox<Integer>();
        for(int i = 0; i< 10; i++) {
        	hourBox.getItems().add(i);
        }
        for(int i = 0; i< 60;i++) {
        	minuteBox.getItems().add(i);
        }
        
        nameCol = new TableColumn<Patient, String>();
        ageCol = new TableColumn<Patient, String>();
        sexCol = new TableColumn<Patient, String>();
        
        

        paTable.setItems(service.getPatientList());
        paTable.getColumns().addAll(nameCol,ageCol,sexCol);
        //获取未入住的病人
        //TODO:add check here
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        sexCol.setCellValueFactory(new PropertyValueFactory<>("sex"));
        

    }

    private void lay() {
        modalRoot.getChildren().add(paTable);
        modalRoot.getChildren().add(boxGridPane);
        modalRoot.getChildren().add(btnBox);
        modalRoot.getChildren().add(warnText);
        modalRoot.setSpacing(20);
        modalRoot.setPadding(new Insets(20,20,50,20));
        

        boxGridPane.add(new Label("小时"),1,0);
        boxGridPane.add(new Label("分钟"),1,1);
        boxGridPane.add(hourBox,0,0);
        boxGridPane.add(minuteBox,0,1);
        boxGridPane.setPadding(new Insets(20));

        btnBox.getChildren().add(confirmBtn);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setSpacing(20);
        btnBox.setPadding(new Insets(20));

    }
    public void apply(){
        TableView.TableViewSelectionModel<Patient> tvsm = paTable.getSelectionModel();
        confirmBtn.setOnAction(e->{
            Patient patient = tvsm.getSelectedItem();
            String text1 = "病人";
            int hours = hourBox.getValue();
            int minutes = minuteBox.getValue();
            if(patient == null) {
            	Alert.showAlert("请选择一个病人！");
            	return;
            }
            if(hours ==0 && minutes == 0) {
            	Alert.showAlert("请选择时长！");
            	return;
            }
            Duration duration = Duration.ofMinutes(minutes);
            duration = duration.plus(Duration.ofHours(hours));
            

                Duration wait = service.apply(specialRoom,patient,duration);
                if(wait == null) {
                    text1 += patient.getName()+" ";
                    warnText.setText(text1+"申请失败");
                }
                else {
                    text1 += patient.getName();
                    //TODO: change the format
                    warnText.setText(text1+"申请成功！还需等待"+wait.toMinutes()+"分钟");
                }



        });

        Scene scene = new Scene(modalRoot);
        choosePatientModal.setScene(scene);
        choosePatientModal.setTitle("选择申请人");
        choosePatientModal.showAndWait();
    }
    
   

}
