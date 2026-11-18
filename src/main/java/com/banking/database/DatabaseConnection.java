package com.banking.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:h2:./bankingdb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private DatabaseConnection() {
        // Prevent creating instances of this utility class.
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}