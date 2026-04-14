import java.util.ArrayList;
import java.util.Scanner;

class InSufficientFundsException extends Exception {
    public InSufficientFundsException(String message) {
        super(message);
    }
}

class Account {
    private String accountHolder;
    private double balance;
    private ArrayList<Double> history = new ArrayList<>();

    public Account(String accountHolder, double balance) {
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid deposit amount");
        }
        balance += amount;
        addToHistory(amount);
        System.out.println("Deposited: " + amount);
    }

    public void processTransaction(double amount) throws InSufficientFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid withdrawal amount");
        }
        if (amount > balance) {
            throw new InSufficientFundsException("Insufficient balance");
        }
        balance -= amount;
        addToHistory(-amount);
        System.out.println("Withdrawn: " + amount);
    }

    private void addToHistory(double amount) {
        if (history.size() == 5) {
            history.remove(0);
        }
        history.add(amount);
    }

    public void printMiniStatement() {
        System.out.println("Last Transactions:");
        for (double t : history) {
            System.out.println(t);
        }
    }

    public double getBalance() {
        return balance;
    }
}

public class FinSafeApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Account acc = new Account("User", 1000);

        while (true) {
            System.out.println("\n1.Deposit  2.Withdraw  3.History  4.Exit");
            int choice = sc.nextInt();

            try {
                if (choice == 1) {
                    System.out.print("Enter amount: ");
                    double amt = sc.nextDouble();
                    acc.deposit(amt);

                } else if (choice == 2) {
                    System.out.print("Enter amount: ");
                    double amt = sc.nextDouble();
                    acc.processTransaction(amt);

                } else if (choice == 3) {
                    acc.printMiniStatement();
                    System.out.println("Balance: " + acc.getBalance());

                } else if (choice == 4) {
                    break;
                }

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (InSufficientFundsException e) {
                System.out.println(e.getMessage());
            }
        }

        sc.close();
    }
}