package software.aoc.day04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PapersManager {
    private final List<PaperRow> grid;

    public static PapersManager create(){
        return new PapersManager();
    }

    private PapersManager(){
        this.grid = new ArrayList<>();
    }

    public PapersManager add(String... row) {
        Arrays.stream(row)
                .forEach(this::add);
        return this;
    }

    private void add(String row) {
        grid.add(new PaperRow(row));
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
