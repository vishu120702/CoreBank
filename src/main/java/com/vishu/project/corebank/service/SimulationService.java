package com.vishu.project.corebank.service;

import com.vishu.project.corebank.concurrency.WithdrawalSimulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class SimulationService {

    @Autowired
    private BankService bankService;

    public boolean simulateWithdrawals(String accountNumber, double amount, int numberOfThreads)
            throws InterruptedException {

        try (ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads)) {
            for (int i = 0; i < numberOfThreads; i++) {
                executor.execute(new WithdrawalSimulator(bankService, accountNumber, amount));
            }
            executor.shutdown();
            return executor.awaitTermination(10, TimeUnit.SECONDS);
        }
    }
}
