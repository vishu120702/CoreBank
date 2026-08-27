package com.corebank;

import com.corebank.exception.AccountNotFoundException;
import com.corebank.exception.InsufficientFundsException;
import com.corebank.exception.MinimumBalanceViolationException;
import com.corebank.model.CurrentAccount;
import com.corebank.model.Customer;
import com.corebank.model.SavingsAccount;
import com.corebank.repository.AccountRepository;
import com.corebank.repository.CustomerRepository;
import com.corebank.service.BankService;

public class Main {
    public static void main(String[] args) {
        CustomerRepository customerRepo = new CustomerRepository();
        AccountRepository accountRepo = new AccountRepository();

        Customer c1 = new Customer("CUST101", "Vishwambhar", "vishutambekar@gmail.com");
        Customer c2 = new Customer("CUST102", "Yash", "yashchougule@gmail.com");

        customerRepo.save(c1.getCustomerId(), c1);
        customerRepo.save(c2.getCustomerId(), c2);
        // System.out.println(customerRepo.save(c1.getCustomerId(), c1));

        // System.out.println(customerRepo.findById("CUST101"));

        SavingsAccount saving = new SavingsAccount("SAV001", 59_000.0, c1);
        CurrentAccount current = new CurrentAccount("CUR001", 1_29_000, c1);
        accountRepo.save(saving.getAccountNumer(), saving);
        accountRepo.save(current.getAccountNumer(), current);
        SavingsAccount saving1 = new SavingsAccount("SAV002", 1_26_000, c2);
        accountRepo.save(saving1.getAccountNumer(), saving1);

        // Quick sanity check that everything wired correctly
        // accountRepo.findById(saving1.getAccountNumer())
        //         .ifPresent(account -> System.out
        //                 .println(account.getOwner().getName() + "'s savings balance: " + account.getBalance()
        //                         + " at " + account.getInterestRate() + "% interest"));

        // System.out.println(saving);
        // accountRepo.findById("CUR01").ifPresent( ac ->
        // System.out.println(ac.getOwner().getName()));
        // System.out.println(accountRepo.findAll().size());
        // System.out.println(customerRepo.findAll().size());

        BankService bankService = new BankService(accountRepo);
        // accountRepo.findById(saving1.getAccountNumer())
        // .ifPresent(acc -> System.out.println(acc.getOwner().getName() + "'s balance
        // befor deposit: " + acc.getBalance()));

        // try {
        // bankService.deposit(saving1.getAccountNumer(), 72_000.0);
        // } catch (AccountNotFoundException e) {
        // e.printStackTrace();
        // }

        // accountRepo.findById(saving1.getAccountNumer())
        // .ifPresent(acc -> System.out.println(acc.getOwner().getName() + "'s balance
        // after deposit: " + acc.getBalance()));

        // 1. Normal successful withdrawal — SAV001: 59000 -> 54000
        try {
            bankService.withdraw("SAV001", 5000.0);
            System.out.println("Withdraw OK. SAV001 balance: " +
                    accountRepo.findById("SAV001").get().getBalance());
        } catch (AccountNotFoundException | InsufficientFundsException e) {
            System.out.println("Withdraw failed: " + e.getMessage());
        } catch (MinimumBalanceViolationException e) {
            System.out.println("Withdraw failed (min balance): " + e.getMessage());
        }

        // 2. Withdraw more than the balance -> InsufficientFundsException (SAV002 has
        // 126000)
        try {
            bankService.withdraw("SAV002", 500000.0);
        } catch (AccountNotFoundException | InsufficientFundsException e) {
            System.out.println("Withdraw failed: " + e.getMessage());
        } catch (MinimumBalanceViolationException e) {
            System.out.println("Withdraw failed (min balance): " + e.getMessage());
        }

        // 3. Withdraw an amount that would breach minimum balance (leaves 500, below
        // 1000 minimum)
        try {
            bankService.withdraw("SAV002", 125500.0);
        } catch (AccountNotFoundException | InsufficientFundsException e) {
            System.out.println("Withdraw failed: " + e.getMessage());
        } catch (MinimumBalanceViolationException e) {
            System.out.println("Withdraw failed (min balance): " + e.getMessage());
        }

        // 4. Successful transfer: Vishwambhar's current -> Vishwambhar's savings
        try {
            bankService.transfer("CUR001", "SAV001", 10000.0);
            System.out.println("Transfer successful.");
            System.out.println("CUR001 balance: " + accountRepo.findById("CUR001").get().getBalance());
            System.out.println("SAV001 balance: " + accountRepo.findById("SAV001").get().getBalance());
        } catch (AccountNotFoundException | InsufficientFundsException e) {
            System.out.println("Transfer failed: " + e.getMessage());
        } catch (MinimumBalanceViolationException e) {
            System.out.println("Transfer failed (min balance): " + e.getMessage());
        }

        // 5. Transfer to a non-existent account — Yash has no current account (no
        // "CUR002" exists)
        try {
            bankService.transfer("SAV002", "CUR002", 20000.0);
        } catch (AccountNotFoundException | InsufficientFundsException e) {
            System.out.println("Transfer failed: " + e.getMessage());
        } catch (MinimumBalanceViolationException e) {
            System.out.println("Transfer failed (min balance): " + e.getMessage());
        }

        accountRepo.findById("SAV002").ifPresent(
                acc -> System.out.println("Yash's SAV002 balance after 'failed' transfer: " + acc.getBalance()));

    }
}
