/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class User {

    private int user_id;
    private String f_name;
    private String l_name;
    private String phone_number;
    private String email;
    private int role;
    private String image;
    private String status;

    public User() {
    }

    public User(String f_name, String l_name, String phone_number, String email) {
        this.f_name = f_name;
        this.l_name = l_name;
        this.phone_number = phone_number;
        this.email = email;
    }

    public User(int user_id, String f_name, String l_name, String phone_number, String email, int role, String image, String status) {
        this.user_id = user_id;
        this.f_name = f_name;
        this.l_name = l_name;
        this.phone_number = phone_number;
        this.email = email;
        this.role = role;
        this.image = image;
        this.status = status;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" + "user_id=" + user_id + ", f_name=" + f_name + ", l_name=" + l_name + ", phone_number=" + phone_number + ", email=" + email + ", role=" + role + ", image=" + image + ", status=" + status + '}';
    }

}
