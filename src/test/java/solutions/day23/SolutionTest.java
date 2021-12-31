package solutions.day23;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class SolutionTest {

    private Solution solution = new Solution();
    private URL resource = solutions.day16.Solution.class.getResource("/day23_example.txt");

    @Test
    void answerPart1ExampleTest() throws IOException {
        List<String> lines = Files.lines(Paths.get(resource.getPath())).toList();
        solution.answerPart1(lines);
    }

    @Test
    void getPossibleMovePositionsWithLengthTest1() throws IOException {
        List<String> lines = Files.lines(Paths.get(resource.getPath())).toList();
        char[] hallway = new char[]{'.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'};
        char[][] rooms = Solution.createInitialRoomState(lines);

        Map<Integer, Integer> expectedMap = new HashMap<>();
        expectedMap.put(0, 3);
        expectedMap.put(1, 2);
        expectedMap.put(3, 2);
        expectedMap.put(5, 4);
        expectedMap.put(7, 6);
        expectedMap.put(9, 8);
        expectedMap.put(10, 9);

        Map<Integer, Integer> actualMap = Solution.getPossibleMovePositionsFromRoomWithLength(0, 0, rooms, hallway);
        assertThat(actualMap.size(), is(7));
        assertThat(actualMap, is(expectedMap));

        Map<Integer, Integer> actualMap2 = Solution.getPossibleMovePositionsFromRoomWithLength(0, 1, rooms, hallway);
        assertThat(actualMap2.size(), is(0));
    }

    @Test
    void getPossibleMovePositionsWithLengthTest2() throws IOException {
        List<String> lines = Files.lines(Paths.get(resource.getPath())).toList();
        char[] hallway = new char[]{'.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'};
        char[][] rooms = Solution.createInitialRoomState(lines);

        Map<Integer, Integer> expectedMap = new HashMap<>();
        expectedMap.put(0, 5);
        expectedMap.put(1, 4);
        expectedMap.put(3, 2);
        expectedMap.put(5, 2);
        expectedMap.put(7, 4);
        expectedMap.put(9, 6);
        expectedMap.put(10, 7);

        Map<Integer, Integer> actualMap = Solution.getPossibleMovePositionsFromRoomWithLength(1, 0, rooms, hallway);
        assertThat(actualMap.size(), is(7));
        assertThat(actualMap, is(expectedMap));
    }

    @Test
    void getPossibleMovePositionsWithLengthTest3() throws IOException {
        List<String> lines = Files.lines(Paths.get(resource.getPath())).toList();
        char[] hallway = new char[]{'.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'};
        char[][] rooms = Solution.createInitialRoomState(lines);

        Map<Integer, Integer> expectedMap = new HashMap<>();
        expectedMap.put(0, 7);
        expectedMap.put(1, 6);
        expectedMap.put(3, 4);
        expectedMap.put(5, 2);
        expectedMap.put(7, 2);
        expectedMap.put(9, 4);
        expectedMap.put(10, 5);

        Map<Integer, Integer> actualMap = Solution.getPossibleMovePositionsFromRoomWithLength(2, 0, rooms, hallway);
        assertThat(actualMap.size(), is(7));
        assertThat(actualMap, is(expectedMap));
    }

    @Test
    void getPossibleMovePositionsWithLengthTest4() throws IOException {
        List<String> lines = Files.lines(Paths.get(resource.getPath())).toList();
        char[] hallway = new char[]{'.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'};
        char[][] rooms = Solution.createInitialRoomState(lines);

        Map<Integer, Integer> expectedMap = new HashMap<>();
        expectedMap.put(0, 9);
        expectedMap.put(1, 8);
        expectedMap.put(3, 6);
        expectedMap.put(5, 4);
        expectedMap.put(7, 2);
        expectedMap.put(9, 2);
        expectedMap.put(10, 3);

        Map<Integer, Integer> actualMap = Solution.getPossibleMovePositionsFromRoomWithLength(3, 0, rooms, hallway);
        assertThat(actualMap.size(), is(7));
        assertThat(actualMap, is(expectedMap));
    }

    @Test
    void getPossibleMovePositionsWithLengthTest5() throws IOException {
        List<String> lines = Files.lines(Paths.get(resource.getPath())).toList();
        char[] hallway = new char[]{'.', '.', '.', 'A', '.', '.', '.', '.', '.', '.', '.'};
        char[][] rooms = Solution.createInitialRoomState(lines);

        Map<Integer, Integer> expectedMap = new HashMap<>();
        expectedMap.put(5, 2);
        expectedMap.put(7, 2);
        expectedMap.put(9, 4);
        expectedMap.put(10, 5);

        Map<Integer, Integer> actualMap = Solution.getPossibleMovePositionsFromRoomWithLength(2, 0, rooms, hallway);
        assertThat(actualMap.size(), is(4));
        assertThat(actualMap, is(expectedMap));
    }

    @Test
    void canDirectlyMoveToDestinationRoomFromInitialRoomTest1() {
        char[] hallway = new char[]{'.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'};
        char[][] rooms = new char[][]{{'.', 'A', '.', '.'}, {'B', 'C', 'D', 'A'}};

        Pair<Integer, Integer> expectedPair = Pair.of(-1, -1);
        Pair<Integer, Integer> actualPair = Solution.canDirectlyMoveToDestinationRoomFromInitialRoom(1, 0, rooms, hallway);
        Assertions.assertEquals(expectedPair.getLeft(), actualPair.getLeft());
        Assertions.assertEquals(expectedPair.getRight(), actualPair.getRight());
    }

    @Test
    void canDirectlyMoveToDestinationRoomFromInitialRoomTest2() {
        char[] hallway = new char[]{'.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'};
        char[][] rooms = new char[][]{{'.', 'A', '.', '.'}, {'A', 'C', 'D', 'B'}};

        Pair<Integer, Integer> expectedPair = Pair.of(0, 4);
        Pair<Integer, Integer> actualPair = Solution.canDirectlyMoveToDestinationRoomFromInitialRoom(1, 0, rooms, hallway);
        Assertions.assertEquals(expectedPair.getLeft(), actualPair.getLeft());
        Assertions.assertEquals(expectedPair.getRight(), actualPair.getRight());
    }

    @Test
    void canDirectlyMoveToDestinationRoomFromInitialRoomTest3() {
        char[] hallway = new char[]{'.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'};
        char[][] rooms = new char[][]{{'.', 'A', '.', '.'}, {'.', 'C', 'D', 'B'}};

        Pair<Integer, Integer> expectedPair = Pair.of(1, 5);
        Pair<Integer, Integer> actualPair = Solution.canDirectlyMoveToDestinationRoomFromInitialRoom(1, 0, rooms, hallway);
        Assertions.assertEquals(expectedPair.getLeft(), actualPair.getLeft());
        Assertions.assertEquals(expectedPair.getRight(), actualPair.getRight());
    }

    @Test
    void isRoomAvailable() {
        char[][] rooms = new char[][]{{'.', 'A', '.', '.'}, {'.', 'C', 'D', 'B'}};
        Assertions.assertTrue(Solution.isRoomAvailable(0, rooms));
        Assertions.assertFalse(Solution.isRoomAvailable(1, rooms));
        Assertions.assertFalse(Solution.isRoomAvailable(2, rooms));
        Assertions.assertFalse(Solution.isRoomAvailable(3, rooms));

        char[][] rooms2 = new char[][]{{'A', 'A', '.', '.'}, {'A', 'C', 'D', 'B'}};
        Assertions.assertFalse(Solution.isRoomAvailable(0, rooms2));

        char[][] rooms3 = new char[][]{{'.', '.', '.', '.'}, {'A', 'C', 'D', 'B'}};
        Assertions.assertTrue(Solution.isRoomAvailable(0, rooms3));
        Assertions.assertFalse(Solution.isRoomAvailable(1, rooms3));
        Assertions.assertFalse(Solution.isRoomAvailable(2, rooms3));
        Assertions.assertFalse(Solution.isRoomAvailable(3, rooms3));
    }

    @Test
    void getPositionInDestinationRoomAndCostWhenMovingFromHallwayTest1() {
        char[] hallway = new char[]{'A', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'};
        char[][] rooms = new char[][]{{'.', 'A', '.', '.'}, {'.', 'C', 'D', 'B'}};

        Pair<Integer, Integer> expectedPair = Pair.of(1, 4);
        Pair<Integer, Integer> actualPair = Solution.getPositionInDestinationRoomAndCostWhenMovingFromHallway(0, hallway, rooms);
        Assertions.assertEquals(expectedPair.getLeft(), actualPair.getLeft());
        Assertions.assertEquals(expectedPair.getRight(), actualPair.getRight());
    }

    @Test
    void getPositionInDestinationRoomAndCostWhenMovingFromHallwayTest2() {
        char[] hallway = new char[]{'A', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'};
        char[][] rooms = new char[][]{{'.', 'A', '.', '.'}, {'A', 'C', 'D', 'B'}};

        Pair<Integer, Integer> expectedPair = Pair.of(0, 3);
        Pair<Integer, Integer> actualPair = Solution.getPositionInDestinationRoomAndCostWhenMovingFromHallway(0, hallway, rooms);
        Assertions.assertEquals(expectedPair.getLeft(), actualPair.getLeft());
        Assertions.assertEquals(expectedPair.getRight(), actualPair.getRight());
    }

    @Test
    void getPositionInDestinationRoomAndCostWhenMovingFromHallwayTest3() {
        char[] hallway = new char[]{'B', '.', '.', '.', '.', '.', '.', '.', 'A', '.', '.'};
        char[][] rooms = new char[][]{{'.', 'A', '.', '.'}, {'A', 'C', 'D', 'B'}};

        Pair<Integer, Integer> expectedPair = Pair.of(0, 7);
        Pair<Integer, Integer> actualPair = Solution.getPositionInDestinationRoomAndCostWhenMovingFromHallway(8, hallway, rooms);
        Assertions.assertEquals(expectedPair.getLeft(), actualPair.getLeft());
        Assertions.assertEquals(expectedPair.getRight(), actualPair.getRight());
    }

    @Test
    void getPositionInDestinationRoomAndCostWhenMovingFromHallwayTest4() {
        char[] hallway = new char[]{'B', '.', '.', 'C', '.', '.', '.', '.', 'A', '.', '.'};
        char[][] rooms = new char[][]{{'.', 'A', '.', '.'}, {'A', 'C', 'D', 'B'}};

        Pair<Integer, Integer> expectedPair = Pair.of(-1, -1);
        Pair<Integer, Integer> actualPair = Solution.getPositionInDestinationRoomAndCostWhenMovingFromHallway(8, hallway, rooms);
        Assertions.assertEquals(expectedPair.getLeft(), actualPair.getLeft());
        Assertions.assertEquals(expectedPair.getRight(), actualPair.getRight());
    }

    @Test
    void canAmphipodExitCurrentRoomTest1() {
        /*
        ###.#A#.#.###
          #A#C#D#B#
         */
        char[][] rooms = new char[][]{{'.', 'A', '.', '.'}, {'A', 'C', 'D', 'B'}};
        // it's in destination room
        Assertions.assertFalse(Solution.canAmphipodExitCurrentRoom(0, 1, rooms));
        // it's blocked
        Assertions.assertFalse(Solution.canAmphipodExitCurrentRoom(1, 1, rooms));

        Assertions.assertTrue(Solution.canAmphipodExitCurrentRoom(1, 0, rooms));

        // destination room contains incorrect aphipods
        Assertions.assertFalse(Solution.canAmphipodExitCurrentRoom(2, 1, rooms));

        // destination room contains incorrect aphipods and room is full
        Assertions.assertFalse(Solution.canAmphipodExitCurrentRoom(3, 1, rooms));
    }

    @Test
    void canAmphipodExitCurrentRoomTest2() {
        /*
        ###.#.#.#.###
          #A#B#.#.#
          #A#B#C#.#
          #A#C#C#D#
         */
        char[][] rooms = new char[][]{{'.', '.', '.', '.'}, {'A', 'B', 'D', 'B'}, {'A', 'B', 'C', 'D'}, {'A', 'C', 'C', 'D'}};
        // it's in destination room
        Assertions.assertFalse(Solution.canAmphipodExitCurrentRoom(0, 1, rooms));
        Assertions.assertFalse(Solution.canAmphipodExitCurrentRoom(0, 2, rooms));
        Assertions.assertFalse(Solution.canAmphipodExitCurrentRoom(0, 3, rooms));

        // it's in destination, but the room contains incorrect amphipod
        Assertions.assertTrue(Solution.canAmphipodExitCurrentRoom(1, 1, rooms));
        // it's blocked
        Assertions.assertFalse(Solution.canAmphipodExitCurrentRoom(1, 2, rooms));
        Assertions.assertFalse(Solution.canAmphipodExitCurrentRoom(1, 3, rooms));

        // it's in destination room
        Assertions.assertFalse(Solution.canAmphipodExitCurrentRoom(2, 2, rooms));
        // it's blocked but also it's in destination room
        Assertions.assertFalse(Solution.canAmphipodExitCurrentRoom(2, 3, rooms));

        // it's in destination room
        Assertions.assertFalse(Solution.canAmphipodExitCurrentRoom(3, 3, rooms));
    }
}