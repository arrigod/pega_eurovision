package interview.pega.eurovision.actions;

import interview.pega.eurovision.util.JsonReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * The results action is responsible for loading and displaying the computed results for the given year and country.
 *
 * Created by arrigod on 18/02/17.
 */
class ResultsAction implements Action {
    final static String command = "results";

    private static ResultsAction instance;
    private ResultsAction() {}

    static ResultsAction getInstance() {
        if (instance == null) {
            instance = new ResultsAction();
        }

        return instance;
    }

    @Override
    public void execute(String[] argv) {
        if (argv.length != 2) {
            System.out.println("usage: eurovision results <country> <year>");
            return;
        }

        String country = argv[0];
        String year = argv[1];

        try {
            doResults(country, year);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * This method is responsible for loading the requested results and to display them.
     *
     * @param country the country for which the results have to be loaded
     * @param year the year for which the results have to be loaded
     * @throws Exception the exception message describes the error in a human readeable form
     */
    private void doResults(String country, String year) throws Exception {
        try {
            String currentPath = new File("").getAbsolutePath();

            JSONObject results = JsonReader.readJsonObjectFromFile(currentPath + "/" +year);

            printCountryResults(results, country, year);
        } catch (IOException exception) {
            throw new Exception("The results file for year " + year + " does not seem to be readeable. Try to reload it.");
        } catch (ParseException exception) {
            throw new Exception("The results file for year " + year + " does not seem to be in the correct format. Try to reload it.");
        }
    }

    /**
     * This method is responsible for printing the results in a human readeable form.
     * The results are returned sorted in an ascending order of points.
     *
     * Protected in order to be visible for testing.
     *
     * @param results the results for the given year
     * @param country the country for which the results have to be printed
     * @param year the year of the given results
     */
    protected void printCountryResults(JSONObject results, String country, String year) {
        JSONObject countryResults = (JSONObject) results.get(country);

        System.out.println(country + " " + year + " voting results:");

        if (countryResults == null || countryResults.size() == 0) {
            System.out.println("No results found.");
            return;
        }

        List<Object> orderedResults = Arrays.asList(countryResults.entrySet().toArray());

        Collections.sort(orderedResults, (o1, o2) -> {
            Long votes1 = (Long) ((Map.Entry) o1).getValue();
            Long votes2 = (Long) ((Map.Entry) o2).getValue();
            return votes1.compareTo(votes2);
        });

        for(Object result : orderedResults) {
            Map.Entry entry = (Map.Entry) result;

            Long votes = (Long) entry.getValue();
            String pointSentence = votes == 1? "point goes to" : "points go to";

            System.out.println(votes + " " + pointSentence + " " + entry.getKey());
        }
    }
}
