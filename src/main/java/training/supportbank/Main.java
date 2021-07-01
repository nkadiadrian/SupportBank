package training.supportbank;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        AccountMap accountMap  = new AccountMap();

        try {
            Scanner sc = new Scanner(new File("src/main/java/training/supportbank/Transactions2014.csv"));
            String row = "";
            if (sc.hasNextLine()) sc.nextLine(); //ignores the first line of the csv file as it is the header
            while (sc.hasNextLine()) {
                row = sc.nextLine();
                Transaction transaction = new Transaction(row);
                accountMap.addTransaction(transaction);
                }
            sc.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("What instruction would you like?");
        String input = scanner.nextLine();
        if (input.equals("List All")) {
            accountMap.getAccountMap().values().stream().forEach(System.out::println);
        }

        System.out.println("Test!");
    }
}
