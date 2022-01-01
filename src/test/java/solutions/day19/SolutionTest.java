package solutions.day19;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @Test
    void getAnswerPart1ExampleTest() throws IOException {

        URL resource = solutions.day16.Solution.class.getResource("/day19_test.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).filter(Predicate.not(String::isEmpty)).toList();
        int testAnswer = Solution.getFirstPartAnswer(lines);
        assertEquals(79, testAnswer);
    }

    @Test
    void getAnswerPart2ExampleTest() throws IOException {

        URL resource = solutions.day16.Solution.class.getResource("/day19_test.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).filter(Predicate.not(String::isEmpty)).toList();
        int testAnswer = Solution.getSecondPartAnswer(lines);
        assertEquals(3621, testAnswer);
    }
}