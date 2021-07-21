package com.neuedu.view.mainFrame;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeCell;
import com.jfoenix.controls.JFXTreeView;

import com.neuedu.main.Router;
import com.neuedu.model.Service;
import com.neuedu.model.User;
import com.neuedu.view.component.ConfirmAlert;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class MainFrameController {
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label nameLbl;

    @FXML
    private JFXButton logOutBtn;

    @FXML
    private JFXTreeView<String> menuTreeView;

    @FXML
    void logOut(ActionEvent event) {
    	if(ConfirmAlert.getConfirm("您确定吗?"))
    	router.navToLoginView();
    }
    private Router router;
    private Service service;
    private User user;
    @FXML
    void initialize() {
        router = Router.getInstance();
        service = Service.getInstance();
        check();
        initMenuTree();
        
        
    }
    public void setUser(User user){
        this.user =user;
        this.nameLbl.setText(user.getName());;
    }
    private void check() {
    	assert nameLbl != null : "fx:id=\"nameLb\" was not injected: check your FXML file 'mainFrame.fxml'.";
        assert logOutBtn != null : "fx:id=\"logOutBtn\" was not injected: check your FXML file 'mainFrame.fxml'.";
        assert menuTreeView != null : "fx:id=\"menuTreeView\" was not injected: check your FXML file 'mainFrame.fxml'.";

    }
    
    private void initMenuTree() {
    	TreeItem<String> rootNode = new TreeItem<String>();
    	
    	TreeItem<String> userManNode = new TreeItem<String>("用户管理");
    	TreeItem<String> evaluManNode = new TreeItem<String>("评估管理");
    	TreeItem<String> buildManNode = new TreeItem<String>("楼宇管理");

    	TreeItem<String> staffManNode = new TreeItem<String>("职工管理");
    	
    	rootNode.getChildren().addAll(userManNode,evaluManNode,buildManNode,staffManNode);

    	TreeItem<String> patientManNode = new TreeItem<>("病患管理");
    	TreeItem<String> bedManNode = new TreeItem<>("床位管理");
    	TreeItem<String> specialNode = new TreeItem<>("特殊房间管理");
    	
    	userManNode.getChildren().addAll(patientManNode,bedManNode,specialNode);

    	TreeItem<String> quesManNode = new TreeItem<>("问题管理");
    	TreeItem<String> temManNode = new TreeItem<>("模板管理");
    	evaluManNode.getChildren().addAll(quesManNode,temManNode);

    	TreeItem<String> siteManNode = new TreeItem<>("楼宇管理");
    	buildManNode.getChildren().addAll(siteManNode);

    	TreeItem<String> docManNode = new TreeItem<>("医生管理");
    	TreeItem<String> nurseManNode = new TreeItem<>("护士管理");
    	TreeItem<String> nurWorkerManNode = new TreeItem<>("护工管理");

    	staffManNode.getChildren().addAll(docManNode,nurseManNode,nurWorkerManNode);

    	menuTreeView.setRoot(rootNode);
    	menuTreeView.setShowRoot(false);
    	menuTreeView.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleMouseClicked);
    }
    
    private void handleMouseClicked(MouseEvent event) {
        Node node = event.getPickResult().getIntersectedNode();
        // Accept clicks only on node cells, and not on empty spaces of the TreeView
        if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
        	if(menuTreeView.getSelectionModel().getSelectedItem()!=null) {
            String name = (String) ((TreeItem) menuTreeView.getSelectionModel().getSelectedItem()).getValue();
            switch(name) {
            case "病患管理":
            	router.navToPatientView();
            	break;
            case "床位管理":
            	router.navToBedView();
            	break;
            case "特殊房间管理":
            	router.navToSpecialView();
            	break;
            case "问题管理":
            	router.navToQuestionView();
            	break;
            case "模板管理":
            	router.navToTemplateView();
            	break;
            case "楼宇管理":
            	router.navToBuidingView();
            	break;
            case "医生管理":
            	router.navToStaffView("医生");
            	break;
            case "护士管理":
            	router.navToStaffView("护士");
            	break;
            case "护工管理":
            	router.navToStaffView("护工");
            	
            }
            }
        }
    }
}
