package com.corebank;

import java.util.ArrayList;
// import java.util.Comparator;
import java.util.List;

import com.corebank.concurrency.WithdrawalSimulator;
import com.corebank.exception.AccountNotFoundException;
import com.corebank.exception.InsufficientFundsException;
import com.corebank.exception.MinimumBalanceViolationException;
import com.corebank.model.CurrentAccount;
import com.corebank.model.Customer;
import com.corebank.model.SavingsAccount;
import com.corebank.model.Transaction;
import com.corebank.repository.AccountRepository;
import com.corebank.repository.CustomerRepository;
import com.corebank.service.BankService;
import com.corebank.service.ReportService;

public class Main {
    public static void main(String[] args) {
        CustomerRepository customerRepository = new CustomerRepository();
        AccountRepository accountRepository = new AccountRepository();

        Customer c1 = new Customer("CUST101", "Vishwambhar", "vishutambekar@gmail.com");
        Customer c2 = new Customer("CUST102", "Yash", "yashchougule@gmail.com");

        customerRepository.save(c1.getCustomerId(), c1);
        customerRepository.save(c2.getCustomerId(), c2);
        // System.out.println(customerRepository.save(c1.getCustomerId(), c1));

        // System.out.println(customerRepository.findById("CUST101"));

        SavingsAccount saving = new SavingsAccount("SAV001", 59_000.0, c1);
        CurrentAccount current = new CurrentAccount("CUR001", 1_29_000, c1);
        accountRepository.save(saving.getAccountNumer(), saving);
        accountRepository.save(current.getAccountNumer(), current);
        SavingsAccount saving1 = new SavingsAccount("SAV002", 1_26_000, c2);
        accountRepository.save(saving1.getAccountNumer(), saving1);

        // Quick sanity check that everything wired correctly
        // accountRepository.findById(saving1.getAccountNumer())
        //         .ifPresent(account -> System.out
        //                 .println(account.getOwner().getName() + "'s savings balance: " + account.getBalance()
        //                         + " at " + account.getInterestRate() + "% interest"));

        // System.out.println(saving);
        // accountRepository.findById("CUR01").ifPresent( ac ->
        // System.out.println(ac.getOwner().getName()));
        // System.out.println(accountRepository.findAll().size());
        // System.out.println(customerRepository.findAll().size());

        BankService bankService = new BankService(accountRepository);

        // accountRepository.findById(saving1.getAccountNumer())
        // .ifPresent(acc -> System.out.println(acc.getOwner().getName() + "'s balance
        // befor deposit: " + acc.getBalance()));

        // try {
        // bankService.deposit(saving1.getAccountNumer(), 72_000.0);
        // } catch (AccountNotFoundException e) {
        // e.printStackTrace();
        // }

        // accountRepository.findById(saving1.getAccountNumer())
        // .ifPresent(acc -> System.out.println(acc.getOwner().getName() + "'s balance
        // after deposit: " + acc.getBalance()));

        // 1. Normal successful withdrawal — SAV001: 59000 -> 54000
        try {
            bankService.withdraw("SAV001", 5000.0);
            System.out.println("Withdraw OK. SAV001 balance: " +
                    accountRepository.findById("SAV001").get().getBalance());
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
            System.out.println("CUR001 balance: " + accountRepository.findById("CUR001").get().getBalance());
            System.out.println("SAV001 balance: " + accountRepository.findById("SAV001").get().getBalance());
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

        accountRepository.findById("SAV002").ifPresent(
                acc -> System.out.println("Yash's SAV002 balance after 'failed' transfer: " + acc.getBalance()));


        System.out.println(saving.calculateInterest());  //for 64000 balance - 2560.0
        System.out.println(current.calculateInterest()); //for 119000 balance - 4165.0

        List<Transaction> copyOfLedger = new ArrayList<>(bankService.getLedger());
        copyOfLedger.sort((txn1,txn2) -> Double.compare(txn1.getAmount(), txn2.getAmount()));
        // copyOfLedger.sort(Comparator.comparingDouble(Transaction :: getAmount));  //same thing via method reference instead
        copyOfLedger.forEach(System.out :: println);  //Prints the copyOfLedger(sorted wrt amount of transaction)

        System.out.println();


        ReportService reportService = new ReportService(accountRepository, bankService);
        System.out.println(reportService.getTotalBankBalance());
        System.out.println(reportService.getHighestBalanceAccount());
        System.out.println(reportService.getHighValueTransactions(12000));
        System.out.println(reportService.getTransactionsByType());
        System.out.println();

        reportService.getHighestBalanceAccount().ifPresentOrElse(
            acc -> System.out.print("Highest Balance : "+ acc),
            () -> System.out.println("No account found")
        );

        System.out.println();
        System.out.println("...............");
        System.out.println("...............");

        // Phase 5: concurrency simulation
        // Give SAV001 a balance where not all withdrawals can succeed
        accountRepository.findById("SAV001").ifPresent(acc -> acc.setBalance(20000.0));
        
        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(new WithdrawalSimulator(bankService, "SAV001", 5000.0));
        }
        
        for (Thread t : threads) {
            t.start();
        }
        
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        System.out.println("Final SAV001 balance after concurrent withdrawals: " + accountRepository.findById("SAV001").get().getBalance());

    }
}
