package controller.manager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.accesscontrol.User;
import model.accesscontrol.Features;
import dal.UserDBContext;  

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class GiamdocController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.html");
            return;
        }

        try {
            
            List<Features> features = user.getRoles().stream()
                    .flatMap(role -> role.getFeatures().stream())
                    .distinct()
                    .collect(Collectors.toList());

            
            request.setAttribute("features", features);

            
            request.getRequestDispatcher("/View/Giamdoc/Menu.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Có lỗi xảy ra khi xử lý yêu cầu của bạn.");
        }
    }
}
