package solutions.day16;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

public class Solution {

    static int versionsSUm  = 0;
    public static void main(String[] args) throws IOException {

        boolean test = true;

        URL resource = Solution.class.getResource("/day16" + (test ? "_1" : "") + ".txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).filter(Predicate.not(String::isEmpty)).toList();


        String message = lines.get(0);

        decode(message, true);

        System.out.println("part1 = " + versionsSUm);

        System.out.println("part2 = ");
    }

    static int decode(String message, boolean prepare) {
        String binary;
        if(prepare)
            binary = prepareBinaryMsg(message);
        else binary = message;
//        System.out.println(binary);
        String version = binary.substring(0, 3);
        int intVersion = Integer.valueOf(version, 2);
        versionsSUm += intVersion;
        System.out.println("VERSION = " + intVersion);
        String packetTypeID = binary.substring(3, 6);
        int intPacketType = Integer.valueOf(packetTypeID, 2);
        if(intPacketType == 4) {
            return getLiteralValue(binary.substring(6));
        } else {
            parseOperator(binary.substring(6));
        }

        //String nbOfSubpackets = binary.substring(7, 7+12);
        return  -1;
    }

    public static void parseOperator(String string) {
        char lengthType = string.charAt(0);
        if(lengthType == '0') {
            String lengthSubpack = string.substring(1, 16);
            int intLengthSuyb = Integer.valueOf(lengthSubpack, 2);
            int totalSUb = intLengthSuyb / 16;
            int firstLength = intLengthSuyb - totalSUb * 16;
            decode(string.substring(16, 16+firstLength), false);
            int start = 16+firstLength;
            for(int i = 0; i < totalSUb; i++ ) {
                decode(string.substring(start + (16*i), start + (16*(i+1))), false);
            }
            int a = 12;
        } else {
            if(string.length() > 12) {
                String lengthSubpack = string.substring(1, 12);
                int intLengthSuyb = Integer.valueOf(lengthSubpack, 2);
                String rest = string.substring(12);
                int restLength = rest.length();
                int bitsPerSubpackage = restLength / intLengthSuyb;
                for(int i = 0; i < intLengthSuyb; i++) {
                    String sub = rest.substring((i*bitsPerSubpackage) ,((i+1)*bitsPerSubpackage));
                    int decode = decode(sub, false);
//                    System.out.println("dec sub = " + decode);
                }
            }
        }
    }

    public static int getLiteralValue(String string) {
        int length = string.length() / 5;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++) {
            String subs = string.substring(5*i, + 5*(i+1));
            sb.append(subs.substring(1));
//            System.out.println(subs);
            if(subs.charAt(0) == 0) {
                break;
            }
        }
        String result = sb.toString();
        Integer integer = Integer.valueOf(result, 2);
        return integer;
    }

    public static String prepareBinaryMsg(String message) {
        String binary = hexToBinary(message);
        int length = binary.length();
        int allN  = length / 4;
        int missing = length - allN*4;
        binary = "0".repeat(missing) + binary;
        return binary;
    }

    public static String hexToBinary(String hex) {
        return new BigInteger(hex, 16).toString(2);
    }

}