package software.aoc.day07.a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Tree {

    private final List<char[]> structure;

    public static Tree create() {
        return new Tree();
    }

    private Tree() {
        this.structure = new ArrayList<>();
    }

    public void with(String treeStructure) {
        createStructure(treeStructure.split("\n"));
    }

    public void with(List<String> treeStructure) {
        createStructure(treeStructure.toArray(String[]::new));
    }

    private void createStructure(String[] str) {
        Arrays.stream(str)
                .map(s -> s.toCharArray())
                .forEach(structure::add);
    }

    public int countTachyons() {
        return outcome();
    }

    private int outcome() {
        int result = 0;
        for (int index = 0; index < structure.size(); index++) {
            result += whatCharacter(index);
        }
        return result;
    }

    private int whatCharacter(int rowIndex) {
        int posIndex = -1;
        int divide = 0;
        for (char charac : structure.get(rowIndex)) {
            posIndex++;

            if (charac == 'S') {
                changeStructure(rowIndex, posIndex);
                break;
            } else if (charac == '^' && stickOn(rowIndex - 1, posIndex)) {
                divide += divider(rowIndex, posIndex);
            } else if (charac == '|') changeStructure(rowIndex, posIndex);

        }
        return divide;
    }

    private boolean stickOn(int rowIndex, int posIndex) {
        return structure.get(rowIndex)[posIndex] == '|';
    }

    private int changeStructure(int rowIndex, int posIndex) {
        if (outOfLimits(rowIndex, posIndex) || !isPoint(rowIndex, posIndex)) return 0;
        structure.get(rowIndex + 1)[posIndex] = '|';
        return 1;
    }

    private boolean isPoint(int rowIndex, int posIndex) {
        return structure.get(rowIndex + 1)[posIndex] == '.';
    }

    private boolean outOfLimits(int rowIndex, int posIndex) {
        return (rowIndex + 1 >= structure.size() || posIndex < 0 || posIndex >= structure.get(rowIndex + 1).length);
    }

    private int divider(int rowIndex, int posIndex) {
        return left(rowIndex, posIndex) + right(rowIndex, posIndex) >= 1 ? 1 : 0;
    }

    private int left(int rowIndex, int posIndex) {
        return changeStructure(rowIndex, posIndex - 1);
    }

    private int right(int rowIndex, int posIndex) {
        return changeStructure(rowIndex, posIndex + 1);
    }

    private int sum(int rowIndex) {
        return (int) new String(structure.get(rowIndex))
                .chars()
                .filter(c -> c == '|')
                .count();
    }
}
