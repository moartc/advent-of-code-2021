package solutions.day15;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.Predicate;

public class Solution {

    public static void main(String[] args) throws IOException {

        URL resource = Solution.class.getResource("/day15.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).filter(Predicate.not(String::isEmpty)).toList();
        int[][] map = new int[lines.size()][lines.get(0).length()];

        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(0).length(); x++) {
                int i = lines.get(y).charAt(x) - '0';
                map[y][x] = i;
            }
        }

        int part1 = findShortestPath(map) - map[0][0];
        System.out.println("part1 = " + part1);

        int[][] map2 = new int[lines.size() * 5][lines.size() * 5];
        for (int i = 0; i < map.length; i++) {
            System.arraycopy(map[i], 0, map2[i], 0, map.length);
        }
        for (int i = 0; i < map2.length; i++) {
            for (int j = 0; j < map2.length; j++) {
                int tempI = i % map.length;
                int tempJ = j % map.length;
                int newVal = getNewVal(map[tempI][tempJ], (i / map.length), (j / map.length));
                map2[i][j] = newVal;
            }
        }
        int part2 = findShortestPath(map2) - map[0][0];
        System.out.println("part2 = " + part2);
    }

    static int getNewVal(int current, int x, int y) {
        if (current + x + y < 10) {
            return current + x + y;
        } else {
            int newV = current + x + y;
            return (newV % 10) + 1;
        }
    }

    public static int findShortestPath(int[][] array) {
        int[][] dist = new int[array.length][array.length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                dist[i][j] = Integer.MAX_VALUE;
            }
        }
        dist[0][0] = array[0][0];
        Queue<Cell> queue = new PriorityQueue<>(array.length * array.length, Comparator.comparingInt(o -> o.distance));
        queue.add(new Cell(0, 0, dist[0][0]));
        while (!queue.isEmpty()) {
            Cell curr = queue.poll();
            for (int i = 0; i < 4; i++) {
                int row = curr.x + dx[i];
                int col = curr.y + dy[i];
                if (row >= 0 && row < array.length && col >= 0 && col < array.length) {
                    if (dist[row][col] > dist[curr.x][curr.y] + array[row][col]) {
                        if (dist[row][col] != Integer.MAX_VALUE) {
                            Cell adj = new Cell(row, col, dist[row][col]);
                            queue.remove(adj);
                        }
                        dist[row][col] = dist[curr.x][curr.y] + array[row][col];
                        queue.add(new Cell(row, col, dist[row][col]));
                    }
                }
            }
        }
        return dist[array.length - 1][array.length - 1];
    }

    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};

    record Cell(int x, int y, int distance) {
    }
}