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
public class Brand extends AbstractModel {

    private String brandName;
    private String description;
    private String image;
    private int totalProduct;

    public Brand() {
        this.description = "";
        this.image = "";
    }

    public Brand(int id) {
        super(id);
    }

    public void setTotalProduct(int totalProduct) {
        this.totalProduct = totalProduct;
    }

    public int getTotalProduct() {
        return totalProduct;
    }

    public Brand(String brandName, String description, String image, int id) {
        super(id);
        this.brandName = brandName;
        this.description = description;
        this.image = image;
    }

    
    public Brand(String brandName, String description, String image) {
        this.brandName = brandName;
        this.description = description;
        this.image = image;
    }

    public Brand(String brandName, String description, String image, int id, Date createdTime, Date updatedTime) {
        super(id, createdTime, updatedTime);
        this.brandName = brandName;
        this.description = description;
        this.image = image;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null) {
            this.description = "";
        } else {
            this.description = description;
        }

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        if (image == null) {
            this.image = "";
        } else {
            this.image = image;
        }
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
