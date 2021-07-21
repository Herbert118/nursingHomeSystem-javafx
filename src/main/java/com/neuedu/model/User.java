package com.neuedu.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class User extends Person{

	private StringProperty position;
	private StringProperty name;
	private StringProperty password;
	private StringProperty id;
	private StringProperty IDNumber;
	private StringProperty speciality;
	private ObjectProperty<LocalDate> birthDate;
	private StringProperty phoneNumber;
	private boolean deleted;
	
	//damn fastjson, you can't do default thing without telling me....
	public void User() {
		this.position = new SimpleStringProperty("");
		this.name = new SimpleStringProperty("");
		this.password = new SimpleStringProperty("");
		this.id = new SimpleStringProperty("");
		this.IDNumber = new SimpleStringProperty("");
		this.speciality = new SimpleStringProperty("");
		this.birthDate = new SimpleObjectProperty<LocalDate>(LocalDate.now());
		this.phoneNumber = new SimpleStringProperty("");
		this.deleted = false;
	}

	public User(String position, String name, String password, String id, String IDNumber, String speciality, LocalDate birthDate, String phoneNumber) {
		this.position = new SimpleStringProperty(position);
		this.name = new SimpleStringProperty(name);
		this.password = new SimpleStringProperty(password);
		this.id = new SimpleStringProperty(id);
		this.IDNumber = new SimpleStringProperty(IDNumber);
		this.speciality = new SimpleStringProperty(speciality);
		this.birthDate = new SimpleObjectProperty<LocalDate>(birthDate);
		this.phoneNumber = new SimpleStringProperty(phoneNumber);
		this.deleted = false;
	}


	public User(String id, String password) {
		this.id =  new SimpleStringProperty(id);
		this.password = new SimpleStringProperty(password);
		this.position = new SimpleStringProperty("");
		this.name = new SimpleStringProperty("");
		this.IDNumber = new SimpleStringProperty("");
		this.speciality = new SimpleStringProperty("");
		this.phoneNumber = new SimpleStringProperty("");
		this.birthDate = new SimpleObjectProperty(null);
	}


	public User(String position, String id, String password) {
		this.position = new SimpleStringProperty(position);
		this.id = new SimpleStringProperty(id);
		this.password = new SimpleStringProperty(password);
		this.name = new SimpleStringProperty("");
		this.IDNumber = new SimpleStringProperty("");
		this.speciality = new SimpleStringProperty("");
		this.phoneNumber = new SimpleStringProperty("");
		this.birthDate = new SimpleObjectProperty(null);
	}

	public String getPassword() {
		return password.get();
	}

	public StringProperty passwordProperty() {
		return password;
	}

	public void setPassword(String password) {
		this.password.set(password);
	}

	public String getId() {
		return id.get();
	}

	public StringProperty idProperty() {
		return id;
	}

	public void setId(String id) {
		this.id.set(id);
	}

	public String getIDNumber() {
		return IDNumber.get();
	}

	public StringProperty IDNumberProperty() {
		return IDNumber;
	}

	public void setIDNumber(String IDNumber) {
		this.IDNumber.set(IDNumber);
	}

	public String getSpeciality() {
		return speciality.get();
	}

	public StringProperty specialityProperty() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality.set(speciality);
	}

	public LocalDate getBirthDate() {
		return birthDate.get();
	}

	public ObjectProperty birthDateProperty() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate.set(birthDate);
	}

	public String getPhoneNumber() {
		return phoneNumber.get();
	}

	public StringProperty phoneNumberProperty() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber.set(phoneNumber);
	}

	public String getName() {
		return name.get();
	}

	public StringProperty nameProperty() {
		return name;
	}

	public void setName(String name) {
		this.name.set(name);
	}


	public String getPosition() {
		return position.get();
	}

	public StringProperty positionProperty() {
		return position;
	}

	public void setPosition(String position) {
		this.position.set(position);
	}
	public boolean isDeleted(){
		return this.deleted;
	}
	public void setDeleted(boolean deleted){
		this.deleted = deleted;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return (IDNumber.equals(user.IDNumber) || id.equals(user.id))&&!user.isDeleted()&& !this.isDeleted();
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
