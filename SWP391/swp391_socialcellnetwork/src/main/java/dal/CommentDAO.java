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
import models.Comment;

/**
 *
 * @author ACER
 */
public class CommentDAO {

    private List<Comment> comment;
    private List<Comment> commentById;
    public static CommentDAO INSTANCE = new CommentDAO();
    private Connection con;

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public CommentDAO() {
        if (INSTANCE == null) {
            try {
                con = new DBContext().getConnection();
            } catch (Exception e) {
                Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
            }

        }
    }

    public void loadCommentById(int statusId) {
        commentById = new Vector<>();
        String sql = "Select * from comment where status_id =?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, statusId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                commentById.add(new Comment(rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4)
                ));
            }
        } catch (SQLException e) {
            Logger.getLogger(StatusDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }
    
    public void loadCommentByIdTop10(int statusId, int count) {
        commentById = new Vector<>();
        String sql = "Select * from comment where status_id =? limit 10 offset ?; ";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, statusId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                commentById.add(new Comment(rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4)
                ));
            }
        } catch (SQLException e) {
            Logger.getLogger(StatusDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }
    public List<Comment> getCommentByStatusId(int statusId) {
        return commentById;
    }

    public void addComment(String description, int status_id, int user_id) {
        String sql = "insert into comment (`description`, status_id, user_id ) values (?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, description);
            ps.setInt(2, status_id);
            ps.setInt(3, user_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

    public int countComment(int status_id) {
        String sql = "select count(comment_id) from `comment` where status_id =?;";
        int count = 0;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, status_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return count;
    }
    
     public void updateComment(int comment_id, String description) {
        String sql = "update comment set description = ? where comment_id  = ?;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, description);
            ps.setInt(2, comment_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }
     
     public void deleteComment (int comment_id){
         String sql = "delete from comment where comment_id = ?;";
         try{
             PreparedStatement ps = con.prepareStatement(sql);
             ps.setInt(1, comment_id);
             ps.executeUpdate();
         } catch(SQLException e){
             Logger.getLogger(CommentDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
         }
     }
     
     public int getNewCommentId( int user_id){
        String sql = "select comment_id from comment where user_id= ? order by comment_id desc limit 1;";
        int id = -1;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(StatusDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return id;
     }
  
     
}
