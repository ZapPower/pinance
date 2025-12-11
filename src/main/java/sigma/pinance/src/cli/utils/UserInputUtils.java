package sigma.pinance.src.cli.utils;

import sigma.pinance.src.cli.config.CLIConfig;
import sigma.pinance.src.core.budget.BudgetItem;
import sigma.pinance.src.core.budget.Objective;
import sigma.pinance.src.core.managers.AppManager;
import sigma.pinance.src.core.managers.ObjectiveManager;

import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;

public final class UserInputUtils {
    public static String queryUser(String query, Scanner scanner) {
        System.out.print(query + CLIConfig.INPUT_PREFIX);
        return scanner.nextLine();
    }

    public static boolean queryUserValidation(String query, Scanner scanner) {
        System.out.print(query + CLIConfig.INPUT_PREFIX);
        String input = scanner.nextLine().toLowerCase();
        return !input.equals("no") && !input.equals("n");
    }

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

    private UserInputUtils() {}
}
