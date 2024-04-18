/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author khang
 */
public class Notification {

    private int notification_id;
    private int user_id;
    private int notification_type;
    private Timestamp date;
    private String description;
    private Timestamp remind_event;
    private int relation_id;
    private int event_id;
    private String senderImage;

    public Notification() {
    }

    public Notification(int notification_id, int user_id, int notification_type, Timestamp date, String description, Timestamp remind_event, int relation_id, int event_id, String senderImage) {
        this.notification_id = notification_id;
        this.user_id = user_id;
        this.notification_type = notification_type;
        this.date = date;
        this.description = description;
        this.remind_event = remind_event;
        this.relation_id = relation_id;
        this.event_id = event_id;
        this.senderImage = senderImage;
    }

    public int getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(int notification_id) {
        this.notification_id = notification_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(int notification_type) {
        this.notification_type = notification_type;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getRemind_event() {
        return remind_event;
    }

    public void setRemind_event(Timestamp remind_event) {
        this.remind_event = remind_event;
    }

    public int getRelation_id() {
        return relation_id;
    }

    public void setRelation_id(int relation_id) {
        this.relation_id = relation_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getSenderImage() {
        return senderImage;
    }

    public void setSenderImage(String senderImage) {
        this.senderImage = senderImage;
    }

}
