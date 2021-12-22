package solutions.day22;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
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
        for (String line : lines) {
            log.info("perform line {}", line);
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


            boolean mode = modeString.equals("on");

        }
        return 12L;
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
