package software.aoc.day04;

import java.util.ArrayList;
import java.util.List;

class Matrix {
    private final List<char[]> matrix;
    private final List<Position> paperPositions;

    Matrix(List<PaperRow> grids) {
        this.matrix = new ArrayList<>();
        this.paperPositions = new ArrayList<>();
        grids.stream()
                .forEach(this::addToMatrix);
    }

    private void addToMatrix(PaperRow row) {
        matrix.add(row.paperRow().toCharArray());
    }

    public int numberOfPapers() {
        int rowId = -1;
        int papers = 0;
        for (char[] row : matrix) {
            rowId++;
            for (int posInRow = 0; posInRow < row.length; posInRow++) {
                if (!iAmAPaper(rowId, posInRow)) continue;
                if (!if4ConsecutivesIsBlocked(rowId, posInRow)){
                    paperPositions.add(Position.with(rowId, posInRow));
                    papers += 1;
                }
            }
        }
        return papers;
    }

    private boolean if4ConsecutivesIsBlocked(int rowId, int posInRow) {
        int posiblePapers = 0;
        for (int psRow = rowId - 1; psRow <= rowId + 1; psRow++) {
            for (int element = posInRow - 1; element <= posInRow + 1; element++) {
                if (thereAreTheSame(psRow, rowId, element, posInRow)) continue;
                if (outOfOrder(psRow, element)) continue;
                posiblePapers += iAmAPaper(psRow, element) ? 1 : 0;
            }
        }
        return posiblePapers >= 4;
    }

    public int maxNumberOfPapers() {
        int papers = 0;
        int removed;

        while ((removed = numberOfPapers()) != 0){
            papers += removed;
            paperPositions.stream()
                    .forEach(this::changeMatrix);
            paperPositions.clear();
        }
        return papers;
    }

    private void changeMatrix(Position position) {
        matrix.get(position.row())[position.column()] = '.';
    }

    private boolean thereAreTheSame(int psRow, int rowId, int element, int posInRow) {
        return (psRow == rowId && element == posInRow);
    }

    private boolean outOfOrder(int ps, int posInRow) {
        if (ps < 0 || posInRow < 0 || ps >= matrix.size()) return true;
        return posInRow >= matrix.get(ps).length;
    }

    private boolean iAmAPaper(int rowId, int posInARow) {
        return matrix.get(rowId)[posInARow] == '@';
    }

}
