package Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    // Define pattern for registration numbers
    private static final Pattern REG_PATTERN = Pattern.compile(
            "\\b([A-Z]{2}\\d{2} ?[A-Z]{3}|[A-Z]\\d{3} ?[A-Z]{3}|[A-Z]{3} ?\\d{3}[A-Z]|[A-Z]{3} ?\\d{3}|\\d{3} ?[A-Z]{3})\\b");

    // Extracts all registration numbers from a given line and returns them as a list
    public static List<String> extractRegistrationNumbers(String line) {
        List<String> registrationNumbers = new ArrayList<>();
        Matcher matcher = REG_PATTERN.matcher(line);

        while (matcher.find()) {
            registrationNumbers.add(matcher.group());
        }
        return registrationNumbers;
    }
}