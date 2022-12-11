package org.matmech.db.models;

/**
 * Объект словарь, у которого поля хранят в себе значение атрибутов соответствующей записи в таблице Dictonary
 */
public class Dictionary {
    private int userId = -1;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}
