package org.matmech.userData;

final public class UserData {
    private final String firstname;
    private final String surname;
    private final String tag;
    private final long chatId;

    public UserData(String firstname, String surname, String tag, long chatId) {
        this.firstname = firstname;
        this.surname = surname;
        this.tag = tag;
        this.chatId = chatId;
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

    public long getChatId() {
        return chatId;
    }
}
