package solutions.day02;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        InputStream is = Solution.class.getResourceAsStream("/day02.txt");
        Scanner scanner = new Scanner(is);
        List<String> input = new ArrayList<>();

        while (scanner.hasNextLine()) {
            input.add(scanner.nextLine());
        }
        int horizontal = 0;
        int depth = 0;
        for (String line : input) {
            String command = line.substring(0, line.indexOf(" "));
            String number = line.substring(line.indexOf(" ") + 1);
            int intNumber = Integer.parseInt(number);
            switch (command) {
                case "forward" -> horizontal += intNumber;
                case "down" -> depth += intNumber;
                case "up" -> depth -= intNumber;
            }
        }
        System.out.println("part1 = " + horizontal * depth);

        horizontal = 0;
        depth = 0;
        int aim = 0;
        for (String line : input) {
            String command = line.substring(0, line.indexOf(" "));
            String number = line.substring(line.indexOf(" ") + 1);
            int intNumber = Integer.parseInt(number);
            switch (command) {
                case "forward" -> {
                    horizontal += intNumber;
                    depth += aim * intNumber;
                }
                case "down" -> aim += intNumber;
                case "up" -> aim -= intNumber;
            }
        }
        System.out.println("part2 = " + horizontal * depth);
    }
}
