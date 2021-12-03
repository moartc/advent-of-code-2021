package solutions.day03;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        InputStream is = Solution.class.getResourceAsStream("/day03.txt");
        Scanner scanner = new Scanner(is);
        List<String> input = new ArrayList<>();
        while (scanner.hasNextLine()) {
            input.add(scanner.nextLine());
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.get(0).length(); i++) {
            int zeros = 0;
            int ones = 0;
            for (String line : input) {
                if (line.charAt(i) == '1') {
                    ones++;
                } else {
                    zeros++;
                }
            }
            result.append(ones > zeros ? '1' : '0');
        }
        String result2 = result.toString();
        result2 = result2.replaceAll("0", "x");
        result2 = result2.replaceAll("1", "0");
        result2 = result2.replaceAll("x", "1");
        int int1 = Integer.valueOf(result.toString(), 2);
        int int2 = Integer.valueOf(result2, 2);
        System.out.println("part1 = " + int1 * int2);

        List<String> more = new ArrayList<>(input);
        List<String> less = new ArrayList<>(input);
        int stringLength = input.get(0).length();
        for (int i = 0; i < stringLength; i++) {
            char toCompare = getMoreFrequentCharOnPosition(more, i);
            int finalI = i;
            more.removeIf(s -> s.charAt(finalI) != toCompare);
            if (more.size() == 1) {
                break;
            }
        }
        for (int i = 0; i < less.get(0).length(); i++) {
            int finalI = i;
            char toCompare = getMoreFrequentCharOnPosition(less, i);
            less.removeIf(s -> s.charAt(finalI) == toCompare);
            if (less.size() == 1) {
                break;
            }
        }
        System.out.println("part2 = " + Integer.valueOf(more.get(0), 2) * Integer.valueOf(less.get(0), 2));
    }

    private static char getMoreFrequentCharOnPosition(List<String> string, int position) {
        int zeros = 0;
        int ones = 0;
        for (String line : string) {
            if (line.charAt(position) == '1') {
                ones++;
            } else {
                zeros++;
            }
        }
        return ones >= zeros ? '1' : '0';
    }
}

