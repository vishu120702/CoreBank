package com.corebank.exception;

public class MinimumBalanceViolationException extends Exception {
    public MinimumBalanceViolationException(String accountNumber, double minBalance) {
        super("Withdrawal would breach minimum balance of " + minBalance + " for account " + accountNumber);
    }
}
