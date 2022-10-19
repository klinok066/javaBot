package org.matmech.connector.telegramHandler;

import org.json.JSONObject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import org.matmech.requestHandler.RequestHandler;

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

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());
        JSONObject info;

        if (update.hasMessage() && update.getMessage().hasText()) {
            StringBuilder answer = new StringBuilder();

            // creating JSON object with all information about user which send message
            info = requestHandler.getInfo(update.getMessage().getFrom().getFirstName(),
                    update.getMessage().getFrom().getLastName(),
                    update.getMessage().getFrom().getUserName(),
                    update.getMessage().getFrom().getIsBot().toString(),
                    update.getMessage().getFrom().getId().toString());

            String messageString = update.getMessage().getText();

            answer.append(requestHandler.onUse(messageString, info));

            message.setText(answer.toString());
        }

        {
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
