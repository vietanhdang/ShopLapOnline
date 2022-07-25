/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author hoang
 */
public class SlideColection extends AbstractModel{
    private String sliderColectionName;
    private Admin admin;
    private boolean status;

    public SlideColection() {
    }

    public SlideColection(String sliderColectionName, Admin admin, boolean status, int id, Date createdTime, Date updatedTime) {
        super(id, createdTime, updatedTime);
        this.sliderColectionName = sliderColectionName;
        this.admin = admin;
        this.status = status;
    }
    

    public String getSliderColectionName() {
        return sliderColectionName;
    }

    public void setSliderColectionName(String sliderColectionName) {
        this.sliderColectionName = sliderColectionName;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
    
}
