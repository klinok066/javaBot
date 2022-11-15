package org.matmech.connector.cmdBot;

import org.matmech.connector.Connector;
import org.matmech.connector.cmdLogic.CmdLogic;
import org.matmech.requestHandler.RequestHandler;

import java.util.Scanner;

/**
 * Класс-интерфейс, который реализует консольную обертку над ботом
 */
public class CmdBot implements Connector {
    private String firstName;
    private String lastName;
    private String tag;
    private final Scanner input;
    private final RequestHandler requestHandler;

    private CmdLogic bot;

    /**
     * Инициализирует пользователя, который пользуется сейчас консольным ботом
     */
    private void initUser() {
        System.out.println("Добро пожаловать! Авторизуйте свой компьютер!");
        System.out.print("Введите свое имя:");
        this.firstName = input.nextLine();
        System.out.print("Введите свою фамилию:");
        this.lastName = input.nextLine();
        System.out.print("Введите/придумайте себе тег:");
        this.tag = input.nextLine();

        System.out.println("Вы были успешно авторизированы! Для того, чтобы начать работать с ботом, напишите /start");
    }

    public CmdBot(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
        this.input = new Scanner(System.in);

        initUser();

        bot = new CmdLogic(firstName, lastName, tag, requestHandler);
    }

    /**
     * Этот метод запускает консольного бота
     */
    public void start() {
        bot.responseForCDM();
    }
}
