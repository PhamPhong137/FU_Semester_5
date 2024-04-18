/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class Account {

    private static int lastUserId = 0;

    private int account_id;
    private int user_id;
    private String username;
    private String password;
    private boolean isBanned;

    public Account() {
        // Increment lastUserId and assign it to user_id for a new account
        this.user_id = ++lastUserId;
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Account(int account_id, int user_id, String username, String password, boolean isBanned) {
        this.account_id = account_id;
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.isBanned = isBanned;
    }

    public Account(int account_id, String username, String password, boolean isBanned) {
        this.account_id = account_id;
        this.user_id = ++lastUserId;
        this.username = username;
        this.password = password;
        this.isBanned = isBanned;
    }

    public static int getLastUserId() {
        return lastUserId;
    }

    public static void setLastUserId(int lastUserId) {
        Account.lastUserId = lastUserId;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIsBanned() {
        return isBanned;
    }

    public void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    // Other methods...
    // Getter and setter methods...
    @Override
    public String toString() {
        return "Account{"
                + "account_id=" + account_id
                + ", user_id=" + user_id
                + ", username='" + username + '\''
                + ", password='" + password + '\''
                + ", isBanned=" + isBanned
                + '}';
    }
}
