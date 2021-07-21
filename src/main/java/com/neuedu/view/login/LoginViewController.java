package com.neuedu.view.login;

import com.neuedu.main.Router;
import com.neuedu.model.Service;
import com.neuedu.model.User;
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
			String id = view.idFld.getText();
			String password = view.passwordFld.getText();
			User user;
			if ((user = service.checkLogin(id, password))!=null) {
				router.initToMenu(user);
			}
			else {
				Alert.showAlert("登录失败");
			}
			
		});
	}
	
	
}
