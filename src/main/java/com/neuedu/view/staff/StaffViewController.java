package com.neuedu.view.staff;

import java.net.URL;
import java.util.ResourceBundle;

import com.neuedu.model.Service;
import com.neuedu.model.User;

import com.neuedu.view.component.Alert;
import com.neuedu.view.component.ConfirmAlert;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;

public class StaffViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label title;

    @FXML
    private TextField searchFld;
    @FXML
    private TableView<User> staffTable;

    @FXML
    private TableColumn<User, String> idCol;

    @FXML
    private TableColumn<User, String> nameCol;

    @FXML
    private TableColumn<User, String> positionCol;
    @FXML 
    private TableColumn<User,String> birthDateCol;

    @FXML
    private TableColumn<User, String> specialityCol;

    @FXML
    private TableColumn<User, String> IDNumberCol;
    private String staffLoc;
    private Service service;
    TableSelectionModel<User> tsm;
    @FXML
    void initialize() {
    	service = Service.getInstance();
    	//service.addUser("医生","good", "abcabc1234","good","362226200210304518","内科",LocalDate.of(2002, 10, 30),"18279033381");
    	initTable();
    }
    private void initTable() {
    	while(staffLoc==null) {
    		try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
    		System.out.println("null");
    	}
			staffTable.setItems(service.getStaffList(staffLoc));
			idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
			nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
			birthDateCol.setCellValueFactory(cellData->{
				return new SimpleStringProperty (cellData.getValue().getBirthDate().toString());
			});
			positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
			specialityCol.setCellValueFactory(new PropertyValueFactory<>("speciality"));
			IDNumberCol.setCellValueFactory(new PropertyValueFactory<>("IDNumber"));
			tsm = staffTable.getSelectionModel();
			title.setText(staffLoc+"管理");

			searchFld.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    if(t1 == null||"".equals(t1)){
                    	
                        staffTable.setItems(service.getStaffList(staffLoc));
                    }
                    else {
                        staffTable.setItems(service.searchUser(service.getStaffList(staffLoc),t1));
                    }
                }
            });
	}

    
    public void setLoc(String staffLoc) {
    	this.staffLoc = staffLoc;
    	
    }
    @FXML
    void addStaff(ActionEvent event) {
        StaffModal modal = new StaffModal(service);
        modal.addUser();
        staffTable.setItems(service.getStaffList(staffLoc));
    }

    @FXML
    void deleteStaff(ActionEvent event) {
        ObservableList<User> users =  tsm.getSelectedItems();
        if(users.isEmpty()){
            Alert.showAlert("请先选择一个职工");
            return;
        }
        else {
            if (!ConfirmAlert.getConfirm("您确定要删除吗?")) {
                return;
            }
            String text = "";
            for (User user : users) {

                if (service.deleteUser(user)) {
                    text+=(user.getName() + "删除成功!");
                } else {
                    text+=(user.getName() + "删除失败");
                }

            }
            Alert.showAlert(text);
            staffTable.setItems(service.getStaffList(staffLoc));
        }
    }

    @FXML
    void searchStaff(InputMethodEvent event) {
        //useless
    }

    @FXML
    void updateStaff(ActionEvent event) {
        User user =  tsm.getSelectedItem();
        if(user == null){
            Alert.showAlert("请先选择一个职工");
            return;
        }
        StaffModal modal = new StaffModal(service);
        modal.updateUser(user);
        staffTable.setItems(service.getStaffList(staffLoc));
    }
}
