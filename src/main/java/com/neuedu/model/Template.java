package com.neuedu.model;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Template {
    private ArrayList<Question> questionList;
    private WritableStringProperty name;
    private WritableStringProperty id;

    public String getId() {
        return id.get();
    }

    public WritableStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    private WritableStringProperty temType;
    private boolean deleted;

    public Template(String name, String temType) {
        this.name = new WritableStringProperty(name) ;
        this.temType = new WritableStringProperty(temType);
        this.id = new WritableStringProperty("");
        this.questionList = new ArrayList<Question>();
    }
    public Template(){
        this.name = new WritableStringProperty("");
        this.temType = new WritableStringProperty("");
        this.questionList = new ArrayList<Question>();
        this.id = new WritableStringProperty("");
    }

    public void setQuestionList(ArrayList<Question> questionList) {
        this.questionList = questionList;
    }

    public String getName() {
        return name.get();
    }

    public WritableStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getTemType() {
        return temType.get();
    }

    public WritableStringProperty temTypeProperty() {
        return temType;
    }

    public void setTemType(String temType) {
        this.temType.set(temType);
    }



    public ObservableList<Question> getQuestionList(){
        ObservableList<Question> resultList = FXCollections.observableArrayList();
        for(Question question:questionList){
            if(question.isDeleted()==false){
                resultList.add(question);
            }
        }
        return resultList;
    }

    protected boolean addQuestion(Question question){
        if(!this.questionList.contains(question)){
            return this.questionList.add(question);
        }
        else return false;
    }
    protected boolean removeQuestion(Question question){
        if(this.questionList.contains(question)){
            return questionList.remove(question);
        }
        else {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Template template = (Template) o;

        if (deleted != template.deleted) return false;
        return name != null ? name.get().equals(template.name.get()) : template.name.get() == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.get().hashCode() : 0;
        result = 31 * result + (deleted ? 1 : 0);
        return result;
    }
}
