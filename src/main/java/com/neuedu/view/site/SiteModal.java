package com.neuedu.view.site;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.neuedu.model.Building;
import com.neuedu.model.Floor;
import com.neuedu.model.Service;
import com.neuedu.model.Site;
import com.neuedu.model.Ward;
import com.neuedu.util.CheckUtil;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SiteModal {
    private Service service;
    private Stage siteModal;
    private VBox modalRoot;
    private GridPane fieldGridPane;
    private JFXTextField nameFld;
    private JFXComboBox<String> typeBox;
    private JFXTextField descFld;
    
    private JFXTextField containNumFld;
    private JFXButton confirmButton;
    private Text warnText;
    private Site parent;
    public SiteModal(Service service,Site parent){
        this.service = service;
        this.parent = parent;
        init();
        lay();
    }
    private void init(){
    	siteModal = new Stage();
        modalRoot = new VBox();
        fieldGridPane = new GridPane();
        nameFld = new JFXTextField();
        typeBox = new JFXComboBox<String>();
        descFld = new JFXTextField();
        containNumFld = new JFXTextField();
        confirmButton = new JFXButton("确认");
        warnText = new Text();
    }
    private void lay(){
        fieldGridPane.add(new Label("名称"),0,0);
        fieldGridPane.add(new Label(("类型")),0,1);
        fieldGridPane.add(new Label("补充描述"),0,2);
        
        fieldGridPane.add(nameFld,1,0);
        fieldGridPane.add(typeBox, 1, 1);
        fieldGridPane.add(descFld, 1, 2);
        
        
        //fieldGridPane.add(new Label("容纳数量"),0,3);
        Label label = new Label("容纳数量");
        switch(parent.getType()) {
        case "center":
        	typeBox.getItems().add("楼房");
        	typeBox.setValue("楼房");
        	break;
        case "building":
        	typeBox.getItems().add("楼层");
        	typeBox.setValue("楼房");
        	break;
        case "floor":
        	typeBox.getItems().add("病房");
        	typeBox.getItems().add("特殊房间");
        	fieldGridPane.add(label, 0, 3);
        	fieldGridPane.add(containNumFld, 1, 3);
        	label.visibleProperty().bind(typeBox.valueProperty().isEqualTo("特殊房间"));
        	containNumFld.visibleProperty().bind(typeBox.valueProperty().isEqualTo("特殊房间"));
        	break;
        case "ward" :
        	typeBox.getItems().add("床位");
        	typeBox.setValue("床位");;
        	break;
        default:
        	throw new IllegalArgumentException("wrong argument!");
        }
        
        
        fieldGridPane.setHgap(20);
        fieldGridPane.setVgap(20);
        fieldGridPane.setAlignment(Pos.CENTER);
        fieldGridPane.setPadding(new Insets(50,30,30,30));
        
        modalRoot.getChildren().add(fieldGridPane);
        modalRoot.getChildren().add(confirmButton);
        modalRoot.setAlignment(Pos.CENTER);
        
        VBox.setMargin(confirmButton,new Insets(30));
        modalRoot.getChildren().add(warnText);
        VBox.setMargin(warnText, new Insets(30,0,0,0));
        warnText.setFill(Color.RED);

    }

    public void addSite(){
        confirmButton.setOnAction(e ->{
            String name = nameFld.getText();
            String description = descFld.getText();
            String type = typeBox.getValue();
            if(type.equals("特殊房间")){
            	String	containNum = containNumFld.getText();
            	if(!check(name,type,description,containNum)){
                    return;//jump out of actionlistener
                }
            	else {
            		boolean success = service.addSpecialRoom((Floor)parent, name, description, Integer.parseInt(containNum));
            		if(success) {
            			warnText.setText("添加成功!");
            		}
            		else {
            			warnText.setText("添加失败!");
            		}
            	}
            	
            	
            }
            else {
            	if(!check(name,type,description,"3")){
                    return;
                }
            	boolean success = true;
            	switch(parent.getType()) {
                case "center":
                	success = service.addBuilding(name, description);
                	break;
                case "building":
                	success = service.addFloor((Building)parent,name, description);
                	break;
                case "floor":
                	success = service.addRoom((Floor)parent, name, "ward", description);
                	break;
                case "ward" :
                	success = service.addBed((Ward)parent, name,description);
                	break;
                default:
                	throw new IllegalArgumentException("wrong argument!");
                }
            	if(success) {
            		warnText.setText("新增成功");
            	}
            	else {
            		warnText.setText("新增失败");
            	}
            }
            
  
        });

        Scene scene = new Scene(modalRoot);
        scene.getStylesheets().add("modal.css");
        siteModal.setScene(scene);
        siteModal.setTitle("新增地点");
        siteModal.showAndWait();
    }
    
    
    private boolean check(String name, String type, String description, String containNumber) {
        boolean result = true;
        String text = "";
        if (!CheckUtil.checkName(name)){
            text += "名称 ";
            result = false;
        }
        if (!CheckUtil.checkTypeCN(type)){
            text += "类型 ";
            result = false;
        }
        //TODO:check if the birthDate is reasonable
        if(!CheckUtil.checkContainNumber(containNumber)){
            text += "容纳数量 ";
            result = false;
        }
        if(!CheckUtil.checkDescription(description)){
            text += "补充描述 ";
            result = false;
        }
        if(result == false){
            warnText.setText(text+"格式有误");
        }
        return result;

    }


}
