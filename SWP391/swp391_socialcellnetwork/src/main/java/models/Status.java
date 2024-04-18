/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class Status {

    private int status_id;
    private int user_id;
    private int group_id;
    private String img;
    private String description;
    private Timestamp date;
    private Date date_memory;
    private String place;
    private int access;

    public Status() {
    }

    public Status(int status_id, int user_id, int group_id, String img, String description, Timestamp date, Date date_memory, String place, int access) {
        this.status_id = status_id;
        this.user_id = user_id;
        this.group_id = group_id;
        this.img = img;
        this.description = description;
        this.date = date;
        this.date_memory = date_memory;
        this.place = place;
        this.access = access;
    }

    public Date getDate_memory() {
        return date_memory;
    }

    public void setDate_memory(Date date_memory) {
        this.date_memory = date_memory;
    }

    

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

   

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

}
