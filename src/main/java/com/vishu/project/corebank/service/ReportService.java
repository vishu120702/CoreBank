package com.vishu.project.corebank.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.vishu.project.corebank.model.Account;
import com.vishu.project.corebank.model.Transaction;
import com.vishu.project.corebank.model.TransactionType;
import com.vishu.project.corebank.repository.AccountRepository;
import com.vishu.project.corebank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    BankService bankService;

    public double getTotalBankBalance(){
        return accountRepository.findAll().stream()
                .mapToDouble(Account::getBalance)
                .sum();
    };

    public List<Transaction> getHighValueTransactions(double threshold) {
        return transactionRepository.findByAmountGreaterThan(threshold);   // real SQL query now
    }

    public Map<TransactionType, List<Transaction>> getTransactionsByType() {
        return transactionRepository.findAll().stream()
                .collect(Collectors.groupingBy(Transaction::getType));   // same groupingBy you already know, now sourced from DB
    }

    public Optional<Account> getHighestBalanceAccount() {
        return accountRepository.findAll().stream()
                .max(Comparator.comparingDouble(Account::getBalance));
    }
}