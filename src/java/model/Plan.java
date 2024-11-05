/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.*;
import java.util.ArrayList;

public class Plan {
    private int plid;
    private java.sql.Date startd;
    private java.sql.Date endd;
    private ArrayList<Product> products;
    private String status;
    private Department department;
    private ArrayList<PlanCampain> campains = new ArrayList<>();

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public ArrayList<PlanCampain> getCampains() {
        return campains;
    }

    public void setCampains(ArrayList<PlanCampain> campains) {
        this.campains = campains;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getters and setters
    public int getPlid() {
        return plid;
    }

    public void setPlid(int plid) {
        this.plid = plid;
    }

    public java.sql.Date getStartd() {
        return startd;
    }

    public void setStartd(java.sql.Date startd) {
        this.startd = startd;
    }

    public java.sql.Date getEndd() {
        return endd;
    }

    public void setEndd(java.sql.Date endd) {
        this.endd = endd;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<String> getDates() {
        ArrayList<String> dates = new ArrayList<>();
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(startd);
        while (!calendar.getTime().after(endd)) {
            dates.add(new java.text.SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
            calendar.add(java.util.Calendar.DATE, 1);
        }
        return dates;
    }
}
