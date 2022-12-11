package org.matmech;

import org.matmech.connector.vk.vkBot.VKBot;
import org.matmech.context.Context;
import org.matmech.connector.cmd.cmdBot.CmdBot;
import org.matmech.connector.Connector;
import org.matmech.connector.telegram.telegramBot.TelegramBot;
import org.matmech.db.DBHandler;
import org.matmech.requests.requestHandler.RequestHandler;

public class Main {
    final static String TELEGRAM_BOT_USERNAME = System.getenv("TELEGRAM_BOT_USERNAME");
    final static String TELEGRAM_BOT_TOKEN = System.getenv("TELEGRAM_BOT_TOKEN");
    final static int VK_BOT_GROUP_ID = Integer.parseInt(System.getenv("VK_BOT_GROUP_ID"));
    final static String VK_BOT_ACCESS_TOKEN = System.getenv("VK_BOT_ACCESS_TOKEN");
    final static String DB_URL = System.getenv("DB_URL");
    final static String DB_USERNAME = System.getenv("DB_USERNAME");
    final static String DB_PASSWORD = System.getenv("DB_PASSWORD");

    public static void main(String[] args) {
        // database

        DBHandler db = new DBHandler(DB_URL, DB_USERNAME, DB_PASSWORD);

        // cache

        Context cache = new Context();

        // request handler

        RequestHandler requestHandler = new RequestHandler(db, cache);

        // bots

        Connector bot = new TelegramBot(TELEGRAM_BOT_USERNAME, TELEGRAM_BOT_TOKEN, requestHandler);
        bot.start();

        VKBot vkBot = new VKBot(VK_BOT_GROUP_ID, VK_BOT_ACCESS_TOKEN, requestHandler);
        vkBot.start();

        CmdBot cmdBot = new CmdBot(requestHandler);
        cmdBot.start();
    }
}