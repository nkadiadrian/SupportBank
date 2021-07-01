package training.supportbank;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static final String LIST_REGEX = "List[\\s]+(\\w[ \\w]+)";

    public static void main(String[] args) {
        AccountMap accountMap  = new AccountMap();

        accountMap.parseCSV();

        boolean exit = false;
        while (!exit) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("What instruction would you like?");
            String input = scanner.nextLine();

            Pattern pattern = Pattern.compile(LIST_REGEX, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);

            if (input.toUpperCase().equals("EXIT")) {
              exit = true;
              System.out.println("Exiting Support Bank");
            } else if (input.equals("List All")) {
                accountMap.getAccountMap().values().stream().forEach(System.out::println);
            } else if (matcher.matches()) {
                String accountName = matcher.group(1);
                if (accountMap.getAccountMap().containsKey(accountName)) {
                    accountMap.getAccountMap().get(accountName).getTransactions().stream().forEach(System.out::println);
                } else {
                    System.out.println("Account not found");
                }
            }
        }
    }
}
