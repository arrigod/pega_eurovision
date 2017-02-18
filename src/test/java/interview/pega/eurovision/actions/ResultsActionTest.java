package interview.pega.eurovision.actions;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Created by arrigod on 18/02/17.
 */
public class ResultsActionTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final ResultsAction resultsAction = (ResultsAction) ResultsAction.getInstance();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void testExecuteNoParameters() {
        resultsAction.execute(new String[]{});
        assert("usage: eurovision results <country> <year>\n".equals(outContent.toString()));
    }

    @Test
    public void testExecuteFewParameters() {
        resultsAction.execute(new String[]{"param1"});
        assert("usage: eurovision results <country> <year>\n".equals(outContent.toString()));
    }

    @Test
    public void testExecuteMoreParameters() {
        resultsAction.execute(new String[]{"param1", "param2", "param3"});
        assert("usage: eurovision results <country> <year>\n".equals(outContent.toString()));
    }

    @Test
    public void testPrintCountryResults() {
        JSONObject results = new JSONObject();

        JSONObject netherlandsResults = new JSONObject();
        netherlandsResults.put("Belgium", 1L);
        netherlandsResults.put("Germany", 10L);
        netherlandsResults.put("Malta", 3L);

        JSONObject italyResults = new JSONObject();
        italyResults.put("Germany", 1L);

        JSONObject germanyResults = new JSONObject();
        germanyResults.put("Netherlands", 1L);

        results.put("Netherlands", netherlandsResults);
        results.put("Italy", italyResults);
        results.put("Germany", germanyResults);

        resultsAction.printCountryResults(results, "Netherlands", "2016");

        String printedResults =
            "Netherlands 2016 voting results:\n" +
            "1 point goes to Belgium\n" +
            "3 points go to Malta\n" +
            "10 points go to Germany\n";

        assert(printedResults.equals(outContent.toString()));
    }

    @Test
    public void testPrintCountryResultsForNoResults() {
        JSONObject results = new JSONObject();

        JSONObject netherlandsResults = new JSONObject();
        netherlandsResults.put("Belgium", 1L);
        netherlandsResults.put("Germany", 10L);
        netherlandsResults.put("Malta", 3L);

        JSONObject italyResults = new JSONObject();
        italyResults.put("Germany", 1L);

        JSONObject germanyResults = new JSONObject();
        germanyResults.put("Netherlands", 1L);

        results.put("Netherlands", netherlandsResults);
        results.put("Italy", italyResults);
        results.put("Germany", germanyResults);

        resultsAction.printCountryResults(results, "Switzerland", "2017");

        String printedResults =
            "Switzerland 2017 voting results:\n" +
            "No results found.\n";

        assert(printedResults.equals(outContent.toString()));
    }
}
