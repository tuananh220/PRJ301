/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.accesscontrol;

import dal.UserDBContext;
import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.accesscontrol.User;
import model.accesscontrol.Role;
import java.util.ArrayList;
import java.util.List;
import model.accesscontrol.Features;

/**
 *
 * @author Admin
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.html").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String user = request.getParameter("username");
        String pass = request.getParameter("password");
        UserDBContext db = new UserDBContext();
        User account = db.get(user, pass);

        if (account != null) {
            ArrayList<Role> roles = db.getRoles(user);
            account.setRoles(roles);
            request.getSession().setAttribute("account", account);

            // Ghi lại các vai trò của người dùng để kiểm tra
            for (Role role : roles) {
                System.out.println("User " + user + " has role: " + role.getName());
            }

            // Thu thập tất cả các tính năng từ vai trò của người dùng
            List<Features> features = roles.stream()
                    .flatMap(role -> role.getFeatures().stream())
                    .distinct()
                    .toList();

            // Đặt các tính năng vào request và chuyển hướng đến Menu.jsp
            request.setAttribute("features", features);
            response.sendRedirect(request.getContextPath() + "/View/Giamdoc/Menu.jsp");
        } else {
            response.getWriter().println("Login failed! Incorrect username or password.");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
