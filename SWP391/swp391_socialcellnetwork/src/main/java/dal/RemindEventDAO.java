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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Remind;
import models.RemindEvent;

/**
 *
 * @author PC-Phong
 */
public class RemindEventDAO extends DBContext {

    private Connection con;
    public static RemindEventDAO INSTANCE = new RemindEventDAO();

    public RemindEventDAO() {
        if (INSTANCE == null) {
            try {
                con = new DBContext().getConnection();
            } catch (Exception e) {
                Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
            }
        }
    }

    public void addRemindEvent(int event_id, String remind_title, String date, int userid_target) {
        String sql = "INSERT INTO `social`.`remind` (`event_id`, `remind_title`, `date`, `userid_target`) VALUES (?, ?, ?, ?);";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, event_id);
            ps.setString(2, remind_title);
            ps.setString(3, date);
            ps.setInt(4, userid_target);

            ps.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(CalendarDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

    public List<Remind> showRemindByEventId(int event_id, int userid) {
        List<Remind> showRemindByEventId = new Vector<>();
        String sql = "select r.remind_id,r.event_id,r.remind_title,r.date,r.userid_target,DATEDIFF(e.start_date, r.date) as day\n"
                + "from  remind r \n"
                + "join event e on e.event_id=r.event_id             \n"
                + "where r.event_id =?  and r.userid_target =? \n"
                + "order by day ";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, event_id);
            ps.setInt(2, userid);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                showRemindByEventId.add(new Remind(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getTimestamp(4),
                        rs.getInt(5),
                        rs.getInt(6)
                ));
            }
        } catch (SQLException e) {
            Logger.getLogger(CalendarDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return showRemindByEventId;
    }

    public List<RemindEvent> showRemindEventByTargetUser(int userId) {
        List<RemindEvent> showRemindEventByTargetUser = new Vector<>();

        String sql = "SELECT e.event_id, e.user_id, e.name,\"\", CONCAT(u.first_name, ' ', u.last_name) AS full_name, \n"
                + "                     u.image, 0 AS days_after, e.start_date, \n"
                + "                       e.description, e.frequency, e.place\n"
                + "                FROM event e\n"
                + "                JOIN `user` u ON u.user_id = e.user_id\n"
                + "                WHERE e.start_date = CURRENT_DATE AND e.user_id = ? AND e.family_id IS NULL\n"
                + "                union all\n"
                + "SELECT e.event_id, e.user_id, e.name,r.remind_title as remindtitle , CONCAT(u.first_name, ' ', u.last_name) AS full_name, \n"
                + "                      u.image, DATEDIFF(e.start_date, CURRENT_DATE) AS days_after, e.start_date, \n"
                + "                     e.description, e.frequency, e.place\n"
                + "                FROM event e\n"
                + "               JOIN `user` u ON u.user_id = e.user_id\n"
                + "              JOIN remind r ON e.event_id = r.event_id\n"
                + "               WHERE r.`date` = CURRENT_DATE AND r.userid_target = ? and e.family_id IS NULL\n"
                + "                UNION ALL\n"
                + "                SELECT e.event_id, e.user_id, e.name,r.remind_title ,f.name AS full_name, \n"
                + "                      f.img AS image, DATEDIFF(e.start_date, CURRENT_DATE) AS days_after, e.start_date, \n"
                + "                      e.description, e.frequency, e.place\n"
                + "              FROM event e\n"
                + "               JOIN `family` f ON e.family_id = f.family_id\n"
                + "                JOIN remind r ON e.event_id = r.event_id\n"
                + "             WHERE r.`date` = CURRENT_DATE AND r.userid_target = ?\n"
                + "                      ";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            ps.setInt(3, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                showRemindEventByTargetUser.add(new RemindEvent(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getTimestamp(8),
                        rs.getString(9),
                        rs.getInt(10),
                        rs.getString(11)
                ));
            }
        } catch (SQLException e) {
            Logger.getLogger(CalendarDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return showRemindEventByTargetUser;
    }
    public void deleteRemind(int remindid) {
        String sql = "DELETE FROM remind WHERE remind_id = ?;";
             
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, remindid);
            int rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(CalendarDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

}
