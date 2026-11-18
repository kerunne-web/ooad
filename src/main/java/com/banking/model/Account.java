package com.banking.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public abstract class Account {

    private String accountNumber;
    private BigDecimal balance;
    private String branch;
    private LocalDate dateOpened;
    private Customer customer;

    private List<Transaction> transactions;

    public Account(
            String accountNumber,
            BigDecimal openingBalance,
            String branch,
            Customer customer) {

        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Account number cannot be empty.");
        }

        if (openingBalance == null ||
                openingBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Invalid opening balance.");
        }

        if (customer == null) {
            throw new IllegalArgumentException(
                    "An account must belong to a customer.");
        }

        this.accountNumber = accountNumber;
        this.balance = openingBalance;
        this.branch = branch;
        this.customer = customer;
        this.dateOpened = LocalDate.now();
        this.transactions = new ArrayList<>();
    }

    public void deposit(BigDecimal amount) {

        if (amount == null ||
                amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Deposit must be greater than zero.");
        }

        balance = balance.add(amount);
        recordTransaction("DEPOSIT", amount);
    }

    public abstract boolean withdraw(BigDecimal amount);

    protected void creditInterest(BigDecimal interest) {
        balance = balance.add(interest);
    }

    protected void debitBalance(BigDecimal amount) {
        balance = balance.subtract(amount);
    }

    protected void recordTransaction(String type, BigDecimal amount) {

        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                type,
                amount
        );

        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getBranch() {
        return branch;
    }

    public LocalDate getDateOpened() {
        return dateOpened;
    }

    // Restore the original opening date when loading from the database
    public void setDateOpened(LocalDate dateOpened) {

        if (dateOpened == null) {
            throw new IllegalArgumentException(
                    "Opening date cannot be null.");
        }

        this.dateOpened = dateOpened;
    }

    public Customer getCustomer() {
        return customer;
    }
}