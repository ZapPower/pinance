package sigma.pinance.src.cli.utils.parsers;

import sigma.pinance.src.core.exceptions.AppException;

import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * Parser that parses dates from raw input.
 */
public class DateParser {
    /**
     * Attempt to coerce and parse input into a LocalDate
     *
     * @param input The raw input
     * @param today The current date (for defaulting)
     * @return The interpreted LocalDate
     */
    public static LocalDate parseFuzzy(String input, LocalDate today) {
        if (input == null || input.trim().isEmpty()) {
            return today;
        }

        String normalized = input.trim()
                .replace('-', '/')
                .replace(':', '/');

        String[] parts = normalized.split("/");

        if (parts.length < 2 || parts.length > 3) {
            throw new IllegalArgumentException("Invalid date format: " + input);
        }

        int month = parseInt(parts[0], "month", input);
        int day   = parseInt(parts[1], "day", input);
        int year;

        if (parts.length == 2) {
            year = today.getYear();
        } else {
            String yearStr = parts[2];
            if (yearStr.length() == 2) {
                year = 2000 + parseInt(yearStr, "year", input);
            } else {
                year = parseInt(yearStr, "year", input);
            }
        }

        try {
            return LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            throw new AppException("Invalid date value: " + input);
        }
    }

    private static int parseInt(String s, String field, String originalInput) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new AppException(
                    "Invalid " + field + " in date: " + originalInput
            );
        }
    }


    private DateParser() {}
}
