package com.banking.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {

    private String transactionID;
    private String type;
    private BigDecimal amount;
    private LocalDateTime date;

    public Transaction(String transactionID, String type, BigDecimal amount) {

        if (transactionID == null || transactionID.trim().isEmpty()) {
            throw new IllegalArgumentException("Transaction ID is required.");
        }

        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Transaction type is required.");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Transaction amount must be greater than zero.");
        }

        this.transactionID = transactionID;
        this.type = type;
        this.amount = amount;
        this.date = LocalDateTime.now();
    }

    public String getTransactionID() {
        return transactionID;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        if (date == null) {
            throw new IllegalArgumentException("Transaction date is required.");
        }

        this.date = date;
    }
}