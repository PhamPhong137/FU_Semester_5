/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.*;

public class FriendDAO extends DBContext {

    private Connection con;
    private String status = "";
    public static FriendDAO INSTANCE = new FriendDAO();

    private FriendDAO() {
        try {
            this.con = new DBContext().getConnection();
            if (this.con == null) {
                throw new IllegalStateException("Connection to database could not be established.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error establishing database connection.", e);
        }
    }

    public List<User> SearchAllFriend(String searchQuery, int currentUserId, boolean isFriendSearch) {
        ArrayList<User> users = new ArrayList<>();
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            return users;
        }
        try {
            String sql;
            if (isFriendSearch) {
                sql = "SELECT u.* FROM user u "
                        + "JOIN relation r ON (u.user_id = r.user1_id OR u.user_id = r.user2_id) "
                        + "WHERE (u.first_name LIKE ? OR u.last_name LIKE ?) "
                        + "AND (r.user1_id = ? OR r.user2_id = ?) "
                        + "AND u.user_id != ? AND r.type = 1";
            } else {
                sql = "SELECT * FROM user WHERE phone_number LIKE ? "
                        + "AND user_id != ?";
            }
            PreparedStatement stm = con.prepareStatement(sql);
            if (isFriendSearch) {
                stm.setString(1, "%" + searchQuery + "%");
                stm.setString(2, "%" + searchQuery + "%");
                stm.setInt(3, currentUserId);
                stm.setInt(4, currentUserId);
                stm.setInt(5, currentUserId);
            } else {
                stm.setString(1, "%" + searchQuery + "%");
                stm.setInt(2, currentUserId);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                User a = new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getDate(5),
                        rs.getDate(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getInt(9));
                users.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FriendDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

    public List<User> searchFriendByNumberOrName(String searchQuery, int currentUserId) {
        ArrayList<User> users = new ArrayList<>();
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            return users;
        }

        // SQL query to search for friends by name or by phone number
        String sql = "SELECT u.* FROM user u "
                + "LEFT JOIN relation r ON (u.user_id = r.user1_id OR u.user_id = r.user2_id) AND (r.user1_id = ? OR r.user2_id = ?) "
                + "WHERE (u.first_name LIKE ? OR u.last_name LIKE ? OR u.phone_number = ?) "
                + "AND u.user_id != ? "
                + "GROUP BY u.user_id";

        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, currentUserId);
            stm.setInt(2, currentUserId);
            stm.setString(3, "%" + searchQuery + "%");
            stm.setString(4, "%" + searchQuery + "%");
            stm.setString(5, "%" + searchQuery + "%");
            stm.setInt(6, currentUserId);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("gender"),
                        rs.getDate("date_birth"),
                        rs.getDate("date_death"),
                        rs.getString("phone_number"),
                        rs.getString("image"),
                        rs.getInt("status"));
                users.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FriendDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

    public boolean areFriends(int userId, int otherUserId) {
        try {
            String sql = "SELECT type FROM relation WHERE (user1_id = ? AND user2_id = ?) OR (user1_id = ? AND user2_id = ?)";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, otherUserId);
            stm.setInt(3, otherUserId);
            stm.setInt(4, userId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                int type = rs.getInt("type");
                return type == 1;
            }
        } catch (SQLException e) {
            Logger.getLogger(FriendDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return false;
    }

    public Integer getRelation(int userId, int otherUserId) {
        try {
            String sql = "SELECT type FROM relation WHERE (user1_id = ? AND user2_id = ?) OR (user1_id = ? AND user2_id = ?)";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, otherUserId);
            stm.setInt(3, otherUserId);
            stm.setInt(4, userId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("type");
            } else {
                return null;
            }
        } catch (SQLException e) {
            Logger.getLogger(FriendDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return null;
    }

    public int getRelationId(int userId, int friendId) {
        String sql = "SELECT relation_id FROM relation WHERE (user1_id = ? AND user2_id = ?) OR (user1_id = ? AND user2_id = ?)";
        try (PreparedStatement stm = this.getConnection().prepareStatement(sql)) {
            stm.setInt(1, userId);
            stm.setInt(2, friendId);
            stm.setInt(3, friendId);
            stm.setInt(4, userId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("relation_id");
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(FriendDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return -1;
    }

    public int getFriendIdFromRelation(int userId, int relationId) {
        String sql = "SELECT user1_id, user2_id FROM relation WHERE relation_id = ?";
        try (PreparedStatement stm = this.getConnection().prepareStatement(sql)) {
            stm.setInt(1, relationId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    int user1Id = rs.getInt("user1_id");
                    int user2Id = rs.getInt("user2_id");
                    if (userId == user1Id) {
                        return user2Id;
                    } else if (userId == user2Id) {
                        return user1Id;
                    }
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(FriendDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return -1;
    }

    public int addFriend(int userId, int friendId) {
        String sqlInsert = "INSERT INTO relation (user1_id, user2_id, type) VALUES (?, ?, 0) ON DUPLICATE KEY UPDATE type = VALUES(type);";
        String sqlSelect = "SELECT relation_id FROM relation WHERE (user1_id = ? AND user2_id = ?) OR (user1_id = ? AND user2_id = ?);";
        int relationId = -1;
        try {
            try (PreparedStatement stm = con.prepareStatement(sqlInsert)) {
                stm.setInt(1, userId);
                stm.setInt(2, friendId);
                stm.executeUpdate();
            }
            try (PreparedStatement stm = con.prepareStatement(sqlSelect)) {
                stm.setInt(1, userId);
                stm.setInt(2, friendId);
                stm.setInt(3, friendId);
                stm.setInt(4, userId);
                ResultSet rs = stm.executeQuery();
                if (rs.next()) {
                    relationId = rs.getInt("relation_id");
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(FriendDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return relationId;
    }

    public Integer getRequestSenderId(int currentUserId, int friendId) {
        Integer senderId = null;
        String sql = "SELECT user1_id FROM relation WHERE (user1_id = ? AND user2_id = ?) OR (user1_id = ? AND user2_id = ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            // Set the parameters for the PreparedStatement
            ps.setInt(1, currentUserId);
            ps.setInt(2, friendId);
            ps.setInt(3, friendId);
            ps.setInt(4, currentUserId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    senderId = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(FriendDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return senderId;
    }

    public void deleteFriend(int userId, int friendId) {
        String sqlDeleteNotifications = "DELETE FROM notification WHERE relation_id IN (SELECT relation_id FROM relation WHERE (user1_id = ? AND user2_id = ?) OR (user1_id = ? AND user2_id = ?))";
        String sqlDeleteRelation = "DELETE FROM relation WHERE (user1_id = ? AND user2_id = ?) OR (user1_id = ? AND user2_id = ?)";
        try (Connection conn = this.getConnection(); PreparedStatement psDeleteNotifications = conn.prepareStatement(sqlDeleteNotifications); PreparedStatement psDeleteRelation = conn.prepareStatement(sqlDeleteRelation)) {
            psDeleteNotifications.setInt(1, userId);
            psDeleteNotifications.setInt(2, friendId);
            psDeleteNotifications.setInt(3, friendId);
            psDeleteNotifications.setInt(4, userId);
            psDeleteNotifications.executeUpdate();
            psDeleteRelation.setInt(1, userId);
            psDeleteRelation.setInt(2, friendId);
            psDeleteRelation.setInt(3, friendId);
            psDeleteRelation.setInt(4, userId);
            psDeleteRelation.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FriendDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<User> SearchAllFriendOfUserProfile(int currentUserId) {
        ArrayList<User> students = new ArrayList<>();
        try {
            String sql = "select * from user where user_id in ((select r.user1_id from user u join relation r\n"
                    + "on r.user1_id = u.user_id where user1_id = " + currentUserId + " or user2_id = " + currentUserId + " and r.type = 1)\n"
                    + "union \n"
                    + "select r.user2_id from user u join relation r\n"
                    + "on r.user1_id = u.user_id where user1_id = " + currentUserId + " or user2_id = " + currentUserId + " and r.type = 1) and user_id != " + currentUserId + ";";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User a = new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getDate(5),
                        rs.getDate(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getInt(9));
                students.add(a);
            }
        } catch (SQLException e) {
            Logger.getLogger(FriendDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return students;
    }

}
