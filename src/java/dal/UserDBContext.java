package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.accesscontrol.Role;
import model.accesscontrol.User;
import model.accesscontrol.Features;

public class UserDBContext extends DBContext<User> {
    public ArrayList<Role> getRoles(String username) {
        ArrayList<Role> roles = new ArrayList<>();
        String sql = "SELECT r.roleId,r.roleName,f.featureId,f.featureName,f.url FROM [User] u "
                + "    INNER JOIN UserRole ur ON ur.username = u.username\n"
                + "    INNER JOIN [Role] r ON r.roleId = ur.roleId\n"
                + "    INNER JOIN RoleFeature rf ON rf.roleId = r.roleId\n"
                + "    INNER JOIN Feature f ON f.featureId = rf.featureId\n"
                + "WHERE u.username = ?\n"
                + "ORDER BY r.roleId ASC, f.featureId ASC";

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            rs = stm.executeQuery();
            Role crole = new Role();
            crole.setId(-1);
            while (rs.next()) {
                int rid = rs.getInt("roleId");
                if (rid != crole.getId()) {
                    crole = new Role();
                    crole.setId(rid);
                    crole.setName(rs.getString("roleName"));
                    roles.add(crole);
                }
                Features f = new Features();
                f.setId(rs.getInt("featureId"));
                f.setName(rs.getString("featureName"));
                f.setUrl(rs.getString("url"));
                crole.getFeatures().add(f);
                f.setRoles(roles);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return roles;
    }

    public User get(String username, String password) {
        String sql = "SELECT [username], [password], [displayname] FROM [User] WHERE username = ? AND [password] = ?";

        PreparedStatement stm = null;
        ResultSet rs = null;
        User user = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            rs = stm.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setDisplayname(rs.getString("displayname"));
                user.setUsername(username);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return user;
    }

    @Override
    public User get(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void insert(User model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(User model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(User model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<User> list() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
