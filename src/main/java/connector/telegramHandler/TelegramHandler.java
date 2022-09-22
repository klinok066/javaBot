package connector.telegramHandler;

import connector.TelegramBot;
import org.json.JSONObject;
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
        JSONObject info = new JSONObject();

        if (update.hasMessage() && update.getMessage().hasText()) {
            StringBuilder answer = new StringBuilder("");
            String userTextMessage;

            if (update.getMessage().getText().charAt(0) == '/') {
                // forming answer for message

                userTextMessage = requestHandler.formatCommandFromTelegram(update.getMessage().getText());
                answer.append(requestHandler.useCommand(userTextMessage));
            } else {
                // creating JSON object with all information about user which send message
                info.put("firstName", update.getMessage().getFrom().getFirstName());
                info.put("lastName", update.getMessage().getFrom().getLastName());
                info.put("username", update.getMessage().getFrom().getUserName());
                info.put("isBot", update.getMessage().getFrom().getIsBot());
                info.put("id", update.getMessage().getFrom().getId());

                // forming answer for message

                userTextMessage = update.getMessage().getText();
                answer.append(requestHandler.toAnswer(userTextMessage, info));
            }

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
