/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import com.mysql.cj.callback.UsernameCallback;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import model.*;

/**
 *
 * @author Admin
 */
public class AccountDAO extends DBContext {

    private Connection con;
    private String status = "";
    private List<Account> accList;
    public static AccountDAO INSTANCE = new AccountDAO();

    public AccountDAO() {
        if (INSTANCE == null) {
            try {
                con = new DBContext().getConnection();
            } catch (Exception e) {
                status = "Error ar connection" + e.getMessage();
            }
        }
    }

    public List<Account> getAccList() {
        return accList;
    }

    public void setAccList(List<Account> accList) {
        this.accList = accList;
    }

    public void loadAccount() {
        accList = new Vector<>();
        String sql = "select * from accounts";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                accList.add(new Account(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getBoolean(5)
                ));
            }
        } catch (SQLException e) {
            status = "Error at load Account " + e.getMessage();
        }
    }

    public boolean checkLogin(String username, String password) {
        for (Account acc : accList) {
            if (acc.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkExisted(String username) {
        for (Account acc : accList) {
            if (acc.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    public Account getAccByUsername(String username) {
        Account account = null;
        String sql = "SELECT * FROM accounts WHERE username = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                account = new Account(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getBoolean(5));
            }
        } catch (SQLException e) {
            status = "Error at get Acc";
        }
        return account;
    }

    public void addNewUsernameAndPass(Account a) {
        String sql = "INSERT INTO `social_networking`.`accounts`\n"
                + "(`username`,`password`)\n"
                + "VALUES\n"
                + "(?,?);";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, a.getUsername());
            ps.setString(2, a.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            status = "Error at addNewUsernameAndPass " + e.getMessage();
        }
    }

}
