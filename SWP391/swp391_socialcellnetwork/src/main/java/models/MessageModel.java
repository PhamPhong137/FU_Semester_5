
package models;

import java.sql.Timestamp;


public class MessageModel {
    
    private String description;
    private int send_by;
    private int conversation_id;
    private String date;
    private String image;
    private String name;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSend_by() {
        return send_by;
    }

    public void setSend_by(int send_by) {
        this.send_by = send_by;
    }

    public int getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(int conversation_id) {
        this.conversation_id = conversation_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    

    
    

    @Override
    public String toString() {
        return "MessageModel{" + "description=" + description + ", send_by=" + send_by + '}';
    }
    
}
