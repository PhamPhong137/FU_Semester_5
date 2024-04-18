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
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.*;

public class CalendarDAO extends DBContext {

    private Connection con;

    public static CalendarDAO INSTANCE = new CalendarDAO();

    public CalendarDAO() {
        if (INSTANCE == null) {
            try {
                con = new DBContext().getConnection();
            } catch (Exception e) {
                Logger.getLogger(CalendarDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
            }
        }
    }

    public List<Event> loadEventByFamilyId(int userId) {
        List<Event> eventFamilyList = new Vector<>();
        String sql = "SELECT * FROM event WHERE family_id = ? ";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                eventFamilyList.add(new Event(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getTimestamp(5),
                        rs.getTimestamp(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getString(9),
                        rs.getInt(10)
                ));
            }
        } catch (SQLException e) {
            Logger.getLogger(CalendarDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return eventFamilyList;
    }

    public List<Event> loadAllEventByUserId(int userId) {
        List<Event> loadAllEventByUserId = new Vector<>();
        String sql = "SELECT e.event_id, e.user_id, e.family_id, e.name, e.start_date, e.end_date, e.description, e.frequency, e.place,e.access,'#00BFFF' as color\n"
                + "                FROM event e\n"
                + "            WHERE user_id = ? AND family_id IS NULL\n"
                + "                union all\n"
                + "                SELECT e.event_id, e.user_id, e.family_id, e.name, e.start_date, e.end_date, e.description, e.frequency, e.place,e.access,'#32CD32'as color\n"
                + "                FROM event e\n"
                + "                INNER JOIN relation r ON (e.user_id = r.user2_id AND r.user1_id = ?) OR (e.user_id = r.user1_id AND r.user2_id = ?)\n"
                + "                 WHERE r.type = 1 \n"
                + "                AND e.access = 0 and e.family_id is null\n"
                + "                union all\n"
                + "                SELECT e.event_id, e.user_id, e.family_id, e.name, e.start_date, e.end_date, e.description, e.frequency, e.place,e.access,'#32CD32'as color\n"
                + "FROM event e\n"
                + "JOIN event_tag et ON e.event_id = et.event_id\n"
                + "WHERE et.user_id = ? AND e.access = 1\n"
                + "                union all\n"
                + "              SELECT e.event_id, e.user_id, e.family_id, e.name, e.start_date, e.end_date, e.description, e.frequency, e.place,e.access,'orange'as color \n"
                + "                FROM event e\n"
                + "             JOIN family_member fm ON e.family_id = fm.family_id\n"
                + "            WHERE fm.user_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            ps.setInt(3, userId);
            ps.setInt(4, userId);
            ps.setInt(5, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                loadAllEventByUserId.add(new Event(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getTimestamp(5),
                        rs.getTimestamp(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getString(9),
                        rs.getInt(10),
                        rs.getString(11)
                ));
            }
        } catch (SQLException e) {
            Logger.getLogger(CalendarDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return loadAllEventByUserId;
    }

    public int addEvent(int user_id, String title_event, String start_date, String description, int frequency, String place, int access) {
        String sql = "INSERT INTO `social`.`event` (`user_id`, `name`, `start_date`, `description`, `frequency`, `place`,`access`) VALUES (?,?, ?, ?, ?,?,?);";

        int insertedID = 0;
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, user_id);
            ps.setString(2, title_event);
            ps.setString(3, start_date);
            ps.setString(4, description);
            ps.setInt(5, frequency);
            ps.setString(6, place);
            ps.setInt(7, access);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                insertedID = rs.getInt(1);
            }

        } catch (SQLException e) {
            Logger.getLogger(CalendarDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return insertedID;
    }

    public void addFamilyEvent(int user_id, int family_id, String title_event, String start_date, String description, int frequency, String place) {
        String sql = "INSERT INTO `social`.`event` (`user_id`, `family_id`, `name`, `start_date`, `description`, `frequency`, `place`) VALUES (?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user_id);
            ps.setInt(2, family_id);
            ps.setString(3, title_event);
            ps.setString(4, start_date);
            ps.setString(5, description);
            ps.setInt(6, frequency);
            ps.setString(7, place);

            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(CalendarDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

    public List<Event> loadFamilyEventsByStartDate(int familyid, String start_date) {
        List<Event> showFamilyEventByStartDate = new Vector<>();
        String sql = "SELECT * FROM event WHERE family_id = ? AND start_date = ?;";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, familyid);
            ps.setString(2, start_date);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                showFamilyEventByStartDate.add(new Event(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getTimestamp(5),
                        rs.getTimestamp(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getString(9),
                        rs.getInt(10)
                ));
            }
        } catch (SQLException e) {
            Logger.getLogger(CalendarDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return showFamilyEventByStartDate;
    }

    public List<EventUser> loadAllEventsByUserIdAndStartDate(int userid, String start_date) {
        List<EventUser> loadAllEventsByUserIdAndStartDate = new Vector<>();
        String sql = "SELECT e.event_id, e.user_id, e.family_id, e.name AS title, CONCAT(u.first_name, ' ', u.last_name) AS name, e.start_date, e.end_date, e.description, e.frequency, e.place, e.access\n"
                + "FROM event e\n"
                + "JOIN `user` u ON e.user_id = u.user_id\n"
                + "WHERE e.user_id = ? AND family_id IS NULL AND start_date = ?\n"
                + "UNION ALL\n"
                + "SELECT e.event_id, e.user_id, e.family_id, e.name AS title, CONCAT(u.first_name, ' ', u.last_name) AS name, e.start_date, e.end_date, e.description, e.frequency, e.place, e.access\n"
                + "FROM event e\n"
                + "JOIN `user` u ON e.user_id = u.user_id\n"
                + "INNER JOIN relation r ON (e.user_id = r.user2_id AND r.user1_id = ?) OR (e.user_id = r.user1_id AND r.user2_id = ?)\n"
                + "WHERE r.type = 1\n"
                + "AND e.access = 0 AND e.family_id IS NULL AND start_date = ?\n"
                + "UNION ALL\n"
                + "SELECT e.event_id, e.user_id, e.family_id, e.name AS title, CONCAT(u.first_name, ' ', u.last_name) AS name, e.start_date, e.end_date, e.description, e.frequency, e.place, e.access\n"
                + "FROM event e\n"
                + "JOIN event_tag et ON e.event_id = et.event_id\n"
                + "JOIN `user` u ON e.user_id = u.user_id\n"
                + "WHERE et.user_id = ? AND e.access = 1 and e.family_id is null AND start_date = ?\n"
                + "union all\n"
                + "SELECT e.event_id, e.user_id, e.family_id, e.name, f.name AS family_name, e.start_date, e.end_date, e.description, e.frequency, e.place, e.access\n"
                + "FROM event e\n"
                + "JOIN `user` u ON e.user_id = u.user_id\n"
                + "JOIN family f ON e.family_id = f.family_id\n"
                + "JOIN family_member fm ON e.family_id = fm.family_id\n"
                + "WHERE fm.user_id = ? AND start_date = ?;";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userid);
            ps.setString(2, start_date);
            ps.setInt(3, userid);
            ps.setInt(4, userid);
            ps.setString(5, start_date);
            ps.setInt(6, userid);
            ps.setString(7, start_date);
            ps.setInt(8, userid);
            ps.setString(9, start_date);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                loadAllEventsByUserIdAndStartDate.add(new EventUser(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getTimestamp(6),
                        rs.getTimestamp(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getString(10),
                        rs.getInt(11)
                ));
            }
        } catch (SQLException e) {
            Logger.getLogger(CalendarDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return loadAllEventsByUserIdAndStartDate;
    }

    public void updateEvent(int eventId, String title_event, int feq, String description, String place, int access) {
        String sql = "UPDATE `social`.`event` SET `name` = ?, `description` = ?, `frequency` = ?, `place` = ?,`access`= ? WHERE (`event_id` = ?);";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, title_event);
            ps.setString(2, description);
            ps.setInt(3, feq);
            ps.setString(4, place);
            ps.setInt(5, access);
            ps.setInt(6, eventId);
            int rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(CalendarDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

    public void deleteEvent(int eventId) {
        String sql = "DELETE FROM event WHERE event_id = ?;";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, eventId);
            int rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(CalendarDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

    public List<UpComingEvent> loadEventUpComing(int userId) {
        List<UpComingEvent> showUpComingEvent = new Vector<>();

        String sql = "SELECT \n"
                + "    e.event_id, \n"
                + "    e.family_id, \n"
                + "    e.user_id, \n"
                + "    e.name AS title, \n"
                + "    CONCAT(u.first_name, ' ', u.last_name) AS name, \n"
                + "    u.image, \n"
                + "    e.start_date, \n"
                + "    DATEDIFF(e.start_date, CURDATE()) AS dayafter, \n"
                + "    e.description, \n"
                + "    e.place\n"
                + "FROM \n"
                + "    event e\n"
                + "JOIN \n"
                + "    user u ON e.user_id = u.user_id\n"
                + "WHERE \n"
                + "    e.user_id = ? \n"
                + "    AND e.family_id IS NULL \n"
                + "    AND start_date > CURDATE() \n"
                + "    AND start_date <= CURDATE() + INTERVAL 1 MONTH\n"
                + "\n"
                + "UNION ALL\n"
                + "\n"
                + "SELECT \n"
                + "    e.event_id, \n"
                + "    e.family_id, \n"
                + "    e.user_id, \n"
                + "    e.name AS title, \n"
                + "    CONCAT(u.first_name, ' ', u.last_name) AS name, \n"
                + "    u.image, \n"
                + "    e.start_date, \n"
                + "    DATEDIFF(e.start_date, CURDATE()) AS dayafter, \n"
                + "    e.description, \n"
                + "    e.place\n"
                + "FROM \n"
                + "    event e\n"
                + "INNER JOIN \n"
                + "    relation r ON (e.user_id = r.user2_id AND r.user1_id = ?) OR (e.user_id = r.user1_id AND r.user2_id = ?)\n"
                + "JOIN \n"
                + "    user u ON e.user_id = u.user_id\n"
                + "WHERE \n"
                + "    r.type = 1 \n"
                + "    AND e.access = 0 \n"
                + "    AND e.family_id IS NULL \n"
                + "    AND start_date > CURDATE() \n"
                + "    AND start_date <= CURDATE() + INTERVAL 1 MONTH\n"
                + "\n"
                + "UNION ALL\n"
                + "SELECT \n"
                + "    e.event_id, \n"
                + "    e.family_id, \n"
                + "    e.user_id, \n"
                + "    e.name AS title, \n"
                + "    CONCAT(u.first_name, ' ', u.last_name) AS name, \n"
                + "    u.image, \n"
                + "    e.start_date, \n"
                + "    DATEDIFF(e.start_date, CURDATE()) AS dayafter, \n"
                + "    e.description, \n"
                + "    e.place\n"
                + "     FROM event e\n"
                + "                JOIN event_tag et ON e.event_id = et.event_id\n"
                + "                JOIN user u ON e.user_id = u.user_id\n"
                + "             WHERE et.user_id = ? AND e.access = 1\n"
                + "             AND start_date > CURDATE() \n"
                + "    AND start_date <= CURDATE() + INTERVAL 1 MONTH\n"
                + "\n"
                + "\n"
                + "union all\n"
                + "\n"
                + "SELECT \n"
                + "    e.event_id, \n"
                + "    e.family_id, \n"
                + "    e.user_id, \n"
                + "    e.name, \n"
                + "    f.name AS family_name, \n"
                + "    f.img AS family_image, \n"
                + "    e.start_date, \n"
                + "    DATEDIFF(e.start_date, CURDATE()) AS dayafter, \n"
                + "    e.description, \n"
                + "    e.place\n"
                + "FROM \n"
                + "    event e\n"
                + "INNER JOIN \n"
                + "    family_member fm ON e.family_id = fm.family_id\n"
                + "JOIN \n"
                + "    family f ON e.family_id = f.family_id\n"
                + "WHERE \n"
                + "    fm.user_id = ? \n"
                + "    AND start_date > CURDATE() \n"
                + "    AND start_date <= CURDATE() + INTERVAL 1 MONTH\n"
                + "\n"
                + "ORDER BY \n"
                + "    dayafter";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            ps.setInt(3, userId);
            ps.setInt(4, userId);
            ps.setInt(5, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                showUpComingEvent.add(new UpComingEvent(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getTimestamp(7),
                        rs.getInt(8),
                        rs.getString(9),
                        rs.getString(10)
                ));
            }
        } catch (SQLException e) {
            Logger.getLogger(CalendarDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return showUpComingEvent;
    }

    public List<UpComingEvent> loadFamilyEventUpComing(int familyid) {
        List<UpComingEvent> showUpComingFamilyEvent = new Vector<>();

        String sql = " SELECT e.event_id, e.family_id, e.user_id, e.name, f.name AS family_name, f.img AS family_image, e.start_date, DATEDIFF(e.start_date, CURDATE()) AS dayafter, e.description, e.place\n"
                + "FROM event e\n"
                + "JOIN family f ON e.family_id = f.family_id\n"
                + "WHERE e.family_id = ? AND start_date > CURDATE() AND start_date <= CURDATE() + INTERVAL 1 MONTH\n"
                + "order by dayafter";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, familyid);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                showUpComingFamilyEvent.add(new UpComingEvent(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getTimestamp(7),
                        rs.getInt(8),
                        rs.getString(9),
                        rs.getString(10)
                ));
            }
        } catch (SQLException e) {
            Logger.getLogger(CalendarDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return showUpComingFamilyEvent;
    }

    public List<EventUser> loadAllEventByMonthAndYear(int userId, String month, String year) {
        List<EventUser> alleventListMonth = new Vector<>();
        String sql = "SELECT e.event_id, e.family_id, e.user_id, e.name AS title, CONCAT(u.first_name, ' ', u.last_name) AS name, u.image, e.start_date, e.end_date, e.description, e.frequency, e.place, e.access, '#00BFFF' AS color\n"
                + "FROM event e\n"
                + "JOIN user u ON e.user_id = u.user_id\n"
                + "WHERE e.user_id = ? AND e.family_id IS NULL AND MONTH(e.start_date) = ? AND YEAR(e.start_date) = ?\n"
                + "\n"
                + "UNION ALL\n"
                + "\n"
                + "SELECT e.event_id, e.family_id, e.user_id, e.name AS title, CONCAT(u.first_name, ' ', u.last_name) AS name, u.image, e.start_date, e.end_date, e.description, e.frequency, e.place, e.access, '#32CD32' AS color\n"
                + "FROM event e\n"
                + "INNER JOIN relation r ON (e.user_id = r.user2_id AND r.user1_id = ?) OR (e.user_id = r.user1_id AND r.user2_id = ?)\n"
                + "JOIN user u ON e.user_id = u.user_id\n"
                + "WHERE r.type = 1 AND e.access = 0 AND e.family_id IS NULL AND MONTH(e.start_date) = ? AND YEAR(e.start_date) = ?\n"
                + "\n"
                + "UNION ALL\n"
                + "\n"
                + "SELECT e.event_id, e.family_id, e.user_id, e.name AS title, CONCAT(u.first_name, ' ', u.last_name) AS name, u.image, e.start_date, e.end_date, e.description, e.frequency, e.place, e.access, '#32CD32' AS color\n"
                + "FROM event e\n"
                + "JOIN event_tag et ON e.event_id = et.event_id\n"
                + "JOIN user u ON e.user_id = u.user_id\n"
                + "WHERE et.user_id = ? AND e.access = 1 and MONTH(e.start_date) = ? AND YEAR(e.start_date) = ?\n"
                + "\n"
                + "UNION ALL\n"
                + "\n"
                + "SELECT e.event_id, e.family_id, e.user_id, e.name AS title, f.name AS family_name, f.img AS family_image, e.start_date, e.end_date, e.description, e.frequency, e.place, e.access, 'orange' AS color\n"
                + "FROM event e\n"
                + "INNER JOIN family_member fm ON e.family_id = fm.family_id\n"
                + "JOIN family f ON e.family_id = f.family_id\n"
                + "WHERE fm.user_id = ? AND MONTH(e.start_date) = ? AND YEAR(e.start_date) = ?;";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, month);
            ps.setString(3, year);
            ps.setInt(4, userId);
            ps.setInt(5, userId);
            ps.setString(6, month);
            ps.setString(7, year);
            ps.setInt(8, userId);
            ps.setString(9, month);
            ps.setString(10, year);
            ps.setInt(11, userId);
            ps.setString(12, month);
            ps.setString(13, year);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                alleventListMonth.add(new EventUser(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getTimestamp(7),
                        rs.getTimestamp(8),
                        rs.getString(9),
                        rs.getInt(10),
                        rs.getString(11),
                        rs.getInt(12),
                        rs.getString(13)
                ));
            }
        } catch (SQLException e) {
            Logger.getLogger(CalendarDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return alleventListMonth;
    }

    public List<EventUser> loadMyEventByMonthAndYear(int userId, String month, String year) {
        List<EventUser> loadMyEventByMonthAndYear = new Vector<>();
        String sql = "(\n"
                + "    SELECT e.event_id, e.family_id, e.user_id, e.name AS title, CONCAT(u.first_name, ' ', u.last_name) AS name, u.image, e.start_date, e.end_date, e.description, e.frequency, e.place,e.access,'#00BFFF' as color\n"
                + "    FROM event e\n"
                + "    JOIN user u ON e.user_id = u.user_id\n"
                + "    WHERE e.user_id = ? and e.family_id is null and MONTH(start_date) = ? AND YEAR(start_date) = ?\n"
                + ")";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, month);
            ps.setString(3, year);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                loadMyEventByMonthAndYear.add(new EventUser(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getTimestamp(7),
                        rs.getTimestamp(8),
                        rs.getString(9),
                        rs.getInt(10),
                        rs.getString(11),
                        rs.getInt(12),
                        rs.getString(13)
                ));
            }
        } catch (SQLException e) {
            Logger.getLogger(CalendarDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return loadMyEventByMonthAndYear;
    }

    public List<Event> loadFamilyEventByMonth(int familyid, String month, String year) {
        List<Event> allFamilyEventByMonth = new Vector<>();
        String sql = "SELECT *\n"
                + "FROM event e\n"
                + "WHERE e.family_id =? and MONTH(start_date) = ? AND YEAR(start_date) = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, familyid);
            ps.setString(2, month);
            ps.setString(3, year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                allFamilyEventByMonth.add(new Event(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getTimestamp(5),
                        rs.getTimestamp(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getString(9),
                        rs.getInt(10)
                ));
            }
        } catch (SQLException e) {
            Logger.getLogger(CalendarDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return allFamilyEventByMonth;
    }

    public Event getInfoByEventId(int eventid) {
    Event event = null;
    String sql = "SELECT * FROM event WHERE eventid=?";

    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, eventid);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            event = new Event(
                rs.getInt("eventid"),
                rs.getInt("field2"), // Thay "field2" bằng tên cột thực tế trong cơ sở dữ liệu
                rs.getInt("field3"),
                rs.getString("field4"),
                rs.getTimestamp("field5"),
                rs.getTimestamp("field6"),
                rs.getString("field7"),
                rs.getInt("field8"),
                rs.getString("field9"),
                rs.getInt("field10")
            );
        }
        rs.close();
        ps.close();
    } catch (SQLException e) {
        Logger.getLogger(CalendarDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
    }
    return event;
}

}
