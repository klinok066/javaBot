package connector;

import connector.telegramHandler.TelegramHandler;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TelegramBot implements Connector {
    private final TelegramHandler bot;

    public TelegramBot(String botUsername, String botToken) {
        bot = new TelegramHandler(botUsername, botToken);
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
