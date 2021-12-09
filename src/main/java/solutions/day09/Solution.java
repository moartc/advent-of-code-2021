package solutions.day09;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Solution {

    public static void main(String[] args) throws IOException {

        URL resource = Solution.class.getResource("/day09.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).toList();
        int[][] locations = lines.stream().map(line -> line.chars().map(c -> c - '0').toArray()).toArray(int[][]::new);
        List<Point> lowestPoints = new ArrayList<>();
        int part1 = 0;
        for (int y = 0; y < locations.length; y++) {
            for (int x = 0; x < locations[0].length; x++) {
                if (isLowestPoint(x, y, locations)) {
                    part1 += (locations[y][x] + 1);
                    lowestPoints.add(new Point(x, y));
                }
            }
        }
        System.out.println("part1 = " + part1);
        int part2 = lowestPoints.stream()
                .map(point -> getBasinSize(point.x, point.y, locations))
                .sorted(Comparator.reverseOrder()).limit(3)
                .reduce(1, (a, b) -> a * b);
        System.out.println("part2 = " + part2);
    }

    private static int getBasinSize(int x, int y, int[][] arr) {
        Set<Point> locations = addLowerPoints(x, y, arr, new HashSet<>());
        return locations.size() + 1;
    }

    private static Set<Point> addLowerPoints(int x, int y, int[][] arr, Set<Point> locations) {
        int current = arr[y][x];
        if (y != arr.length - 1) {
            tryToAddPoints(arr, x, y + 1, current, locations);
        }
        if (y <= arr.length - 1 && y != 0) {
            tryToAddPoints(arr, x, y - 1, current, locations);
        }
        if (x != arr[0].length - 1) {
            tryToAddPoints(arr, x + 1, y, current, locations);
        }
        if (x < arr[0].length - 1 && x != 0) {
            tryToAddPoints(arr, x - 1, y, current, locations);
        }
        return locations;
    }

    private static void tryToAddPoints(int[][] arr, int x, int y, int current, Set<Point> locations) {
        if (arr[y][x] != 9 && arr[y][x] > current) {
            locations.add(new Point(x, y));
            addLowerPoints(x, y, arr, locations);
        }
    }

    private static boolean isLowestPoint(int x, int y, int[][] arr) {
        int current = arr[y][x];
        if (y != arr.length - 1 && arr[y + 1][x] <= current) {
            return false;
        }
        if (y <= arr.length - 1 && y != 0 && arr[y - 1][x] <= current) {
            return false;
        }
        if (x != arr[0].length - 1 && arr[y][x + 1] <= current) {
            return false;
        }
        return x > arr[0].length - 1 || x == 0 || arr[y][x - 1] > current;
    }

    private static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point location = (Point) o;
            return x == location.x && y == location.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}


