/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.*;

public class ConversationDAO extends DBContext {

    private Connection con;
    private String status = "";
    public static ConversationDAO INSTANCE = new ConversationDAO();

    private ConversationDAO() {
        try {
            this.con = new DBContext().getConnection();
            if (this.con == null) {
                throw new IllegalStateException("Connection to database could not be established.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error establishing database connection.", e);
        }
    }

    public int addConversationPair(String name, String image) {
        String sql = "INSERT INTO `social`.`conversation` (`name`, `image`, `Status`) "
                + "VALUES (?, ?, 0)";
        int insertedID = 0;
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, image);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                insertedID = rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(ConversationDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return insertedID;
    }
    
    public Conversation SearchConversationById(int conversationId) {
        Conversation conversation = new Conversation();
        try {
            String sql = "select * from conversation where conversation_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, conversationId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                conversation = new Conversation(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5));
            }
        } catch (SQLException e) {
            Logger.getLogger(ConversationDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return conversation;
    }

    public int addConversationGroup(int family_id, String name, String image) {
        String sql = "INSERT INTO `social`.`conversation` (`family_id`,`name`, `image`, `Status`) "
                + "VALUES (?, ?, ?, 0)";
        int insertedID = 0;
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, family_id);
            ps.setString(2, name);
            ps.setString(3, image);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                insertedID = rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(ConversationDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return insertedID;
    }

    public void addConversationMember(int role, int userId, int conversation_id) {
        String sql = "INSERT INTO `social`.`conversation_member` (`role`, `user_id`, `conversation_id`) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, role);
            ps.setInt(2, userId);
            ps.setInt(3, conversation_id);
            ps.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(ConversationDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
            e.printStackTrace();
        }

    }
    
    public void removeConversationMember(int userId, int conversation_id) {
        String sql = "DELETE FROM `social`.`conversation_member` WHERE user_id = ? and conversation_id = ?;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, conversation_id);
            ps.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(ConversationDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
            e.printStackTrace();
        }
    }

//    public void deleteConversation(int userId, int friendId) {
//        String sql = "DELETE FROM `social`.`conversation` WHERE conversation_id = ?";
//        try {
//            PreparedStatement stm = con.prepareStatement(sql);
//            stm.setInt(1, userId);
//            stm.setInt(2, friendId);
//            stm.setInt(3, friendId);
//            stm.setInt(4, userId);
//            stm.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(FriendDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public int checkExistedConversationPair(int user_id, int friend_id) {
        //nửa đầu lấy ra các lớp có chứa 1 và 3, nửa sau lấy ra các conversation có độ dài là 2
        String sql = "select b.conversation_id from \n"
                + "(select c.conversation_id, count(c.conversation_id) countConversation from conversation c \n"
                + "join conversation_member cm on c.conversation_id = cm.conversation_id\n"
                + "where cm.user_id in (? , ?) \n"
                + "group by c.conversation_id\n"
                + "having countConversation = 2) b where b.conversation_id in (select a.conversation_id \n"
                + "from (select c.conversation_id, count(cm.conversation_id) countMember from conversation c \n"
                + "join conversation_member cm on c.conversation_id = cm.conversation_id\n"
                + "group by c.conversation_id\n"
                + "having count(cm.conversation_id) = 2) a);";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user_id);
            ps.setInt(2, friend_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("conversation_id");
            }
        } catch (SQLException e) {
            Logger.getLogger(ConversationDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return 0;
    }
    
    public int checkExistedFamilyConversation(int family_id) {
        String sql = "select * from conversation c where c.family_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, family_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("conversation_id");
            }
        } catch (SQLException e) {
            Logger.getLogger(ConversationDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return 0;
    }

    public List<User> SearchAllFriendChattingPair(int currentUserId) {
        ArrayList<User> students = new ArrayList<>();
        try {
            // mục tiêu là lấy thông tin về các user tham gia vào các cuộc trò chuyện mà user có user_id = ? cũng tham gia và số lượng thành viên của mỗi cuộc trò chuyện là 2.
            //tìm những nhóm có 2 người, tìm những nhóm có 2 người và có thằng user hiện tại, tím id 
            //của những thằng mà thằng hiện tại đang chát cùng
            String sql = "select * from user where user_id in (select user_id "
                    + "from conversation_member where user_id != ? and conversation_id in (select conversation_id "
                    + "from conversation_member where user_id = ? and conversation_id in (select a.conversation_id \n"
                    + "from (select c.conversation_id, count(cm.conversation_id) countMember from conversation c \n"
                    + "join conversation_member cm on c.conversation_id = cm.conversation_id\n"
                    + "group by c.conversation_id\n"
                    + "having count(cm.conversation_id) = 2) a)))";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, currentUserId);
            ps.setInt(2, currentUserId);
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
            Logger.getLogger(ConversationDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return students;
    }

    public ConversationMessage SearchNewChatFriendChattingPair(int conversationId) {
        ConversationMessage conversation = new ConversationMessage();
        try {
            String sql = "select * from conservation_message cm\n"
                    + "where cm.conversation_id = ?\n"
                    + "order by date desc\n"
                    + "limit 1;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, conversationId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                conversation = new ConversationMessage(rs.getInt(1),
                        rs.getString(2),
                        rs.getTimestamp(3),
                        rs.getInt(4),
                        rs.getInt(5));
            }
        } catch (SQLException e) {
            Logger.getLogger(ConversationDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return conversation;
    }
    
 
    public List<FamilyInfo> SearchAllFamilyChatting(int currentUserId) {
        ArrayList<FamilyInfo> listFamily = new ArrayList<>();
        try {
            // mục tiêu là lấy thông tin về các user tham gia vào các cuộc trò chuyện mà user có user_id = ? cũng tham gia và số lượng thành viên của mỗi cuộc trò chuyện là 2.
            //tìm những nhóm có 2 người, tìm những nhóm có 2 người và có thằng user hiện tại, tím id 
            //của những thằng mà thằng hiện tại đang chát cùng
            String sql = "select f.* from conversation c join conversation_member cm\n"
                    + "on c.conversation_id = cm.conversation_id\n"
                    + "join family f on f.family_id = c.family_id\n"
                    + "where cm.user_id = ? and c.family_id is not null";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, currentUserId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FamilyInfo a = new FamilyInfo(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getDate(7));
                listFamily.add(a);
            }
        } catch (SQLException e) {
            Logger.getLogger(ConversationDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return listFamily;
    }

    public void deleteConversation(int conversationId) {
        String sql = "DELETE FROM `social`.`conversation` WHERE (`conversation_id` = ?);";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, conversationId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConversationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void BanConversation(int user_id, int conversation_id) {
        String sql = "UPDATE `social`.`conversation` SET `Status` = ? WHERE (`conversation_id` = ?);";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user_id);
            ps.setInt(2, conversation_id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConversationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void UnbanConversation(int conversation_id) {
        String sql = "UPDATE `social`.`conversation` SET `Status` = 0 WHERE (`conversation_id` = ?);";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, conversation_id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConversationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<ConversationMessage> SearchAllConversationMessage(int conversationChoosenId) {
        ArrayList<ConversationMessage> conversationMessage = new ArrayList<>();
        try {
            String sql = "SELECT * from conservation_message where conversation_id = " + conversationChoosenId + " order by date asc";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ConversationMessage a = new ConversationMessage(rs.getInt(1),
                        rs.getString(2),
                        rs.getTimestamp(3),
                        rs.getInt(4),
                        rs.getInt(5));
                conversationMessage.add(a);
            }
        } catch (SQLException e) {
            Logger.getLogger(ConversationDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return conversationMessage;
    }

    public void addConversationMessage(String description, Timestamp date, int user_id, int conversation_id) {
        String sql = "INSERT INTO `social`.`conservation_message` (`description`, `date`, `user_id`, `conversation_id`) VALUES (?, ?, ?, ?  );";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, description);
            ps.setTimestamp(2, date);
            ps.setInt(3, user_id);
            ps.setInt(4, conversation_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(ConversationDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }
    
    public void updateMessage(String description, int message_id) {
        String sql = "UPDATE `social`.`conservation_message` SET `description` = ? WHERE (`message_id` = ?);";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, description);
            ps.setInt(2, message_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(ConversationDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

  
}
