package solutions.day06;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Stream;

public class Solution {

    static Map<Integer, Long> dayToProducedNumbers = new ConcurrentSkipListMap<>();

    public static void main(String[] args) {

        InputStream is = Solution.class.getResourceAsStream("/day06.txt");
        Scanner scanner = new Scanner(Objects.requireNonNull(is));
        List<Integer> input = Stream.of(scanner.nextLine().split(",")).map(Integer::valueOf).toList();
        int nbOfDays = 80;

        long answerPart1 = input.size();
        for (Integer number : input) {
            answerPart1 += getNumberProducedLanternfishes(number, nbOfDays);
        }
        System.out.println("part1 = " + answerPart1);

        nbOfDays = 256;
        long answerPart2 = input.size();
        for (Integer number : input) {
            answerPart2 += getNumberProducedLanternfishes(number, nbOfDays);
        }
        System.out.println("part2 = " + answerPart2);
    }

    static long getNumberProducedLanternfishes(int initialDay, int daysToEnd) {
        long result = 0;
        List<Integer> productionDays = new ArrayList<>();
        for (int day = 1; day <= daysToEnd; day++) {
            if (initialDay == 0) {
                result++;
                productionDays.add(day);
                initialDay = 6;
            } else {
                initialDay--;
            }
        }
        result += productionDays.stream()
                .map(day -> dayToProducedNumbers.computeIfAbsent(daysToEnd - day,
                        dayToAdd -> getNumberProducedLanternfishes(8, dayToAdd))
                )
                .mapToLong(Long::longValue)
                .sum();
        return result;
    }
}
