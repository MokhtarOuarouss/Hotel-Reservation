package skypay.test.Entity;

import skypay.test.exception.InsufficientBalanceException;

public class User {
    private int userId;
    private int balance;

    public User(int userId, int balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public int getUserId() {
        return userId;
    }


    public int getBalance() {
        return balance;
    }

    public void deductBalance(int amount) throws InsufficientBalanceException {
        if (balance >= amount) {
            balance -= amount;
        } else {
            throw new InsufficientBalanceException("User does not have enough balance to deduct. Required: " + amount + ", Available: " + balance);
        }
    }
}
