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

    SolutionTest() throws IOException {
    }

    @Test
    void getTestAnswerV2() throws IOException {

        URL resource = solutions.day16.Solution.class.getResource("/day19_test2.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).filter(Predicate.not(String::isEmpty)).toList();
        int testAnswer = Solution.getTestAnswer2(lines);
        assertEquals(79, testAnswer);

    }

}