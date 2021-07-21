package com.neuedu.model;

import javafx.beans.property.StringProperty;

import java.util.ArrayList;

public class Question {
    private WritableStringProperty stem;
    private WritableStringProperty form;
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
    private WritableStringProperty choiceA;
    private WritableStringProperty choiceB;
    private WritableStringProperty choiceC;
    private boolean deleted;
    //TODO:maybe it can be better? try not rely on TableView


    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    protected Question() {
    	this.stem = new WritableStringProperty("");
        this.form = new WritableStringProperty("");
        this.id = new WritableStringProperty("");
        
        this.choiceA = new WritableStringProperty("");
        this.choiceB = new WritableStringProperty("");
        this.choiceC = new WritableStringProperty("");
        this.deleted = false;
    }

    protected Question(String stem,String form, String choiceA, String choiceB, String choiceC) {
        this.stem = new WritableStringProperty(stem);
        this.form = new WritableStringProperty(form);
        this.choiceA = new WritableStringProperty("A "+choiceA);
        this.choiceB = new WritableStringProperty("B"+choiceB);
        this.choiceC = new WritableStringProperty("C"+choiceC);
        this.id = new WritableStringProperty("");
        this.deleted =false;
    }


    public String getStem() {
        return stem.get();
    }

    public WritableStringProperty stemProperty() {
        return stem;
    }

    public void setStem(String stem) {
        this.stem.set(stem);
    }

    public String getForm() {
        return form.get();
    }

    public WritableStringProperty formProperty() {
        return form;
    }

    public void setForm(String form) {
        this.form.set(form);
    }

    public String getChoiceA() {
        return choiceA.get();
    }

    public WritableStringProperty choiceAProperty() {
        return choiceA;
    }

    public void setChoiceA(String choiceA) {
        this.choiceA.set(choiceA);
    }

    public String getChoiceB() {
        return choiceB.get();
    }

    public WritableStringProperty choiceBProperty() {
        return choiceB;
    }

    public void setChoiceB(String choiceB) {
        this.choiceB.set(choiceB);
    }

    public String getChoiceC() {
        return choiceC.get();
    }

    public WritableStringProperty choiceCProperty() {
        return choiceC;
    }

    public void setChoiceC(String choiceC) {
        this.choiceC.set(choiceC);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (!stem.get().equals(question.stem.get())) return false;
        return id != null ? id.get().equals(question.id.get()) : question.id == null;
    }

    @Override
    public int hashCode() {
        return stem.get().hashCode();
    }
}
