package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Notification;

public class NotifiDAO extends DBContext {

    private Connection con;
    public static final NotifiDAO INSTANCE = new NotifiDAO();

    private NotifiDAO() {
        try {
            this.con = this.getConnection();
            if (this.con == null) {
                throw new IllegalStateException("Connection to database could not be established.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error establishing database connection.", e);
        }
    }

    public void createNotification(int friendId, int notificationType, Timestamp date, String description, int relationId) {
        String insertNotificationSql = "INSERT INTO notification (user_id, notification_type, date, description, relation_id) VALUES (?, ?, ?, ?, ?);";
        try {
            try (PreparedStatement insertNotificationStmt = con.prepareStatement(insertNotificationSql)) {
                insertNotificationStmt.setInt(1, friendId);
                insertNotificationStmt.setInt(2, notificationType);
                insertNotificationStmt.setTimestamp(3, date);
                insertNotificationStmt.setString(4, description);
                insertNotificationStmt.setInt(5, relationId);
                insertNotificationStmt.executeUpdate();
            }
        } catch (SQLException e) {
            Logger.getLogger(NotifiDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void createNotificationRemineEvent(int friendId, int notificationType, Timestamp date, String description, Timestamp remindEvent, int relationId, int eventid) {
        String insertNotificationSql = "INSERT INTO notification (user_id, notification_type, date, description,remind_event, relation_id, event_id) VALUES (?,?,?,?,?,?,?);";
        try {
            try (PreparedStatement insertNotificationStmt = con.prepareStatement(insertNotificationSql)) {
                insertNotificationStmt.setInt(1, friendId);
                insertNotificationStmt.setInt(2, notificationType);
                insertNotificationStmt.setTimestamp(3, date);
                insertNotificationStmt.setString(4, description);
                insertNotificationStmt.setTimestamp(5, remindEvent);
                insertNotificationStmt.setInt(6, relationId);
                insertNotificationStmt.setInt(7, eventid);
                insertNotificationStmt.executeUpdate();
            }
        } catch (SQLException e) {
            Logger.getLogger(NotifiDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public int getFriendRequestNotificationId(int currentUserId, int friendId) {
        String sql = "SELECT n.notification_id FROM notification n "
                + "JOIN relation r ON n.relation_id = r.relation_id "
                + "WHERE ((r.user1_id = ? AND r.user2_id = ?) OR (r.user1_id = ? AND r.user2_id = ?)) "
                + "AND n.notification_type = 1";

        try (Connection con = this.getConnection(); PreparedStatement stm = con.prepareStatement(sql)) {

            stm.setInt(1, currentUserId);
            stm.setInt(2, friendId);
            stm.setInt(3, friendId);
            stm.setInt(4, currentUserId);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(NotifiDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return -1;
    }

    public List<Notification> getAllNotificationsForUser(int userId) {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT n.*, u.image AS friend_image, r.user1_id, r.user2_id FROM notification n "
                + "LEFT JOIN relation r ON n.relation_id = r.relation_id "
                + "LEFT JOIN user u ON (u.user_id = r.user1_id OR u.user_id = r.user2_id) AND u.user_id != ? "
                + "WHERE n.user_id = ? ORDER BY n.date DESC";
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, userId);
            stm.setInt(2, userId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Notification notification = new Notification(
                        rs.getInt("notification_id"),
                        rs.getInt("user_id"),
                        rs.getInt("notification_type"),
                        rs.getTimestamp("date"),
                        rs.getString("description"),
                        rs.getTimestamp("remind_event"),
                        rs.getInt("relation_id"),
                        rs.getInt("event_id"),
                        rs.getString("friend_image")
                );
                notifications.add(notification);
            }
        } catch (SQLException e) {
            Logger.getLogger(NotifiDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return notifications;
    }

    public void acceptFriendRequest(int notificationId) {
        String sqlUpdateRelation = "UPDATE relation r JOIN notification n ON r.relation_id = n.relation_id SET r.type = 1 WHERE n.notification_id = ?";
        String sqlDeleteNotification = "DELETE FROM notification WHERE notification_id = ?";

        try (PreparedStatement psUpdateRelation = con.prepareStatement(sqlUpdateRelation); PreparedStatement psDeleteNotification = con.prepareStatement(sqlDeleteNotification)) {
            psUpdateRelation.setInt(1, notificationId);
            psUpdateRelation.executeUpdate();

            psDeleteNotification.setInt(1, notificationId);
            psDeleteNotification.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(NotifiDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void rejectFriendRequest(int notificationId, int relationId) {
        String sqlDeleteNotification = "DELETE FROM notification WHERE notification_id = ?";
        try (PreparedStatement psDeleteNotification = con.prepareStatement(sqlDeleteNotification)) {
            psDeleteNotification.setInt(1, notificationId);
            int affectedRowsNotif = psDeleteNotification.executeUpdate();
            if (affectedRowsNotif > 0) {
                String sqlDeleteRelation = "DELETE FROM relation WHERE relation_id = ?";
                try (PreparedStatement psDeleteRelation = con.prepareStatement(sqlDeleteRelation)) {
                    psDeleteRelation.setInt(1, relationId);
                    psDeleteRelation.executeUpdate();
                } catch (SQLException e) {
                    Logger.getLogger(NotifiDAO.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(NotifiDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void deleteNotification(int notificationId) {
        String sql = "DELETE FROM notification WHERE notification_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, notificationId);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(NotifiDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void deleteNotificationEvent(int eventid) {
        String sql = "DELETE FROM notification WHERE event_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, eventid);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Notification deleted successfully.");
            } else {
                System.out.println("No notification found to delete.");
            }
        } catch (SQLException e) {
            Logger.getLogger(NotifiDAO.class.getName()).log(Level.SEVERE, "Error deleting notification event", e);
        }
    }

    public void updateNotification(int eventid, String newTitle) {
        String sql = "UPDATE notification SET description = CONCAT('You have been tagged in the event: \"', ?, '\" by ', (SELECT CONCAT(first_name, ' ', last_name) FROM user WHERE user_id = (SELECT user_id FROM event WHERE event_id = ?)), '.') WHERE event_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, newTitle);
            ps.setInt(2, eventid);
            ps.setInt(3, eventid);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(NotifiDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
