package com.banking;

import com.banking.model.*;
import java.math.BigDecimal;

public class TestCoreModel {

    public static void main(String[] args) {

        // Create a customer
        Customer customer = new Customer(
            "C001",
            "Dihno",
            "Walker",
            "Gaborone",
            "71235467",
            "dihno@gmail.com",
            "password123"
        );

        // Create a savings account
        SavingsAccount savings = new SavingsAccount(
            "sa001",
            new BigDecimal("1000.00"),
            "Gaborone Branch",
            customer
        );

        // Create an investment account
        InvestmentAccount investment = new InvestmentAccount(
            "ia001",
            new BigDecimal("1000.00"),
            "Gaborone Branch",
            customer
        );

        // Create a cheque account
        ChequeAccount cheque = new ChequeAccount(
            "ca001",
            new BigDecimal("2000.00"),
            "Gaborone Branch",
            customer,
            "ABC Company",
            "Gaborone"
        );

        // Test deposits and withdrawals
        investment.deposit(new BigDecimal("500.00"));
        investment.withdraw(new BigDecimal("200.00"));

        cheque.deposit(new BigDecimal("300.00"));
        cheque.withdraw(new BigDecimal("100.00"));

        // Test interest
        investment.payInterest();
        savings.payInterest();

        // Display account information
        System.out.println("CUSTOMER: " + customer.getFirstName()
                + " " + customer.getSurname());

        System.out.println("Number of accounts: "
                + customer.getAccounts().size());

        System.out.println("Savings balance: BWP " + savings.getBalance());
        System.out.println("Investment balance: BWP " + investment.getBalance());
        System.out.println("Cheque balance: BWP " + cheque.getBalance());

        System.out.println("Savings withdrawal allowed: "
                + savings.withdraw(new BigDecimal("100.00")));

        System.out.println("\nCore model test completed!");
    }
}
