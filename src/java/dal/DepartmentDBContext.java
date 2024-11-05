/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Department;
import model.Employee;

/**
 *
 * @author sonnt-local
 */
public class DepartmentDBContext extends DBContext<Department> {

    @Override
    public void insert(Department model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Department model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Department model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Department> list() {
        
        ArrayList<Department> depts = new ArrayList<>();
        PreparedStatement stm = null;
        try {
            String sql = "SELECT did,dname FROM Department";

            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Department d = new Department();
                d.setId(rs.getInt("did"));
                d.setName(rs.getString("dname"));
                depts.add(d);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return depts;
    }
    
     public String getDepartmentNameByPlanId(int plid) {
        String departmentName = null;
        String sql = "SELECT d.dname "
                + "FROM [Department] d "
                + "JOIN [Plan] p ON d.did = p.did "
                + "WHERE p.plid = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, plid);
            rs = stm.executeQuery();
            if (rs.next()) {
                departmentName = rs.getString("dname");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return departmentName;
    }

    public ArrayList<Department> get(String type) {
        ArrayList<Department> depts = new ArrayList<>();

        String sql = "SELECT TOP (1000) [did]\n"
                + "      ,[dname]\n"
                + "      ,[dtype]\n"
                + "  FROM [Department]\n"
                + "  WHERE dtype = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, type);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Department d = new Department();
                d.setId(rs.getInt("did"));
                d.setName(rs.getString("dname"));
                d.setType(rs.getString("dtype"));
                depts.add(d);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return depts;
    }

    @Override
    public Department get(int id) {
        Department department = null;
        String sql = "SELECT did, dname, dtype FROM Department WHERE did = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();

            if (rs.next()) {
                department = new Department();
                department.setId(rs.getInt("did"));
                department.setName(rs.getString("dname"));
                department.setType(rs.getString("dtype"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return department;
    }

    
    
}
