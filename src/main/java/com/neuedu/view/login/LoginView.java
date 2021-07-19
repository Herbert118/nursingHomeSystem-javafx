package com.neuedu.view.login;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LoginView extends StackPane{
	protected TextField nameFld;
	protected PasswordField passwordFld;
	protected Button loginBtn;
	protected GridPane loginPane;
 	public LoginView() {
 		super();
 		initialize();
 		
 	}

	private void initialize() {
		
		nameFld = new TextField();
		passwordFld = new PasswordField();
		loginBtn = new Button("login");
		loginPane = new GridPane();
		Label title = new Label("登录");
		//this.getStyleClass().add("loginView");
		
		
		loginPane.setAlignment(Pos.CENTER);
		loginPane.setHgap(25);
		loginPane.setVgap(25);
		loginPane.setPadding(new Insets(25));
		loginPane.add(new Label("name"), 0, 0);
		loginPane.add(new Label("password"), 0, 1);
		loginPane.add(nameFld, 1, 0);
		loginPane.add(passwordFld, 1, 1);
		
		Rectangle rec = new Rectangle(350,350);
		rec.setFill(Color.rgb(191, 210, 255));
		
		
		this.getChildren().addAll(rec,loginPane,loginBtn,title);
		this.setAlignment(Pos.CENTER);
		StackPane.setAlignment(rec, Pos.CENTER);
		StackPane.setAlignment(loginPane,Pos.CENTER);
		StackPane.setAlignment(loginBtn,Pos.CENTER);
		StackPane.setAlignment(title, Pos.CENTER);
		StackPane.setMargin(loginBtn, new Insets(150,0,0,0));
		StackPane.setMargin(title, new Insets(-150,0,0,0));
		
	
		
		
	}
	
}
