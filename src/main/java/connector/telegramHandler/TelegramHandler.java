package connector.telegramHandler;

import connector.TelegramBot;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import requestHandler.RequestHandler;

public class TelegramHandler extends TelegramLongPollingBot {
    private final String botUsername;
    private final String botToken;

    public TelegramHandler(String botUsername, String botToken) {
        this.botUsername = botUsername;
        this.botToken = botToken;
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
        RequestHandler requestHandler = new RequestHandler();
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());

        if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().charAt(0) == '/') {
            StringBuilder answer = new StringBuilder("");
            String userTextMessage = requestHandler.formatCommandFromTelegram(update.getMessage().getText());
            requestHandler.useCommand(userTextMessage, answer);
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
