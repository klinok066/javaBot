package org.matmech.connector.cmd.cmdBot;

import org.matmech.connector.Connector;
import org.matmech.connector.cmd.cmdLogic.CmdLogic;
import org.matmech.requestHandler.RequestHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Класс-интерфейс, который реализует консольную обертку над ботом
 */
public class CmdBot implements Connector {
    private final Scanner input;
    private final RequestHandler requestHandler;

    private CmdLogic bot;

    /**
     * Инициализирует пользователя, который пользуется сейчас консольным ботом
     */
    private Map<String, String> initUser() {
        Map<String, String> userData = new HashMap<String, String>();

        System.out.println("Добро пожаловать! Авторизуйте свой компьютер!");
        System.out.print("Введите свое имя:");
        userData.put("firstname", input.nextLine());

        System.out.print("Введите свою фамилию:");
        userData.put("lastname", input.nextLine());

        System.out.print("Введите/придумайте себе тег:");
        userData.put("tag", input.nextLine());

        System.out.println("Вы были успешно авторизированы! Для того, чтобы начать работать с ботом, напишите /start");

        return userData;
    }

    public CmdBot(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
        this.input = new Scanner(System.in);

        Map<String, String> userData = initUser();

        bot = new CmdLogic(userData.get("firstname"), userData.get("lastname"), userData.get("tag"), requestHandler);
    }

    /**
     * Этот метод запускает консольного бота
     */
    public void start() {
        bot.startChat();
    }
}
