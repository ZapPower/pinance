package sigma.pinance.src.core.exceptions;

import lombok.Getter;

import java.util.UUID;

@Getter
public class BudgetItemException extends RuntimeException {
    final Severity severity;
    final UUID budgetItemID;
    public BudgetItemException(Severity severity, String message, UUID budgetItemID) {
        super(message);
        this.severity = severity;
        this.budgetItemID = budgetItemID;
    }
    public BudgetItemException(Severity severity, String message, UUID budgetItemID, Throwable cause) {
        super(message, cause);
        this.severity = severity;
        this.budgetItemID = budgetItemID;
    }
}
