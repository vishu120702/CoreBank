package com.vishu.project.corebank.repository;

import com.vishu.project.corebank.model.Transaction;
import com.vishu.project.corebank.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    // Spring Data parses this method NAME and writes the SQL for you —
    // same idea as findByOwner_CustomerId from earlier, just a different field/operator
    List<Transaction> findByAmountGreaterThan(double amount);

    List<Transaction> findByType(TransactionType type);

    List<Transaction> findByAccountNumber(String accountNumber);
}
