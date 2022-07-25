/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author hoang
 */
public class Turnover {
    private int month;
    private int year;
    private long revenue;

    public Turnover() {
    }

    public Turnover(int month, int year, long revenue) {
        this.month = month;
        this.year = year;
        this.revenue = revenue;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    @Override
    public String toString() {
        return "Turnover{" + "month=" + month + ", year=" + year + ", revenue=" + revenue + '}';
    }
    
    
}
