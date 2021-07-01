package training.supportbank;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


// for XML file
import java.io.File;
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
        try{
            File inputFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize(); 
            NodeList nList = doc.getElementsByTagName("SupportTansaction");

            for(int temp = 0; temp <nList.getLength(); temp++){
                Node nNode =nList.item(temp);
                if(nNode.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) nNode;
                    String date = eElement.getAttribute("Date");
                    String description = eElement.getElementsByTagName("Description").item(0).getTextContent();
                    String value = eElement.getElementsByTagName("Value").item(0).getTextContent();
                    String from = eElement.getElementsByTagName("Parties").item(0).getTextContent();
                    String to = eElement.getElementsByTagName("Parties").item(1).getTextContent();
                    Transaction transaction = new Transaction(date+","+from+"," +to+","+description+","+value);
                    accountMap.addTransaction(transaction);

                }
            }


        } catch(Exception e){
            e.printStackTrace();
        }

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
