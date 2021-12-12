package solutions.day12;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Solution {

    static int counter = 0;

    public static void main(String[] args) throws IOException {

        URL resource = Solution.class.getResource("/day12.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).toList();
        Map<String, List<String>> map = new HashMap<>();
        lines.forEach(line -> {
            String[] split = line.split("-");
            String first = split[0];
            String second = split[1];
            addPairToMap(first, second, map);
            addPairToMap(second, first, map);
        });
        createPaths("start", map, List.of("Start"), false);
        System.out.println("part1 = " + counter);
        counter = 0;
        createPaths("start", map, List.of("Start"), true);
        System.out.println("part2 = " + counter);
    }

    private static void addPairToMap(String first, String second, Map<String, List<String>> map) {
        map.computeIfAbsent(first, k -> new ArrayList<>()).add(second);
    }

    static void createPaths(String start, Map<String, List<String>> map, List<String> currentPath, boolean canVisitTwice) {
        List<String> toVisit = map.get(start);
        if (!start.equals("start")) {
            currentPath.add(start);
        }
        for (String cave : toVisit) {
            if (cave.equals("end")) {
                counter++;
            } else if (canVisit(currentPath, cave, canVisitTwice)) {
                createPaths(cave, map, new ArrayList<>(currentPath), canVisitTwice);
            }
        }
    }

    public static boolean canVisit(List<String> path, String cave, boolean canVisitTwice) {
        if (cave.equals("start")) {
            return false;
        } else if (Character.isUpperCase(cave.charAt(0))) {
            return true;
        } else if (path.contains(cave)) {
            return canVisitTwice && path.stream().filter(c -> Character.isLowerCase(c.charAt(0)))
                    .allMatch(new HashSet<>()::add);
        }
        return true;
    }
}

