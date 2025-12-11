package sigma.pinance.src.cli;

import lombok.Getter;
import sigma.pinance.src.cli.directory.CommandDirectory;
import sigma.pinance.src.cli.models.Command;
import sigma.pinance.src.cli.models.CommandInput;
import sigma.pinance.src.cli.utils.parsers.ArgParser;
import sigma.pinance.src.cli.utils.UserInputUtils;
import sigma.pinance.src.cli.utils.formatters.ExceptionFormatter;

import java.util.*;

/**
 * Controls the CLI on a high-level and routes and executes commands.
 */
public final class CLIController {
    @Getter
    private static final ArrayList<CommandInput> COMMAND_LOG = new ArrayList<>();
    private static Scanner scanner;

    /**
     * Executes a command from raw input.
     * {@link CLIController#init(Scanner)} Must be run beforehand.
     *
     * @param input Raw input string
     */
    public static void executeCommand(String input) {
        CommandInput commandInput = parseCommand(input);
        if (commandInput.commandName().isBlank()) return;
        Command command = CommandDirectory.fetchCommand(commandInput.commandName());
        if (Objects.isNull(command)) {
            System.out.println("Command '" + commandInput.commandName() + "' not found - try 'help'");
            return;
        }

        try {
            command.execute(commandInput, scanner);
        } catch (RuntimeException e) {
            System.out.println(ExceptionFormatter.getExceptionString(e));
        }
    }

    private static CommandInput parseCommand(String input) {
        String[] args = input.split(" ");
        if (args.length == 0) {
            return new CommandInput("", null);
        }
        String commandName = args[0].toLowerCase();
        ArrayList<String> commandArgs = ArgParser.parse(args);
        return new CommandInput(commandName, commandArgs);
    }

    /**
     * Initializes the CLI with a scanner.
     * @param s Scanner for user input
     */
    public static void init(Scanner s) {
        scanner = s;
        // TODO: Add wrapper function for this
        System.out.print(UserInputUtils.getRelativeQueryPrefix());
    }

    /**
     * Queries the user for the next command and executes it.
     * {@link CLIController#init(Scanner)} Must be run beforehand.
     */
    public static void nextCommand() {
        String input = scanner.nextLine();
        executeCommand(input);
        System.out.print(UserInputUtils.getRelativeQueryPrefix());
    }

    private CLIController() {}
}
