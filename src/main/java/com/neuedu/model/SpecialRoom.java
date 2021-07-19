package com.neuedu.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class SpecialRoom extends Room {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8226921976292600212L;
	private LinkedList<Person> applierQueue;
	private LinkedList<LocalDateTime> timerQueue;
	private int containNum = 3;
    public int getContainNum() {
		return containNum;
	}

	public void setContainNum(int containNum) {
		this.containNum = containNum;
	}

	public SpecialRoom(String name, String type, String description) {
        super(name, type, description);
        applierQueue = new LinkedList<Person>();
        timerQueue = new LinkedList<LocalDateTime>();
        
    }
	
	public SpecialRoom(String name, String type, String description,int num) {
        super(name, type, description);
        applierQueue = new LinkedList<Person>();
        timerQueue = new LinkedList<LocalDateTime>();
        this.containNum =num;
    }

    public Duration apply(Person person,Duration duration) {
    	LocalDateTime.now().plusSeconds(0);
    	applierQueue.add(person);
    	if(timerQueue.size()>this.containNum) {
    		LocalDateTime startTime = timerQueue.get(timerQueue.size()-containNum);
    		//拿到"前一个人"的结束时间
    		
    		LocalDateTime endTime = startTime.plus(duration);
    		timerQueue.add(endTime);
    		return Duration.between(startTime, endTime);
    	}
    	else {
    		timerQueue.add(LocalDateTime.now().plus(duration));
    		return duration;
    	}
		
    	
    	
    	
    }
}
