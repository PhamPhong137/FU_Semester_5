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
public class EventUser {

    private int event_id;
    private int user_id;
    private int family_id;
    private String title;
    private String name;
    private String image;
    private Timestamp start_date;
    private Timestamp end_date;
    private String description;
    private int frequency;
    private String place;
    private int access;
    private String color;

    public EventUser() {
    }

    public EventUser(int event_id, int user_id, int family_id, String title, String name, String image, Timestamp start_date, Timestamp end_date, String description, int frequency, String place, int access, String color) {
        this.event_id = event_id;
        this.user_id = user_id;
        this.family_id = family_id;
        this.title = title;
        this.name = name;
        this.image = image;
        this.start_date = start_date;
        this.end_date = end_date;
        this.description = description;
        this.frequency = frequency;
        this.place = place;
        this.access = access;
        this.color = color;
    }
    public EventUser(int event_id, int user_id, int family_id, String title, String name, Timestamp start_date, Timestamp end_date, String description, int frequency, String place, int access) {
        this.event_id = event_id;
        this.user_id = user_id;
        this.family_id = family_id;
        this.title = title;
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.description = description;
        this.frequency = frequency;
        this.place = place;
        this.access = access;
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

    public int getFamily_id() {
        return family_id;
    }

    public void setFamily_id(int family_id) {
        this.family_id = family_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Timestamp getStart_date() {
        return start_date;
    }

    public void setStart_date(Timestamp start_date) {
        this.start_date = start_date;
    }

    public Timestamp getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Timestamp end_date) {
        this.end_date = end_date;
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

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    

}
