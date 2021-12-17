package solutions.day16;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class Solution {

    static long totalVersionSum = 0;

    public static void main(String[] args) throws IOException {

        URL resource = Solution.class.getResource("/day16.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).filter(Predicate.not(String::isEmpty)).toList();
        String message = lines.get(0);
        Tuple decode = decode(message, true, -1);
        System.out.println("part1 = " + totalVersionSum);
        System.out.println("part2 = " + decode.value);
    }

    static Tuple decode(String message, boolean prepare, int mode) {
        String binary;
        if (prepare) {
            binary = createBin(message);
        } else {
            binary = message;
        }
        System.out.println("Start decode for binary: " + binary);

        int version = getVersion(binary);
        System.out.println("V = " + version);
        totalVersionSum += version;
        long packetType = getPacketType(binary);
        System.out.println("T = " + packetType);
        if (packetType == 4) {
            System.out.println("will process literal");
            return getLiteralValue(binary.substring(6)).addToBits(6);
        } else if (packetType == 0) {
            String toProcess = binary.substring(6);
            System.out.println("will process operator");
            return parseOperator(toProcess, 0).addToBits(6);
        } else if (packetType == 1) {
            String toProcess = binary.substring(6);
            System.out.println("will process operator");
            return parseOperator(toProcess, 1).addToBits(6);
        } else if (packetType == 2) {
            String toProcess = binary.substring(6);
            System.out.println("will process operator");
            return parseOperator(toProcess, 2).addToBits(6);
        } else if (packetType == 3) {
            String toProcess = binary.substring(6);
            System.out.println("will process operator");
            return parseOperator(toProcess, 3).addToBits(6);
        } else if (packetType == 5) {
            String toProcess = binary.substring(6);
            System.out.println("will process operator");
            return parseOperator(toProcess, 5).addToBits(6);
        } else if (packetType == 6) {
            String toProcess = binary.substring(6);
            System.out.println("will process operator");
            return parseOperator(toProcess, 6).addToBits(6);
        } else if (packetType == 7) {
            String toProcess = binary.substring(6);
            System.out.println("will process operator");
            return parseOperator(toProcess, 7).addToBits(6);
        } else {
            String toProcess = binary.substring(6);
            System.out.println("will process operator");
            return parseOperator(toProcess, mode).addToBits(6);
        }
    }

    private static int getVersion(String binary) {
        String version = binary.substring(0, 3);
        int intVersion = Integer.valueOf(version, 2);
        return intVersion;
    }

    private static long getPacketType(String binary) {
        String packetTypeID = binary.substring(3, 6);
        int intPacketType = Integer.valueOf(packetTypeID, 2);
        return intPacketType;
    }

    public static Tuple parseOperator(String string, int mode) {
        System.out.println("Operator to process = " + string);
        char lengthType = string.charAt(0);
        System.out.println("length type = " + lengthType);
        System.out.println("I = " + lengthType);
        if (lengthType == '0') {
            String lengthSubpack = string.substring(1, 16);
            int subpacketLength = Integer.valueOf(lengthSubpack, 2);
            System.out.println("L = " + subpacketLength);
            String subpacket = string.substring(16, 16 + subpacketLength);
            System.out.println("subpacket length = " + subpacket.length());
            System.out.println("subpacket to process = " + subpacket);
            List<BigInteger> tupleValues = new ArrayList<>();
            Tuple decode1 = decode(subpacket, false, mode);
            long processedBits = decode1.bits;
            tupleValues.add(decode1.value);
            long decodedNow = processedBits;
            while (processedBits != subpacketLength) {
                subpacket = subpacket.substring((int) decodedNow);
                System.out.println("subpacket length = " + subpacket.length());
                System.out.println("will process subpacketcik = " + subpacket);
                Tuple decode = decode(subpacket, false, mode);
                tupleValues.add(decode.value);
                decodedNow = decode.bits;
                System.out.println("now decoded = " + decodedNow);
                processedBits += decodedNow;
                System.out.println("processedBits = " + processedBits);

            }
            BigInteger val = getValue(tupleValues, mode);
            return new Tuple(processedBits + 16, val);
        } else {
            String subpacks = string.substring(1, 12);
            int numberOfSubpackets = Integer.valueOf(subpacks, 2);
            System.out.println("number of subpackets = " + numberOfSubpackets);
            String stringToProcess = string.substring(12);
            System.out.println("String to process with subpackets = " + stringToProcess);
            int decoded = 0;
            List<BigInteger> tupleValues = new ArrayList<>();
            for (int i = 0; i < numberOfSubpackets; i++) {
                Tuple decode = decode(stringToProcess.substring(decoded), false, mode);
                tupleValues.add(decode.value);
                decoded += decode.bits;
                System.out.println("decoded = " + decoded);
            }
            BigInteger result = getValue(tupleValues, mode);
            return new Tuple(decoded + 12, result);
        }
    }

    public static BigInteger getValue(List<BigInteger> values, int mode) {
        if (mode == 0) {
            BigInteger result = BigInteger.ZERO;
            for (BigInteger v : values) {
                result = result.add(v);
            }
            return result;
        } else if (mode == 1) {
            BigInteger result = BigInteger.ONE;
            for (BigInteger v : values) {
                result = result.multiply(v);
            }
            return result;
        } else if (mode == 2) {
            BigInteger result = new BigInteger("10000000000000000000000000000000");
            for (BigInteger v : values) {
                if (v.compareTo(result) == -1) {
                    result = v;
                }
            }
            return result;
        } else if (mode == 3) {

            BigInteger result = new BigInteger("-10000000000000000000000000000000");
            for (BigInteger v : values) {
                if (v.compareTo(result) > 0) {
                    result = v;
                }
            }
            return result;
        } else if (mode == 5) {
            if (values.get(0).compareTo(values.get(1)) > 0) {
                return BigInteger.ONE;
            } else {
                return BigInteger.ZERO;
            }
        } else if (mode == 6) {
            if (values.get(0).compareTo(values.get(1)) < 0) {
                return BigInteger.ONE;
            } else {
                return BigInteger.ZERO;
            }
        } else if (mode == 7) {
            if (values.get(0).equals(values.get(1))) {
                return BigInteger.ONE;
            } else {
                return BigInteger.ZERO;
            }
        }
        return new BigInteger("-1");
    }

    public static Tuple getLiteralValue(String string) {
        System.out.println("Literal to process = " + string);
        int nums = string.length() / 5;
        StringBuilder sb = new StringBuilder();
        int processedBits = 0;
        for (int i = 0; i < nums; i++) {
            String subs = string.substring(5 * i, +5 * (i + 1));
            sb.append(subs.substring(1));
            processedBits += 5;
            System.out.println("processedBits = " + processedBits);
            if (subs.charAt(0) == '0') {
                break;
            }
        }
        String result = sb.toString();
        Long integer = Long.valueOf(result, 2);
        System.out.println("Literal value = " + integer);
        System.out.println("processed bits = " + processedBits);
        return new Tuple(processedBits, BigInteger.valueOf(integer));
    }

    static String createBin(String input) {

        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray()) {
            sb.append(map.get(c));
        }
        return sb.toString();
    }

    static Map<Character, String> map = Map.ofEntries(
            Map.entry('0', "0000"),
            Map.entry('1', "0001"),
            Map.entry('2', "0010"),
            Map.entry('3', "0011"),
            Map.entry('4', "0100"),
            Map.entry('5', "0101"),
            Map.entry('6', "0110"),
            Map.entry('7', "0111"),
            Map.entry('8', "1000"),
            Map.entry('9', "1001"),
            Map.entry('A', "1010"),
            Map.entry('B', "1011"),
            Map.entry('C', "1100"),
            Map.entry('D', "1101"),
            Map.entry('E', "1110"),
            Map.entry('F', "1111")
    );

    public static String hexToBinary(String hex) {
        return new BigInteger(hex, 16).toString(2);
    }

}

class Tuple {
    long bits;
    BigInteger value;

    public Tuple(long bits, BigInteger value) {
        this.bits = bits;
        this.value = value;
    }

    public Tuple addToBits(long v) {
        bits += v;
        return new Tuple(bits, value);
    }
}