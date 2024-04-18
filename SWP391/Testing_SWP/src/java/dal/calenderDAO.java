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
import model.*;

public class calenderDAO extends DBContext {

    private Connection con;
    private String status = "";
    private List<Event> userca;
    public static calenderDAO INSTANCE = new calenderDAO();

    public calenderDAO() {
        if (INSTANCE == null) {
            try {
                con = new DBContext().getConnection();
            } catch (Exception e) {
                status = "Error ar connection" + e.getMessage();
            }
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Event> getUserca() {
        return userca;
    }

    public void setUserca(List<Event> userca) {
        this.userca = userca;
    }

    public void loadCalendar() {
        userca = new Vector<>();
        String sql = "SELECT * FROM events";
        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                userca.add(new Event(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDate(3),
                        rs.getDate(4)
                ));
            }

        } catch (SQLException e) {
            status = "Error at loading events: " + e.getMessage();
        }
    }

}
