package com.banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.banking.database.DatabaseConnection;
import com.banking.model.Customer;

public class CustomerDAO {

    // CREATE: Save a new customer
    public boolean saveCustomer(Customer customer) {

        String sql = """
            INSERT INTO customers
            (customer_id, first_name, surname, address,
             phone, email, password)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, customer.getCustomerID());
            statement.setString(2, customer.getFirstName());
            statement.setString(3, customer.getSurname());
            statement.setString(4, customer.getAddress());
            statement.setString(5, customer.getPhone());
            statement.setString(6, customer.getEmail());
            statement.setString(7, customer.getPassword());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Could not save customer: " + e.getMessage());
            return false;
        }
    }

    // READ: Find a customer by ID
    public Customer findCustomerByID(String customerID) {

        String sql = """
            SELECT *
            FROM customers
            WHERE customer_id = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, customerID);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {
                    return new Customer(
                            result.getString("customer_id"),
                            result.getString("first_name"),
                            result.getString("surname"),
                            result.getString("address"),
                            result.getString("phone"),
                            result.getString("email"),
                            result.getString("password")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Could not find customer: " + e.getMessage());
        }

        return null;
    }

    // UPDATE: Update customer contact information
    public boolean updateCustomer(Customer customer) {

        String sql = """
            UPDATE customers
            SET address = ?, phone = ?, email = ?
            WHERE customer_id = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, customer.getAddress());
            statement.setString(2, customer.getPhone());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getCustomerID());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Could not update customer: " + e.getMessage());
            return false;
        }
    }

    // DELETE: Delete a customer
    public boolean deleteCustomer(String customerID) {

        String sql = """
            DELETE FROM customers
            WHERE customer_id = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, customerID);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Could not delete customer: " + e.getMessage());
            return false;
        }
    }
}