package dal;

import model.ScheduleCampain;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Plan;
import model.PlanCampain;
import model.Product;

public class ScheduleCampainDBContext extends DBContext<ScheduleCampain> {
    private static final Logger logger = Logger.getLogger(ScheduleCampainDBContext.class.getName());

    @Override
    public ArrayList<ScheduleCampain> list() {
        ArrayList<ScheduleCampain> scheduleCampaigns = new ArrayList<>();
        String sql = "SELECT sc.*, pc.*, p.* FROM ScheduleCampaign sc "
                   + "JOIN PlanCampaign pc ON sc.canid = pc.canid "
                   + "JOIN Product p ON pc.pid = p.pid";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                PlanCampain planCampaign = new PlanCampain();
                planCampaign.setId(rs.getInt("canid"));
                planCampaign.setProduct(new Product(rs.getInt("pid"), rs.getString("pname")));
                ScheduleCampain scheduleCampaign = new ScheduleCampain(
                        rs.getInt("scid"),
                        planCampaign,
                        rs.getDate("date"),
                        rs.getString("shift"),
                        rs.getInt("quantity")
                );
                scheduleCampaigns.add(scheduleCampaign);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error while listing ScheduleCampaigns", ex);
        }
        return scheduleCampaigns;
    }

    public void createSchedule(PlanCampain planCampaign, java.sql.Date date, String shift, int quantity) {
        String sql = "INSERT INTO ScheduleCampaign (canid, date, shift, quantity) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, planCampaign.getId());
            statement.setDate(2, date);
            statement.setString(3, shift);
            statement.setInt(4, quantity);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while creating ScheduleCampaign", e);
        }
    }

    public Plan getPlanWithProducts(int planId) throws SQLException {
        Plan plan = null;
        String sql = "SELECT p.plid, p.startd, p.endd, pr.pid, pr.pname FROM [Plan] p "
                   + "JOIN PlanCampaign pc ON p.plid = pc.plid "
                   + "JOIN Product pr ON pc.pid = pr.pid WHERE p.plid = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, planId);
            try (ResultSet rs = statement.executeQuery()) {
                ArrayList<Product> products = new ArrayList<>();
                while (rs.next()) {
                    if (plan == null) {
                        plan = new Plan();
                        plan.setPlid(rs.getInt("plid"));
                        plan.setStartd(rs.getDate("startd"));
                        plan.setEndd(rs.getDate("endd"));
                    }
                    Product product = new Product(rs.getInt("pid"), rs.getString("pname"));
                    products.add(product);
                }
                if (plan != null) {
                    plan.setProducts(products);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while getting plan with products", e);
            throw e;
        }
        return plan;
    }

    @Override
    public void insert(ScheduleCampain model) {
        String sql = "INSERT INTO ScheduleCampaign (canid, date, shift, quantity) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, model.getPlanCampain().getId());
            stm.setDate(2, new java.sql.Date(model.getDate().getTime()));
            stm.setString(3, model.getShift());
            stm.setInt(4, model.getQuantity());
            stm.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while inserting ScheduleCampaign", e);
        }
    }

    @Override
    public void update(ScheduleCampain model) {
        String sql = "UPDATE ScheduleCampaign SET canid = ?, date = ?, shift = ?, quantity = ? WHERE scid = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, model.getPlanCampain().getId());
            stm.setDate(2, new java.sql.Date(model.getDate().getTime()));
            stm.setString(3, model.getShift());
            stm.setInt(4, model.getQuantity());
            stm.setInt(5, model.getScid());
            stm.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while updating ScheduleCampaign", e);
        }
    }

    @Override
    public void delete(ScheduleCampain model) {
        String sql = "DELETE FROM ScheduleCampaign WHERE scid = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, model.getScid());
            stm.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while deleting ScheduleCampaign", e);
        }
    }

    @Override
    public ScheduleCampain get(int id) {
        String sql = "SELECT sc.*, pc.*, p.* FROM ScheduleCampaign sc "
                   + "JOIN PlanCampaign pc ON sc.canid = pc.canid "
                   + "JOIN Product p ON pc.pid = p.pid WHERE scid = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                PlanCampain planCampaign = new PlanCampain();
                planCampaign.setId(rs.getInt("canid"));
                planCampaign.setProduct(new Product(rs.getInt("pid"), rs.getString("pname")));
                ScheduleCampain sc = new ScheduleCampain(
                        rs.getInt("scid"),
                        planCampaign,
                        rs.getDate("date"),
                        rs.getString("shift"),
                        rs.getInt("quantity")
                );
                return sc;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while getting ScheduleCampaign by ID", e);
        }
        return null;
    }
}
