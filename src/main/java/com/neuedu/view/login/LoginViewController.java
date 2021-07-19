package com.neuedu.view.login;

import com.neuedu.main.Router;
import com.neuedu.model.Service;
import com.neuedu.view.component.Alert;



public class LoginViewController {
	private LoginView view;
	private Service service;
	private Router router;
	public LoginViewController(LoginView view){
		this.view = view;
		this.service = Service.getInstance();
		this.router = Router.getInstance();
		attachEvents();
	}
	
	
	private void attachEvents() {
		view.loginBtn.setOnAction(e -> {
			String name = view.nameFld.getText();
			String password = view.passwordFld.getText();
			
			if (service.checkLogin(name, password)) {
				Alert.showAlert("登录成功!");
			}
			else {
				view.nameFld.setText("登录失败!");
			}
			
		});
	}
	
	
}
