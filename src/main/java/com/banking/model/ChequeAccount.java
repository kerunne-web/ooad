package com.banking.model;

import java.math.BigDecimal;

public class ChequeAccount extends Account {

    private String employerName;
    private String employerAddress;

    public ChequeAccount(
            String accountNumber,
            BigDecimal openingBalance,
            String branch,
            Customer customer,
            String employerName,
            String employerAddress) {

        super(accountNumber, openingBalance, branch, customer);

        if (employerName == null || employerName.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Employer name is required.");
        }

        if (employerAddress == null || employerAddress.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Employer address is required.");
        }

        this.employerName = employerName.trim();
        this.employerAddress = employerAddress.trim();

        customer.addAccount(this);
    }

    @Override
    public boolean withdraw(BigDecimal amount) {

        if (amount == null ||
                amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        if (getBalance().compareTo(amount) < 0) {
            return false;
        }

        debitBalance(amount);
        recordTransaction("WITHDRAWAL", amount);

        return true;
    }

    public String getEmployerName() {
        return employerName;
    }

    public String getEmployerAddress() {
        return employerAddress;
    }
}