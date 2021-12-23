package solutions.day22;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class Solution {

    private static Logger log = LogManager.getLogger(Solution.class);


    public static void main(String[] args) throws IOException {

        URL resource = Solution.class.getResource("/day22.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).toList();


        System.out.println("part1 = " + answerPart1(lines));
        System.out.println("part2 = " + answerPart2(lines));
    }

    static long answerPart2(List<String> lines) {

        List<Cube> allOnCubes = new ArrayList<>();
        List<Cube> intersectionCubes = new ArrayList<>();
        List<Cube> allOffCubes = new ArrayList<>();

        for (String line : lines) {
            Cube cube = createCube(line);
            boolean isOn = isOnMode(line);
            if (isOn) {
                allOnCubes.add(cube);
                List<Cube> cubeList = updateIntersectionCubes(cube, allOnCubes);
                intersectionCubes.addAll(cubeList);
                for (Cube newIntersection : cubeList) {
                    log.info("found that intersection: {}", newIntersection);
                }
            } else {
                allOffCubes.add(cube);
            }
        }
        return getFinalVolume(allOnCubes, intersectionCubes);
    }

    static long getFinalVolume(List<Cube> allCubes, List<Cube> allIntersections) {
        long finalTotalVolume = 0;
        for(Cube cube : allCubes) {
            finalTotalVolume += cube.getVolume();
        }
        for(Cube intersectedCube : allIntersections) {
            finalTotalVolume -= intersectedCube.getVolume();
        }
        return finalTotalVolume;
    }

    static long getFinalVolumeForCube(Cube cube, List<Cube> allIntersections) {
        long allIntersectedVolume = 0;
        for(Cube intersection : allIntersections) {
            allIntersectedVolume += getIntersectionCube(cube, intersection).getVolume();
        }
        return cube.getVolume() - allIntersectedVolume;
    }

    static List<Cube> updateIntersectionCubes(Cube newCube, List<Cube> allOnCubes) {
        List<Cube> intersections = new ArrayList<>();
        for (Cube addedCube : allOnCubes) {
            if (!addedCube.equals(newCube)) {
                Cube intersectionCube = getIntersectionCube(newCube, addedCube);
                if (intersectionCube != null) {
                    intersections.add(intersectionCube);
                }
            }
        }
        return intersections;
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
                    Cube intersectionCube = new Cube(higherMinX, lowerMaxX, higherMinY, lowerMaxY, higherMinZ, lowerMaxZ);
                    log.info("will return new intersection cube {}", intersectionCube);
                    return intersectionCube;
                }
            }
        }
        log.info("intersection doesn't exist");
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

        return new Cube(xStart, xEnd, yStart, yEnd, zStart, zEnd);
    }

    static boolean isOnMode(String line) {
        String modeString = line.substring(0, line.indexOf(" "));
        return modeString.equals("on");
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
        log.info("mode = {}", mode);
        for (int x : IntStream.rangeClosed(xStart, xEnd).toArray()) {
            for (int y : IntStream.rangeClosed(yStart, yEnd).toArray()) {
                for (int z : IntStream.rangeClosed(zStart, zEnd).toArray()) {
                    if (inRange(x) && inRange(y) && inRange(z)) {
                        cubes[x + 50][y + 50][z + 50] = mode;
                    }
                }
            }
        }
        log.info("turned on after this step: {}", countTurnOnCubes(cubes));

        return cubes;
    }

    static boolean shouldPerformStep(int sX, int eX, int sY, int eY, int sZ, int eZ) {
        if (inRange(sX) && inRange(eX) && inRange(sY) && inRange(eY) && inRange(sZ) && inRange(eZ)) {
            return true;
        } else {
            return false;
        }
    }

    static boolean inRange(int x) {
        return x >= -50 && x <= 50;
    }

    static boolean[][][] getInitialCubes() {
        boolean[][][] cubes = new boolean[101][101][101];
        return cubes;
    }
}

class Cube {
    int minX;
    int maxX;
    int minY;
    int maxY;
    int minZ;
    int maxZ;

    public Cube(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.minZ = minZ;
        this.maxZ = maxZ;
    }

    public long getVolume() {
        return ((maxX - minX) + 1)
                * ((maxY - minY) + 1)
                * ((maxZ - minZ) + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cube cube = (Cube) o;
        return minX == cube.minX && maxX == cube.maxX && minY == cube.minY && maxY == cube.maxY && minZ == cube.minZ && maxZ == cube.maxZ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minX, maxX, minY, maxY, minZ, maxZ);
    }

    @Override
    public String toString() {
        return "x=" + minX + ".." + maxX +
                ", y=" + minY + ".." + maxY +
                ", z=" + minZ + ".." + maxZ;
    }
}