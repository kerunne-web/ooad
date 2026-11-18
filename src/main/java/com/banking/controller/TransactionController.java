package com.banking.controller;

import com.banking.dao.TransactionDAO;
import com.banking.model.Account;
import com.banking.model.Transaction;
import com.banking.view.TransactionView;

import java.util.List;

public class TransactionController {

    private TransactionView transactionView;
    private Account account;
    private TransactionDAO transactionDAO;

    public TransactionController(
            TransactionView transactionView,
            Account account) {

        this.transactionView = transactionView;
        this.account = account;
        this.transactionDAO = new TransactionDAO();
    }

    public void loadTransactions() {

        try {
            List<Transaction> transactions =
                    transactionDAO.findTransactionsByAccount(
                            account.getAccountNumber()
                    );

            transactionView.displayTransactions(transactions);

        } catch (Exception e) {
            e.printStackTrace();
            transactionView.showMessage(
                    "Unable to load transaction history."
            );
        }
    }
}