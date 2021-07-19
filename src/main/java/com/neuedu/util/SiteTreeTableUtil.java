package com.neuedu.util;

import com.neuedu.model.Bed;
import com.neuedu.model.Building;
import com.neuedu.model.Floor;
import com.neuedu.model.Room;
import com.neuedu.model.Site;
import com.neuedu.model.Ward;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class SiteTreeTableUtil {
	public static TreeItem<Site> parseRootItem(ObservableList<? extends Site> buildingList) {
		Building theBuilding = new Building("东软颐养中心","","好地方!");
		theBuilding.setType("center");
		TreeItem<Site> rootItem = new TreeItem<Site>(theBuilding);
		
		for (Site building : buildingList) {
			TreeItem<Site> buildingItem = new TreeItem<Site>(building);
			rootItem.getChildren().add(buildingItem);
			
			if (building.getType().equals("building")) {
				
				for (Floor floor : ((Building) building).getFloorList()) {
					TreeItem<Site> floorItem = new TreeItem<Site>(floor);
					buildingItem.getChildren().add(floorItem);
					
					for(Room room: floor.getRoomList()) {
						TreeItem<Site> roomItem = new TreeItem<Site>(room);
						floorItem.getChildren().add(roomItem);
						
						if(room.getType().equals("ward")) {
							for(Bed bed:((Ward)room).getBedList()) {
								TreeItem<Site> bedItem = new TreeItem<Site>(bed);
								roomItem.getChildren().add(bedItem);
							}
						}
					}
				}
			}
			else {
				throw new IllegalArgumentException("wrong type!");
			}
		}
		
		return rootItem;
	}
}
