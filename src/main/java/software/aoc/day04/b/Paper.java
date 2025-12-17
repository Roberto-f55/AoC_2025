package software.aoc.day04.b;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Paper {
    private List<String> grid;

    public static Paper create() {
        return new Paper();
    }

    private Paper() {
        this.grid = new ArrayList<>();
    }

    public Paper put(String matrix_of_papers) {
        add(matrix_of_papers.split("\n"));
        return this;
    }

    public Paper add(String... row) {
        Arrays.stream(row)
                .forEach(this::add);
        return this;
    }

    private void add(String row) {
        grid.add(row);
    }

    public Matrix matrix() {
        return new Matrix(grid);
    }

    public int numberOfPapers(){
        return new Matrix(grid).numberOfPapers();
    }

    public int maxNumberOfPapers(){
        return new Matrix(grid).maxNumberOfPapers();
    }
}
