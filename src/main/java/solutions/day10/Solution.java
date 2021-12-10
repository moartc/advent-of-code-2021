package solutions.day10;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Solution {

    public static void main(String[] args) throws IOException {

        URL resource = Solution.class.getResource("/day10.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).toList();

        List<Character> opens = List.of('(', '{', '[', '<');
        List<Character> closes = List.of(')', '}', ']', '>');
        int[] scores = new int[]{0, 0, 0, 0};
        List<Integer> characterPoints = List.of(3, 1197, 57, 25137);
        List<String> incompleteLines = new ArrayList<>();
        for (String line : lines) {
            Stack<Character> stack = new Stack<>();
            boolean corrupted = false;
            for (char c : line.toCharArray()) {
                if (opens.contains(c)) {
                    stack.add(c);
                } else {
                    int index = closes.indexOf(c);
                    char expectedOpen = opens.get(index);
                    if (stack.pop() != expectedOpen) {
                        corrupted = true;
                        scores[index]++;
                        break;
                    }
                }
            }
            if (!corrupted) {
                incompleteLines.add(line);
            }
        }
        int part1 = 0;
        for (int i = 0; i < 4; i++) {
            part1 += scores[i] * characterPoints.get(i);
        }
        System.out.println("part1 = " + part1);

        List<Integer> characterPointsPart2 = List.of(1, 3, 2, 4);
        List<Long> allScores = new ArrayList<>();
        for (String line : incompleteLines) {
            Stack<Character> stack = new Stack<>();
            for (char c : line.toCharArray()) {
                if (opens.contains(c)) {
                    stack.add(c);
                } else {
                    if (stack.peek() == opens.get(closes.indexOf(c))) {
                        stack.pop();
                    }
                }
            }
            long scoreForLine = 0;
            while (!stack.isEmpty()) {
                scoreForLine = scoreForLine * 5 + characterPointsPart2.get(opens.indexOf(stack.pop()));
            }
            allScores.add(scoreForLine);
        }
        Collections.sort(allScores);
        long part2 = allScores.get(allScores.size() / 2);
        System.out.println("part2 = " + part2);
    }
}


