package sigma.pinance.src.cli.utils;

import java.util.ArrayList;

public final class ArgParser {
    private static ArrayList<String> parseArguments(char[] input) {
        ArrayList<String> args = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < input.length; i++) {
            char c = input[i];

            char prev = (i == 0) ? ' ' : input[i - 1];
            char next = (i == input.length - 1) ? ' ' : input[i + 1];

            if (c == '"') {
                // A quote is "syntactic" (starts/ends a quoted section)
                // if it has a space (or boundary) on either side.
                boolean syntacticQuote = (prev == ' ' || next == ' ');

                if (syntacticQuote) {
                    inQuotes = !inQuotes;    // toggle quote mode
                    // Do NOT append this quote character
                } else {
                    // Literal quote, just part of the argument
                    current.append(c);
                }
            } else if (c == ' ') {
                if (inQuotes) {
                    // space inside quotes is kept
                    current.append(c);
                } else {
                    // space outside quotes ends the current argument
                    if (!current.isEmpty()) {
                        args.add(current.toString());
                        current.setLength(0);
                    }
                    // multiple spaces just collapse; nothing else to do
                }
            } else {
                current.append(c);
            }
        }

        // Add last argument if any
        if (!current.isEmpty()) {
            args.add(current.toString());
        }

        return args;
    }

    private static String getArgumentString(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }
        if (sb.isEmpty()) return "";
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public static ArrayList<String> parse(String[] command) {
        return parseArguments(getArgumentString(command).toCharArray());
    }

    private ArgParser() {}
}
