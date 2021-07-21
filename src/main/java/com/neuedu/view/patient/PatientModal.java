package com.neuedu.view.patient;

import java.time.LocalDate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.neuedu.model.Patient;
import com.neuedu.model.Service;
import com.neuedu.util.CheckUtil;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PatientModal {
    private Service service;
    private Stage patientModal;
    private VBox modalRoot;
    private GridPane fieldGridPane;
    private JFXTextField nameFld;
    private ToggleGroup sexGroup;
    private JFXRadioButton sexRadio1;
    private JFXRadioButton sexRadio2;
    private DatePicker birthDatePicker;
    private JFXTextField phoneFld;
    private JFXTextField urgentConFld;
    private JFXTextField urgentphoneFld;
    private JFXTextField IDNumberFld;
    private JFXButton confirmButton;
    private Text warnText;
    public PatientModal(Service service){
        this.service = service;
        init();
        lay();
    }
    private void init(){
    	patientModal = new Stage();
        modalRoot = new VBox();
        fieldGridPane = new GridPane();
        nameFld = new JFXTextField();
        sexRadio1 = new JFXRadioButton("男");
        sexRadio2 = new JFXRadioButton("女");
        sexGroup = new ToggleGroup();
        sexRadio1.setToggleGroup(sexGroup);
        sexRadio2.setToggleGroup(sexGroup);
        birthDatePicker = new DatePicker();
        urgentConFld = new JFXTextField();
        urgentphoneFld = new JFXTextField();
        phoneFld = new JFXTextField();
        IDNumberFld = new JFXTextField();
        confirmButton = new JFXButton("确认");
        warnText = new Text();
    }
    private void lay(){
        fieldGridPane.add(new Label("姓名"),0,0);
        fieldGridPane.add(new Label(("性别")),0,1);
        fieldGridPane.add(new Label("出生日期"),0,2);
        fieldGridPane.add(new Label("身份证号"),0,3);
        fieldGridPane.add(nameFld,1,0,2,1);
        fieldGridPane.add(sexRadio1,1,1);
        fieldGridPane.add(sexRadio2,2,1);
        fieldGridPane.add(birthDatePicker,1,2,2,1);
        fieldGridPane.addColumn(3,new Label("联系电话"),new Label("紧急联系人"),new Label("紧急联系人电话"));
        fieldGridPane.addColumn(4,phoneFld,urgentConFld,urgentphoneFld);
        fieldGridPane.add(IDNumberFld,1,3,3,1);

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

    public void addPatient(){
        confirmButton.setOnAction(e ->{
            String name = nameFld.getText();
            Toggle selected = sexGroup.getSelectedToggle();
            String sex = "";
            if(selected!= null) {
                 sex = ((Labeled) selected).getText();
            }
            else{

            }
            LocalDate birthDate = birthDatePicker.getValue();
            String phoneNumber = phoneFld.getText();
            String urgentContact = urgentConFld.getText();
            String urgentPhoneNumber = urgentphoneFld.getText();
            String IDNumber = IDNumberFld.getText();
            if(!check(name, sex,birthDate,phoneNumber,urgentContact,urgentPhoneNumber,IDNumber)){
                return;
            }
            else{
               if(service.addPatient(name,sex,birthDate,IDNumber,phoneNumber,urgentContact,urgentPhoneNumber)){
                   warnText.setText("添加成功!");
               }
               else{
                   warnText.setText("添加失败!");
                }
               //TODO: better add a check
            }
        });

        Scene scene = new Scene(modalRoot);
        scene.getStylesheets().add("modal.css");
        patientModal.setScene(scene);
        patientModal.setTitle("新增病患");
        patientModal.showAndWait();
    }
    
    public void updatePatient(Patient oldPatient) {
    	nameFld.setText(oldPatient.getName());
    	if(sexRadio1.getText().equals(oldPatient.getSex())) {
    		sexRadio1.setSelected(true);
    	}
    	else {
    		sexRadio2.setSelected(true);
    	}
    	birthDatePicker.setValue(oldPatient.getBirthDate());
    	phoneFld.setText(oldPatient.getPhoneNumber());
    	urgentConFld.setText(oldPatient.getUrgentContact());
    	urgentphoneFld.setText(oldPatient.getUrgentPhoneNumber());
    	IDNumberFld.setText(oldPatient.getIDNumber());
    	
        confirmButton.setOnAction(e ->{
            String name = nameFld.getText();
            Toggle selected = sexGroup.getSelectedToggle();
            String sex = "";
            if(selected!= null) {
                 sex = ((Labeled) selected).getText();
            }
            else{

            }
            LocalDate birthDate = birthDatePicker.getValue();
            String phoneNumber = phoneFld.getText();
            String urgentContact = urgentConFld.getText();
            String urgentPhoneNumber = urgentphoneFld.getText();
            String IDNumber = IDNumberFld.getText();
            if(!check(name, sex,birthDate,phoneNumber,urgentContact,urgentPhoneNumber,IDNumber)){
                return;
            }
            else{
            	try {
            		
            	
                if(service.updatePatient(oldPatient,name,sex,birthDate,IDNumber,phoneNumber,urgentContact,urgentPhoneNumber)){
                   warnText.setText("修改成功!");
                }
                else{
                    warnText.setText("修改失败!");
                }
            	}
            	catch(Exception ex) {
            		warnText.setText("修改失败!");
            		ex.printStackTrace();
            	}

            }
        });

        Scene scene = new Scene(modalRoot);
        scene.getStylesheets().add("modal.css");
        patientModal.setScene(scene);
        patientModal.setTitle("修改病患");
        //TODO:change here
        patientModal.showAndWait();
    }

    private boolean check(String name, String sex, LocalDate birthDate, String phoneNumber, String urgentContact, String urgentPhoneNumber,String IDNumber) {
        boolean result = true;
        String text = "";
        if (!CheckUtil.checkName(name)){
            text += "姓名 ";
            result = false;
        }
        if (!CheckUtil.checkSex(sex)){
            text += "性别 ";
            result = false;
        }
        //TODO:check if the birthDate is reasonable
        if(!CheckUtil.checkBirthDate(birthDate)){
            text+="时间 ";
            result = false;
        }
        if(!CheckUtil.checkIDNumber(IDNumber)){
            text += "身份证号 ";
            result = false;
        }
        if(!CheckUtil.checkPhoneNumber(phoneNumber)){
            text += "联系电话 " ;
            result = false;
        }
        if(!CheckUtil.checkName(urgentContact)){
            text += "紧急联系人 ";
            result = false;
        }
        if (!CheckUtil.checkPhoneNumber(urgentPhoneNumber)){
            text += "电话号码 ";
            result = false;
        }
        if(result == false){
            warnText.setText(text+"格式有误");
        }
        return result;

    }


}
