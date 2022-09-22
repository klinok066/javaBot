package connector.telegramHandler;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import requestHandler.RequestHandler;

public class TelegramHandler extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "matmech_learn_english_bot";
    }

    @Override
    public String getBotToken() {
        return "5657871435:AAFzHduN1o9i3LxzNEfeXqAAdf-GSu50N2c";
    }

    @Override
    public void onUpdateReceived(Update update) {
        RequestHandler requestHandler = new RequestHandler();
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());
        message.setText("Hello!");

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
