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
            boolean firstLine = true; // Skip the header line
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
        }
        return null;
    }

    public static void main(String[] args) {
        BankingSystem bankingSystem = new BankingSystem();
        try {
            bankingSystem.readAccountsFromFile("accounts.csv");
        } catch (IOException e) {
            System.out.println("Error reading accounts file: " + e.getMessage());
            return;
        }

        bankingSystem.listAllAccounts();

        // Example operations
        bankingSystem.deposit("1001", 500.0);
        bankingSystem.withdraw("1002", 200.0);
        bankingSystem.transfer("1001", "1002", 100.0);

        bankingSystem.listAllAccounts();

        bankingSystem.closeAccount("1001");
        bankingSystem.updateAccount("1002", "Jane Doe");

        bankingSystem.listAllAccounts();
    }
}
