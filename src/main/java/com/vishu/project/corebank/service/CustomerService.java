package com.vishu.project.corebank.service;

import com.vishu.project.corebank.model.Customer;
import com.vishu.project.corebank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(String customerId, String name, String email) {
        Customer customer = new Customer(customerId, name, email);
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
