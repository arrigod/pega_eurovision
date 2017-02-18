package interview.pega.eurovision.actions;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

/**
 * Created by arrigod on 18/02/17.
 */
public class LoadActionTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final LoadAction loadAction = (LoadAction) LoadAction.getInstance();

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
        loadAction.execute(new String[]{});
        assert("usage: eurovision load <file> <year>\n".equals(outContent.toString()));
    }

    @Test
    public void testExecuteFewParameters() {
        loadAction.execute(new String[]{"param1"});
        assert("usage: eurovision load <file> <year>\n".equals(outContent.toString()));
    }

    @Test
    public void testExecuteMoreParameters() {
        loadAction.execute(new String[]{"param1", "param2", "param3"});
        assert("usage: eurovision load <file> <year>\n".equals(outContent.toString()));
    }

    @Test
    public void testComputeVotesResults() {
        List<JSONObject> votes = new ArrayList<>();

        JSONObject vote1 = new JSONObject();
        vote1.put("country", "Netherlands");
        vote1.put("votedFor", "Belgium");
        votes.add(vote1);

        JSONObject vote2 = new JSONObject();
        vote2.put("country", "Italy");
        vote2.put("votedFor", "Germany");
        votes.add(vote2);

        JSONObject vote3 = new JSONObject();
        vote3.put("country", "Netherlands");
        vote3.put("votedFor", "Germany");
        votes.add(vote3);

        JSONObject vote4 = new JSONObject();
        vote4.put("country", "Netherlands");
        vote4.put("votedFor", "Malta");
        votes.add(vote4);

        JSONObject vote5 = new JSONObject();
        vote5.put("country", "Germany");
        vote5.put("votedFor", "Netherlands");
        votes.add(vote5);

        JSONObject vote6 = new JSONObject();
        vote6.put("country", "Netherlands");
        vote6.put("votedFor", "Malta");
        votes.add(vote6);

        Map<String, Map<String, Long>> results = loadAction.computeVotesResults(votes);

        assert (results != null);

        assert (results.size() == 3);

        Map<String, Long> countryResults1 = results.get("Netherlands");
        Map<String, Long> countryResults2 = results.get("Italy");
        Map<String, Long> countryResults3 = results.get("Germany");

        assert (countryResults1 != null);
        assert (countryResults2 != null);
        assert (countryResults3 != null);

        assert (countryResults1.size() == 3);
        assert (countryResults2.size() == 1);
        assert (countryResults3.size() == 1);

        assert (new Long(1).equals(countryResults1.get("Belgium")));
        assert (new Long(1).equals(countryResults1.get("Germany")));
        assert (new Long(2).equals(countryResults1.get("Malta")));
        assert (new Long(1).equals(countryResults2.get("Germany")));
        assert (new Long(1).equals(countryResults3.get("Netherlands")));
    }

    @Test
    public void testConvertResultsMapToJsonObject() {
        Map<String, Map<String, Long>> results = new HashMap<>();

        Map<String, Long> netherlandsResults = new HashMap<>();
        netherlandsResults.put("Belgium", 1L);
        netherlandsResults.put("Germany", 1L);
        netherlandsResults.put("Malta", 1L);

        Map<String, Long> italyResults = new HashMap<>();
        italyResults.put("Germany", 1L);

        Map<String, Long> germanyResults = new HashMap<>();
        germanyResults.put("Netherlands", 1L);

        results.put("Netherlands", netherlandsResults);
        results.put("Italy", italyResults);
        results.put("Germany", germanyResults);

        JSONObject resultsJson = loadAction.convertResultsMapToJsonObject(results);

        assert (resultsJson != null);

        assert (resultsJson.size() == 3);

        JSONObject countryResults1 = (JSONObject) resultsJson.get("Netherlands");
        JSONObject countryResults2 = (JSONObject) resultsJson.get("Italy");
        JSONObject countryResults3 = (JSONObject) resultsJson.get("Germany");

        assert (countryResults1 != null);
        assert (countryResults2 != null);
        assert (countryResults3 != null);

        assert (countryResults1.size() == 3);
        assert (countryResults2.size() == 1);
        assert (countryResults3.size() == 1);

        assert (new Long(1).equals(countryResults1.get("Belgium")));
        assert (new Long(1).equals(countryResults1.get("Germany")));
        assert (new Long(1).equals(countryResults1.get("Malta")));
        assert (new Long(1).equals(countryResults2.get("Germany")));
        assert (new Long(1).equals(countryResults3.get("Netherlands")));
    }
}
