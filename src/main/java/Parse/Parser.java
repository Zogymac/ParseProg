package Parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Parser {
    private Document doc = null;
    private String addressSite;
    private String str;
    private String[] arrSplit;
    private Map<String, Integer> map = new HashMap<>();

    public void parse() {
        scanAddressStr();
        getDoc();
        toSplit();
        toUpper();
        getStats();
    }
    private void scanAddressStr() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the site: ");
        addressSite = scanner.next();
//        addressSite = "https://www.simbirsoft.com/";
        System.out.println("You have entered: " + addressSite);
    }
    private void getDoc() {
        try {
            doc = Jsoup.connect(addressSite).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void toSplit() {
        str = doc.text();
        arrSplit = str.trim().split("[\\s\\,\\.\\«\\»\\'\\!\\?\"\\Q\\[\\]\\E\\(\\)\\;\\:\\@\n\r\t]+");

    }
    private void toUpper() {
        for (int i = 0; i < arrSplit.length; i++) {
            arrSplit[i] = arrSplit[i].toUpperCase();
        }
    }

    private void getStats() {
        for (String s : arrSplit) {
            if (map.containsKey(s)) {
                int counter = map.get(s);
                counter++;
                map.put(s, counter);
            } else {
                map.put(s, 1);
            }
        }
        map.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue())
                .forEach(System.out::println);
    }
}
