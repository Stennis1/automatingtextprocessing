package com.example.automatingtextprocessing.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class RegexUtils {

    public static List<String> findMatches(String input, String regex) {
        List<String> matches = new ArrayList<>();
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(input);
            while (matcher.find()) {
                matches.add(matcher.group());
            }
        } catch (PatternSyntaxException e) {
            matches.add("Invalid regex: " + e.getDescription());
        }
        return matches;
    }

    public static String replaceMatches(String input, String regex, String replacement) {
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(input);
            return matcher.replaceAll(replacement);
        } catch (PatternSyntaxException e) {
            return "Error: " + e.getDescription();
        }
    }
}
