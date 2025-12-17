package software.aoc.day04.a;

import java.util.ArrayList;
import java.util.List;

class Matrix {
    private final List<char[]> matrix;

    Matrix(List<String> grids) {
        this.matrix = new ArrayList<>();
        grids.stream()
                .forEach(this::addToMatrix);
    }

    private void addToMatrix(String row) {
        matrix.add(row.toCharArray());
    }

     int numberOfPapers() {
        int row_id = -1;
        int papers = 0;
        for (char[] row : matrix) {
            row_id++;
            for (int pos_in_row = 0; pos_in_row < row.length; pos_in_row++) {
                if (iAmNotAPaper(row_id, pos_in_row)) continue;
                papers += if4Consecutives(row_id, pos_in_row) ? 0 : 1;
            }
        }
        return papers;
    }

    private boolean if4Consecutives(int row_id, int pos_in_row) {
        int posible_papers = 0;
        for (int ps_row = row_id - 1; ps_row <= row_id + 1; ps_row++) {
            for (int element = pos_in_row - 1; element <= pos_in_row + 1; element++) {
                if (thereAreTheSame(ps_row, row_id, element, pos_in_row)) continue;
                if (outOfOrder(ps_row, element)) continue;
                posible_papers += iAmNotAPaper(ps_row, element) ? 0 : 1;
            }
        }
        return posible_papers >= 4;
    }

    private boolean thereAreTheSame(int ps_row, int rowId, int element, int posInRow) {
        return (ps_row == rowId && element == posInRow);
    }

    private boolean outOfOrder(int ps, int pos_in_row) {
        if (ps < 0 || pos_in_row < 0 || ps >= matrix.size()) return true;
        return pos_in_row >= matrix.get(ps).length;
    }

    private boolean iAmNotAPaper(int row_id, int pos_in_a_row) {
        return matrix.get(row_id)[pos_in_a_row] != '@';
    }
}
