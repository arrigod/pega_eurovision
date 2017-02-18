package interview.pega.eurovision;

import interview.pega.eurovision.actions.Action;
import interview.pega.eurovision.actions.Actions;

import java.util.Arrays;

/**
 * This class hold the main methods of the eurovision song contest voting system
 *
 * Created by arrigod on 18/02/17.
 */
public class Main {
    /**
     * This is the main method of the eurovision song contest voting system
     *
     * @param argv the list of parameters
     */
    public static void main(String[] argv) {
        int argumentsLength = argv.length;

        if (argumentsLength == 0) {
            printHelp();
        } else {
            String command = argv[0];
            Action action = Actions.getActionByCommand(command);
            if (action == null) {
                printHelp();
                return;
            }

            action.execute(Arrays.copyOfRange(argv, 1, argumentsLength));
        }
    }

    /**
     * This methods displays all the available actions to the user
     */
    private static void printHelp() {
        System.out.println("Specify a valid action:");
        for (Actions action: Actions.values()) {
            System.out.println("-\t" + action.getCommand());
        }
    }
}
