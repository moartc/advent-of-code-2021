package solutions.day13;

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
        Set<Dot> dots = new HashSet<>();
        for (String line : lines) {
            if (line.startsWith("fold")) {
                folds.add(line.substring(line.indexOf("g ") + 2));
            } else if (!line.isEmpty() && Character.isDigit(line.charAt(0))) {
                String[] split = line.split(",");
                dots.add(new Dot(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
            }
        }

        Set<Dot> dotSetPart1 = new HashSet<>(fold(new HashSet<>(dots), folds.get(0)));
        System.out.println("part1 = " + dotSetPart1.size());

        for (String fold : folds) {
            dots = fold(dots, fold);
        }
        int maxX = dots.stream().max(Comparator.comparingInt(o -> o.x)).get().x;
        int maxY = dots.stream().max(Comparator.comparingInt(o -> o.y)).get().y;
        boolean[][] arrayToPrint = new boolean[maxY + 1][maxX + 1];
        for (Dot dot : dots) {
            arrayToPrint[dot.y][dot.x] = true;
        }
        System.out.println("part2:\n");
        printArray(arrayToPrint);
    }

    private static Set<Dot> fold(Set<Dot> dots, String instruction) {
        char foldChar = instruction.charAt(0);
        int foldValue = Integer.parseInt(instruction.substring(2));
        for (Dot dot : dots) {
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

class Dot {

    int x;
    int y;

    public Dot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dot dot = (Dot) o;
        return x == dot.x && y == dot.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

