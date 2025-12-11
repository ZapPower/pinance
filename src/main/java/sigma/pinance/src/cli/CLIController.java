package sigma.pinance.src.cli;

import lombok.Getter;
import sigma.pinance.src.cli.config.CLIConfig;
import sigma.pinance.src.cli.directory.CommandDirectory;
import sigma.pinance.src.cli.models.Command;
import sigma.pinance.src.cli.models.CommandInput;
import sigma.pinance.src.cli.utils.UserInputUtils;
import sigma.pinance.src.cli.utils.formatters.ExceptionFormatter;
import sigma.pinance.src.core.managers.AppManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public final class CLIController {
    @Getter
    private static final ArrayList<CommandInput> COMMAND_LOG = new ArrayList<>();
    private static Scanner scanner;

    public static void executeCommand(String input) {
        CommandInput commandInput = parseCommand(input);
        if (commandInput.commandName().isBlank()) return;
        Command command = CommandDirectory.fetchCommand(commandInput.commandName());
        if (Objects.isNull(command)) {
            System.out.println("Command '" + commandInput.commandName() + "' found - try 'help'");
            return;
        }

        try {
            command.execute(commandInput, scanner);
        } catch (RuntimeException e) {
            System.out.println(ExceptionFormatter.getExceptionString(e));
        }
    }

    private static CommandInput parseCommand(String input) {
        String[] args = input.toLowerCase().split(" ");
        if (args.length == 0) {
            return new CommandInput("", null);
        }
        String commandName = args[0];
        ArrayList<String> commandArgs = new ArrayList<>(Arrays.asList(args).subList(1, args.length));
        return new CommandInput(commandName, commandArgs);
    }

    public static void init(Scanner s) {
        scanner = s;
        System.out.print(UserInputUtils.getRelativeQueryPrefix());
    }

    public static void nextCommand() {
        String input = scanner.nextLine();
        executeCommand(input);
        System.out.print(UserInputUtils.getRelativeQueryPrefix());
    }
}
