package sigma.pinance.src.cli.utils.formatters;

import sigma.pinance.src.cli.config.CLIConfig;
import sigma.pinance.src.cli.utils.TextColor;
import sigma.pinance.src.core.budget.BudgetItem;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Formats BudgetItems to their string format with context.
 */
public final class BudgetItemFormatter {
    public static String format(BudgetItem item) {
        String childFormat = getChildItemsFormat(item.getChildItems());

        return "\n" +
                colorByCompletion(item.getName(), item.isCompleted()) +
                " (" +
                item.getAmount() +
                ")" +
                childFormat +
                "\n" + (Objects.nonNull(item.getDescription()) ? item.getDescription() + "\n" : "");
    }

    private static String colorByCompletion(String str, boolean completed) {
        TextColor tc = completed ? TextColor.GREEN : TextColor.ORANGE;
        return tc + str + TextColor.RESET;
    }

    private static String getChildItemsFormat(ArrayList<BudgetItem> children) {
        StringBuilder builder = new StringBuilder();
        updateChildItemsBuilder(children, builder, 1);
        return builder.toString();
    }

    private static void updateChildItemsBuilder(ArrayList<BudgetItem> children, StringBuilder sb, int depth) {
        if (depth > CLIConfig.MAX_VIEW_DEPTH || children.isEmpty()) {
            return;
        }
        for (BudgetItem child : children) {
            sb.append("\n");
            sb.append("\t".repeat(Math.max(0, depth)));
            sb.append("- ").append(colorByCompletion(child.getName(), child.isCompleted())).append(" (").append(child.getAmount())
                    .append(")");
            if (depth == CLIConfig.MAX_VIEW_DEPTH && !child.getChildItems().isEmpty()) {
                sb.append(" >");
            }
            updateChildItemsBuilder(child.getChildItems(), sb, depth + 1);
        }
    }

    private BudgetItemFormatter() {}
}
