/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.WorkerSchedule;
import model.Employee;
import model.ScheduleCampain;
import model.PlanCampain;
import model.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkerScheduleDBContext extends DBContext<WorkerSchedule> {

    public List<WorkerSchedule> listByWorkshopAndShift(String workshopNo, String shiftNo) {
        List<WorkerSchedule> workerSchedules = new ArrayList<>();
        String sql = """
                     SELECT ws.wsid, e.eid, e.ename, sc.scid, pc.pid as planCampaignId, p.pid as productId, p.pname as productName, 
                                          sc.shift, ws.quantity, a.alpha
                                          FROM WorkerSchedule ws 
                                          JOIN Employee e ON ws.eid = e.eid 
                                          JOIN ScheduleCampaign sc ON ws.scid = sc.scid 
                                          JOIN PlanCampaign pc ON sc.canid = pc.canid 
                                          JOIN Product p ON pc.pid = p.pid
                     \t\t\t\t\t JOIN Attendance a ON a.wsid = ws.wsid
                     \t\t\t\t\t Where ws.wsid = ? AND sc.scid = ?""";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, workshopNo);
            stm.setString(2, shiftNo);  
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                WorkerSchedule workerSchedule = new WorkerSchedule();
                workerSchedule.setWsid(rs.getInt("wsid"));

                Employee employee = new Employee();
                employee.setEid(rs.getString("eid"));
                employee.setEname(rs.getString("ename"));
                workerSchedule.setEmployee(employee);

                ScheduleCampain scheduleCampain = new ScheduleCampain();
                scheduleCampain.setScid(rs.getInt("scid"));

                PlanCampain planCampain = new PlanCampain();
                planCampain.setId(rs.getInt("planCampaignId"));

                Product product = new Product();
                product.setId(rs.getInt("productId"));
                product.setName(rs.getString("productName"));

                planCampain.setProduct(product);
                scheduleCampain.setPlanCampain(planCampain);
                workerSchedule.setScheduleCampain(scheduleCampain);

                workerSchedule.setQuantity(rs.getInt("quantity"));
                workerSchedule.setAlpha(rs.getFloat("alpha"));

                workerSchedules.add(workerSchedule);
            }
        } catch (SQLException ex) {
            Logger.getLogger(WorkerScheduleDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(WorkerScheduleDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return workerSchedules;
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<WorkerSchedule> list() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

