package org.matmech.db;

import java.sql.*;

import org.matmech.db.bll.GroupsDBSource;
import org.matmech.db.bll.UsersDBSource;
import org.matmech.db.bll.DictonaryDBSource;
import org.matmech.db.bll.WordsDBSource;
import org.matmech.db.models.Group;
import org.matmech.db.models.User;
import org.matmech.db.models.Dictionary;
import org.matmech.db.models.Word;
import org.matmech.db.repository.DBConnection;

/**
 * Класс <b>DBHandler</b> реализует обертку над классами сервисами. Каждый метод работает с методами
 * классов-сервисов и возвращает готовое сообщение пользователю в текстовом виде или просто значение метода
 */
public class DBHandler {
    private DBConnection dbConnection = null;
    private final UsersDBSource usersDBSource;
    private final GroupsDBSource groupsDBSource;
    private final DictonaryDBSource dictonaryDBSource;
    private final WordsDBSource wordsDBSource;

    public DBHandler(String DB_URL, String DB_USERNAME, String DB_PASSWORD) {
        dbConnection = new DBConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        usersDBSource = new UsersDBSource(dbConnection);
        groupsDBSource = new GroupsDBSource(dbConnection);
        dictonaryDBSource = new DictonaryDBSource(dbConnection);
        wordsDBSource = new WordsDBSource(dbConnection);
    }


    public boolean IsWordExist(String word){
        WordsDBSource WordIsExist = new WordsDBSource();
        Words words = new Words();
        return WordIsExist.WordIsExist(words ,dbConnection);
    }

    /**
     * Подключение к базе данных
     * @param DB_URL - url адрес базы данных
     * @param DB_USERNAME - пользователь в базед данных
     * @param DB_PASSWORD - пароль пользователя
     */
    public void setDbConnection(String DB_URL, String DB_USERNAME, String DB_PASSWORD) {
        dbConnection = new DBConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    /**
     * <p>Добавляет нового пользователя в базу данных</p>
     *
     * @param firstname - имя человека
     * @param surname - фамилия человека
     * @param tag - username человека
     */
    public String usersInsert(String firstname, String surname, String tag) {
        try {
            User user = new User();
            Dictionary dictionary = new Dictionary();

            user.setFirstname(firstname);
            user.setSurname(surname);
            user.setTag(tag);

            boolean answer = usersDBSource.regUser(user);

            if (answer) {
                // создание словаря для пользователя
                dictionary.setUserId(usersDBSource.getUserIdByTag(user));
                dictonaryDBSource.createDictonary(dictionary);

                return "Привет, добро пожаловать в нашего бота для изучения английского языка!\n"
                        + "Список всех команд можете посмотреть с помощью /help";
            }

            return "Вы уже зарегистрированны в нашей системе";
        } catch (SQLException e) {
            System.out.println("Не удалось зарегистрировать пользователя");
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Выводит всех пользователей на экран
     */
    public void getAllUsers() {
        try {
            usersDBSource.getAllUsers();
        } catch (SQLException e) {
            System.out.println("Не удалось получить пользователей");
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Добавляет слово в базу данных</p>
     *
     * @param wordValue - слово, переданное в параметрах
     * @param wordTranslate - перевод этого слова
     * @param group - параметр, который означает
     * @param tag - username человека
     */
    public String wordAdd(String wordValue, String wordTranslate, String group, String tag) {
        try {
            Word word = new Word();
            User user = new User();
            Dictionary dictionary = new Dictionary();
            Group groups = new Group();

            word.setWordTranslate(wordTranslate);
            word.setWordValue(wordValue);

            user.setTag(tag);

            dictionary.setUserId(usersDBSource.getUserIdByTag(user));

            int dictonaryId = dictonaryDBSource.getDictonaryId(dictionary);

            if (dictonaryId == -1)
                return "Вашего словаря не существует! Чтобы его создать, вам нужно зарегистрироваться!\n" +
                        "Для регистрации напишите /start";

            word.setDictonaryId(dictonaryId);

            groups.setTitle(group);
            groups.setDictonaryId(dictonaryId);

            groupsDBSource.createGroup(groups);

            word.setGroupId(groupsDBSource.getGroupId(groups));

            boolean response = wordsDBSource.wordAdd(word);

            if (response)
                return "Слово было успешно добавлено!";
            else
                return "Ошибка! Слово уже существует!\nЕсли хотите что-то поменять в слове, то воспользуйтесь командой /edit";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Показать перевод слова без водной части для пользователя</p>
     *
     * @param wordValue - слово, переданное в параметрах
     */
    public String translateWordWithoutMessage(String wordValue) {
        Word word = new Word();

        word.setWordValue(wordValue);

        String wordTranslate = wordsDBSource.translate(word);

        if (wordTranslate != null)
            return wordTranslate;

        return null;
    }

    /**
     * <p>Показать перевод слова</p>
     *
     * @param wordValue - слово, переданное в параметрах
     */
    public String translateWord(String wordValue) {
        String wordTranslate = translateWordWithoutMessage(wordValue);

        if (wordTranslate != null)
            return "Перевод слова " + wordTranslate + ": " + wordTranslate;
        else
            return "Ошибка! В словаре нет этого слова!\n Полный список команд можете посмотреть с помощью /help";
    }

    /**
     * <p>Изменить параметр слова</p>
     *
     * @param wordValue - слово, переданное в параметрах
     * @param wordParam - параметр для изменения, переданное в параметрах
     * @param paramValue - значение изменяемого параметра, переданное в параметрах
     */

    public String edit(String wordValue, String wordParam, String paramValue){
        Word word = new Word();
        word.setWordValue(wordValue);
        switch (wordParam){
            case("group"):
                try {
                    Group group = new Group();
                    group.setTitle(paramValue);
                    // вытягиваю dictionary_id из слова

                    int dictionaryID = wordsDBSource.getDictonaryId(word);

                    if (dictionaryID == -1)
                        return "Вашего словаря не существует! Чтобы его создать, вам нужно зарегистрироваться!\n" +
                                "Для регистрации напишите /start";

                    group.setDictonaryId(dictionaryID);
                    // поиск по d_id group_id с title из paramValue

                    int groupID = groupsDBSource.getGroupId(group);

                    if (groupID == -1)
                        return "Такой группы не существует! Создайте группу, пожалуйста!";

                    word.setGroupId(groupID);

                    boolean response = wordsDBSource.editGroupId(word);

                    if (response)
                        return "Вы успешно изменили group_id";

                    return "Ошибка, редактирование не удалось выполнить, так как слова нет в словаре - прежде чем поменять что-то в нем, добавьте его в словарь";
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            case("translation"):
                word.setWordTranslate(paramValue);

                boolean response = wordsDBSource.editTranslation(word);

                if (response)
                    return "Вы успешно изменили translation";

                return "Ошибка, редактирование не удалось выполнить, так как слова нет в словаре - прежде чем поменять что-то в нем, добавьте его в словарь";
        }

        return "Этот параметр не подлежит изменению.\n Полный список команд можете посмотреть с помощью /help";
    }

    /**
     * <p>Узнать группу слова</p>
     *
     * @param wordValue - слово, переданное в параметрах
     */
    public String getGroup(String wordValue) {
        try {
            Word word = new Word();

            word.setWordValue(wordValue);

            int groupId = wordsDBSource.getGroupId(word);

            if (groupId == -1)
                return "Слова нет в вашем словаре. Введите существующее слово!";

            word.setGroupId(groupId);

            Group group = new Group();
            group.setId(word.getGroupId());

            String groupTitle = groupsDBSource.getGroupTitle(group);

            if (groupTitle != null)
                return "Группа у слова " + wordValue + ": " + groupTitle;

            return "Не удалось получить группу у слова: либо слова не существует, либо группы у слова нет";
        } catch (SQLException e) {
            System.out.println("Не удалось получить группу");
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Удаляет слово</p>
     *
     * @param wordValue - слово, переданное в параметрах
     */
    public String deleteWord(String wordValue) {
        Word word = new Word();

        word.setWordValue(wordValue);

        boolean response = wordsDBSource.deleteWord(word);

        if (response)
            return "Слово было удалено из базы данных!";

        return "Слова нет в базе данных!";
    }

    /**
     * Проверяет существование группы по названию
     * @param group - название группы
     * @return - возвращает <i>true</i>, если группа существует, и <i>false</i>, если группа не существует
     */
    public boolean groupIsExist(String group) {
        Group groups = new Group();

        groups.setTitle(group);

        return groupsDBSource.groupIsExist(groups);
    }

    /**
     * Проверяет существование пользователя по тегу
     * @param tag - тег пользователя
     * @return - возвращает <i>true</i>, если пользователь существует, и <i>false</i>, если пользователя не существует
     */
    public boolean userIsExist(String tag) {
        User user = new User();

        user.setTag(tag);

        return usersDBSource.userIsExist(user);
    }

    /**
     * Возвращает случайное слово из базы данных у конкретного пользователя.\
     * Может возвращать как по любым группам, так и по какой-то конкретной группе слов
     * @param tag - тег пользователя, у которого должно лежать это слово
     * @param group - название конкретной группы слов или если хотим по всем, то нужно указать значение <i>все</i>
     * @return - возвращает случайно слово
     */
    public String getRandomWord(String tag, String group) {
        try {
            User user = new User();
            user.setTag(tag);

            int userId = usersDBSource.getUserIdByTag(user);

            Dictionary dictionary = new Dictionary();
            dictionary.setUserId(userId);

            int dictonaryId = dictonaryDBSource.getDictonaryId(dictionary);

            Group groups = new Group();
            groups.setTitle(group);
            groups.setDictonaryId(dictonaryId);

            int groupId = groupsDBSource.getGroupId(groups);

            Word word = new Word();
            word.setGroupId(groupId);
            word.setDictonaryId(dictonaryId);

            group = group.toLowerCase();

            if (group.equals("все"))
                return wordsDBSource.getRandomWord(word);

            return wordsDBSource.getRandomWordByGroup(word);
        } catch (SQLException e) {
            System.out.println("Не удалось получить слово!\n" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public int getCountsWordsOfUser(String tag) {
        try {
            User user = new User();
            user.setTag(tag);

            int userId = usersDBSource.getUserIdByTag(user);

            Dictionary dictionary = new Dictionary();
            dictionary.setUserId(userId);

            int dictonaryId = dictonaryDBSource.getDictonaryId(dictionary);

            Word word = new Word();
            word.setDictonaryId(dictonaryId);

            return wordsDBSource.getCountWords(word);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
