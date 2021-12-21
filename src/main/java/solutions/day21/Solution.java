package solutions.day21;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Solution {

    private static Logger log = LogManager.getLogger(Solution.class);

    public static void main(String[] args) throws IOException {

        URL resource = solutions.day16.Solution.class.getResource("/day21.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).toList();

        int firstStartingPosition = Integer.parseInt(lines.get(0).substring(lines.get(0).indexOf(":") + 1));
        int secondStartingPosition = Integer.parseInt(lines.get(0).substring(lines.get(0).indexOf(":") + 1));


//        System.out.println("part1 = " + answerPart1(firstStartingPosition, secondStartingPosition));
        System.out.println("part2 = ");
    }

    static long answerPart1(int firstStartingPosition, int secondStartingPosition) {

        long score1 = 0;
        int currentPosition1 = firstStartingPosition;
        long score2 = 0;
        int currentPosition2 = secondStartingPosition;

        int num = 0;
        while (score1 <= 1000 || score2 <= 1000) {
            long move1 = getNextRollNumberSum((3 * num) + 1);
            log.info("move1 {}", move1);
            currentPosition1 = getNextPosition(currentPosition1, move1);
            log.info("pos1 {}", currentPosition1);
            score1 += currentPosition1;
            if (score1 >= 1000)
                break;
            log.info("play1 pos = {} score = {}", currentPosition1, score1);
            num++;
            long move2 = getNextRollNumberSum((3 * num) + 1);
            log.info("move2 {}", move2);
            currentPosition2 = getNextPosition(currentPosition2, move2);
            log.info("pos2 {}", currentPosition2);
            score2 += currentPosition2;
            num++;
            log.info("play2 pos = {} score = {}", currentPosition2, score2);
        }

        long lowerScore = Math.min(score1, score2);
        log.info("lower score = {}", lowerScore);
        long diceRoll = (num + 1) * 3;
        log.info("dice rolled {} times", diceRoll);
        long result = diceRoll * lowerScore;
        log.info("result {}", result);

        return result;
    }

    static long answerPart2(int firstStartingPosition, int secondStartingPosition) {

        playGame(0, firstStartingPosition, 0, secondStartingPosition, true);
        return Math.max(win1, win2);
    }

    static long win1 = 0;
    static long win2 = 0;

    static void playGame(long s1, int pos1, long s2, int pos2, boolean startFirst) {
        long score1 = s1;
        int currentPosition1 = pos1;
        long score2 = s2;
        int currentPosition2 = pos2;

        while (score1 <= 21 || score2 <= 21) {
            if (startFirst) {
                int newPos1 = getNextPosition(currentPosition1, 1);
                int newPos2 = getNextPosition(currentPosition1, 2);
                int newPos3 = getNextPosition(currentPosition1, 3);
                if (score1 + newPos1 >= 21) {
                    win1++;
                    break;
                }
                if (score1 + newPos2 >= 21) {
                    win1++;
                    break;
                }
                if (score1 + newPos3 >= 21) {
                    win1++;
                    break;
                }
                playGame(score1 + newPos1, newPos1, score2, pos2, false);
                playGame(score1 + newPos2, newPos2, score2, pos2, false);
                playGame(score1 + newPos3, newPos3, score2, pos2, false);
            } else {
                int newPos1 = getNextPosition(currentPosition2, 1);
                int newPos2 = getNextPosition(currentPosition2, 2);
                int newPos3 = getNextPosition(currentPosition2, 3);
                if (score2 + newPos1 >= 21) {
                    win2++;
                    break;
                }

                if (score2 + newPos2 >= 21) {
                    win2++;
                    break;
                }
                if (score2 + newPos3 >= 21) {
                    win2++;
                    break;
                }
                playGame(score1, pos1, score2 + newPos1, newPos1, true);
                playGame(score1, pos1, score2 + newPos2, newPos2, true);
                playGame(score1, pos1, score2 + newPos3, newPos3, true);
            }
            log.info("win1 = {}, win2 = {}", win1, win2);
        }
    }

    static int getNextPosition(int start, long move) {
        long pos = (start + move) % 10;
        return pos == 0 ? 10 : (int) pos;
    }

    static long getNextRollNumberSum(long prevFirst) {
        return prevFirst + 1 + prevFirst + 2 + prevFirst;
    }
}



