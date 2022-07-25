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
public class SpecifiedAttributeValue extends AbstractModel {

    private SpecifiedAttribute specifiedAttribute;
    private String name;
    private int totalProduct;

    public SpecifiedAttribute getSpecifiedAttribute() {
        return specifiedAttribute;
    }

    public SpecifiedAttributeValue(SpecifiedAttribute specifiedAttribute, String name, int id) {
        this.specifiedAttribute = specifiedAttribute;
        this.name = name;
        this.id = id;
    }

    public SpecifiedAttributeValue() {

    }

    public SpecifiedAttributeValue(int id, String name, int totalProduct) {
        super(id);
        this.name = name;
        this.totalProduct = totalProduct;
    }

    public void setSpecifiedAttribute(SpecifiedAttribute specifiedAttribute) {
        this.specifiedAttribute = specifiedAttribute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(int totalProduct) {
        this.totalProduct = totalProduct;
    }

}
