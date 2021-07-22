package com.neuedu.view.site;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.neuedu.model.Bed;
import com.neuedu.model.Building;
import com.neuedu.model.Floor;
import com.neuedu.model.Room;
import com.neuedu.model.Service;
import com.neuedu.model.Site;
import com.neuedu.util.SiteTreeTableUtil;
import com.neuedu.view.component.Alert;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeTableView.TreeTableViewSelectionModel;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

public class SiteViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton addBtn;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private TreeTableView<Site> siteTreeTableView;

    @FXML
    private TreeTableColumn<Site, String> nameCol;

    @FXML
    private TreeTableColumn<Site, String> typeCol;

    @FXML
    private TreeTableColumn<Site, String> siteIdCol;

    @FXML
    private TreeTableColumn<Site, String> descCol;

    private Service service;
    TreeTableViewSelectionModel<Site> ttvsm;
    @FXML
    void initialize() {
    	this.service = Service.getInstance();
        check();
        initTreeTable();
        
    }
    
    /**
     * @param event
     * @description 弹出模态框,将点击的对象传入,进行对应的新增操作 
     */
    @FXML
    
    void addNode(ActionEvent event) {
    	if(ttvsm.getSelectedItem()!=null) {
    	//获取点击对象
    	  Site site = ttvsm.getSelectedItem().getValue();
    	  if(!site.getType().equals("specialRoom")&&!site.getType().equals("bed")) {
    	//弹出模态框
    	  SiteModal modal = new SiteModal(service,site);
    	  modal.addSite();
    	  
    	 //拉取数据刷新缓存
    	  initData();
    	  }
    	  else {
    		  Alert.showAlert("该地不可新建!");
    	  }
    	}
    	else {
    		Alert.showAlert("请先选择一个地点!");
    	}
    	 
    }

    @FXML
    void deleteNode(ActionEvent event) {
    	if(ttvsm.getSelectedItem()==null) {
    		Alert.showAlert("请先选择一个地点");
    	}
    	else if(ttvsm.getSelectedItem().getValue().getType().equals("center")){
    		Alert.showAlert("该地不可删除!");
    	}
    	else {
    		ObservableList<TreeItem<Site>> items = ttvsm.getSelectedItems();
    		String mes = "";
    		for(int i = 0; i <items.size();i++) {
    		Site site = items.get(i).getValue();
    		boolean success;
    		switch(site.getType()) {
    		case "building":
    			success = service.deleteBuilding((Building)site);
    			break;
    			//TODO: not a good practice, both the String type and the inheritance , can be improved
    		case "floor":
    			success = service.deleteFloor((Floor)site);
    			break;
    		case "ward":
    		case "specialRoom":
    			success = service.deleteRoom((Room) site);
    			break;
    		case "bed":
    			success = service.deleteBed((Bed)site);
    			break;
    		default:
    			throw new IllegalArgumentException("wrong argument!");
    		}
    		if(success) {
        		mes+=site.getName()+"删除成功!\n";
    		}
    		else {
    			mes+= site.getName()+"删除失败!\n";
    		}
    		
    		}
    		Alert.showAlert(mes);
    	}
    	initData();
    }
    
    private void check() {
    	assert addBtn != null : "fx:id=\"addBtn\" was not injected: check your FXML file 'SiteView.fxml'.";
        assert deleteBtn != null : "fx:id=\"deleteBtn\" was not injected: check your FXML file 'SiteView.fxml'.";
        assert siteTreeTableView != null : "fx:id=\"siteTreeTableView\" was not injected: check your FXML file 'SiteView.fxml'.";
        assert nameCol != null : "fx:id=\"nameCol\" was not injected: check your FXML file 'SiteView.fxml'.";
        assert typeCol != null : "fx:id=\"typeCol\" was not injected: check your FXML file 'SiteView.fxml'.";
        assert siteIdCol != null : "fx:id=\"siteIdCol\" was not injected: check your FXML file 'SiteView.fxml'.";
        assert descCol != null : "fx:id=\"descCol\" was not injected: check your FXML file 'SiteView.fxml'.";

    }
    private void initTreeTable() {
    	initData();
    	nameCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
    	typeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("type"));
    	siteIdCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("siteId"));
    	descCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
    	ttvsm = siteTreeTableView.getSelectionModel();
    }
    private void initData() {
    	TreeItem<Site> rootNode = SiteTreeTableUtil.parseRootItem(service.getBuildingList());
    	System.out.println(service.getBuildingList());
    	siteTreeTableView.setRoot(rootNode);
    }
}
