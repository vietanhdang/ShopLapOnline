
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Product;

/**
 * @author Benjamin
 */
public class ProductFilter {

    private String brandId;
    private int numOfAttributeFitler;
    private String specifiedAttribute;
    private String categoryId;
    private String sortBy;
    private boolean isAsc;
    private String search;
    private int currentPage;
    private int totalPage;
    private int priceFrom;
    private int priceTo;
    private int recordPerPage;
    private int statusId;
    private int totalRecord;

    public ProductFilter() {
        this.numOfAttributeFitler = 0;
        brandId = "";
        specifiedAttribute = "";
        categoryId = "";
        sortBy = "date";
        isAsc = false;
        search = "";
        currentPage = 1;
        totalPage = 0;
        recordPerPage = 12;
        statusId = 1;
        totalRecord = 0;
        this.priceFrom = 0;
        this.priceTo = 0;
    }

    public void setPriceFrom(int priceFrom) {
        this.priceFrom = priceFrom;
    }

    public void setPriceTo(int priceTo) {
        this.priceTo = priceTo;
    }

    public int getPriceFrom() {
        return priceFrom;
    }

    public int getPriceTo() {
        return priceTo;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        if (totalRecord % recordPerPage == 0) {
            return totalRecord / recordPerPage;
        } else {
            return totalRecord / recordPerPage + 1;
        }
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getRecordPerPage() {
        return recordPerPage;
    }

    public void setRecordPerPage(int recordPerPage) {
        this.recordPerPage = recordPerPage;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSearch() {
        return search;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public boolean isIsAsc() {
        return isAsc;
    }

    public void setIsAsc(boolean isAsc) {
        this.isAsc = isAsc;
    }

    public int getNumOfAttributeFitler() {
        return numOfAttributeFitler;
    }

    public void setNumOfAttributeFitler(int numOfAttributeFitler) {
        this.numOfAttributeFitler = numOfAttributeFitler;
    }

    public String getSpecifiedAttribute() {
        return specifiedAttribute;
    }

    public void setSpecifiedAttribute(String specifiedAttribute) {
        this.specifiedAttribute = specifiedAttribute;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

}
