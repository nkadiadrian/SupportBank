package training.supportbank;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AccountMap {
    private Map<String, Account> accountMap  = new HashMap<>();

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

    public void parseCSV(){
        try {
            Scanner sc = new Scanner(new File("src/main/java/training/supportbank/Transactions2014.csv"));
            String row = "";
            if (sc.hasNextLine()) sc.nextLine(); //ignores the first line of the csv file as it is the header
            while (sc.hasNextLine()) {
                row = sc.nextLine();
                Transaction transaction = new Transaction(row);
                this.addTransaction(transaction);
            }
            sc.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public Map<String, Account> getAccountMap() {
        return accountMap;
    }

    public void setAccountMap(Map<String, Account> accountMap) {
        this.accountMap = accountMap;
    }

}
