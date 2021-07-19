package com.neuedu.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Ward extends Room implements Serializable {
    private ArrayList<Bed> bedList;
    public Ward(String name, String type, String description) {
        super(name, type, description);
        bedList = new ArrayList<Bed>();
    }

    public ArrayList<Bed> getBedList() {
        return bedList;
    }

    protected void setBedList(ArrayList<Bed> bedList) {
        this.bedList = bedList;
    }

    public boolean addBed(Bed bed){
        if(bedList.contains(bed)||bed==null){
        	return false;
        }
            boolean success =  bedList.add(bed);
            int index = bedList.indexOf(bed);
            String bedIndex = index<10?"0":"";
            bed.setSiteId(this.getSiteId()+bedIndex);
            bed.setLocation(this.getLocation()+"->"+bed.getName());
            return success;
    }
    public boolean deleteBed(Bed bed){
        if(bedList.contains(bed)){
            if(bed.isDeleted()==false){
                bed.setDeleted(true);
            }
        }
        return false;
    }
}
