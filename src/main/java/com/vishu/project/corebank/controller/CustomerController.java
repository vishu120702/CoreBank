package com.vishu.project.corebank.controller;

import com.vishu.project.corebank.dto.CreateCustomerRequest;
import com.vishu.project.corebank.model.Customer;
import com.vishu.project.corebank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody CreateCustomerRequest request) {
        return ResponseEntity.ok(customerService.createCustomer(
                request.getCustomerId(), request.getName(), request.getEmail()));
    }

    @GetMapping
    public List<Customer> getAll() {
        return customerService.getAllCustomers();
    }
}
