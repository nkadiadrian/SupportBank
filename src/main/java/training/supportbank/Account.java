package training.supportbank;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private String name;
    private long amountOwed;
    private List<Transaction> transactions;

    public Account(String name) {
        this.name = name;
        amountOwed = 0;
        transactions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAmountOwed() {
        return amountOwed;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransactionTo(Transaction transaction) {
        transactions.add(transaction);
        amountOwed += (long) transaction.getAmount() * 100;
    }

    public void addTransactionFrom(Transaction transaction) {
        transactions.add(transaction);
        amountOwed -= (long) transaction.getAmount() * 100;
    }

    @Override
    public String toString() {
        return name + ", amountOwed: " + amountOwed/100.0;
    }
}
