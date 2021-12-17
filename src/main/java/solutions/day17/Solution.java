package solutions.day17;

import utils.Point;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

public class Solution {

    public static void main(String[] args) throws IOException {

        URL resource = solutions.day16.Solution.class.getResource("/day17.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).filter(Predicate.not(String::isEmpty)).toList();
        String input = lines.get(0);
        String[] split = input.split(",");
        int x1 = Integer.parseInt(split[0].substring(split[0].indexOf("=") + 1, split[0].indexOf("..")));
        int x2 = Integer.parseInt(split[0].substring(split[0].indexOf("..") + 2));
        int y1 = Integer.parseInt(split[1].substring(split[1].indexOf("=") + 1, split[1].indexOf("..")));
        int y2 = Integer.parseInt(split[1].substring(split[1].indexOf("..") + 2));

        Area area = new Area(new Point(x1, y1), new Point(x2, y2));
        System.out.println("part1 = " + simulatePart1(area));
        System.out.println("part2 = " + simulatePart2(area));
    }

    static int simulatePart1(Area area) {
        int highestPosition = Integer.MIN_VALUE;
        for (int i = 1; i < area.point1.x; i++) {
            for (int j = 1; j < 1000; j++) {
                var position = new Position(0, 0);
                var velocityToTest = new Velocity(i, j);
                int tempHighest = Integer.MIN_VALUE;
                while (!area.pointPassedArea(position)) {
                    if (position.y > tempHighest) {
                        tempHighest = position.y;
                    }
                    if (area.isPointInsideArea(position) && tempHighest > highestPosition) {
                        highestPosition = tempHighest;
                    }
                    position.updatePosition(velocityToTest);
                    velocityToTest.updateVelocity();
                }
            }
        }
        return highestPosition;
    }

    static long simulatePart2(Area area) {
        long counter = 0;
        for (int i = 1; i < 1000; i++) {
            for (int j = -1000; j < 1000; j++) {
                var position = new Position(0, 0);
                var velocityToTest = new Velocity(i, j);
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

    static class Area {
        Point point1;
        Point point2;

        public Area(Point point1, Point point2) {
            this.point1 = point1;
            this.point2 = point2;
        }

        boolean isPointInsideArea(Position position) {
            return point1.x <= position.x && point2.x >= position.x && point1.y <= position.y && point2.y >= position.y;
        }

        boolean pointPassedArea(Position position) {
            return position.x > point2.x || position.y < point1.y;
        }
    }

    static class Position extends Point {

        public Position(int x, int y) {
            super(x, y);
        }

        public void updatePosition(Velocity velocity) {
            x += velocity.x;
            y += velocity.y;
        }
    }

    static class Velocity extends Point {

        public Velocity(int x, int y) {
            super(x, y);
        }

        public void updateVelocity() {
            if (x > 0) {
                x--;
            } else if (x < 0) {
                x++;
            }
            y--;
        }
    }
}

