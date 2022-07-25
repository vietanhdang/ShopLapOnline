/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author vietd
 */
public class Role {

    // id = 1 => Admin
    // id = 2 => Sales
    // id = 3 => Customer
    private int roleID;
    private String name;

    public Role() {
    }

    public Role(int roleID) {
        this.roleID = roleID;
    }

    public Role(int roleID, String name) {
        this.roleID = roleID;
        this.name = name;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
