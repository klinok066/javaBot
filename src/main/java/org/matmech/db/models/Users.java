package org.matmech.db.models;

public class Users extends BaseModel{
    private String firstname;
    private String surname;
    private String tag;

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getTag() {
        return this.tag;
    }
}
