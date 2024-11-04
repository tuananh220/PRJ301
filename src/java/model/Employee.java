package model;

public class Employee {
    private String eid;
    private String ename;
    private String salaryLevel;
    private int did;
    private String createdby;

    public Employee() {
    }

    // Constructor
    public Employee(String eid, String ename, String salaryLevel, int did, String createdby) {
        this.eid = eid;
        this.ename = ename;
        this.salaryLevel = salaryLevel;
        this.did = did;
        this.createdby = createdby;
    }

    // Getters and Setters
    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getSalaryLevel() {
        return salaryLevel;
    }

    public void setSalaryLevel(String salaryLevel) {
        this.salaryLevel = salaryLevel;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "eid='" + eid + '\'' +
                ", ename='" + ename + '\'' +
                ", salaryLevel='" + salaryLevel + '\'' +
                ", did=" + did +
                ", createdby='" + createdby + '\'' +
                '}';
    }
}
