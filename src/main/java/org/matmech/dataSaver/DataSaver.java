package org.matmech.dataSaver;

final public class DataSaver {
    private final String firstname;
    private final String surname;
    private final String tag;

    public DataSaver(String firstname, String surname, String tag) {
        this.firstname = firstname;
        this.surname = surname;
        this.tag = tag;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return surname;
    }

    public String getTag() {
        return tag;
    }
}
