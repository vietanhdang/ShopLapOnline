/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Admin
 */
public class OrderFilter {
    private int currentPage = 1;
    private int totalPage = 1;
    private int totalOrder = 0;
    private int orderPerPage = 20;
    private String search = "";
    private String sortBy = "HistoryTime";
    private boolean isAsc = false;
    private int orderStatus1 = 0;
    private int orderStatus2 = 0;
    private int orderStatus3 = 0;
    private int orderStatus4 = 0;
    private int orderStatus5 = 0;
    private boolean [] selectStatus = {false, false, false, false, false};
    
    public static final String[] SORT_OPTION = {"HistoryTime", "OrderDate", "Order_ID", "Fullname", "TotalTransaction"};

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(int totalOrder) {
        this.totalOrder = totalOrder;
    }

    public int getOrderPerPage() {
        return orderPerPage;
    }

    public void setOrderPerPage(int orderPerPage) {
        this.orderPerPage = orderPerPage;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSortBy() {
        return sortBy;
    }

    public boolean setSortBy(String sortBy) {
        for (String s : SORT_OPTION) {
            if (sortBy.equals(s)) {
                this.sortBy = sortBy;
                return true;
            }
        }
        return false;
    }

    public boolean isIsAsc() {
        return isAsc;
    }

    public void setIsAsc(boolean isAsc) {
        this.isAsc = isAsc;
    }

    public int getOrderStatus1() {
        return orderStatus1;
    }

    public void setOrderStatus1(int orderStatus1) {
        this.orderStatus1 = orderStatus1;
    }

    public int getOrderStatus2() {
        return orderStatus2;
    }

    public void setOrderStatus2(int orderStatus2) {
        this.orderStatus2 = orderStatus2;
    }

    public int getOrderStatus3() {
        return orderStatus3;
    }

    public void setOrderStatus3(int orderStatus3) {
        this.orderStatus3 = orderStatus3;
    }

    public int getOrderStatus4() {
        return orderStatus4;
    }

    public void setOrderStatus4(int orderStatus4) {
        this.orderStatus4 = orderStatus4;
    }

    public int getOrderStatus5() {
        return orderStatus5;
    }

    public void setOrderStatus5(int orderStatus5) {
        this.orderStatus5 = orderStatus5;
    }
    
    public void addStatusNum(int type, int add) {
        switch(type) {
            case 1:
                this.orderStatus1 += add;
                break;
            case 2:
                this.orderStatus2 += add;
                break;
            case 3:
                this.orderStatus3 += add;
                break;
            case 4:
                this.orderStatus4 += add;
                break;
            case 5:
                this.orderStatus5 += add;
                break;
            default:
                break;
        }
    }

    public boolean[] getSelectStatus() {
        return selectStatus;
    }

    public void setSelectStatus(boolean[] selectStatus) {
        this.selectStatus = selectStatus;
    }
}
