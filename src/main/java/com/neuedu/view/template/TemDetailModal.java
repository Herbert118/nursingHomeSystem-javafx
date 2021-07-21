package com.neuedu.view.template;

import com.jfoenix.controls.JFXButton;
import com.neuedu.model.Question;
import com.neuedu.model.Service;
import com.neuedu.model.Template;
import com.neuedu.view.component.Alert;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TemDetailModal {
	private Stage detailModal;
	private VBox modalRoot;
	private HBox btnBox;
	private Template template;
	private TableView<Question> quesTable;
	private TableColumn<Question,String> idCol;
	private TableColumn<Question,String> stemCol;
	private TableColumn<Question,String> formCol;
	private JFXButton addBtn;
	private JFXButton delBtn;
	private Service service;
	public TemDetailModal(Template template, Service service){
		this.template = template;
		this.service = service;
		init();
		lay();
	}
	private void init() {
		detailModal = new Stage();
		modalRoot = new VBox();
		btnBox = new HBox();
		quesTable = new TableView<Question>();
		idCol = new TableColumn<Question,String>("id");
		stemCol = new TableColumn<Question,String>("题干");
		formCol = new TableColumn<Question,String>("类型");
		addBtn = new JFXButton("新增问题");
		delBtn = new JFXButton("删除问题");

		quesTable.setItems(template.getQuestionList());
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		stemCol.setCellValueFactory(new PropertyValueFactory<>("stem"));
		formCol.setCellValueFactory(new PropertyValueFactory<>("form"));
		quesTable.getColumns().addAll(idCol,stemCol,formCol);
		TableView.TableViewSelectionModel tvsm = quesTable.getSelectionModel();

		addBtn.setOnAction(e->{
			ChooseQuesModal modal = new ChooseQuesModal(template,service);
			modal.addQuesToTem();
			//TODO: make it more reasonable;
			quesTable.setItems(template.getQuestionList());
		});

		delBtn.setOnAction(e->{
			ObservableList<Question> questions = tvsm.getSelectedItems();
			if(questions.isEmpty()){
				Alert.showAlert("请先选择一个问题");
			}
			for(Question question:questions){
				service.removeQuestionFromTem(template,question);
			}
		});
	}
	private void lay() {
		modalRoot.getChildren().add(quesTable);
		modalRoot.getChildren().add(btnBox);
		modalRoot.setPadding(new Insets(20));
		btnBox.getChildren().add(addBtn);
		btnBox.getChildren().add(delBtn);
		btnBox.setAlignment(Pos.CENTER);
		btnBox.setSpacing(20);
		btnBox.setPadding(new Insets(20));

	}

	public void showDetail(){
		Scene scene = new Scene(modalRoot);
		scene.getStylesheets().add("modal.css");
		detailModal.setScene(scene);
		detailModal.setTitle("模板详情");
		detailModal.showAndWait();
	}




}
