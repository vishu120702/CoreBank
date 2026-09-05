package com.vishu.project.corebank.model;

import jakarta.persistence.*;

@Entity
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type")   // ← this is the extra column
public abstract class Account {
    @Id
    private String accountNumer;
    private double balance;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer owner;

    // protected (not public): exists only for Hibernate to build this object via
    // reflection when reading a row from the DB. Blocks accidental use like
    // `new SavingsAccount()` from other packages, which would create a broken
    // object with no accountNumer/balance/owner set. Always use the real
    // constructor below for actual object creation.
    protected Account(){}
    // required by Hibernate — it builds objects via reflection,
    // not by calling your constructor with args

    public Account(String accountNumer, double balance, Customer owner) {
        this.accountNumer = accountNumer;
        this.balance = balance;
        this.owner = owner;
    }

    public String getAccountNumer() {
        return accountNumer;
    }

    public void setAccountNumer(String accountNumer) {
        this.accountNumer = accountNumer;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public abstract double getInterestRate(double balance);

    public abstract double getMinimumBalance();

    @Override
    public String toString() {
        return "Account [accountNumer=" + accountNumer + ", balance=" + balance + ", owner=" + owner + "]";
    }

    // implement it here, once, non-abstract
    public double calculateInterest() {
        return this.getBalance() * this.getInterestRate(getBalance()) / 100;
    }

}