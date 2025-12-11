package sigma.pinance.src.cli.utils;

import sigma.pinance.src.cli.config.CLIConfig;
import sigma.pinance.src.cli.utils.parsers.DateParser;
import sigma.pinance.src.core.budget.BudgetItem;
import sigma.pinance.src.core.budget.Objective;
import sigma.pinance.src.core.exceptions.AppException;
import sigma.pinance.src.core.managers.AppManager;
import sigma.pinance.src.core.managers.ObjectiveManager;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;

/**
 * Contains utility functions that can be used to capture/query user input.
 */
public final class UserInputUtils {
    /**
     * Query the user for a raw input.
     * NOTE: The query is sent to the user raw and appended with the INPUT_PREFIX
     * @see CLIConfig
     *
     * @param query The question string to be asked to the user
     * @param scanner The scanner that the user is hooked into
     * @return The raw input
     */
    public static String queryUser(String query, Scanner scanner) {
        // TODO: add wrapper function for this
        System.out.print(query + CLIConfig.INPUT_PREFIX);
        return scanner.nextLine();
    }

    /**
     * Query a user for a Y/N question (boolean)
     * NOTE: The query is sent to the user raw and appended with the INPUT_PREFIX
     * @see CLIConfig
     *
     * @param query The question string to be asked to the user
     * @param scanner The scanner that the user is hooked into
     * @return The boolean response
     */
    public static boolean queryUserValidation(String query, Scanner scanner) {
        String input = queryUser(query, scanner).toLowerCase();
        return !input.equals("no") && !input.equals("n");
    }

    /**
     * Gets the relative query prefix for the CLI (similar to terminal)
     * @return The query prefix for general operations.
     */
    public static String getRelativeQueryPrefix() {
        Objective o = ObjectiveManager.getCurrentObjective();
        if (Objects.isNull(o)) {
            return CLIConfig.INPUT_PREFIX;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("(").append(o.getName());

        BudgetItem curr = AppManager.getViewpoint();
        Stack<String> s = new Stack<>();
        while (curr.getParentBudget() != null) {
            s.add(curr.getName());
            curr = curr.getParentBudget();
        }
        while (!s.empty()) {
            sb.append("/").append(s.pop());
        }
        sb.append(") ").append(CLIConfig.INPUT_PREFIX);

        return sb.toString();
    }

    /**
     * Query a user for a date (fuzzy parsing)
     * NOTE: The query is sent to the user raw and appended with the INPUT_PREFIX
     * @see CLIConfig
     *
     * @param query The question string to be asked to the user
     * @param scanner The scanner that the user is hooked into
     * @return The LocalDate that gets parsed
     */
    public static LocalDate queryUserDate(String query, Scanner scanner) {
        return DateParser.parseFuzzy(queryUser(query, scanner), LocalDate.now());
    }

    // TODO: move to its own parser
    public static double parseDouble(String input) {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new AppException("Input needs to be a number!");
        }
    }

    private UserInputUtils() {}
}
