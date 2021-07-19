package com.neuedu.view.patient;

import com.jfoenix.controls.JFXButton;
import com.neuedu.model.Patient;
import com.neuedu.model.Service;

import com.neuedu.view.component.Alert;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class patientViewController {
	@FXML
	private TableView<Patient> patientTable;
	@FXML
	private TableColumn<Patient, String> nameCol;
	@FXML
	private TableColumn<Patient, Integer> ageCol;
	@FXML
	private TableColumn<Patient, String> sexCol;
	@FXML
	private TableColumn<Patient, String> phoneNumberCol;
	@FXML
	private TableColumn<Patient, String> urgentContactCol;
	@FXML
	private TableColumn<Patient, String> urgentPhoneNumberCol;
	@FXML
	private JFXButton addPaBtn;

	@FXML
	private JFXButton modPaBtn;

	@FXML
	private JFXButton delPaBtn;

	@FXML
	private JFXButton evaPaBtn;
	@FXML
	private TextField searchFld;

	private Service service;
	ObservableList<Patient> patientList;
	TableView.TableViewSelectionModel<Patient> tsm;

	@FXML
	void initialize() {
		service = Service.getInstance();
		check();
		initTable();

	}

	private void check() {
		assert patientTable != null
				: "fx:id=\"patientTable\" was not injected: check your FXML file 'patientView.fxml'.";
		assert nameCol != null : "fx:id=\"nameCol\" was not injected: check your FXML file 'patientView.fxml'.";
		assert ageCol != null : "fx:id=\"ageCol\" was not injected: check your FXML file 'patientView.fxml'.";
		assert sexCol != null : "fx:id=\"sexCol\" was not injected: check your FXML file 'patientView.fxml'.";
		assert phoneNumberCol != null
				: "fx:id=\"phoneNumberCol\" was not injected: check your FXML file 'patientView.fxml'.";
		assert urgentContactCol != null
				: "fx:id=\"urgentContactCol\" was not injected: check your FXML file 'patientView.fxml'.";
		assert urgentPhoneNumberCol != null
				: "fx:id=\"urgentPhoneNumberCol\" was not injected: check your FXML file 'patientView.fxml'.";

	}

	private void initTable() {
		patientList = FXCollections.<Patient>observableArrayList();

		// patientList.add(new Patient("test","男",
		// LocalDate.of(2002,10,30),"test","test","test","test"));
		patientList = service.getPatientList();
		patientTable.setItems(patientList);
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
		sexCol.setCellValueFactory(new PropertyValueFactory<>("sex"));
		phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		urgentContactCol.setCellValueFactory(new PropertyValueFactory<>("urgentContact"));
		urgentContactCol.setCellFactory(col -> {
			TableCell<Patient, String> cell = new TableCell<Patient, String>() {
				@Override
				public void updateItem(String urgentContact, boolean isEmpty) {
					super.updateItem(urgentContact, isEmpty);
					this.setText(null);
					this.setGraphic(null);
					if (!isEmpty) {
						this.setText(urgentContact);
					}
				}
			};
			return cell;
		});
		urgentPhoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("urgentPhoneNumber"));

		searchFld.textProperty().addListener(this::searchPatient);

		tsm = patientTable.getSelectionModel();

	}

	@FXML
	void addPatient(ActionEvent event) {
		PatientModal modal = new PatientModal(service);
		modal.addPatient();
		patientList = service.getPatientList();
		patientTable.setItems(patientList);
	}

	@FXML
	void deletePatient(ActionEvent event) {

		String mes = "";
		for (Patient patient : tsm.getSelectedItems()) {
			if (service.deletePatient(patient)) {
				mes += patient.getName() + "已被删除";
				// TODO:add a tip here
			} else {
				mes += patient.getName() + "删除失败";
			}
		}
		Alert.showAlert(mes);
	}

	@FXML
	void evalutePatient(ActionEvent event) {

	}

	@FXML
	void modifyPatient(ActionEvent event) {
		ObservableList<Patient> selectedList = tsm.getSelectedItems();
		if (selectedList.isEmpty()) {
			Alert.showAlert("请选择一个病患");
		} else if (selectedList.size() > 1) {
			Alert.showAlert("仅能同时修改一个病患");
		} else {
			PatientModal updateModal = new PatientModal(service);
			updateModal.updatePatient(selectedList.get(0));
			patientList = service.getPatientList();
		}
	}

	void searchPatient(ObservableValue<? extends String> searchText, String oldVal, String newVal) {
		this.patientList = service.searchPatient(newVal);
		this.patientTable.setItems(patientList);

	}

}
