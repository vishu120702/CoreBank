package com.vishu.project.corebank.service;

import java.util.UUID;

import com.vishu.project.corebank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vishu.project.corebank.exception.AccountNotFoundException;
import com.vishu.project.corebank.exception.InsufficientFundsException;
import com.vishu.project.corebank.exception.MinimumBalanceViolationException;
import com.vishu.project.corebank.model.Account;
import com.vishu.project.corebank.model.Transaction;
import com.vishu.project.corebank.model.TransactionType;
import com.vishu.project.corebank.repository.AccountRepository;
import com.vishu.project.corebank.util.TransactionLogger;

@Service
public class BankService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public void deposit(String accountNumber, double amount) throws AccountNotFoundException {
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);   // <-- was save(accountNumber, account)

        Transaction txn = new Transaction(UUID.randomUUID().toString(), accountNumber, TransactionType.DEPOSIT, amount);
        transactionRepository.save(txn);
        TransactionLogger.logTransaction(txn);
    }

    public synchronized void withdraw(String accountNumber, double amount)//synchronized allows
            throws AccountNotFoundException, InsufficientFundsException, MinimumBalanceViolationException {
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));

        if (account.getBalance() < amount)
            throw new InsufficientFundsException(accountNumber, account.getBalance(), amount);
        else if (account.getBalance() - amount < account.getMinimumBalance())
            throw new MinimumBalanceViolationException(accountNumber, account.getMinimumBalance());
        else {
            account.setBalance(account.getBalance() - amount);
            accountRepository.save(account);   // <-- was save(accountNumber, account)
            Transaction txn = new Transaction(UUID.randomUUID().toString(), accountNumber, TransactionType.WITHDRAW, amount);
            transactionRepository.save(txn);
            TransactionLogger.logTransaction(txn);
        }
    }

    public void transfer(String fromAccount, String toAccount, double amount)
            throws AccountNotFoundException, InsufficientFundsException, MinimumBalanceViolationException {
        withdraw(fromAccount, amount);
        try {
            deposit(toAccount, amount);
        } catch (AccountNotFoundException e) {
            deposit(fromAccount, amount);
            throw e;
        }
    }
}