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
	}

	public static Database getInstance() {
		if (db == null) {
			db = new Database();
		}
		return db;
	}

	// 文件读写***************************************************************************************
	public void loadFile() {

		try {
			ObjectInputUtil<User> oiu1 = new ObjectInputUtil<User>("users.json", User.class,
					ObjectInputUtil.JSONFORMAT);
			userList = FXCollections.<User>observableArrayList();
			userList.addAll(oiu1.readJsonArrayList());// ObservableList not Serializable
			ObjectInputUtil<Patient> oiu2 = new ObjectInputUtil<Patient>("patients.json", Patient.class,
					ObjectInputUtil.JSONFORMAT);
			patientList = FXCollections.<Patient>observableArrayList();
			patientList.addAll(oiu2.readJsonArrayList());
			// TODO: the util should be improved
			// fastjson 似乎对复杂的继承关系的解析是不够的, 因此此处采用序列化
			ObjectInputUtil<ArrayList<Building>> oiu3 = new ObjectInputUtil<ArrayList<Building>>("buildings.ser",
					ObjectInputUtil.SERIALFORMAT);
			buildingList = FXCollections.<Building>observableArrayList();
			buildingList.addAll(oiu3.readSerialObject());
			ObjectInputUtil<Question> oiu4 = new ObjectInputUtil<Question>("questions.json", Question.class,
					ObjectInputUtil.JSONFORMAT);
			questionList = FXCollections.<Question>observableArrayList();
			questionList.addAll(oiu4.readJsonArrayList());
			ObjectInputUtil<Template> oiu5 = new ObjectInputUtil<Template>("templates.json", Template.class,
					ObjectInputUtil.JSONFORMAT);
			templateList = FXCollections.<Template>observableArrayList();
			templateList.addAll(oiu5.readJsonArrayList());
			// TODO: reunion patient with bed and reunion question and template in case of
			// bad thing
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (userList == null) {
				userList = FXCollections.<User>observableArrayList();
				userList.add(new User("Admin", "Admin", "Admin"));
				saveUserFile();
				System.out.println("本地没有用户信息");
			}
			
			if (patientList == null) {
				patientList = FXCollections.<Patient>observableArrayList();
				patientList.add(new Patient("test", "男", LocalDate.of(2002, 10, 30), "test", "test", "test", "test"));
				savePatientFile();
				System.out.println(("本地没有病人信息"));
			}
			if (buildingList == null || buildingList.size() == 0) {
				buildingList = FXCollections.observableArrayList();
				Building building = new Building("name", "building", "good");
				building.setSiteId("00");
				building.setLocation("name");
				Floor floor = new Floor("name", "floor", "good");
				floor.setLocation("name->name");
				building.addFloor(floor);
				buildingList.add(building);
				saveBuildingFile();
				System.out.println(("本地沒有楼宇信息"));
			}
			if (questionList == null || questionList.size() == 0) {
				questionList = FXCollections.observableArrayList();
				questionList.add(new Question("Are u ok?", "A", "no", "no", "no"));
				saveQuestionFile();
			}
			if (templateList == null || templateList.size() == 0) {
				templateList = FXCollections.observableArrayList();
				templateList.add(new Template("goodTemplate", "good"));
				saveTemplateFile();
			}

			reunionBed();
			reunionTemplate();
		}
	}

	private void reunionTemplate() {
		for(Template template : templateList) {
			if(template.isDeleted()==false) {
				for(Question question: template.getQuestionList()) {
					for(Question question1:questionList) {
						if(question1.isDeleted()==false) {
							if(question.equals(question1)) {
								question = question1;
								//drag the same reference to single object
							}
						}
					}
				}
			}
		}
		
	}

	private void reunionBed() {
		for (Patient patient : patientList) {
			System.out.println("here");
			if (patient.isDeleted() == false&&patient.getBed()!=null) {
				for (Bed bed : this.getAllBedList()) {
					System.out.println("here");
					if(patient.getBed().equals(bed)) {
						//TODO:solve the matter of reunion
						patient.setBed(bed);
						bed.setPatient(patient);
					}
				}
			}
		}
	}

	public void saveFile() {
		saveUserFile();
		savePatientFile();
		saveBuildingFile();
		saveQuestionFile();
		saveTemplateFile();
	}
	
	public void saveUserFile() {
		ObjectOutputUtil<ArrayList<User>> oou1 = new ObjectOutputUtil<ArrayList<User>>("users.json",
				ObjectOutputUtil.JSONFORMAT);
		try {
			oou1.writeJsonObject(new ArrayList<User>(userList));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void savePatientFile() {
		ObjectOutputUtil<ArrayList<Patient>> oou2 = new ObjectOutputUtil<ArrayList<Patient>>("patients.json",
				ObjectOutputUtil.JSONFORMAT);
		try {
			oou2.writeJsonObject(new ArrayList<Patient>(patientList));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveBuildingFile() {
		ObjectOutputUtil<ArrayList<Building>> oou3 = new ObjectOutputUtil<>("buildings.ser",
				ObjectOutputUtil.SERIALFORMAT);
		try {
			oou3.writeSerialObject(new ArrayList<Building>(buildingList));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveQuestionFile() {
		ObjectOutputUtil<ArrayList<Question>> oou4 = new ObjectOutputUtil<>("questions.json",
				ObjectOutputUtil.JSONFORMAT);
		try {
			oou4.writeJsonObject(new ArrayList<Question>(questionList));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveTemplateFile() {
		ObjectOutputUtil<ArrayList<Template>> oou5 = new ObjectOutputUtil<>("templates.json",
				ObjectOutputUtil.JSONFORMAT);
		try {
			oou5.writeJsonObject(new ArrayList<Template>(templateList));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 文件读写***************************************************************

	// 用户管理*******************************************************************
	public boolean addUser(User user) {
		if(userList.contains(user)) {
			return false;
		}
		return userList.add(user);
	}

	protected final ObservableList<User> getUserList() {
		ObservableList<User> resultList = FXCollections.observableArrayList();
		for (User user : userList) {
			if (user.isDeleted() == false) {
				resultList.add(user);
			}
		}
		return resultList;
	}

	public User getUserById(String id) {
		for (User user : userList) {
			if (user.getId().equals(id) && !user.isDeleted()) {
				return user;
			}
		}
		return null;
	}

	public ObservableList<User> getUserByPosition(String position) {
		ObservableList<User> resultList = FXCollections.observableArrayList();
		for (User user : userList) {
			if (user.isDeleted() == false && user.getPosition().equals(position)) {
				resultList.add(user);
			}
		}
		return resultList;
	}

	public ObservableList<User> searchUser(String info) {
		return this.searchUser(this.userList, info);
	}

	public ObservableList<User> searchUser(ObservableList<User> userList, String info) {
		ObservableList<User> resultList = FXCollections.<User>observableArrayList();

		for (User user : userList) {
			if (!user.isDeleted()) {
				if (user.getName().contains(info)) {
					resultList.add(user);
				}
				// TODO:to know what localDate is like
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

	public boolean updateUser(int index, User newUser) {
		return (userList.set(index, newUser) != null);
	}

	public boolean updateUser(User oldUser, User newUser) {
		if(userList.contains(oldUser)) {
		return this.updateUser(userList.indexOf(oldUser), newUser);
		}
		else {
			return false;
		}
	}
	// 用户管理***********************************************************
//病患管理*********************************************************

	public boolean addPatient(Patient patient) {
		if(patientList.contains(patient)){
			return false;
		}
		return patientList.add(patient);
	}

	public ObservableList<Patient> searchPatient(String info) {
		ObservableList<Patient> resultList = FXCollections.<Patient>observableArrayList();
		for (Patient patient : patientList) {
			if (!patient.isDeleted()) {
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

	// TODO: to be tested if it works for IDnumber
	public int getIndexOfPatient(Patient patient) {
		return patientList.indexOf(patient);
	}

	public boolean updatePatient(int index, Patient patient) {
		return (patientList.set(index, patient) != null);
	}

	public boolean updatePatient(Patient oldpatient, Patient newPatient) {
		if(!patientList.contains(oldpatient)) {
			return false;
		}
		return this.updatePatient(this.getIndexOfPatient(oldpatient), newPatient);
	}

	public boolean deletePatient(int index) {
		if (patientList.get(index) != null) {
			if (patientList.get(index).isDeleted() == false) {
				patientList.get(index).setDeleted(true);
				return true;
			}
		}
		return false;
	}

	public boolean deletePatient(Patient patient) {
		if (patient != null) {
			if (patient.isDeleted() == false) {
				patient.setDeleted(true);
				return true;
			}
		}
		return false;
	}

	public ObservableList<Patient> getPatientList() {
		ObservableList<Patient> validPaList = FXCollections.observableArrayList();
		for (Patient patient : patientList) {
			if (!patient.isDeleted()) {
				validPaList.add(patient);
			}
		}
		;
		return validPaList;
	}

	public ObservableList<Patient> getUnsettledPatientList() {
		ObservableList<Patient> unSettledPatientList = FXCollections.<Patient>observableArrayList();
		for (Patient patient : patientList) {
			if (!patient.isDeleted() && patient.getBed() == null) {
				unSettledPatientList.add(patient);
			}
		}
		return unSettledPatientList;
	}

	public int getIndexOfUser(User oldUser) {
		if (oldUser.isDeleted() == false) {
			return userList.indexOf(oldUser);
		} else {
			return -1;
		}
	}

//病患管理***************************************************************
//楼宇管理****************************************************************
	public boolean addBuilding(Building building) {
		if (buildingList.contains(building) || building == null) {
			return false;
		}
		boolean success = buildingList.add(building);
		if (success) {
			int index = buildingList.indexOf(building);
			String buildingIndex = (index < 10 ? "0" : "") + index;
			building.setSiteId(buildingIndex);
			building.setLocation(building.getName());
		}
		return success;
	}

	public ObservableList<Building> getBuildingList() {
		ObservableList<Building> resultList = FXCollections.observableArrayList();
		for (Building building : buildingList) {
			if (building.isDeleted() == false) {
				resultList.add(building);
			}
		}
		return resultList;
	}

	// it is not good practice, but more convient
	public boolean addFloor(Building building, Floor floor) {
		return building.addFloor(floor);
	}

	public boolean addRoom(Floor floor, Room room) {
		return floor.addRoom(room);
	}

	public boolean addBed(Ward ward, Bed bed) {
		return ward.addBed(bed);
	}

	public boolean deleteSite(Site site) {
		if (!site.isDeleted()) {
			site.setDeleted(true);
			return true;
		} else {
			return false;
		}
	}

	// 床位管理**************************************************************
	public ObservableList<Bed> getAllBedList() {
		ObservableList<Bed> allBedList = FXCollections.observableArrayList();
		for (Building building : buildingList) {
			for (Floor floor : building.oFloorList()) {
				for (Room room : floor.oRoomList()) {
					if (room.getType().equals("ward")) {
						for (Bed bed : ((Ward) room).oBedList()) {
							// deleted is all checked in model
							allBedList.add(bed);
						}
					}
				}
			}
		}
		return allBedList;
	}

	public boolean moveIn(Bed bed, Patient patient, LocalDate startDate, LocalDate endDate) {
		bed.setPatient(patient);
		bed.setStartDate(startDate);
		bed.setEndDate(endDate);
		patient.setBed(bed);
		return true;
	}

	public boolean moveOut(Bed bed, Patient patient) {
		bed.setPatient(null);
		bed.setStartDate(null);
		bed.setEndDate(null);
		patient.setBed(null);
		return true;
	}

	public boolean swap(Bed bed1, Bed bed2) {
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

	// 特殊房间管理*************************************
	public ObservableList<SpecialRoom> getSpecialRoomList() {
		ObservableList<SpecialRoom> resultList = FXCollections.observableArrayList();
		for (Building building : this.getBuildingList()) {
			for (Floor floor : building.oFloorList()) {
				for (Room room : floor.oRoomList()) {
					if (room.getType().equals("specialRoom")) {
						resultList.add((SpecialRoom) room);
					}
				}
			}
		}
		return resultList;
	}

	public Duration apply(SpecialRoom specialRoom, Person person, Duration duration) {
		return specialRoom.apply(person, duration);
	}

	public boolean addQuestion(Question question) {
		if (questionList.contains(question)) {
			return false;
		}
		boolean success = questionList.add(question);
		if (success) {
			int index = questionList.indexOf(question);
			String questionIndex = (index < 10 ? "0" : "") + index;
			question.setId(questionIndex);
		}
		return success;
	}

	public boolean deleteQuestion(Question question) {
		if (questionList.contains(question)) {
			question.setDeleted(true);
			return true;
		} else {
			return false;
		}
	}

	public ObservableList<Question> searchQuestion(String info) {
		ObservableList<Question> resultList = FXCollections.observableArrayList();
		for (Question question : questionList) {
			if (question.isDeleted() == false) {
				if (question.getStem().contains(info)) {
					resultList.add(question);
				} else if (question.getChoiceA().contains(info)) {
					resultList.add(question);
				} else if (question.getChoiceB().contains(info)) {
					resultList.add(question);
				} else if ((question.getChoiceC().contains(info))) {
					resultList.add(question);
				} else if (question.getId().contains(info)) {
					resultList.add(question);
				} else if (question.getForm().contains(info)) {
					resultList.add(question);
				}
			}
		}
		return resultList;
	}

	public ObservableList<Question> getQuestionList() {
		ObservableList<Question> resultList = FXCollections.observableArrayList();
		for (Question question : questionList) {
			if (question.isDeleted() == false) {
				resultList.add(question);
			}
		}
		return resultList;
	}

	public boolean updateQuestion(int index, Question newQuestion) {
		return questionList.set(index, newQuestion) != null;
	}

	public boolean updateQuestion(Question oldQuestion, Question newQuestion) {
		if (questionList.contains(oldQuestion)) {
			return this.updateQuestion(questionList.indexOf(oldQuestion), newQuestion);
		} else {
			return false;
		}

	}

	public boolean addTemplate(Template template) {
		if (this.templateList.contains(template)) {
			return false;
		} else {
			boolean success = templateList.add(template);
			if (success) {
				int index = templateList.indexOf(template);
				String templateIndex = (index < 10 ? "0" : "") + index;
				template.setId(templateIndex);
			}
			return success;
		}
	}

	public boolean deleteTemplate(Template template) {
		if (!templateList.contains(template)) {
			return false;
		} else {
			template.setDeleted(true);
			return true;
		}
	}

	public ObservableList<Template> searchTemplate(String info) {
		ObservableList<Template> resultList = FXCollections.observableArrayList();
		for (Template template : templateList) {
			if (template.isDeleted() == false) {
				if (template.getName().contains(info)) {
					resultList.add(template);
				} else if (template.getTemType().contains(info)) {

				} else if (template.getId().contains(info)) {
					resultList.add(template);
				}
			}
		}
		return resultList;
	}

	public boolean updateTemplate(int index, Template newTemplate) {
		return templateList.set(index, newTemplate) != null;
	}

	public boolean updateTemplate(Template oldTemplate, Template newTemplate) {
		if (!templateList.contains(oldTemplate)) {
			return false;
		} else {
			return this.updateTemplate(templateList.indexOf(oldTemplate), newTemplate);
		}
	}

	public boolean addQuestionToTem(Template template, Question question) {

		return template.addQuestion(question);
	}

	public boolean removeQuestionFormTem(Template template, Question question) {
		return template.removeQuestion(question);
	}

	public ObservableList<Template> getTemplateList() {
		ObservableList<Template> resultList = FXCollections.observableArrayList();
		for (Template template : templateList) {
			if (template.isDeleted() == false) {
				resultList.add(template);
			}
		}
		return resultList;
	}

    public ObservableList<SpecialRoom> searchSpecialRoom(String info) {
		ObservableList<SpecialRoom> resultList = FXCollections.observableArrayList();
		for (SpecialRoom specialRoom : this.getSpecialRoomList()) {
			if (specialRoom.isDeleted() == false) {
				if (specialRoom.getName().contains(info)) {
					resultList.add(specialRoom);
				} else if (specialRoom.getLocation().contains(info)) {

				} else if (specialRoom.getSiteId().contains(info)) {
					resultList.add(specialRoom);
				}else if (specialRoom.getDescription().contains(info)) {
					resultList.add(specialRoom);
				}else if (specialRoom.getType().contains(info)) {
					resultList.add(specialRoom);
				}

			}
		}
		return resultList;
    }

	public boolean deleteUser(User user) {
		if(!userList.contains(user)){
			return false;
		}
		else {
			user.setDeleted(true);
			return true;
		}

	}
}