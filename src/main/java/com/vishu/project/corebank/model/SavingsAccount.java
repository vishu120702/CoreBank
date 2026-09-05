package com.vishu.project.corebank.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SAVINGS")
public class SavingsAccount extends Account {

    protected SavingsAccount(){}
    // required by Hibernate — it builds objects via reflection,
    // not by calling your constructor with args

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
