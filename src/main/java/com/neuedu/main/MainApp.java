package com.neuedu.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application{
	private Stage primaryStage;
	
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		Router router = Router.getInstance(this);
		router.initToTestRoot();
		primaryStage.show();
	}
	
	public static void main(String [] args) {
		Application.launch(args);
	}
	
	public void setStageScene(Scene newScene) {
		this.primaryStage.setScene(newScene);
	}
}
