package software.aoc.day08;

import java.util.*;

public class CircuitManager {

    private final List<Coordinate> allCoordinates;
    private final List<Circuit> circuits;
    private final Map<Coordinate, Circuit> circuitByCoordinate;

    private Pair lastMergedPair;

    public static CircuitManager create() {
        return new CircuitManager();
    }

    private CircuitManager() {
        this.allCoordinates = new ArrayList<>();
        this.circuits = new ArrayList<>();
        this.circuitByCoordinate = new HashMap<>();
    }

    public CircuitManager with(String input) {
        add(input.split("\n"));
        return this;
    }

    public CircuitManager with(List<String> inputList) {
        add(inputList.toArray(new String[0]));
        return this;
    }

    private void add(String[] inputLines) {
        Arrays.stream(inputLines)
                .map(Coordinate::from)
                .forEach(coordinate -> {
                    allCoordinates.add(coordinate);
                    createCircuit(coordinate);
                });
    }

    private boolean createCircuit(Coordinate c) {
        return circuits.add(associateCircuitWithCoordinate(Circuit.create().add(c), c));
    }

    private Circuit associateCircuitWithCoordinate(Circuit circuit, Coordinate coordinate) {
        circuitByCoordinate.put(coordinate, circuit);
        return circuit;
    }

    public CircuitManager connectClosestPairs(int limit) {
        CircuitConnector connector = new CircuitConnector(allCoordinates, circuitByCoordinate, circuits);
        this.lastMergedPair = connector.connect(limit);

        return this;
    }

    public long countBiggest3Circuits() {
        return count(top3(circuits));
    }

    private List<Circuit> top3(List<Circuit> cts) {
        return cts.stream()
                .sorted(Comparator.comparingInt(Circuit::countBoxes).reversed())
                .limit(3)
                .toList();
    }

    private long count(List<Circuit> cts) {
        return cts.stream()
                .mapToLong(Circuit::countBoxes)
                .reduce(1, (a, b) -> a * b);
    }

    public long lastPairDistance() {
        if (lastMergedPair == null) return 0;
        return lastMergedPair.low().x() * lastMergedPair.high().x();
    }
}