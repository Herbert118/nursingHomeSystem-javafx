package com.neuedu.main;

import com.neuedu.model.Database;
import com.neuedu.model.Service;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author 刘海波
 * @description
 * 主程序，初始化路由，展示stage，并在最后保存文件
 */
public class MainApp extends Application{
	private Stage primaryStage;
	
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		Router router = Router.getInstance(this);
		router.navToLoginView();
		
		Service service = Service.getInstance();
		
		primaryStage.setResizable(false);
		primaryStage.setTitle("东软颐养中心");
		primaryStage.setOnCloseRequest(e ->{
			service.save();
		});
	}
	
	public static void main(String [] args) {
		Application.launch(args);
	}
	
	public void setStageScene(Scene newScene) {
		this.primaryStage.setScene(newScene);
		primaryStage.show();
	}
}
