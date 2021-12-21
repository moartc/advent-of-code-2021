package solutions.day21;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {

    private static final Map<Universe, Pair<Long, Long>> universeToResult = new HashMap<>();

    public static void main(String[] args) throws IOException {

        URL resource = solutions.day16.Solution.class.getResource("/day21.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).toList();

        int firstStartingPosition = Integer.parseInt(lines.get(0).substring(lines.get(0).indexOf(":") + 2));
        int secondStartingPosition = Integer.parseInt(lines.get(1).substring(lines.get(1).indexOf(":") + 2));
        System.out.println("part1 = " + answerPart1(firstStartingPosition, secondStartingPosition));
        System.out.println("part2 = " + answerPart2(firstStartingPosition, secondStartingPosition));
    }

    static long answerPart1(int firstPlayerPosition, int secondPlayerPosition) {

        long score1 = 0;
        long score2 = 0;
        int num = 0;
        while (true) {
            firstPlayerPosition = getNextPosition(firstPlayerPosition, getNextRollNumberSum(num));
            score1 += firstPlayerPosition;
            if (score1 >= 1000)
                break;
            num++;
            secondPlayerPosition = getNextPosition(secondPlayerPosition, getNextRollNumberSum(num));
            score2 += secondPlayerPosition;
            num++;
        }
        return (num + 1) * 3 * Math.min(score1, score2);
    }

    static long answerPart2(int firstStartingPosition, int secondStartingPosition) {

        Pair<Long, Long> longLongPair = playGame(0, firstStartingPosition, 0, secondStartingPosition, true, 0, 0);
        return Math.max(longLongPair.getLeft(), longLongPair.getRight());
    }

    static Pair<Long, Long> playGame(long score1, int position1, long score2, int position2, boolean firstPlayerTurn, int round, int currentRoll) {
        if (score1 >= 21) {
            return Pair.of(1L, 0L);
        }
        if (score2 >= 21) {
            return Pair.of(0L, 1L);
        }
        Universe universe = new Universe(score1, position1, score2, position2, firstPlayerTurn, round, currentRoll);
        Pair<Long, Long> universeResult = universeToResult.get(universe);
        if (universeResult != null) {
            return universeResult;
        } else {
            List<Pair<Long, Long>> results = new ArrayList<>();
            for (int i : List.of(1, 2, 3)) {
                if (round < 2) {
                    currentRoll += 1;
                    results.add(playGame(score1, position1, score2, position2, firstPlayerTurn, round + 1, currentRoll));
                } else {
                    int newPosition = getNextPosition(firstPlayerTurn ? position1 : position2, currentRoll + i);
                    if (firstPlayerTurn) {
                        results.add(playGame(score1 + newPosition, newPosition, score2, position2, false, 0, 0));
                    } else {
                        results.add(playGame(score1, position1, score2 + newPosition, newPosition, true, 0, 0));
                    }
                }
            }
            Pair<Long, Long> sum = sumListOfPairs(results);
            universeToResult.put(universe, Pair.of(sum.getLeft(), sum.getRight()));
            return sum;
        }
    }

    private static Pair<Long, Long> sumListOfPairs(List<Pair<Long, Long>> pairList) {
        long first = 0;
        long second = 0;
        for (Pair<Long, Long> pair : pairList) {
            first += pair.getLeft();
            second += pair.getRight();
        }
        return Pair.of(first, second);
    }

    static int getNextPosition(int start, long move) {
        long pos = (start + move) % 10;
        return pos == 0 ? 10 : (int) pos;
    }

    private static long getNextRollNumberSum(long number) {
        return 9 * number + 6;
    }

    record Universe(long score1, int position1, long score2, int position2, boolean firstPlayerTurn, int round,
                    int currentRoll) {
    }
}





