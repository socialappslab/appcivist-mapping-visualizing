package demographics;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * DataSF, a sample class that shows how to use JSON-Simple to retrieve
 * data from an endpoint provided by the Socrata API of a dataset. In this
 * example, using the Budget dataset from DataSF.
 * @author michaelju
 *
 */
public class DataSF {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        try {
            URL source = new URL("https://data.sfgov.org/resource/xdgd-c79v.json?");
            BufferedReader reader = new BufferedReader(new InputStreamReader(source.openStream()));
            JSONArray json = (JSONArray) parser.parse(reader);
            JSONObject objOne = (JSONObject) json.get(0);
            System.out.println("First row: ");
            System.out.println(objOne.toString());
            System.out.println();
            String number = (String) objOne.get("fiscal_year");
            System.out.println("Fiscal year: ");
            System.out.println(number);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}