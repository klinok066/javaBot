package org.matmech.context.contextHandler.handlers.usuallyMessage;

import org.matmech.dataSaver.DataSaver;

/**
 * Этот класс отвечает за обработку простого сообщения
 */
public class UsuallyMessage {
    private String sayHello(String name) {
        return "Привет, " + name;
    }

    private String defaultMessage() {
        return "Прости, но я тебя не понимаю!\nМожешь посмотреть полный список команд для взаимодействия со мной" +
                "с помощью /help";
    }

    /**
     * Главный метод, который запускает простого сообщения
     *
     * @param info - объект DataSaver с информацией о пользователе
     * @return - возвращает сообщение для пользователя соответствующее
     */
    public String handle(DataSaver info, String message) {
        return switch (message.toLowerCase()) {
            case "привет!" -> sayHello(info.getFirstname());
            default -> defaultMessage();
        };
    }
}
