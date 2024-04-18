package model;

import java.util.Date;

public class Event {
    private int event_id;
    private String title;
    private Date start_date;
    private Date end_date;

    public Event(int event_id, String title, Date start_date, Date end_date) {
        this.event_id = event_id;
        this.title = title;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

        

    // Getters and Setters...

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

 
}
