/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */

import java.sql.Date;
import java.util.ArrayList;


public class Plan {
    private int id;
    private Department dept;
    private Date start;
    private Date end;
    
    private ArrayList<PlanCampain> campains = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public ArrayList<PlanCampain> getCampains() {
        return campains;
    }

    public void setCampains(ArrayList<PlanCampain> campains) {
        this.campains = campains;
    }
    
}
