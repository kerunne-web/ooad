package com.banking.controller;

import com.banking.dao.AccountDAO;
import com.banking.dao.TransactionDAO;
import com.banking.model.Account;
import com.banking.model.Transaction;
import com.banking.view.AccountView;
import com.banking.view.TransactionView;

import java.math.BigDecimal;
import java.util.List;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class AccountController {

    private AccountView accountView;
    private Account account;
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;

    public AccountController(AccountView accountView, Account account) {

        this.accountView = accountView;
        this.account = account;

        this.accountDAO = new AccountDAO();
        this.transactionDAO = new TransactionDAO();

        accountView.getDepositButton().setOnAction(event -> deposit());
        accountView.getWithdrawButton().setOnAction(event -> withdraw());
        accountView.getHistoryButton().setOnAction(event -> openTransactionHistory());

        accountView.updateBalance(account.getBalance());
    }

    private void deposit() {

        try {
            String input = accountView.getAmount().trim();
            BigDecimal amount = new BigDecimal(input);

            account.deposit(amount);

            saveLatestTransaction();

            accountDAO.updateAccountBalance(
                    account.getAccountNumber(),
                    account.getBalance()
            );

            accountView.updateBalance(account.getBalance());
            accountView.showMessage("Deposit successful!");
            accountView.clearAmountField();

        } catch (NumberFormatException e) {
            accountView.showMessage("Please enter a valid amount.");

        } catch (IllegalArgumentException e) {
            accountView.showMessage(e.getMessage());
        }
    }

    private void withdraw() {

        try {
            String input = accountView.getAmount().trim();
            BigDecimal amount = new BigDecimal(input);

            boolean successful = account.withdraw(amount);

            if (successful) {

                saveLatestTransaction();

                accountDAO.updateAccountBalance(
                        account.getAccountNumber(),
                        account.getBalance()
                );

                accountView.updateBalance(account.getBalance());
                accountView.showMessage("Withdrawal successful!");
                accountView.clearAmountField();

            } else {
                accountView.showMessage(
                        "Withdrawal not allowed or insufficient funds."
                );
            }

        } catch (NumberFormatException e) {
            accountView.showMessage("Please enter a valid amount.");

        } catch (IllegalArgumentException e) {
            accountView.showMessage(e.getMessage());
        }
    }

    private void saveLatestTransaction() {

        List<Transaction> transactions = account.getTransactions();

        if (!transactions.isEmpty()) {

            Transaction latest = transactions.get(transactions.size() - 1);

            transactionDAO.saveTransaction(
                    latest,
                    account.getAccountNumber()
            );
        }
    }

    private void openTransactionHistory() {

        TransactionView transactionView = new TransactionView();

        TransactionController transactionController =
                new TransactionController(transactionView, account);

        transactionController.loadTransactions();

        Stage historyStage = new Stage();
        historyStage.setTitle("Transaction History");
        historyStage.setScene(new Scene(transactionView, 800, 400));
        historyStage.show();
    }
}