package controller.attendance;

import dal.AttendanceDBContext;
import dal.WorkerScheduleDBContext;
import model.Attendance;
import model.WorkerSchedule;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class AttendanceController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String workshopNo = request.getParameter("workshopNo");
        String shiftNo = request.getParameter("shiftNo");

        WorkerScheduleDBContext workerScheduleDB = new WorkerScheduleDBContext();
        List<WorkerSchedule> workerSchedules = workerScheduleDB.listByWorkshopAndShift(workshopNo, shiftNo);

        request.setAttribute("workerSchedules", workerSchedules);
        request.setAttribute("workshopNo", workshopNo);
        request.setAttribute("shiftNo", shiftNo);

        request.getRequestDispatcher("/View/Attendance/attendanceDetail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] workerScheduleIds = request.getParameterValues("wsid");
        AttendanceDBContext attendanceDB = new AttendanceDBContext();

        for (String wsid : workerScheduleIds) {
            String note = request.getParameter("note" + wsid);

            Attendance attendance = new Attendance();
            WorkerSchedule workerSchedule = new WorkerSchedule();
            workerSchedule.setWsid(Integer.parseInt(wsid));
            attendance.setWorkerSchedule(workerSchedule);
            attendance.setNote(note);

            attendanceDB.updateNote(attendance);
        }

        response.sendRedirect(request.getContextPath() + "/attendance?workshopNo=" +
                request.getParameter("workshopNo") + "&shiftNo=" + request.getParameter("shiftNo"));
    }
}
