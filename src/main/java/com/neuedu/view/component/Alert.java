package com.neuedu.view.component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Alert {
	public static void showAlert(String message) {
		Stage alert = new Stage();
		
		VBox messageBox = new VBox();
		Label mesLbl = new Label(message);
		Button closeBtn = new Button("关闭");
		closeBtn.setOnAction(e -> {
			alert.close();
		});
		messageBox.getChildren().add(mesLbl);
		messageBox.getChildren().add(closeBtn);
		messageBox.setSpacing(20);
		messageBox.setPadding(new Insets(20));
		messageBox.setAlignment(Pos.CENTER);
		Scene scene = new Scene(messageBox);
		alert.setScene(scene);
		alert.showAndWait();
		
	}

}
