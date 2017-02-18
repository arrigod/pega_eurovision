package interview.pega.eurovision.actions;

import interview.pega.eurovision.util.JsonReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The load action is responsible of loading and counting the votes as well as storing them in a results file.
 *
 * Created by arrigod on 18/02/17.
 */
class LoadAction implements Action {
    final static String command = "load";

    private static LoadAction instance;
    private LoadAction() {}

    static LoadAction getInstance() {
        if (instance == null) {
            instance = new LoadAction();
        }

        return instance;
    }

    @Override
    public void execute(String[] argv) {
        if (argv.length != 2) {
            System.out.println("usage: eurovision load <file> <year>");
        } else {
            String currentPath = new File("").getAbsolutePath();
            String file = currentPath + "/" + argv[0];
            String year = currentPath + "/" + argv[1];

            try {
                doLoad(file, year);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * This method is responsible to load the votes, compute the results and storing them in the specified file.
     *
     * @param file the absolute path to the input file containing the votes
     * @param year the absolute path to the output file to which the results are stored
     * @throws Exception the exception message describes the error in a human readable form
     */
    private void doLoad(String file, String year) throws Exception {
        List<JSONObject> votes;

        try {
            votes = JsonReader.readJsonObjectsFromFile(file);
        } catch (IOException exception) {
            throw new Exception("The specified file does not seem to be readable.");
        } catch (ParseException exception) {
            throw new Exception("The specified file does not seem to be in the correct format");
        }

        Map<String, Map<String, Long>> results = computeVotesResults(votes);

        try {
            writeResultsToFile(results, year);
        } catch (IOException e) {
            throw new Exception("An error occourred while exporting the loaded results");
        }
    }

    /**
     * This method is responsible for computing the results given a list of votes.
     * Protected in order to be visible for testing.
     *
     * @param votes the list of votes
     * @return a map containing the computer results
     */
    protected Map<String, Map<String, Long>> computeVotesResults(List<JSONObject> votes) {
        Map<String, Map<String, Long>> results = new HashMap<>();

        for(JSONObject vote : votes) {
            String country = (String) vote.get("country");
            String votedFor = (String) vote.get("votedFor");

            Map<String, Long> countryResults = results.get(country);
            if (countryResults == null) {
                countryResults = new HashMap<>();
                results.put(country, countryResults);
            }

            Long numberOfVotes = countryResults.get(votedFor);
            if (numberOfVotes == null) {
                numberOfVotes = 0L;
            }

            countryResults.put(votedFor, numberOfVotes + 1);
        }

        return results;
    }

    /**
     * This method is responsible for the conversion of the computer results into JSON and for writing them in the output file.
     *
     * @param results the computed results
     * @param outputFilePath the file to which the results have to be stored
     * @throws IOException
     */
    private void writeResultsToFile(Map<String, Map<String, Long>> results, String outputFilePath) throws IOException {
        JSONObject resultsJson = convertResultsMapToJsonObject(results);

        FileWriter file = new FileWriter(outputFilePath);
        file.write(resultsJson.toJSONString());
        file.flush();
    }

    /**
     * This method converts the computer results map into JSON.
     * Protected in order to be visible for testing.
     *
     * @param results
     * @return
     */
    protected JSONObject convertResultsMapToJsonObject(Map<String, Map<String, Long>> results) {
        JSONObject resultsJson = new JSONObject();

        for(Map.Entry<String, Map<String, Long>> result : results.entrySet()) {
            JSONObject countryResults = new JSONObject();
            for (Map.Entry<String, Long> countryResult : result.getValue().entrySet()) {
                countryResults.put(countryResult.getKey(), countryResult.getValue());
            }

            resultsJson.put(result.getKey(), countryResults);
        }

        return resultsJson;
    }
}
