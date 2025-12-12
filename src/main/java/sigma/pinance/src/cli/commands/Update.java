package sigma.pinance.src.cli.commands;

import sigma.pinance.src.cli.CLIController;
import sigma.pinance.src.cli.models.Command;
import sigma.pinance.src.cli.models.CommandInput;
import sigma.pinance.src.cli.utils.UserInputUtils;
import sigma.pinance.src.core.budget.BudgetItem;
import sigma.pinance.src.core.exceptions.AppException;
import sigma.pinance.src.core.managers.AppManager;

import java.util.Scanner;

public final class Update extends Command {
    @Override
    public void execute(CommandInput commandInput, Scanner scanner) {
        var args = commandInput.args();
        if (args.size() < 3) {
            throw new AppException(getArgFormat());
        }


        BudgetItem updateItem = (args.getFirst().equalsIgnoreCase("this")) ?
                AppManager.getViewpoint() :
                AppManager.getItem(args.getFirst());

        switch (args.get(1).toLowerCase()) {
            case "name":
                updateItem.setName(args.get(2));
                break;
            case "desc":
                updateItem.setDescription(args.get(2));
                break;
            case "amount":
                updateItem.setAmount(UserInputUtils.parseDouble(args.get(2)));
                break;
            default:
                throw new AppException("Invalid item property!");
        }

        CLIController.executeCommand("view");
    }
}
