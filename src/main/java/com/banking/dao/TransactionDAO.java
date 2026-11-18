package com.banking.dao;

import com.banking.database.DatabaseConnection;
import com.banking.model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    // CREATE: Save one transaction
    public boolean saveTransaction(
            Transaction transaction,
            String accountNumber) {

        String sql = """
            INSERT INTO transactions
            (transaction_id, type, amount,
             transaction_date, account_number)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, transaction.getTransactionID());
            statement.setString(2, transaction.getType());
            statement.setBigDecimal(3, transaction.getAmount());
            statement.setTimestamp(
                    4, Timestamp.valueOf(transaction.getDate()));
            statement.setString(5, accountNumber);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Could not save transaction: " + e.getMessage());
            return false;
        }
    }

    // READ: Retrieve transactions belonging to an account
    public List<Transaction> findTransactionsByAccount(
            String accountNumber) {

        List<Transaction> transactions = new ArrayList<>();

        String sql = """
            SELECT *
            FROM transactions
            WHERE account_number = ?
            ORDER BY transaction_date DESC
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, accountNumber);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {

                    Transaction transaction = new Transaction(
                            result.getString("transaction_id"),
                            result.getString("type"),
                            result.getBigDecimal("amount")
                    );

                    transaction.setDate(
                            result.getTimestamp("transaction_date")
                                    .toLocalDateTime()
                    );

                    transactions.add(transaction);
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Could not retrieve transactions: " + e.getMessage());
        }

        return transactions;
    }

    // DELETE: Delete a transaction by ID
    public boolean deleteTransaction(String transactionID) {

        String sql = """
            DELETE FROM transactions
            WHERE transaction_id = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, transactionID);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Could not delete transaction: " + e.getMessage());
            return false;
        }
    }
}