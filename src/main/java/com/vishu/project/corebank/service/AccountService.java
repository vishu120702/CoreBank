package com.vishu.project.corebank.service;

import com.vishu.project.corebank.exception.AccountNotFoundException;
import com.vishu.project.corebank.exception.CustomerNotFoundException;
import com.vishu.project.corebank.model.Account;
import com.vishu.project.corebank.model.AccountType;
import com.vishu.project.corebank.model.CurrentAccount;
import com.vishu.project.corebank.model.Customer;
import com.vishu.project.corebank.model.SavingsAccount;
import com.vishu.project.corebank.repository.AccountRepository;
import com.vishu.project.corebank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccount(String accountNumer) throws AccountNotFoundException {
        return accountRepository.findById(accountNumer)
                .orElseThrow(() -> new AccountNotFoundException(accountNumer));
    }

    public Account createAccount(String accountNumer, String customerId,
                                 AccountType type, double initialBalance)
            throws CustomerNotFoundException {

        Customer owner = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        Account account = (type == AccountType.SAVINGS)
                ? new SavingsAccount(accountNumer, initialBalance, owner)
                : new CurrentAccount(accountNumer, initialBalance, owner);

        return accountRepository.save(account);
    }
}
