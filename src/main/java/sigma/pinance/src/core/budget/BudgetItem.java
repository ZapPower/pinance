package sigma.pinance.src.core.budget;

import lombok.Data;
import sigma.pinance.src.core.exceptions.BudgetItemException;
import sigma.pinance.src.core.exceptions.Severity;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

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

    public BudgetItem getItem(UUID id) {
        BudgetItem item = childItems.stream()
                .filter(i -> i.budgetItemID.equals(id))
                .findFirst().orElse(null);
        if (Objects.isNull(item)) {
            throw new BudgetItemException(Severity.LOW, "Could not find budget item with id " + id, budgetItemID);
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
//        for (BudgetItem child : childItems) {
//            child.validate();
//        }
    }

    public double getCompletedItemAmount() {
        double amount = 0;
        if (!completed) {
            for (BudgetItem budgetItem : childItems) {
                amount += budgetItem.getCompletedItemAmount();
            }
        }
        return amount + (completed ? amount : 0);
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
}
