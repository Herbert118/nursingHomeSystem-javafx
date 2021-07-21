package com.neuedu.model;

import javafx.beans.property.*;

import java.io.Serializable;
import java.time.LocalDate;

public class Patient extends Person implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9108895701869089028L;
	private StringProperty name;
    private StringProperty sex;
	private ObjectProperty<LocalDate> birthDate;
    private StringProperty IDNumber;
    private StringProperty phoneNumber;
    private StringProperty urgentContact;
    private StringProperty urgentPhoneNumber;
    private IntegerProperty age;
    private Bed bed;
    private boolean deleted;
    public Bed getBed() {
        return bed;
    }


    public void setBed(Bed bed) {
        this.bed = bed;
    }

    

    public boolean isDeleted() {
        return deleted;
    }
    public boolean getDeleted() {
    	return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Patient() {
    	this.name = new SimpleStringProperty("");
        this.sex = new SimpleStringProperty("");
        this.birthDate = new SimpleObjectProperty<LocalDate>(LocalDate.now());
        this.phoneNumber = new SimpleStringProperty("");
        this.urgentContact = new SimpleStringProperty("");
        this.urgentPhoneNumber = new SimpleStringProperty("");
        this.IDNumber = new SimpleStringProperty("");
        this.age = new SimpleIntegerProperty();
        this.age.set(LocalDate.now().getYear() - this.getBirthDate().getYear());
        this.deleted = false;
    }
    
    public Patient(String name, String sex, LocalDate birthDate ,
                   String IDNumber,String phoneNumber, String urgentContact, String urgentPhoneNumber ) {
        this.name = new SimpleStringProperty(name);
        this.sex = new SimpleStringProperty(sex);
        this.birthDate = new SimpleObjectProperty<LocalDate>(birthDate);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.urgentContact = new SimpleStringProperty(urgentContact);
        this.urgentPhoneNumber = new SimpleStringProperty(urgentPhoneNumber);
        this.IDNumber = new SimpleStringProperty(IDNumber);
        this.age = new SimpleIntegerProperty();
        this.age.set(LocalDate.now().getYear() - this.getBirthDate().getYear());
        this.deleted =false;
    }

    public LocalDate getBirthDate() {
		return birthDate.get();
	}
    
    public void setBirthDate(LocalDate birthDate) {
    	this.birthDate.set(birthDate);
    }
    public ObjectProperty<LocalDate>birthDateProperty(){
    	return birthDate;
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

    public int getAge() {
        return age.get();
    }

    public IntegerProperty ageProperty() {
        return age;
    }
    
    public void setAge(int age) {
    	this.age.set(age);
    }


    public String getSex() {
        return sex.get();
    }

    public StringProperty sexProperty() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex.set(sex);
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

    public String getUrgentContact() {
        return urgentContact.get();
    }

    public StringProperty urgentContactProperty() {
        return urgentContact;
    }

    public void setUrgentContact(String urgentContact) {
        this.urgentContact.set(urgentContact);
    }

    public String getUrgentPhoneNumber() {
        return urgentPhoneNumber.get();
    }

    public StringProperty urgentPhoneNumberProperty() {
        return urgentPhoneNumber;
    }

    public void setUrgentPhoneNumber(String urgentPhoneNumber) {
        this.urgentPhoneNumber.set(urgentPhoneNumber);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Patient patient = (Patient) o;

        return IDNumber.equals(patient.IDNumber) && !patient.isDeleted() && !this.isDeleted();
    }

    @Override
    public int hashCode() {
        return IDNumber.hashCode();
    }
}
