package com.neuedu.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;

public class WritableStringProperty extends SimpleStringProperty implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6256090384909621242L;
	public WritableStringProperty(String string) {
		super(string);
	}
	
	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		s.writeUTF(this.getValueSafe());
	}
	private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
		s.defaultReadObject();
		set(s.readUTF());
	}
}
