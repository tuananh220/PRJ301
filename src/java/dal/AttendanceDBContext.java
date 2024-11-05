package dal;

import model.Attendance;
import model.WorkerSchedule;
import model.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Employee;
import model.ScheduleCampain;

public class AttendanceDBContext extends DBContext<Attendance> {

    public ArrayList<Attendance> listWithProducts() {
        ArrayList<Attendance> attendances = new ArrayList<>();
        String sql = "SELECT Attendance.*, Product.pid, Product.pname, Employee.eid, Employee.ename, WorkerSchedule.quantity AS ordered_quantity, "
                + "ScheduleCampaign.date, ScheduleCampaign.shift "
                + "FROM Attendance "
                + "INNER JOIN WorkerSchedule ON Attendance.wsid = WorkerSchedule.wsid "
                + "INNER JOIN ScheduleCampaign ON WorkerSchedule.scid = ScheduleCampaign.scid "
                + "INNER JOIN PlanCampaign ON ScheduleCampaign.canid = PlanCampaign.canid "
                + "INNER JOIN Product ON PlanCampaign.pid = Product.pid "
                + "INNER JOIN Employee ON WorkerSchedule.eid = Employee.eid";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Attendance attendance = new Attendance();
                attendance.setAid(rs.getInt("aid"));
                attendance.setQuantity(rs.getInt("quantity"));
                attendance.setAlpha(rs.getFloat("alpha"));
                attendance.setNote(rs.getString("note"));

                WorkerSchedule workerSchedule = new WorkerSchedule();
                workerSchedule.setWsid(rs.getInt("wsid"));
                workerSchedule.setQuantity(rs.getInt("ordered_quantity"));
                attendance.setWorkerSchedule(workerSchedule);

                Product product = new Product();
                product.setId(rs.getInt("pid"));
                product.setName(rs.getString("pname"));
                attendance.setProduct(product);

                Employee employee = new Employee();
                employee.setEid(rs.getString("eid"));
                employee.setEname(rs.getString("ename"));
                workerSchedule.setEmployee(employee);

                ScheduleCampain schedule = new ScheduleCampain();
                schedule.setDate(rs.getDate("date"));
                schedule.setShift(rs.getString("shift"));
                workerSchedule.setScheduleCampain(schedule);

                attendances.add(attendance);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return attendances;
    }

    public ArrayList<Attendance> listWithProducts(Date selectedDate, String selectedShift) {
        ArrayList<Attendance> attendances = new ArrayList<>();
        String sql = "SELECT Attendance.*, Product.pid, Product.pname, Employee.eid, Employee.ename, WorkerSchedule.quantity AS ordered_quantity, "
                + "ScheduleCampaign.date, ScheduleCampaign.shift "
                + "FROM Attendance "
                + "INNER JOIN WorkerSchedule ON Attendance.wsid = WorkerSchedule.wsid "
                + "INNER JOIN ScheduleCampaign ON WorkerSchedule.scid = ScheduleCampaign.scid "
                + "INNER JOIN PlanCampaign ON ScheduleCampaign.canid = PlanCampaign.canid "
                + "INNER JOIN Product ON PlanCampaign.pid = Product.pid "
                + "INNER JOIN Employee ON WorkerSchedule.eid = Employee.eid "
                + "WHERE ScheduleCampaign.date = ? AND ScheduleCampaign.shift = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setDate(1, (java.sql.Date) selectedDate);
            stm.setString(2, selectedShift);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Attendance attendance = new Attendance();
                attendance.setAid(rs.getInt("aid"));
                attendance.setQuantity(rs.getInt("quantity"));
                attendance.setAlpha(rs.getFloat("alpha"));
                attendance.setNote(rs.getString("note"));

                WorkerSchedule workerSchedule = new WorkerSchedule();
                workerSchedule.setWsid(rs.getInt("wsid"));
                workerSchedule.setQuantity(rs.getInt("ordered_quantity"));
                attendance.setWorkerSchedule(workerSchedule);

                Product product = new Product();
                product.setId(rs.getInt("pid"));
                product.setName(rs.getString("pname"));
                attendance.setProduct(product);

                Employee employee = new Employee();
                employee.setEid(rs.getString("eid"));
                employee.setEname(rs.getString("ename"));
                workerSchedule.setEmployee(employee);

                ScheduleCampain schedule = new ScheduleCampain();
                schedule.setDate(rs.getDate("date"));
                schedule.setShift(rs.getString("shift"));
                workerSchedule.setScheduleCampain(schedule);

                attendances.add(attendance);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return attendances;
    }

    @Override
    public void insert(Attendance model) {
        String sql = "INSERT INTO Attendance (quantity, note, date) VALUES (?, ?, ?)";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, model.getQuantity());
            stm.setString(2, model.getNote());
            stm.setDate(3, (java.sql.Date) model.getDate());
            
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Attendance model) {
        String sql = "UPDATE Attendance SET note = ? WHERE aid = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, model.getNote());
            stm.setInt(2, model.getAid());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Attendance model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Attendance get(int id) {
        String sql = "SELECT Attendance.*, Product.pid, Product.pname, Employee.eid, Employee.ename, WorkerSchedule.quantity AS ordered_quantity, "
                + "ScheduleCampaign.date, ScheduleCampaign.shift "
                + "FROM Attendance "
                + "LEFT JOIN WorkerSchedule ON Attendance.wsid = WorkerSchedule.wsid "
                + "LEFT JOIN ScheduleCampaign ON WorkerSchedule.scid = ScheduleCampaign.scid "
                + "LEFT JOIN PlanCampaign ON ScheduleCampaign.canid = PlanCampaign.canid "
                + "LEFT JOIN Product ON PlanCampaign.pid = Product.pid "
                + "LEFT JOIN Employee ON WorkerSchedule.eid = Employee.eid "
                + "WHERE aid = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Attendance attendance = new Attendance();
                attendance.setAid(rs.getInt("aid"));
                attendance.setQuantity(rs.getInt("quantity"));
                attendance.setAlpha(rs.getFloat("alpha"));
                attendance.setNote(rs.getString("note"));

                WorkerSchedule workerSchedule = new WorkerSchedule();
                workerSchedule.setWsid(rs.getInt("wsid"));
                workerSchedule.setQuantity(rs.getInt("ordered_quantity"));
                attendance.setWorkerSchedule(workerSchedule);

                Product product = new Product();
                product.setId(rs.getInt("pid"));
                product.setName(rs.getString("pname"));
                attendance.setProduct(product);

                Employee employee = new Employee();
                employee.setEid(rs.getString("eid"));
                employee.setEname(rs.getString("ename"));
                workerSchedule.setEmployee(employee);

                ScheduleCampain schedule = new ScheduleCampain();
                schedule.setDate(rs.getDate("date"));
                schedule.setShift(rs.getString("shift"));
                workerSchedule.setScheduleCampain(schedule);

                return attendance;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Attendance> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}



