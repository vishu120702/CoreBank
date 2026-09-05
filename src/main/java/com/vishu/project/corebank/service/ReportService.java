package com.vishu.project.corebank.service;

import java.util.Comparator;
// import java.util.HashMap;
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

//    public double getTotalBankBalance() {
//        return accountRepository.findAll().stream().mapToDouble(Account -> Account.getBalance()).sum();
//    }
//
//    public List<Transaction> getHighValueTransactions(double threshold) {
//        return bankService.getLedger().stream().filter(Transaction -> Transaction.getAmount() > threshold).toList();
//    }
//
//    public Map<TransactionType, List<Transaction>> getTransactionsByType() {
//        // List<Transaction> depositeList=
//        // bankService.getLedger().stream().filter(Transaction -> Transaction.getType()
//        // == TransactionType.DEPOSIT).toList();
//        // List<Transaction> withdrawList=
//        // bankService.getLedger().stream().filter(Transaction -> Transaction.getType()
//        // == TransactionType.WITHDRAW).toList();
//        // Map<TransactionType, List<Transaction>> resultMap = new
//        // HashMap<TransactionType,List<Transaction>>();
//        // resultMap.put(TransactionType.DEPOSIT, depositeList);
//        // resultMap.put(TransactionType.WITHDRAW, withdrawList);
//        // return resultMap;
//
//        return bankService.getLedger().stream().collect(Collectors.groupingBy(Transaction::getType)); // Collectors.groupingBy
//                                                                                                      // exists to
//                                                                                                      // replace above
//                                                                                                      // exact manual
//                                                                                                      // version
//
//    }
//
//    public Optional<Account> getHighestBalanceAccount() {
//        // accountRepository.findAll().stream().max((a1,a2) ->
//        // Double.compare(a1.getBalance(), a2.getBalance()));
//        // accountRepository.findAll().stream().max(Comparator.comparingDouble(Account
//        // -> Account.getBalance()));
//        return accountRepository.findAll().stream().max(Comparator.comparingDouble(Account::getBalance));
//
//    }
}
