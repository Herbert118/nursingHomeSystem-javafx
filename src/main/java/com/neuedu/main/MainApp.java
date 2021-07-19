package com.neuedu.main;

import com.neuedu.model.Database;
import com.neuedu.model.Service;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application{
	private Stage primaryStage;
	
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		Router router = Router.getInstance(this);
		router.initToTestRoot();
		Service service = Service.getInstance();
		primaryStage.show();
		primaryStage.setResizable(false);
		primaryStage.setOnCloseRequest(e ->{
			service.save();
		});
	}
	
	public static void main(String [] args) {
		Application.launch(args);
	}
	
	public void setStageScene(Scene newScene) {
		this.primaryStage.setScene(newScene);
	}
}
