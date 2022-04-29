package com.neuedu.view.patient;

import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.neuedu.model.Patient;
import com.neuedu.model.Question;
import com.neuedu.model.Template;
import com.neuedu.model.User;
import com.neuedu.view.component.Alert;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EvaluateModal {
	private User user;
	private Patient patient;
	private Template template;
	private Stage evaluateModal;
	private VBox modalRoot;
	private VBox quesVBox;
	private JFXButton confirmBtn;
	private ScrollPane pane;
	ArrayList<ToggleGroup> tgroupList;
	private ObservableList<Question> quesList;
	private Text mesText;
	public EvaluateModal(User user, Patient patient,Template template ) {
		this.user = user;
		this.patient = patient;
		this.template = template;
	}
	public EvaluateModal(Template template) {
		this.template = template;
		init();
		lay();
	}
	
	public void init() {
		evaluateModal = new Stage();
		modalRoot = new VBox();
		quesList = template.getQuestionList();
		mesText = new Text();
		pane = new ScrollPane();
		quesVBox = new VBox();
		confirmBtn = new JFXButton("确认");
		tgroupList = new ArrayList<ToggleGroup>();
	}
	
	public void lay() {
		
		for(Question question: quesList) {
			GridPane quesGridPane = new GridPane();
			ToggleGroup tgroup = new ToggleGroup();
			
			tgroupList.add(tgroup);
			JFXRadioButton radioA = new JFXRadioButton(question.getChoiceA());
			JFXRadioButton radioB = new JFXRadioButton(question.getChoiceB());
			JFXRadioButton radioC = new JFXRadioButton(question.getChoiceC());
			tgroup.getToggles().addAll(radioA,radioB,radioC);
			quesGridPane.add(new Text(question.getStem()), 0, 0);
			quesGridPane.add(radioA, 0, 1);
			quesGridPane.add(radioB, 0, 2);
			quesGridPane.add(radioC, 0, 3);
			quesGridPane.setPadding(new Insets(50));
			quesGridPane.setVgap(15);
			quesVBox.getChildren().add(quesGridPane);
			
		}
		pane.setContent(quesVBox);
		pane.setPadding(new Insets(20));
		quesVBox.setMaxHeight(Double.MAX_VALUE);
		modalRoot.setAlignment(Pos.CENTER);
		modalRoot.getChildren().addAll(pane,confirmBtn,mesText);
		VBox.setMargin(mesText, new Insets(30));
		
		
		Scene scene = new Scene(modalRoot,300,300);
		scene.getStylesheets().add("modal.css");
		evaluateModal.setScene(scene);
	}
	
	public void evaluate() {
		
		confirmBtn.setOnAction(e->{
			int score = 0;
			for(ToggleGroup tgroup:tgroupList) {
				Toggle toggle = tgroup.getSelectedToggle();
				
				if(toggle == null) {
					Alert.showAlert("请先答完！");
				}
				else {
					int index = tgroup.getToggles().indexOf(toggle);
					switch(index) {
					case 0:
						score += 1;
						break;
					case 1:
						score += 3;
						break;
					case 2:
						score+= 5;
						break;
					default:
						throw new IllegalArgumentException("wrong argument");
					}
				}
				
			}
			mesText.setText("得分为"+score);
			
		});
		
		evaluateModal.setTitle("评估");
		evaluateModal.showAndWait();
	}
	


}
