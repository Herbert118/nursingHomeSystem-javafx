package com.neuedu.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Floor extends Site{
    /**
	 * 
	 */
	private static final long serialVersionUID = 220354367315712780L;
	private ArrayList<Room> roomList;

    public Floor(String name, String type, String description) {
        super(name, "floor", description);
        roomList = new ArrayList<Room>();
    }
    protected void setRoomList(ArrayList<Room> roomList) {
        this.roomList = roomList;
    }
    public boolean addRoom(Room room){
        if(room == null||roomList.contains(room)){
            return false;
        }
        else {
            roomList.add(room);
            int index = roomList.indexOf(room);
            String roomIndex = (index<10?"0":"") +index;
            room.setSiteId(this.getSiteId()+roomIndex);
            room.setLocation(this.getLocation()+"->"+room.getName());
            return true;
        }
    }
    
    public void deleteRoom(Room room){
        if(roomList.contains(room)){
           if( room.isDeleted() == false){
               room.setDeleted(true);
           }
        }
    }

    public ObservableList<Room> getRoomList(){
        ObservableList<Room> resultList = FXCollections.observableArrayList();
        for(Room room : roomList){
            if(!room.isDeleted()){
                resultList.add(room);
            }
        }
        return resultList;
    }
}
