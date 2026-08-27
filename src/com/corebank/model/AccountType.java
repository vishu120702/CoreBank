package com.corebank.model;

public enum AccountType {
    SAVINGS,
    CURRENT
}

// Then why would we keep AccountType?

// There are situations where it is useful.

// For example, suppose your application receives this:

// Create account:
// Account Type = SAVINGS
// Customer = Vishu
// Initial Balance = 5000

// You could use:

// AccountType type = AccountType.SAVINGS;

// Then your service could decide which class to create:

// if (type == AccountType.SAVINGS) {
//     return new SavingsAccount(...);
// }

// if (type == AccountType.CURRENT) {
//     return new CurrentAccount(...);
// }

// Conceptually:

//                 AccountType
//                     │
//           ┌─────────┴─────────┐
//           ↓                   ↓
//        SAVINGS             CURRENT
//           │                   │
//           ↓                   ↓
//  SavingsAccount        CurrentAccount

// This is useful when the type comes from user input, database data, API request, etc.