package org.matmech.dbHandler;

import java.sql.*;

public class DBHandler {
    private final String DB_URL;
    private final String DB_USERNAME;
    private final String DB_PASSWORD;
    private Connection connection;

    public DBHandler(String DB_URL, String DB_USERNAME, String DB_PASSWORD) {
        this.DB_URL = DB_URL;
        this.DB_USERNAME = DB_USERNAME;
        this.DB_PASSWORD = DB_PASSWORD;
    }

    public void init() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void connectionClose() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
