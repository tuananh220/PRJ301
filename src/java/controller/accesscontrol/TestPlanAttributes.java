package controller.accesscontrol;
import java.sql.SQLException;
import java.util.ArrayList;
import dal.ScheduleCampainDBContext;
import java.util.Calendar;
import java.util.Date;
import model.Plan;
import model.Product;

public class TestPlanAttributes {
    public static void main(String[] args) {
         
        ScheduleCampainDBContext scheduleDB = new ScheduleCampainDBContext();

        try {
            // Retrieve plan with products using ScheduleCampainDBContext
            int planId = 1; // Example plan ID
            Plan plan = scheduleDB.getPlanWithProducts(planId);

            if (plan == null) {
                System.out.println("Plan not found with ID: " + planId);
                return;
            }

            // Generate formatted dates between start and end date
            ArrayList<String> formattedDates = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(plan.getStartd());
            while (!calendar.getTime().after(plan.getEndd())) {
                formattedDates.add(new java.text.SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
                calendar.add(Calendar.DATE, 1);
            }

            // Set attributes as would be done in the servlet
            System.out.println("Plan ID: " + plan.getPlid());
            System.out.println("Products:");
            for (Product product : plan.getProducts()) {
                System.out.println("Product ID: " + product.getId() + ", Product Name: " + product.getName());
            }
            System.out.println("Dates:");
            for (String date : formattedDates) {
                System.out.println(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        
    }
}