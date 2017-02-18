package interview.pega.eurovision.actions;

/**
 * This is the interface used by all the actions in the system.
 *
 * Created by arrigod on 18/02/17.
 */
public interface Action {

    /**
     * This method is used to start the action execution.
     *
     * @param argv the list of parameters for the action
     */
    void execute(String[] argv);
}
