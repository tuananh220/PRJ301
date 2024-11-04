/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.accesscontrol;

import dal.UserDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import model.accesscontrol.Role;
import model.accesscontrol.User;
import model.accesscontrol.Features;

/**
 *
 * @author Admin
 */
public abstract class BaseRBACController extends BaseRequiredAuthenticationController{
    private boolean isAuthorized(HttpServletRequest req, User loggeduser)
    {
        UserDBContext db = new UserDBContext();
        ArrayList<Role> roles = db.getRoles(loggeduser.getUsername());
        loggeduser.setRoles(roles);
        String c_url = req.getServletPath();
        for (Role role : roles) {
            for (Features feature : role.getFeatures()) {
                if(feature.getUrl().equals(c_url))
                    return true;
            }
        }
        
        return false;
    }
    
    protected abstract void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException;
     protected abstract void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException;
     
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        if(isAuthorized(req, loggeduser))
        {
            doAuthorizedGet(req, resp, loggeduser);
        }
        else
            resp.sendError(403, "you do not have right to access this feature!");
    
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        if(isAuthorized(req, loggeduser))
        {
            doAuthorizedPost(req, resp, loggeduser);
        }
        else
            resp.sendError(403, "you do not have right to access this feature!");
    }
}
