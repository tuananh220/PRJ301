/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.accesscontrol;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class User {
    private String username;
    private String password;
    private String displayname;
    private ArrayList<Role> roles = new ArrayList<>();

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public boolean hasRole(String roleName) {
        for (Role role : roles) {
            if (role.getName().equalsIgnoreCase(roleName)) {
                return true;
            }
        }
        return false;
    }

    public String determineRedirectPage() {
        for (Role role : roles) {
            if (role.getName().equalsIgnoreCase("Giám Đốc")) {
                return "directorTasks.jsp";
            } else if (role.getName().equalsIgnoreCase("Trưởng Phòng")) {
                return "managerTasks.jsp";
            } else if (role.getName().equalsIgnoreCase("Quản Đốc")) {
                return "employeeTasks.jsp";
            }
        }
        return "defaultTasks.jsp";
    }
}
