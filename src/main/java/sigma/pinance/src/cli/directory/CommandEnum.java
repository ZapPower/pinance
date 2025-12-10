package sigma.pinance.src.cli.directory;

import lombok.Getter;
import sigma.pinance.src.cli.commands.Create;
import sigma.pinance.src.cli.commands.View;
import sigma.pinance.src.cli.models.Command;

@Getter
public enum CommandEnum {
    VIEW("view", new View()),
    CREATE("create", new Create());

    private final Command command;
    private final String name;
    CommandEnum(String name, Command command) {
        this.command = command;
        this.name = name;
    }
}
