package software.aoc.day12;

import java.util.*;

public record TreeArea(int width, int length, Map<Integer, Integer> inventory) {

    public static TreeArea parse(String input) {
        String[] mainParts = input.split(": ");
        String[] dims = mainParts[0].split("x");

        int w = Integer.parseInt(dims[0]);
        int l = Integer.parseInt(dims[1]);

        String[] quantities = mainParts[1].split(" ");
        Map<Integer, Integer> items = new HashMap<>();

        for (int i = 0; i < quantities.length; i++) {
            int qty = Integer.parseInt(quantities[i]);
            if (qty > 0) {
                items.put(i, qty);
            }
        }

        return new TreeArea(w, l, Collections.unmodifiableMap(items));
    }

    public int totalItemCount() {
        int sum = 0;
        for (int count : inventory.values()) {
            sum += count;
        }
        return sum;
    }

    public int totalGridArea() {
        return width * length;
    }

    public List<Integer> getExpandedInventoryList() {
        List<Integer> list = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : inventory.entrySet()) {
            for (int k = 0; k < entry.getValue(); k++) {
                list.add(entry.getKey());
            }
        }
        return list;
    }

    public List<Point2D> getValidAnchors() {
        List<Point2D> anchors = new ArrayList<>();
        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < length - 1; y++) {
                anchors.add(new Point2D(x, y));
            }
        }
        return anchors;
    }
}