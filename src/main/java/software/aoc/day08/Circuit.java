package software.aoc.day08;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Circuit {
    private final List<Coordinate> boxes;

    public static Circuit create() {
        return new Circuit();
    }

    private Circuit() {
        boxes = new ArrayList<>();
    }

    public Circuit with(String inputLines) {
        addBoxes(inputLines.split("\n"));
        return this;
    }

    public Circuit add(Coordinate box) {
        boxes.add(box);
        return this;
    }

    private void addBoxes(String[] inputLines) {
        Arrays.stream(inputLines)
                .map(Coordinate::from)
                .forEach(this::add);
    }

    public int countBoxes() {
        return boxes.size();
    }

    public List<Coordinate> getBoxes() {
        return new ArrayList<>(boxes);
    }
}