package com.corebank;

import com.corebank.model.CurrentAccount;
import com.corebank.model.Customer;
import com.corebank.model.SavingsAccount;
import com.corebank.repository.AccountRepository;
import com.corebank.repository.CustomerRepository;

public class Main {
    public static void main(String[] args) {
        CustomerRepository customerRepo = new CustomerRepository();
        AccountRepository accountRepo = new AccountRepository();

        Customer c1 = new Customer(101, "Vishwambhar", "vishutambekar@gmail.com");
        Customer c2 = new Customer(101, "Yash", "yashchougule@gmail.com");

        customerRepo.save(c1.getCustomerId(), c1);
        customerRepo.save(c2.getCustomerId(), c2);
        // System.out.println(customerRepo.save(c1.getCustomerId(), c1));

        System.out.println(customerRepo.findById(101));

        SavingsAccount saving = new SavingsAccount("SAV01", 28_000.0, c1);
        CurrentAccount current = new CurrentAccount("CUR01", 1_00_000, c1);
        accountRepo.save(saving.getAccountNumer(), saving);
        accountRepo.save(current.getAccountNumer(), current);

        // System.out.println(saving);
        // accountRepo.findById("CUR01").ifPresent( ac -> System.out.println(ac.getOwner().getName()));
        System.out.println(accountRepo.findAll().size());
        System.out.println(customerRepo.findAll().size());

    }
}
