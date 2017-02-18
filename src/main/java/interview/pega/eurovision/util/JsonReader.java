package interview.pega.eurovision.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to simplify the reading of JSON from a file
 *
 * Created by arrigod on 18/02/17.
 */
public class JsonReader {
    private static JSONParser parser = new JSONParser();

    /**
     * This method read the JSON object from the specified file and returns it
     *
     * @param filePath the file containing the JSON object
     * @return the JSON object
     * @throws IOException
     * @throws ParseException
     */
    public static JSONObject readJsonObjectFromFile(String filePath) throws IOException, ParseException {
        FileReader fileReader = new FileReader(filePath);
        return (JSONObject) parser.parse(fileReader);
    }

    /**
     * This method read multiple JSON objects from the specified file and returns them.
     * Those object are divided in the file being each on a distinct line.
     *
     * @param filePath the file containing the json OBJECT
     * @return the list of JSON objects
     * @throws IOException
     * @throws ParseException
     */
    public static List<JSONObject> readJsonObjectsFromFile(String filePath) throws IOException, ParseException {
        List<JSONObject> jsonObjects = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                jsonObjects.add(parseJsonObject(line));
            }
        }

        return jsonObjects;
    }

    /**
     * This method is used to convert a JSON string into a JSONObject.
     *
     * @param objectString the string defining the JSON object
     * @return the JSON object
     * @throws ParseException
     */
    private static JSONObject parseJsonObject(String objectString) throws ParseException {
        return (JSONObject) parser.parse(objectString);
    }
}
