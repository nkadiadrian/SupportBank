package training.supportbank;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserFactory {
    private static final String FILE_ENDING_REGEX = ".+\\.(\\w+)";

    public void parseInput(AccountMap accountMap, String fileName, String fileType) {
        selectParser(accountMap, fileName, fileType);
    }

    public static void parseInput(AccountMap accountMap, String fileName) {
        Pattern pattern = Pattern.compile(FILE_ENDING_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(fileName);
        if (matcher.matches()) {
            selectParser(accountMap, fileName, matcher.group(1));
        }
    }

    private static void selectParser(AccountMap accountMap, String fileName, String fileType) {
        fileType = fileType.toLowerCase();
        switch(fileType) {
            case "csv":
                parseCSV(accountMap, fileName);
            case "json":
                parseJSON(accountMap, fileName);
            case "xml":
                parseXML(accountMap, fileName);
            default:
                parseCSV(accountMap, fileName);
        }
    }

    private static void parseJSON(AccountMap accountMap, String fileName) {
    }

    private static void parseXML(AccountMap accountMap, String fileName) {
    }

    public static void parseCSV(AccountMap accountMap, String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
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
    }
}
