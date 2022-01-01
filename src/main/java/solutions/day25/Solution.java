package solutions.day25;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Solution {

    public static void main(String[] args) throws IOException {

        URL resource = solutions.day16.Solution.class.getResource("/day25.txt");//_example1
        List<String> lines = Files.lines(Paths.get(resource.getPath())).toList();

        char[][] map = new char[lines.size()][lines.get(0).length()];

        int y = 0;
        int x = 0;
        for (String line : lines) {
            x = 0;
            for (char c : line.toCharArray()) {
                map[y][x] = c;
                x++;
            }
            y++;
        }

        printArray(map);
        var newMap = performStep(map);
        int ctr = 1;
        while (true) {
            printArray(newMap);
            newMap = performStep(newMap);
            ctr++;
            if (!isDifferent(newMap, map))
                break;
            map = newMap;

        }

        System.out.println("part1 = " + ctr);
    }

    static char[][] performStep(char[][] map) {
        char[][] newMap = new char[map.length][map[0].length];
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                char c = map[y][x];
                if (c == '>') {
                    if (isNextEmpty(c, y, x, map)) {
                        var newPosition = getNextPosition(c, y, x, map);
                        newMap[newPosition.getLeft()][newPosition.getRight()] = c;
                    } else {
                        newMap[y][x] = c;
                    }
                }
            }
        }
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                char c = map[y][x];
                if (c == 'v') {
                    if (isNextEmpty(c, y, x, map, newMap)) {
                        var newPosition = getNextPosition(c, y, x, map);
                        newMap[newPosition.getLeft()][newPosition.getRight()] = c;
                    } else {
                        newMap[y][x] = c;
                    }
                }
            }
        }
        return newMap;
    }

    static boolean isDifferent(char[][] map, char[][] newMap) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x] != newMap[y][x]) {
                    return true;
                }
            }
        }
        return false;
    }

    static long answerPart1() {
        return 12;
    }

    private static void printArray(char[][] array) {
        for (char[] chars : array) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < array[0].length; j++) {
                sb.append(chars[j] == 0 ? "." : chars[j]);
            }
            System.out.println(sb);
        }
        System.out.println("-----------------------------------------------");
    }

    static boolean isNextEmpty(char c, int y, int x, char[][] map) {
        if (c == '>') {
            if (x < map[0].length - 1) {
                return map[y][x + 1] != 'v' && map[y][x + 1] != '>';
            } else {
                return map[y][0] != 'v' && map[y][0] != '>';
            }
        } else if (c == 'v') {
            if (y < map.length - 1) {
                return map[y + 1][x] != 'v' && map[y + 1][x] != '>';
            } else {
                return map[0][x] != 'v' && map[0][x] != '>';
            }
        }
        return false;
    }

    static boolean isNextEmpty(char c, int y, int x, char[][] map, char[][] newMap) {
        if (y < map.length - 1) {
            return map[y + 1][x] != 'v' && newMap[y + 1][x] != '>';
        } else {
            return map[0][x] != 'v' && newMap[0][x] != '>';
        }
    }

    static Pair<Integer, Integer> getNextPosition(char c, int y, int x, char[][] map) {
        if (c == '>') {
            if (x < map[0].length - 1) {
                return Pair.of(y, x + 1);
            } else {
                return Pair.of(y, 0);
            }
        } else if (c == 'v') {
            if (y < map.length - 1) {
                return Pair.of(y + 1, x);
            } else {
                return Pair.of(0, x);
            }
        }
        return Pair.of(-1, -1);
    }
}

