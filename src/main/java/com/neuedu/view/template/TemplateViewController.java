package com.neuedu.view.template;

import com.neuedu.model.Service;
import com.neuedu.model.Template;
import com.neuedu.view.component.Alert;
import com.neuedu.view.component.ConfirmAlert;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;

public class TemplateViewController {

    @FXML
    private Label title;

    @FXML
    private TextField searchFld;

    @FXML
    private TableView<Template> templateTable;
    @FXML
    private TableColumn<Template, String> idCol;

    @FXML
    private TableColumn<Template, String> nameCol;

    @FXML
    private TableColumn<Template, String> temTypeCol;
    private Service service;
    private TableViewSelectionModel<Template> tvsm;

    @FXML
    public void initialize() {
    	service = Service.getInstance();
        initTable();
    }
    
    private void initTable()
    {
        templateTable.setItems(service.getTemplateList());
    	idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    	nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    	temTypeCol.setCellValueFactory(new PropertyValueFactory<>("temType"));
    	
    	tvsm = templateTable.getSelectionModel();

        searchFld.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(t1 == null){
                    templateTable.setItems(service.getTemplateList());
                }
                else {
                    templateTable.setItems(service.searchTemplate(t1));
                }
            }
        });
    }

    @FXML
    void addTemplate(ActionEvent event) {
        TemplateModal modal = new TemplateModal(service,"新增模板");
        modal.addTemplate();
        templateTable.setItems(service.getTemplateList());
    }

    @FXML
    void deleteTemplate(ActionEvent event) {
        ObservableList<Template> templates =  tvsm.getSelectedItems();
        if(templates.isEmpty()){
            Alert.showAlert("请先选择一个职工");
            return;
        }
        else{
        	
        	if(!ConfirmAlert.getConfirm("您确定要删除吗?")) {
        		return;
        	}
        	String text = "";
        	for(Template template : templates) {
            if(service.deleteTemplate(template)){
                text += (template.getName()+"删除成功\n!");
                
            }
            else{
                text += (template.getName()+"删除失败\n");
            }
        	}
        	Alert.showAlert(text);
        	templateTable.setItems(service.getTemplateList());
        }
    }

    @FXML
    void searchTemplate(InputMethodEvent event) {
//useless
    }

    @FXML
    void updateTemplate(ActionEvent event) {
        Template template =  tvsm.getSelectedItem();
        if(template == null){
            Alert.showAlert("请先选择一个模板");
            return;
        }
        else {
            TemplateModal modal = new TemplateModal(service,"修改模板");
            modal.updateTemplate(template);
            templateTable.setItems(service.getTemplateList());
        }
    }
    @FXML
    void seeTemplate(ActionEvent event) {
    	if(tvsm.getSelectedItem()== null) {
    	Alert.showAlert("请先选择一个模板！");	
    	}
    	
    	TemDetailModal modal = new TemDetailModal(tvsm.getSelectedItem(),service);
    	modal.showDetail();
    	
    }
}
