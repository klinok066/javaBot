package org.matmech.db.models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Модель записи в таблице Stats
 */
public class Stat extends BaseModel {
    private Integer userId;
    private String completeDate;
    private Integer result;

    /**
     * Присваивает userId в поле класса
     * @param userId - идентификатор пользователя
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Присваивает дату пользователю
     * @param date - объект Date, который хранит в себе год, месяц, день
     */
    public void setCompleteDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        this.completeDate = dateFormat.format(date);
    }

    /**
     * Присваивает результат теста соответствующему полю
     * @param result - количество правильных ответов
     */
    public void setResult(Integer result) {
        this.result = result;
    }

    /**
     * Получает id пользователя
     * @return - id пользователя
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Возвращает дату теста в формате yyyy-MM-dd
     * @return - дата
     */
    public String getCompleteDate() {
        return completeDate;
    }

    /**
     * Возвращает результат теста
     * @return - количество правильных ответов
     */
    public Integer getResult() {
        return result;
    }
}
