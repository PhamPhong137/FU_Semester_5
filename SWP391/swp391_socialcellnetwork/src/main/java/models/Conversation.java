/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Fpt
 */
public class Conversation {
    private int conversation_id;
    private int family_id;
    private String name;
    private String image;
    private int Status;

    public Conversation() {
    }

    public Conversation(int conversation_id, int family_id, String name, String image, int Status) {
        this.conversation_id = conversation_id;
        this.family_id = family_id;
        this.name = name;
        this.image = image;
        this.Status = Status;
    }

    public int getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(int conversation_id) {
        this.conversation_id = conversation_id;
    }

    public int getFamily_id() {
        return family_id;
    }

    public void setFamily_id(int family_id) {
        this.family_id = family_id;
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

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    @Override
    public String toString() {
        return super.toString(); 
    }
    
}
