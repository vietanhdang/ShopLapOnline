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
public class Account extends AbstractModel {

    private String username;
    private String password;
    private Role role;
    private boolean status;
    private String validator;
    private String cookie;
    private String note;

    public Account() {
    }

    public Account(String username, String password, Role role, boolean status, String validator, int id, Date createdTime) {
        super(id, createdTime);
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
        this.validator = validator;
    }

    public Account(String username, String password, Role role, boolean status, String validator, int id, Date createdTime, Date updatedTime) {
        super(id, createdTime, updatedTime);
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
        this.validator = validator;
    }

    public Account(String username, String password, Role role, boolean status) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getCookie() {
        return cookie;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getValidator() {
        return validator;
    }

    public void setValidator(String validator) {
        this.validator = validator;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Date getCreatedTime() {
        return createdTime;
    }

    @Override
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public Date getUpdatedTime() {
        return updatedTime;
    }

    @Override
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

}
