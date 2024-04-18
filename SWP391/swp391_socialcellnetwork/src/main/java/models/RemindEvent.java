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
public class RemindEvent {
    private int event_id;
    private int user_id;
    private String event_title;
    private String remind_title;
    private String fullname;
    private String img;
    private int dayafter;
    private Timestamp start_date;
    private String description;
    private int frequency;
    private String place;

    public RemindEvent() {
    }

    public RemindEvent(int event_id, int user_id, String event_title, String remind_title, String fullname, String img, int dayafter, Timestamp start_date, String description, int frequency, String place) {
        this.event_id = event_id;
        this.user_id = user_id;
        this.event_title = event_title;
        this.remind_title = remind_title;
        this.fullname = fullname;
        this.img = img;
        this.dayafter = dayafter;
        this.start_date = start_date;
        this.description = description;
        this.frequency = frequency;
        this.place = place;
    }
     public RemindEvent(int event_id, int user_id, String event_title, String fullname, String img, int dayafter, Timestamp start_date, String description, int frequency, String place) {
        this.event_id = event_id;
        this.user_id = user_id;
        this.event_title = event_title;
        this.fullname = fullname;
        this.img = img;
        this.dayafter = dayafter;
        this.start_date = start_date;
        this.description = description;
        this.frequency = frequency;
        this.place = place;
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

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public String getRemind_title() {
        return remind_title;
    }

    public void setRemind_title(String remind_title) {
        this.remind_title = remind_title;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getDayafter() {
        return dayafter;
    }

    public void setDayafter(int dayafter) {
        this.dayafter = dayafter;
    }

    public Timestamp getStart_date() {
        return start_date;
    }

    public void setStart_date(Timestamp start_date) {
        this.start_date = start_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

   
    
    
    
    
}
