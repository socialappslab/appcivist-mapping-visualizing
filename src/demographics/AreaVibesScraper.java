package demographics;

import java.net.URL;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;

import org.jsoup.Jsoup;

public class AreaVibesScraper {
    private static final String USroot = "http://www.areavibes.com/";
    private static HashMap<String, String> targToURL = new HashMap<String, String>();
    static {
        targToURL.put("Cost of living index", "/cost-of-living/");
        targToURL.put("Housing index", "/cost-of-living/");
        targToURL.put("Population", "/livability/");
        targToURL.put("Total crime", "/crime/");
        targToURL.put("Median household income", "/employment/");
        targToURL.put("Unemployment rate", "/employment/");
        targToURL.put("Poverty level", "/employment/");
        targToURL.put("Median home price", "/housing/");
    }
    public static void main(String[] args) {
        Scanner inp = new Scanner(System.in);
        try {
            //you can improve by having a parsing method
            //cities and states are separated by -
            //cities with multiple words have the words separated by +,
            //i.e. "palo+alto-ca"
            System.out.print("Enter a city: ");
            String city = inp.next().toLowerCase();
            city = city.replaceAll(" ", "+");
            System.out.print("State abbreviation? (For example, WA for Washington) ");
            String state = inp.next().toLowerCase();
            String location = city + "-" + state;
            System.out.println(location + " statistics: ");
            for (String desired : targToURL.keySet()) {
                System.out.println(getData(location, desired));
            }
        }
        catch (MalformedURLException e) {
            System.out.println("Invalid URL");
            //System.exit(0);
        }
        catch (IOException e) {
            System.out.println("No available statistics. Sorry!");
            //System.exit(0);
        }
    }
    
    public static String getData(String location, String target) 
            throws MalformedURLException, IOException {
        URL url = new URL(USroot + location + targToURL.get(target));
        URLConnection connection = url.openConnection();
        connection.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                                               connection.getInputStream()));
        
        String line = in.readLine();
        int index;
        String[] tokens;
        while (line != null) {
            index = line.indexOf(target);
            if (index != -1) {
                line = line.substring(index);
                line = line.replaceAll("</td>", "FOO");
                //remove html
                line = Jsoup.parse(line).text();
//                System.out.println(line);
                tokens = line.split("FOO");
                return target + ": " + tokens[1];
            }
//            System.out.println(line);
            line = in.readLine();
        }
        return null;
    }
}