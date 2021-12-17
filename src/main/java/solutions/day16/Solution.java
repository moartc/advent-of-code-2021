package solutions.day16;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Solution {

    static long totalVersionSum = 0;

    public static void main(String[] args) throws IOException {

        URL resource = Solution.class.getResource("/day16.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).filter(Predicate.not(String::isEmpty)).toList();
        String message = hexToBinary(lines.get(0));
        Tuple decode = decode(message);
        System.out.println("part1 = " + totalVersionSum);
        System.out.println("part2 = " + decode.value);
    }

    static Tuple decode(String message) {
        totalVersionSum += getVersion(message);
        int packetType = getPacketType(message);
        if (packetType == 4) {
            return parseLiteral(message.substring(6));
        } else {
            return parseOperator(message.substring(6), packetType);
        }
    }

    private static int getVersion(String binary) {
        String version = binary.substring(0, 3);
        return Integer.valueOf(version, 2);
    }

    private static int getPacketType(String binary) {
        String packetTypeID = binary.substring(3, 6);
        return Integer.valueOf(packetTypeID, 2);
    }

    static Tuple parseOperator(String string, int mode) {
        char lengthType = string.charAt(0);
        if (lengthType == '0') {
            int subpacketLength = Integer.valueOf(string.substring(1, 16), 2);
            String subpacket = string.substring(16, 16 + subpacketLength);
            List<Long> tupleValues = new ArrayList<>();
            long processedBits = 0;
            while (processedBits != subpacketLength) {
                Tuple decode = decode(subpacket.substring((int) processedBits));
                tupleValues.add(decode.value);
                processedBits += decode.bits;
            }
            return new Tuple(processedBits + 22, getValue(tupleValues, mode));
        } else {
            int numberOfSubpackets = Integer.valueOf(string.substring(1, 12), 2);
            int decoded = 0;
            List<Long> tupleValues = new ArrayList<>();
            for (int i = 0; i < numberOfSubpackets; i++) {
                Tuple decode = decode(string.substring(12).substring(decoded));
                tupleValues.add(decode.value);
                decoded += decode.bits;
            }
            return new Tuple(decoded + 18, getValue(tupleValues, mode));
        }
    }

    static Long getValue(List<Long> values, int mode) {
        switch (mode) {
            case 0:
                return values.stream().mapToLong(Long::longValue).sum();
            case 1:
                return values.stream().mapToLong(Long::longValue).reduce(1, (a, b) -> a * b);
            case 2:
                return values.stream().min(Long::compareTo).get();
            case 3:
                return values.stream().max(Long::compareTo).get();
            case 5:
                return (values.get(0).compareTo(values.get(1)) > 0) ? 1L : 0L;
            case 6:
                return (values.get(0).compareTo(values.get(1)) < 0) ? 1L : 0L;
            case 7:
                return (values.get(0).equals(values.get(1))) ? 1L : 0L;
        }
        return null;
    }

    static Tuple parseLiteral(String string) {
        int processedBits = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < string.length(); i += 5) {
            String substringToProcess = string.substring(i, i + 5);
            sb.append(substringToProcess.substring(1));
            processedBits += 5;
            if (substringToProcess.charAt(0) == '0') {
                break;
            }
        }
        return new Tuple(processedBits + 6, Long.valueOf(sb.toString(), 2));
    }

    static String hexToBinary(String hex) {
        String binary = new BigInteger(hex, 16).toString(2);
        int expectedSize = (int) Math.ceil(binary.length() / 4.) * 4;
        return StringUtils.leftPad(binary, expectedSize, "0");
    }

    static class Tuple {
        long bits;
        long value;

        public Tuple(long bits, long value) {
            this.bits = bits;
            this.value = value;
        }
    }
}

