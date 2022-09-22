package org.matmech;

import connector.Connector;
import connector.TelegramBot;

public class Main {
    public static void main(String[] args) {
        // environment variables

        String TELEGRAM_BOT_USERNAME = System.getenv("TELEGRAM_BOT_USERNAME");
        String TELEGRAM_BOT_TOKEN = System.getenv("TELEGRAM_BOT_TOKEN");

        // bots

        Connector bot = new TelegramBot(TELEGRAM_BOT_USERNAME, TELEGRAM_BOT_TOKEN);
        bot.start();
    }
}