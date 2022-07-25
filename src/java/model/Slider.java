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
public class Slider extends AbstractModel{
    private String title;
    private String description;
    private String imageUrl;
    private boolean status;
    private SlideColection sliderColection;
    private Product product;

    public Slider() {
    }

    public Slider(String title, String description, String imageUrl, boolean status, SlideColection sliderColection, Product product, int id, Date createdTime, Date updatedTime) {
        super(id, createdTime, updatedTime);
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.status = status;
        this.sliderColection = sliderColection;
        this.product = product;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public SlideColection getSliderColection() {
        return sliderColection;
    }

    public void setSliderColection(SlideColection sliderColection) {
        this.sliderColection = sliderColection;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
