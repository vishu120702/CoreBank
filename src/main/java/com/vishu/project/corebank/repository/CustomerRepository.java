package com.vishu.project.corebank.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.vishu.project.corebank.model.Customer;

public class CustomerRepository implements Repository<Customer, String> {

    private Map<String, Customer> customers = new HashMap<>();

    @Override
    public Customer save(String customerId, Customer customer) {
        customers.put(customerId, customer);
        return customer;
    }

    @Override
    public Optional<Customer> findById(String customerId) {
        return Optional.ofNullable(customers.get(customerId));
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public void deleteById(String customerId) {
        customers.remove(customerId);
    }

}
