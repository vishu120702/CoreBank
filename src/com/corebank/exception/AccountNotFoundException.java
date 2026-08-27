package com.corebank.exception;

public class AccountNotFoundException extends Exception {
    public AccountNotFoundException(String accountNumber) {
        // super(accountNumber);
        // System.out.println("Account not found: " + accountNumber);
        super("Account not found: " + accountNumber);
    }
}
