package com.vishu.project.corebank.controller;

import com.vishu.project.corebank.dto.SimulateWithdrawalsRequest;
import com.vishu.project.corebank.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/simulate")
public class ConcurrencyController {

    @Autowired
    private SimulationService simulationService;

    @PostMapping("/withdrawals")
    public ResponseEntity<String> simulateWithdrawals(@RequestBody SimulateWithdrawalsRequest request)
            throws InterruptedException {

        boolean finishedInTime = simulationService.simulateWithdrawals(
                request.getAccountNumber(), request.getAmount(), request.getNumberOfThreads());

        if (!finishedInTime) {
            return ResponseEntity.status(503)
                    .body("Some withdrawal threads did not finish within 10 seconds");
        }
        return ResponseEntity.ok(request.getNumberOfThreads() +
                " concurrent withdrawal attempts completed. Check your IDE's Run console for per-thread results");
    }
}
