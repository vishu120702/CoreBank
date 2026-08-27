package com.corebank.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.corebank.model.Account;

public class AccountRepository implements Repository<Account, String> {
    private Map<String, Account> accounts = new HashMap<>();

    @Override
    public Account save(String accountNumber, Account account) {
        accounts.put(accountNumber, account);
        return account;
    }

     @Override
    public Optional<Account> findById(String accountNumber) {
        return Optional.ofNullable(accounts.get(accountNumber));
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(accounts.values());
    }

    @Override
    public void deleteById(String accountNumber) {
        accounts.remove(accountNumber);
    }
}
