package com.orangia.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/orangia_db";

    private static final String USER = "postgres";

    private static final String PASSWORD = System.getenv("ORANGIA_DB_PASSWORD");

    public static Connection getConnection() throws SQLException {

        if (PASSWORD == null || PASSWORD.isBlank()) {
            throw new SQLException("Database password is not configured.");
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}