package solutions.day22;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class Solution {

    private static final Logger log = LogManager.getLogger(Solution.class);

    public static void main(String[] args) throws IOException {

        URL resource = Solution.class.getResource("/day22.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).toList();

        System.out.println("part1 = " + answerPart1(lines));
        System.out.println("part2 = " + answerPart2(lines));
    }

    static BigInteger answerPart2(List<String> lines) {

        List<Cube> finalCubesList = new ArrayList<>();

        for (String line : lines) {
            Cube cube = createCube(line);
            List<Cube> list = new ArrayList<>();
            for (Cube added : finalCubesList) {
                Cube intersectionsCube = getIntersectionCube(cube, added);
                if (intersectionsCube != null) {
                    list.add(intersectionsCube);
                }
            }
            finalCubesList.addAll(list);
            if (cube.isOn) {
                finalCubesList.add(cube);
            }
        }
        return getResult(finalCubesList);
    }


    static BigInteger getResult(List<Cube> allCubes) {
        BigInteger result = BigInteger.ZERO;
        for (Cube allCube : allCubes) {
            if (allCube.isOn) {
                result = result.add(allCube.getVolume());
            } else {
                result = result.subtract(allCube.getVolume());
            }
        }
        return result;
    }

    static Cube getIntersectionCube(Cube newCube, Cube existingCube) {
        int higherMinX = Math.max(newCube.minX, existingCube.minX);
        int lowerMaxX = Math.min(newCube.maxX, existingCube.maxX);
        if (higherMinX <= lowerMaxX) {
            int higherMinY = Math.max(newCube.minY, existingCube.minY);
            int lowerMaxY = Math.min(newCube.maxY, existingCube.maxY);
            if (higherMinY <= lowerMaxY) {
                int higherMinZ = Math.max(newCube.minZ, existingCube.minZ);
                int lowerMaxZ = Math.min(newCube.maxZ, existingCube.maxZ);
                if (higherMinZ <= lowerMaxZ) {
                    return new Cube(higherMinX, lowerMaxX, higherMinY, lowerMaxY, higherMinZ, lowerMaxZ, !existingCube.isOn);
                }
            }
        }
        return null;
    }

    static Cube createCube(String line) {
        String xOption = line.substring(line.indexOf("x=") + 2, line.indexOf(",y="));
        String xStartString = xOption.substring(0, xOption.indexOf('.'));
        String xEndString = xOption.substring(xOption.indexOf(".") + 2);

        String yOption = line.substring(line.indexOf("y=") + 2, line.indexOf(",z="));
        String yStartString = yOption.substring(0, yOption.indexOf('.'));
        String yEndString = yOption.substring(yOption.indexOf(".") + 2);

        String zOption = line.substring(line.indexOf("z=") + 2);
        String zStartString = zOption.substring(0, zOption.indexOf('.'));
        String zEndString = zOption.substring(zOption.indexOf(".") + 2);

        int xStart = Integer.parseInt(xStartString);
        int xEnd = Integer.parseInt(xEndString);

        int yStart = Integer.parseInt(yStartString);
        int yEnd = Integer.parseInt(yEndString);

        int zStart = Integer.parseInt(zStartString);
        int zEnd = Integer.parseInt(zEndString);

        String modeString = line.substring(0, line.indexOf(" "));
        boolean isOn = modeString.equals("on");

        return new Cube(xStart, xEnd, yStart, yEnd, zStart, zEnd, isOn);
    }

    static long answerPart1(List<String> lines) {
        boolean[][][] cubes = getInitialCubes();
        for (String line : lines) {
            performStep(line, cubes);
        }
        return countTurnOnCubes(cubes);
    }

    static long countTurnOnCubes(boolean[][][] cubes) {
        long counter = 0;
        for (int x : IntStream.rangeClosed(0, 100).toArray()) {
            for (int y : IntStream.rangeClosed(0, 100).toArray()) {
                for (int z : IntStream.rangeClosed(0, 100).toArray()) {
                    if (cubes[x][y][z]) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    static boolean[][][] performStep(String line, boolean[][][] cubes) {
        String modeString = line.substring(0, line.indexOf(" "));
        String xOption = line.substring(line.indexOf("x=") + 2, line.indexOf(",y="));
        String xStartString = xOption.substring(0, xOption.indexOf('.'));
        String xEndString = xOption.substring(xOption.indexOf(".") + 2);

        String yOption = line.substring(line.indexOf("y=") + 2, line.indexOf(",z="));
        String yStartString = yOption.substring(0, yOption.indexOf('.'));
        String yEndString = yOption.substring(yOption.indexOf(".") + 2);

        String zOption = line.substring(line.indexOf("z=") + 2);
        String zStartString = zOption.substring(0, zOption.indexOf('.'));
        String zEndString = zOption.substring(zOption.indexOf(".") + 2);

        int xStart = Integer.parseInt(xStartString);
        int xEnd = Integer.parseInt(xEndString);

        int yStart = Integer.parseInt(yStartString);
        int yEnd = Integer.parseInt(yEndString);

        int zStart = Integer.parseInt(zStartString);
        int zEnd = Integer.parseInt(zEndString);

        if (!shouldPerformStep(xStart, xEnd, yStart, yEnd, zStart, zEnd)) {
            return cubes;
        }
        boolean mode = modeString.equals("on");
        for (int x : IntStream.rangeClosed(xStart, xEnd).toArray()) {
            for (int y : IntStream.rangeClosed(yStart, yEnd).toArray()) {
                for (int z : IntStream.rangeClosed(zStart, zEnd).toArray()) {
                    if (inRange(x) && inRange(y) && inRange(z)) {
                        cubes[x + 50][y + 50][z + 50] = mode;
                    }
                }
            }
        }
        return cubes;
    }

    static boolean shouldPerformStep(int sX, int eX, int sY, int eY, int sZ, int eZ) {
        return inRange(sX) && inRange(eX) && inRange(sY) && inRange(eY) && inRange(sZ) && inRange(eZ);
    }

    static boolean inRange(int x) {
        return x >= -50 && x <= 50;
    }

    static boolean[][][] getInitialCubes() {
        return new boolean[101][101][101];
    }
}

class Cube {
    int minX;
    int maxX;
    int minY;
    int maxY;
    int minZ;
    int maxZ;
    boolean isOn;

    public Cube(int minX, int maxX, int minY, int maxY, int minZ, int maxZ, boolean isOn) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.minZ = minZ;
        this.maxZ = maxZ;
        this.isOn = isOn;
    }

    public BigInteger getVolume() {
        BigInteger bigIntegerX = BigInteger.valueOf((maxX - minX) + 1);
        BigInteger bigIntegerY = BigInteger.valueOf((maxY - minY) + 1);
        BigInteger bigIntegerZ = BigInteger.valueOf((maxZ - minZ) + 1);
        return bigIntegerX.multiply(bigIntegerY).multiply(bigIntegerZ);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cube cube = (Cube) o;
        return minX == cube.minX && maxX == cube.maxX && minY == cube.minY && maxY == cube.maxY && minZ == cube.minZ && maxZ == cube.maxZ && isOn == cube.isOn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minX, maxX, minY, maxY, minZ, maxZ, isOn);
    }

    @Override
    public String toString() {
        return "x=" + minX + ".." + maxX +
                ", y=" + minY + ".." + maxY +
                ", z=" + minZ + ".." + maxZ +
                ", isOn=" + isOn;
    }
}