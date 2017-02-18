package interview.pega.eurovision.util;

import org.json.simple.JSONObject;
import org.junit.Test;

import java.util.List;

/**
 * Created by arrigod on 18/02/17.
 */
public class JsonReaderTest {
    @Test
    public void testReadJsonObjectFromFile() throws Exception {
        JSONObject jsonObject = JsonReader.readJsonObjectFromFile(getClass().getClassLoader().getResource("results1").getPath());
        assert (jsonObject != null);

        assert (jsonObject.size() == 3);

        JSONObject countryResults1 = (JSONObject) jsonObject.get("Netherlands");
        JSONObject countryResults2 = (JSONObject) jsonObject.get("Italy");
        JSONObject countryResults3 = (JSONObject) jsonObject.get("Germany");

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

    @Test
    public void readJsonObjectsFromFile() throws Exception {
        List<JSONObject> jsonObjects = JsonReader.readJsonObjectsFromFile(getClass().getClassLoader().getResource("votes1").getPath());

        assert (jsonObjects.size() == 5);

        int NLvotesForBelgium = 0;
        int NLvotesForGermany = 0;
        int NLvotesForMalta = 0;
        int ITvotesForGermany = 0;
        int DEvotesForNetherlands = 0;
        int otherVotes = 0;

        for (JSONObject jsonObject : jsonObjects) {
            if (jsonObject.get("country").equals("Netherlands") && jsonObject.get("votedFor").equals("Belgium")) {
                NLvotesForBelgium++;
            } else if (jsonObject.get("country").equals("Netherlands") && jsonObject.get("votedFor").equals("Germany")) {
                NLvotesForGermany++;
            } else if (jsonObject.get("country").equals("Netherlands") && jsonObject.get("votedFor").equals("Malta")) {
                NLvotesForMalta++;
            } else if (jsonObject.get("country").equals("Italy") && jsonObject.get("votedFor").equals("Germany")) {
                ITvotesForGermany++;
            } else if (jsonObject.get("country").equals("Germany") && jsonObject.get("votedFor").equals("Netherlands")) {
                DEvotesForNetherlands++;
            } else {
                otherVotes++;
            }
        }

        assert (NLvotesForBelgium == 1);
        assert (NLvotesForGermany == 1);
        assert (NLvotesForMalta == 1);
        assert (ITvotesForGermany == 1);
        assert (DEvotesForNetherlands == 1);
        assert (otherVotes == 0);
    }
}