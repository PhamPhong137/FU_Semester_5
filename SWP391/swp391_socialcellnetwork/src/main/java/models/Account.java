/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class Account {

    private int user_id;
    private String username;
    private String password;
    private String email;
    private int isBanned;
    private int role;
    private Timestamp creation_date; 
    private Timestamp operating_date;

    public Account() {
    }

    public Account(int user_id, String username, String password, String email, int isBanned, int role, Timestamp creation_date, Timestamp operating_date) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isBanned = isBanned;
        this.role = role;
        this.creation_date = creation_date;
        this.operating_date = operating_date;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIsBanned() {
        return isBanned;
    }

    public void setIsBanned(int isBanned) {
        this.isBanned = isBanned;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public Timestamp getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Timestamp creation_date) {
        this.creation_date = creation_date;
    }

    public Timestamp getOperating_date() {
        return operating_date;
    }

    public void setOperating_date(Timestamp operating_date) {
        this.operating_date = operating_date;
    }
 
    
    

}
