package com.neuedu.view.special;

import com.neuedu.model.Service;
import com.neuedu.model.SpecialRoom;
import com.neuedu.view.component.Alert;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;

public class SpecialViewController {

    @FXML
    private TextField searchFld;

    @FXML
    private TableView<SpecialRoom> specialTable;
    @FXML
    private TableColumn<SpecialRoom,String > nameCol;

    @FXML
    private TableColumn<SpecialRoom, String> localCol;
    @FXML
    private TableColumn<SpecialRoom, String> containNumCol;

    @FXML
    private TableColumn<SpecialRoom, String> descCol;

    private Service service;
    private TableSelectionModel<SpecialRoom> tsm;
    @FXML
    void initialize() {
    	this.service = Service.getInstance();
    	initTable();
    }
    
    private void initTable() {
		specialTable.setItems(service.getSpecialRoomList());
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		localCol.setCellValueFactory(new PropertyValueFactory<>("location"));
		containNumCol.setCellValueFactory(cellData->{
		    //TODO: learn this!
			return new SimpleStringProperty(String.valueOf(cellData.getValue().getContainNum()));
		});
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		tsm = specialTable.getSelectionModel();
		
		
		searchFld.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(t1 ==null){
                    specialTable.setItems(service.getSpecialRoomList());
                }
                else{
                    specialTable.setItems(service.searchSpecialRoom(t1));
                }
            }
        });
	}

	@FXML
    void applyForSpec(ActionEvent event) {
		SpecialRoom specialRoom = tsm.getSelectedItem();
		if(specialRoom == null) {
			Alert.showAlert("请先选择一间房间！");
			return;
		}
		ApplyPaModal modal = new ApplyPaModal(specialRoom, service);
		modal.apply();
		
    }

    @FXML
    void searchSpecialRoom(InputMethodEvent event) {
        
    }

}
