package com.neuedu.model;

import com.neuedu.util.CheckUtil;

import javafx.collections.ObservableList;

import java.time.Duration;
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
	
	public void save() {
		db.saveFile();
	}

//职工管理********************************************************
	public User checkLogin(String id, String password) {
		User user = db.getUserById(id);
		if(user!=null) {
			if(user.getPassword().equals(password)) {
				return user;
			}
		}
		return null;
	}

	public boolean addUser(String id,String password ){
		if(CheckUtil.checkId(id)&&CheckUtil.checkPassword(password)){
			return this.addUser(new User(id, password));
		}
		else{
			return false;
		}
	}
	
	public boolean addUser(String position, String name, String password, String id, String IDNumber, String speciality, LocalDate birthDate, String phoneNumber ){
		if(checkUserInfo(position,name,password,id,IDNumber,speciality,birthDate,phoneNumber)){
			User newUser = new User(position,name,password,id,IDNumber,speciality,birthDate,phoneNumber);
			return this.addUser(newUser);
		}
		else{
			throw new IllegalArgumentException("user argument is wrong!");
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
		if(oldUser!=null){
			return db.updateUser(oldUser,newUser);
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
	public boolean deleteUser(User user){
		if(user != null){
			return db.deleteUser(user);
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
				&&CheckUtil.checkBirthDate(birthDate)&&CheckUtil.checkPhoneNumber(phoneNumber);
	}
	
	public ObservableList<User> getStaffList(String position){
		if(position == null) {
			throw new IllegalArgumentException("null argument!");
		}
		return db.getUserByPosition(position);
		
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
	
	public ObservableList<Patient> getPatientList(){
		return db.getPatientList();
	}
	
	public ObservableList<Patient> getUnsettledPatientList(){
		return db.getUnsettledPatientList();
	}

	public boolean checkPatientInfo(String name, String sex, LocalDate birthDate , String IDNumber,
							String phoneNumber, String urgentContact, String urgentPhoneNumber){
		return CheckUtil.checkName(name)&&CheckUtil.checkSex(sex)
				&&CheckUtil.checkBirthDate(birthDate)
				&&CheckUtil.checkIDNumber(IDNumber)&&CheckUtil.checkPhoneNumber(phoneNumber)
				&&CheckUtil.checkName(urgentContact)&&CheckUtil.checkPhoneNumber(urgentPhoneNumber);
	}
	public ObservableList<Patient> searchPatient(String info){
		if(!info.isEmpty()) {
			return db.searchPatient(info);
		}
		else {
			return this.getPatientList();
		}
		
	}
	public boolean updatePatient(Patient oldPatient,String name, String sex, LocalDate birthDate, String IDNumber, String phoneNumber,
			String urgentContact, String urgentPhoneNumber) {
		if(checkPatientInfo(name,sex,birthDate,IDNumber,
				phoneNumber,urgentContact,urgentPhoneNumber)){
		return this.updatePatient(oldPatient,new Patient(name,sex,birthDate,IDNumber,
				phoneNumber,urgentContact,urgentPhoneNumber));
		}
		else{
			throw new IllegalArgumentException("your argument is not right!");
		}
		
	}
	
	public boolean updatePatient(Patient oldPatient,Patient newPatient) {
		return db.updatePatient(oldPatient, newPatient);
	}
	
	public boolean deletePatient(Patient patient) {
		return db.deletePatient(patient);
	}
	//病患管理******************************************************
	//楼宇管理******************************************************
	public ObservableList<Building> getBuildingList() {
		return db.getBuildingList();
	}
	
	public boolean addBuilding(Building building){
		if(building!= null) {
			return  db.addBuilding(building);
		}
			return false;
		
	}
	
	public boolean addBuilding(String name,String type,String description) {
		if(CheckUtil.checkName(name)&&CheckUtil.checkDescription(description)) {
			return this.addBuilding(new Building(name,type,description));
		}
		else {
			throw new IllegalArgumentException("wrong argument!");
		}
	}
	
	public boolean addBuilding(String name,String description) {
		if(CheckUtil.checkName(name)&&CheckUtil.checkDescription(description)) {
			return this.addBuilding(new Building(name,"building",description));
		}
		else {
			throw new IllegalArgumentException("wrong argument!");
		}
	}
	public boolean addFloor(Building parent, Floor floor) {
		if(parent!=null&&floor!=null) {
			return db.addFloor(parent, floor);
		}
		else {
			return false;
		}
	}
	public boolean addFloor(Building parent, String name , String description) {
		if(CheckUtil.checkName(name)&&CheckUtil.checkDescription(description)) {
		return this.addFloor(parent, new Floor(name, "floor",description));
		}
		else {
			throw new IllegalArgumentException("wrong argument!");
		}
	}
	
	public boolean addRoom(Floor parent, Room room) {
		if(parent!= null&&room!=null) {
			return db.addRoom(parent, room);
		}
		return false;
	}
	public boolean addRoom(Floor parent, String name,String type, String description){
		if(CheckUtil.checkName(name)&&CheckUtil.checkType(type)&&CheckUtil.checkDescription(description)) {
			if(type.equals("ward")) {
				return db.addRoom(parent, new Ward(name,type,description));
			}
			else {
				return db.addRoom(parent, new SpecialRoom(name,type,description));
			}
		}
		throw new IllegalArgumentException("wrong arguemnt!");
	}
	public boolean addSpecialRoom(Floor parent, String name,String description,int num){
		if(CheckUtil.checkName(name)&&CheckUtil.checkDescription(description)) {
				return this.addRoom(parent, new SpecialRoom(name,"specialRoom",description,num));
			}
		throw new IllegalArgumentException("wrong arguemnt!");
	}
	
	public boolean addBed(Ward parent,Bed bed) {
		if(parent!= null && bed!= null) {
			return db.addBed(parent, bed);
		}
		return false;
	}
	
	public boolean addBed(Ward parent, String name, String description) {
		if(CheckUtil.checkName(name)&&CheckUtil.checkDescription(description)) {
			return this.addBed(parent, new Bed(name,"bed",description));
		}
		throw new IllegalArgumentException("wrong argument!");
		
	}
	
	public boolean deleteBuilding(Building building) {
		//TODO:add check here
		if(building.oFloorList().size()== 0) {
			return db.deleteSite(building);
		}
		return false;
	}
	
	public boolean deleteFloor(Floor floor) {
		if(floor.oRoomList().size()==0) {
			return db.deleteSite(floor);
		}
		return false;
	}
	
	public boolean deleteRoom(Room room) {
		if(room instanceof Ward) {
			if(((Ward)room).oBedList().size()==0) {
				return db.deleteSite(room);
			}
			else {
				return false;
			}
		}
		else {
			return db.deleteSite(room);
		}
	}
	
	public boolean deleteBed(Bed bed) {
		if(bed.getPatient()==null) {
			return db.deleteSite(bed);
		}
		else {
			return false;
		}
	}
	
	//楼宇管理*************************************************************************
	//床位管理*************************************************************************
	public ObservableList<Bed> getAllBedList() {
		return db.getAllBedList();
	}
	
	public boolean moveIn(Bed bed, Patient patient,LocalDate startDate, LocalDate endDate) {
		if(this.getAllBedList().contains(bed)&&this.getPatientList().contains(patient)){
			if(bed.getPatient()==null){
				return db.moveIn(bed,patient,startDate,endDate);
			}
			else if(bed.getPatient().equals(patient)){
				return  db.moveIn(bed,patient,startDate,endDate);
				}
			else{
				return false;
			}
		}
		return false;
	}

	public boolean moveOut(Bed bed){
		if(bed.getPatient()!=null){
			return db.moveOut(bed,bed.getPatient());
		}
		return false;
	}
	public boolean moveOut(Patient patient){
		//TODO:add more check here
		if(patient.getBed()!=null){
			return db.moveOut(patient.getBed(),patient);
		}
		return false;
	}
	public boolean swapBed(Bed bed1, Bed bed2){
		if (bed1.getPatient()==null||bed2.getPatient()==null){
			return false;
		}
		else if(!this.getAllBedList().contains(bed1)||!this.getAllBedList().contains(bed2)){
			return false;
		}
		else{
			return db.swap(bed1,bed2);
		}
	}
//特殊房间管理*********************************************************************************
	public ObservableList<SpecialRoom> getSpecialRoomList(){
		return db.getSpecialRoomList();
	}
	public Duration apply(SpecialRoom specialRoom, Person person, Duration duration){
		if(specialRoom==null||person == null|| duration == null){
			throw new IllegalArgumentException("null argument!");
		}
		return db.apply(specialRoom,person,duration);
	}

	
	
	
	
	
//问题管理与模板管理*******************************************************************************
	public boolean addQuestion(Question question){
		if(question ==null){
			return false;
		}
		return db.addQuestion(question);
	}
	
	public boolean addQuestion(String stem,String form, String choiceA, String choiceB, String choiceC) {
		//TODO:add check here
		return this.addQuestion(new Question(stem,form,choiceA,choiceB,choiceC));
		
	}

	public boolean deleteQuestion(Question question){
		if(question == null){
			return false;
		}
		return db.deleteQuestion(question);
	}

	public ObservableList<Question> searchQuestion(String info){
		if(info == null){
			throw new IllegalArgumentException("null argument");
		}
		return db.searchQuestion(info);
	}

	public ObservableList<Question> getQuestionList(){
		return db.getQuestionList();
	}

	public boolean updateQuestion(Question oldQuestion, Question newQuestion){
		if(oldQuestion==null||newQuestion==null){
			return false;
		}
		else{
			return db.updateQuestion(oldQuestion,newQuestion);
		}
	}

	public boolean addTemplate(Template template){
		if (template ==null){
			return false;
		}
		else{
			return db.addTemplate(template);
		}
	}

	public boolean addTemplate(String name, String temType){
		//TODO:add check here
		return db.addTemplate(new Template(name,temType));
	}
	public boolean deleteTemplate(Template template){
		if(template == null){
			return false;
		}
		else{
			return db.deleteTemplate(template);
		}
	}
	public ObservableList<Template> searchTemplate(String info){
		if(info==null){
			throw new IllegalArgumentException("null argument!");
		}
		return db.searchTemplate(info);
	}

	public ObservableList<Template> getTemplateList(){

		return db.getTemplateList();
	}
	public boolean updateTemplate(Template oldTemplate, Template newTemplate){
		if(oldTemplate==null||newTemplate==null){
			return false;
		}
		else {
			return db.updateTemplate(oldTemplate,newTemplate);
		}
	}

	public boolean addQuestionToTem(Template template,Question question){
		if(template == null|| question == null){
			return false;
		}
		else {
			return db.addQuestionToTem(template,question);
		}
	}
	public boolean removeQuestionFromTem(Template template, Question question){
		if(template == null||question==null){
			return false;
		}
		else{
			return db.removeQuestionFormTem(template,question);
		}
	}


	public boolean updateQuestion(Question oldQuestion, String stem, String form, String choiceA, String choiceB, String choiceC) {
		//TODO:add better check
		if(CheckUtil.checkNotBlank(stem)&&CheckUtil.checkNotBlank(form)&&CheckUtil.checkNotBlank(choiceA)
				&&CheckUtil.checkNotBlank(choiceB)&&CheckUtil.checkNotBlank(choiceC)){
			return this.updateQuestion(oldQuestion,new Question(stem,form,choiceA,choiceB,choiceC));

		}
		return false;
	}

	public ObservableList<SpecialRoom> searchSpecialRoom(String info) {
		if(info == null){
			throw new IllegalArgumentException("null argument");
		}
		return db.searchSpecialRoom(info);
	}

	public ObservableList<User> searchUser(String t1) {
		return db.searchUser(t1);
	}

	public boolean updateTemplate(Template oldTemplate, String stem, String form) {
		if(CheckUtil.checkNotBlank(stem)&&CheckUtil.checkNotBlank(form)) {
		return  this.updateTemplate(oldTemplate,new Template(stem,form));
				}
		else {
			return false;
		}
	}
}
