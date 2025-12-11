package sigma.pinance.src.cli.utils.formatters;

import sigma.pinance.src.cli.utils.TextColor;
import sigma.pinance.src.core.exceptions.AppException;
import sigma.pinance.src.core.exceptions.BudgetItemException;
import sigma.pinance.src.core.exceptions.ObjectiveException;
import sigma.pinance.src.core.exceptions.Severity;

public final class ExceptionFormatter {
    public static String getExceptionString(RuntimeException e) {
        return switch (e) {
            case BudgetItemException i -> getBudgetItemExceptionString(i);
            case ObjectiveException i -> getObjectiveExceptionString(i);
            case AppException i -> getAppExceptionString(i);
            default -> e.getMessage();
        };
    }

    private static String getBudgetItemExceptionString(BudgetItemException e) {
        return "BudgetItemException: " + e.getMessage() + "\n\tSeverity: " +
                formatSeverity(e.getSeverity()) +
                "\nItemID: " + e.getBudgetItemID();
    }

    private static String getObjectiveExceptionString(ObjectiveException e) {
        return "ObjectiveException: " + e.getMessage() +
                "\n\tSeverity: " + formatSeverity(e.getSeverity());
    }

    private static String getAppExceptionString(AppException e) {
        return "AppException: " + e.getMessage();
    }

    private static String formatSeverity(Severity s) {
        TextColor tc;
        if (s.equals(Severity.LOW)) {
            tc = TextColor.YELLOW;
        } else if (s.equals(Severity.MEDIUM)) {
            tc = TextColor.ORANGE;
        } else {
            tc = TextColor.RED;
        }
        return tc + s.toString() + TextColor.RESET;
    }

    private ExceptionFormatter() {}
}
