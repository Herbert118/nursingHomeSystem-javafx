package com.neuedu.view.component;

import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConfirmAlert {
	private static boolean result;
	public static boolean getConfirm(String tip) {
		Stage alert = new Stage();
		VBox messageBox = new VBox();
		HBox btnBox = new HBox();
		Label mesLbl = new Label(tip);
		JFXButton confirmBtn = new JFXButton("确定");
		JFXButton cancelBtn = new JFXButton("取消");
		btnBox.getChildren().addAll(confirmBtn,cancelBtn);
		btnBox.setSpacing(20);
		confirmBtn.setOnAction(e -> {
			alert.close();
			result = true;
		});
		cancelBtn.setOnAction(e->{
			alert.close();
			result = false;
		});
		messageBox.getChildren().add(mesLbl);
		messageBox.getChildren().add(btnBox);
		messageBox.setSpacing(20);
		messageBox.setPadding(new Insets(20));
		messageBox.setAlignment(Pos.CENTER);
		Scene scene = new Scene(messageBox);
		scene.getStylesheets().add("modal.css");
		alert.setScene(scene);
		alert.showAndWait();
		alert.setOnCloseRequest(e->{
			result = false;
		});
		return result;
	}
}
