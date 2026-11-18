package com.banking.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SavingsAccount extends Account implements InterestBearing {

    private static final BigDecimal INTEREST_RATE =
            new BigDecimal("0.0005");

    public SavingsAccount(String accountNumber,
                          BigDecimal openingBalance,
                          String branch,
                          Customer customer) {

        super(accountNumber, openingBalance, branch, customer);
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

        return false;
    }
}