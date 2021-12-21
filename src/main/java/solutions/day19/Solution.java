package solutions.day19;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;

public class Solution {

    private static Logger log = LogManager.getLogger(Solution.class);

    public static void main(String[] args) throws IOException {
        URL resource = solutions.day16.Solution.class.getResource("/day19.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).filter(Predicate.not(String::isEmpty)).toList();

        System.out.println("part2 = " + getSecondPartAnswer(lines));
    }


    static Map<Double, List<Pair<Vector3D, Vector3D>>> getDistancesBetweenVectors(Scanner scanner) {
        Map<Double, List<Pair<Vector3D, Vector3D>>> mapToReturn = new HashMap<>();
        for (int i = 0; i < scanner.vectors.size(); i++) {
            for (int j = 0; j < scanner.vectors.size(); j++) {
                Vector3D first = scanner.vectors.get(i);
                Vector3D second = scanner.vectors.get(j);
                double firstDistance = first.distance(second);
                if (mapToReturn.containsKey(firstDistance)) {
                    mapToReturn.get(firstDistance).add(new Pair<>(first, second));
                } else {
                    List<Pair<Vector3D, Vector3D>> list = new ArrayList<>();
                    list.add(new Pair<>(first, second));
                    mapToReturn.put(firstDistance, list);
                }
            }
        }
        return mapToReturn;
    }



    static int getTestAnswer2(List<String> input) {
        List<Scanner> scanners = readScanners(input);
        Scanner first = scanners.get(0);

        Set<Vector3D> foundVectors = new HashSet<>(first.vectors);

        var allFoundDistances = getAllDistancesFromScannerVectors(new ArrayList<>(foundVectors));

        for (int i = 1; i < scanners.size(); i++) {
            var testedScanner = scanners.get(i);
            var allDistancesForTestedScanner = getAllDistancesFromScannerVectors(testedScanner.vectors);
            Triple<Vector3D, Vector3D, Collection<Double>> triple = getVectorPairWithAtLeast12TheSameDistances(allFoundDistances, allDistancesForTestedScanner);
            Map<Vector3D, Double> vectorsAndDistancesForMainVector = getVectorsAndDistancesForVector(triple.getLeft(), triple.getRight(), first);
            Map<Vector3D, Double> vectorsAndDistancesForTestedVector = getVectorsAndDistancesForVector(triple.getMiddle(), triple.getRight(), testedScanner);
            log.info("mapSize1 = {}", vectorsAndDistancesForMainVector.size());
            log.info("mapSize2 = {}", vectorsAndDistancesForTestedVector.size());
            List<Pair<Vector3D, Vector3D>> correspondingPairFromVectors = createCorrespondingPairFromVectors(vectorsAndDistancesForMainVector, vectorsAndDistancesForTestedVector);
            log.info("corresponding vectors:");
            for (Pair<Vector3D, Vector3D> correspondingPairFromVector : correspondingPairFromVectors) {
                log.info("{} {}", correspondingPairFromVector.getFirst(), correspondingPairFromVector.getSecond());
            }
            getOperationsForVectorTransformation(first.vectors, testedScanner.vectors);


        }
        return 1;
    }

    static List<Vector3D> getOperationsForVectorTransformation(List<Vector3D> original, List<Vector3D> transformed) {

        for (int xFix : List.of(-1, 1)) {
            for (int yFix : List.of(-1, 1)) {
                for (int zFix : List.of(-1, 1)) {
                    for (int permutation : List.of(0, 1, 2, 3, 4, 5)) {
                        List<Vector3D> changedVector = changeAllVectors(transformed, xFix, yFix, zFix, permutation);
                        boolean areEquals = areAllDifferencesTheSame(original, changedVector);
                        log.info("are equals? {}", areEquals);
                        if(areEquals) {
                            log.info("they are:");
                            for(Vector3D v : changedVector) {
                                log.info("{}", v);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    static boolean areAllDifferencesTheSame(List<Vector3D> originalVectors, List<Vector3D> toTest) {
        Vector3D firstDiff = originalVectors.get(0).subtract(toTest.get(0));
        for(int i = 0; i < toTest.size(); i++) {
            if(!originalVectors.get(i).subtract(toTest.get(i)).equals(firstDiff)) {
                return false;
            }
        }
        return true;
    }


    static List<Pair<Vector3D, Vector3D>> createCorrespondingPairFromVectors(Map<Vector3D, Double> firstMap, Map<Vector3D, Double> secondMap) {
        List<Pair<Vector3D, Vector3D>> list = new ArrayList<>();
        for (Map.Entry<Vector3D, Double> first : firstMap.entrySet()) {
            for (Map.Entry<Vector3D, Double> second : secondMap.entrySet()) {
                if(first.getValue().equals(second.getValue())) {
                    list.add(new Pair<>(first.getKey(), second.getKey()));
                }
            }
        }
        return list;
    }

    static Triple<Vector3D, Vector3D, Collection<Double>> getVectorPairWithAtLeast12TheSameDistances(Map<Vector3D, List<Double>> first, Map<Vector3D, List<Double>> second) {
        for(Map.Entry<Vector3D, List<Double>> firstEntry : first.entrySet()) {
            for(Map.Entry<Vector3D, List<Double>> secondEntry : second.entrySet()) {
                Collection<Double> intersection = CollectionUtils.intersection(firstEntry.getValue(), secondEntry.getValue());
                if(intersection.size() == 12) {
                    return Triple.of(firstEntry.getKey(), secondEntry.getKey(), intersection);
                }
            }
        }
        return null;
    }

    static Map<Vector3D, Double> getVectorsAndDistancesForVector(Vector3D vector, Collection distances, Scanner scannerWithVector) {
        Map<Vector3D, Double> mapToReturn = new HashMap<>();
        for(Vector3D vectorToTest : scannerWithVector.vectors) {
            double distance = vectorToTest.distance(vector);
            if(distances.contains(distance)) {
                mapToReturn.put(vectorToTest, distance);
            }
        }
        return mapToReturn;
    }

    static Map<Vector3D, List<Double>> getAllDistancesFromScannerVectors(List<Vector3D> vectors ) {
        Map<Vector3D, List<Double>> map = new HashMap<>();
        for(int i = 0; i < vectors.size(); i++) {
            Vector3D firstTestedVector = vectors.get(i);
            List<Double> distances = new ArrayList<>();
            for(int j = 0; j < vectors.size(); j++) {
                distances.add(firstTestedVector.distance(vectors.get(j)));
            }
            map.put(firstTestedVector, distances);
        }
        return map;
    }

    static List<Vector3D> changeAllVectors(List<Vector3D> vectors, int x, int y, int z, int perm) {
        List<Vector3D> vectorsToReturn = new ArrayList<>();
        for(var vector : vectors) {
            vectorsToReturn.add(changeVector(vector, x, y, z,perm));
        }
        return vectorsToReturn;
    }

    static Vector3D changeVector(Vector3D input, int x, int y, int z, int perm) {
        double newX = x * input.getX();
        double newY = y * input.getY();
        double newZ = z * input.getZ();
        Vector3D newVector = new Vector3D(newX, newY, newZ);
        return permute(newVector, perm);
    }

    static Vector3D permute(Vector3D input, int option) {
            /*
                x y z
                x z y
                y x z
                y z x
                z x y
                z y x
             */
        double x = input.getX();
        double y = input.getY();
        double z = input.getZ();
        switch (option) {
            case 1:
                return new Vector3D(x, z, y);
            case 2:
                return new Vector3D(y, x, z);
            case 3:
                return new Vector3D(y, z, x);
            case 4:
                return new Vector3D(z, x, y);
            case 5:
                return new Vector3D(z, y, x);
        }
        return input;
    }


    static int getSecondPartAnswer(List<String> input) {
        return 2;
    }

    static List<Scanner> readScanners(List<String> input) {
        List<Scanner> scanners = new ArrayList<>();
        List<Position> positions = null;
        List<Vector3D> vectors = null;
        int id = 0;
        for (String line : input) {
            if (line.contains("scanner")) {
                if (positions != null) {
                    Scanner scanner = new Scanner(id, positions, vectors);
                    scanners.add(scanner);
                    id++;
                }
                positions = new ArrayList<>();
                vectors = new ArrayList<>();
            } else if (!line.isBlank()) {
                String[] split = line.split(",");
                int x = Integer.parseInt(split[0]);
                int y = Integer.parseInt(split[1]);
                int z = Integer.parseInt(split[2]);
                Position position = new Position(x, y, z);
                Vector3D vector = new Vector3D(x, y, z);
                positions.add(position);
                vectors.add(vector);
            } else {
                Scanner scanner = new Scanner(id, positions, vectors);
                scanners.add(scanner);
            }
        }
        Scanner lastScanner = new Scanner(id, positions, vectors);
        scanners.add(lastScanner);
        return scanners;
    }
}

class Scanner {

    int id;
    public List<Position> positions;
    public List<Vector3D> vectors;

    public Scanner(int id, List<Position> positions, List<Vector3D> vectors) {
        this.id = id;
        this.positions = positions;
        this.vectors = vectors;
    }
}

class Position {

    int x;
    int y;
    int z;

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}



