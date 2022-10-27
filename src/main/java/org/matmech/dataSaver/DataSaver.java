package org.matmech.dataSaver;

final public class DataSaver {
    private final String firstname;
    private final String surname;
    private final String tag;
    private final long id;

    public DataSaver(String firstname, String surname, String tag, long id) {
        this.firstname = firstname;
        this.surname = surname;
        this.tag = tag;
        this.id = id;
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

    public long id() {
        return id;
    }
}
