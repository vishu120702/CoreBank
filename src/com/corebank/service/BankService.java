package com.corebank.service;

import java.util.UUID;

import com.corebank.exception.AccountNotFoundException;
import com.corebank.exception.InsufficientFundsException;
import com.corebank.exception.MinimumBalanceViolationException;
import com.corebank.model.Account;
import com.corebank.model.Transaction;
import com.corebank.model.TransactionType;
import com.corebank.repository.AccountRepository;
import com.corebank.util.TransactionLogger;

public class BankService {

    // his is just plain dependency injection, nothing framework-related yet (Spring
    // Boot will later automate this exact wiring for you, which is part of why it's
    // worth doing manually now).
    private AccountRepository accountRepository;

    public BankService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void deposit(String accountNumber, double amount) throws AccountNotFoundException {
        // AccountRepository accountRepository = new AccountRepository();
        // if (!accountRepository.findById(accountNumber).isPresent())
        // throw new AccountNotFoundException(accountNumber);
        // else{
        // accountRepository.findById(accountNumber).
        // }
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));
        account.setBalance(account.getBalance() + amount);

        // UUID is a built-in Java class (java.util.UUID) that produces a random,
        // effectively-unique 128-bit identifier, formatted as a string like
        // "a1b2c3d4-e5f6-..."
        Transaction txn = new Transaction(UUID.randomUUID().toString(), accountNumber, TransactionType.DEPOSIT, amount);

        TransactionLogger.logTransaction(txn);
    }

    public void withdraw(String accountNumber, double amount)
            throws AccountNotFoundException, InsufficientFundsException, MinimumBalanceViolationException {
        // Look up the account; throw if not found
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));

        // Check balance < amount → throw InsufficientFundsException
        if (account.getBalance() < amount)
            throw new InsufficientFundsException(accountNumber, account.getBalance(), amount);
        // Check balance - amount < account.getMinimumBalance() → throw
        // MinimumBalanceViolationException
        else if (account.getBalance() - amount < account.getMinimumBalance())
            throw new MinimumBalanceViolationException(accountNumber, account.getMinimumBalance());
        // Otherwise deduct, log a WITHDRAWAL transaction
        else {
            account.setBalance(account.getBalance() - amount);
            Transaction txn = new Transaction(UUID.randomUUID().toString(), accountNumber, TransactionType.WITHDRAW,
                    amount);
            TransactionLogger.logTransaction(txn);
        }

    }

    public void transfer(String fromAccount, String toAccount, double amount)
            throws AccountNotFoundException, InsufficientFundsException, MinimumBalanceViolationException {
        // the withdrawal already happened before the deposit failed. That's the bug
        // made visible. This is a genuinely good thing to have witnessed firsthand,
        // because it's precisely the problem @Transactional in Spring exists to solve
        // later — a transfer needs to be all-or-nothing

        // withdraw(fromAccount, amount);
        // deposit(toAccount, amount);

        withdraw(fromAccount, amount);
        try {
            deposit(toAccount, amount);
        } catch (AccountNotFoundException e) {
            deposit(fromAccount, amount);   //a transfer needs to be all-or-nothing
            throw e;
        }

    }
}
