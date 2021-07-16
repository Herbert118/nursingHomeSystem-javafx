package com.neuedu.model;

import java.time.LocalDate;
import java.util.ArrayList;

import com.neuedu.util.ObjectInputUtil;
import com.neuedu.util.ObjectOutputUtil;
import javafx.beans.Observable;
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
            ObjectInputUtil<Patient> oiu2 = new ObjectInputUtil<Patient>("patients.json", Patient.class, ObjectInputUtil.JSONFORMAT);
            userList = FXCollections.<User>observableArrayList();
            patientList = FXCollections.<Patient>observableArrayList();
            userList.addAll(oiu1.readJsonArrayList());//ObservableList not Serializable
            patientList.addAll(oiu2.readJsonArrayList());
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
            saveFile();
        }
    }

    public void saveFile() {
        ObjectOutputUtil<ArrayList<User>> oou1 = new ObjectOutputUtil<ArrayList<User>>("users.json", ObjectOutputUtil.JSONFORMAT);
        ObjectOutputUtil<ArrayList<Patient>> oou2 = new ObjectOutputUtil<ArrayList<Patient>>("patients.json", ObjectOutputUtil.JSONFORMAT);

        try {
            oou1.writeJsonObject(new ArrayList<User>(userList));
            oou2.writeJsonObject(new ArrayList<Patient>(patientList));
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
                if (user.getName().equals(info)) {
                    resultList.add(user);
                }
                //TODO:to know what localDate is like
                else if (user.getBirthDate().toString().equals(info)) {
                    resultList.add(user);
                } else if (user.getPhoneNumber().equals(info)) {
                    resultList.add(user);
                } else if (user.getIDNumber().equals(info)) {
                    resultList.add(user);
                } else if (user.getId().equals(info)) {
                    resultList.add(user);
                } else if (user.getSpeciality().equals(info)) {
                    resultList.add(user);
                }
            }
        }
        return resultList;
    }
    public boolean updateUser(int index,User newUser){
        return  (userList.set(index, newUser)!= null);
    }
    //用户管理***********************************************************
//病患管理*********************************************************

    public boolean addPatient(Patient patient){
        return patientList.add(patient);
    }

    public ObservableList<Patient> searchPatient(String info) {
        ObservableList<Patient> resultList = FXCollections.<Patient>observableArrayList();
        for (Patient patient : patientList) {
            if(patient.isDeleted()) {
                if (patient.getName().equals(info)) {
                    resultList.add(patient);
                } else if (info.matches("^[1-9]{1,4}$")) {
                    if (patient.getAge() == Integer.parseInt(info)) {
                        resultList.add(patient);
                    }
                } else if (patient.getSex().equals(info)) {
                    resultList.add(patient);
                } else if (patient.getPhoneNumber().equals(info)) {
                    resultList.add(patient);
                } else if (patient.getUrgentContact().equals(info)) {
                    resultList.add(patient);
                } else if (patient.getUrgentContact().equals(info)) {
                    resultList.add(patient);
                } else if (patient.getUrgentPhoneNumber().equals(info)) {
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

    public boolean updatePatient(Patient patient,int index){
        return  (patientList.set(index, patient) != null);
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

    public int getIndexOfUser(User oldUser) {
        if(oldUser.isDeleted() == false){
            return userList.indexOf(oldUser);
        }
        else{
            return -1;
        }
    }
//病患管理***************************************************************

}