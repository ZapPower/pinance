package sigma.pinance.src.cli.commands;

import sigma.pinance.src.cli.CLIController;
import sigma.pinance.src.cli.models.Command;
import sigma.pinance.src.cli.models.CommandInput;
import sigma.pinance.src.cli.utils.UserInputUtils;
import sigma.pinance.src.core.budget.BudgetItem;
import sigma.pinance.src.core.exceptions.AppException;
import sigma.pinance.src.core.managers.AppManager;

import java.util.Scanner;

public final class Complete extends Command {

    @Override
    public void execute(CommandInput commandInput, Scanner scanner) {
        var args = commandInput.args();
        if (args.isEmpty()) {
            throw new AppException("Please specify the item to complete: " + getArgFormat());
        }

        BudgetItem updateItem = (args.getFirst().equalsIgnoreCase("this")) ?
                AppManager.getViewpoint() :
                AppManager.getItem(args.getFirst());

        boolean response = UserInputUtils.queryUserValidation("Are you sure you want to complete "
                + updateItem.getName() + "? (Y/N)", scanner);
        if (response) {
            updateItem.complete();
        }

        CLIController.executeCommand("view");
    }
}
