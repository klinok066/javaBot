package org.matmech;

import connector.CmdLogic;
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

        System.out.println("Hello, i'm bot");
        CmdLogic cmd = new CmdLogic("User", "Unknown", "unknown", "false", "-1");
        cmd.responseForCDM();
    }
}