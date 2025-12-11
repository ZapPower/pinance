package sigma.pinance.src.cli.commands;

import sigma.pinance.src.cli.models.Command;
import sigma.pinance.src.cli.models.CommandInput;
import sigma.pinance.src.cli.utils.formatters.BudgetItemFormatter;
import sigma.pinance.src.core.budget.BudgetItem;
import sigma.pinance.src.core.exceptions.AppException;
import sigma.pinance.src.core.managers.AppManager;

import java.util.Objects;
import java.util.Scanner;

public final class View extends Command {
    @Override
    public void execute(CommandInput commandInput, Scanner scanner) {
        var args = commandInput.args();
        if (args.isEmpty()) {
            viewNoArgs();
        } else if (args.getFirst().equalsIgnoreCase("parent")) {
            AppManager.traverseUp();
            viewNoArgs();
        } else {
            AppManager.traverseDown(args.getFirst());
            viewNoArgs();
        }
    }

    private void viewNoArgs() {
        BudgetItem view = AppManager.getViewpoint();
        if (Objects.isNull(view)) {
            throw new AppException("There is no objective currently selected!");
        }
        System.out.println(BudgetItemFormatter.format(view));
    }
}
