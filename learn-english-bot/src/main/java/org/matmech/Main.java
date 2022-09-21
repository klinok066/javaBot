package org.matmech;

import connector.Connector;
import connector.TelegramBot;

public class Main {
    public static void main(String[] args) {
        Connector bot = new TelegramBot();
        bot.start();
    }
}