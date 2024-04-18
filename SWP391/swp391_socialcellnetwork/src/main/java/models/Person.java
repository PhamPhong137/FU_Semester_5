/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author thanh
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.ArrayList;
import java.util.List;

/**
 * The Person class represents an individual in a family tree.
 * It includes details such as the person's name, birth date, spouse, and children.
 * The birth date is expected to be in the format YYYY-MM-DD.
 */
public class Person {
    private long id;
    private int userId;
    private String name;
    private String birthDate; // Định dạng YYYY-MM-DD
    private String deathDate;
    private String information;
    private String optionalInfo;
    private String treeId;
    private Person spouse;
    private List<Person> children = new ArrayList<>();

    // Constructors, getters và setters
 /**
     * Default constructor for creating an instance of Person without setting any attributes.
     */
        public static class Builder {
        private long id;
        private int userId;
        private String name;
        private String birthDate;
        private String deathDate;
        private String information;
        private String optionalInfo;
        private String treeId;
        private Person spouse;
        private List<Person> children = new ArrayList<>();

        public Builder() {
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder birthDate(String birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder deathDate(String deathDate) {
            this.deathDate = deathDate;
            return this;
        }
        
        public Builder information(String information) {
            this.information = information;
            return this;
        }

        public Builder optionalInfo(String optionalInfo) {
            this.optionalInfo = optionalInfo;
            return this;
        }

        public Builder treeId(String treeId) {
            this.treeId = treeId;
            return this;
        }

        public Builder spouse(Person spouse) {
            this.spouse = spouse;
            return this;
        }

        public Builder children(List<Person> children) {
            this.children = children; // Có thể cần phải thực hiện sâu hơn để đảm bảo tính đúng đắn
            return this;
        }

        public Person build() {
            Person person = new Person();
            person.id = this.id;
            person.userId = this.userId;
            person.name = this.name;
            person.birthDate = this.birthDate;
            person.deathDate = this.deathDate;
            person.information = this.information;
            person.optionalInfo = this.optionalInfo;
            person.treeId = this.treeId;
            person.spouse = this.spouse;
            person.children = this.children;
            return person;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    

    /**
     * Adds a child to the current person's list of children.
     * 
     * @param child The child to be added.
     */
    public void addChild(Person child) {
        this.children.add(child);
    }

    /**
     * Returns the list of children of the person.
     * 
     * @return The list of Person objects representing the children.
     */
    public List<Person> getChildren() {
        return children;
    }

    /**
     * Sets the spouse of the person.
     * 
     * @param spouse The spouse to be set for the person.
     */
    public void setSpouse(Person spouse) {
        this.spouse = spouse;
    }

    /**
     * Returns the spouse of the person.
     * 
     * @return The Person object representing the spouse.
     */
    public Person getSpouse() {
        return spouse;
    }

    /**
     * Returns the name of the person.
     * 
     * @return The name of the person.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the person.
     * 
     * @param name The name to be set for the person.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the birth date of the person.
     * 
     * @return The birth date of the person in format YYYY-MM-DD.
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the birth date of the person.
     * 
     * @param birthDate The birth date to be set for the person, expected in format YYYY-MM-DD.
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getOptionalInfo() {
        return optionalInfo;
    }

    public void setOptionalInfo(String optionalInfo) {
        this.optionalInfo = optionalInfo;
    }

    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }
    
    
    
}
