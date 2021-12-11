package solutions.day11;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Solution {

    static int flashes = 0;

    public static void main(String[] args) throws IOException {

        URL resource = Solution.class.getResource("/day11.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).toList();
        int[][] octopus = lines.stream().map(line -> line.chars().map(c -> c - '0').toArray()).toArray(int[][]::new);
        for (int i = 0; i < 100; i++) {
            increaseByOne(octopus);
            round(octopus);
        }
        System.out.println("part1 = " + flashes);

        octopus = lines.stream().map(line -> line.chars().map(c -> c - '0').toArray()).toArray(int[][]::new);
        int roundCounter = 0;
        boolean allFlashedThisRound;
        do {
            increaseByOne(octopus);
            allFlashedThisRound = round(octopus);
            roundCounter++;
        } while (!allFlashedThisRound);

        System.out.println("part2 = " + roundCounter);
    }

    static void increaseByOne(int[][] octopus) {
        for (int y = 0; y < octopus.length; y++) {
            for (int x = 0; x < octopus[0].length; x++) {
                octopus[y][x]++;
            }
        }
    }

    static boolean allFlashed(boolean[][] flashed) {
        for (int y = 0; y < flashed.length; y++) {
            for (int x = 0; x < flashed[0].length; x++) {
                if (!flashed[y][x]) {
                    return false;
                }
            }
        }
        return true;
    }

    static boolean round(int[][] octopus) {
        boolean[][] flashedThisRound = new boolean[octopus.length][octopus[0].length];
        while (shouldFlash(octopus, flashedThisRound)) {
            for (int y = 0; y < octopus.length; y++) {
                for (int x = 0; x < octopus[0].length; x++) {
                    if (octopus[y][x] > 9 && !flashedThisRound[y][x]) {
                        flashes++;
                        flash(octopus, y, x, flashedThisRound);
                    }
                }
            }
        }
        return allFlashed(flashedThisRound);
    }

    private static void flash(int[][] octopus, int y, int x, boolean[][] flashed) {
        octopus[y][x] = 0;
        flashed[y][x] = true;
        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                if (i >= 0 && i < octopus.length && (!(i == y && j == x)) && j >= 0 && j < octopus[0].length && !flashed[i][j]) {
                    octopus[i][j]++;
                }
            }
        }
    }

    static boolean shouldFlash(int[][] octopus, boolean[][] flashedThisRound) {
        for (int y = 0; y < octopus.length; y++) {
            for (int x = 0; x < octopus[0].length; x++) {
                if (octopus[y][x] > 9 && !flashedThisRound[y][x]) {
                    return true;
                }
            }
        }
        return false;
    }
}


