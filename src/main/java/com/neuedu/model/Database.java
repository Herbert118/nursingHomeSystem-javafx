package com.neuedu.model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

import com.neuedu.util.ObjectInputUtil;
import com.neuedu.util.ObjectOutputUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Database {
    private static Database db;
    private ObservableList<User> userList;
    private ObservableList<Patient> patientList;
    private ObservableList<Building> buildingList;
    private ObservableList<Template> templateList;
    private ObservableList<Question> questionList;


    private Database() {
        loadFile();
        saveFile();
    }

    public static Database getInstance() {
        if (db == null) {
            db = new Database();
        }
        return db;
    }

    //文件读写***************************************************************************************
    public void loadFile() {
        
        try {
        	ObjectInputUtil<User> oiu1 = new ObjectInputUtil<User>("users.json", User.class, ObjectInputUtil.JSONFORMAT);
        	userList = FXCollections.<User>observableArrayList();
        	userList.addAll(oiu1.readJsonArrayList());//ObservableList not Serializable
            ObjectInputUtil<Patient> oiu2 = new ObjectInputUtil<Patient>("patients.json", Patient.class, ObjectInputUtil.JSONFORMAT);
            patientList = FXCollections.<Patient>observableArrayList();
            patientList.addAll(oiu2.readJsonArrayList());
            ObjectInputUtil<ArrayList<Building>> oiu3 = new ObjectInputUtil<ArrayList<Building>>("buildings.ser",ObjectInputUtil.SERIALFORMAT);
            buildingList = FXCollections.<Building>observableArrayList();
            buildingList.addAll(oiu3.readSerialObject());
            
            //TODO: init patient
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (userList == null) {
                userList = FXCollections.<User>observableArrayList();
                userList.add(new User("Admin", "Admin", "Admin"));
                System.out.println("本地没有用户文件");
            }
            if (patientList == null) {
                patientList = FXCollections.<Patient>observableArrayList();
                patientList.add(new Patient("test", "男", LocalDate.of(2002,10,30), "test", "test", "test", "test"));
                System.out.println(("本地没有病人信息"));
            }
            if(buildingList == null||buildingList.size()==0){
                buildingList = FXCollections.observableArrayList();
                Building building = new Building("name","building","good");
                building.setSiteId("00");
                building.addFloor(new Floor("name","floor","good"));
                buildingList.add(building);
                System.out.println(("本地沒有楼宇信息"));
            }
            saveFile();
        }
    }

    public void saveFile() {
        ObjectOutputUtil<ArrayList<User>> oou1 = new ObjectOutputUtil<ArrayList<User>>("users.json", ObjectOutputUtil.JSONFORMAT);
        ObjectOutputUtil<ArrayList<Patient>> oou2 = new ObjectOutputUtil<ArrayList<Patient>>("patients.json", ObjectOutputUtil.JSONFORMAT);
        ObjectOutputUtil<ArrayList<Building>> oou3 = new ObjectOutputUtil<>("buildings.ser",ObjectOutputUtil.SERIALFORMAT);
        try {
            oou1.writeJsonObject(new ArrayList<User>(userList));
            oou2.writeJsonObject(new ArrayList<Patient>(patientList));
            oou3.writeSerialObject(new ArrayList<Building>(buildingList));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //文件读写***************************************************************

    //用户管理*******************************************************************
    public boolean addUser(User user){
        return userList.add(user);
    }

    protected final ObservableList<User> getUserList(){
        return userList;
        
    }
    public User getUserById(String id){
        for(User user: userList){
            if(user.getId().equals(id) && !user.isDeleted()){
                return user;
            }
        }
        return null;
    }
    public ObservableList<User> searchUser(String info) {
        ObservableList<User> resultList = FXCollections.<User>observableArrayList();

        for (User user : userList) {
            if(!user.isDeleted()) {
                if (user.getName().contains(info)) {
                    resultList.add(user);
                }
                //TODO:to know what localDate is like
                else if (user.getBirthDate().toString().contains(info)) {
                    resultList.add(user);
                } else if (user.getPhoneNumber().contains(info)) {
                    resultList.add(user);
                } else if (user.getIDNumber().contains(info)) {
                    resultList.add(user);
                } else if (user.getId().contains(info)) {
                    resultList.add(user);
                } else if (user.getSpeciality().contains(info)) {
                    resultList.add(user);
                }
            }
        }
        return resultList;
    }
    public boolean updateUser(int index,User newUser){
        return  (userList.set(index, newUser)!= null);
    }
    
    public boolean updateUser(User oldUser, User newUser) {
    	return this.updateUser(this.getUserList().indexOf(oldUser), newUser);
    }
    //用户管理***********************************************************
//病患管理*********************************************************

    public boolean addPatient(Patient patient){
        return patientList.add(patient);
    }

    public ObservableList<Patient> searchPatient(String info) {
        ObservableList<Patient> resultList = FXCollections.<Patient>observableArrayList();
        for (Patient patient : patientList) {
            if(!patient.isDeleted()) {
                if (patient.getName().matches(info)) {
                    resultList.add(patient);
                } else if (info.matches("^[1-9]{1,4}$")) {
                    if (patient.getAge() == Integer.parseInt(info)) {
                        resultList.add(patient);
                    }
                } else if (patient.getSex().contains(info)) {
                    resultList.add(patient);
                } else if (patient.getPhoneNumber().contains(info)) {
                    resultList.add(patient);
                } else if (patient.getUrgentContact().contains(info)) {
                    resultList.add(patient);
                } else if (patient.getUrgentContact().contains(info)) {
                    resultList.add(patient);
                } else if (patient.getUrgentPhoneNumber().contains(info)) {
                    resultList.add(patient);
                }
            }
        }
        return resultList;
    }

    
    //TODO: to be tested if it works for IDnumber
    public int getIndexOfPatient(Patient patient){
        return patientList.indexOf(patient);
    }

    public boolean updatePatient(int index,Patient patient){
        return  (patientList.set(index, patient) != null);
    }
    public boolean updatePatient(Patient oldpatient,Patient newPatient){
        return this.updatePatient(this.getIndexOfPatient(oldpatient), newPatient);
    }

    public boolean deletePatient(int index){
        if(patientList.get(index) != null) {
            if(patientList.get(index).isDeleted() == false) {
                patientList.get(index).setDeleted(true);
                return true;
            }
        }
        return false;
    }
    public boolean deletePatient(Patient patient){
        if(patient!=null){
            if(patient.isDeleted() == false){
                patient.setDeleted(true);
                return true;
            }
        }
        return false;
    }
    public ObservableList<Patient> getPatientList() {
    	ObservableList<Patient> validPaList = FXCollections.observableArrayList();
		for(Patient patient : patientList) {
			if(!patient.isDeleted()) {
				validPaList.add(patient);
			}
		}
		;
		return validPaList;
	}
    
    public ObservableList<Patient> getUnsettledPatientList(){
    	ObservableList<Patient> unSettledPatientList = FXCollections.<Patient>observableArrayList();
    	for(Patient patient:patientList) {
    		if(!patient.isDeleted()&&patient.getBed()==null) {
    			unSettledPatientList.add(patient);
    		}
    	}
    	return unSettledPatientList;
    }

    public int getIndexOfUser(User oldUser) {
        if(oldUser.isDeleted() == false){
            return userList.indexOf(oldUser);
        }
        else{
            return -1;
        }
    }
//病患管理***************************************************************
//楼宇管理****************************************************************
    public boolean addBuilding(Building building) {
    	if(buildingList.contains(building)||building==null) {
    		return false;
    	}
    	boolean success = buildingList.add(building);
    	if(success){
            int index = buildingList.indexOf(building);
            String buildingIndex = (index<10?"0":"")+index;
    	    building.setSiteId(buildingIndex);
    	    building.setLocation(building.getName());
        }
        return success;
    }

    public ObservableList<Building> getBuildingList(){
        ObservableList<Building> resultList = FXCollections.observableArrayList();
		for(Building building : buildingList) {
			if(building.isDeleted()==false){
		        resultList.add(building);
            }
		}
    	return resultList;
    }
    //it is not good practice, but more convient
    public boolean addFloor(Building building,Floor floor){
        return building.addFloor(floor);
    }

    public boolean addRoom(Floor floor,Room room){
        return floor.addRoom(room);
    }
    
    public boolean addBed(Ward ward, Bed bed) {
    	return ward.addBed(bed);
    }

    public boolean deleteSite(Site site){
        if(!site.isDeleted()){
            site.setDeleted(true);
            return true;
        }
        else{
            return false;
        }
    }
    
    //床位管理**************************************************************
    public ObservableList<Bed> getAllBedList(){
    	ObservableList<Bed> allBedList = FXCollections.observableArrayList();
    	for(Building building:buildingList) {
    		for(Floor floor:building.getFloorList()) {
    			for(Room room:floor.getRoomList()) {
    				if(room.getType().equals("ward")) {
    					for(Bed bed:((Ward)room).getBedList()) {
    						//deleted is all checked in model
    						allBedList.add(bed);
    					}
    				}
    			}
    		}
    	}
    	return allBedList;
    }

    public boolean moveIn(Bed bed, Patient patient,LocalDate startDate, LocalDate endDate) {
        bed.setPatient(patient);
        bed.setStartDate(startDate);
        bed.setEndDate(endDate);
        patient.setBed(bed);
        return true;
    }

    public boolean moveOut(Bed bed, Patient patient){
        bed.setPatient(null);
        bed.setStartDate(null);
        bed.setEndDate(null);
        patient.setBed(null);
        return true;
    }

    public boolean swap(Bed bed1, Bed bed2){
        Patient temp1;
        LocalDate temp2;
        temp1 = bed1.getPatient();
        bed1.setPatient(bed1.getPatient());
        bed1.setPatient(temp1);
        temp2 = bed1.getStartDate();
        bed1.setStartDate(bed2.getStartDate());
        bed2.setStartDate(temp2);
        temp2 = bed1.getEndDate();
        bed1.setEndDate(bed2.getEndDate());
        bed2.setEndDate(temp2);
        return true;
    }
    //特殊房间管理*************************************
    public ObservableList<SpecialRoom> getSpecialRoomList(){
        ObservableList<SpecialRoom> resultList = FXCollections.observableArrayList();
        for(Building building: this.getBuildingList()){
            for(Floor floor:building.getFloorList()){
                for(Room room: floor.getRoomList()){
                    if(room.getType().equals("specialRoom")){
                        resultList.add((SpecialRoom) room);
                    }
                }
            }
        }
        return resultList;
    }

    public Duration apply(SpecialRoom specialRoom, Person person, Duration duration){
        return specialRoom.apply(person,duration);
    }

}