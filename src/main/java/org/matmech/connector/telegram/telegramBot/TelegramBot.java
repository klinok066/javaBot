package org.matmech.connector.telegram.telegramBot;

import org.matmech.connector.Connector;
import org.matmech.connector.telegram.telegramHandler.TelegramHandler;
import org.matmech.requestHandler.RequestHandler;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TelegramBot implements Connector {
    private final TelegramHandler bot;

    public TelegramBot(String botUsername, String botToken, RequestHandler requestHandler) {
        bot = new TelegramHandler(botUsername, botToken, requestHandler);
    }

    public void start() {
        {
            try {
                TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
                botsApi.registerBot(bot);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
