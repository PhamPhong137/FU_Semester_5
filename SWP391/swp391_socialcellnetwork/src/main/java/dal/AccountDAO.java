/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.*;

/**
 *
 * @author Admin
 */
public class AccountDAO extends DBContext {

    private Connection con;
    public static AccountDAO INSTANCE = new AccountDAO();
    private List<Account> accList;

    public AccountDAO() {
        if (INSTANCE == null) {
            try {
                con = new DBContext().getConnection();
            } catch (Exception e) {
                Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
            }
        }
    }

    public List<Account> getAccList() {
        return accList;
    }

    public void setAccList(List<Account> accList) {
        this.accList = accList;
    }

    public List<Account> getAllAccounts() {
        accList = new Vector<>();
        String sql = "SELECT * FROM account\n"
                + "ORDER BY role,\n"
                + "CASE \n"
                + "	WHEN operating_date IS NULL THEN 1 \n"
                + "	ELSE 0 \n"
                + "END, \n"
                + "CASE \n"
                + "	WHEN operating_date IS NULL THEN creation_date \n"
                + "	ELSE operating_date \n"
                + "END DESC;\n"
                + "\n"
                + "";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                accList.add(new Account(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getTimestamp(7),
                        rs.getTimestamp(8)
                ));
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return accList;
    }

    public boolean checkLogin(String userOrGmail, String password) throws NoSuchAlgorithmException {
        String passwordEncryption = SHAHash.createSHA(password);
        String sql = "SELECT * FROM `social`.`account` WHERE (username = ? or email = ?) AND password = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, userOrGmail);
            ps.setString(2, userOrGmail);
            ps.setString(3, passwordEncryption);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return false;
    }

    public boolean checkSuspendedAccount(int user_id) {
        String sql = "SELECT is_banned FROM social.account WHERE user_id = ? AND is_banned = 0";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return false;
    }

    public boolean checkExistedUser(String username) {
        String sql = "SELECT * FROM `social`.`account` WHERE username = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return false;
    }

    public void addAccount(int userId, String username, String email, String password, Timestamp creation_date) throws NoSuchAlgorithmException {
        String passwordEncryption = SHAHash.createSHA(password);
        String sql = "INSERT INTO `social`.`account` (`user_id`, `username`, `password`, `email`, `is_banned`, `role`, `creation_date`) VALUES (?, ?, ?, ?, 0, 1, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, username);
            ps.setString(3, passwordEncryption);
            ps.setString(4, email);
            ps.setTimestamp(5, creation_date);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
            e.printStackTrace();
        }
    }

    public void addAccountByAdmin(int userId, String username, String email, String password, int role, Timestamp creation_date) throws NoSuchAlgorithmException {
        String passwordEncryption = SHAHash.createSHA(password);
        String sql = "INSERT INTO `social`.`account` (`user_id`, `username`, `password`, `email`, `is_banned`, `role`, `creation_date`) VALUES (?, ?, ?, ?, 0, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, username);
            ps.setString(3, passwordEncryption);
            ps.setString(4, email);
            ps.setInt(5, role);
            ps.setTimestamp(6, creation_date);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
            e.printStackTrace();
        }
    }

    public int getUserIdByUsernameOrEmail(String userOrGmail) {
        String sql = "SELECT user_id FROM account WHERE (username = ? or email = ?)";
        int user_id = 0;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, userOrGmail);
            ps.setString(2, userOrGmail);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user_id = rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
            e.printStackTrace();
        }
        return user_id;
    }

    public Account getAccountByEmail(String email) {
        Account account = null;
        String sql = "SELECT * FROM account WHERE email = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                account = new Account(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getTimestamp(7),
                        rs.getTimestamp(8)
                );
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return account;
    }

    public void updateRoleAccountById(int id, int role) {
        String sql = "UPDATE `social`.`account` SET `role` = ? WHERE (`user_id` = ?);";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, role);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

    public void updateStatusAccountById(int id, int isBanned) {
        String sql = "UPDATE `social`.`account` SET `is_banned` = ? WHERE (`user_id` = ?);";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, isBanned);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

    public User getUserById(int userId) {
        User user = null;
        try {
            String sql = "SELECT user_id, first_name, last_name, phone_number, image "
                    + "FROM user "
                    + "WHERE user_id = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, userId);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUser_id(rs.getInt("user_id"));
                user.setF_name(rs.getString("first_name"));
                user.setL_name(rs.getString("last_name"));
                user.setPhone_number(rs.getString("phone_number"));
                user.setImage(rs.getString("image"));
            }
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return user;
    }

    public void deleteAccountById(int id) {
        String sql = "delete from account where user_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

    public void updateAccountById(int id, int isBanned, int role) {
        String sql = "UPDATE `social`.`account` SET `is_banned` = ?, `role` = ? WHERE (`user_id` = ?);";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, isBanned);
            ps.setInt(2, role);
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

    public Account getAccountById(int userId) {
        Account account = null;
        String sql = "SELECT * FROM account WHERE user_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                account = new Account(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getTimestamp(7),
                        rs.getTimestamp(8)
                );
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return account;
    }

    public List<Account> searchAccount(String key) {
        List<Account> accounts = new Vector<>();
        String sql = "SELECT * FROM account WHERE username LIKE ? OR email LIKE ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + key + "%");
            ps.setString(2, "%" + key + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                accounts.add(new Account(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getTimestamp(7),
                        rs.getTimestamp(8)
                ));
            }
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return accounts;
    }

    public List<Account> getAccountsByPage(int currentPage, int pageLimit, List<Account> allAccounts) {
        Pagination pagination = new Pagination(allAccounts.size(), pageLimit, currentPage);
        List<Account> productsOnPage = new Vector<>();

        for (int i = pagination.getStartItem(); i <= pagination.getLastItem() && i < allAccounts.size(); i++) {
            productsOnPage.add(allAccounts.get(i));
        }

        return productsOnPage;
    }

    public void updateOperatingDateAccount(int user_id, Timestamp operating_date) {
        String sql = "UPDATE `social`.`account` SET `operating_date` = ? WHERE `user_id` = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setTimestamp(1, operating_date);
            ps.setInt(2, user_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean checkLoginGoogle(String email) {
        String sql = "SELECT * FROM `social`.`account` WHERE email = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return false;
    }

    public void addAccountByLoginGoogle(int userId, String email, Timestamp creation_date) {
        String sql = "INSERT INTO `social`.`account` (`user_id`, `email`, `is_banned`, `role`, `creation_date`) VALUES (?, ?, 0, 1, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, email);
            ps.setTimestamp(3, creation_date);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean checkExistedOtp(int otp) {
        String sql = "SELECT * FROM `social`.`account` WHERE otp = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, otp);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return false;
    }

    public void updateOtpById(int id, int otp) {
        String sql = "UPDATE `social`.`account` SET `otp` = ? WHERE (`user_id` = ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, otp);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

    public void changePassword(int user_id, String password) throws NoSuchAlgorithmException {
        String passwordEncryption = SHAHash.createSHA(password);
        String sql = "UPDATE `social`.`account` SET `password` = ? WHERE `user_id` = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, passwordEncryption);
            ps.setInt(2, user_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean checkCorrectCurrentPassword(int id, String password) throws NoSuchAlgorithmException {
        String passwordEncryption = SHAHash.createSHA(password);
        String sql = "SELECT * FROM `social`.`account` WHERE password = ? AND user_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, passwordEncryption);
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
        return false;
    }

}
