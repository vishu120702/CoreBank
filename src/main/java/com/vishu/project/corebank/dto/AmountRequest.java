// AmountRequest.java — reused for BOTH deposit and withdraw (same shape: just an amount)
package com.vishu.project.corebank.dto;

public class AmountRequest {
    private double amount;

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}