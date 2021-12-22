package solutions.day22;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @Test
    void answerPart1ExampleTest1() throws IOException {

        URL resource = Solution.class.getResource("/day22_test1.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).toList();
        long expected = 39;
        long actual = Solution.answerPart1(lines);
        assertEquals(expected, actual);
    }

    @Test
    void answerPart1ExampleTest2() throws IOException {
        URL resource = Solution.class.getResource("/day22_test2.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).toList();
        long expected = 590784;
        long actual = Solution.answerPart1(lines);
        assertEquals(expected, actual);
    }

    @Test
    void answerPart2ExampleTest1() throws IOException {
        URL resource = Solution.class.getResource("/day22_test3.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).toList();
        long expected = 2758514936282235L;
        long actual = Solution.answerPart2(lines);
        assertEquals(expected, actual);
    }

    @Test
    void answerPart2() {
    }
}