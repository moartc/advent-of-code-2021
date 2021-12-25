package solutions.day22;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {

    @Test
    void newSolutionTest1() {
        List<String> lines = List.of("on x=10..12,y=10..12,z=10..12",
                "on x=11..13,y=11..13,z=11..13",
                "off x=9..11,y=9..11,z=9..11",
                "on x=10..10,y=10..10,z=10..10");

        assertEquals(BigInteger.valueOf(27), Solution.answerPart2(lines.subList(0, 1)));
        assertEquals(BigInteger.valueOf(46), Solution.answerPart2(lines.subList(0, 2)));
        assertEquals(BigInteger.valueOf(38), Solution.answerPart2(lines.subList(0, 3)));
        assertEquals(BigInteger.valueOf(39), Solution.answerPart2(lines));
    }

    @Test
    void newSolutionTest2() {
        List<String> lines = List.of("on x=10..12,y=10..12,z=10..12",
                "on x=10..12,y=10..12,z=10..12",
                "on x=10..12,y=10..12,z=10..12",
                "on x=10..12,y=10..12,z=10..12");

        assertEquals(BigInteger.valueOf(27), Solution.answerPart2(lines.subList(0, 1)));
        assertEquals(BigInteger.valueOf(27), Solution.answerPart2(lines.subList(0, 2)));
        assertEquals(BigInteger.valueOf(27), Solution.answerPart2(lines.subList(0, 3)));
        assertEquals(BigInteger.valueOf(27), Solution.answerPart2(lines));
    }

    @Test
    void newSolutionTest3() {
        List<String> lines = List.of("on x=0..2,y=0..2,z=0..2",
                "off x=0..2,y=0..2,z=0..2");

        assertEquals(BigInteger.valueOf(0), Solution.answerPart2(lines));
    }

    @Test
    void newSolutionTest4() {
        List<String> lines = List.of("on x=0..1,y=0..1,z=0..1");
        assertEquals(BigInteger.valueOf(8), Solution.answerPart2(lines));
    }

    @Test
    void newSolutionTest5() {
        List<String> lines = List.of("on x=0..1,y=0..1,z=0..1",
                "on x=1..2,y=1..2,z=1..2");
        assertEquals(BigInteger.valueOf(15), Solution.answerPart2(lines));
    }

    @Test
    void newSolutionTest6() {
        List<String> lines = List.of("on x=10..12,y=10..12,z=10..12",
                "on x=10..12,y=10..12,z=10..12",
                "on x=10..12,y=10..12,z=10..12");
        assertEquals(BigInteger.valueOf(27), Solution.answerPart2(lines));
    }

    @Test
    void newSolutionTest7() {
        List<String> lines = List.of("on x=0..2,y=0..2,z=0..2",
                "off x=2..2,y=2..2,z=2..2");

        assertEquals(BigInteger.valueOf(26), Solution.answerPart2(lines));
    }

    @Test
    void newSolutionTest8() {
        List<String> lines = List.of("off x=0..2,y=0..2,z=0..2");

        assertEquals(BigInteger.valueOf(0), Solution.answerPart2(lines));
    }

    @Test
    void newSolutionTest9() {
        List<String> lines = List.of("off x=0..2,y=0..2,z=0..2",
                "on x=0..1,y=0..1,z=0..1");

        assertEquals(BigInteger.valueOf(8), Solution.answerPart2(lines));
    }

    @Test
    void newSolutionTest10() {
        List<String> lines = List.of("off x=0..2,y=0..2,z=0..2",
                "on x=1..1,y=1..1,z=1..1");

        assertEquals(BigInteger.valueOf(1), Solution.answerPart2(lines));
    }
}