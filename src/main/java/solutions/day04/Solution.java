package solutions.day04;

import java.io.InputStream;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
    public static void main(String[] args) {

        InputStream is = Solution.class.getResourceAsStream("/day04.txt");
        Scanner scanner = new Scanner(is);
        String firstLine = scanner.nextLine();
        List<Integer> numbersToCheck =
                Arrays.stream(firstLine.split(",")).map(Integer::valueOf).collect(Collectors.toList());
        List<List<Integer>> boardsLines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String currentLine = scanner.nextLine();
            if (currentLine.isEmpty()) continue;
            List<Integer> tmpList =
                    Arrays.stream(currentLine.split("\s+"))
                            .filter(Predicate.not(String::isEmpty))
                            .map(Integer::valueOf)
                            .collect(Collectors.toList());
            boardsLines.add(tmpList);
        }

        List<Integer> checkedNumbersPart1 = new ArrayList<>();
        for (Integer currentNumberToCheck : numbersToCheck) {
            checkedNumbersPart1.add(currentNumberToCheck);
            List<Integer> winBoards = getAllWinBoards(boardsLines, checkedNumbersPart1);
            if (!winBoards.isEmpty()) {
                int resultPart1 = getResult(boardsLines, winBoards.get(0), checkedNumbersPart1, currentNumberToCheck);
                System.out.println("part1 = " + resultPart1);
                break;
            }
        }
        int numberOfBoards = boardsLines.size() / 5;
        Set<Integer> allWinBoards = new HashSet<>();
        List<Integer> checkedNumbersPart2 = new ArrayList<>();
        int indexOfLastFoundBoard = -1;
        for (Integer numberToCheck : numbersToCheck) {
            checkedNumbersPart2.add(numberToCheck);
            List<Integer> winBoards = getAllWinBoards(boardsLines, checkedNumbersPart2);
            allWinBoards.addAll(winBoards);
            if (allWinBoards.size() == numberOfBoards - 1) {
                indexOfLastFoundBoard = IntStream.range(0, numberOfBoards).filter(i -> !allWinBoards.contains(i))
                        .boxed()
                        .findFirst().get();

            } else if (allWinBoards.size() == numberOfBoards) {
                System.out.println("part2 = " + getResult(boardsLines, indexOfLastFoundBoard, checkedNumbersPart2,
                        numberToCheck));
                return;
            }
        }
    }

    static int getResult(List<List<Integer>> boardsLines, int boardNumber, List<Integer> checkedNumbers,
                         int lastNumber) {
        return boardsLines.subList(boardNumber * 5, boardNumber * 5 + 5)
                .stream()
                .mapToInt(sublist -> sublist
                        .stream()
                        .filter(num -> !checkedNumbers.contains(num) && num != lastNumber)
                        .mapToInt(Integer::intValue)
                        .sum())
                .sum()
                * lastNumber;
    }

    static List<Integer> getAllWinBoards(List<List<Integer>> listAll, List<Integer> checked) {
        List<Integer> winBoards = new ArrayList<>();
        int numberOfBoards = listAll.size() / 5;
        for (int column = 0; column < 5; column++) {
            for (int board = 0; board < numberOfBoards; board++) {
                int rowStart = 5 * board;
                List<Integer> subList = new ArrayList<>();
                for (int row = rowStart; row < rowStart + 5; row++) {
                    subList.add(listAll.get(row).get(column));
                }
                if (checked.containsAll(subList)) {
                    winBoards.add(board);
                }
            }
        }
        int currentColumn = 0;
        for (List<Integer> list : listAll) {
            if (checked.containsAll(list)) {
                winBoards.add(currentColumn / 5);
            }
            currentColumn++;
        }
        return winBoards;
    }
}

