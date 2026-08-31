package com.vishu.project.corebank.repository;

import com.vishu.project.corebank.model.Customer;
import com.vishu.project.corebank.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcCustomerRepository implements Repository<Customer, String> {

    @Override
    public Customer save(String customerId, Customer customer) {

        String sql = "INSERT INTO customers (customer_id, name, email) " +
                "VALUES (?, ?, ?) " +
                "ON CONFLICT (customer_id) DO UPDATE SET " +
                "name = EXCLUDED.name, " +
                "email = EXCLUDED.email";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customerId);
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getEmail());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to save customer: " + customerId, e);
        }

        return customer;
    }

    @Override
    public Optional<Customer> findById(String customerId) {

        String sql = "SELECT customer_id, name, email " +
                "FROM customers " +
                "WHERE customer_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customerId);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    Customer customer = new Customer(
                            rs.getString("customer_id"),
                            rs.getString("name"),
                            rs.getString("email")
                    );

                    return Optional.of(customer);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to find customer: " + customerId, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Customer> findAll() {

        String sql = "SELECT customer_id, name, email FROM customers";

        List<Customer> customers = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Customer customer = new Customer(
                        rs.getString("customer_id"),
                        rs.getString("name"),
                        rs.getString("email")
                );

                customers.add(customer);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch customers", e);
        }

        return customers;
    }

    @Override
    public void deleteById(String customerId) {

        String sql = "DELETE FROM customers WHERE customer_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customerId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to delete customer: " + customerId, e);
        }
    }
}