package com.banking.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer {

    private String customerID;
    private String firstName;
    private String surname;
    private String address;
    private String phone;
    private String email;
    private String password;

    // A customer can have multiple accounts
    private List<Account> accounts;

    public Customer(
            String customerID,
            String firstName,
            String surname,
            String address,
            String phone,
            String email,
            String password) {

        if (customerID == null || customerID.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID is required.");
        }

        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required.");
        }

        if (surname == null || surname.trim().isEmpty()) {
            throw new IllegalArgumentException("Surname is required.");
        }

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password is required.");
        }

        this.customerID = customerID.trim();
        this.firstName = firstName.trim();
        this.surname = surname.trim();
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.password = password;

        this.accounts = new ArrayList<>();
    }

    // Add an account to the customer's account list
    public void addAccount(Account account) {

        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }

        if (account.getCustomer() != this) {
            throw new IllegalArgumentException(
                    "This account does not belong to this customer.");
        }

        if (!accounts.contains(account)) {
            accounts.add(account);
        }
    }

    // Get all customer accounts
    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    // Customer getters
    public String getCustomerID() {
        return customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Update customer address
    public void setAddress(String address) {
        this.address = address;
    }

    // Update customer phone number
    public void setPhone(String phone) {
        this.phone = phone;
    }
}