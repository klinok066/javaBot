package org.matmech.db.models;

/**
 * Объект пользователя, у которого поля хранят в себе значение атрибутов соответствующей записи в таблице Users
 */
public class User extends BaseModel{
    private String firstname;
    private String surname;
    private String tag;
    private String testMode;
    private boolean testing;

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setTestMode(String testMode) {
        this.testMode = testMode;
    }
    public void setTesting(boolean testing) {
        this.testing = testing;
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

    public String getTestMode() {
        return testMode;
    }
    public boolean getTesting() {
        return testing;
    }
}
