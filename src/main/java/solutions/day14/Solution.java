package solutions.day14;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {

    public static void main(String[] args) throws IOException {

        URL resource = Solution.class.getResource("/day14.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).filter(Predicate.not(String::isEmpty)).toList();
        String template = lines.get(0);
        Map<String, Character> insertionPairs = lines.stream().skip(1).map(line -> line.split(" -> "))
                .collect(Collectors.toMap(s -> s[0], s -> s[1].charAt(0)));
        Map<String, Long> templateMap = IntStream.range(0, template.length() - 1)
                .mapToObj(i -> template.substring(i, i + 2))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        System.out.println("part1 = " + getResult(new HashMap<>(templateMap), insertionPairs, 10));
        System.out.println("part2 = " + getResult(new HashMap<>(templateMap), insertionPairs, 40));
    }

    private static Long getResult(Map<String, Long> startMap, Map<String, Character> insertionPairs, int nbOfSteps) {
        for (int i = 0; i < nbOfSteps; i++) {
            startMap = step(startMap, insertionPairs);
        }
        Map<Character, Long> collectPart1 = startMap.entrySet().stream()
                .collect(Collectors.groupingBy(e -> e.getKey().charAt(0), Collectors.summingLong(Map.Entry::getValue)));
        Long maxPart1 = collectPart1.values().stream().max(Long::compare).get();
        Long minPart1 = collectPart1.values().stream().min(Long::compare).get();
        return maxPart1 - minPart1 - 1;
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
        map.computeIfPresent(key, (k, v) -> v + value);
        map.computeIfAbsent(key, (k) -> value);
    }
}