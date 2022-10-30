package org.matmech.db.models;

public class Groups extends BaseModel {
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
