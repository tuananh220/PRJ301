/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;




import model.Plan;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.PlanCampain;

/**
 *
 * @author sonnt-local
 */
public class PlanDBContext extends DBContext<Plan> {

    @Override
    public void insert(Plan model) {
        try {
            connection.setAutoCommit(false);
            String sql_insert_plan = "INSERT INTO [Plan]\n"
                    + "           ([startd]\n"
                    + "           ,[endd]\n"
                    + "           ,[did])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?)";
            String sql_select_plan = "SELECT @@IDENTITY as plid";
            String sql_insert_campain = "INSERT INTO [PlanCampaign]\n"
                    + "           ([plid]\n"
                    + "           ,[pid]\n"
                    + "           ,[quantity]\n"
                    + "           ,[estimatedeffort])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            
            PreparedStatement stm_insert_plan = connection.prepareStatement(sql_insert_plan);
            stm_insert_plan.setDate(1, model.getStartd());
            stm_insert_plan.setDate(2, model.getEndd());
            stm_insert_plan.setInt(3, model.getDepartment().getId());
            stm_insert_plan.executeUpdate();
            
            PreparedStatement stm_select_plan = connection.prepareStatement(sql_select_plan);
            ResultSet rs = stm_select_plan.executeQuery();
            if(rs.next())
            {
                model.setPlid(rs.getInt("plid"));
            }
            for (PlanCampain campain : model.getCampains()) {
                PreparedStatement stm_insert_campain = connection.prepareStatement(sql_insert_campain);
                stm_insert_campain.setInt(1, model.getPlid());
                stm_insert_campain.setInt(2, campain.getProduct().getId());
                stm_insert_campain.setInt(3, campain.getQuantity());
                stm_insert_campain.setFloat(4, campain.getEstimatedeffort());
                stm_insert_campain.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    public List<Plan> getAllPlans() {
        List<Plan> plans = new ArrayList<>();
        String sql = "SELECT pl.plid, pl.startd, pl.endd, " +
                     "       CASE " +
                     "           WHEN SUM(ISNULL(ws.quantity, 0)) >= SUM(pc.quantity) THEN 'Complete' " +
                     "           WHEN MAX(pl.endd) < GETDATE() THEN 'Late' " +
                     "           ELSE 'On-going' " +
                     "       END AS status " +
                     "FROM [Plan] pl " +
                     "LEFT JOIN PlanCampaign pc ON pl.plid = pc.plid " +
                     "LEFT JOIN ScheduleCampaign sc ON pc.canid = sc.canid " +
                     "LEFT JOIN WorkerSchedule ws ON sc.scid = ws.scid " +
                     "GROUP BY pl.plid, pl.startd, pl.endd";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Plan plan = new Plan();
                plan.setPlid(resultSet.getInt("plid"));
                plan.setStartd(resultSet.getDate("startd"));
                plan.setEndd(resultSet.getDate("endd"));
                plan.setStatus(resultSet.getString("status"));
                plans.add(plan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return plans;
    }

    // Lấy thông tin của kế hoạch theo ID
    public Plan getPlanById(int plid) {
        Plan plan = null;
        String sql = "SELECT pl.plid, pl.startd, pl.endd, " +
                     "       CASE " +
                     "           WHEN SUM(ISNULL(ws.quantity, 0)) >= SUM(pc.quantity) THEN 'Complete' " +
                     "           WHEN MAX(pl.endd) < GETDATE() THEN 'Late' " +
                     "           ELSE 'On-going' " +
                     "       END AS status " +
                     "FROM [Plan] pl " +
                     "LEFT JOIN PlanCampaign pc ON pl.plid = pc.plid " +
                     "LEFT JOIN ScheduleCampaign sc ON pc.canid = sc.canid " +
                     "LEFT JOIN WorkerSchedule ws ON sc.scid = ws.scid " +
                     "WHERE pl.plid = ? " +
                     "GROUP BY pl.plid, pl.startd, pl.endd";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, plid);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                plan = new Plan();
                plan.setPlid(resultSet.getInt("plid"));
                plan.setStartd(resultSet.getDate("startd"));
                plan.setEndd(resultSet.getDate("endd"));
                plan.setStatus(resultSet.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return plan;
    }
    
    @Override
    public void update(Plan model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Plan model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Plan> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Plan get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

