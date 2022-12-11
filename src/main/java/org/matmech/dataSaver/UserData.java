package org.matmech.dataSaver;

/**
 * Класс хранящий в себе данные о пользователе, с которым ведется в данные момент диалог
 */
final public class UserData {
    private final String firstname;
    private final String surname;
    private final String tag;

    public UserData(String firstname, String surname, String tag) {
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
