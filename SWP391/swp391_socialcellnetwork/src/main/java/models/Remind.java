/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Timestamp;

/**
 *
 * @author PC-Phong
 */
public class Remind {
    private int remind_id;
    private int event_id;
    private String remind_title;
    private Timestamp date;
    private int userid_target;
    private int day;
    public Remind() {
    }

    public Remind(int remind_id, int event_id, String remind_title, Timestamp date, int userid_target) {
        this.remind_id = remind_id;
        this.event_id = event_id;
        this.remind_title = remind_title;
        this.date = date;
        this.userid_target = userid_target;
    }

    public Remind(int remind_id, int event_id, String remind_title, Timestamp date, int userid_target, int day) {
        this.remind_id = remind_id;
        this.event_id = event_id;
        this.remind_title = remind_title;
        this.date = date;
        this.userid_target = userid_target;
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
    

    public int getRemind_id() {
        return remind_id;
    }

    public void setRemind_id(int remind_id) {
        this.remind_id = remind_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getRemind_title() {
        return remind_title;
    }

    public void setRemind_title(String remind_title) {
        this.remind_title = remind_title;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getUserid_target() {
        return userid_target;
    }

    public void setUserid_target(int userid_target) {
        this.userid_target = userid_target;
    }

    
    

    
    
    
}
