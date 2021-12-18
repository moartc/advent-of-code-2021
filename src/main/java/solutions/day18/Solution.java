package solutions.day18;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Solution {

    public static void main(String[] args) throws IOException {

        URL resource = solutions.day16.Solution.class.getResource("/day18.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).filter(Predicate.not(String::isEmpty)).toList();
        System.out.println("part1 = " + getFirstPartAnswer(lines));
        System.out.println("part2 = " + getSecondPartAnswer(lines));
    }

    static int getFirstPartAnswer(List<String> input) {
        String finalStringForPart1 = createFinalString(input);
        return computeMagnitude(finalStringForPart1);
    }

    static int getSecondPartAnswer(List<String> input) {
        return IntStream.range(0, input.size()).boxed()
                .flatMap(i -> input.stream().map(s -> getFirstPartAnswer(List.of(input.get(i), s))))
                .mapToInt(Integer::valueOf).max().getAsInt();
    }

    static int computeMagnitude(String input) {
        Pattern pattern = Pattern.compile("(\\[\\d+,\\d+])");
        while (input.contains("[")) {
            Matcher matcher = pattern.matcher(input);
            if (matcher.find()) {
                String group = matcher.group(1);
                int int1 = Integer.parseInt(group.substring(1, group.indexOf(",")));
                int int2 = Integer.parseInt(group.substring(group.indexOf(",") + 1, group.length() - 1));
                int result = 3 * int1 + 2 * int2;
                input = input.replaceFirst(Pattern.quote(group), String.valueOf(result));
            }
        }
        return Integer.parseInt(input);

    }

    static String createFinalString(List<String> input) {
        String result = input.get(0);
        for (int i = 1; i < input.size(); i++) {
            String newToAdd = input.get(i);
            result = addTwo(result, newToAdd);
            while (true) {
                String beforeExplode = result;
                result = explode(result);
                if (!result.equals(beforeExplode)) {
                    continue;
                }
                result = split(result);
                if (beforeExplode.equals(result)) {
                    break;
                }
            }
        }
        return result;
    }

    static String explode(String string) {
        int depth = 0;
        for (int i = 0; i < string.length(); i++) {
            char charToCheck = string.charAt(i);
            if (charToCheck == '[') {
                depth++;
            } else if (charToCheck == ']') {
                depth--;
            } else if (Character.isDigit(charToCheck) && depth > 4) {
                int commaIndex = string.indexOf(",", i);
                if (!Character.isDigit(string.charAt(commaIndex + 1))) {
                    continue;
                }
                String firstNumberString = string.substring(i, string.indexOf(",", i));
                String secondNumberString = string.substring(commaIndex + 1, string.indexOf("]", commaIndex));
                int firstNumberInt = Integer.parseInt(firstNumberString);
                int secondNumberInt = Integer.parseInt(secondNumberString);
                int lengthBefore = string.length();
                string = addToLeftChar(i, firstNumberInt, string);
                int indexChange = string.length() - lengthBefore;
                string = addToRightChar(i + 2 + (firstNumberString.length() - 1) + secondNumberString.length() + indexChange, secondNumberInt, string);
                String substring = string.substring(i - 1 + indexChange, i + 2 + indexChange + firstNumberString.length() + secondNumberString.length());
                return replaceFirstFromIndexWithZero(string, i - 1, Pattern.quote(substring));
            }
        }
        return string;
    }

    static String split(String string) {
        StringBuilder sb = new StringBuilder(string);
        for (int i = 0; i < string.length() - 1; i++) {
            if (Character.isDigit(string.charAt(i)) && Character.isDigit(string.charAt(i + 1))) {
                String currentNumberString = string.substring(i, getCommaOrBracketIndex(string, i));
                int currentNumberInt = Integer.parseInt(currentNumberString);
                String newPair = "[" + currentNumberInt / 2 + "," + (currentNumberInt + 1) / 2 + "]";
                sb.replace(i, i + currentNumberString.length(), newPair);
                return sb.toString();
            }
        }
        return sb.toString();
    }

    static String addTwo(String first, String second) {
        return String.format("[%s,%s]", first, second);
    }

    static String addToLeftChar(int charIndex, int valueToAdd, String string) {
        StringBuilder sb = new StringBuilder(string);
        for (int i = charIndex - 1; i >= 0; i--) {
            if (Character.isDigit(string.charAt(i))) {
                int numberBeginningIndex = i;
                while (Character.isDigit(string.charAt(numberBeginningIndex))) {
                    numberBeginningIndex--;
                }
                String currentNumberString = string.substring(numberBeginningIndex + 1, i + 1);
                sb.delete(numberBeginningIndex + 1, i + 1);
                sb.insert(numberBeginningIndex + 1, Integer.parseInt(currentNumberString) + valueToAdd);
                return sb.toString();
            }
        }
        return sb.toString();
    }

    static String addToRightChar(int charIndex, int valueToAdd, String string) {
        StringBuilder sb = new StringBuilder(string);
        for (int i = charIndex + 1; i < string.length(); i++) {
            if (Character.isDigit(string.charAt(i))) {
                String currentNumberString = string.substring(i, getCommaOrBracketIndex(string, i));
                sb.delete(i, i + currentNumberString.length());
                sb.insert(i, Integer.parseInt(currentNumberString) + valueToAdd);
                return sb.toString();
            }
        }
        return sb.toString();
    }

    static int getCommaOrBracketIndex(String string, int startIndex) {
        int commaIndex = string.indexOf(",", startIndex);
        int bracketIndex = string.indexOf("]", startIndex);
        if (commaIndex == -1) {
            return bracketIndex;
        } else {
            return Math.min(commaIndex, bracketIndex);
        }
    }

    private static String replaceFirstFromIndexWithZero(String str, int from, String regex) {
        String prefix = str.substring(0, from);
        return prefix + str.substring(from).replaceFirst(regex, "0");
    }
}



