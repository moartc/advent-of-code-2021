package solutions.day17;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

public class Solution {

    static boolean log = false;

    public static void main(String[] args) throws IOException {

        URL resource = solutions.day16.Solution.class.getResource("/day17.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).filter(Predicate.not(String::isEmpty)).toList();
        String input = lines.get(0);
        String[] split = input.split(",");
        int x1 = Integer.parseInt(split[0].substring(split[0].indexOf("=") + 1, split[0].indexOf("..")));
        int x2 = Integer.parseInt(split[0].substring(split[0].indexOf("..") + 2));
        int y1 = Integer.parseInt(split[1].substring(split[1].indexOf("=") + 1, split[1].indexOf("..")));
        int y2 = Integer.parseInt(split[1].substring(split[1].indexOf("..") + 2));

        Area area = new Area(x1, x2, y1, y2);
        System.out.println("part1 = " + simulatePart1(area));
        System.out.println("part2 = " + simulatePart2(area));
    }

    static long simulatePart2(Area area) {
        long counter = 0;
        for (int i = 1; i < 1000; i++) {
            for (int j = -1000; j < 1000; j++) {
                Pair position = new Pair(0, 0);
                Pair velocityToTest = new Pair(i, j);
                while (!area.pointPassedArea(position)) {
                    if (area.isPointInsideArea(position)) {
                        counter++;
                        break;
                    }
                    position.updatePosition(velocityToTest);
                    velocityToTest.updateVelocity();
                }
            }
        }
        return counter;
    }

    static int simulatePart1(Area area) {
        int highest = Integer.MIN_VALUE;
        for (int i = 1; i < area.x1; i++) {
            for (int j = 1; j < 1000; j++) {
                Pair position = new Pair(0, 0);
                Pair velocityToTest = new Pair(i, j);
                int tempHighest = Integer.MIN_VALUE;
                while (!area.pointPassedArea(position)) {
                    if (position.y > tempHighest) {
                        tempHighest = position.y;
                    }
                    if (area.isPointInsideArea(position) && tempHighest > highest) {
                        highest = tempHighest;
                    }
                    position.updatePosition(velocityToTest);
                    velocityToTest.updateVelocity();
                }
            }
        }
        return highest;
    }
}

class Area {
    int x1;
    int x2;
    int y1;
    int y2;

    public Area(int x1, int x2, int y1, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    boolean isPointInsideArea(Pair point) {
        return x1 <= point.x && x2 >= point.x && y1 <= point.y && y2 >= point.y;
    }

    boolean pointPassedArea(Pair point) {
        return point.x > x2 || point.y < y1;
    }
}

class Pair {
    public int x;
    public int y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void updateVelocity() {
        if (x > 0) {
            x--;
        } else if (x < 0) {
            x++;
        }
        y--;
    }

    public void updatePosition(Pair pair) {
        x += pair.x;
        y += pair.y;
    }
}

