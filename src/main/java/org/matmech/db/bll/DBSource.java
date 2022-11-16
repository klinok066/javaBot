package org.matmech.db.bll;

import java.util.HashMap;

/**
 * Абстрактный класс-сервис, в котором содержится дублирующийся код всех классов-сервисов,
 * которые от него наследуются
 */
abstract public class DBSource {
    /**
     * Создает HashMap объект с параметром
     * @param type - тип параметра
     * @param value - значение параметра
     */
    protected HashMap<String, String> createParams(String type, String value) {
        HashMap<String, String> item = new HashMap<String, String>();

        item.put("type", type);
        item.put("value", value);

        return item;
    }
}
