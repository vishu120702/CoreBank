package com.vishu.project.corebank.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CURRENT")
public class CurrentAccount extends Account {

    protected CurrentAccount(){}
    // required by Hibernate — it builds objects via reflection,
    // not by calling your constructor with args

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