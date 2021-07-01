package training.supportbank;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    AccountMap accountMap  = new AccountMap();

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("src/main/java/training/supportbank/Transactions2014.csv"));
            String row = "";
            if (sc.hasNextLine()) sc.nextLine(); //ignores the first line of the csv file as it is the header
            while (sc.hasNextLine()) {
                row = sc.nextLine();
                Transaction transaction = new Transaction(row);
                System.out.println(transaction.getFrom());
                }
            sc.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Test!");
    }
}
