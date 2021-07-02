package training.supportbank;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    public static final String LIST_REGEX = "List[\\s]+(\\w[ \\w]+)";
    public static final String IMPORT_REGEX = "Import File[\\s]+([/\\w\\\\]+.[\\w]+)";

    public static void main(String[] args) {
        AccountMap accountMap  = new AccountMap();
        boolean killProgram = false;

        LOGGER.info("TEST INFO LOG");
        while (!killProgram) {
            killProgram = importFileLoop(accountMap);
            killProgram = listInputLoop(accountMap, killProgram);
        }
    }

    private static boolean importFileLoop(AccountMap accountMap) {
        boolean exit = false;
        boolean killProgram = false;

        while (!exit) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("What file would you like to load in? (Type quit to quit the program or default to load the default file)");
            String input = scanner.nextLine();

            Pattern pattern = Pattern.compile(IMPORT_REGEX, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);
            if (input.equalsIgnoreCase("QUIT")) {
                exit = true;
                killProgram = true;
            } else if (input.equalsIgnoreCase("DEFAULT")) {
                exit = true;
                accountMap.parseInput("src/main/java/training/supportbank/Transactions2014.csv");
            } else if (matcher.matches()) {
                exit = true; // TODO: Add check to not exit if input failed
                accountMap.parseInput(matcher.group(1));
            }
        }
        return killProgram;
    }

    private static boolean listInputLoop(AccountMap accountMap, boolean killProgram) {
        boolean exit = false;

        while (!exit & !killProgram) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("What instruction would you like? (Type quit to quit the program or import to import a new file)");
            String input = scanner.nextLine();

            Pattern pattern = Pattern.compile(LIST_REGEX, Pattern.CASE_INSENSITIVE);

            Matcher matcher = pattern.matcher(input);

            if (input.equalsIgnoreCase("IMPORT")) {
                exit = true;
                System.out.println("Preparing to load another file");
            } else if (input.equalsIgnoreCase("QUIT")) {
              exit = true;  
              killProgram = true;
              System.out.println("Exiting Support Bank");
            } else if (input.equals("List All")) {
                accountMap.getAccountMap().values().forEach(System.out::println);
            } else if (matcher.matches()) {
                String accountName = matcher.group(1);
                if (accountMap.getAccountMap().containsKey(accountName)) {
                    accountMap.getAccountMap().get(accountName).getTransactions().forEach(System.out::println);
                } else {
                    System.out.println("Account not found");
                }
            }
        }
        return killProgram;
    }
}
