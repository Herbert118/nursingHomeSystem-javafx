package com.neuedu.model;


import java.io.Serializable;

/**
 * @author 刘海波
 * 地点类型的父类，主要是为了装入TreeTableView
 */
public abstract class Site implements Serializable {
    
	private static final long serialVersionUID = -6652372784397410631L;
	
	//WritableStringProperty 继承了 SimpleStringProperty , 是之可序列化
	private WritableStringProperty name;
	private WritableStringProperty siteId;
    private WritableStringProperty type;
    private WritableStringProperty description;
    private WritableStringProperty location;



    public String getLocation() {
        return location.get();
    }

    public WritableStringProperty locationProperty() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    private boolean deleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Site site = (Site) o;
        if (deleted != site.deleted) return false;
        if (name != null ? !name.get().equals(site.name.get()) : site.name != null) return false;
        return true;
    }
//that is just cool
    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        return result;
    }

    
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getType() {
        return type.get();
    }

    public WritableStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getName() {
        return name.get();
    }

    public WritableStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSiteId() {
        return siteId.get();
    }

    public WritableStringProperty siteIdProperty() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId.set(siteId);
    }

    public String getDescription() {
        return description.get();
    }

    public WritableStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public Site(String name, String type,String description){
        this.name = new WritableStringProperty(name);
        this.siteId = new WritableStringProperty("");
        this.type = new WritableStringProperty(type);
        this.description = new WritableStringProperty(description);
        this.location = new WritableStringProperty("");
        this.deleted = false;
    }
    protected Site(){
        this.name = new WritableStringProperty("");
        this.siteId = new WritableStringProperty("");
        this.type = new WritableStringProperty("");
        this.description = new WritableStringProperty("");
        this.location = new WritableStringProperty("");
        this.deleted = false;
    }

}
