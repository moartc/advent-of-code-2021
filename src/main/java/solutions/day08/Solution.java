package solutions.day08;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Solution {

    public static void main(String[] args) throws IOException {

        URL resource = Solution.class.getResource("/day08.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).toList();
        Map<String, String> input = lines.stream().map(line -> line.split("\\|"))
                .collect(Collectors.toMap(line -> line[0].trim(), line -> line[1].trim()));
        System.out.println("part1 = " + countFirstPartSolution(input.values().stream().toList()));

        int resultPart2 = input.entrySet().stream().map(entry -> {
            List<String> stringIntegerMap = getPatternsMap(entry.getKey());
            return Integer.valueOf(Arrays.stream(entry.getValue().split(" "))
                    .map(value -> String.valueOf(getValue(value, stringIntegerMap)))
                    .collect(Collectors.joining("")));
        }).mapToInt(Integer::intValue).sum();

        System.out.println("part2 = " + resultPart2);

    }

    static int getValue(String num, List<String> numberList) {
        for (String numberFromList : numberList) {
            if (numberFromList.length() == num.length()) {
                char[] numToCheckArray = numberFromList.toCharArray();
                char[] numberToMap = num.toCharArray();
                Arrays.sort(numToCheckArray);
                Arrays.sort(numberToMap);
                if (Arrays.equals(numToCheckArray, numberToMap)) {
                    return numberList.indexOf(numberFromList);
                }
            }
        }
        return -1;
    }

    static List<String> getPatternsMap(String line) {

        List<String> strings = Arrays.asList(line.split(" "));
        Collections.sort(strings, Comparator.comparing(String::length));
        List<String> stringToInt = new ArrayList<>(Collections.nCopies(10, ""));
        stringToInt.set(1, strings.get(0));
        stringToInt.set(7, strings.get(1));
        stringToInt.set(4, strings.get(2));
        stringToInt.set(8, strings.get(9));

        String string6 = strings.stream().filter(string -> stringNumberMatch(string, 6, 1, 1, stringToInt)).findFirst().get();
        stringToInt.set(6, string6);
        String string5 = strings.stream().filter(string -> stringNumberMatch(string, 5, 6, 1, stringToInt)).findFirst().get();
        stringToInt.set(5, string5);
        String string2 = strings.stream().filter(string -> stringNumberMatch(string, 5, 5, 2, stringToInt)).findFirst().get();
        stringToInt.set(2, string2);
        String string3 = strings.stream().filter(string -> stringNumberMatch(string, 5, 5, 1, stringToInt)).findFirst().get();
        stringToInt.set(3, string3);
        String string9 = strings.stream().filter(string -> stringNumberMatch(string, 6, 5, 0, stringToInt)).findFirst().get();
        stringToInt.set(9, string9);
        String string0 = strings.stream().filter(string -> !stringToInt.contains(string)).findFirst().get();
        stringToInt.set(0, string0);
        return stringToInt;
    }

    static boolean stringNumberMatch(String string, int expectedLength, int numberToCompare, int expectedDiff, List<String> stringToInt) {
        return string.length() == expectedLength
                && !stringToInt.contains(string)
                && getNbOfDifferentSegments(stringToInt.get(numberToCompare), string) == expectedDiff;

    }

    static long getNbOfDifferentSegments(String firstNumber, String secondNumber) {
        return firstNumber.chars().filter(c -> secondNumber.indexOf(c) == -1).count();
    }

    static long countFirstPartSolution(List<String> input) {
        return input.stream().mapToLong(line -> Arrays.stream(line.split(" "))
                .mapToInt(String::length).filter(value -> List.of(2, 3, 4, 7).contains(value))
                .count()).sum();
    }
}
