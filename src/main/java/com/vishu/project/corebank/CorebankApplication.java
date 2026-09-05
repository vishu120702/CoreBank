package com.vishu.project.corebank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CorebankApplication {
    public static void main(String[] args) {
        SpringApplication.run(CorebankApplication.class, args);
        System.out.println("Working Fine");
    }
}




















//package com.vishu.project.corebank;
//
//import java.util.ArrayList;
//// import java.util.Comparator;
//import java.util.List;
//
//import com.vishu.project.corebank.concurrency.WithdrawalSimulator;
//import com.vishu.project.corebank.exception.AccountNotFoundException;
//import com.vishu.project.corebank.exception.InsufficientFundsException;
//import com.vishu.project.corebank.exception.MinimumBalanceViolationException;
//import com.vishu.project.corebank.model.*;
//import com.vishu.project.corebank.repository.AccountRepository;
//import com.vishu.project.corebank.repository.CustomerRepository;
//import com.vishu.project.corebank.repository.JdbcAccountRepository;
//import com.vishu.project.corebank.repository.JdbcCustomerRepository;
//import com.vishu.project.corebank.service.BankService;
//import com.vishu.project.corebank.service.ReportService;
//
//public class Main {
//    public static void main(String[] args) {
////        CustomerRepository customerRepository = new CustomerRepository();
////        AccountRepository accountRepository = new AccountRepository();
////
////        Customer c1 = new Customer("CUST101", "Vishwambhar", "vishutambekar@gmail.com");
////        Customer c2 = new Customer("CUST102", "Yash", "yashchougule@gmail.com");
////
////        customerRepository.save(c1.getCustomerId(), c1);
////        customerRepository.save(c2.getCustomerId(), c2);
////        // System.out.println(customerRepository.save(c1.getCustomerId(), c1));
////
////        // System.out.println(customerRepository.findById("CUST101"));
////
////        SavingsAccount saving = new SavingsAccount("SAV001", 59_000.0, c1);
////        CurrentAccount current = new CurrentAccount("CUR001", 1_29_000, c1);
////        accountRepository.save(saving.getAccountNumer(), saving);
////        accountRepository.save(current.getAccountNumer(), current);
////        SavingsAccount saving1 = new SavingsAccount("SAV002", 1_26_000, c2);
////        accountRepository.save(saving1.getAccountNumer(), saving1);
////
////        // Quick sanity check that everything wired correctly
////        // accountRepository.findById(saving1.getAccountNumer())
////        // .ifPresent(account -> System.out
////        // .println(account.getOwner().getName() + "'s savings balance: " +
////        // account.getBalance()
////        // + " at " + account.getInterestRate() + "% interest"));
////
////        // System.out.println(saving);
////        // accountRepository.findById("CUR01").ifPresent( ac ->
////        // System.out.println(ac.getOwner().getName()));
////        // System.out.println(accountRepository.findAll().size());
////        // System.out.println(customerRepository.findAll().size());
////
////        BankService bankService = new BankService(accountRepository);
////
////        // accountRepository.findById(saving1.getAccountNumer())
////        // .ifPresent(acc -> System.out.println(acc.getOwner().getName() + "'s balance
////        // befor deposit: " + acc.getBalance()));
////
////        // try {
////        // bankService.deposit(saving1.getAccountNumer(), 72_000.0);
////        // } catch (AccountNotFoundException e) {
////        // e.printStackTrace();
////        // }
////
////        // accountRepository.findById(saving1.getAccountNumer())
////        // .ifPresent(acc -> System.out.println(acc.getOwner().getName() + "'s balance
////        // after deposit: " + acc.getBalance()));
////
////        // 1. Normal successful withdrawal — SAV001: 59000 -> 54000
////        try {
////            bankService.withdraw("SAV001", 5000.0);
////            System.out.println("Withdraw OK. SAV001 balance: " +
////                    accountRepository.findById("SAV001").get().getBalance());
////        } catch (AccountNotFoundException | InsufficientFundsException e) {
////            System.out.println("Withdraw failed: " + e.getMessage());
////        } catch (MinimumBalanceViolationException e) {
////            System.out.println("Withdraw failed (min balance): " + e.getMessage());
////        }
////
////        // 2. Withdraw more than the balance -> InsufficientFundsException (SAV002 has
////        // 126000)
////        try {
////            bankService.withdraw("SAV002", 500000.0);
////        } catch (AccountNotFoundException | InsufficientFundsException e) {
////            System.out.println("Withdraw failed: " + e.getMessage());
////        } catch (MinimumBalanceViolationException e) {
////            System.out.println("Withdraw failed (min balance): " + e.getMessage());
////        }
////
////        // 3. Withdraw an amount that would breach minimum balance (leaves 500, below
////        // 1000 minimum)
////        try {
////            bankService.withdraw("SAV002", 125500.0);
////        } catch (AccountNotFoundException | InsufficientFundsException e) {
////            System.out.println("Withdraw failed: " + e.getMessage());
////        } catch (MinimumBalanceViolationException e) {
////            System.out.println("Withdraw failed (min balance): " + e.getMessage());
////        }
////
////        // 4. Successful transfer: Vishwambhar's current -> Vishwambhar's savings
////        try {
////            bankService.transfer("CUR001", "SAV001", 10000.0);
////            System.out.println("Transfer successful.");
////            System.out.println("CUR001 balance: " + accountRepository.findById("CUR001").get().getBalance());
////            System.out.println("SAV001 balance: " + accountRepository.findById("SAV001").get().getBalance());
////        } catch (AccountNotFoundException | InsufficientFundsException e) {
////            System.out.println("Transfer failed: " + e.getMessage());
////        } catch (MinimumBalanceViolationException e) {
////            System.out.println("Transfer failed (min balance): " + e.getMessage());
////        }
////
////        // 5. Transfer to a non-existent account — Yash has no current account (no
////        // "CUR002" exists)
////        try {
////            bankService.transfer("SAV002", "CUR002", 20000.0);
////        } catch (AccountNotFoundException | InsufficientFundsException e) {
////            System.out.println("Transfer failed: " + e.getMessage());
////        } catch (MinimumBalanceViolationException e) {
////            System.out.println("Transfer failed (min balance): " + e.getMessage());
////        }
////
////        accountRepository.findById("SAV002").ifPresent(
////                acc -> System.out.println("Yash's SAV002 balance after 'failed' transfer: " + acc.getBalance()));
////
////        System.out.println(saving.calculateInterest()); // for 64000 balance - 2560.0
////        System.out.println(current.calculateInterest()); // for 119000 balance - 4165.0
////
////        List<Transaction> copyOfLedger = new ArrayList<>(bankService.getLedger());
////        copyOfLedger.sort((txn1, txn2) -> Double.compare(txn1.getAmount(), txn2.getAmount()));
////        // copyOfLedger.sort(Comparator.comparingDouble(Transaction :: getAmount));
////        // //same thing via method reference instead
////        copyOfLedger.forEach(System.out::println); // Prints the copyOfLedger(sorted wrt amount of transaction)
////
////        System.out.println();
////
////        ReportService reportService = new ReportService(accountRepository, bankService);
////        System.out.println(reportService.getTotalBankBalance());
////        System.out.println(reportService.getHighestBalanceAccount());
////        System.out.println(reportService.getHighValueTransactions(12000));
////        System.out.println(reportService.getTransactionsByType());
////        System.out.println();
////
////        reportService.getHighestBalanceAccount().ifPresentOrElse(
////                acc -> System.out.print("Highest Balance : " + acc),
////                () -> System.out.println("No account found"));
////
////        System.out.println();
////        System.out.println("...............");
////        System.out.println("...............");
////
////        // Phase 5: concurrency simulation
////        // Give SAV001 a balance where not all withdrawals can succeed
////        accountRepository.findById("SAV001").ifPresent(acc -> acc.setBalance(20000.0));
////
////        Thread[] threads = new Thread[5];
////        for (int i = 0; i < 5; i++) {
////            threads[i] = new Thread(new WithdrawalSimulator(bankService, "SAV001", 5000.0));
////        }
////
////        for (Thread t : threads) {
////            t.start();
////        }
////
////        for (Thread t : threads) {
////            try {
////                t.join();
////            } catch (InterruptedException e) {
////                // TODO Auto-generated catch block
////                e.printStackTrace();
////            }
////        }
////
////        System.out.println("Final SAV001 balance after concurrent withdrawals: "
////                + accountRepository.findById("SAV001").get().getBalance());
////
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////
////
////        JdbcAccountRepository accountRepo = new JdbcAccountRepository();
////
////        // Seed accounts — customers CUST101/CUST102 already exist in the DB via SQL.
////        // Using save() here also resets balances to a known starting point on every
////        // run, so this test is repeatable regardless of what previous runs did.
////        Customer c1 = new Customer("CUST101", "Vishwambhar", "vishutambekar@gmail.com");
////        Customer c2 = new Customer("CUST102", "Yash", "yashchougule@gmail.com");
////
////        accountRepo.save("SAV001", new SavingsAccount("SAV001", 59000.0, c1));
////        accountRepo.save("CUR001", new CurrentAccount("CUR001", 129000.0, c1));
////        accountRepo.save("SAV002", new SavingsAccount("SAV002", 126000.0, c2));
////
////        BankService bankService = new BankService(accountRepo);
////
////        // One deposit
////        try {
////            bankService.deposit("SAV001", 5000.0);
////            System.out.println("Deposit OK.");
////        } catch (AccountNotFoundException e) {
////            System.out.println("Deposit failed: " + e.getMessage());
////        }
////
////        // One withdrawal
////        try {
////            bankService.withdraw("CUR001", 20000.0);
////            System.out.println("Withdraw OK.");
////        } catch (AccountNotFoundException | InsufficientFundsException | MinimumBalanceViolationException e) {
////            System.out.println("Withdraw failed: " + e.getMessage());
////        }
////
////        // One transfer
////        try {
////            bankService.transfer("SAV002", "SAV001", 10000.0);
////            System.out.println("Transfer OK.");
////        } catch (AccountNotFoundException | InsufficientFundsException | MinimumBalanceViolationException e) {
////            System.out.println("Transfer failed: " + e.getMessage());
////        }
////
////        // Re-fetch from the DB — this is the real proof, not the in-memory object
////        System.out.println("---- Final balances (read back from Postgres) ----");
////        accountRepo.findById("SAV001").ifPresent(a -> System.out.println("SAV001: " + a.getBalance()));
////        accountRepo.findById("CUR001").ifPresent(a -> System.out.println("CUR001: " + a.getBalance()));
////        accountRepo.findById("SAV002").ifPresent(a -> System.out.println("SAV002: " + a.getBalance()));
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        JdbcCustomerRepository customerRepo = new JdbcCustomerRepository();
//        JdbcAccountRepository accountRepo = new JdbcAccountRepository();
//
//        // --------------------------------------------------
//        // 1. Create customers
//        // --------------------------------------------------
//
//        Customer c1 = new Customer(
//                "CUST101",
//                "Vishwambhar",
//                "vishutambekar@gmail.com"
//        );
//
//        Customer c2 = new Customer(
//                "CUST102",
//                "Yash",
//                "yashchougule@gmail.com"
//        );
//
//        customerRepo.save(c1.getCustomerId(), c1);
//        customerRepo.save(c2.getCustomerId(), c2);
//
//        System.out.println("Customers saved successfully.");
//
//        // --------------------------------------------------
//        // 2. Retrieve customers from database
//        // --------------------------------------------------
//
//        System.out.println("\n--- Customers ---");
//
//        customerRepo.findAll().forEach(customer ->
//                System.out.println(
//                        customer.getCustomerId() + " | " +
//                                customer.getName() + " | " +
//                                customer.getEmail()
//                )
//        );
//
//        // --------------------------------------------------
//        // 3. Create accounts
//        // --------------------------------------------------
//
//        Account savings = new SavingsAccount(
//                "SAV001",
//                15000.0,
//                c1
//        );
//
//        Account current = new CurrentAccount(
//                "CUR001",
//                8000.0,
//                c2
//        );
//
//        accountRepo.save(
//                savings.getAccountNumer(),
//                savings
//        );
//
//        accountRepo.save(
//                current.getAccountNumer(),
//                current
//        );
//
//        System.out.println("\nAccounts saved successfully.");
//
//        // --------------------------------------------------
//        // 4. Retrieve accounts from database
//        // --------------------------------------------------
//
//        System.out.println("\n--- Accounts ---");
//
//        accountRepo.findAll().forEach(account ->
//                System.out.println(
//                        account.getAccountNumer() + " | " +
//                                account.getOwner().getName() + " | " +
//                                account.getBalance()
//                )
//        );
//
//        // --------------------------------------------------
//        // 5. Find one customer
//        // --------------------------------------------------
//
//        System.out.println("\n--- Find Customer ---");
//
//        customerRepo.findById("CUST101").ifPresent(customer ->
//                System.out.println(
//                        "Found: " +
//                                customer.getName() +
//                                " | " +
//                                customer.getEmail()
//                )
//        );
//
//        // --------------------------------------------------
//        // 6. Find one account
//        // --------------------------------------------------
//
//        System.out.println("\n--- Find Account ---");
//
//        accountRepo.findById("SAV001").ifPresent(account ->
//                System.out.println(
//                        "Found: " +
//                                account.getAccountNumer() +
//                                " | Owner: " +
//                                account.getOwner().getName() +
//                                " | Balance: " +
//                                account.getBalance()
//                )
//        );
//
//
//        BankService bankService = new BankService();
//                // One deposit
//        try {
//            bankService.deposit("SAV001", 5000.0);
//            System.out.println("Deposit OK.");
//        } catch (AccountNotFoundException e) {
//            System.out.println("Deposit failed: " + e.getMessage());
//        }
//
//        // One withdrawal
//        try {
//            bankService.withdraw("CUR001", 20000.0);
//            System.out.println("Withdraw OK.");
//        } catch (AccountNotFoundException | InsufficientFundsException | MinimumBalanceViolationException e) {
//            System.out.println("Withdraw failed: " + e.getMessage());
//        }
//
//        // One transfer
//        try {
//            bankService.transfer("SAV002", "SAV001", 10000.0);
//            System.out.println("Transfer OK.");
//        } catch (AccountNotFoundException | InsufficientFundsException | MinimumBalanceViolationException e) {
//            System.out.println("Transfer failed: " + e.getMessage());
//        }
//
//        // Re-fetch from the DB — this is the real proof, not the in-memory object
//        System.out.println("---- Final balances (read back from Postgres) ----");
//        accountRepo.findById("SAV001").ifPresent(a -> System.out.println("SAV001: " + a.getBalance()));
//        accountRepo.findById("CUR001").ifPresent(a -> System.out.println("CUR001: " + a.getBalance()));
//        accountRepo.findById("SAV002").ifPresent(a -> System.out.println("SAV002: " + a.getBalance()));
//
//    }
//}
