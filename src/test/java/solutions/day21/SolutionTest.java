package solutions.day21;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {

    @Test
    void exampleAnswerPart1Test() {
        int startingPosition1 = 4;
        int startingPosition2 = 8;
        long expected = 739785;
        long actual = Solution.answerPart1(startingPosition1, startingPosition2);
        assertEquals(expected, actual);
    }

    @Test
    void exampleAnswerPart2Test() {
        int startingPosition1 = 4;
        int startingPosition2 = 8;
        long expected = 444356092776315L;
        long actual = Solution.answerPart2(startingPosition1, startingPosition2);
        assertEquals(expected, actual);
    }

    @Test
    void answerPart1Test() {
        int startingPosition1 = 7;
        int startingPosition2 = 9;
        long expected = 1234;
        long actual = Solution.answerPart1(startingPosition1, startingPosition2);
        assertEquals(expected, actual);
    }

    @Test
    void getNextPosition() {

        assertEquals(10, Solution.getNextPosition(4, 6));
        assertEquals(3, Solution.getNextPosition(8, 15));
        assertEquals(4, Solution.getNextPosition(10, 14));
    }
}