package controller.employee;

import dal.EmployeeDBContext;
import model.Employee;
import controller.accesscontrol.BaseRBACController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.accesscontrol.User;

public class EmployeeListController extends BaseRBACController {
    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        EmployeeDBContext dbContext = new EmployeeDBContext();
        List<Employee> employees = dbContext.getAllEmployees();
        req.setAttribute("employees", employees);
        req.getRequestDispatcher("../View/Employee/list.jsp").forward(req, resp);
        
    }
    

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        processRequest(req, resp, loggeduser);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        processRequest(req, resp, loggeduser);
    }
}
