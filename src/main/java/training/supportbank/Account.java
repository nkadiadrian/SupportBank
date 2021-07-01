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

    public void addTransactionTo(Transaction transaction) {
        transactions.add(transaction);
        amountOwed += transaction.getAmount();
    }

    public void addTransactionFrom(Transaction transaction) {
        transactions.add(transaction);
        amountOwed -= transaction.getAmount();
    }
}
