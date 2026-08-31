package com.vishu.project.corebank.model;

import java.time.LocalDateTime;

public class Transaction {
    private String transactionId;
    private String accountNumber;
    private TransactionType type;
    private double amount;
    private LocalDateTime timestamp;

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
