package solutions.day08;

import java.io.InputStream;
import java.util.*;

public class Solution {

    public static void main(String[] args) {

        InputStream is = Solution.class.getResourceAsStream("/day08.txt");
        Scanner scanner = new Scanner(Objects.requireNonNull(is));

        List<String> lines = new ArrayList<>();


        while (scanner.hasNextLine()) {
            String currentLine = scanner.nextLine();
            lines.add(currentLine);
        }

        List<String> stringsPart1 = new ArrayList<>();
        List<String> stringsPart2 = new ArrayList<>();

        for (String line : lines) {
            String[] split = line.split(" \\| ");
            String trim = split[0].trim();
            stringsPart1.add(trim);
            String trim1 = split[1].trim();
            stringsPart2.add(trim1);
        }

        System.out.println("part1 = " + countUnique(stringsPart2));
        int result = 0;
        for (int i = 0; i < stringsPart1.size(); i++) {
            String p1 = stringsPart1.get(i);
            String p2 = stringsPart2.get(i);
            Map<String, Integer> stringIntegerMap = mapPatterns(p1);

            StringBuilder sb = new StringBuilder();
            for (String str : p2.split(" ")) {
                sb.append(getValue(str, stringIntegerMap));
            }
            result += Integer.parseInt(sb.toString());
        }
        System.out.println("part2 = " + result);

    }

    static int getValue(String num, Map<String, Integer> map) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key.length() == num.length()) {
                char[] keyArr = key.toCharArray();
                char[] numArr = num.toCharArray();
                Arrays.sort(keyArr);
                Arrays.sort(numArr);

                if (Arrays.equals(keyArr, numArr)) {
                    return entry.getValue();
                }
            }
        }
        return -1;
    }

    static Map<String, Integer> mapPatterns(String line) {
        Map<String, Integer> stringToInt = new HashMap<>();
        Map<Integer, String> intToString = new HashMap<>();
        String[] s = line.split(" ");
        List<String> strings = Arrays.asList(s);
        Collections.sort(strings, Comparator.comparing(String::length));
        stringToInt.put(strings.get(0), 1);
        intToString.put(1, strings.get(0));
        stringToInt.put(strings.get(1), 7);
        intToString.put(7, strings.get(1));
        stringToInt.put(strings.get(2), 4);
        intToString.put(4, strings.get(2));
        stringToInt.put(strings.get(9), 8);
        intToString.put(8, strings.get(9));
        for (String str : strings) {         // for 6
            if (str.length() == 6 && getDiffNum(strings.get(0), str) == 1) {
                stringToInt.put(str, 6);
                intToString.put(6, str);
            }
        }
        for (String str : strings) {         // for 5
            if (str.length() == 5 && getDiffNum(intToString.get(6), str) == 1) {// diff with 1 is 0
                stringToInt.put(str, 5);
                intToString.put(5, str);
        }
    }
        for(
    String str :strings)

    {         // for 2
        if (str.length() == 5 && !stringToInt.containsKey(str)) {
            if (getDiffNum(intToString.get(5), str) == 2) {// diff with 1 is 0
                stringToInt.put(str, 2);
                intToString.put(2, str);
            } else {
                stringToInt.put(str, 3);
                intToString.put(3, str);
            }
        }
    }
        for(
    String str :strings)

    {         // for 0 & 9
        if (str.length() == 6 && !stringToInt.containsKey(str)) {
            if (getDiffNum(intToString.get(5), str) == 0) {
                stringToInt.put(str, 9);
                intToString.put(9, str);
            } else {
                stringToInt.put(str, 0);
                intToString.put(0, str);
            }
        }
    }
        return stringToInt;
}

    static int getDiffNum(String num1, String num2) {
        int diff = 0;
        for (char c : num1.toCharArray()) {
            if (num2.indexOf(c) == -1) {
                diff++;
            }
        }
        return diff;
    }

    static int countUnique(List<String> input) {
        int counter = 0;
        for (String line : input) {
            for (String string : line.split(" ")) {
                int length = string.length();
                if (length == 2 || length == 4 || length == 3 || length == 7) {
                    counter++;
                }
            }
        }
        return counter;

    }
}
