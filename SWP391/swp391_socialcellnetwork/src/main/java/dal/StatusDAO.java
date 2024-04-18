/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import models.*;
import java.sql.*;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ACER
 */
public class StatusDAO extends DBContext {

    private List<Status> statusInfo;
    private List<Status> statusInfoByUserId;
    private List<Status> statusInfoByFamilyId;

    public static StatusDAO INSTANCE = new StatusDAO();

    private Connection con;

    public StatusDAO() {
        if (INSTANCE == null) {
            try {
                con = new DBContext().getConnection();
            } catch (Exception e) {
                Logger.getLogger(StatusDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
            }
        }
    }

    public List<Status> getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(List<Status> statusInfo) {
        this.statusInfo = statusInfo;
    }

    public List<Status> getStatusInfoByUserId() {
        return statusInfoByUserId;
    }

    public void setStatusInfoByUserId(List<Status> statusInfoByUserId) {
        this.statusInfoByUserId = statusInfoByUserId;
    }

    public List<Status> getStatusInfoByFamilyId() {
        return statusInfoByFamilyId;
    }

    public void setStatusInfoByFamilyId(List<Status> statusInfoByFamilyId) {
        this.statusInfoByFamilyId = statusInfoByFamilyId;
    }

    public void loadStatus(int id) {
        statusInfo = new Vector<>();
        String sql = "select * from status\n"
                + "where ((user_id =? or user_id in(\n"
                + "(select user1_id from relation where user2_id=?) union\n"
                + "(select user2_id from relation where user1_id=?))) and access=0) or\n"
                + "(access = 1 and family_id in (select family_id from family_member where user_id =?))\n"
                + "order by date desc;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, id);
            ps.setInt(3, id);
            ps.setInt(4, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                statusInfo.add(new Status(rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getTimestamp(6),
                        rs.getDate(7),
                        rs.getString(8),
                        rs.getInt(9)
                ));
            }
        } catch (SQLException e) {
            Logger.getLogger(StatusDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

    public void updateStatus(int status_id, String img, String description) {
        String sql = "UPDATE social.`status` SET description = ? , img = ? WHERE (status_id = ?); ";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, description);
            ps.setString(2, img);
            ps.setInt(3, status_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(StatusDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

    public void loadStatusTop100(int id, int count) {
        statusInfo = new Vector<>();
        String sql = "select * from status\n"
                + "where ((user_id =? or user_id in(\n"
                + "(select user1_id from relation where user2_id=?) union\n"
                + "(select user2_id from relation where user1_id=?))) and access=0) or\n"
                + "(access = 1 and family_id in (select family_id from family_member where user_id =?))\n"
                + "order by date desc\n"
                + "limit 10 offset ?;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, id);
            ps.setInt(3, id);
            ps.setInt(4, id);
            ps.setInt(5, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                statusInfo.add(new Status(rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getTimestamp(6),
                        rs.getDate(7),
                        rs.getString(8),
                        rs.getInt(9)
                ));
            }
        } catch (SQLException e) {
            Logger.getLogger(StatusDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

    public void loadStatusByUserId(int user_id) {
        statusInfoByUserId = new Vector<>();
        String sql = "SELECT * FROM social.status where user_id =? and access != 1 order by date desc;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                statusInfoByUserId.add(new Status(rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getTimestamp(6),
                        rs.getDate(7),
                        rs.getString(8),
                        rs.getInt(9)
                ));
            }
        } catch (SQLException e) {
            Logger.getLogger(StatusDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

    public void loadStatusByFamilyId(int family_id) {
        statusInfoByFamilyId = new Vector<>();
        String sql = "select * from status where family_id =? and access =1 order by date desc;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, family_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                statusInfoByFamilyId.add(new Status(rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getTimestamp(6),
                        rs.getDate(7),
                        rs.getString(8),
                        rs.getInt(9)
                ));
            }
        } catch (SQLException e) {
            Logger.getLogger(StatusDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

    public Status GetStatusInfoById(int id) {
        String sql = "select * from status where status_id = ?";
        Status status = null;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                status = new Status(rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getTimestamp(6),
                        rs.getDate(7),
                        rs.getString(8),
                        rs.getInt(9)
                );
            }
        } catch (SQLException e) {
            Logger.getLogger(StatusDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return status;
    }

    public int addStatus(int user_id, String img, String description, Timestamp date, Date date_memory, String place, int access) {
        String sql = "INSERT INTO `social`.`status` (`user_id`,`img` ,`description`, `date`,`date_memory`,`place`, `access`) VALUES (?,?,?,?,?,?,?);";
        int statusId = -1;

        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, user_id);
            ps.setString(2, img);
            ps.setString(3, description);
            ps.setTimestamp(4, date);
            ps.setDate(5, date_memory);
            ps.setString(6, place);
            ps.setInt(7, access);

            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                statusId = generatedKeys.getInt(1);
            }

        } catch (SQLException e) {
            Logger.getLogger(StatusDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return statusId;
    }

    public void addFamilyStatus(int user_id, int family_id, String img, String description, Timestamp date, int access) {
        String sql = "INSERT INTO `social`.`status` (`user_id`,`family_id`,`img` ,`description`, `date`,`access`) VALUES (?,?,?,?,?,?);";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user_id);
            ps.setInt(2, family_id);
            ps.setString(3, img);
            ps.setString(4, description);
            ps.setTimestamp(5, date);
            ps.setInt(6, access);

            ps.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(StatusDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

    public void updateStatus(int status_id, String img, String description, Date date_memory, String place) {
        String sql = "UPDATE `social`.`status` SET `description` = ? , `img` = ?,`date_memory`= ?,`place`=? WHERE (`status_id` = ?); ";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, description);
            ps.setString(2, img);
            ps.setDate(3, date_memory);
            ps.setString(4, place);
            ps.setInt(5, status_id);

            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(StatusDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

    public void deleteStatus(int status_id) {
        String sql = "delete from status  where status_id= ?; ";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, status_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(StatusDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

    //    react
    public void addReaction(int user_id, int staus_id) {
        String sql = "insert into `social`.`interaction` (`user_id`, `status_id`) values(?, ?);";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user_id);
            ps.setInt(2, staus_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(StatusDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

    public int checkReaction(int user_id, int staus_id) {
        String sql = "select interaction_id from interaction where user_id = ? and status_id = ?";
        int interaction_id = -1;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user_id);
            ps.setInt(2, staus_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(StatusDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return interaction_id;
    }

    public void deleteReaction(int interaction_id) {
        String sql = "delete from interaction  where interaction_id = ? ; ";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, interaction_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(StatusDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

    public int countReaction(int status_id) {
        String sql = "select count(interaction_id) from interaction where status_id = ?";
        int count = 0;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, status_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(StatusDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return count;
    }

    public void updateAudienceEditor(int status_id, int access) {
        String sql = "update status set access = ? where status_id=?;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, access);
            ps.setInt(2, status_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(StatusDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

    public int getNewStatusId(int user_id) {
        String sql = "select status_id from status where user_id= ? order by status_id desc limit 1;";
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

    public static void main(String[] args) {
        //StatusDAO.INSTANCE.loadStatusByFamilyId(2);
        // StatusDAO.INSTANCE.updateAudienceEditor(25, 0);
        //System.out.println(StatusDAO.INSTANCE.getStatusInfoByFamilyId().get(1).getDescription());
    }
}
