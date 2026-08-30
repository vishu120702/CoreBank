package com.corebank.concurrency;

import com.corebank.exception.AccountNotFoundException;
import com.corebank.exception.InsufficientFundsException;
import com.corebank.exception.MinimumBalanceViolationException;
import com.corebank.service.BankService;

public class WithdrawalSimulator implements Runnable{
    BankService bankService;
    String accountNumber;
    double amountToWithdraw;

    public WithdrawalSimulator(BankService bankService, String accountNumber, double amountToWithdraw){
        this.bankService=bankService;
        this.accountNumber=accountNumber;
        this.amountToWithdraw=amountToWithdraw;
    }

    @Override
    public void run() {
        try {
            bankService.withdraw(accountNumber, amountToWithdraw);
            System.out.println(Thread.currentThread().getName() + " withdrew " + amountToWithdraw + " successfully.");
        } catch (AccountNotFoundException | InsufficientFundsException e) {
            System.out.println(Thread.currentThread().getName() + " failed: " + e.getMessage());
        } catch (MinimumBalanceViolationException e) {
            System.out.println(Thread.currentThread().getName() + " failed (min balance): " + e.getMessage());
        }
    }
    
}
