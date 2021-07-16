package com.neuedu.model;

import com.neuedu.util.CheckUtil;

import java.time.LocalDate;

public class Service {
	private final Database db;
	private static Service service;
	private Service() {
		db = Database.getInstance();
	}
	public static Service getInstance() {
		if(service == null) {
			service = new Service();
		}
		return service;
	}

//职工管理********************************************************
	public boolean checkLogin(String id, String password) {
		User user = db.getUserById(id);
		if(user!=null) {
			if(user.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}

	public boolean addUser(String id,String password ){
		if(CheckUtil.checkId(id)&&CheckUtil.checkPassword(password)){
			return this.addUser(new User(id, password));
		}
		else{
			return false;
		}
	}
	public boolean addUser(User user) {
		if (user != null) {
			return db.addUser(user);
		}
		else {
			return false;
		}
	}
	public boolean updateUser(int index, User newUser){
		if(newUser != null){
		 return	db.updateUser(index,newUser);
		}
		else{
			return false;
		}
	}

	public boolean updateUser(User oldUser, User newUser){
		int index = db.getIndexOfUser(oldUser);
		if(index != -1){
			return this.updateUser(index,newUser);
		}
		else {
			return true;
		}
	}

	public boolean updateUser(User oldUser,String position, String name,
							  String password, String id, String IDNumber,
							  String speciality, LocalDate birthDate, String phoneNumber){

		if(checkUserInfo(position,name,password,id,IDNumber,speciality,birthDate,phoneNumber)){
			User newUser = new User(position,name,password,id,IDNumber,speciality,birthDate,phoneNumber);
			return this.updateUser(oldUser,newUser);
		}
		else{
			throw new IllegalArgumentException("user argument is wrong!");
		}

	}
	public boolean deleteUser(Patient patient){
		if(patient != null){
			return db.deletePatient(patient);
		}else {
			return false;
		}
	}
	public boolean deleteUser(int index){
		if(index > 0 && index < db.getUserList().size()){
			return db.deletePatient(index);
		}
		else{
			return false;
		}
	}
	public boolean checkUserInfo(String position, String name,
								 String password, String id, String IDNumber,
								 String speciality, LocalDate birthDate, String phoneNumber){
		return CheckUtil.checkPosition(position)&&CheckUtil.checkName(name)&&CheckUtil.checkId(id)
				&&CheckUtil.checkIDNumber(IDNumber)&&CheckUtil.checkSpeciality(speciality)
				&&CheckUtil.checkBirthDate(birthDate.toString())&&CheckUtil.checkPhoneNumber(phoneNumber);
	}
	//职工管理*******************************************************

	//病患管理******************************************************
	public boolean addPatient(Patient patient){
		if(patient!= null) {
			return db.addPatient(patient);
		}
		else {
			return false;
		}
	}

	public boolean addPatient(String name, String sex, LocalDate birthDate , String IDNumber,
							  String phoneNumber, String urgentContact, String urgentPhoneNumber){
		if(checkPatientInfo(name,sex,birthDate,IDNumber,
				phoneNumber,urgentContact,urgentPhoneNumber)){
		return this.addPatient(new Patient(name,sex,birthDate,IDNumber,
				phoneNumber,urgentContact,urgentPhoneNumber));
		}
		else{
			throw new IllegalArgumentException("your argument is not right!");
		}

	}

	public boolean checkPatientInfo(String name, String sex, LocalDate birthDate , String IDNumber,
							String phoneNumber, String urgentContact, String urgentPhoneNumber){
		return CheckUtil.checkName(name)&&CheckUtil.checkSex(sex)
				&&CheckUtil.checkBirthDate(birthDate.toString())
				&&CheckUtil.checkIDNumber(IDNumber)&&CheckUtil.checkPhoneNumber(phoneNumber)
				&&CheckUtil.checkName(urgentContact)&&CheckUtil.checkPhoneNumber(urgentPhoneNumber);
	}
	//病患管理******************************************************
}
