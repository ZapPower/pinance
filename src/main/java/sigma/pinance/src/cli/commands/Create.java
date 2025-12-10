package sigma.pinance.src.cli.commands;

import sigma.pinance.src.cli.models.Command;
import sigma.pinance.src.cli.models.CommandInput;
import sigma.pinance.src.core.managers.ObjectiveManager;

import java.time.LocalDate;

public final class Create extends Command {
    @Override
    public void execute(CommandInput commandInput) {
        var args = commandInput.args();
        ObjectiveManager.createNewObjective(
                args.isEmpty() ? "Test" : args.getFirst(),
                200,
                LocalDate.now(),
                LocalDate.now()
        );
    }
}
