package sigma.pinance.src.cli.commands;

import sigma.pinance.src.cli.CLIController;
import sigma.pinance.src.cli.models.Command;
import sigma.pinance.src.cli.models.CommandInput;
import sigma.pinance.src.cli.utils.UserInputUtils;
import sigma.pinance.src.core.managers.AppManager;
import sigma.pinance.src.core.managers.ObjectiveManager;

import java.time.LocalDate;
import java.util.Scanner;

public final class Objective extends Command {
    @Override
    public void execute(CommandInput commandInput, Scanner scanner) {
        String name = UserInputUtils.queryUser("Enter name", scanner);
        double startingBudget = getUserStartingBudget(scanner);
        LocalDate startDate = LocalDate.parse(UserInputUtils.queryUser("Start Date", scanner));
        LocalDate endDate = LocalDate.parse(UserInputUtils.queryUser("End Date", scanner));

        sigma.pinance.src.core.budget.Objective o = ObjectiveManager.createNewObjective(name, startingBudget, startDate, endDate);
        if (UserInputUtils.queryUserValidation("Go to Objective " + o.getName() + "?", scanner)) {
            AppManager.selectObjective(o.getObjectiveID());
        }
        CLIController.executeCommand("view");
    }

    private double getUserStartingBudget(Scanner scanner) {
        return Double.parseDouble(UserInputUtils.queryUser("Enter starting budget", scanner));
    }
}
