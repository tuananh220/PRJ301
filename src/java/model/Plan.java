/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Minh Duc
 */
public class Plan {

    private int plid;
    private Date startd;
    private Date endd;
    private Department department;

    private int totalQuantity;
    private int deliveredQuantity;
    private String status;

    private ArrayList<PlanCampain> campaigns = new ArrayList<>();

    public ArrayList<PlanCampain> getCampains() {
        return campaigns;
    }

    public void setCampains(ArrayList<PlanCampain> campains) {
        this.campaigns = campains;
    }

    public int getPlid() {
        return plid;
    }

    public void setPlid(int plid) {
        this.plid = plid;
    }

    public Date getStartd() {
        return startd;
    }

    public void setStartd(Date startd) {
        this.startd = startd;
    }

    public Date getEndd() {
        return endd;
    }

    public void setEndd(Date endd) {
        this.endd = endd;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getDeliveredQuantity() {
        return deliveredQuantity;
    }

    public void setDeliveredQuantity(int deliveredQuantity) {
        this.deliveredQuantity = deliveredQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void calculateStatus(ArrayList<ScheduleCampain> completedCampaigns) {
        boolean isComplete = true;

        for (PlanCampain planCampaign : campaigns) {
            int requiredQuantity = planCampaign.getQuantity();

            // Kiểm tra và lấy completedQuantity
            int completedQuantity = completedCampaigns.stream()
                    .filter(s -> s.getPlanCampain() != null && s.getPlanCampain().getId() == planCampaign.getId())
                    .mapToInt(ScheduleCampain::getQuantity)
                    .sum();

            if (completedQuantity < requiredQuantity) {
                isComplete = false;
                break;
            }
        }

        if (isComplete) {
            this.status = "Complete";
        } else {
            this.status = endd.before(new Date(System.currentTimeMillis())) ? "Late" : "On-going";
        }
    }

}
