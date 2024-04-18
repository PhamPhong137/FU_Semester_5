/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Timestamp;

/**
 *
 * @author Fpt
 */
public class ConversationMessage {
    private int mesage_id;
    private String description;
    private Timestamp date;
    private int sent_by;
    private int conversation_id;

    public ConversationMessage() {
    }

    public ConversationMessage(int mesage_id, String description, Timestamp date, int sent_by, int conversation_id) {
        this.mesage_id = mesage_id;
        this.description = description;
        this.date = date;
        this.sent_by = sent_by;
        this.conversation_id = conversation_id;
    }

    public int getMesage_id() {
        return mesage_id;
    }

    public void setMesage_id(int mesage_id) {
        this.mesage_id = mesage_id;
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

    public int getSent_by() {
        return sent_by;
    }

    public void setSent_by(int sent_by) {
        this.sent_by = sent_by;
    }

    public int getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(int conversation_id) {
        this.conversation_id = conversation_id;
    }

    @Override
    public String toString() {
        return super.toString(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}
