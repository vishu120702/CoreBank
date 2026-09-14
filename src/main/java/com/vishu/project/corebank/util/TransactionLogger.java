package com.vishu.project.corebank.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.vishu.project.corebank.model.Transaction;

public class TransactionLogger {
    // Open transactions.log in append mode, create a buffered writer around it,
    // write the transaction as text, move to the next line, and automatically close
    // the file when finished.

    private static final String LOG_FILE = "transaction.log";

    public static void logTransaction(Transaction txn) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(txn.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Failed to log transaction: " + e.getMessage());
        }
    }
}