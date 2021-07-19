package com.neuedu.model;

import javafx.beans.property.ObjectProperty;

import java.time.LocalDate;

public class Bed extends Site{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7495446426670326572L;
	private Patient patient;
	private LocalDate startDate;
	private LocalDate endDate;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Bed(String name, String type, String description) {
		super(name,"bed",description);
	}

	public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
