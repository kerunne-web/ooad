package com.banking.controller;

import com.banking.dao.CustomerDAO;
import com.banking.dao.AccountDAO;
import com.banking.model.Customer;
import com.banking.model.Account;
import com.banking.view.LoginView;
import com.banking.view.AccountView;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class LoginController {

    private LoginView loginView;
    private CustomerDAO customerDAO;
    private AccountDAO accountDAO;
    private Stage primaryStage;

    public LoginController(LoginView loginView, Stage primaryStage) {

        this.loginView = loginView;
        this.primaryStage = primaryStage;

        this.customerDAO = new CustomerDAO();
        this.accountDAO = new AccountDAO();

        loginView.getLoginButton().setOnAction(event -> login());
    }

    private void login() {

        String customerID = loginView.getCustomerID().trim();
        String password = loginView.getPassword();

        if (customerID.isEmpty() || password.isEmpty()) {
            loginView.showMessage("Please enter your ID and password.");
            return;
        }

        try {
            Customer customer = customerDAO.findCustomerByID(customerID);

            if (customer == null || !customer.getPassword().equals(password)) {
                loginView.showMessage("Invalid customer ID or password.");
                return;
            }

            List<Account> accounts =
                    accountDAO.findAccountsByCustomerID(customerID, customer);

            if (accounts == null || accounts.isEmpty()) {
                loginView.showMessage("No bank accounts found.");
                return;
            }

            openDashboard(customer, accounts);

        } catch (Exception e) {
            e.printStackTrace();
            loginView.showMessage("Unable to log in. Please try again.");
        }
    }

    private void openDashboard(Customer customer, List<Account> accounts) {

        AccountView accountView = new AccountView();

        List<String> accountOptions = new ArrayList<>();

        for (Account account : accounts) {
            accountOptions.add(
                    account.getAccountNumber() + " - "
                    + account.getClass().getSimpleName()
            );
        }

        accountView.setAccountOptions(accountOptions);
        accountView.setSelectedAccount(0);

        displayAccount(customer, accounts.get(0), accountView);

        accountView.getAccountSelector().setOnAction(event -> {

            int selectedIndex = accountView.getAccountSelector()
                    .getSelectionModel().getSelectedIndex();

            if (selectedIndex >= 0 && selectedIndex < accounts.size()) {
                displayAccount(
                        customer,
                        accounts.get(selectedIndex),
                        accountView
                );
            }
        });

        Scene dashboardScene = new Scene(accountView, 500, 500);

        primaryStage.setTitle("Banking Management System - Dashboard");
        primaryStage.setScene(dashboardScene);
    }

    private void displayAccount(
            Customer customer,
            Account account,
            AccountView accountView) {

        accountView.setAccountInformation(
                customer.getFirstName() + " " + customer.getSurname(),
                account.getAccountNumber(),
                account.getClass().getSimpleName(),
                account.getBalance()
        );

        new AccountController(accountView, account);
    }
}