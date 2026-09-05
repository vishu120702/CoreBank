package com.vishu.project.corebank.controller;

import com.vishu.project.corebank.concurrency.WithdrawalSimulator;
import com.vishu.project.corebank.dto.SimulateWithdrawalsRequest;
import com.vishu.project.corebank.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/simulate")
public class ConcurrencyController {

    @Autowired
    private BankService bankService;   // the ONE singleton instance — this is the whole point

    @PostMapping("/withdrawals")
    public ResponseEntity<String> simulateWithdrawals(@RequestBody SimulateWithdrawalsRequest request)
            throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(request.getNumberOfThreads());

        for (int i = 0; i < request.getNumberOfThreads(); i++) {
            executor.execute(new WithdrawalSimulator(
                    bankService, request.getAccountNumber(), request.getAmount()));
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        return ResponseEntity.ok(request.getNumberOfThreads() +
                " concurrent withdrawal attempts submitted — check your IDE's Run console for per-thread results");
    }
}