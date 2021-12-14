package solutions.day14;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Solution {

    public static void main(String[] args) throws IOException {

        boolean test = true;

        URL resource = Solution.class.getResource("/day14" + (test ? "_1" : "") + ".txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).toList();

        String template = null;
        Map<String, Character> pairs = new HashMap<>();
        int ctr = 0;
        for (String line : lines) {
            if (ctr == 0) {
                template = line;
            } else if (!line.isEmpty()) {
                String[] split = line.split(" -> ");
                pairs.put(split[0], split[1].charAt(0));
            }
            ctr++;
        }

        Map<String, Integer> myMap = new HashMap<>();
        for(int i = 0; i < template.length()-1; i++) {
            String key = template.substring(i, i+2);
            if(!myMap.containsKey(key)) {
                myMap.put(key, 1);
            } else {
                myMap.put(key, myMap.get(key) + 1);
            }
        }

        Map<String, Integer> step = step(myMap, pairs);
        for(int i = 0; i < 11; i++) {
            myMap = step(myMap, pairs);
        }

        int h = 0;
        for(Map.Entry<String, Integer> e : myMap.entrySet()) {
            if(e.getKey().contains("H")) {
                h += e.getValue();
            }
        }
        System.out.println("h = " + h);

//        System.out.println("part1 = " + (max - min));
        System.out.println("part2 = ");
    }

    static Map<String, Integer> step(Map<String, Integer> map, Map<String, Character> mapping) {
        Map<String, Integer> mapRes = new HashMap<>();
        for(Map.Entry<String, Integer> entry : map.entrySet()) {
            Character s = mapping.get(entry.getKey());
            String s1 = new String(entry.getKey().charAt(0)+""+s);
            String s2 = new String(s+""+entry.getKey().charAt(1)+"");
            if(!mapRes.containsKey(s1)) {
                mapRes.put(s1, entry.getValue());
            } else {
                mapRes.put(s1, mapRes.get(s1) + 1);
            }
            if(!mapRes.containsKey(s2)) {
                mapRes.put(s2, entry.getValue());
            } else {
                mapRes.put(s2, mapRes.get(s2) + 1);
            }
        }
        return mapRes;
    }

    static long getMax(String result) {
        Map<Character, Long> hm = result.chars().mapToObj(c ->
                (char) c).collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        long min = Long.MAX_VALUE;
        long max = 0;
        for (Map.Entry<Character, Long> en : hm.entrySet()) {
            if (en.getValue() > max) {
                max = en.getValue();
            }
            if (en.getValue() < min) {
                min = en.getValue();
            }
        }
        return max;
    }

    static long getMin(String result) {
        Map<Character, Long> hm = result.chars().mapToObj(c ->
                (char) c).collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        System.out.println(hm);
        long min = Long.MAX_VALUE;
        long max = 0;
        for (Map.Entry<Character, Long> en : hm.entrySet()) {
            if (en.getValue() > max) {
                max = en.getValue();
            }
            if (en.getValue() < min) {
                min = en.getValue();
            }
        }
        return min;
    }





}





