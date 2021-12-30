package solutions.day23;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {

    private static Logger log = LogManager.getLogger();

    public static void main(String[] args) throws IOException {

        URL resource = solutions.day16.Solution.class.getResource("/day23.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).toList();

    }

    static long answerPart1(List<String> input) {
        char[] hallway = new char[]{'.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'};
        char[][] rooms = createInitialRoomState(input);
        printBurrow(hallway, rooms);
        return 12;
    }

    public static Pair<Integer, Integer> canDirectlyMoveToDestinationRoomFromInitialRoom(int room, int positionInRoom, char[][] rooms, char[] hallway) {
        char c = rooms[positionInRoom][room];
        int destinationRoom = c - 'A';
        if(!isRoomAvailable(destinationRoom, rooms)) {
            return Pair.of(-1,-1);
        } else {
            int lowerIndexRoom = Math.min(room, destinationRoom);
            int higherIndexRoom = Math.max(room, destinationRoom);
            for(int i = 2 + (2*lowerIndexRoom); i <= 2 + (2*higherIndexRoom);i++) {
                if(hallway[i] != '.') {
                    return Pair.of(-1,-1);
                }
            }
            int indexOfFDestinationPositionInRoom = getIndexOfFDestinationPositionInRoom(destinationRoom, rooms);
            int totalDistance = positionInRoom + indexOfFDestinationPositionInRoom + (higherIndexRoom - lowerIndexRoom)*2 + 2;
            return Pair.of(indexOfFDestinationPositionInRoom, totalDistance);
        }
    }

    public static int getIndexOfFDestinationPositionInRoom(int roomNumber, char[][] rooms) {
        for(int i = 0; i < rooms.length; i++) {
            if(rooms[i][roomNumber] != '.') {
                return i-1;
            }
        }
        return rooms.length -1;
    }

    public static boolean isRoomAvailable(int room, char[][] rooms) {
        if(rooms[0][room] != '.') {
            return false;
        }
        for(int i = 1; i < rooms.length; i++) {
            char roomType = (char) ('A' - room);
            if(rooms[i][room] == '.') {
                return true;
            } else if(rooms[i][room] != roomType) {
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
                if (rooms[room][tempPosition] != '.') {
                    return positionsWithLength;
                }
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

    private static void printBurrow(char[] hallway, char rooms[][]) {
        System.out.println("#############");
        System.out.print("#");
        for (char c : hallway) {
            System.out.print(c);
        }
        System.out.println("#");
        System.out.println("###" + rooms[0][0] + "#" + rooms[0][1] + "#" + rooms[0][2] + "#" + rooms[0][3] + "###");
        for (int i = 1; i < rooms.length; i++) {
            System.out.println("  #" + rooms[i][0] + "#" + rooms[i][1] + "#" + rooms[i][2] + "#" + rooms[i][3] + "#");
        }
        System.out.println("  #########");
    }
}


