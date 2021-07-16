package com.neuedu.model;

import java.util.ArrayList;

public class Building {
	ArrayList<Floor> floorList;
	
	public boolean addFloor(Floor floor) {
		return floorList.add(floor);
	}
	
	public boolean removeFloor(Floor floor) { 
		if(floorList.contains(floor)) {
			return floorList.remove(floor);
		}
		else {
			return false;
		}
		
	}
	
	public void getFloor() {
		
	}
	
	
	
	
	
	
}
