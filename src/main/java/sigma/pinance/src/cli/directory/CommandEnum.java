package sigma.pinance.src.cli.directory;

import lombok.Getter;
import sigma.pinance.src.cli.commands.Create;
import sigma.pinance.src.cli.commands.Objective;
import sigma.pinance.src.cli.commands.Update;
import sigma.pinance.src.cli.commands.View;
import sigma.pinance.src.cli.models.Command;

@Getter
public enum CommandEnum {
    VIEW("view", new View()),
    CREATE("create", new Create()),
    OBJECTIVE("objective", new Objective()),
    UPDATE("update", new Update(), "update {item} [name | desc | amount] {updated}");

    private final Command command;
    private final String name;
    CommandEnum(String name, Command command, String argformat) {
        this.command = command;
        this.name = name;
        command.setArgFormat(argformat);
    }

    CommandEnum(String name, Command command) {
        this(name, command, null);
    }

}
