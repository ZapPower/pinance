package sigma.pinance.src.cli.utils;

public class TextUtils {
    public static String color(String str, TextColor color) {
        return color + str + TextColor.RESET;
    }
}
