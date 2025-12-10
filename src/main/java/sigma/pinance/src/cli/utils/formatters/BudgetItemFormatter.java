package sigma.pinance.src.cli.utils.formatters;

import sigma.pinance.src.cli.utils.TextColor;
import sigma.pinance.src.core.budget.BudgetItem;

import java.util.Objects;

public class BudgetItemFormatter {
    public static String format(BudgetItem item) {
        StringBuilder builder = new StringBuilder();
        builder.append(colorByCompletion(item.getName(), item.isCompleted()));
        builder.append(" (");
        builder.append(item.getAmount());
        builder.append(")");
        builder.append("\n\t");
        for (BudgetItem budgetItem : item.getChildItems()) {
            builder.append("- ").append(colorByCompletion(budgetItem.getName(), budgetItem.isCompleted()))
                    .append(" (").append(budgetItem.getAmount()).append(")")
                    .append("\n\t");
        }
        builder.append("\r").append(Objects.nonNull(item.getDescription()) ? item.getDescription() : "");
        return builder.toString();
    }

    private static String colorByCompletion(String str, boolean completed) {
        TextColor tc = completed ? TextColor.GREEN : TextColor.ORANGE;
        return tc + str + TextColor.RESET;
    }
}
