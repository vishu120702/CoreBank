package com.corebank.model;

public class CurrentAccount extends Account {

    public CurrentAccount(String accountNumer, double balance, Customer owner) {
        super(accountNumer, balance, owner);
        
    }

    @Override
    public double getInterestRate(double balance) {
        return balance > 50000 ? 3.5 : 0.0;
    }

    @Override
    public double getMinimumBalance() {
        return 5000.0;
    }
    
}
