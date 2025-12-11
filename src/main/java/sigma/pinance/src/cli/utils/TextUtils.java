package sigma.pinance.src.cli.utils;

/**
 * Utilities to perform operations on any text.
 */
public final class TextUtils {
    public static String color(String str, TextColor color) {
        return color + str + TextColor.RESET;
    }

    private TextUtils() {}
}
