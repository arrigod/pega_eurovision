package interview.pega.eurovision.actions;

/**
 * Here we specify all the actions that can be executed by the tool.
 *
 * Created by arrigod on 18/02/17.
 */
public enum Actions {
    LOAD(LoadAction.command, LoadAction.getInstance()),
    RESULTS(ResultsAction.command, ResultsAction.getInstance());

    private final String command;
    private final Action action;

    Actions(String command, Action action) {
        this.command = command;
        this.action = action;
    }

    public String getCommand() {
        return command;
    }

    public Action getAction() {
        return action;
    }

    public static Action getActionByCommand(String command) {
        for (Actions action: Actions.values()) {
            if (action.getCommand().equals(command)) {
                return action.getAction();
            }
        }

        return null;
    }
}
