package org.matmech.cache;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Этот класс отвечает за кеш всех пользователей, которые пользуются этим приложением.
 * Кеш может использовать только один какой-то процесс
 * Поэтому у кеша пользователя есть два обязательных поля:
 *      <i>chatId</i> - идентификатор чата с пользователем
 *      <i>processName</i> - имя процесса, который в данный момент использует кеш. Если никто не использует,
 *          то стоит null
 */
public class Cache {
    private final ArrayList<HashMap<String, String>> cache;

    /**
     * Ищет кеш пользователя по chatId
     * @param chatId - идентификатор чата с пользователем
     * @return - возвращает коллекцию HashMap, где ключ и значение - строки, с кешем пользователя или null,
     *           если кеш не найден
     */
    private HashMap<String, String> searchItem(long chatId) {
        for (HashMap<String, String> item : cache)
            if (Long.parseLong(item.get("chatId")) == chatId)
                return item;

        return null;
    }

    public Cache() {
        cache = new ArrayList<HashMap<String, String>>();
    }

    /**
     * Добавляет параметр в кеш пользователя
     * @param chatId - идентификатор чата с пользователем
     * @param processName - имя процесса, который сейчас использует этот кеш
     * @param itemName - имя параметра, который мы хотим добавить в кеш
     * @param itemValue - значение параметра, который мы хотим добавить в кеш
     */
    public void addParams(long chatId, String processName, String itemName, String itemValue) {
        HashMap<String, String> item = searchItem(chatId);

        if (item == null) {
            item = new HashMap<String, String>();

            item.put("chatId", String.valueOf(chatId));
            item.put("processName", processName);
            item.put(itemName, itemValue);

            cache.add(item);
            return;
        }

        if (item.get("processName").equals(processName))
            item.put(itemName, itemValue);
    }

    /**
     * Получение параметров в кеше пользователя
     * @param chatId - идентификатор чата с пользователем
     * @return - возвращает HashMap со всеми параметрами пользователя в кеше
     */
    public HashMap<String, String> getParams(long chatId) {
        return searchItem(chatId);
    }

    /**
     * Дает понять занят ли кеш другим процессом или нет
     * @param chatId - идентификатор чата с пользователем
     * @return - возвращает <i>true</i> - если занят, и <i>false</i> - если свободен
     */
    public boolean isBusy(long chatId) {
        HashMap<String, String> item = searchItem(chatId);

        if (item == null)
            return false;

        return item.get("processName") != null;
    }

    /**
     * Очищает кеш пользователя. Оставляет только поле <i>chatId</i> и <i>processName</i>.
     * Последнему устанавливает значение <i>null</i>
     * @param chatId - идентификатор чата с пользователем
     */
    public void clear(long chatId) {
        HashMap<String, String> item = searchItem(chatId);

        if (item != null) {
            for (String key : item.keySet())
                if (!key.equals("chatId"))
                    item.remove(key);

            item.put("processName", null);
        }
    }
}
