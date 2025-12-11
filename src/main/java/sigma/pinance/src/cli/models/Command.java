package sigma.pinance.src.cli.models;

import lombok.Data;

import java.util.Objects;
import java.util.Scanner;

@Data
public abstract class Command {
    private String argFormat;

    public abstract void execute(CommandInput commandInput, Scanner scanner);

    public void setArgFormat(String desc) {
        argFormat = (Objects.isNull(desc) || desc.isBlank())
                ? "" : desc;
    }
}
