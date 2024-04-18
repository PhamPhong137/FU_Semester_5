/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.*;

/**
 *
 * @author Admin
 */
public class UserDAO extends DBContext {

    private Connection con;
    public static UserDAO INSTANCE = new UserDAO();

    public UserDAO() {
        if (INSTANCE == null) {
            try {
                con = new DBContext().getConnection();
            } catch (Exception e) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
            }
        }
    }

    public User getUserByUserId(int userId) {
        User user = null;
        String sql = "SELECT u.*, t.tree_id FROM user u LEFT JOIN tree t ON u.user_id = t.user_id WHERE u.user_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User(rs.getInt("u.user_id"),
                        rs.getString("u.first_name"),
                        rs.getString("u.last_name"),
                        rs.getInt("u.gender"),
                        rs.getDate("u.date_birth"),
                        rs.getDate("u.date_death"),
                        rs.getString("u.phone_number"),
                        rs.getString("u.image"),
                        rs.getInt("u.status"),
                        rs.getInt("t.tree_id"));
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return user;
    }

    public List<User> loadFriendByUserId(int userId) {
        List<User> loadFriendByUserId = new Vector<>();
        String sql = "SELECT\n"
                + "  u.user_id,\n"
                + "  u.first_name,\n"
                + "  u.last_name\n"
                + "FROM\n"
                + "  relation r\n"
                + "JOIN `user` u ON u.user_id = CASE\n"
                + "                              WHEN r.user1_id = ? THEN r.user2_id\n"
                + "                              ELSE r.user1_id\n"
                + "                            END\n"
                + "WHERE\n"
                + "  (r.user1_id = ? OR r.user2_id =?)\n"
                + "  AND r.type = 1;";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            ps.setInt(3, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                loadFriendByUserId.add(new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3)
                ));
            }
        } catch (SQLException e) {
            Logger.getLogger(CalendarDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return loadFriendByUserId;
    }

    public boolean checkExistedPhone(String phone_number) {
        if ("".equals(phone_number.trim())) {
            return false;
        }
        String sql = "SELECT * FROM social.user WHERE phone_number = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, phone_number);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return false;
    }

    public boolean checkExistedEmail(String email) {
        if ("".equals(email.trim())) {
            return false;
        }
        String sql = "SELECT * FROM `social`.`account` WHERE email = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return false;
    }

    public int addUser(String f_name, String l_name, int gender, String phone_number) {
        String sql = "INSERT INTO `social`.`user` (`first_name`, `last_name`, `gender`, `phone_number`, `status`, `image`) "
                + "VALUES (?, ?, ?, ?, 0, 'userdefault.jpg')";
        int insertedID = 0;
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, f_name);
            ps.setString(2, l_name);
            ps.setInt(3, gender);
            ps.setString(4, phone_number);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                insertedID = rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return insertedID;
    }

    public void updateUser(int id, String f_name, String l_name, int gender, Date date_birth, String phone_number, String image) {
        String sql = "UPDATE `social`.`user`\n"
                + "SET\n"
                + "`first_name` = ?,\n"
                + "`last_name` =?,\n"
                + "`gender` = ?,\n"
                + "`date_birth` = ?,\n"
                + "`phone_number` = ?,\n"
                + "`image` = ?\n"
                + "WHERE `user_id` = ?;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, f_name);
            ps.setString(2, l_name);
            ps.setInt(3, gender);
            ps.setDate(4, date_birth);
            ps.setString(5, phone_number);
            ps.setString(6, image);
            ps.setInt(7, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

    public int addUserByLoginGoogle(String f_name, String l_name) {
        String sql = "INSERT INTO `social`.`user` (`first_name`, `last_name`, `status`, `image`) "
                + "VALUES (?, ?, 0, 'userdefault.jpg')";
        int insertedID = 0;
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, f_name);
            ps.setString(2, l_name);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                insertedID = rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return insertedID;
    }

    public void updateUserPhone(int id, String phone_number) {
        String sql = "UPDATE `social`.`user`\n"
                + "SET\n"
                + "`phone_number` = ?\n"
                + "WHERE `user_id` = ?;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, phone_number);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }
}
