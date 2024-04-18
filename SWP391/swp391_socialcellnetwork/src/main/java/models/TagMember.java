/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author PC-Phong
 */
public class TagMember {

    private int event_tag_id;
    private int event_id;
    private int user_id;
    private String f_name;
    private String l_name;

    public TagMember() {
    }

    public TagMember(int event_tag_id, int event_id, int user_id) {
        this.event_tag_id = event_tag_id;
        this.event_id = event_id;
        this.user_id = user_id;
    }

    public TagMember(int event_tag_id, int event_id, int user_id, String f_name, String l_name) {
        this.event_tag_id = event_tag_id;
        this.event_id = event_id;
        this.user_id = user_id;
        this.f_name = f_name;
        this.l_name = l_name;
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
    

    public int getEvent_tag_id() {
        return event_tag_id;
    }

    public void setEvent_tag_id(int event_tag_id) {
        this.event_tag_id = event_tag_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

}
