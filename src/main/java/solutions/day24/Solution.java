package solutions.day24;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {


    private static final Logger log = LogManager.getLogger(Solution.class);

    public static void main(String[] args) {

        System.out.println("part1 = " + part1Answer());
        System.out.println("part2 = " + part2Answer());
    }

    static String part1Answer() {
        Set<Integer> forF = new HashSet<>();
        Set<Integer> forG = new HashSet<>();
        Set<Integer> forH = new HashSet<>();
        Set<Integer> forI = new HashSet<>();
        Set<Integer> forJ = new HashSet<>();
        for (int a = 9; a > 0; a--) {
            for (int b = 9; b > 0; b--) {
                for (int c = 9; c > 0; c--) {
                    for (int d = 9; d > 0; d--) {
                        for (int e = 9; e > 0; e--) {
                            for (int f = 9; f > 0; f--) {
                                int currentZF;
                                int z = 0;
                                z = foo0(a, z);
                                z = foo1(b, z);
                                z = foo2(c, z);
                                z = foo3(d, z);
                                z = foo4(e, z);
                                z = foo5(f, z);
                                if (!forF.add(z)) {
                                    continue;
                                }
                                currentZF = z;
                                for (int g = 9; g > 0; g--) {
                                    z = currentZF;
                                    z = foo6(g, z);
                                    if (!forG.add(z)) {
                                        continue;
                                    }
                                    int currentZG = z;
                                    for (int h = 9; h > 0; h--) {
                                        z = currentZG;
                                        z = foo7(h, z);
                                        if (!forH.add(z)) {
                                            continue;
                                        }
                                        int currentZH = z;
                                        for (int i = 9; i > 0; i--) {
                                            z = currentZH;
                                            z = foo8(i, z);
                                            if (!forI.add(z)) {
                                                continue;
                                            }
                                            int currentZI = z;
                                            for (int j = 9; j > 0; j--) {
                                                z = currentZI;
                                                z = foo9(j, z);
                                                if (!forJ.add(z)) {
                                                    continue;
                                                }
                                                int currentZJ = z;
                                                for (int k = 9; k > 0; k--) {
                                                    for (int l = 9; l > 0; l--) {
                                                        for (int m = 9; m > 0; m--) {
                                                            for (int n = 9; n > 0; n--) {
                                                                z = currentZJ;
                                                                z = foo10(k, z);
                                                                z = foo11(l, z);
                                                                z = foo12(m, z);
                                                                z = foo13(n, z);
                                                                if (z == 0) {
                                                                    StringBuilder sb = new StringBuilder();
                                                                    StringBuilder result = sb.append(a).append(b).append(c).append(d).append(e).append(f).append(g).append(h).append(i)
                                                                            .append(j).append(k).append(l).append(m).append(n);
                                                                    return result.toString();
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return "";
    }

    static String part2Answer() {
        Set<Integer> forF = new HashSet<>();
        Set<Integer> forG = new HashSet<>();
        Set<Integer> forH = new HashSet<>();
        Set<Integer> forI = new HashSet<>();
        Set<Integer> forJ = new HashSet<>();
        for (int a = 9; a > 0; a--) {
            for (int b = 9; b > 0; b--) {
                for (int c = 9; c > 0; c--) {
                    for (int d = 9; d > 0; d--) {
                        for (int e = 9; e > 0; e--) {
                            for (int f = 9; f > 0; f--) {
                                int currentZF;
                                int z = 0;
                                z = foo0(10 - a, z);
                                z = foo1(10 - b, z);
                                z = foo2(10 - c, z);
                                z = foo3(10 - d, z);
                                z = foo4(10 - e, z);
                                z = foo5(10 - f, z);
                                if (!forF.add(z)) {
                                    continue;
                                }
                                currentZF = z;
                                for (int g = 9; g > 0; g--) {
                                    z = currentZF;
                                    z = foo6(10 - g, z);
                                    if (!forG.add(z)) {
                                        continue;
                                    }
                                    int currentZG = z;
                                    for (int h = 9; h > 0; h--) {
                                        z = currentZG;
                                        z = foo7(10 - h, z);
                                        if (!forH.add(z)) {
                                            continue;
                                        }
                                        int currentZH = z;
                                        for (int i = 9; i > 0; i--) {
                                            z = currentZH;
                                            z = foo8(10 - i, z);
                                            if (!forI.add(z)) {
                                                continue;
                                            }
                                            int currentZI = z;
                                            for (int j = 9; j > 0; j--) {
                                                z = currentZI;
                                                z = foo9(10 - j, z);
                                                if (!forJ.add(z)) {
                                                    continue;
                                                }
                                                int currentZJ = z;
                                                for (int k = 9; k > 0; k--) {
                                                    for (int l = 9; l > 0; l--) {
                                                        for (int m = 9; m > 0; m--) {
                                                            for (int n = 9; n > 0; n--) {
                                                                z = currentZJ;
                                                                z = foo10(10 - k, z);
                                                                z = foo11(10 - l, z);
                                                                z = foo12(10 - m, z);
                                                                z = foo13(10 - n, z);
                                                                if (z == 0) {
                                                                    StringBuilder sb = new StringBuilder();
                                                                    StringBuilder result = sb.append(10 - a).append(10 - b).append(10 - c).append(10 - d).append(10 - e)
                                                                            .append(10 - f).append(10 - g).append(10 - h).append(10 - i)
                                                                            .append(10 - j).append(10 - k).append(10 - l).append(10 - m).append(10 - n);
                                                                    return result.toString();
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return "";
    }

    static int foo0(int w, int z) {
        return general(w, z, 2, 15, 13);
    }

    static int foo1(int w, int z) {
        return general(w, z, 1, 10, 16);
    }

    static int foo2(int w, int z) {
        return general(w, z, 1, 12, 2);
    }

    static int foo3(int w, int z) {
        return general(w, z, 1, 10, 8);
    }

    static int foo4(int w, int z) {
        return general(w, z, 1, 14, 11);
    }

    static int foo5(int w, int z) {
        return general(w, z, 26, -11, 6);
    }

    static int foo6(int w, int z) {
        return general(w, z, 1, 10, 12);
    }

    static int foo7(int w, int z) {
        return general(w, z, 26, -16, 2);
    }

    static int foo8(int w, int z) {
        return general(w, z, 26, -9, 2);
    }

    static int foo9(int w, int z) {
        return general(w, z, 1, 11, 15);
    }

    static int foo10(int w, int z) {
        return general(w, z, 26, -8, 1);
    }

    static int foo11(int w, int z) {
        return general(w, z, 26, -8, 10);
    }

    static int foo12(int w, int z) {
        return general(w, z, 26, -10, 14);
    }

    static int foo13(int w, int z) {
        return general(w, z, 26, -9, 10);
    }

    static int general(int w, int z, int zDivider, int xAppender, int yAppender) {
        int x = 0;
        x = x + z;
        x = x % 26;
        z = z / zDivider;
        x = x + xAppender;
        x = x != w ? 1 : 0;
        int y = 25 * x;
        y = y + 1;
        z = z * y;
        y = w;
        y = y + yAppender;
        y = y * x;
        z = z + y;
        return z;
    }

    static List<String> generateCode(List<String> instructions) {
        List<String> optimized = new ArrayList<>();
        optimized.add("long w = 0;");
        optimized.add("long x = 0;");
        optimized.add("long y = 0;");
        optimized.add("long z = 0;");
        int i = 0;
        for (String instruction : instructions) {
            String[] s = instruction.split(" ");
            String operation = s[0];
            char charToStoreValue = s[1].charAt(0);
            String second = s.length > 2 ? s[2] : "";
            if (operation.startsWith("inp")) {
                optimized.add("w = input[" + i + "]" + ";");
                i++;
            } else if (operation.startsWith("add")) {
                optimized.add(charToStoreValue + "= " + charToStoreValue + " + " + second + ";");
            } else if (operation.startsWith("mod")) {
                optimized.add(charToStoreValue + "= " + charToStoreValue + " % " + second + ";");
            } else if (operation.startsWith("mul")) {
                optimized.add(charToStoreValue + "= " + charToStoreValue + " * " + second + ";");
            } else if (operation.startsWith("div")) {
                optimized.add(charToStoreValue + "= " + charToStoreValue + " / " + second + ";");
            } else if (operation.startsWith("eql")) {
                optimized.add(charToStoreValue + "= " + charToStoreValue + " == " + second + "? 1 : 0" + ";");
            }
        }
        optimized.add("return z;");
        return optimized;
    }
}
