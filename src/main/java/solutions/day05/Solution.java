package solutions.day05;

import java.io.InputStream;
import java.util.*;
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
        List<Point> allPointsPart1 = new ArrayList<>();
        for (String s : input) {
            String[] split = s.split(" -> ");
            int a = 12;
            String[] split1 = split[0].split(",");
            String[] split2 = split[1].split(",");
            int x1 = Integer.valueOf(split1[0]);
            int y1 = Integer.valueOf(split1[1]);
            int x2 = Integer.valueOf(split2[0]);
            int y2 = Integer.valueOf(split2[1]);
            allPointsPart1.addAll(getCoverPointsPart1(x1, y1, x2, y2));
        }
        Map<Point, Integer> coverPart1 = new HashMap<>();
        for (Point p : allPointsPart1) {
            Integer value = coverPart1.get(p);
            if (value == null) {
                value = 0;
            }
            value++;
            coverPart1.put(p, value);
        }
        long counterPart1 = coverPart1.values().stream().filter(integer -> integer >= 2).count();
        System.out.println("part1 = " + counterPart1);

        List<Point> allPointsPart2 = new ArrayList<>();
        for (String s : input) {
            String[] split = s.split(" -> ");
            int a = 12;
            String[] split1 = split[0].split(",");
            String[] split2 = split[1].split(",");
            int x1 = Integer.valueOf(split1[0]);
            int y1 = Integer.valueOf(split1[1]);
            int x2 = Integer.valueOf(split2[0]);
            int y2 = Integer.valueOf(split2[1]);
            allPointsPart2.addAll(getCoverPointsPart2(x1, y1, x2, y2));
        }
        Map<Point, Integer> coverPart2 = new HashMap<>();
        for (Point p : allPointsPart2) {
            Integer value = coverPart2.get(p);
            if (value == null) {
                value = 0;
            }
            value++;
            coverPart2.put(p, value);
        }
        long counterPart2 = coverPart2.values().stream().filter(integer -> integer >= 2).count();
        System.out.println("part2 = " + counterPart2);
    }

    public static List<Point> getCoverPointsPart1(int x1, int y1, int x2, int y2) {
        List<Point> points = new ArrayList<>();
        if (x1 == x2) {
            if (y1 > y2) {
                IntStream.rangeClosed(y2, y1).forEach(i -> points.add(new Point(x1, i)));
            } else {
                IntStream.rangeClosed(y1, y2).forEach(i -> points.add(new Point(x1, i)));
            }

        } else if (y1 == y2) {
            if (x1 > x2) {
                IntStream.rangeClosed(x2, x1).forEach(i -> points.add(new Point(i, y1)));
            } else {
                IntStream.rangeClosed(x1, x2).forEach(i -> points.add(new Point(i, y1)));
            }
        }
        return points;

    }

    public static List<Point> getCoverPointsPart2(int x1, int y1, int x2, int y2) {
        List<Point> points = new ArrayList<>();
        if (x1 == x2) {
            if (y1 > y2) {
                IntStream.rangeClosed(y2, y1).forEach(i -> points.add(new Point(x1, i)));
            } else {
                IntStream.rangeClosed(y1, y2).forEach(i -> points.add(new Point(x1, i)));
            }

        } else if (y1 == y2) {
            if (x1 > x2) {
                IntStream.rangeClosed(x2, x1).forEach(i -> points.add(new Point(i, y1)));
            } else {
                IntStream.rangeClosed(x1, x2).forEach(i -> points.add(new Point(i, y1)));
            }
        } else {
            float angle = getAngle(x1, y1, x2, y2);
            if (angle % 45 == 0) {
                int startX;
                int endX;
                int startY;
                int endY;
                if (x1 > x2) {
                    startX = x2;
                    endX = x1;
                    startY = y2;
                    endY = y1;
                } else {
                    startX = x1;
                    endX = x2;
                    startY = y1;
                    endY = y2;
                }
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
        }
        return points;

    }

    public static float getAngle(int x1, int y1, int x2, int y2) {
        float angle = (float) Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }

    static class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}