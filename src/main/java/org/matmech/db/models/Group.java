package org.matmech.db.models;

/**
 * Объект группы, у которого поля хранят в себе значение атрибутов соответствующей записи в таблице Groups
 */
public class Group extends BaseModel {
    private String title;
    private int dictonaryId;

    public void setTitle(String group) {
        this.title = group;
    }

    public String getTitle() {
        return title;
    }

    public void setDictonaryId(int dictonaryId) {
        this.dictonaryId = dictonaryId;
    }

    public int getDictonaryId() {
        return dictonaryId;
    }
}
