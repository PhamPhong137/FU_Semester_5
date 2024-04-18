/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author ACER
 */
public class StatusTag {
    private int status_tag_id;
    private int status_id;
    private int user_id;

    public StatusTag(int status_tag_id, int status_id, int user_id) {
        this.status_tag_id = status_tag_id;
        this.status_id = status_id;
        this.user_id = user_id;
    }

    public int getStatus_tag_id() {
        return status_tag_id;
    }

    public void setStatus_tag_id(int status_tag_id) {
        this.status_tag_id = status_tag_id;
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
    
           
}
