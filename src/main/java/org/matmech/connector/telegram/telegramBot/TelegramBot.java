package org.matmech.connector.telegram.telegramBot;

import org.matmech.connector.Connector;
import org.matmech.connector.telegram.telegramHandler.TelegramHandler;
import org.matmech.context.contextManager.ContextManager;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Этот класс-интерфейс, который реализует обертку над ботом в телеграмме
 */
public class TelegramBot implements Connector {
    private final TelegramHandler bot;

    public TelegramBot(String botUsername, String botToken, ContextManager contextManager) {
        bot = new TelegramHandler(botUsername, botToken, contextManager);
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
