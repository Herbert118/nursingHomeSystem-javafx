package com.neuedu.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;


public class Building extends Site implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 946323915649344774L;
	ArrayList<Floor> floorList;


	public Building(String name, String type, String description) {
		super(name, "building", description);
		floorList = new ArrayList<Floor>();
	}
	public Building() {
		super();
		floorList = new ArrayList<Floor>();
	}

	public boolean addFloor(Floor floor) {
		if(floor == null||floorList.contains(floor)) {
			return false;
		}
		boolean success = floorList.add(floor);
		
		if(success){
			int index = floorList.indexOf(floor);
			String floorIndex = (index<10?"0":"") +index;
			floor.setSiteId(this.getSiteId()+floorIndex);
			floor.setLocation(this.getLocation()+"->"+floor.getName());
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean deleteFloor(Floor floor) {
		if(floorList.contains(floor)) {
			if(floor.isDeleted()== false){
				floor.setDeleted(true);
				return true;
			}
		}
			return false;
	}

	public boolean deleteFloor(int index) {
		Floor floor = floorList.get(index);
		if(floor!= null) {
			if(floor.isDeleted()==false){
				floor.setDeleted(true);
			}
		}
			return false;

	}
	//I have no choice for fastjson
	public ObservableList<Floor> oFloorList(){
		ObservableList<Floor> resultList = FXCollections.observableArrayList();
		for(Floor floor:floorList) {
			if(floor.isDeleted()== false) {
				resultList.add(floor);
			}
		}
		return resultList;
	}
	public ArrayList<Floor> getFloorList() {
		return floorList;
	}
	public void setFloorList(ArrayList<Floor> floorList){
		this.floorList = floorList;
	}
}
