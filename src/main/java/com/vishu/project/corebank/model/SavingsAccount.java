package com.vishu.project.corebank.model;

public class SavingsAccount extends Account {

    public SavingsAccount(String accountNumer, double balance, Customer owner) {
        super(accountNumer, balance, owner);
    }

    @Override
    public double getInterestRate(double balance) {
        return 4.0;
    }

    @Override
    public double getMinimumBalance() {
        return 1000.0;
    }

}
