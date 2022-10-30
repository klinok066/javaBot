package org.matmech.db.models;

public class Words extends BaseModel {
    private int dictonaryId;
    private int groupId;
    private String wordValue;
    private String wordTranslate;

    public void setDictonaryId(int dictonaryId) {
        this.dictonaryId = dictonaryId;
    }

    public void setWordTranslate(String wordTranslate) {
        this.wordTranslate = wordTranslate;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setWordValue(String wordValue) {
        this.wordValue = wordValue;
    }

    public String getWordValue() {
        return wordValue;
    }

    public String getWordTranslate() {
        return wordTranslate;
    }

    public int getDictonaryId() {
        return dictonaryId;
    }

    public int getGroupId() {
        return groupId;
    }
}