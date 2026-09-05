package com.vishu.project.corebank.controller;

import com.vishu.project.corebank.dto.CreateCustomerRequest;
import com.vishu.project.corebank.model.Customer;
import com.vishu.project.corebank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody CreateCustomerRequest request){
        Customer customer = new Customer(request.getCustomerId(), request.getName(), request.getEmail());
        return ResponseEntity.ok(customerRepository.save(customer));
    }

    @GetMapping
    public List<Customer> getAll(){
        return customerRepository.findAll();
    }
}
