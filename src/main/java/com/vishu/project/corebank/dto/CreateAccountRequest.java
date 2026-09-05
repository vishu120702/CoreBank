package com.vishu.project.corebank.dto;
import com.vishu.project.corebank.model.AccountType;

public class CreateAccountRequest {
    private String accountNumer;
    private String customerId;
    private AccountType type;
    private double initialBalance;

    public String getAccountNumer() {
        return accountNumer;
    }

    public void setAccountNumer(String accountNumer) {
        this.accountNumer = accountNumer;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }
}