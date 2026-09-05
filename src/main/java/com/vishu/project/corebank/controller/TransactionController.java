package com.vishu.project.corebank.controller;

import com.vishu.project.corebank.dto.TransferRequest;
import com.vishu.project.corebank.exception.AccountNotFoundException;
import com.vishu.project.corebank.exception.InsufficientFundsException;
import com.vishu.project.corebank.exception.MinimumBalanceViolationException;
import com.vishu.project.corebank.model.Account;
import com.vishu.project.corebank.model.Transaction;
import com.vishu.project.corebank.model.TransactionType;
import com.vishu.project.corebank.repository.TransactionRepository;
import com.vishu.project.corebank.service.BankService;
import com.vishu.project.corebank.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private BankService bankService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private TransactionRepository transactionRepository;

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequest request)
            throws AccountNotFoundException, InsufficientFundsException, MinimumBalanceViolationException {
        bankService.transfer(request.getFromAccount(), request.getToAccount(), request.getAmount());
        return ResponseEntity.ok("Transfer successful");
    }

    @GetMapping("/transactions")
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @GetMapping("/reports/total-balance")
    public double totalBalance() {
        return reportService.getTotalBankBalance();
    }

    @GetMapping("/reports/highest-balance")
    public Optional<Account> highestBalance() {
        return reportService.getHighestBalanceAccount();
    }

    @GetMapping("/reports/high-value")
    public List<Transaction> highValue(@RequestParam double threshold) {
        return reportService.getHighValueTransactions(threshold);
    }

    @GetMapping("/reports/by-type")
    public Map<TransactionType, List<Transaction>> byType() {
        return reportService.getTransactionsByType();
    }
}