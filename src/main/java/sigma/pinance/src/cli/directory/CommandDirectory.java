package sigma.pinance.src.cli.directory;

import sigma.pinance.src.cli.models.Command;

public final class CommandDirectory {
    /**
     * Fetches a commandName from the registered commands in {@link CommandEnum}
     *
     * @param commandName The name of the commandName requested
     * @return The commandName to be executed, null if none found
     */
    public static Command fetchCommand(String commandName) {
        for (CommandEnum command : CommandEnum.values()) {
            if (command.getName().equals(commandName)) {
                return command.getCommand();
            }
        }
        return null;
    }

    private CommandDirectory() {}
}
