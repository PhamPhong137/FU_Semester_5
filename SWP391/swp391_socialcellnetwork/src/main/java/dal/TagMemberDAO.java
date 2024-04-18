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
import java.util.logging.Level;
import java.util.logging.Logger;
import models.TagMember;

/**
 *
 * @author PC-Phong
 */
public class TagMemberDAO {

    private Connection con;

    public static TagMemberDAO INSTANCE = new TagMemberDAO();

    public TagMemberDAO() {
        if (INSTANCE == null) {
            try {
                con = new DBContext().getConnection();
            } catch (Exception e) {
                Logger.getLogger(TagMemberDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
            }
        }
    }

    public void addTagEvent(int event_id, int user_id) {
        String sql = "INSERT INTO `social`.`event_tag` (`event_id`, `user_id`) VALUES (?, ?);";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, event_id);
            ps.setInt(2, user_id);

            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(TagMemberDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

    public void deleteTagEventByEventId(int event_id) {
        String sql = "Delete FROM social.event_tag where event_id=?;";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, event_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(TagMemberDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

    public List<TagMember> loadTagmemberByEventId(int event_id) {
        List<TagMember> loadTagmemberByEventId = new Vector<>();
        String sql = "SELECT et.event_tag_id,et.event_id,et.user_id,u.first_name,u.last_name\n"
                + "FROM social.event_tag et\n"
                + "JOIN social.user u ON et.user_id = u.user_id\n"
                + "WHERE et.event_id = ?;";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, event_id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                loadTagmemberByEventId.add(new TagMember(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5)
                ));
            }
        } catch (SQLException e) {
            Logger.getLogger(TagMemberDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return loadTagmemberByEventId;
    }

    public int countTagMember(int event_id) {
        String sql = "select count(user_id) as count from social.event_tag where event_id=?";
        int count = 0;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, event_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(StatusDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return count;
    }

    public void deleteTagEventByRelation(int currentUserId, int friendId) {
        String sql = "Delete FROM social.event_tag et where event_id in(select e.event_id from event e where e.user_id = ? ) and et.user_id =?;";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, currentUserId);
            ps.setInt(2, friendId);

            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(TagMemberDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

}
