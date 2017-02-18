package interview.pega.eurovision.actions;

import org.junit.Test;

/**
 * Created by arrigod on 18/02/17.
 */
public class ActionsTest {
    @Test
    public void testGetActionByCommand() {
        Action action = Actions.getActionByCommand("load");
        assert (action != null);
        assert (action instanceof LoadAction);
    }

    @Test
    public void testGetActionByNonExistingCommand() {
        Action action = Actions.getActionByCommand("nonExisting");
        assert (action == null);
    }
}
