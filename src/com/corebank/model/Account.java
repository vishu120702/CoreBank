package com.corebank.model;

public abstract class Account{
    private String accountNumer;
    private double balance;
    private Customer owner;

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

    public abstract double getInterestRate();

    public abstract double getMinimumBalance();

    @Override
    public String toString() {
        return "Account [accountNumer=" + accountNumer + ", balance=" + balance + ", owner=" + owner + "]";
    }

    

    
    
}