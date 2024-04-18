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

/**
 *
 * @author ACER
 */
public class TagMemberStatusDAO {

    public static TagMemberStatusDAO INSTANCE = new TagMemberStatusDAO();
    private Connection con;

    public TagMemberStatusDAO() {
        if (INSTANCE == null) {
            try {
                con = new DBContext().getConnection();
            } catch (Exception e) {
                Logger.getLogger(TagMemberStatusDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
            }
        }
    }

    public void addTagStatus(int status_id, int user_id) {
        String sql = "insert into status_tag (status_id, user_id) values (?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, status_id);
            ps.setInt(2, user_id);

            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(TagMemberStatusDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

    public void deleteTagStatus(int statusid) {
        String sql = "delete from status_tag where status_id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, statusid);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(TagMemberStatusDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

    public void updateTagStatus(int user_id, int status_tag_id) {
        String sql = "update status_tag  set user_id= ? where status_tag_id=?;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user_id);
            ps.setInt(2, status_tag_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(TagMemberStatusDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

    public List loadTagStatus(int status_id) {
        List tagedMembers = new ArrayList<>();
        String sql = "select distinct user_id  from status_tag where status_id= ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, status_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tagedMembers.add( UserDAO.INSTANCE.getUserByUserId( rs.getInt(1))
                );
            }
        } catch (SQLException e) {
            Logger.getLogger(TagMemberStatusDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return tagedMembers;
    }
    
    public static void main(String[] args) {
        System.out.println(TagMemberStatusDAO.INSTANCE.loadTagStatus(47).size());
    }
}
