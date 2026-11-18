package com.banking.database;

import java.math.BigDecimal;
import java.util.List;

import com.banking.dao.AccountDAO;
import com.banking.dao.CustomerDAO;
import com.banking.model.Account;
import com.banking.model.ChequeAccount;
import com.banking.model.Customer;
import com.banking.model.InvestmentAccount;
import com.banking.model.SavingsAccount;

public class TestDataInitializer {

    public static void createTestCustomer() {

        CustomerDAO customerDAO = new CustomerDAO();
        AccountDAO accountDAO = new AccountDAO();

        // Preserve the original login customer
        Customer customer = customerDAO.findCustomerByID("C001");

        if (customer == null) {

            customer = new Customer(
                    "C001",
                    "Dihno",
                    "Walker",
                    "Gaborone",
                    "71235467",
                    "dihno@gmail.com",
                    "1234"
            );

            if (!customerDAO.saveCustomer(customer)) {
                System.out.println("Could not create test customer.");
                return;
            }

            System.out.println("Test customer created.");

        } else {
            System.out.println("Test customer already exists.");
        }

        // Preserve and create the original three accounts if missing
        List<Account> existingAccounts =
                accountDAO.findAccountsByCustomerID("C001", customer);

        if (!accountExists(existingAccounts, "SAV001")) {

            SavingsAccount savings = new SavingsAccount(
                    "SAV001",
                    new BigDecimal("1000.00"),
                    "Gaborone Branch",
                    customer
            );

            accountDAO.saveAccount(savings);
        }

        if (!accountExists(existingAccounts, "INV001")) {

            InvestmentAccount investment = new InvestmentAccount(
                    "INV001",
                    new BigDecimal("1500.00"),
                    "Gaborone Branch",
                    customer
            );

            accountDAO.saveAccount(investment);
        }

        if (!accountExists(existingAccounts, "CHQ001")) {

            ChequeAccount cheque = new ChequeAccount(
                    "CHQ001",
                    new BigDecimal("2000.00"),
                    "Gaborone Branch",
                    customer,
                    "ABC Company",
                    "Gaborone"
            );

            accountDAO.saveAccount(cheque);
        }

        // Additional sample customers
        String[][] sampleCustomers = {
            {"C002", "Lorato", "Ticks"},
            {"C003", "Mpho", "Kgosi"},
            {"C004", "Tshepo", "Brown"},
            {"C005", "Sarah", "Molefe"},
            {"C006", "Neo", "Katse"},
            {"C007", "Grace", "Mondlizi"},
            {"C008", "Tebogo", "Pilato"},
            {"C009", "Thabo", "Moyo"},
            {"C010", "Mitchell", "Taylor"}
        };

        for (String[] data : sampleCustomers) {

            String customerID = data[0];

            Customer sampleCustomer =
                    customerDAO.findCustomerByID(customerID);

            if (sampleCustomer == null) {

                sampleCustomer = new Customer(
                        customerID,
                        data[1],
                        data[2],
                        "Gaborone",
                        "7123456" + customerID.substring(3),
                        customerID.toLowerCase() + "@gmail.com",
                        "1234"
                );

                if (!customerDAO.saveCustomer(sampleCustomer)) {
                    System.out.println(
                            "Could not create customer " + customerID);
                    continue;
                }
            }

            String accountNumber =
                    "CHQ" + customerID.substring(1);

            List<Account> customerAccounts =
                    accountDAO.findAccountsByCustomerID(
                            customerID, sampleCustomer);

            if (!accountExists(customerAccounts, accountNumber)) {

                ChequeAccount account = new ChequeAccount(
                        accountNumber,
                        new BigDecimal("1000.00"),
                        "Gaborone Branch",
                        sampleCustomer,
                        "Sample Employer",
                        "Gaborone"
                );

                if (accountDAO.saveAccount(account)) {
                    System.out.println(
                            "Created sample account: " + accountNumber);
                }
            }
        }

        System.out.println("Test data initialization completed.");
    }

    private static boolean accountExists(
            List<Account> accounts,
            String accountNumber) {

        for (Account account : accounts) {

            if (account.getAccountNumber().equals(accountNumber)) {
                return true;
            }
        }

        return false;
    }
}