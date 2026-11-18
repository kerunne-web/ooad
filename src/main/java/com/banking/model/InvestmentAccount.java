package com.banking.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class InvestmentAccount extends Account implements InterestBearing {

    private static final BigDecimal INTEREST_RATE =
            new BigDecimal("0.05");

    private static final BigDecimal MIN_OPENING_DEPOSIT =
            new BigDecimal("500.00");

    public InvestmentAccount(
            String accountNumber,
            BigDecimal openingBalance,
            String branch,
            Customer customer) {

        super(accountNumber, openingBalance, branch, customer);

        if (openingBalance.compareTo(MIN_OPENING_DEPOSIT) < 0) {
            throw new IllegalArgumentException(
                    "Investment account requires a minimum opening deposit of BWP500.");
        }

        customer.addAccount(this);
    }

    @Override
    public BigDecimal calculateInterest() {

        return getBalance()
                .multiply(INTEREST_RATE)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public void payInterest() {

        BigDecimal interest = calculateInterest();

        creditInterest(interest);

        recordTransaction("INTEREST", interest);
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
}