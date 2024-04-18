/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Date;

/**
 *
 * @author Admin
 */
public class FamilyInfo {

    private int family_id;
    private String name;
    private String background;
    private String img;
    private int type;
    private String description;
    private Date dateTime;
    private int treeId;

    public FamilyInfo() {
    }

    public FamilyInfo(int family_id, String name, String background, String img, int type, String description, Date dateTime) {
        this.family_id = family_id;
        this.name = name;
        this.background = background;
        this.img = img;
        this.type = type;
        this.description = description;
        this.dateTime = dateTime;
    }

    public FamilyInfo(int family_id, String name, String background, String img, int type, String description, Date dateTime, int treeId) {
        this.family_id = family_id;
        this.name = name;
        this.background = background;
        this.img = img;
        this.type = type;
        this.description = description;
        this.dateTime = dateTime;
        this.treeId = treeId;
    }

    public int getTreeId() {
        return treeId;
    }

    public void setTreeId(int treeId) {
        this.treeId = treeId;
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

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

}
