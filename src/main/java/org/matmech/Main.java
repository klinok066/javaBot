package org.matmech;

import org.matmech.connector.cmdLogic.CmdLogic;
import org.matmech.connector.Connector;
import org.matmech.connector.telegramBot.TelegramBot;
import org.matmech.dbHandler.DBHandler;
import org.matmech.requestHandler.RequestHandler;

public class Main {
    public static void main(String[] args) {
        // environment variables

        String TELEGRAM_BOT_USERNAME = System.getenv("TELEGRAM_BOT_USERNAME");
        String TELEGRAM_BOT_TOKEN = System.getenv("TELEGRAM_BOT_TOKEN");
        String DB_URL = System.getenv("DB_URL");
        String DB_USERNAME = System.getenv("DB_USERNAME");
        String DB_PASSWORD = System.getenv("DB_PASSWORD");

        // database

        DBHandler db = new DBHandler(DB_URL, DB_USERNAME, DB_PASSWORD);
        db.init();

        // request handler

        RequestHandler requestHandler = new RequestHandler(db);

        // bots

        Connector bot = new TelegramBot(TELEGRAM_BOT_USERNAME, TELEGRAM_BOT_TOKEN, requestHandler);
        bot.start();

        CmdLogic cmd = new CmdLogic("User", "Unknown", requestHandler);
        cmd.responseForCDM();
    }
}