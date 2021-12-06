package solutions.day06;

import java.awt.*;
import java.io.InputStream;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {

    static Map<Integer, Long> dayToProd = new HashMap<>();

    public static void main(String[] args) {

//        InputStream is = Solution.class.getResourceAsStream("/day06.txt");
//        Scanner scanner = new Scanner(is);
        List<Integer> input = Arrays.asList(5, 3, 2, 2, 1, 1, 4, 1, 5, 5, 1, 3, 1, 5, 1, 2, 1, 4, 1, 2, 1, 2, 1, 4, 2, 4, 1, 5, 1, 3, 5, 4, 3, 3, 1, 4, 1, 3, 4, 4, 1, 5, 4, 3, 3, 2, 5, 1, 1, 3, 1, 4, 3, 2, 2, 3, 1, 3, 1, 3, 1, 5, 3, 5, 1, 3, 1, 4, 2, 1, 4, 1, 5, 5, 5, 2, 4, 2, 1, 4, 1, 3, 5, 5, 1, 4, 1, 1, 4, 2, 2, 1, 3, 1, 1, 1, 1, 3, 4, 1, 4, 1, 1, 1, 4, 4, 4, 1, 3, 1, 3, 4, 1, 4, 1, 2, 2, 2, 5, 4, 1, 3, 1, 2, 1, 4, 1, 4, 5, 2, 4, 5, 4, 1, 2, 1, 4, 2, 2, 2, 1, 3, 5, 2, 5, 1, 1, 4, 5, 4, 3, 2, 4, 1, 5, 2, 2, 5, 1, 4, 1, 5, 1, 3, 5, 1, 2, 1, 1, 1, 5, 4, 4, 5, 1, 1, 1, 4, 1, 3, 3, 5, 5, 1, 5, 2, 1, 1, 3, 1, 1, 3, 2, 3, 4, 4, 1, 5, 5, 3, 2, 1, 1, 1, 4, 3, 1, 3, 3, 1, 1, 2, 2, 1, 2, 2, 2, 1, 1, 5, 1, 2, 2, 5, 2, 4, 1, 1, 2, 4, 1, 2, 3, 4, 1, 2, 1, 2, 4, 2, 1, 1, 5, 3, 1, 4, 4, 4, 1, 5, 2, 3, 4, 4, 1, 5, 1, 2, 2, 4, 1, 1, 2, 1, 1, 1, 1, 5, 1, 3, 3, 1, 1, 1, 1, 4, 1, 2, 2, 5, 1, 2, 1, 3, 4, 1, 3, 4, 3, 3, 1, 1, 5, 5, 5, 2, 4, 3, 1, 4);

        long ans = input.size();
        for(Integer i : input) {
            ans += getProd(i, 80);
        }
        System.out.println("part1 = " + ans);

        long ans2 = input.size();
        for(Integer i : input) {
            ans2 += getProd(i, 256);
        }
        System.out.println("part2 = " + ans2);
    }

    static long getProd(int init, int toEnd) {
        long res = 0;
        List<Integer> prod = new ArrayList<>();
        for (int i = 1; i <= toEnd; i++) {
            if (init == 0) {
                res++;
                prod.add(i);
                init = 6;
            } else {
                init--;
            }
        }
        for (Integer produced : prod) {
            Long intger = dayToProd.get(toEnd - produced);
            if (intger != null) {
                res += intger;
            } else {
                long num = getProd(8, toEnd - produced);
                dayToProd.put(toEnd - produced, num);
                res += num;
            }
        }
        return res;
    }
}
