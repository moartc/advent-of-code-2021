package solutions.day13;

import utils.Point;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Solution {

    public static void main(String[] args) throws IOException {

        URL resource = Solution.class.getResource("/day13.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).toList();

        List<String> folds = new ArrayList<>();
        Set<Point> dots = new HashSet<>();
        for (String line : lines) {
            if (line.startsWith("fold")) {
                folds.add(line.substring(line.indexOf("g ") + 2));
            } else if (!line.isEmpty() && Character.isDigit(line.charAt(0))) {
                String[] split = line.split(",");
                dots.add(new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
            }
        }

        Set<Point> dotSetPart1 = new HashSet<>(fold(new HashSet<>(dots), folds.get(0)));
        System.out.println("part1 = " + dotSetPart1.size());

        for (String fold : folds) {
            dots = fold(dots, fold);
        }
        int maxX = dots.stream().max(Comparator.comparingInt(o -> o.x)).get().x;
        int maxY = dots.stream().max(Comparator.comparingInt(o -> o.y)).get().y;
        boolean[][] arrayToPrint = new boolean[maxY + 1][maxX + 1];
        for (Point dot : dots) {
            arrayToPrint[dot.y][dot.x] = true;
        }
        System.out.println("part2:\n");
        printArray(arrayToPrint);
    }

    private static Set<Point> fold(Set<Point> dots, String instruction) {
        char foldChar = instruction.charAt(0);
        int foldValue = Integer.parseInt(instruction.substring(2));
        for (Point dot : dots) {
            if (foldChar == 'y') {
                if (dot.y > foldValue) {
                    dot.y = (dot.y - 2 * (dot.y - foldValue));
                }
            } else if (dot.x > foldValue) {
                dot.x = (dot.x - 2 * (dot.x - foldValue));
            }
        }
        return dots;
    }

    private static void printArray(boolean[][] array) {
        for (boolean[] chars : array) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < array[0].length; j++) {
                sb.append(chars[j] ? "#" : " ");
            }
            System.out.println(sb);
        }
    }
}
