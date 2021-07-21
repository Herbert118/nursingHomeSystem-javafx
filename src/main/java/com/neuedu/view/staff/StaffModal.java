package com.neuedu.view.staff;

import java.time.LocalDate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.neuedu.model.Service;
import com.neuedu.model.User;
import com.neuedu.util.CheckUtil;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StaffModal {
    private Service service;
    private Stage staffModal;
    private VBox modalRoot;
    private GridPane fieldGridPane;
    private JFXTextField nameFld;
    private JFXTextField idFld;
    private JFXPasswordField passwordFld;
    private JFXPasswordField checkPasswordFld;
    private JFXComboBox<String> positionBox;
    private DatePicker birthDatePicker;
    private JFXTextField phoneFld;
    private JFXTextField specialtyFld;
    private JFXTextField IDNumberFld;
    private JFXButton confirmButton;
    private Text warnText;
    public StaffModal(Service service){
        this.service = service;
        init();
        lay();
    }
    private void init(){
    	staffModal = new Stage();
        modalRoot = new VBox();
        fieldGridPane = new GridPane();
        nameFld = new JFXTextField();
        passwordFld = new JFXPasswordField();
        checkPasswordFld = new JFXPasswordField();
        birthDatePicker = new DatePicker();
        specialtyFld = new JFXTextField();
        positionBox = new JFXComboBox<>();
        idFld = new JFXTextField();
        phoneFld = new JFXTextField();
        IDNumberFld = new JFXTextField();
        confirmButton = new JFXButton("确认");
        warnText = new Text();

        positionBox.getItems().addAll("医生","护士","护工");
    }
    private void lay(){
        fieldGridPane.add(new Label("登录名"),0,0);
        fieldGridPane.add(new Label(("密码")),0,1);
        fieldGridPane.add(new Label("确认密码"),0,2);
        fieldGridPane.add(new Label("出生日期"),0,3);
        fieldGridPane.add(new Label("身份证号"),0,4);
        fieldGridPane.add(idFld,1,0);
        fieldGridPane.add(passwordFld,1,1);
        fieldGridPane.add(checkPasswordFld,1,2);
        fieldGridPane.add(birthDatePicker,1,3);
        fieldGridPane.addColumn(3,new Label("姓名"),new Label("联系电话"),new Label("专长"),new Label("职位"));
        fieldGridPane.addColumn(4,nameFld,phoneFld,specialtyFld,positionBox);
        fieldGridPane.add(IDNumberFld,1,4,3,1);

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

    public void addUser(){
        confirmButton.setOnAction(e ->{
            String name = nameFld.getText();
            LocalDate birthDate = birthDatePicker.getValue();
            String phoneNumber = phoneFld.getText();
            String specialty = specialtyFld.getText();
            String id = idFld.getText();
            String IDNumber = IDNumberFld.getText();
            String password = passwordFld.getText();
            String position = positionBox.getValue();
            String checkPassword = checkPasswordFld.getText();

            if(!check(name, id,birthDate,phoneNumber,password,specialty,IDNumber,position)){
                return;
            }
            if(!password.equals(checkPassword)){
                warnText.setText("密码不一致！");
                return;
            }

            else{
               if(service.addUser(position,name, password,id,IDNumber,specialty,birthDate,phoneNumber)){
                   warnText.setText("添加成功!");
               }
               else{
                   warnText.setText("添加失败!");
                }
               //TODO: better add a check
            }
        });

        Scene scene = new Scene(modalRoot);
        staffModal.setScene(scene);
        scene.getStylesheets().add("modal.css");
        staffModal.setTitle("新增职工");
        staffModal.showAndWait();
    }
    
    public void updateUser(User oldUser) {
        nameFld.setText(oldUser.getName());
        birthDatePicker.setValue(oldUser.getBirthDate());
        phoneFld.setText(oldUser.getPhoneNumber());
        specialtyFld.setText(oldUser.getSpeciality());
        idFld.setText(oldUser.getId());
        IDNumberFld.setText(oldUser.getIDNumber());
        passwordFld.setText(oldUser.getPassword());
        positionBox.setValue(oldUser.getPosition());

        confirmButton.setOnAction(e ->{
            String name = nameFld.getText();
            LocalDate birthDate = birthDatePicker.getValue();
            String phoneNumber = phoneFld.getText();
            String specialty = specialtyFld.getText();
            String id = idFld.getText();
            String IDNumber = IDNumberFld.getText();
            String password = passwordFld.getText();
            String position = positionBox.getValue();
            String checkPassword = checkPasswordFld.getText();

            if(!check(name, id,birthDate,phoneNumber,password,specialty,IDNumber,position)){
                return;
            }
            if(!password.equals(checkPassword)){
                warnText.setText("密码不一致！");
                return;
            }

            else{
                if(service.updateUser(oldUser,position,name, password,id,IDNumber,specialty,birthDate,phoneNumber)){
                    warnText.setText("修改成功!");
                }
                else{
                    warnText.setText("修改失败!");
                }
                //TODO: better add a check
            }
        });

        Scene scene = new Scene(modalRoot);
        staffModal.setScene(scene);
        scene.getStylesheets().add("modal.css");
        staffModal.setTitle("修改职工");
        staffModal.showAndWait();    
    }

    private boolean check(String name, String id, LocalDate birthDate, String phoneNumber, String password, String speciality, String IDNumber, String position) {
        boolean result = true;
        String text = "";
        if (!CheckUtil.checkName(name)){
            text += "姓名 ";
            result = false;
        }
        if (!CheckUtil.checkNotBlank(id)){
            text += "登录名 ";
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
        if(!CheckUtil.checkName(speciality)){
            text += "专长 ";
            result = false;
        }
        if (!CheckUtil.checkPassword(password)){
            text += "密码 ";
            result = false;
        }
        if (!CheckUtil.checkPosition(position)){
            text += "职位 ";
            result = false;
        }
        if(result == false){
            warnText.setText(text+"格式有误");
        }
        return result;

    }


}
