package com.vishu.project.corebank.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="transactions")
public class Transaction {
    @Id
    private String transactionId;
    private String accountNumber;

    @Enumerated(EnumType.STRING)  // stores "DEPOSIT"/"WITHDRAW" as text, not 0/1
    private TransactionType type;

    private double amount;
    private LocalDateTime timestamp;

    protected Transaction(){}; // Hibernate-only — same pattern as your other entities

    public Transaction(String transactionId, String accountNumber, TransactionType type, double amount) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // @Override
    // public String toString() {
    // return "Transaction [transactionId=" + transactionId + ", accountNumber=" +
    // accountNumber + ", type=" + type
    // + ", amount=" + amount + ", timestamp=" + timestamp + "]";
    // }

    @Override
    public String toString() {
        return type + " of " + amount + " on account " + accountNumber + " at " + timestamp;
    }
}
