/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


public class WorkerSchedule {

    private int wsid;
    private ScheduleCampain scheduleCampain;
    private Employee employee;
    private int quantity;
    private float Alpha;

    public float getAlpha() {
        return Alpha;
    }

    public void setAlpha(float Alpha) {
        this.Alpha = Alpha;
    }


    public int getWsid() {
        return wsid;
    }

    public void setWsid(int wsid) {
        this.wsid = wsid;
    }

    public ScheduleCampain getScheduleCampain() {
        return scheduleCampain;
    }

    public void setScheduleCampain(ScheduleCampain scheduleCampain) {
        this.scheduleCampain = scheduleCampain;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
