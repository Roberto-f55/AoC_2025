package software.aoc.day08.a;

import software.aoc.day08.BoxConnection;
import software.aoc.day08.Circuit;
import software.aoc.day08.Coordinate;

import java.util.*;

public class CircuitManager {
    private final List<BoxConnection> boxes;
    private final List<Circuit> circuits;
    private final Map<BoxConnection, Circuit> boxToCircuit;
    private final Map<Coordinate, BoxConnection> getBox;
    private final List<Pair> pairs;

    public static CircuitManager create() {
        return new CircuitManager();
    }

    private CircuitManager() {
        boxes = new ArrayList<>();
        circuits = new ArrayList<>();
        boxToCircuit = new HashMap<>();
        pairs = new ArrayList<>();
        getBox = new HashMap<>();
    }

    public CircuitManager with(String bxs) {
        add(bxs.split("\n"));
        return this;
    }

    public CircuitManager with(List<String> bxs) {
        add(bxs.toArray(new String[0]));
        return this;
    }

    private void add(String[] bxs) {
        Arrays.stream(bxs)
                .forEach(coor -> {
                    BoxConnection box = newBox(coor);
                    boxes.add(box);
                    getBox.put(box.getCoordinate(), box);
                    createCircuit(box);
                });
    }

    private BoxConnection newBox(String box) {
        return BoxConnection.in(box);
    }

    private boolean createCircuit(BoxConnection b) {
        return circuits.add(relationWithBox(Circuit.create().add(b), b));
    }

    private Circuit relationWithBox(Circuit circuit, BoxConnection b) {
        boxToCircuit.put(b, circuit);
        return circuit;
    }

    public CircuitManager divideBoxes(int limit) {
        for (int frs = 0; frs < boxes.size(); frs++) {
            for (int scd = frs + 1; scd < boxes.size(); scd++) {
                pairs.add(new Pair(getCoordinate(frs), getCoordinate(scd)));
            }
        }
        pairs.sort(Comparator.comparingDouble(Pair::distance));
        unionFind(limit);
        return this;
    }

    private void unionFind(int limit) {
        int changes = 0;
        for (Pair pair : pairs) {
            changes++;
            canChange(pair);
            if (changes == limit) break;
        }
    }

    private boolean canChange(Pair pair) {
        Circuit frs = getCircuit(pair.getLow());
        Circuit scd = getCircuit(pair.getHigh());
        if (!frs.equals(scd)) return union(frs, scd);
        return false;
    }

    private Circuit getCircuit(Coordinate coordinate) {
        return boxToCircuit.get(getBox.get(coordinate));
    }

    private boolean union(Circuit frs, Circuit scd) {
        if (frs.countBoxes() >= scd.countBoxes()) smallToBig(scd, frs);
        else smallToBig(frs, scd);
        return true;
    }

    private void smallToBig(Circuit small, Circuit big) {
        small.getBoxes().stream()
                .forEach(b -> {
                    big.add(b);
                    boxToCircuit.put(b, big);
                });
        circuits.remove(small);
    }

    private Coordinate getCoordinate(int id) {
        return boxes.get(id).getCoordinate();
    }

    public int countCircuits() {
        return circuits.size();
    }

    public int countBoxes() {
        return circuits.stream()
                .mapToInt(c -> c.countBoxes())
                .sum();
    }

    public int countBiggest3Circuits() {
        return count(top3(circuits));
    }

    private List<Circuit> top3(List<Circuit> cts) {
        return sort(cts, 3);
    }

    private List<Circuit> sort(List<Circuit> cts, int limit) {
        return cts.stream()
                .sorted(Comparator.comparingInt(Circuit::countBoxes).reversed())
                .limit(limit)
                .toList();
    }

    private int count(List<Circuit> cts) {
        return cts.stream()
                .mapToInt(c -> c.countBoxes())
                .reduce(1, (a, b) -> a * b);
    }

    private class Pair {
        private final Coordinate low;
        private final Coordinate high;

        private Pair(Coordinate low, Coordinate high) {
            this.low = low;
            this.high = high;
        }

        public Coordinate getLow() {
            return low;
        }

        public Coordinate getHigh() {
            return high;
        }

        public double distance() {
            return distance2();
        }

        private double distance2() {
            return x() + y() + z();
        }

        private double x() {
            return square(low.x() - high.x());
        }

        private double y() {
            return square(low.y() - high.y());
        }

        private double z() {
            return square(low.z() - high.z());
        }

        private double square(double v) {
            return v * v;
        }
    }
}
