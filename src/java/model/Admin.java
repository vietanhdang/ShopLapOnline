/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author vietd
 */
public class Admin extends AbstractModel {
    private String fullname;
    private String address;
    private String phone;
    private String image;
    private boolean gender;
    private Date dateOfBirth;
    private Account account;

    public Admin() {
    }

    public Admin(String fullname, String address, String phone, boolean gender, Date dateOfBirth, Account account) {
        this.fullname = fullname;
        this.address = address;
        this.phone = phone;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.account = account;
    }

    public Admin(String fullname, String address, String phone, String image, boolean gender, Date dateOfBirth, Account account, int id, Date createdTime, Date updatedTime) {
        super(id, createdTime, updatedTime);
        this.fullname = fullname;
        this.address = address;
        this.phone = phone;
        this.image = image;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.account = account;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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