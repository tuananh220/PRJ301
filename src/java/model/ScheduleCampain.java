/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.*;

public class ScheduleCampain {
    private int scid;
    private PlanCampain planCampain;
        private java.sql.Date date;
    private String shift;
    private int quantity;

    public ScheduleCampain() {
    }

    public ScheduleCampain(int scid, PlanCampain planCampain, Date date, String shift, int quantity) {
        this.scid = scid;
        this.planCampain = planCampain;
        this.date = date;
        this.shift = shift;
        this.quantity = quantity;
    }

    // Getters and setters
    public int getScid() {
        return scid;
    }

    public void setScid(int scid) {
        this.scid = scid;
    }

    public PlanCampain getPlanCampain() {
        return planCampain;
    }

    public void setPlanCampain(PlanCampain planCampain) {
        this.planCampain = planCampain;
    }

    

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
