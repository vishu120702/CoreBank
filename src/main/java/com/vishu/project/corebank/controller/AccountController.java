package com.vishu.project.corebank.controller;

import com.vishu.project.corebank.dto.CreateAccountRequest;
import com.vishu.project.corebank.dto.AmountRequest;
import com.vishu.project.corebank.exception.AccountNotFoundException;
import com.vishu.project.corebank.exception.CustomerNotFoundException;
import com.vishu.project.corebank.exception.InsufficientFundsException;
import com.vishu.project.corebank.exception.MinimumBalanceViolationException;
import com.vishu.project.corebank.model.*;
import com.vishu.project.corebank.repository.AccountRepository;
import com.vishu.project.corebank.repository.CustomerRepository;
import com.vishu.project.corebank.service.BankService;
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
    AccountRepository accountRepository;

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping
    public List<Account> getAll(){
        return accountRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Account> create(@RequestBody CreateAccountRequest request) throws CustomerNotFoundException {

//        Account account;
//        if(request.getType() == AccountType.SAVINGS){
//            account = new SavingsAccount(request.getAccountNumer(), request.getInitialBalance(), owner);
//        }
//        else{
//            account = new CurrentAccount(request.getAccountNumer(), request.getInitialBalance(), owner);
//        }

        Customer owner = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException(request.getCustomerId()));

        Account account = (request.getType() == AccountType.SAVINGS)
                ? new SavingsAccount(request.getAccountNumer(), request.getInitialBalance(), owner)
                : new CurrentAccount(request.getAccountNumer(), request.getInitialBalance(), owner);

        return ResponseEntity.ok(accountRepository.save(account));
    }

    @GetMapping("/{accountNumer}")
    public Account getOne(@PathVariable String accountNumer) throws AccountNotFoundException {
        return accountRepository.findById(accountNumer)
                .orElseThrow(() -> new AccountNotFoundException(accountNumer));
    }

    @PostMapping("/{accountNumer}/deposit")
    public ResponseEntity<String> deposit(@PathVariable String accountNumer, @RequestBody AmountRequest request)
            throws AccountNotFoundException {
        bankService.deposit(accountNumer, request.getAmount());
        return ResponseEntity.ok("Deposit successful");
    }

    @PostMapping("/{accountNumer}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable String accountNumer, @RequestBody AmountRequest request)
            throws AccountNotFoundException, InsufficientFundsException, MinimumBalanceViolationException {
        bankService.withdraw(accountNumer, request.getAmount());
        return ResponseEntity.ok("Withdraw successful");
    }

}
