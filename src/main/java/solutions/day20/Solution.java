package solutions.day20;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {

    public static void main(String[] args) throws IOException {

        boolean test = false;

        URL resource = solutions.day16.Solution.class.getResource("/day20" + (test ? "_" : "") + ".txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).toList();

        String algorithm = lines.get(0);
        char[][] arr = getArrayFromInput(lines);

        System.out.println("part1 = " + getAnswer(arr, algorithm, 2));
        System.out.println("part2 = " + getAnswer(arr, algorithm, 50));
    }

    static int getAnswer(char[][] arr, String algorithm, int steps) {
        arr = extendArray(arr, 6);
        for (int i = 0; i < steps; i++) {
            arr = performStep(arr, algorithm);
            arr = extendArray(arr, 2);
            if (i % 2 == 1) {
                arr = removeBorder(arr);
            }
        }
        return countPixels(arr, 6);
    }

    static char[][] removeBorder(char[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (i < 6 || i > arr.length - 6 || j < 6 || j > arr[0].length - 6) {
                    arr[i][j] = '.';
                }
            }
        }
        return arr;
    }

    static int countPixels(char[][] arr, int offset) {
        int counter = 0;
        for (int i = offset; i < arr.length - offset; i++) {
            for (int j = offset; j < arr[i].length - offset; j++) {
                if (arr[i][j] == '#') {
                    counter++;
                }
            }
        }
        return counter;
    }

    static char[][] performStep(char[][] arr, String algorithm) {
        char[][] copy = Arrays.stream(arr).map(char[]::clone).toArray(char[][]::new);
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                int n = getNumberForPixel(i, j, copy);
                char algoChar = algorithm.charAt(n);
                arr[i][j] = algoChar;
            }
        }
        return arr;
    }

    static char[][] extendArray(char[][] current, int val) {
        char[][] newArray = new char[current.length + val * 2][current[0].length + val * 2];
        for (int i = 0; i < newArray.length; i++) {
            for (int j = 0; j < newArray[0].length; j++) {
                newArray[i][j] = '.';
            }
        }
        for (int i = val; i < current.length + val; i++) {
            for (int j = val; j < current[0].length + val; j++) {
                newArray[i][j] = current[i - val][j - val];
            }
        }
        return newArray;
    }

    static int getNumberForPixel(int y, int x, char[][] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                if (i < 0 || i > arr.length - 1 || j < 0 || j > arr[0].length - 1) {
                    sb.append("0");
                } else {
                    sb.append(arr[i][j] == '#' ? "1" : "0");
                }
            }
        }
        return Integer.valueOf(sb.toString(), 2);
    }

    static char[][] getArrayFromInput(List<String> input) {
        List<String> image = new ArrayList<>();
        boolean wasEmptyLine = false;
        for (String line : input) {
            if (line.isEmpty() && !wasEmptyLine) {
                wasEmptyLine = true;
            } else if (wasEmptyLine) {
                image.add(line);
            }
        }
        char[][] arr = new char[image.size()][image.get(0).length()];
        for (int y = 0; y < image.size(); y++) {
            for (int x = 0; x < image.get(0).length(); x++) {
                arr[y][x] = image.get(y).charAt(x);
            }
        }
        return arr;
    }
}



