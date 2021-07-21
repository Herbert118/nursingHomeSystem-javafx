package com.neuedu.view.bed;

import java.time.LocalDate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.neuedu.main.Router;
import com.neuedu.model.Bed;
import com.neuedu.model.Building;
import com.neuedu.model.Floor;
import com.neuedu.model.Patient;
import com.neuedu.model.Room;
import com.neuedu.model.Service;
import com.neuedu.model.Ward;
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
import javafx.util.StringConverter;

public class bedViewController {

	@FXML
	private TextField searchFld;

	@FXML
	private JFXButton getBedBtn;

	@FXML
	private JFXComboBox<Ward> wardBox;

	@FXML
	private JFXComboBox<Floor> floorBox;

	@FXML
	private JFXComboBox<Building> buildingBox;

	@FXML
	private TableView<Bed> bedTable;
	@FXML
	private TableColumn<Bed, String> idCol;

	@FXML
	private TableColumn<Bed, String> locationCol;

	@FXML
	private TableColumn<Bed, LocalDate> startDateCol;

	@FXML
	private TableColumn<Bed, LocalDate> endDateCol;

	@FXML
	private TableColumn<Bed, String> statusCol;

	@FXML
	private TableColumn<Bed, String> nameCol;

	@FXML
	private TableColumn<Bed, String> sexCol;

	@FXML
	private TableColumn<Bed, String> ageCol;

	@FXML
	private JFXButton moveInBtn;

	@FXML
	private JFXButton moveOutBtn;

	private Service service;
	private TableSelectionModel<Bed> tsm;

	@FXML
	void initialize() {
		this.service = Service.getInstance();
		initTable();
		initsearchBox();
	}

	private void initsearchBox() {
		buildingBox.setConverter(new StringConverter<Building>() {
			@Override
			public String toString(Building building) {
				if (building == null) {
					return "";
				}
				return building.getName();
			}

			@Override
			public Building fromString(String s) {
				return null;
			}
		});

		floorBox.setConverter(new StringConverter<Floor>() {
			@Override
			public String toString(Floor floor) {
				if (floor != null) {
					return floor.getName();
				} else {
					return "";
				}

			}

			@Override
			public Floor fromString(String s) {
				return null;
			}
		});

		wardBox.setConverter(new StringConverter<Ward>() {
			@Override
			public String toString(Ward ward) {
				if (ward != null) {
					return ward.getName();
				} else {
					return "";
				}

			}

			@Override
			public Ward fromString(String s) {
				return null;
			}
		});

		buildingBox.setItems(service.getBuildingList());

		buildingBox.valueProperty().addListener(new ChangeListener<Building>() {
			@Override
			public void changed(ObservableValue<? extends Building> observableValue, Building building, Building t1) {
				if (t1 != null) {
					floorBox.setItems(t1.oFloorList());
				}
			}
		});
		floorBox.valueProperty().addListener(new ChangeListener<Floor>() {
			@Override
			public void changed(ObservableValue<? extends Floor> observableValue, Floor floor, Floor t1) {
				for (Room room : t1.oRoomList()) {
					if (room.getType().equals("ward")) {
						wardBox.getItems().add((Ward) room);
					}
				}
			}
		});

	}

	private void initTable() {
		bedTable.setItems(service.getAllBedList());
		idCol.setCellValueFactory(new PropertyValueFactory<>("siteId"));
		// TODO:change name
		locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
		startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
		endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
		statusCol.setCellValueFactory(cellData -> {
			return new SimpleStringProperty(cellData.getValue().getPatient() == null ? "未入住" : "已入住");
		});
		nameCol.setCellValueFactory(cellData -> {
			Patient patient = cellData.getValue().getPatient();
			if (patient == null) {
				return null;
			} else {
				return patient.nameProperty();
			}
		});
		ageCol.setCellValueFactory(cellData -> {
			Patient patient = cellData.getValue().getPatient();
			if (patient == null) {
				return null;
			} else {
				return new SimpleStringProperty(String.valueOf(patient.getAge()));
			}
		});
		sexCol.setCellValueFactory(cellData -> {
			Patient patient = cellData.getValue().getPatient();
			if (patient == null) {
				return null;
			} else {
				return patient.sexProperty();
			}
		});

		tsm = bedTable.getSelectionModel();

	}

	@FXML
	void moveIn(ActionEvent event) {
		Bed bed = tsm.getSelectedItem();
		if (bed == null) {
			Alert.showAlert("请先选择一个病床");
			return;
		}
		ChoosePatModal modal = new ChoosePatModal(bed, service);
		modal.moveIn();
		// TODO: solve the matter of change
		// bedTable.setItems(service.getAllBedList());
		Router.getInstance().navToBedView();
	}

	@FXML
	void moveOut(ActionEvent event) {
		Bed bed = tsm.getSelectedItem();
		if (bed == null) {
			Alert.showAlert("请先选择一个病床");
			return;
		}
		if (bed.getPatient() == null) {
			Alert.showAlert("该病床未入住！");
			return;
		}
		boolean success = service.moveOut(bed);
		if(success) {
			Alert.showAlert("操作成功!");
			
		}
		else {
			Alert.showAlert("操作失败!");
		}
	}

	@FXML
	void searchBed(ActionEvent event) {
		bedTable.getItems().clear();
		if (wardBox.getValue() != null) {
			this.bedTable.setItems(wardBox.getValue().oBedList());
		} else if (floorBox.getValue() != null) {
			for (Room room : floorBox.getValue().oRoomList()) {
				if (room.getType().equals("ward")) {
					bedTable.getItems().clear();;
					bedTable.getItems().addAll(((Ward) room).oBedList());
				}
			}
		} else if (buildingBox.getValue() != null) {
			for (Floor floor : buildingBox.getValue().oFloorList()) {
				for (Room room : floor.oRoomList()) {
					if (room.getType().equals("ward")) {
						bedTable.getItems().clear();;
						bedTable.getItems().addAll(((Ward) room).oBedList());
					}
				}
			}

		} else {
			bedTable.setItems(service.getAllBedList());
		}
	}
}
