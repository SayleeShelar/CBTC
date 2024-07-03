
# Virtual Internship Projects - Cipherbyte Technology

## Overview
This repository contains the projects developed during my virtual internship as a Java Developer at Cipherbyte Technology. The projects include a Number Guessing Game and a Banking System. Below are the details of each project.

---

## Project 1: Number Guessing Game

### Description
The Number Guessing Game is a simple console-based game where the player is required to guess a randomly generated number within a specified range and a limited number of attempts. The game consists of three rounds, and the player’s score is based on the number of attempts taken to guess the number correctly.

### Features
- Random number generation within a specified range.
- Limited number of attempts to guess the number.
- Feedback on whether the guessed number is higher or lower than the target number.
- Scoring system based on the number of attempts taken to guess the number.

### How to Run
1. Compile the `GuessGame.java` file using a Java compiler.
2. Run the compiled class file.
3. Follow the on-screen instructions to play the game.

### Code
```java
import java.util.Random;
import java.util.Scanner;

public class GuessGame {
    private static final int MIN_RANGE = 1;
    private static final int MAX_RANGE = 100;
    private static final int MAX_ATTEMPTS = 10;
    private static final int MAX_ROUNDS = 3;

    public static void main(String[] args) {
        Random random = new Random();
        int totalScore = 0;

        System.out.println("Number Guessing Game");
        System.out.println("Total Number of Rounds: 3");
        System.out.println("Attempts To Guess Number In Each Round: 10\n");

        try (Scanner scanner = new Scanner(System.in)) {
            for (int i = 1; i <= MAX_ROUNDS; i++) {
                int number = random.nextInt(MAX_RANGE - MIN_RANGE + 1) + MIN_RANGE;
                int attempts = 0;

                System.out.printf("Round %d: Guess the number between %d and %d in %d attempts.\n", i, MIN_RANGE, MAX_RANGE, MAX_ATTEMPTS);
                while (attempts < MAX_ATTEMPTS) {
                    System.out.print("Enter your guess: ");

                    String input = scanner.nextLine();
                    try {
                        int guessNumber = Integer.parseInt(input.trim());

                        if (guessNumber < MIN_RANGE || guessNumber > MAX_RANGE) {
                            System.out.printf("Your guess should be between %d and %d.\n", MIN_RANGE, MAX_RANGE);
                            continue;
                        }

                        attempts++;

                        if (guessNumber == number) {
                            int score = MAX_ATTEMPTS - attempts + 1;
                            totalScore += score;
                            System.out.printf("You Guessed Right. Attempts = %d. Round Score = %d\n", attempts, score);
                            break;
                        } else if (guessNumber < number) {
                            System.out.printf("The number is greater than %d. Attempts Left = %d.\n", guessNumber, MAX_ATTEMPTS - attempts);
                        } else {
                            System.out.printf("The number is less than %d. Attempts Left = %d\n", guessNumber, MAX_ATTEMPTS - attempts);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                    }
                }

                if (attempts == MAX_ATTEMPTS) {
                    System.out.printf("Round %d: You did not guess the number. The correct number was %d.\n\n", i, number);
                } else {
                    System.out.printf("End of Round %d\n\n", i);
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        System.out.printf("Game Over. Total Score = %d\n", totalScore);
    }
}
```

---

## Project 2: Banking System

### Description
The Banking System is a console-based application that simulates basic banking operations such as account creation, deposit, withdrawal, transfer, and account management. The accounts are read from a CSV file at startup, and the application provides functionalities to interact with these accounts.

### Features
- Reading account details from a CSV file.
- Listing all accounts with their details.
- Depositing and withdrawing money from an account.
- Transferring money between accounts.
- Updating account holder names.
- Closing accounts.

### How to Run
1. Ensure you have a CSV file named `accounts.csv` with the following format:
    ```
    accountNumber,accountHolderName,balance
    1001,John Doe,5000.00
    1002,Jane Smith,3500.50
    ```
2. Compile the `Account.java` and `BankingSystem.java` files using a Java compiler.
3. Run the compiled `BankingSystem` class.
4. Follow the on-screen instructions to perform various banking operations.

### Code
```java
// Account.java
public class Account {
    private final String accountNumber;
    private String accountHolderName;
    private double balance;

    public Account(String accountNumber, String accountHolderName, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: " + amount);
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount);
            return true;
        } else {
            System.out.println("Insufficient balance");
            return false;
        }
    }

    public void transfer(Account recipient, double amount) {
        if (withdraw(amount)) {
            recipient.deposit(amount);
            System.out.println("Transferred " + amount + " to account " + recipient.getAccountNumber());
        } else {
            System.out.println("Transfer failed: Insufficient balance");
        }
    }

    public void checkBalance() {
        System.out.println("Account Balance: " + balance);
    }
}
```

```java
// BankingSystem.java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BankingSystem {
    private final List<Account> accounts;

    public BankingSystem() {
        this.accounts = new ArrayList<>();
    }

    public void readAccountsFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] parts = line.split(",");
                String accountNumber = parts[0].trim();
                String accountHolderName = parts[1].trim();
                double balance;
                try {
                    balance = Double.parseDouble(parts[2].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Skipping line due to invalid balance format: " + line);
                    continue;
                }
                Account account = new Account(accountNumber, accountHolderName, balance);
                accounts.add(account);
            }
        }
    }

    public void listAllAccounts() {
        for (Account account : accounts) {
            System.out.println(account.getAccountNumber() + " | " + account.getAccountHolderName() + " | " + account.getBalance());
        }
    }

    public void closeAccount(String accountNumber) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            accounts.remove(account);
            System.out.println("Closed account " + accountNumber);
        } else {
            System.out.println("Account not found");
        }
    }

    public void updateAccount(String accountNumber, String newAccountHolderName) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            account.setAccountHolderName(newAccountHolderName);
            System.out.println("Updated account holder name for account " + accountNumber);
        } else {
            System.out.println("Account not found");
        }
    }

    public void deposit(String accountNumber, double amount) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            account.deposit(amount);
        } else {
            System.out.println("Account not found");
        }
    }

    public void withdraw(String accountNumber, double amount) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            account.withdraw(amount);
        } else {
            System.out.println("Account not found or insufficient balance");
        }
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        Account fromAccount = findAccount(fromAccountNumber);
        Account toAccount = findAccount(toAccountNumber);
        if (fromAccount != null && toAccount != null) {
            fromAccount.transfer(toAccount, amount);
        } else {
            System.out.println("Account not found");
        }
    }

    private Account findAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
