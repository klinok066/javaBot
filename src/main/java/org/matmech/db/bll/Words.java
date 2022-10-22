package org.matmech.db.bll;

import org.matmech.db.models.BaseModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Words extends BaseModelExtend {
    private int dictonaryId;
    private int groupId;
    private String wordValue;
    private String wordTranslate;

    /**
     * <p>Проверяет на существование какого-то слова</p>
     * <p>Перед этим нужно проинициализировать поле <i>wordValue</i> с помощью сеттера</p>
     */
    private boolean isExist() {
        try {
            String getWords = "select * from words where word_value='" + wordValue + "';";
            ResultSet response = statement.executeQuery(getWords);
            return response.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Words(Connection connection) {
        super(connection);
    }

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

    /**
     * <p>Добавляет слово в базу данных. Если слово уже существует, то будет возвращаенно соответствующее сообщение</p>
     */
    public String wordAdd() {
        try {
            if (isExist())
                return "Ошибка! Слово уже существует!\nЕсли хотите что-то поменять в слове, то воспользуйтесь командой /edit";

            String wordAddSQL = "insert into words(dictonary_id, group_id, word_value, word_translate) values(" + dictonaryId + ", "
                                                                                                                + groupId + ", '"
                                                                                                                + wordValue + "', '"
                                                                                                                + wordTranslate + "');";

            statement.executeUpdate(wordAddSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return "Слово было успешно добавлено!";
    }

    public String translate() {
        try {
            if (isExist()) {
                String translateValue = "select word_translate from words where word_value='" + wordValue + "';";
                ResultSet response = statement.executeQuery(translateValue);

                while (response.next()) {
                    return "Перевод слова " + wordValue + " - " + response.getString("word_translate");
                }
            }

            return "Ошибка! В словаре нет этого слова!\n Полный список команд можете посмотреть с помощью /help";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
