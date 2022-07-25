/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Benjamin
 */
public class ProductDetails {

    private StringBuilder description;
    private List<String> images;
    private List<Comment> comments;
    private Product product;

    public ProductDetails() {
        this.images = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public List<String> getImages() {
        return images;
    }

    public String getImagesToDB() {
        StringBuilder sb = new StringBuilder();
        if (images != null && !images.isEmpty()) {
            images.forEach((image) -> {
                sb.append(image).append(" @@@ ");
            });
            return sb.substring(0, sb.length() - 5);
        }
        return "";
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public StringBuilder getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.isEmpty()) {
            this.description = new StringBuilder("Hiện chưa có thông tin chi tiết sản phẩm");
        } else {
            this.description = new StringBuilder(description);
        }

    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
