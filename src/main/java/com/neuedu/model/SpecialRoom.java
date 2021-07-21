package com.neuedu.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class SpecialRoom extends Room {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8226921976292600212L;
	//private LinkedList<Person> applierQueue;
	//TODO:do something to avoid this happen again
	private LinkedList<LocalDateTime> timerQueue;
	private int containNum;
    public int getContainNum() {
		return containNum;
	}

	public void setContainNum(int containNum) {
		this.containNum =containNum;
	}

	public SpecialRoom(String name, String type, String description) {
        super(name, type, description);
      //  applierQueue = new LinkedList<Person>();
        timerQueue = new LinkedList<LocalDateTime>();
        containNum = 3;
    }
	
	public SpecialRoom(String name, String type, String description,int num) {
        super(name, type, description);
        //applierQueue = new LinkedList<Person>();
        timerQueue = new LinkedList<LocalDateTime>();
        containNum = num;
    }
	
	protected SpecialRoom() {
		super();
		//applierQueue = new LinkedList<Person>();
        timerQueue = new LinkedList<LocalDateTime>();
        containNum = 3;
	}

    public Duration apply(Person person,Duration duration) {
    	//applierQueue.add(person);
    	//it has problem 
    	//TODO:fix the problem
    	if(timerQueue.size()>this.containNum) {
    		LocalDateTime startTime = timerQueue.get(timerQueue.size()-containNum);
    		//拿到"前一个人"的结束时间
    		
    		LocalDateTime endTime = startTime.plus(duration);
    		timerQueue.add(endTime);
    		return Duration.between(LocalDateTime.now(),startTime );
    	}
    	else {
    		timerQueue.add(LocalDateTime.now().plus(duration));
    		return duration;
    	}
		
    	
    	
    	
    }
}
