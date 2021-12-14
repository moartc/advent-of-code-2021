package solutions.day14;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Solution {

    public static void main(String[] args) throws IOException {

        URL resource = Solution.class.getResource("/day14.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).filter(Predicate.not(String::isEmpty)).toList();
        String template = lines.get(0);
        Map<String, Character> pairs = lines.stream().skip(1).map(line -> line.split(" -> ")).collect(Collectors.toMap(s -> s[0], s -> s[1].charAt(0)));

        Map<String, Long> startMap = new HashMap<>();
        for (int i = 0; i < template.length() - 1; i++) {
            String key = template.substring(i, i + 2);
            if (!startMap.containsKey(key)) {
                startMap.put(key, 1L);
            } else {
                startMap.put(key, startMap.get(key) + 1);
            }
        }
        Map<String, Long> mapPart1 = step(new HashMap<>(startMap), pairs);
        for (int i = 1; i < 10; i++) {
            mapPart1 = step(mapPart1, pairs);
        }
        Map<Character, Long> collectPart1 = mapPart1.entrySet().stream().collect(Collectors.groupingBy(e -> e.getKey().charAt(0), Collectors.summingLong(Map.Entry::getValue)));
        Long maxPart1 = collectPart1.values().stream().max(Long::compare).get();
        Long minPart1 = collectPart1.values().stream().min(Long::compare).get();
        System.out.println("part1 = " + (maxPart1 - minPart1 - 1));

        Map<String, Long> mapPart2 = step(new HashMap<>(startMap), pairs);
        for (int i = 1; i < 40; i++) {
            mapPart2 = step(mapPart2, pairs);
        }
        Map<Character, Long> collectPart2 = mapPart2.entrySet().stream().collect(Collectors.groupingBy(e -> e.getKey().charAt(0), Collectors.summingLong(Map.Entry::getValue)));
        Long maxPart2 = collectPart2.values().stream().max(Long::compare).get();
        Long minPart2 = collectPart2.values().stream().min(Long::compare).get();
        System.out.println("part2 = " + (maxPart2 - minPart2 - 1));
    }

    private static Map<String, Long> step(Map<String, Long> map, Map<String, Character> mapping) {
        Map<String, Long> mapRes = new HashMap<>();
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            Character s = mapping.get(entry.getKey());
            String key1 = entry.getKey().charAt(0) + "" + s;
            String key2 = s + "" + entry.getKey().charAt(1);
            addKeyToMap(key1, mapRes, entry.getValue());
            addKeyToMap(key2, mapRes, entry.getValue());
        }
        return mapRes;
    }

    private static void addKeyToMap(String key, Map<String, Long> map, Long value) {
        if (!map.containsKey(key)) {
            map.put(key, value);
        } else {
            map.put(key, map.get(key) + value);
        }
    }
}