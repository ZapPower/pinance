package sigma.pinance.src.core.budget;

import lombok.Data;
import sigma.pinance.src.core.exceptions.BudgetItemException;
import sigma.pinance.src.core.exceptions.Severity;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

/**
 * A Budget Item is a singular item within a budget that can track itself and its children
 * and is the primary unit for budget operations
 */
@Data
public class BudgetItem {
    private double amount;
    private ArrayList<BudgetItem> childItems;
    private String description;
    private UUID budgetItemID;
    private String name;
    private BudgetItem parentBudget;
    private boolean completed;

    public BudgetItem(double amount, String name, BudgetItem parentBudget) {
        this.amount = amount;
        this.name = name;
        this.parentBudget = parentBudget;
        this.childItems = new ArrayList<>();
        this.completed = false;
        this.budgetItemID = UUID.randomUUID();
    }

    /**
     * Grab a child item by budgetItemID
     *
     * @param id budgetItemID
     * @return The associated item
     * @throws BudgetItemException when an item cannot be found
     */
    public BudgetItem getItem(UUID id) {
        BudgetItem item = childItems.stream()
                .filter(i -> i.budgetItemID.equals(id))
                .findFirst().orElse(null);
        if (Objects.isNull(item)) {
            throw new BudgetItemException(Severity.LOW, "Could not find budget item with id " + id, budgetItemID);
        }
        return item;
    }

    /**
     * Grab a child item by name
     *
     * @param name item name
     * @return The associated item
     * @throws BudgetItemException when an item cannot be found
     */
    public BudgetItem getItem(String name) {
        BudgetItem item = childItems.stream()
                .filter(i -> i.getName().equalsIgnoreCase(name))
                .findFirst().orElse(null);
        if (Objects.isNull(item)) {
            throw new BudgetItemException(Severity.LOW, "Could not find item " + name, budgetItemID);
        }
        return item;
    }

    public void addItem(String name, double amount, String description) {
        BudgetItem item = new BudgetItem(amount, name, this);
        item.setDescription(description);
        childItems.add(item);
        validate();
    }

    public void addItem(String name, double amount) {
        addItem(name, amount, null);
        validate();
    }

    public void updateItem(UUID id, double amount) {
        BudgetItem item = getItem(id);
        item.setAmount(amount);
        validate();
    }

    /**
     * Validates that the sub-items within this budget conform to budgeting rules.
     *
     * @throws BudgetItemException When invalid
     */
    public void validate() {
        // Get internal total
        double totalAmount = childItems.stream().mapToDouble(BudgetItem::getAmount).sum();
        if (totalAmount == amount) {
            throw new BudgetItemException(Severity.MEDIUM,
                    "An exact budget has been created. Consider adding some padding!", budgetItemID);
        }
        if (totalAmount > amount) {
            throw new BudgetItemException(Severity.HIGH,
                    "Sub-items total beyond the parent budget! Increase the parent budget or reduce a child budget",
                    budgetItemID);
        }
        for (BudgetItem child : childItems) {
            child.validate();
        }
    }

    /**
     * @return The total completed amount of the child budget items
     */
    public double getCompletedItemAmount() {
        double completedAmt = 0;
        if (!completed) {
            for (BudgetItem budgetItem : childItems) {
                completedAmt += budgetItem.getCompletedItemAmount();
            }
        }
        return completedAmt + (completed ? completedAmt : 0);
    }

    /**
     * Completes this budgetItem and marks its children as completed
     */
    public void complete() {
        completed = true;
        for (BudgetItem child : getChildItems()) {
            if (!child.isCompleted()) {
                child.complete();
            }
        }
    }

    /**
     * Un-Completes this budget item and marks the parent as un-completed
     */
    public void unComplete() {
        completed = false;
        if (Objects.nonNull(parentBudget) && parentBudget.isCompleted()) {
            parentBudget.unComplete();
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        builder.append(" (");
        builder.append(amount);
        builder.append(")");
        builder.append("\n\t");
        for (BudgetItem budgetItem : childItems) {
            builder.append("- ").append(budgetItem.getName())
                    .append(" (").append(budgetItem.getAmount()).append(")")
                    .append("\n\t");
        }
        builder.append("\r").append(Objects.nonNull(description) ? description : "");
        return builder.toString();
    }

    public void setAmount(double newAmount) {
        this.amount = newAmount;
        validate();
    }
}
