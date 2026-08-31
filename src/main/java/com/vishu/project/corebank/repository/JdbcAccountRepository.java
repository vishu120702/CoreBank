package com.vishu.project.corebank.repository;

import com.vishu.project.corebank.model.Account;
import com.vishu.project.corebank.model.CurrentAccount;
import com.vishu.project.corebank.model.Customer;
import com.vishu.project.corebank.model.SavingsAccount;
import com.vishu.project.corebank.util.DatabaseConnection;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcAccountRepository implements Repository<Account, String>{

    @Override
    public Account save(String accountNumber, Account account) {
        String sql = "INSERT INTO accounts (account_number, account_type, balance, customer_id) " +
                "VALUES (?, ?, ?, ?) " +
                "ON CONFLICT (account_number) DO UPDATE SET balance = EXCLUDED.balance";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String type = (account instanceof SavingsAccount) ? "SAVINGS" : "CURRENT";
            stmt.setString(1, accountNumber);
            stmt.setString(2, type);
            stmt.setDouble(3, account.getBalance());
            stmt.setString(4, account.getOwner().getCustomerId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save account: " + accountNumber, e);
        }
        return account;
    }

    @Override
    public Optional<Account> findById(String accountNumber) {
        String sql = "SELECT a.account_number, a.account_type, a.balance, " +
                "c.customer_id, c.name, c.email " +
                "FROM accounts a JOIN customers c ON a.customer_id = c.customer_id " +
                "WHERE a.account_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accountNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find account: " + accountNumber, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Account> findAll() {
        String sql = "SELECT a.account_number, a.account_type, a.balance, " +
                "c.customer_id, c.name, c.email " +
                "FROM accounts a JOIN customers c ON a.customer_id = c.customer_id";
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                accounts.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch accounts", e);
        }
        return accounts;
    }

    @Override
    public void deleteById(String accountNumber) {
        String sql = "DELETE FROM accounts WHERE account_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete account: " + accountNumber, e);
        }
    }

    private Account mapRow(ResultSet rs) throws SQLException {
        Customer customer = new Customer(rs.getString("customer_id"), rs.getString("name"), rs.getString("email"));
        double balance = rs.getDouble("balance");
        String accountNumber = rs.getString("account_number");

        if (rs.getString("account_type").equals("SAVINGS")) {
            return new SavingsAccount(accountNumber, balance, customer);
        }
        return new CurrentAccount(accountNumber, balance, customer);
    }

}
