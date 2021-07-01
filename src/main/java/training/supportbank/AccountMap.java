package training.supportbank;

import java.util.HashMap;
import java.util.Map;

public class AccountMap {
    Map<String, Account> accountMap  = new HashMap<>();

    public AccountMap() {
    }

    public void addTransaction(Transaction transaction) {
        String to = transaction.getTo();
        String from = transaction.getFrom();
        accountMap.putIfAbsent(from, new Account(from));
        accountMap.putIfAbsent(to, new Account(to));
        accountMap.get(from).addTransactionFrom(transaction);
        accountMap.get(to).addTransactionTo(transaction);
    }

    public Map<String, Account> getAccountMap() {
        return accountMap;
    }

    public void setAccountMap(Map<String, Account> accountMap) {
        this.accountMap = accountMap;
    }

}
