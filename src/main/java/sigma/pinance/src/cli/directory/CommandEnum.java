package sigma.pinance.src.cli.directory;

import lombok.Getter;
import sigma.pinance.src.cli.commands.*;
import sigma.pinance.src.cli.models.Command;

@Getter
public enum CommandEnum {
    VIEW("view", new View(), "view {item}"),
    CREATE("create", new Create(), "create {item}"),
    OBJECTIVE("objective", new Objective()),
    UPDATE("update", new Update(), "update {item} [name | desc | amount] {updated}"),
    COMPLETE("complete", new Complete(), "complete {item}"),
    REMOVE("remove", new Remove(), "remove {item}");

    private final Command command;
    private final String name;
    CommandEnum(String name, Command command, String argFormat) {
        this.command = command;
        this.name = name;
        command.setArgFormat(argFormat);
    }

    CommandEnum(String name, Command command) {
        this(name, command, null);
    }

}
