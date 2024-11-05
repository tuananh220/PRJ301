/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package dal;

import model.Attendance;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AttendanceDBContext extends DBContext<Attendance> {

    public void updateNote(Attendance attendance) {
        String sql = "UPDATE Attendance SET note = ? WHERE wsid = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, attendance.getNote());
            stm.setInt(2, attendance.getWorkerSchedule().getWsid());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void insert(Attendance model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Attendance model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(Attendance model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Attendance get(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Attendance> list() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

