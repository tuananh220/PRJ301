package controller.attendance;

import dal.AttendanceDBContext;
import dal.WorkerScheduleDBContext;
import model.Attendance;
import model.WorkerSchedule;
import dal.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import model.ScheduleCampain;

public class AttendanceController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         AttendanceDBContext attendanceDB = new AttendanceDBContext();
        String selectedDate = request.getParameter("date");
        String selectedShift = request.getParameter("shift");
        ArrayList<Attendance> attendances;

        if (selectedDate != null && selectedShift != null) {
            attendances = attendanceDB.listWithProducts(java.sql.Date.valueOf(selectedDate), selectedShift);
        } else {
            attendances = attendanceDB.listWithProducts();
        }

        // Fetch available dates and shifts from ScheduleCampaign
        ScheduleCampainDBContext scheduleCampaignDB = new ScheduleCampainDBContext();
        ArrayList<ScheduleCampain> scheduleCampaigns = scheduleCampaignDB.list();

        // Set attributes for JSP
        request.setAttribute("attendances", attendances);
        request.setAttribute("scheduleCampaigns", scheduleCampaigns);

        // Forward to JSP
        request.getRequestDispatcher("/View/Attendance/attendanceDetail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AttendanceDBContext attendanceDB = new AttendanceDBContext();

        // Lấy danh sách attendance để cập nhật note
        String[] aidList = request.getParameterValues("aid");
        if (aidList != null) {
            for (String aidStr : aidList) {
                int aid = Integer.parseInt(aidStr);
                String note = request.getParameter("note_" + aid);

                Attendance attendance = attendanceDB.get(aid);
                if (attendance != null) {
                    attendance.setNote(note);
                    attendanceDB.update(attendance);
                }
            }
        }

        response.sendRedirect("list");
    }
}
