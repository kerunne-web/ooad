package com.banking.dao;

import com.banking.database.DatabaseConnection;
import com.banking.model.Account;
import com.banking.model.SavingsAccount;
import com.banking.model.InvestmentAccount;
import com.banking.model.ChequeAccount;
import com.banking.model.Customer;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    // CREATE: Save a new account
    public boolean saveAccount(Account account) {

        String sql = """
            INSERT INTO accounts
            (account_number, balance, branch, date_opened,
             account_type, customer_id, employer_name,
             employer_address)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String accountType;

            if (account instanceof SavingsAccount) {
                accountType = "SAVINGS";
            } else if (account instanceof InvestmentAccount) {
                accountType = "INVESTMENT";
            } else if (account instanceof ChequeAccount) {
                accountType = "CHEQUE";
            } else {
                throw new IllegalArgumentException("Unknown account type.");
            }

            statement.setString(1, account.getAccountNumber());
            statement.setBigDecimal(2, account.getBalance());
            statement.setString(3, account.getBranch());
            statement.setDate(4, java.sql.Date.valueOf(account.getDateOpened()));
            statement.setString(5, accountType);
            statement.setString(6, account.getCustomer().getCustomerID());

            if (account instanceof ChequeAccount cheque) {
                statement.setString(7, cheque.getEmployerName());
                statement.setString(8, cheque.getEmployerAddress());
            } else {
                statement.setNull(7, Types.VARCHAR);
                statement.setNull(8, Types.VARCHAR);
            }

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Could not save account: " + e.getMessage());
            return false;
        }
    }

    // UPDATE: Update account balance
    public boolean updateAccountBalance(
            String accountNumber,
            BigDecimal newBalance) {

        String sql = """
            UPDATE accounts
            SET balance = ?
            WHERE account_number = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBigDecimal(1, newBalance);
            statement.setString(2, accountNumber);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Could not update account: " + e.getMessage());
            return false;
        }
    }

    // DELETE: Delete an account
    public boolean deleteAccount(String accountNumber) {

        String sql = """
            DELETE FROM accounts
            WHERE account_number = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, accountNumber);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Could not delete account: " + e.getMessage());
            return false;
        }
    }

    // READ: Find all accounts belonging to a customer
    public List<Account> findAccountsByCustomerID(
            String customerID, Customer customer) {

        List<Account> accounts = new ArrayList<>();

        String sql = """
            SELECT *
            FROM accounts
            WHERE customer_id = ?
            ORDER BY account_number
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, customerID);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {

                    String accountNumber =
                            result.getString("account_number");

                    BigDecimal balance =
                            result.getBigDecimal("balance");

                    String branch = result.getString("branch");
                    String accountType = result.getString("account_type");

                    Account account = null;

                    switch (accountType) {

                        case "SAVINGS":
                            account = new SavingsAccount(
                                    accountNumber, balance, branch, customer);
                            break;

                        case "INVESTMENT":
                            account = new InvestmentAccount(
                                    accountNumber, balance, branch, customer);
                            break;

                        case "CHEQUE":
                            account = new ChequeAccount(
                                    accountNumber,
                                    balance,
                                    branch,
                                    customer,
                                    result.getString("employer_name"),
                                    result.getString("employer_address")
                            );
                            break;

                        default:
                            System.out.println(
                                    "Unknown account type: " + accountType);
                    }

                    if (account != null) {
                        account.setDateOpened(
                                result.getDate("date_opened").toLocalDate());

                        accounts.add(account);
                    }
                }
            }

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(
                    "Could not retrieve accounts: " + e.getMessage());
        }

        return accounts;
    }
}