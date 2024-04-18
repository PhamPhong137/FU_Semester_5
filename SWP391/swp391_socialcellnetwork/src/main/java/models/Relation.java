/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Admin
 */
public class Relation {

    private int relation_id;
    private int user1_id;
    private int user2_id;
    private int type;

    public Relation() {
    }

    public Relation(int relation_id, int user1_id, int user2_id, int type) {
        this.relation_id = relation_id;
        this.user1_id = user1_id;
        this.user2_id = user2_id;
        this.type = type;
    }

    public int getRelation_id() {
        return relation_id;
    }

    public void setRelation_id(int relation_id) {
        this.relation_id = relation_id;
    }

    public int getUser1_id() {
        return user1_id;
    }

    public void setUser1_id(int user1_id) {
        this.user1_id = user1_id;
    }

    public int getUser2_id() {
        return user2_id;
    }

    public void setUser2_id(int user2_id) {
        this.user2_id = user2_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Relation{" + "relation_id=" + relation_id + ", user1_id=" + user1_id + ", user2_id=" + user2_id + ", type=" + type + '}';
    }
}
