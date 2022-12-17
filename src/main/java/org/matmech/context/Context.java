package org.matmech.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Этот класс отвечает за контекст всех пользователей, которые пользуются этим приложением.
 * Контекст может использовать только один какой-то процесс
 * Поэтому у контекста пользователя есть два обязательных поля:
 *      <i>chatId</i> - идентификатор чата с пользователем
 *      <i>processName</i> - имя процесса, который в данный момент использует контекст. Если никто не использует,
 *          то стоит null
 */
public class Context {

    //TODO: написать метод createContext(long chatId), и зарефакторить везде код под то, что он ничего не делает, если контекста нет для чата

    private final List<Map<String, String>> cache;

    /**
     * Ищет кеш пользователя по chatId
     * @param chatId - идентификатор чата с пользователем
     * @return - возвращает коллекцию HashMap, где ключ и значение - строки, с кешем пользователя или null,
     *           если кеш не найден
     */
    private Map<String, String> searchItem(long chatId) {
        for (Map<String, String> item : cache)
            if (Long.parseLong(item.get("chatId")) == chatId)
                return item;

        return null;
    }

    public Context() {
        cache = new ArrayList<Map<String, String>>();
    }

    /**
     * Добавляет параметр в кеш пользователя
     * @param chatId - идентификатор чата с пользователем
     * @param processName - имя процесса, который сейчас использует этот кеш
     * @param itemName - имя параметра, который мы хотим добавить в кеш
     * @param itemValue - значение параметра, который мы хотим добавить в кеш
     */
    public boolean addParam(long chatId, String processName, String itemName, String itemValue) {
        Map<String, String> item = searchItem(chatId);

        if (item == null) {
            item = new HashMap<String, String>();

            item.put("chatId", String.valueOf(chatId));
            item.put("processName", processName);
            item.put("stopFlag", null);
            item.put(itemName, itemValue);

            cache.add(item);
            return true;
        }

            if (item.get("processName") == null)
                return false;

        if (item.get("processName").equals(processName))
            item.put(itemName, itemValue);

        return true;
    }

    /**
     * Получение параметров в кеше пользователя
     * @param chatId - идентификатор чата с пользователем
     * @return - возвращает HashMap со всеми параметрами пользователя в кеше
     */
    public Map<String, String> getParams(long chatId) {
        return searchItem(chatId);
    }

    /**
     * Дает понять занят ли кеш другим процессом или нет
     * @param chatId - идентификатор чата с пользователем
     * @return - возвращает <i>true</i> - если занят, и <i>false</i> - если свободен
     */
    public boolean isBusy(long chatId) {
        Map<String, String> item = searchItem(chatId);

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
        Map<String, String> item = searchItem(chatId);

        if (item != null) {
            String chatIdValue = item.get("chatId");
            item.clear();
            item.put("chatId", chatIdValue);
            item.put("processName", null);
            item.put("stopFlag", null);
        }
    }

    /**
     * Присваивает значение полю processName у пользовательского кеша. Если кеш под пользователя
     * не был выделен, то он сначала будет выделен, а потом будет присвоено значение полю processName
     * <b>Имя процессу не будет присвоено, если там уже лежит какое-то значение</b>
     * @param chatId - идентификатор чата с пользователем
     * @param processName - имя процесса, которое хотите присвоить
     */
    public void setProcessName(long chatId, String processName) {
        Map<String, String> item = searchItem(chatId);

        if (item == null) {
            item = new HashMap<String, String>();

            item.put("chatId", String.valueOf(chatId));
            item.put("processName", processName);
            item.put("stopFlag", null);

            cache.add(item);
        }

        item.putIfAbsent("processName", processName);
    }
}
