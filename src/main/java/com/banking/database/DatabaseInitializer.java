package com.banking.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static boolean initialize() {

        String customersTable = """
            CREATE TABLE IF NOT EXISTS customers (
                customer_id VARCHAR(50) PRIMARY KEY,
                first_name VARCHAR(100) NOT NULL,
                surname VARCHAR(100) NOT NULL,
                address VARCHAR(255),
                phone VARCHAR(30),
                email VARCHAR(100) UNIQUE,
                password VARCHAR(255) NOT NULL
            )
            """;

        String accountsTable = """
            CREATE TABLE IF NOT EXISTS accounts (
                account_number VARCHAR(50) PRIMARY KEY,
                balance DECIMAL(15,2) NOT NULL,
                branch VARCHAR(100),
                date_opened DATE NOT NULL,
                account_type VARCHAR(30) NOT NULL,
                customer_id VARCHAR(50) NOT NULL,
                employer_name VARCHAR(150),
                employer_address VARCHAR(255),
                FOREIGN KEY (customer_id)
                    REFERENCES customers(customer_id)
            )
            """;

        String transactionsTable = """
            CREATE TABLE IF NOT EXISTS transactions (
                transaction_id VARCHAR(50) PRIMARY KEY,
                type VARCHAR(30) NOT NULL,
                amount DECIMAL(15,2) NOT NULL,
                transaction_date TIMESTAMP NOT NULL,
                account_number VARCHAR(50) NOT NULL,
                FOREIGN KEY (account_number)
                    REFERENCES accounts(account_number)
            )
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(customersTable);
            statement.execute(accountsTable);
            statement.execute(transactionsTable);

            System.out.println("Database tables created successfully!");
            return true;

        } catch (SQLException e) {
            System.out.println("Database initialization failed!");
            e.printStackTrace();
            return false;
        }
    }
}