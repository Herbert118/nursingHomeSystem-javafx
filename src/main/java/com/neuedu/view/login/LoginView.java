package com.neuedu.view.login;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class LoginView extends GridPane{
	protected TextField nameFld;
	protected PasswordField passwordFld;
	protected Button loginBtn;
	
 	public LoginView() {
 		super();
 		initialize();
 		this.setMaxSize(1000, 1000);
 		
 		this.setGridLinesVisible(true);
 		this.add(new Label("name"), 0, 1,4,1);
 		this.add(new Label("password"), 0, 2);
 		this.add(nameFld, 4, 1);
 		this.add(passwordFld, 4, 2);
 		this.add(loginBtn, 4, 3);
 	}

	private void initialize() {
		nameFld = new TextField();
		passwordFld = new PasswordField();
		loginBtn = new Button("login");
		
	}
	
}
