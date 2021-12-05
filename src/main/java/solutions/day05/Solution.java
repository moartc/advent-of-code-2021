package solutions.day05;

import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
    public static void main(String[] args) {

        InputStream is = Solution.class.getResourceAsStream("/day05.txt");
        Scanner scanner = new Scanner(is);
        List<String> input = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String currentLine = scanner.nextLine();
            input.add(currentLine);
        }

        List<Point> pointsPart1 = readAllPoints(input, false);
        long counterPart1 = countCoveredPoints(pointsPart1);
        System.out.println("part1 = " + counterPart1);

        List<Point> pointsPart2 = readAllPoints(input, true);
        long counterPart2 = countCoveredPoints(pointsPart2);
        System.out.println("part2 = " + counterPart2);
    }

    public static List<Point> readAllPoints(List<String> input, boolean withDiagonal) {
        return input.stream().flatMap(s -> {
            String[] splitPoints = s.split(" -> ");
            String[] firstPoint = splitPoints[0].split(",");
            String[] secondPoint = splitPoints[1].split(",");
            int x1 = Integer.parseInt(firstPoint[0]);
            int y1 = Integer.parseInt(firstPoint[1]);
            int x2 = Integer.parseInt(secondPoint[0]);
            int y2 = Integer.parseInt(secondPoint[1]);
            return getPointsBetweenCoordinates(x1, y1, x2, y2, withDiagonal).stream();
        }).collect(Collectors.toList());
    }

    public static long countCoveredPoints(List<Point> points) {
        return points
                .stream()
                .collect(Collectors.toMap(Function.identity(), v -> 1,
                        Integer::sum))
                .values()
                .stream()
                .filter(integer -> integer >= 2)
                .count();
    }

    public static List<Point> getPointsBetweenCoordinates(int x1, int y1, int x2, int y2, boolean withDiagonal) {
        List<Point> points = new ArrayList<>();
        if (x1 == x2) {
            return IntStream.rangeClosed(Math.min(y1, y2), Math.max(y1, y2)).mapToObj(i -> new Point(x1, i)).toList();
        } else if (y1 == y2) {
            return IntStream.rangeClosed(Math.min(x1, x2), Math.max(x1, x2)).mapToObj(i -> new Point(i, y1)).toList();
        } else if (withDiagonal && isDiagonal(x1, y1, x2, y2)) {
            boolean x1Greater = x1 > x2;
            int startX = x1Greater ? x2 : x1;
            int endX = x1Greater ? x1 : x2;
            int startY = x1Greater ? y2 : y1;
            int endY = x1Greater ? y1 : y2;
            while (startX <= endX) {
                points.add(new Point(startX, startY));
                startX++;
                if (startY > endY) {
                    startY--;
                } else {
                    startY++;
                }
            }
        }
        return points;
    }

    public static boolean isDiagonal(int x1, int y1, int x2, int y2) {
        float angle = (float) Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));
        if (angle < 0) {
            angle += 360;
        }
        return angle % 45 == 0;
    }
}

