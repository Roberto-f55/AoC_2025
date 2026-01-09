package software.aoc.day07;

import java.util.HashMap;
import java.util.Map;

public class TachyonsTreeManager {
    private final TachyonsTree tree;
    private Map<State, Long> memo;

    TachyonsTreeManager(TachyonsTree tree) {
        this.tree = tree;
    }

    public int countTachyonsA() {
        tree.reset();
        return outcome();
    }

    private int outcome() {
        int initialPos = tree.getPosS();
        return initialPos == -1 ? -1 : countTotalSplits(initialPos);
    }

    private int countTotalSplits(int initialPos) {
        int result = 0;
        propagateStick(0, initialPos);
        for (int index = 1; index < tree.size(); index++) {
            result += processRow(index);
        }
        return result == 0 ? 1 : result;
    }

    private int processRow(int rowIndex) {
        int posIndex = -1;
        int divide = 0;
        for (char charac : tree.getRow(rowIndex)) {
            posIndex++;

            if (charac == '^' && hasAStickBehind(rowIndex, posIndex)) {
                divide += processSpliter(rowIndex, posIndex);
            } else if (charac == '|') {
                propagateStick(rowIndex, posIndex);
            }
        }
        return divide;
    }

    private boolean hasAStickBehind(int rowIndex, int posIndex) {
        return tree.get(rowIndex - 1, posIndex) == '|';
    }

    private int propagateStick(int rowIndex, int posIndex) {
        if (tree.outOfLimits(rowIndex + 1, posIndex) || !tree.isPoint(rowIndex + 1, posIndex)) return 0;
        tree.set(rowIndex + 1, posIndex, '|');
        return 1;
    }

    private int processSpliter(int rowIndex, int posIndex) {
        return left(rowIndex, posIndex) + right(rowIndex, posIndex) >= 1 ? 1 : 0;
    }

    private int left(int rowIndex, int posIndex) {
        return propagateStick(rowIndex, posIndex - 1);
    }

    private int right(int rowIndex, int posIndex) {
        return propagateStick(rowIndex, posIndex + 1);
    }

    public long countTachyonsB() {
        tree.reset();
        return tryDfs();
    }

    public long tryDfs() {
        memo = new HashMap<>();
        return dfs(0, tree.getPosS());
    }

    private long dfs(int row, int stickPos) {
        row = whileStickCanContinue(row, stickPos);

        State key = new State(row, stickPos);
        if (memo.containsKey(key)) return memo.get(key);

        if (tree.outOfLimits(row, stickPos)) {
            memo.put(key, 1L);
            return 1;
        }

        if (tree.outOfLimits(row + 1, stickPos)) {
            memo.put(key, 1L);
            return 1;
        }

        if (tree.get(row + 1, stickPos) == '^') {
            long total = 0;

            if (stickPos - 1 >= 0)
                total += dfs(row + 1, stickPos - 1);

            if (stickPos + 1 < tree.rowLength(row + 1))
                total += dfs(row + 1, stickPos + 1);

            memo.put(key, total);
            return total;
        }

        memo.put(key, 1L);
        return 1;
    }

    private int whileStickCanContinue(int row, int stickPos) {
        while (!tree.outOfLimits(row + 1, stickPos) && tree.isPoint(row + 1, stickPos)) {
            row++;
        }
        return row;
    }
}