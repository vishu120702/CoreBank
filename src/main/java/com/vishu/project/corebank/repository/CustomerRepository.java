package com.vishu.project.corebank.repository;

import com.vishu.project.corebank.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {}