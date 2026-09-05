# CoreBank

A banking-domain REST API built with **Spring Boot**, **Spring Data JPA**, and **PostgreSQL** — simulating core banking operations such as account management, deposits, withdrawals, transfers, and transaction reporting.

This project started as a plain Java practice application (OOP, exceptions, collections, streams, multithreading) and was later converted into a layered, database-backed Spring Boot REST API.

---

## Tech Stack

- **Java 21**
- **Spring Boot 3.3.4**
- **Spring Web** — REST API layer
- **Spring Data JPA / Hibernate** — ORM and persistence
- **PostgreSQL** — relational database
- **Maven** — build and dependency management

---

## Features

- Customer management (create, list)
- Account management with **Savings** and **Current** account types (single-table inheritance)
- Deposit, withdraw, and transfer operations with business-rule validation
  - Minimum balance enforcement (savings accounts)
  - Insufficient funds checks
  - Automatic rollback on failed transfers
- Persistent transaction history (database-backed) + file-based audit log
- Reporting endpoints (total balance, highest-balance account, high-value transactions, transactions by type)
- Centralized exception handling with proper HTTP status codes
- Multithreading/concurrency demo endpoint — simulates concurrent withdrawals on the same account to verify thread-safety (`synchronized`)

---

## Project Structure

```
com.vishu.project.corebank/
├── CorebankApplication.java     # Spring Boot entry point
├── model/                       # JPA entities
│   ├── Account (abstract)       # SINGLE_TABLE inheritance, discriminator: account_type
│   ├── SavingsAccount / CurrentAccount
│   ├── Customer
│   ├── Transaction
│   └── AccountType / TransactionType (enums)
├── repository/                  # Spring Data JPA interfaces
│   ├── AccountRepository
│   ├── CustomerRepository
│   └── TransactionRepository
├── service/
│   ├── BankService               # deposit / withdraw / transfer business logic
│   └── ReportService              # DB-backed reporting queries
├── controller/                   # REST endpoints (mapping only, delegates to service layer)
│   ├── CustomerController
│   ├── AccountController
│   ├── TransactionController
│   └── ConcurrencyController      # concurrency/multithreading test endpoint
├── dto/                           # request/response objects
├── exception/                     # custom exceptions + GlobalExceptionHandler
├── concurrency/
│   └── WithdrawalSimulator         # Runnable used to simulate concurrent withdrawals
└── util/
    └── TransactionLogger           # file-based audit log
```

---

## Setup

### Prerequisites
- JDK 21
- Maven
- PostgreSQL running locally

### 1. Create the database
```sql
CREATE DATABASE corebank_db;
```

### 2. Configure `src/main/resources/application.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/corebank_db
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.port=8085
```

> **Note:** For local development this is fine as-is. Before deploying anywhere the database is publicly reachable, move the credentials to environment variables (e.g. `spring.datasource.password=${DB_PASSWORD}`) instead of committing them in plain text.

### 3. Run the application
```bash
mvn spring-boot:run
```

The app starts on `http://localhost:8085`.

---

## API Endpoints

### Customers
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/customers` | Create a customer |
| GET | `/api/customers` | List all customers |

### Accounts
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/accounts` | Create an account (SAVINGS or CURRENT) |
| GET | `/api/accounts` | List all accounts |
| GET | `/api/accounts/{accountNumer}` | Get a single account |
| POST | `/api/accounts/{accountNumer}/deposit` | Deposit an amount |
| POST | `/api/accounts/{accountNumer}/withdraw` | Withdraw an amount |

### Transactions & Reports
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/transfer` | Transfer between two accounts |
| GET | `/api/transactions` | List all transactions |
| GET | `/api/reports/total-balance` | Total balance across the bank |
| GET | `/api/reports/highest-balance` | Account with the highest balance |
| GET | `/api/reports/high-value?threshold=X` | Transactions above a given amount |
| GET | `/api/reports/by-type` | Transactions grouped by type |

### Concurrency Demo
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/simulate/withdrawals` | Fire N concurrent withdrawal threads at one account, to test `synchronized` thread-safety |

---

## Sample Requests

**Create a customer**
```json
POST /api/customers
{
  "customerId": "CUST101",
  "name": "Vishwambhar",
  "email": "vishwambhar@gmail.com"
}
```

**Create an account**
```json
POST /api/accounts
{
  "accountNumer": "SAV101",
  "customerId": "CUST101",
  "type": "SAVINGS",
  "initialBalance": 15000
}
```

**Deposit**
```json
POST /api/accounts/SAV101/deposit
{
  "amount": 5000
}
```

**Transfer**
```json
POST /api/transfer
{
  "fromAccount": "SAV101",
  "toAccount": "SAV103",
  "amount": 2000
}
```

**Simulate concurrent withdrawals**
```json
POST /api/simulate/withdrawals
{
  "accountNumber": "SAV103",
  "amount": 5000,
  "numberOfThreads": 5
}
```


---

## Author

**Vishwambhar Tambekar**
