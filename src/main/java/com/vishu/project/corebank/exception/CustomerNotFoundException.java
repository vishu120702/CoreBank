package com.vishu.project.corebank.exception;

public class CustomerNotFoundException extends Exception {
    public CustomerNotFoundException(String customerId) {
        super("Customer not found: " + customerId);
    }
}