package com.neuedu.view.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class LoginView extends StackPane{
	protected JFXTextField idFld;
	protected JFXPasswordField passwordFld;
	protected JFXButton loginBtn;
	protected GridPane loginPane;
 	public LoginView() {
 		super();
 		initialize();
 		
 	}

	private void initialize() {
		
		idFld = new JFXTextField();
		passwordFld = new JFXPasswordField();
		loginBtn = new JFXButton("登录");
		loginPane = new GridPane();
		Label title = new Label("东软颐养中心");
		title.setStyle("-fx-text-fill:#4285f5;"
				+"-fx-font-size:18");
		
		//this.getStyleClass().add("loginView");
		Label passLb = new Label("密码");
		Label idLb = new Label("用户名");
			
		loginPane.setAlignment(Pos.CENTER);
		loginPane.setHgap(25);
		loginPane.setVgap(25);
		loginPane.setPadding(new Insets(25));
		loginPane.add(idLb, 0, 0);
		loginPane.add(passLb, 0, 1);
		loginPane.add(idFld, 1, 0);
		loginPane.add(passwordFld, 1, 1);
		
		Pane rec = new Pane();
		rec.setPrefSize(100, 100);
		rec.setMaxSize(360, 360);
		rec.setStyle("-fx-background-color:#b0e2ff;"
				+"-fx-background-radius:5");
		idFld.setStyle("-fx-background-color:#ffffff");
		passwordFld.setStyle("-fx-background-color:#ffffff");
		passLb.setStyle("-fx-text-fill:#4285f5;"+
		"-fx-font-size:14");
		idLb.setStyle("-fx-text-fill:#4285f5;"
				+"-fx-font-size:14");
		loginBtn.setStyle("-fx-background-color:#4285f5;"+
		"-fx-text-fill:#ffffff;"+
		"-fx-font-size:14");
		loginBtn.setPadding(new Insets(7,30,7,30));
		
	
		
		
		
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
