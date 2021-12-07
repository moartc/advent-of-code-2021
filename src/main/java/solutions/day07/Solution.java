package solutions.day07;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {

    public static void main(String[] args) {

        InputStream is = Solution.class.getResourceAsStream("/day07.txt");
        Scanner scanner = new Scanner(Objects.requireNonNull(is));
        List<Integer> input = Arrays.stream(scanner.nextLine().split(","))
                .map(Integer::parseInt).collect(Collectors.toList());

        System.out.println("part1 = " + findBestPosition(input, true));
        System.out.println("part2 = " + findBestPosition(input, false));
    }

    static int findBestPosition(List<Integer> positions, boolean withConstantRate) {
        return IntStream.rangeClosed(Collections.min(positions), Collections.max(positions))
                .map(i -> withConstantRate ? getFuelCostWithConstantRate(i, positions) :
                        getFuelCostWithoutConstantRate(i, positions))
                .min()
                .getAsInt();
    }

    static int getFuelCostWithConstantRate(int startPosition, List<Integer> positions) {
        return positions.stream().map(position -> Math.abs(startPosition - position))
                .mapToInt(Integer::intValue)
                .sum();
    }

    static int getFuelCostWithoutConstantRate(int startPosition, List<Integer> positions) {
        return positions.stream().map(position -> IntStream.rangeClosed(1, (Math.abs(startPosition - position))).sum())
                .mapToInt(Integer::intValue)
                .sum();
    }
}
