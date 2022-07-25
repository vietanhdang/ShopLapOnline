/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;

/**
 *
 * @author vietd
 * @param <T>
 */
public class ProductList {

    private int totalProduct;
    private List<Product> products;

    public ProductList() {
    }

    public ProductList(int totalProduct, List<Product> products) {
        this.totalProduct = totalProduct;
        this.products = products;
    }

    public int getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(int totalProduct) {
        this.totalProduct = totalProduct;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
