/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Date;

public class User {

    private int user_id;
    private String f_name;
    private String l_name;
    private int gender;
    private Date date_birth;
    private Date date_death;
    private String phone_number;
    private String image;
    private int status;
    private int treeId;

    public User() {
    }

    public User(int user_id, String f_name, String l_name, int gender, Date date_birth, Date date_death, String phone_number, String image, int status) {
        this.user_id = user_id;
        this.f_name = f_name;
        this.l_name = l_name;
        this.gender = gender;
        this.date_birth = date_birth;
        this.date_death = date_death;
        this.phone_number = phone_number;
        this.image = image;
        this.status = status;
    }

    public User(int user_id, String f_name, String l_name, int gender, Date date_birth, Date date_death, String phone_number, String image, int status, int treeId) {
        this.user_id = user_id;
        this.f_name = f_name;
        this.l_name = l_name;
        this.gender = gender;
        this.date_birth = date_birth;
        this.date_death = date_death;
        this.phone_number = phone_number;
        this.image = image;
        this.status = status;
        this.treeId = treeId;
    }

    public User(int user_id, String f_name, String l_name) {
        this.user_id = user_id;
        this.f_name = f_name;
        this.l_name = l_name;
    }
    

    public int getTreeId() {
        return treeId;
    }

    public void setTreeId(int treeId) {
        this.treeId = treeId;
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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Date getDate_birth() {
        return date_birth;
    }

    public void setDate_birth(Date date_birth) {
        this.date_birth = date_birth;
    }

    public Date getDate_death() {
        return date_death;
    }

    public void setDate_death(Date date_death) {
        this.date_death = date_death;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" + "user_id=" + user_id + ", f_name=" + f_name + ", l_name=" + l_name + ", gender=" + gender + ", date_birth=" + date_birth + ", date_death=" + date_death + ", phone_number=" + phone_number + ", image=" + image + ", status=" + status + ", treeId=" + treeId + '}';
    }

}
