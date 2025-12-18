package software.aoc.day08;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Circuit {
    private final List<BoxConnection> boxes;

    public static Circuit create() {
        return new Circuit();
    }

    private Circuit() {
        boxes = new ArrayList<>();
    }

    public Circuit with(String bxs) {
        addBoxes(bxs.split("\n"));
        return this;
    }

    public Circuit add(BoxConnection box) {
        boxes.add(box);
        return this;
    }

    private void addBoxes(String[] bxs) {
        Arrays.stream(bxs)
                .forEach(box -> boxes.add(newBox(box)));
    }

    private BoxConnection newBox(String box) {
        return BoxConnection.in(box);
    }

    public int countBoxes() {
        return boxes.size();
    }

    public List<BoxConnection> getBoxes() {
        return new ArrayList<>(boxes);
    }
}
