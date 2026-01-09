package software.aoc.day07;

import java.util.ArrayList;
import java.util.List;

public class TachyonsTree {
    private final List<char[]> structure;
    private final List<String> originalInput;

    TachyonsTree(List<String> input) {
        this.structure = new ArrayList<>();
        this.originalInput = new ArrayList<>(input);
        reset();
    }

    public void reset() {
        structure.clear();
        originalInput.stream()
                .map(String::toCharArray)
                .forEach(structure::add);
    }

    public char[] getRow(int index) {
        return structure.get(index);
    }

    public char get(int row, int col) {
        return structure.get(row)[col];
    }

    public void set(int row, int col, char val) {
        structure.get(row)[col] = val;
    }

    public int size() {
        return structure.size();
    }

    public int rowLength(int row) {
        return structure.get(row).length;
    }

    public boolean outOfLimits(int rowIndex, int posIndex) {
        return (rowIndex < 0 || rowIndex >= structure.size() ||
                posIndex < 0 || posIndex >= structure.get(rowIndex).length);
    }

    public boolean isPoint(int rowIndex, int posIndex) {
        return structure.get(rowIndex)[posIndex] == '.';
    }

    public int getPosS() {
        if (structure.isEmpty()) return -1;
        return new String(structure.get(0)).indexOf('S');
    }
}