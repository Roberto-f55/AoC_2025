package software.aoc.day08;

import java.util.*;

public class CircuitConnector {

    private final List<Coordinate> allCoordinates;
    private final Map<Coordinate, Circuit> circuitByCoordinate;
    private final List<Circuit> circuits;
    private final List<Pair> allPairs;

    public CircuitConnector(List<Coordinate> coordinates,
                            Map<Coordinate, Circuit> circuitMap,
                            List<Circuit> circuitList) {
        this.allCoordinates = coordinates;
        this.circuitByCoordinate = circuitMap;
        this.circuits = circuitList;
        this.allPairs = new ArrayList<>();
    }

    public Pair connect(int limit) {
        generateAllPairs();
        sortPairsByDistance();
        return processConnections(limit);
    }

    private void generateAllPairs() {
        for (int i = 0; i < allCoordinates.size(); i++) {
            for (int j = i + 1; j < allCoordinates.size(); j++) {
                allPairs.add(new Pair(allCoordinates.get(i), allCoordinates.get(j)));
            }
        }
    }

    private void sortPairsByDistance() {
        allPairs.sort(Comparator.comparingLong(Pair::distance));
    }

    private Pair processConnections(int limit) {
        int changes = 0;
        Pair lastSuccess = null;

        for (Pair pair : allPairs) {
            changes++;
            if (tryToConnect(pair)) {
                lastSuccess = pair;
            }
            if (changes == limit) break;
        }
        return lastSuccess;
    }

    private boolean tryToConnect(Pair pair) {
        Circuit circuitA = getCircuit(pair.low());
        Circuit circuitB = getCircuit(pair.high());

        if (!circuitA.equals(circuitB)) {
            return mergeCircuits(circuitA, circuitB);
        }
        return false;
    }

    private Circuit getCircuit(Coordinate coordinate) {
        return circuitByCoordinate.get(coordinate);
    }

    private boolean mergeCircuits(Circuit circuitA, Circuit circuitB) {
        if (circuitA.countBoxes() >= circuitB.countBoxes()) {
            transferCoordinates(circuitB, circuitA);
        } else {
            transferCoordinates(circuitA, circuitB);
        }
        return true;
    }

    private void transferCoordinates(Circuit source, Circuit destination) {
        source.getBoxes().forEach(coordinate -> {
            destination.add(coordinate);
            circuitByCoordinate.put(coordinate, destination);
        });
        circuits.remove(source);
    }
}