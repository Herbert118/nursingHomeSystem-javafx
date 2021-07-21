package com.neuedu.main;

import java.io.IOException;
import java.net.URL;

import com.neuedu.model.User;
import com.neuedu.view.login.LoginView;
import com.neuedu.view.login.LoginViewController;
import com.neuedu.view.mainFrame.MainFrameController;
import com.neuedu.view.staff.StaffViewController;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;

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
		double width = (int) Screen.getPrimary().getBounds().getWidth();
        double height = (int) Screen.getPrimary().getBounds().getHeight();
		Scene scene = new Scene(root,width*9/10,height*9/10);
		scene.getStylesheets().add("testView.css");
		app.setStageScene(scene);
	}

	public void initToMenu(User user) {
		FXMLLoader loader = new FXMLLoader();
		URL url = this.getClass().getResource("../view/mainFrame/mainFrame.fxml");
		loader.setLocation(url);
		MainFrameController controller = null;
		try {
			this.root = loader.load();
			 controller = ((MainFrameController)loader.getController());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		root.setRight(null);
		root.setBottom(null);
		double width = (int) Screen.getPrimary().getBounds().getWidth();
		double height = (int) Screen.getPrimary().getBounds().getHeight();
		Scene scene = new Scene(root,width*9/10,height*9/10);
		scene.getStylesheets().add("styleSheet.css");
		app.setStageScene(scene);
		//TODO:fix this damn bug;
		
		if(controller!=null) {
			controller.setUser(user);
		}
	}
	public void navToLoginView() {
		LoginView view = new LoginView();
		LoginViewController loginController = new LoginViewController(view);
		view.setPrefHeight(200);
		view.setPrefWidth(200);
		StackPane pane = new StackPane();
		pane.getChildren().add(view);

		view.setAlignment(Pos.CENTER);
		
		double width = (int) Screen.getPrimary().getBounds().getWidth();
        double height = (int) Screen.getPrimary().getBounds().getHeight();
		Scene scene = new Scene(pane,width*9/10,height*9/10);
	 	app.setStageScene(scene);
	}
	

    public void navToBuidingView() {
		URL url = this.getClass().getResource(("../view/site/siteView.fxml"));
		try{
			Parent buildingView = FXMLLoader.load(url);
			root.setCenter(buildingView);
		}
		catch(IOException e){
			e.printStackTrace();
		}
    }
	public void navToPatientView() {
		URL url = this.getClass().getResource("../view/patient/patientView.fxml");
		try {
			Parent PatientView = FXMLLoader.load(url);
			root.setCenter(PatientView);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void navToBedView() {
		URL url = this.getClass().getResource("../view/bed/bedView.fxml");
		try {
			Parent PatientView = FXMLLoader.load(url);
			root.setCenter(PatientView);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void navToSpecialView() {
		URL url = this.getClass().getResource("../view/special/specialView.fxml");
		try {
			Parent PatientView = FXMLLoader.load(url);
			root.setCenter(PatientView);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void navToQuestionView() {
		URL url = this.getClass().getResource("../view/question/questionView.fxml");
		try {
			Parent PatientView = FXMLLoader.load(url);
			root.setCenter(PatientView);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void navToTemplateView() {
		URL url = this.getClass().getResource("../view/template/templateView.fxml");
		try {
			Parent PatientView = FXMLLoader.load(url);
			root.setCenter(PatientView);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void navToStaffView(String position) {
		FXMLLoader loader = new FXMLLoader();
		
		URL url = this.getClass().getResource("../view/staff/staffView.fxml");
		loader.setLocation(url);
		StaffViewController controller = new StaffViewController();
		controller.setLoc(position);
		try {
			
			loader.setController(controller);
			Parent parentView = loader.load();
			root.setCenter(parentView);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	


		
	}


}
