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
import java.util.stream.Collectors;

public class Solution {

    private static Logger log = LogManager.getLogger(Solution.class);

    static List<Vector3D> differenceVectors = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        URL resource = solutions.day16.Solution.class.getResource("/day19.txt");
        List<String> lines = Files.lines(Paths.get(resource.getPath())).filter(Predicate.not(String::isEmpty)).toList();

        System.out.println("part1 = " + getFirstPartAnswer(lines));
        System.out.println("part2 = " + getSecondPartAnswer(lines));
    }

    static int getFirstPartAnswer(List<String> input) {
        return getAllDistinctScanners(input).size();
    }

    static int getSecondPartAnswer(List<String> input) {
        int maxDistance = Integer.MIN_VALUE;
        getAllDistinctScanners(input);
        for (Vector3D v1 : differenceVectors) {
            for (Vector3D v2 : differenceVectors) {
                int distance = (int) (Math.abs(v1.getX() - v2.getX()) + Math.abs(v1.getY() - v2.getY()) + Math.abs(v1.getZ() - v2.getZ()));
                if (distance > maxDistance) {
                    maxDistance = distance;
                }
            }
        }
        return maxDistance;
    }


    static Set<Vector3D> getAllDistinctScanners(List<String> input) {
        List<Scanner> scanners = readScanners(input);
        Scanner first = scanners.get(0);
        Set<Integer> handledScanners = new HashSet<>();

        Set<Vector3D> foundVectors = new HashSet<>(first.vectors);

        while (handledScanners.size() < scanners.size() - 1) {
            for (int i = 1; i < scanners.size(); i++) {
                log.info("will test scanner id: {}", i);
                Map<Vector3D, List<Double>> allFoundDistances = getAllDistancesFromScannerVectors(new ArrayList<>(foundVectors));
                log.info("all found distances map size = {}", allFoundDistances.size());
                var testedScanner = scanners.get(i);
                Map<Vector3D, List<Double>> allDistancesForTestedScanner = getAllDistancesFromScannerVectors(testedScanner.vectors);
                Triple<Vector3D, Vector3D, Collection<Double>> triple = getVectorPairWithAtLeast12TheSameDistances(allFoundDistances, allDistancesForTestedScanner);
                if (triple != null) {
                    Map<Vector3D, Double> vectorsAndDistancesForMainVector = getVectorsAndDistancesForVector(triple.getLeft(), triple.getRight(), foundVectors);
                    Map<Vector3D, Double> vectorsAndDistancesForTestedVector = getVectorsAndDistancesForVector(triple.getMiddle(), triple.getRight(), testedScanner.vectors);
                    log.info("mapSize1 = {}", vectorsAndDistancesForMainVector.size());
                    log.info("mapSize2 = {}", vectorsAndDistancesForTestedVector.size());
                    List<Pair<Vector3D, Vector3D>> correspondingPairFromVectors = createCorrespondingPairFromVectors(vectorsAndDistancesForMainVector, vectorsAndDistancesForTestedVector);
                    List<Vector3D> allVectorsFromFirstVectorPerspective = getAllVectorsFromFirstVectorPerspective(correspondingPairFromVectors, testedScanner.vectors);
                    log.info("Scanner {} HANDLED!", testedScanner.id);
                    foundVectors.addAll(allVectorsFromFirstVectorPerspective);
                    handledScanners.add(testedScanner.id);
                    log.info("all handled:\n");
                    handledScanners.forEach(integer -> log.info("{}", integer));
                    log.info("after add there are {} vectors in set", foundVectors.size());
                } else {
                    log.info("couldn't find at least 12 the same vectors for scanner with id: {}", testedScanner.id);
                }
            }
        }
        log.info("The full list of beacons (relative to scanner 0) is:");
        List<Vector3D> listAll = new ArrayList<>(foundVectors);
        Collections.sort(listAll, (o1, o2) -> (int) (o1.getX() - o2.getX()));
        listAll.forEach(v -> log.info("{},{},{}", v.getX(), v.getY(), v.getZ()));
        return foundVectors;
    }

    static List<Vector3D> getAllVectorsFromFirstVectorPerspective(List<Pair<Vector3D, Vector3D>> correspondingPairFromVectors, List<Vector3D> allVectorsToChange) {

        List<Vector3D> originalVectors = correspondingPairFromVectors.stream().map(vector3DVector3DPair -> vector3DVector3DPair.getKey()).collect(Collectors.toList());
        List<Vector3D> testedVectors = correspondingPairFromVectors.stream().map(vector3DVector3DPair -> vector3DVector3DPair.getValue()).collect(Collectors.toList());
        for (int xFix : List.of(-1, 1)) {
            for (int yFix : List.of(-1, 1)) {
                for (int zFix : List.of(-1, 1)) {
                    for (int permutation : List.of(0, 1, 2, 3, 4, 5)) {
                        List<Vector3D> changedVector = changeAllVectors(testedVectors, xFix, yFix, zFix, permutation);
                        if (areAllDifferencesTheSame(originalVectors, changedVector)) {
                            Vector3D differenceVector = getDifferenceVector(originalVectors, changedVector);
                            differenceVectors.add(differenceVector);
                            log.info("difference vector: {}", differenceVector);
                            List<Vector3D> allVectorsFromOriginalVectorPerspective = getAllVectorsFromOriginalVectorPerspective(xFix, yFix, zFix, permutation, differenceVector, allVectorsToChange);
                            log.info("all from original perspective:");
                            allVectorsFromOriginalVectorPerspective.forEach(v -> log.info("{}", v));
                            return allVectorsFromOriginalVectorPerspective;
                        }
                    }
                }
            }
        }
        return null;
    }

    static List<Vector3D> getAllVectorsFromOriginalVectorPerspective(int xFix, int yFix, int zFix, int permutation, Vector3D differenceVector, List<Vector3D> vectors) {
        return changeAllVectors(vectors, xFix, yFix, zFix, permutation).stream().map(vector3D -> vector3D.add(differenceVector)).toList();
    }

    static Vector3D getDifferenceVector(List<Vector3D> originalVectors, List<Vector3D> toTest) {
        return originalVectors.get(0).subtract(toTest.get(0));
    }

    static boolean areAllDifferencesTheSame(List<Vector3D> originalVectors, List<Vector3D> toTest) {
        Vector3D firstDiff = originalVectors.get(0).subtract(toTest.get(0));
        for (int i = 0; i < toTest.size(); i++) {
            if (!originalVectors.get(i).subtract(toTest.get(i)).equals(firstDiff)) {
                return false;
            }
        }
        return true;
    }


    static List<Pair<Vector3D, Vector3D>> createCorrespondingPairFromVectors(Map<Vector3D, Double> firstMap, Map<Vector3D, Double> secondMap) {
        List<Pair<Vector3D, Vector3D>> list = new ArrayList<>();
        for (Map.Entry<Vector3D, Double> first : firstMap.entrySet()) {
            for (Map.Entry<Vector3D, Double> second : secondMap.entrySet()) {
                if (first.getValue().equals(second.getValue())) {
                    list.add(new Pair<>(first.getKey(), second.getKey()));
                }
            }
        }
        return list;
    }

    static Triple<Vector3D, Vector3D, Collection<Double>> getVectorPairWithAtLeast12TheSameDistances(Map<Vector3D, List<Double>> first, Map<Vector3D, List<Double>> second) {
        for (Map.Entry<Vector3D, List<Double>> firstEntry : first.entrySet()) {
            for (Map.Entry<Vector3D, List<Double>> secondEntry : second.entrySet()) {
                Collection<Double> intersection = CollectionUtils.intersection(firstEntry.getValue(), secondEntry.getValue());
                if (intersection.size() >= 12) {
                    return Triple.of(firstEntry.getKey(), secondEntry.getKey(), intersection);
                }
            }
        }
        return null;
    }

    static Map<Vector3D, Double> getVectorsAndDistancesForVector(Vector3D vector, Collection distances, Collection<Vector3D> vectors) {
        Map<Vector3D, Double> mapToReturn = new HashMap<>();
        for (Vector3D vectorToTest : vectors) {
            double distance = vectorToTest.distance(vector);
            if (distances.contains(distance)) {
                mapToReturn.put(vectorToTest, distance);
            }
        }
        return mapToReturn;
    }

    static Map<Vector3D, List<Double>> getAllDistancesFromScannerVectors(List<Vector3D> vectors) {
        Map<Vector3D, List<Double>> map = new HashMap<>();
        for (int i = 0; i < vectors.size(); i++) {
            Vector3D firstTestedVector = vectors.get(i);
            List<Double> distances = new ArrayList<>();
            for (int j = 0; j < vectors.size(); j++) {
                distances.add(firstTestedVector.distance(vectors.get(j)));
            }
            map.put(firstTestedVector, distances);
        }
        return map;
    }

    static List<Vector3D> changeAllVectors(List<Vector3D> vectors, int x, int y, int z, int perm) {
        List<Vector3D> vectorsToReturn = new ArrayList<>();
        for (var vector : vectors) {
            vectorsToReturn.add(changeVector(vector, x, y, z, perm));
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



