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
        System.out.println("part1 = " + part1 + ", " + (550 == part1));
        List<Integer> sizes = new ArrayList<>();
        for (Point l : lowestPoints) {
            sizes.add(getBasinSize(l.x, l.y, locations));
        }
        int part2 = sizes.stream().sorted(Comparator.reverseOrder()).limit(3).reduce(1, (a, b) -> a * b);
        System.out.println("part2 = " + part2 + ", " + (1100682 == part2));
    }

    static int getBasinSize(int x, int y, int[][] arr) {
        Set<Point> locations = addLowerPoints(x, y, arr, new HashSet<>());
        return locations.size() + 1;
    }

    static Set<Point> addLowerPoints(int x, int y, int[][] arr, Set<Point> locations) {
        int current = arr[y][x];
        if (y > 0 && y < arr.length - 1) {
            if (arr[y - 1][x] != 9 && arr[y - 1][x] > current) {
                locations.add(new Point(y - 1, x));
                addLowerPoints(x, y - 1, arr, locations);
            }
            if (arr[y + 1][x] != 9 && arr[y + 1][x] > current) {
                locations.add(new Point(y + 1, x));
                addLowerPoints(x, y + 1, arr, locations);
            }
        } else if (y == 0) {
            int val = arr[y + 1][x];
            if (val != 9 && val > current) {
                locations.add(new Point(y + 1, x));
                addLowerPoints(x, y + 1, arr, locations);
            }
        } else if (y == arr.length - 1) {
            if (arr[y - 1][x] != 9 && arr[y - 1][x] > current) {
                locations.add(new Point(y - 1, x));
                addLowerPoints(x, y - 1, arr, locations);
            }
        }
        if (x > 0 && x < arr[0].length - 1) {
            if (arr[y][x - 1] != 9 && arr[y][x - 1] > current) {
                locations.add(new Point(y, x - 1));
                addLowerPoints(x - 1, y, arr, locations);
            }
            if (arr[y][x + 1] != 9 && arr[y][x + 1] > current) {
                locations.add(new Point(y, x + 1));
                addLowerPoints(x + 1, y, arr, locations);
            }
        } else if (x == 0) {
            if (arr[y][x + 1] != 9 && arr[y][x + 1] > current) {
                locations.add(new Point(y, x + 1));
                addLowerPoints(x + 1, y, arr, locations);
            }
        } else if (x == arr[0].length - 1) {
            if (arr[y][x - 1] != 9 && arr[y][x - 1] > current) {
                locations.add(new Point(y, x - 1));
                addLowerPoints(x - 1, y, arr, locations);
            }
        }
        return locations;
    }


    static boolean isLowestPoint(int x, int y, int[][] arr) {
        int current = arr[y][x];
        if (y > 0 && y < arr.length - 1 && (arr[y - 1][x] <= current || arr[y + 1][x] <= current)) {
            return false;
        } else if (y == 0 && arr[y + 1][x] <= current) {
            return false;
        } else if (y == arr.length - 1 && arr[y - 1][x] <= current) {
            return false;
        }
        if (x > 0 && x < arr[0].length - 1 && (arr[y][x - 1] <= current || arr[y][x + 1] <= current)) {
            return false;
        } else if (x == 0 && arr[y][x + 1] <= current) {
            return false;
        } else if (x == arr[0].length - 1 && arr[y][x - 1] <= current) {
            return false;
        }
        return true;
    }

    static class Point {
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


