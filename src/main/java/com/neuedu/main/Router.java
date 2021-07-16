package com.neuedu.main;

import java.io.IOException;
import java.net.URL;

import com.neuedu.view.login.LoginView;
import com.neuedu.view.login.LoginViewController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Router {
	private static Router router;
	private static MainApp app;
	private BorderPane root;
	private Router(MainApp app) {
		this.app = app;
	}
	protected static Router getInstance(MainApp app) {
		if(router == null) {
			router = new Router(app);
		}
		return router;
	}
	
	public static Router getInstance() {
		if(router == null||app == null) {
			throw new IllegalArgumentException("router is not initialized!");
		}
		return router;
	}
	
	
	
	public void initToTestRoot() {
		URL url = this.getClass().getResource("../view/test/testMenu.fxml");
		System.out.println(url);
		try {
			this.root = FXMLLoader.load(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		root.setRight(null);
		root.setBottom(null);
		Scene scene = new Scene(root);
		app.setStageScene(scene);
		
		
	}
	public void navToLogin() {
		LoginView view = new LoginView();
		LoginViewController loginController = new LoginViewController(view);
		root.setCenter(view);
		
		//Scene scene = new Scene(root);
	 	//app.setStageScene(scene);
	}
	
}
