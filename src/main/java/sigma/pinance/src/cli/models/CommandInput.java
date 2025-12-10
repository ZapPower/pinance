package sigma.pinance.src.cli.models;

import java.util.ArrayList;

public record CommandInput(String commandName, ArrayList<String> args) {
}
