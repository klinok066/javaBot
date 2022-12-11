package org.matmech.db.models;

/**
 * Базовая модель, от которой наследуются остальные модели
 */
public class BaseModel {
    protected int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
