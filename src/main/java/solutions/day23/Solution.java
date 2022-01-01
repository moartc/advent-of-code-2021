package solutions.day23;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {

    private static Logger log = LogManager.getLogger();
    static int lowestCost = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {

        URL resource = solutions.day16.Solution.class.getResource("/day23.txt");
        List<String> lines = Files.readAllLines(Paths.get(resource.getPath()));
        System.out.println("part1 = " + answerPart1(lines));
        System.out.println("part2 = " + answerPart2(lines));
    }

    static long answerPart1(List<String> input) {
        lowestCost = Integer.MAX_VALUE;
        char[] hallway = new char[]{'.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'};
        char[][] rooms = createInitialRoomState(input);
        return simulateMoves(rooms, hallway, 0);
    }

    static long answerPart2(List<String> input) {
        input.add(3, "  #D#C#B#A#");
        input.add(4, "  #D#B#A#C#");
        lowestCost = Integer.MAX_VALUE;
        char[] hallway = new char[]{'.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'};
        char[][] rooms = createInitialRoomState(input);
        return simulateMoves(rooms, hallway, 0);
    }

    static boolean isGameFinished(char[][] rooms) {
        for (int i = 0; i < 4; i++) {
            if (!isRoomFullyCompleted(i, rooms)) {
                return false;
            }
        }
        return true;
    }

    static char[][] getRoomCopy(char[][] original) {
        char[][] rooms = Arrays.stream(original).map(char[]::clone).toArray(char[][]::new);
        return rooms;
    }

    static char[] getHallwayCopy(char[] original) {
        return Arrays.copyOf(original, original.length);
    }

    static long simulateMoves(char[][] rooms, char[] hallway, int currentCost) {
        if (currentCost > lowestCost)
            return currentCost;
        if (isGameFinished(rooms)) {
            if (currentCost < lowestCost) {
                log.debug("found lowest = {}", currentCost);
                lowestCost = currentCost;
            }
            return currentCost;
        }
        for (int roomNo = 0; roomNo < 4; roomNo++) {
            for (int positionInRoom = 0; positionInRoom < rooms.length; positionInRoom++) {
                if (rooms[positionInRoom][roomNo] == '.') {
                    continue;
                }
                if (canAmphipodExitCurrentRoom(roomNo, positionInRoom, rooms)) {

                    Map<Integer, Integer> possibleMovePositionsFromRoomWithLength = getPossibleMovePositionsFromRoomWithLength(roomNo, positionInRoom, rooms, hallway);
                    for (Map.Entry<Integer, Integer> integerIntegerEntry : possibleMovePositionsFromRoomWithLength.entrySet()) {
                        var localCopyRooms = getRoomCopy(rooms);
                        var localCopyHallway = getHallwayCopy(hallway);
                        int newCost = moveFromRoomToHallway(roomNo, positionInRoom, localCopyRooms, localCopyHallway, currentCost, Pair.of(integerIntegerEntry.getKey(), integerIntegerEntry.getValue()));
                        simulateMoves(localCopyRooms, localCopyHallway, newCost);
                    }
                }
            }
        }
        for (int hallwayPosition = 0; hallwayPosition < 11; hallwayPosition++) {
            if (hallway[hallwayPosition] != '.') {
                var roomsCopy = getRoomCopy(rooms);
                var hallwayCopy = getHallwayCopy(hallway);
                Pair<Integer, Integer> positionInDestinationRoomAndCostWhenMovingFromHallway = getPositionInDestinationRoomAndCostWhenMovingFromHallway(hallwayPosition, hallwayCopy, roomsCopy);
                if (positionInDestinationRoomAndCostWhenMovingFromHallway.getLeft() != -1) {
                    int newCost = moveFromHallwayToDestinationRoom(hallwayPosition, roomsCopy, hallwayCopy, currentCost, positionInDestinationRoomAndCostWhenMovingFromHallway);
                    simulateMoves(roomsCopy, hallwayCopy, newCost);
                }
            }
        }
        return lowestCost;
    }

    static int getCostMultiplier(char c) {
        return (int) Math.pow(10, c - 'A');
    }

    static int moveFromRoomToDestinationRoom(int currentRoom, int currentPositionInRoom, char[][] rooms, int currentConst, Pair<Integer, Integer> newPositionAndCostOfMove) {
        char c = rooms[currentPositionInRoom][currentRoom];
        int destinationRoom = c - 'A';
        rooms[currentPositionInRoom][currentRoom] = '.';
        rooms[newPositionAndCostOfMove.getLeft()][destinationRoom] = c;
        currentConst += getCostMultiplier(c) * newPositionAndCostOfMove.getRight();
        return currentConst;
    }

    static int moveFromHallwayToDestinationRoom(int position, char[][] rooms, char[] hallway, int currentConst, Pair<Integer, Integer> newPositionAndCostOfMove) {
        char c = hallway[position];
        int destinationRoom = c - 'A';
        hallway[position] = '.';
        rooms[newPositionAndCostOfMove.getLeft()][destinationRoom] = c;
        currentConst += getCostMultiplier(c) * newPositionAndCostOfMove.getRight();
        return currentConst;
    }

    static int moveFromRoomToHallway(int room, int positionInRoom, char[][] rooms, char[] hallway, int currentConst, Pair<Integer, Integer> newPositionAndCostOfMove) {
        char c = rooms[positionInRoom][room];
        hallway[newPositionAndCostOfMove.getLeft()] = c;
        rooms[positionInRoom][room] = '.';
        currentConst += getCostMultiplier(c) * newPositionAndCostOfMove.getRight();
        return currentConst;
    }

    public static boolean canAmphipodExitCurrentRoom(int room, int positionInRoom, char[][] rooms) {
        char c = rooms[positionInRoom][room];
        if (positionInRoom > 0) {
            int tempPosition = positionInRoom;
            while (tempPosition > 0) {
                if (rooms[tempPosition - 1][room] != '.') {
                    return false;
                }
                tempPosition--;
            }
        }
        if (isRoomCorrectlyFilled(room, rooms)) {
            return false;
        }
        return true;
    }

    public static boolean isRoomFullyCompleted(int room, char[][] rooms) {
        char expectedType = (char) ('A' + room);
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i][room] != expectedType) {
                return false;
            }
        }
        return true;
    }

    public static boolean isRoomCorrectlyFilled(int room, char[][] rooms) {
        char expectedType = (char) ('A' + room);
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i][room] != expectedType && rooms[i][room] != '.') {
                return false;
            }
        }
        return true;
    }

    public static Pair<Integer, Integer> getPositionInDestinationRoomAndCostWhenMovingFromHallway(int positionInHallway, char[] hallway, char[][] rooms) {
        char c = hallway[positionInHallway];
        int destinationRoom = c - 'A';
        if (!isRoomAvailable(destinationRoom, rooms) || !isRoomCorrectlyFilled(destinationRoom, rooms)) {
            return Pair.of(-1, -1);
        } else {
            int destinationRoomPositionInHallwayIndex = 2 + destinationRoom * 2;
            int lowerIndex = Math.min(positionInHallway, destinationRoomPositionInHallwayIndex);
            int higherIndex = Math.max(positionInHallway, destinationRoomPositionInHallwayIndex);

            for (int i = lowerIndex; i <= higherIndex; i++) {
                if (i == positionInHallway) {
                    continue;
                }
                if (hallway[i] != '.') {
                    return Pair.of(-1, -1);
                }
            }
            int indexOfDestinationPositionInRoom = getIndexOfFDestinationPositionInRoom(destinationRoom, rooms);
            int totalDistance = indexOfDestinationPositionInRoom + (higherIndex - lowerIndex) + 1;
            return Pair.of(indexOfDestinationPositionInRoom, totalDistance);
        }
    }

    public static int getIndexOfFDestinationPositionInRoom(int roomNumber, char[][] rooms) {
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i][roomNumber] != '.') {
                return i - 1;
            }
        }
        return rooms.length - 1;
    }

    public static boolean isRoomAvailable(int room, char[][] rooms) {
        if (rooms[0][room] != '.') {
            return false;
        }
        for (int i = 1; i < rooms.length; i++) {
            char roomType = (char) ('A' + room);
            if (rooms[i][room] == '.') {
                return true;
            } else if (rooms[i][room] != roomType) {
                return false;
            }
        }
        return true;
    }

    public static Map<Integer, Integer> getPossibleMovePositionsFromRoomWithLength(int room, int positionInRoom, char[][] rooms, char[] hallway) {
        Map<Integer, Integer> positionsWithLength = new HashMap<>();
        if (positionInRoom > 0) { // if it's not in the first position
            int tempPosition = positionInRoom;
            while (tempPosition > 0) {
                if (rooms[tempPosition - 1][room] != '.') {
                    return positionsWithLength;
                }
                tempPosition--;
            }
        }
        if (room == 0) {
            if (hallway[1] == '.') {
                positionsWithLength.put(1, 2 + positionInRoom);
                if (hallway[0] == '.') {
                    positionsWithLength.put(0, 3 + positionInRoom);
                }
            }
            if (hallway[3] == '.') {
                positionsWithLength.put(3, 2 + positionInRoom);
                if (hallway[5] == '.') {
                    positionsWithLength.put(5, 4 + positionInRoom);
                    if (hallway[7] == '.') {
                        positionsWithLength.put(7, 6 + positionInRoom);
                        if (hallway[9] == '.') {
                            positionsWithLength.put(9, 8 + positionInRoom);
                            if (hallway[10] == '.') {
                                positionsWithLength.put(10, 9 + positionInRoom);
                            }
                        }
                    }
                }
            }
        } else if (room == 1) {
            if (hallway[3] == '.') {
                positionsWithLength.put(3, 2 + positionInRoom);
                if (hallway[1] == '.') {
                    positionsWithLength.put(1, 4 + positionInRoom);
                    if (hallway[0] == '.') {
                        positionsWithLength.put(0, 5 + positionInRoom);
                    }
                }
            }
            if (hallway[5] == '.') {
                positionsWithLength.put(5, 2 + positionInRoom);
                if (hallway[7] == '.') {
                    positionsWithLength.put(7, 4 + positionInRoom);
                    if (hallway[9] == '.') {
                        positionsWithLength.put(9, 6 + positionInRoom);
                        if (hallway[10] == '.') {
                            positionsWithLength.put(10, 7 + positionInRoom);
                        }
                    }
                }
            }
        } else if (room == 2) {
            if (hallway[5] == '.') {
                positionsWithLength.put(5, 2 + positionInRoom);
                if (hallway[3] == '.') {
                    positionsWithLength.put(3, 4 + positionInRoom);
                    if (hallway[1] == '.') {
                        positionsWithLength.put(1, 6 + positionInRoom);
                        if (hallway[0] == '.') {
                            positionsWithLength.put(0, 7 + positionInRoom);
                        }
                    }
                }
            }
            if (hallway[7] == '.') {
                positionsWithLength.put(7, 2 + positionInRoom);
                if (hallway[9] == '.') {
                    positionsWithLength.put(9, 4 + positionInRoom);
                    if (hallway[10] == '.') {
                        positionsWithLength.put(10, 5 + positionInRoom);
                    }
                }
            }
        } else if (room == 3) {
            if (hallway[7] == '.') {
                positionsWithLength.put(7, 2 + positionInRoom);
                if (hallway[5] == '.') {
                    positionsWithLength.put(5, 4 + positionInRoom);
                    if (hallway[3] == '.') {
                        positionsWithLength.put(3, 6 + positionInRoom);
                        if (hallway[1] == '.') {
                            positionsWithLength.put(1, 8 + positionInRoom);
                            if (hallway[0] == '.') {
                                positionsWithLength.put(0, 9 + positionInRoom);
                            }
                        }
                    }
                }
            }
            if (hallway[9] == '.') {
                positionsWithLength.put(9, 2 + positionInRoom);
                if (hallway[10] == '.') {
                    positionsWithLength.put(10, 3 + positionInRoom);
                }
            }
        }
        return positionsWithLength;
    }

    public static char[][] createInitialRoomState(List<String> input) {
        char[][] rooms = new char[input.size() - 3][4];
        for (int i = 2; i < input.size() - 1; i++) {
            String row = input.get(i).trim();
            int roomCounter = 0;
            for (char c : row.replaceAll("#", "").toCharArray()) {
                rooms[i - 2][roomCounter] = c;
                roomCounter++;
            }
        }
        return rooms;
    }

    private static String createPrintableBurrow(char[] hallway, char rooms[][]) {
        StringBuilder sb = new StringBuilder();
        sb.append("#");
        for (char c : hallway) {
            sb.append(c);
        }
        sb.append("#\n");
        sb.append("###" + rooms[0][0] + "#" + rooms[0][1] + "#" + rooms[0][2] + "#" + rooms[0][3] + "###\n");
        for (int i = 1; i < rooms.length; i++) {
            sb.append("  #" + rooms[i][0] + "#" + rooms[i][1] + "#" + rooms[i][2] + "#" + rooms[i][3] + "#\n");
        }
        return sb.toString();
    }
}


