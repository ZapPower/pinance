package sigma.pinance.src.cli.utils;

public enum TextColor {
    RESET("\u001B[0m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    ORANGE("\u001B[38;5;208m");

    private final String ansi;
    TextColor(String ansi) {
        this.ansi = ansi;
    };

    @Override
    public String toString() {
        return ansi;
    }
}
