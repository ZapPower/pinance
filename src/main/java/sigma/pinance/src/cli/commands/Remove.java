package sigma.pinance.src.cli.commands;

import sigma.pinance.src.cli.models.Command;
import sigma.pinance.src.cli.models.CommandInput;
import sigma.pinance.src.cli.utils.UserInputUtils;
import sigma.pinance.src.core.exceptions.AppException;
import sigma.pinance.src.core.managers.AppManager;

import java.util.Scanner;

public class Remove extends Command {
    @Override
    public void execute(CommandInput commandInput, Scanner scanner) {
        var args = commandInput.args();
        if (args.isEmpty()) {
            throw new AppException(getArgFormat());
        }
        if (UserInputUtils.queryUserValidation("Are you sure? (Y/N)", scanner)) {
            AppManager.removeItem(args.getFirst());
        }
    }
}
