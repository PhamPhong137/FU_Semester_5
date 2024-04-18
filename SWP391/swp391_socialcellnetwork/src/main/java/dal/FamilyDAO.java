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

public class FamilyDAO extends DBContext {

    private Connection con;
    private String status = "";
    public static FamilyDAO INSTANCE = new FamilyDAO();

    private FamilyDAO() {
        try {
            this.con = new DBContext().getConnection();
            if (this.con == null) {
                throw new IllegalStateException("Connection to database could not be established.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error establishing database connection.", e);
        }
    }

    public List<FamilyInfo> getAllFamilysByUserId(int userId) {
        List<FamilyInfo> familys = new ArrayList<>();
        try {
            String sql = "SELECT g.* FROM `family` g "
                    + "JOIN family_member gm ON g.family_id = gm.family_id "
                    + "WHERE gm.user_id = ? AND g.type=1";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, userId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                FamilyInfo family = new FamilyInfo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getDate(7));
                familys.add(family);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FamilyDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return familys;
    }

    public int addFamily(String familyName, String imageUrl, String introduction) {
        String sql = "INSERT INTO `family` (name,img, type, description, date) "
                + "VALUES (?, ?,1, ?, CURRENT_TIMESTAMP)";
        try (PreparedStatement stm = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stm.setString(1, familyName);
            stm.setString(2, imageUrl);
            stm.setString(3, introduction);
            int affectedRows = stm.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stm.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FamilyDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public boolean addFamilyMember(int familyId, int currentUserId, int role) {
        String sql = "INSERT INTO family_member (family_id, user_id, Role) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, familyId);
            ps.setInt(2, currentUserId);
            ps.setInt(3, role);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public FamilyInfo getFamilyById(int familyId) {
        try {

            String sql = "SELECT family.*, tree.tree_id FROM family LEFT JOIN tree ON family.family_id = tree.family_id WHERE family.family_id = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, familyId);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                FamilyInfo family = new FamilyInfo(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getDate(7),
                        rs.getInt(8));
                return family;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FamilyDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public FamilyMember getFamilyMemberById(int familyId, int currentUserId) {
        try {
            String sql = "SELECT * FROM family_member WHERE family_id = ? and user_id = ? ";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, familyId);
            stm.setInt(2, currentUserId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                FamilyMember familymember = new FamilyMember(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4));
                return familymember;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FamilyDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean checkFamilyExists(int familyId, int currentUserId) {
        String sql = "SELECT * FROM family_member WHERE family_id = ? AND user_id = ?";
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, familyId);
            stm.setInt(2, currentUserId);
            ResultSet rs = stm.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public List<User> getFamilyMembersByRole(int familyId, int role) {
        List<User> familyUserDetails = new ArrayList<>();
        try {
            String sql = "SELECT distinct user.* FROM family_member \n"
                    + "JOIN user ON family_member.user_id = user.user_id \n"
                    + "WHERE family_member.family_id = ? AND family_member.Role = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, familyId);
            stm.setInt(2, role);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getDate(5),
                        rs.getDate(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getInt(9));
                familyUserDetails.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FamilyDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return familyUserDetails;
    }

    public boolean outFamilyMember(int familyId, int currentUserId) {
        String sql = "DELETE FROM family_member WHERE family_id = ? AND user_id = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, familyId);
            stm.setInt(2, currentUserId);
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(FamilyDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void removeFamilyByFamilyId(int id) {
        String sql = "UPDATE `social`.`family` SET `type` = '0' WHERE (`family_id` = ?);";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            status = "Error at delete Account by ID: " + e.getMessage();
        }
    }

    public void editFamily(int id, String name, String img, String description) {
        String sql = "UPDATE `social`.`family`\n"
                + "SET\n"
                + "`name` = ?,\n"
                + "`img` = ?,\n"
                + "`description` = ?\n"
                + "WHERE `family_id` = ?\n"
                + "";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, img);
            ps.setString(3, description);
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            status = "Error at edit Family " + e.getMessage();
        }
    }

    public void removeFamilyMember(int familyId, int userId) {
        String sql = "DELETE FROM family_member WHERE family_id = ? AND user_id = ?";
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, familyId);
            stm.setInt(2, userId);
            stm.executeUpdate();
        } catch (SQLException e) {
            status = "Error at edit FamilyMember " + e.getMessage();
        }
    }

    public void updateFamilyMemberRole(int familyId, int userId, int role) {
        String sql = "UPDATE family_member SET Role = ? WHERE family_id = ? AND user_id = ?";
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, role);
            stm.setInt(2, familyId);
            stm.setInt(3, userId);
            stm.executeUpdate();
        } catch (SQLException e) {
            status = "Error at edit FamilyMember " + e.getMessage();
        }
    }

    public List<User> searchByNameOrPhoneNumber(String searchQuery, int userId, int familyId) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT DISTINCT u.* FROM user u "
                + "JOIN relation r ON ((u.user_id = r.user2_id AND r.user1_id = ?) OR (u.user_id = r.user1_id AND r.user2_id = ?)) "
                + "AND r.type = 1 "
                + "WHERE (u.phone_number LIKE ? OR u.first_name LIKE ? OR u.last_name LIKE ?) "
                + "AND u.user_id NOT IN (SELECT user_id FROM family_member WHERE family_id = ?)";

        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, userId);
            stm.setInt(2, userId);
            stm.setString(3, "%" + searchQuery + "%");
            stm.setString(4, "%" + searchQuery + "%");
            stm.setString(5, "%" + searchQuery + "%");
            stm.setInt(6, familyId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                User a = new User(
                        rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("gender"),
                        rs.getDate("date_birth"),
                        rs.getDate("date_death"),
                        rs.getString("phone_number"),
                        rs.getString("image"),
                        rs.getInt("status")
                );
                users.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FamilyDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }
}
