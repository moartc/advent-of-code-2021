package solutions.day22;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @Test
    @Disabled
    void answerPart1ExampleTest1() throws IOException {
        URL resource = Solution.class.getResource("/day22_test1.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).toList();
        long expected = 39;
        long actual = Solution.answerPart1(lines);
        assertEquals(expected, actual);
    }

    @Test
    @Disabled
    void answerPart1ExampleTest2() throws IOException {
        URL resource = Solution.class.getResource("/day22_test2.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).toList();
        long expected = 590784;
        long actual = Solution.answerPart1(lines);
        assertEquals(expected, actual);
    }

    @Test
    @Disabled
    void answerPart2ExampleTest1() throws IOException {
        URL resource = Solution.class.getResource("/day22_test3.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).toList();
        long expected = 2758514936282235L;
        long actual = Solution.answerPart2(lines);
        assertEquals(expected, actual);
    }

    @Test
    void newSolutionTest1()  {
        List<String> lines = List.of("on x=10..12,y=10..12,z=10..12","on x=11..13,y=11..13,z=11..13");

        assertEquals(27, Solution.answerPart2(lines.subList(0,1)));
        assertEquals(46, Solution.answerPart2(lines.subList(0,2)));
    }

    @Test
    void answerPart2() {
    }
}