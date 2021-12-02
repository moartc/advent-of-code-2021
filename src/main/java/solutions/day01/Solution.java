package solutions.day01;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        InputStream is = Solution.class.getResourceAsStream("/day01.txt");
        Scanner scanner = new Scanner(is);
        List<Integer> input = new ArrayList<>();

        while (scanner.hasNextInt()) {
            input.add(scanner.nextInt());
        }
        int increase = 0;
        for (int i = 0; i < input.size() - 1; i++) {
            if (input.get(i) < input.get(i + 1)) {
                increase++;
            }
        }
        System.out.println("part1 = " + increase);

        List<Integer> subsets = new ArrayList<>();
        for (int i = 0; i < input.size() - 2; i++) {
            int sum = input.subList(i, i + 3).stream().mapToInt(Integer::intValue).sum();
            subsets.add(sum);
        }
        increase = 0;
        for (int i = 0; i < subsets.size() - 1; i++) {
            if (subsets.get(i) < subsets.get(i + 1)) {
                increase++;
            }
        }
        System.out.println("part2 = " + increase);

    }
}
