/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import model.*;


public class UserDAO extends DBContext {

    private Connection con;
    private String status = "";
    private List<User> userList;
    public static UserDAO INSTANCE = new UserDAO();

    public UserDAO() {
        if (INSTANCE == null) {
            try {
                con = new DBContext().getConnection();
            } catch (Exception e) {
                status = "Error ar connection" + e.getMessage();
            }
        }
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public static UserDAO getINSTANCE() {
        return INSTANCE;
    }

    public static void setINSTANCE(UserDAO INSTANCE) {
        UserDAO.INSTANCE = INSTANCE;
    }

    public void loadUser() {
        userList = new Vector<>();
        String sql = "select * from users";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

            }
        } catch (SQLException e) {
            status = "Error at load Account " + e.getMessage();
        }
    }

    public void register(User u) {
        String sql = """
                     INSERT INTO `social_networking`.`users`
                     (`user_id`,
                     `first_name`,
                     `last_name`,
                     `phone_number`,
                     `email`,
                     `role`,
                     `image`,
                     `status`)(?,?,?,?,?,);
                     """;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, u.getUser_id());
            ps.setString(2, u.getF_name());
            ps.setString(3, u.getL_name());
            ps.setString(4, u.getPhone_number());
            ps.setString(5, u.getEmail());
            ps.setInt(6, u.getRole());
            ps.setString(7, u.getImage());
            ps.setString(8, u.getStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            status = "Error at register " + e.getMessage();
        }
    }
}
