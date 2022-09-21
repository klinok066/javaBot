package connector.telegramHandler;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
        SendMessage message = new SendMessage();
        message.setText("Привет, Мир!");

        {
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
