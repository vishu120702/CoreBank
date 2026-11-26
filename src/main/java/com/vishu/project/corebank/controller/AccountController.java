package com.vishu.project.corebank.controller;

import com.vishu.project.corebank.dto.CreateAccountRequest;
import com.vishu.project.corebank.dto.AmountRequest;
import com.vishu.project.corebank.exception.AccountNotFoundException;
import com.vishu.project.corebank.exception.CustomerNotFoundException;
import com.vishu.project.corebank.exception.InsufficientFundsException;
import com.vishu.project.corebank.exception.MinimumBalanceViolationException;
import com.vishu.project.corebank.model.Account;
import com.vishu.project.corebank.service.BankService;
import com.vishu.project.corebank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/accounts")
@RestController
public class AccountController {
    @Autowired
    BankService bankService;

    @Autowired
    AccountService accountService;

    @GetMapping
    public List<Account> getAll(){
        return accountService.getAllAccounts();
    }

    @PostMapping
    public ResponseEntity<Account> create(@RequestBody CreateAccountRequest request)
            throws CustomerNotFoundException {
        return ResponseEntity.ok(accountService.createAccount(
                request.getAccountNumer(), request.getCustomerId(),
                request.getType(), request.getInitialBalance()));
    }

    @GetMapping("/{accountNumer}")
    public Account getOne(@PathVariable String accountNumer) throws AccountNotFoundException {
        return accountService.getAccount(accountNumer);
    }

    @PostMapping("/{accountNumer}/deposit")
    public ResponseEntity<String> deposit(@PathVariable String accountNumer,
                                          @RequestBody AmountRequest request)
            throws AccountNotFoundException {
        bankService.deposit(accountNumer, request.getAmount());
        return ResponseEntity.ok("Deposit successful");
    }

    @PostMapping("/{accountNumer}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable String accountNumer,
                                           @RequestBody AmountRequest request)
            throws AccountNotFoundException, InsufficientFundsException, MinimumBalanceViolationException {
        bankService.withdraw(accountNumer, request.getAmount());
        return ResponseEntity.ok("Withdraw successful");
    }
}
