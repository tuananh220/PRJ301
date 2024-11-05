package dal;

import model.Employee;
import model.Department;
import model.accesscontrol.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeDBContext extends DBContext<Employee> {
    
    public int getDepartmentIdByUsername(String username) {
        int did = -1; // Default value if not found
        try {
            String sql = "SELECT [did]\n"
                    + "  FROM [dbo].[Employee] e\n"
                    + "  Join User_Employee us on e.eid = us.eid\n"
                    + "  Join [User] u on us.username = u.username\n"
                    + "  where u.username = ? ";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                did = rs.getInt("did");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return did;
    }

    // Method to list employees based on logged user's department ID
    public ArrayList<Employee> listByLoggedUser(User loggeduser) {
        ArrayList<Employee> employees = new ArrayList<>();
        int did = getDepartmentIdByUsername(loggeduser.getUsername()); // Get department ID

        if (did == -1) {
            // Department ID not found, return empty list
            return employees;
        }

        try {
            String sql = "SELECT eid, ename, salaryLevel FROM Employee WHERE did = ? and eid LIKE 'CN%'";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, did);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEid(rs.getString("eid"));
                employee.setEname(rs.getString("ename"));
                employee.setSalaryLevel(rs.getString("salaryLevel"));
                employees.add(employee);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return employees;
    }


    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT eid, ename, salaryLevel, did, createdby FROM Employee";
        try (PreparedStatement stm = connection.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEid(rs.getString("eid"));
                employee.setEname(rs.getString("ename"));
                employee.setSalaryLevel(rs.getString("salaryLevel"));
                employee.setDid(rs.getInt("did"));
                employee.setCreatedby(rs.getString("createdby"));
                employees.add(employee);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return employees;
    }

    @Override
    public void insert(Employee employee) {
        String sql = "INSERT INTO Employee (eid, ename, salaryLevel, did, createdby) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, employee.getEid());
            stm.setString(2, employee.getEname());
            stm.setString(3, employee.getSalaryLevel());
            stm.setInt(4, employee.getDid());
            stm.setString(5, employee.getCreatedby());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Employee employee) {
        String sql = "UPDATE Employee SET ename = ?, salaryLevel = ?, did = ?, createdby = ? WHERE eid = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, employee.getEname());
            stm.setString(2, employee.getSalaryLevel());
            stm.setInt(3, employee.getDid());
            stm.setString(4, employee.getCreatedby());
            stm.setString(5, employee.getEid());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Employee employee) {
        String sql = "DELETE FROM Employee WHERE eid = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, employee.getEid());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Employee get(int id) {
        String sql = "SELECT eid, ename, salaryLevel, did, createdby FROM Employee WHERE eid = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    Employee employee = new Employee();
                    employee.setEid(rs.getString("eid"));
                    employee.setEname(rs.getString("ename"));
                    employee.setSalaryLevel(rs.getString("salaryLevel"));
                    employee.setDid(rs.getInt("did"));
                    employee.setCreatedby(rs.getString("createdby"));
                    return employee;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Employee> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}
