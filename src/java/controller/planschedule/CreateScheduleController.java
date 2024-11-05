package controller.planschedule;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import dal.ScheduleCampainDBContext;
import jakarta.servlet.ServletException;
import java.lang.System.Logger.Level;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import model.Plan;
import model.Product;
import java.util.logging.Logger;
import model.PlanCampain;



public class CreateScheduleController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CreateScheduleController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ScheduleCampainDBContext scheduleDB = new ScheduleCampainDBContext();
        if (request.getParameter("planId") == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing planId parameter.");
            return;
        }
        int planId = Integer.parseInt(request.getParameter("planId"));

        try {
            Plan plan = scheduleDB.getPlanWithProducts(planId);
            if (plan == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Plan not found with ID: " + planId);
                return;
            }
            Date startDate = plan.getStartd();
            Date endDate = plan.getEndd();
            ArrayList<Product> products = plan.getProducts();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            ArrayList<String> formattedDates = new ArrayList<>();

            for (long date = startDate.getTime(); date <= endDate.getTime(); date += 86400000L) {
                formattedDates.add(dateFormat.format(new java.sql.Date(date)));
            }

            if (products.isEmpty()) {
                logger.warning("No products found for Plan ID: " + planId);
            }
            logger.info("Plan ID: " + planId);

            // Set attributes to pass to JSP
            request.setAttribute("plan", plan);
            request.setAttribute("products", products);
            request.setAttribute("dates", formattedDates);
            request.getRequestDispatcher("/View/Schedule/schedulecreate.jsp").forward(request, response);
        } catch (Exception e) {
            logger.severe("Error retrieving plan with products: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Plan not found with ID: " + planId);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ScheduleCampainDBContext scheduleDB = new ScheduleCampainDBContext();
        int planId = Integer.parseInt(request.getParameter("planId"));

        try {
            // Retrieve all parameters from the request
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                // Check if the parameter is a quantity (format: quantity_{productID}_{shift}_{date})
                if (paramName.startsWith("quantity_")) {
                    String[] parts = paramName.split("_");
                    int productId = Integer.parseInt(parts[1]);
                    String shift = parts[2];
                    String dateStr = parts[3];
                    java.sql.Date date = java.sql.Date.valueOf(dateStr);
                    int quantity = 0;
                    try {
                        quantity = Integer.parseInt(request.getParameter(paramName));
                    } catch (NumberFormatException e) {
                        // Skip if the value is not a number
                        continue;
                    }

                    // Create PlanCampaign object directly
                    PlanCampain planCampaign = new PlanCampain();
                    planCampaign.setId(planId);
                    Product product = new Product();
                    product.setId(productId);
                    planCampaign.setProduct(product);

                    // Add schedule to the database
                    scheduleDB.createSchedule(planCampaign, date, shift, quantity);
                }
            }
            // Redirect to success page
            response.getWriter().print("Sucess");
        } catch (Exception e) {
            logger.severe("Error creating schedule: " + e.getMessage());
            response.getWriter().println("fail");
    }
}
}


