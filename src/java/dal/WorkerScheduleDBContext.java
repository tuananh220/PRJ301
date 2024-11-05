package dal;

import model.WorkerSchedule;
import model.Employee;
import model.ScheduleCampain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkerScheduleDBContext extends DBContext<WorkerSchedule> {

    @Override
    public ArrayList<WorkerSchedule> list() {
        ArrayList<WorkerSchedule> schedules = new ArrayList<>();
        String sql = "SELECT * FROM WorkerSchedule "
                + "INNER JOIN Employee ON WorkerSchedule.eid = Employee.eid "
                + "INNER JOIN ScheduleCampaign ON WorkerSchedule.scid = ScheduleCampaign.scid";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                WorkerSchedule schedule = new WorkerSchedule();
                schedule.setWsid(rs.getInt("wsid"));
                // Set employee details
                Employee employee = new Employee();
                employee.setEid(rs.getString("eid"));
                employee.setEname(rs.getString("ename"));
                schedule.setEmployee(employee);
                // Set schedule campaign
                ScheduleCampain scheduleCampain = new ScheduleCampain();
                scheduleCampain.setScid(rs.getInt("scid"));
                schedule.setScheduleCampain(scheduleCampain);
                schedules.add(schedule);
            }
        } catch (SQLException ex) {
            Logger.getLogger(WorkerScheduleDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return schedules;
    }

    @Override
    public void insert(WorkerSchedule model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(WorkerSchedule model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(WorkerSchedule model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
public WorkerSchedule get(int id) {
    String sql = "SELECT w.wsid, w.scid, w.eid, w.quantity, "
            + "e.ename, e.salaryLevel,  "
            + "sc.shift, sc.date "
            + "FROM WorkerSchedule w "
            + "INNER JOIN Employee e ON w.eid = e.eid "
            + "INNER JOIN ScheduleCampaign sc ON w.scid = sc.scid "
            + "WHERE w.wsid = ?";
    try (PreparedStatement stm = connection.prepareStatement(sql)) {
        stm.setInt(1, id);
        try (ResultSet rs = stm.executeQuery()) {
            if (rs.next()) {
                WorkerSchedule schedule = new WorkerSchedule();
                schedule.setWsid(rs.getInt("wsid"));
                schedule.setQuantity(rs.getInt("quantity"));

                // Set employee details
                Employee employee = new Employee();
                employee.setEid(rs.getString("eid"));
                employee.setEname(rs.getString("ename"));
                employee.setSalaryLevel(rs.getString("salaryLevel"));
                schedule.setEmployee(employee);

                // Set schedule campaign details
                ScheduleCampain scheduleCampain = new ScheduleCampain();
                scheduleCampain.setScid(rs.getInt("scid"));
                scheduleCampain.setShift(rs.getString("shift"));
                scheduleCampain.setDate(rs.getDate("date"));
                schedule.setScheduleCampain(scheduleCampain);

                return schedule;
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(WorkerScheduleDBContext.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
}

}
