package sigma.pinance.src.cli.models;

import java.util.Scanner;

public abstract class Command {
    public abstract void execute(CommandInput commandInput, Scanner scanner);
}
