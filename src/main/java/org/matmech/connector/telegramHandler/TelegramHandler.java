package org.matmech.connector.telegramHandler;

import org.matmech.dataSaver.UserData;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import org.matmech.requestHandler.RequestHandler;

/**
 * Логика класса-интерфейса телеграмм бота
 */
public class TelegramHandler extends TelegramLongPollingBot {
    private final String botUsername;
    private final String botToken;
    private final RequestHandler requestHandler;

    public TelegramHandler(String botUsername, String botToken, RequestHandler requestHandler) {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.requestHandler = requestHandler;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    /**
     * Метод, который реагирует на новые обновления в чате с ботом
     * @param update - объект, который хранит в себе информацию о новом обновлении в чате с ботом
     */
    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());

        if (update.hasMessage() && update.getMessage().hasText()) {
            StringBuilder answer = new StringBuilder();

            // creating Data Saver object which save information about user

            User userData = update.getMessage().getFrom();

            UserData data = new UserData(
                    userData.getFirstName(),
                    userData.getLastName(),
                    userData.getUserName()
            );

            String messageString = update.getMessage().getText();

            answer.append(requestHandler.onUse(messageString, data));

            message.setText(answer.toString());
        }

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
