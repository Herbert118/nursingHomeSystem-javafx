package com.neuedu.view.test;
import com.jfoenix.controls.JFXButton;
import com.neuedu.main.Router;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
public class TestViewController {
	
		private Router router;

	    @FXML
	    private ResourceBundle resources;

	    @FXML
	    private URL location;

	    @FXML
	    private JFXButton loginView;

	    @FXML
	    private JFXButton MainMenu;

	    @FXML
	    private JFXButton patientView;

	    @FXML
	    private JFXButton bedView;

	    
	    @FXML
	    private JFXButton SpecialView;

	    @FXML
	    private JFXButton QuestionView;

	    @FXML
	    private JFXButton templateView;

	    @FXML
	    private JFXButton BuildingView;

	    @FXML
	    void navToBuildingView(ActionEvent event) {
			System.out.println("click");
			router.navToBuidingView();


	    }

	    @FXML
	    void navToLoginView(ActionEvent event) {
	    	System.out.println("click");
	    	router.navToLoginView();
	    }

	    @FXML
	    void navToMainMenu(ActionEvent event) {

	    }

	    @FXML
	    void navToSpecialView(ActionEvent event) {

	    }

	    @FXML
	    void navToBedView(ActionEvent event) {

	    }

	    @FXML
	    void navToTemplateView(ActionEvent event) {

	    }

	    @FXML
	    void navToPatientView(ActionEvent event) {
	    	System.out.println("patient");
	    	router.navToPatientView();
	    }
	    
	    @FXML
	    void navToQuestionView(ActionEvent event) {
	    	
	    }

	    @FXML
	    void initialize() {
	        assert loginView != null : "fx:id=\"loginView\" was not injected: check your FXML file 'testMenu.fxml'.";
	        assert MainMenu != null : "fx:id=\"MainMenu\" was not injected: check your FXML file 'testMenu.fxml'.";
	        assert patientView != null : "fx:id=\"patientView\" was not injected: check your FXML file 'testMenu.fxml'.";
	        assert bedView != null : "fx:id=\"bedView\" was not injected: check your FXML file 'testMenu.fxml'.";
	        assert SpecialView != null : "fx:id=\"SpecialView\" was not injected: check your FXML file 'testMenu.fxml'.";
	        assert QuestionView != null : "fx:id=\"QuestionView\" was not injected: check your FXML file 'testMenu.fxml'.";
	        assert templateView != null : "fx:id=\"templateView\" was not injected: check your FXML file 'testMenu.fxml'.";
	        assert BuildingView != null : "fx:id=\"BuildingView\" was not injected: check your FXML file 'testMenu.fxml'.";

	        router = Router.getInstance();
	    }
	}


