package org.matmech.connector.telegramBot;

import org.matmech.connector.Connector;
import org.matmech.connector.telegramHandler.TelegramHandler;
import org.matmech.requestHandler.RequestHandler;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Этот класс-интерфейс, который реализует обертку над ботом в телеграмме
 */
public class TelegramBot implements Connector {
    private final TelegramHandler bot;

    public TelegramBot(String botUsername, String botToken, RequestHandler requestHandler) {
        bot = new TelegramHandler(botUsername, botToken, requestHandler);
    }

    /**
     * Этот метод запускает телеграмм-бота
     */
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
