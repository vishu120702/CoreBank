package com.vishu.project.corebank.exception;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String accountNumber, double balance, double requestedAmount) {
        super("Insufficient funds in account " + accountNumber + ": balance=" + balance + ", requested="
                + requestedAmount);
    }
}
