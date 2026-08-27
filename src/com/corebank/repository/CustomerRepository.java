package com.corebank.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.corebank.model.Customer;

public class CustomerRepository implements Repository<Customer, Integer> {

    private Map<Integer, Customer> customers = new HashMap<>();

    @Override
    public Customer save(Integer customerId, Customer customer) {
        customers.put(customerId, customer);
        return customer;
    }

    @Override
    public Optional<Customer> findById(Integer customerId) {
        return Optional.ofNullable(customers.get(customerId));
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public void deleteById(Integer customerId) {
        customers.remove(customerId);
    }
    
}
