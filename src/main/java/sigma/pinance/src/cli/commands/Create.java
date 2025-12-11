package sigma.pinance.src.cli.commands;

import sigma.pinance.src.cli.CLIController;
import sigma.pinance.src.cli.models.Command;
import sigma.pinance.src.cli.models.CommandInput;
import sigma.pinance.src.cli.utils.UserInputUtils;
import sigma.pinance.src.core.exceptions.AppException;
import sigma.pinance.src.core.managers.AppManager;

import java.util.Objects;
import java.util.Scanner;

public final class Create extends Command {
    @Override
    public void execute(CommandInput commandInput, Scanner scanner) {
        if (Objects.isNull(AppManager.getViewpoint())) {
            throw new AppException("Please create or select an objective first!");
        }

        var args = commandInput.args();
        if (args.isEmpty()) {
            throw new AppException("Please specify the name of the new item!");
        }

        double amount = getBudgetAmountFromUser(scanner);
        String description = UserInputUtils.queryUser("Enter description", scanner);
        AppManager.addItem(args.getFirst(), amount, description);

        CLIController.executeCommand("view");
    }

    public double getBudgetAmountFromUser(Scanner scanner) {
        return Double.parseDouble(UserInputUtils.queryUser("Enter amount", scanner));
    }
}
