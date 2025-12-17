package software.aoc.day04.b;

import java.util.ArrayList;
import java.util.List;

class Matrix {
    private final List<char[]> matrix;
    private final List<Position> paperPositions;

    Matrix(List<String> grids) {
        this.matrix = new ArrayList<>();
        this.paperPositions = new ArrayList<>();
        grids.stream()
                .forEach(this::add_To_Matrix);
    }

    private void add_To_Matrix(String row) {
        matrix.add(row.toCharArray());
    }

    public int maxNumberOfPapers() {
        int papers = 0;
        int removed;

        while ((removed = numberOfPapers()) != 0){
            papers += removed;
            paperPositions.stream()
                    .forEach(this::changeMatrix);
        }
        return papers;
    }

    private void changeMatrix(Position position) {
        matrix.get(position.getRow())[position.getColumn()] = '.';
    }

    int numberOfPapers() {
        int row_id = -1;
        int papers = 0;
        for (char[] row : matrix) {
            row_id++;
            for (int pos_in_row = 0; pos_in_row < row.length; pos_in_row++) {
                if (i_Am_Not_A_Paper(row_id, pos_in_row)) continue;
                if (!if4Consecutives(row_id, pos_in_row)){
                    paperPositions.add(Position.with(row_id, pos_in_row));
                    papers += 1;
                }
            }
        }
        return papers;
    }

    private boolean if4Consecutives(int row_id, int pos_in_row) {
        int posible_papers = 0;
        for (int ps_row = row_id - 1; ps_row <= row_id + 1; ps_row++) {
            for (int element = pos_in_row - 1; element <= pos_in_row + 1; element++) {
                if (there_Are_The_Same(ps_row, row_id, element, pos_in_row)) continue;
                if (out_Of_Order(ps_row, element)) continue;
                posible_papers += i_Am_Not_A_Paper(ps_row, element) ? 0 : 1;
            }
        }
        return posible_papers >= 4;
    }

    private boolean there_Are_The_Same(int ps_row, int rowId, int element, int posInRow) {
        return (ps_row == rowId && element == posInRow);
    }

    private boolean out_Of_Order(int ps, int pos_in_row) {
        if (ps < 0 || pos_in_row < 0 || ps >= matrix.size()) return true;
        return pos_in_row >= matrix.get(ps).length;
    }

    private boolean i_Am_Not_A_Paper(int row_id, int pos_in_a_row) {
        return matrix.get(row_id)[pos_in_a_row] != '@';
    }

    private static class Position {
        private final int row;

        private final int column;

        private static Position with(int x, int y){
            return new Position(x, y);
        }

        private Position(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public int getColumn() {return column;}

        public int getRow() {return row;}
    }
}
