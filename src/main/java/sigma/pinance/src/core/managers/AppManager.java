package sigma.pinance.src.core.managers;

import lombok.Getter;
import sigma.pinance.src.core.budget.BudgetItem;
import sigma.pinance.src.core.exceptions.AppException;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class AppManager extends ObjectiveManager {
    private static BudgetItem viewpoint;

    public static void selectObjective(UUID objectiveID) {
        ObjectiveManager.selectObjective(objectiveID);
        viewpoint = ObjectiveManager.getCurrentObjective().getBudget();
    }

    public static BudgetItem getViewpoint() {
        if (Objects.isNull(ObjectiveManager.getCurrentObjective())) {
            throw new AppException("There is no objective currently selected!");
        }
        if (Objects.isNull(viewpoint)) {
            viewpoint = ObjectiveManager.getCurrentObjective().getBudget();
        }
        return viewpoint;
    }

    public static ArrayList<BudgetItem> getViewpointChildren() {
        return viewpoint.getChildItems();
    }

    public static void addItem(String name, double amount, String description) {
        if (Objects.isNull(description)) {
            viewpoint.addItem(name, amount);
        } else {
            viewpoint.addItem(name, amount, description);
        }
        ObjectiveManager.validateCurrentObjective();
    }

    public static void traverseDown(UUID budgetItemID) {
        viewpoint = viewpoint.getItem(budgetItemID);
    }

    public static void traverseDown(String itemName) { viewpoint = viewpoint.getItem(itemName); }

    public static void traverseUp() {
        if (Objects.isNull(viewpoint.getParentBudget())) {
            throw new AppException("You cannot traverse up when you are at the root!");
        }
        viewpoint = viewpoint.getParentBudget();
    }

    public AppManager() {
        super();
    }
}
