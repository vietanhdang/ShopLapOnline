/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author Benjamin
 */
public class AbstractModel {

    int id;
    Date createdTime;
    Date updatedTime;

    public AbstractModel() {
    }

    public AbstractModel(int id, Date createdTime, Date updatedTime) {
        this.id = id;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public AbstractModel(int id, Date createdTime) {
        this.id = id;
        this.createdTime = createdTime;
    }

    public AbstractModel(int id) {
        this.id = id;
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
