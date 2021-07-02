package training.supportbank;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


// for XML file
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class ParserFactory {
    private static final String FILE_ENDING_REGEX = ".+\\.(\\w+)";

    public void parseInput(AccountMap accountMap, String fileName, String fileType) {
        selectParser(accountMap, fileName, fileType);
    }

    public static void parseInput(AccountMap accountMap, String fileName) {
        Pattern pattern = Pattern.compile(FILE_ENDING_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(fileName);
        //String fileType = Arrays.stream(fileName.split("\\.")).collect(Collectors.toList()).get(-1);
        if (matcher.matches()) {
            String fileType = matcher.group(1);
            selectParser(accountMap, fileName, fileType);
        }
    }

    private static void selectParser(AccountMap accountMap, String fileName, String fileType) {
        fileType = fileType.toLowerCase();
        switch(fileType) {
            case "csv":
                System.out.println("csv");
                parseCSV(accountMap, fileName);
                break;
            case "json":
                parseJSON(accountMap, fileName);
                break;
            case "xml":
                parseXML(accountMap, fileName);
                break;
            default:
                parseCSV(accountMap, fileName);
        }
    }

    private static void parseJSON(AccountMap accountMap, String fileName) {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(fileName)) {
            Type transactionList = new TypeToken<ArrayList<Transaction>>(){}.getType();

            List<Transaction> transactions = gson.fromJson(reader, transactionList);
            for (Transaction transaction : transactions) {
                accountMap.addTransaction(transaction);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static String format(Integer days){
        int since12 = days - 40907;
        int[] list = {31, 60, 91, 121,152,182, 213, 244, 274, 305 , 335, 366} ;
        int month = 0;
        int day = 0;
        for(int i = 0; list[i] < since12; i++){
            month = i;
        }
        day = since12 - list[month];
        month++;
        String parsedDays = String.valueOf(day) + "/" + String.valueOf(month) + "/2012";
        return parsedDays;
    }

    private static void parseXML(AccountMap accountMap, String fileName) {
        try{
            File inputFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize(); 
            NodeList nList = doc.getElementsByTagName("SupportTransaction");

            for(int temp = 1; temp <nList.getLength(); temp++){
                Node nNode =nList.item(temp);
                System.out.println(temp);
                if(nNode.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) nNode;
                    String date = eElement.getAttribute("Date");
                    Integer days = Integer.parseInt(date);
                    String parsedDate = format(days);
                    String description = eElement.getElementsByTagName("Description").item(0).getTextContent();
                    String value = eElement.getElementsByTagName("Value").item(0).getTextContent();
                    String from = eElement.getElementsByTagName("From").item(0).getTextContent();
                    String to = eElement.getElementsByTagName("To").item(0).getTextContent();
                    Transaction transaction = new Transaction(parsedDate+","+from+"," +to+","+description+","+value);
                    accountMap.addTransaction(transaction);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    private static void parseCSV(AccountMap accountMap, String fileName) {
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
