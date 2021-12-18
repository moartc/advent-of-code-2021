package solutions.day18;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {

    @Test
    void getSecondPartAnswer() {
        List<String> input = Arrays.asList(
                "[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]",
                "[[[5,[2,8]],4],[5,[[9,9],0]]]",
                "[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]",
                "[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]",
                "[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]",
                "[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]",
                "[[[[5,4],[7,7]],8],[[8,3],8]]",
                "[[9,3],[[9,9],[6,[4,9]]]]",
                "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]",
                "[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]");
        int expected = 3993;
        int actual = Solution.getSecondPartAnswer(input);
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> parametersForComputeMagnitude() {
        return Stream.of(
                Arguments.of("[[1,2],[[3,4],5]]", 143),
                Arguments.of("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", 1384),
                Arguments.of("[[[[1,1],[2,2]],[3,3]],[4,4]]", 445),
                Arguments.of("[[[[5,0],[7,4]],[5,5]],[6,6]]", 1137),
                Arguments.of("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]", 3488)
        );
    }

    @ParameterizedTest
    @MethodSource("parametersForComputeMagnitude")
    void computeMagnitude(String input, int expected) {
        int actual = Solution.computeMagnitude(input);
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> parametersForCreateFinalString() {
        return Stream.of(
                Arguments.of(Arrays.asList("[1,1]", "[2,2]", "[3,3]", "[4,4]"),
                        "[[[[1,1],[2,2]],[3,3]],[4,4]]"),
                Arguments.of(Arrays.asList("[1,1]", "[2,2]", "[3,3]", "[4,4]", "[5,5]"),
                        "[[[[3,0],[5,3]],[4,4]],[5,5]]"),
                Arguments.of(Arrays.asList("[1,1]", "[2,2]", "[3,3]", "[4,4]", "[5,5]", "[6,6]"),
                        "[[[[5,0],[7,4]],[5,5]],[6,6]]"),
                Arguments.of(Arrays.asList("[[[[4,3],4],4],[7,[[8,4],9]]]", "[1,1]"),
                        "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"),
                Arguments.of(Arrays.asList("[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]",
                                "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]"),
                        "[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]"),
                Arguments.of(Arrays.asList("[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]",
                                "[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]"),
                        "[[[[6,7],[6,7]],[[7,7],[0,7]]],[[[8,7],[7,7]],[[8,8],[8,0]]]]"),
                Arguments.of(Arrays.asList("[[[[6,7],[6,7]],[[7,7],[0,7]]],[[[8,7],[7,7]],[[8,8],[8,0]]]]",
                                "[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]"),
                        "[[[[7,0],[7,7]],[[7,7],[7,8]]],[[[7,7],[8,8]],[[7,7],[8,7]]]]"),
                Arguments.of(Arrays.asList("[[[[7,0],[7,7]],[[7,7],[7,8]]],[[[7,7],[8,8]],[[7,7],[8,7]]]]",
                        "[7,[5,[[3,8],[1,4]]]]"), "[[[[7,7],[7,8]],[[9,5],[8,7]]],[[[6,8],[0,8]],[[9,9],[9,0]]]]"),
                Arguments.of(Arrays.asList("[[[[7,7],[7,8]],[[9,5],[8,7]]],[[[6,8],[0,8]],[[9,9],[9,0]]]]",
                        "[[2,[2,2]],[8,[8,1]]]"), "[[[[6,6],[6,6]],[[6,0],[6,7]]],[[[7,7],[8,9]],[8,[8,1]]]]"),
                Arguments.of(Arrays.asList("[[[[6,6],[6,6]],[[6,0],[6,7]]],[[[7,7],[8,9]],[8,[8,1]]]]",
                        "[2,9]"), "[[[[6,6],[7,7]],[[0,7],[7,7]]],[[[5,5],[5,6]],9]]"),
                Arguments.of(Arrays.asList("[[[[6,6],[7,7]],[[0,7],[7,7]]],[[[5,5],[5,6]],9]]",
                        "[1,[[[9,3],9],[[9,0],[0,7]]]]"), "[[[[7,8],[6,7]],[[6,8],[0,8]]],[[[7,7],[5,0]],[[5,5],[5,6]]]]"),
                Arguments.of(Arrays.asList("[[[[7,8],[6,7]],[[6,8],[0,8]]],[[[7,7],[5,0]],[[5,5],[5,6]]]]",
                        "[[[5,[7,4]],7],1]"), "[[[[7,7],[7,7]],[[8,7],[8,7]]],[[[7,0],[7,7]],9]]"),
                Arguments.of(Arrays.asList("[[[[7,7],[7,7]],[[8,7],[8,7]]],[[[7,0],[7,7]],9]]",
                        "[[[[4,2],2],6],[8,7]]"), "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"),
                Arguments.of(Arrays.asList(
                                "[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]",
                                "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]",
                                "[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]",
                                "[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]",
                                "[7,[5,[[3,8],[1,4]]]]",
                                "[[2,[2,2]],[8,[8,1]]]",
                                "[2,9]",
                                "[1,[[[9,3],9],[[9,0],[0,7]]]]",
                                "[[[5,[7,4]],7],1]",
                                "[[[[4,2],2],6],[8,7]]"),
                        "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]")
        );
    }

    @ParameterizedTest
    @MethodSource("parametersForCreateFinalString")
    void createFinalString(List<String> input, String expected) {
        String actual = Solution.createFinalString(input);
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> parametersForExplode() {
        return Stream.of(
                Arguments.of("[[[[[9,8],1],2],3],4]", "[[[[0,9],2],3],4]"),
                Arguments.of("[7,[6,[5,[4,[3,2]]]]]", "[7,[6,[5,[7,0]]]]"),
                Arguments.of("[[6,[5,[4,[3,2]]]],1]", "[[6,[5,[7,0]]],3]"),
                Arguments.of("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]", "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]"),
                Arguments.of("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]", "[[3,[2,[8,0]]],[9,[5,[7,0]]]]"),
                Arguments.of("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]", "[[[[0,7],4],[7,[[8,4],9]]],[1,1]]"),
                Arguments.of("[[[[0,7],4],[7,[[8,4],9]]],[1,1]]", "[[[[0,7],4],[15,[0,13]]],[1,1]]")
        );
    }

    @ParameterizedTest
    @MethodSource("parametersForExplode")
    void explode(String input, String expected) {
        String actual = Solution.explode(input);
        assertEquals(expected, actual);
    }


    private static Stream<Arguments> parametersForSplit() {
        return Stream.of(
                Arguments.of("[15]", "[[7,8]]"),
                Arguments.of("[[[[0,7],4],[[7,8],[0,13]]],[1,1]]", "[[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]"),
                Arguments.of("[[[[0,7],4],[[7,8],[0,12]]],[1,1]]", "[[[[0,7],4],[[7,8],[0,[6,6]]]],[1,1]]")
        );
    }

    @ParameterizedTest
    @MethodSource("parametersForSplit")
    void split(String input, String expected) {
        String actual = Solution.split(input);
        assertEquals(expected, actual);
    }

    @Test
    void addTwo() {
        String first = "[[[[4,3],4],4],[7,[[8,4],9]]]";
        String second = "[1,1]";
        String actual = Solution.addTwo(first, second);
        String expected = "[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]";
        assertEquals(expected, actual);
    }

    @Test
    void addToLeftChar() {
    }
}